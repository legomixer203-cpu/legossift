package net.mcreator.legossift.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.mcreator.legossift.init.SingerSoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;

import net.mcreator.legossift.procedures.SingerTickProcedure;
import net.mcreator.legossift.init.LegosSiftModEntities;
import net.mcreator.legossift.LegosSiftMod;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

import com.geckolib.util.GeckoLibUtil;
import com.geckolib.cache.animation.keyframeevent.ParticleKeyframeData;
import com.geckolib.animation.state.KeyFrameEvent;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.state.AnimationPoint;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.object.LoopType;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.AnimationController;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.GeoEntity;

public class SingerEntity extends PathfinderMob implements GeoEntity {

    public static final EntityDataAccessor<Boolean> SHOOT =
            SynchedEntityData.defineId(SingerEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<String> ANIMATION =
            SynchedEntityData.defineId(SingerEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> TEXTURE =
            SynchedEntityData.defineId(SingerEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> ANIMATION_SINGING =
            SynchedEntityData.defineId(SingerEntity.class, EntityDataSerializers.STRING);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;

    public String animationprocedure = "empty";

    private String procedureControllerRequester = "";

    public String animation_singing = "empty";

    private String prevAnim_singing = "empty";

    private int lastRequestTick_singing = -100;

    private static final String ANIMATION_REQUEST_SEPARATOR = "#";

    private int animationRequestCounter;

    String prevAnim = "empty";

    public SingerEntity(EntityType<SingerEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(false);
    }

    // =====================================================
    // ENTITY DATA
    // =====================================================

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);

        builder.define(SHOOT, false);
        builder.define(ANIMATION, "undefined");
        builder.define(TEXTURE, "singer1");
        builder.define(ANIMATION_SINGING, "undefined");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    // =====================================================
    // AI
    // =====================================================

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new FloatGoal(this));
    }

    // =====================================================
    // SOUNDS
    // =====================================================

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SingerSoundEvents.SINGERHURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SingerSoundEvents.SINGERHURT.get();
    }

    // =====================================================
    // SAVE DATA
    // =====================================================

    @Override
    public void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);

        valueOutput.putString("Texture", this.getTexture());
        valueOutput.putString("AnimationProcedure", this.entityData.get(ANIMATION));
        valueOutput.putString("AnimationSinging", this.entityData.get(ANIMATION_SINGING));
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);

        String savedTexture = valueInput.getStringOr("Texture", "singer1");

        if (savedTexture.equals("singer_basecolor")) {
            savedTexture = "singer1";
        }

        this.setTexture(savedTexture);

        this.entityData.set(
                ANIMATION,
                valueInput.getStringOr("AnimationProcedure", "undefined")
        );

        this.entityData.set(
                ANIMATION_SINGING,
                valueInput.getStringOr("AnimationSinging", "undefined")
        );
    }

    // =====================================================
    // TICK
    // =====================================================

    @Override
    public void baseTick() {
        super.baseTick();

        // Run Singer behavior on the server.
        if (!this.level().isClientSide()) {
            SingerTickProcedure.execute(
                    this.level(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    this
            );
        }

        // Synchronize animations on clients.
        if (this.level().isClientSide()) {
            this.applySyncedControllerAnimations();
        }

        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale(1f);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    // =====================================================
    // REGISTRATION
    // =====================================================

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register(
                LegosSiftModEntities.SINGER.get(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 30);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);

        return builder;
    }

    // =====================================================
    // MOVEMENT ANIMATION
    // =====================================================

    private PlayState movementPredicate(AnimationTest<SingerEntity> event) {

        // Singing takes priority over walking and idle.
        if (!this.animation_singing.equals("empty")) {
            return PlayState.STOP;
        }

        if (this.animationprocedure.equals("empty")) {

            if (event.isMoving()) {
                return event.setAndContinue(
                        RawAnimation.begin().thenLoop("animation.singer.walk")
                );
            }

            return event.setAndContinue(
                    RawAnimation.begin().thenLoop("animation.singer.idle")
            );
        }

        return PlayState.STOP;
    }

    // =====================================================
    // PROCEDURE ANIMATION
    // =====================================================

    private PlayState procedurePredicate(AnimationTest<SingerEntity> event) {

        if ((!animationprocedure.equals("empty")
                && hasPlayOnceAnimationFinished(event.controller()))
                || (!this.animationprocedure.equals(prevAnim)
                && !this.animationprocedure.equals("empty"))) {

            if (!this.animationprocedure.equals(prevAnim)) {
                event.controller().reset();
            }

            event.controller().setAnimation(
                    RawAnimation.begin().thenPlay(this.animationprocedure)
            );

            if (hasPlayOnceAnimationFinished(event.controller())) {
                this.animationprocedure = "empty";
                event.controller().reset();
            }

        } else if (animationprocedure.equals("empty")) {
            prevAnim = "empty";
            return PlayState.STOP;
        }

        prevAnim = this.animationprocedure;

        return PlayState.CONTINUE;
    }

    private static boolean hasPlayOnceAnimationFinished(AnimationController<?> controller) {

        if (!controller.hasAnimationFinished()) {
            return false;
        }

        AnimationPoint point = controller.getCurrentAnimationPoint();

        if (point == null) {
            return true;
        }

        LoopType loopType = point.loopType() == LoopType.DEFAULT
                ? point.animation().loopType()
                : point.loopType();

        return loopType != LoopType.HOLD_ON_LAST_FRAME
                && loopType != LoopType.LOOP;
    }

    // =====================================================
    // SINGING ANIMATION
    // =====================================================

    private PlayState controllerPredicate_singing(
            AnimationTest<SingerEntity> event
    ) {

        if (this.animation_singing.equals("empty")) {
            this.prevAnim_singing = "empty";
            return PlayState.STOP;
        }

        if (!this.animation_singing.equals(this.prevAnim_singing)) {

            event.controller().reset();

            event.controller().setAnimation(
                    RawAnimation.begin().thenLoop(
                            stripAnimationRequest(this.animation_singing)
                    )
            );

            this.prevAnim_singing = this.animation_singing;
        }

        return PlayState.CONTINUE;
    }

    // =====================================================
    // DEATH
    // =====================================================

    @Override
    protected void tickDeath() {
        ++this.deathTime;

        if (this.deathTime == 20) {
            this.remove(SingerEntity.RemovalReason.KILLED);
        }
    }

    // =====================================================
    // SYNCHRONIZATION
    // =====================================================

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        this.entityData.set(ANIMATION, animation);
        this.animationprocedure = animation;
        this.procedureControllerRequester = "";
    }

    public void applySyncedControllerAnimations() {

        String synced_singing = this.entityData.get(ANIMATION_SINGING);

        if (!synced_singing.equals("undefined")) {

            this.entityData.set(ANIMATION_SINGING, "undefined");

            this.animation_singing = synced_singing;
        }
    }

    // =====================================================
    // ANIMATION REQUEST HANDLING
    // =====================================================

    private String tagAnimationRequest(String animation) {

        if (animation == null || animation.isBlank() || animation.equals("empty")) {
            return "empty";
        }

        return animation + ANIMATION_REQUEST_SEPARATOR
                + (++this.animationRequestCounter);
    }

    private static String stripAnimationRequest(String animation) {

        int separator = animation.indexOf(ANIMATION_REQUEST_SEPARATOR);

        return separator < 0
                ? animation
                : animation.substring(0, separator);
    }

    private boolean isRepeatedRequest(
            String current,
            String animation,
            int lastRequestTick
    ) {

        return animation != null
                && !animation.equals("empty")
                && stripAnimationRequest(current).equals(animation)
                && this.tickCount - lastRequestTick <= 1;
    }

    public void setControllerAnimation(String controller, String animation) {

        if (controller == null || controller.isBlank()) {
            this.setAnimation(animation);
            return;
        }

        switch (controller) {

            case "singing" -> {

                boolean repeated_singing = isRepeatedRequest(
                        this.animation_singing,
                        animation,
                        this.lastRequestTick_singing
                );

                this.lastRequestTick_singing = this.tickCount;

                if (repeated_singing) {
                    return;
                }

                String tagged_singing = tagAnimationRequest(animation);

                this.entityData.set(ANIMATION_SINGING, tagged_singing);

                this.animation_singing = tagged_singing;
            }

            default -> {

                if ("empty".equals(animation)
                        && !controller.equals(this.procedureControllerRequester)) {
                    return;
                }

                this.setAnimation(animation);

                this.procedureControllerRequester = controller;
            }
        }
    }

    public String getControllerAnimation(String controller) {

        if (controller == null || controller.isBlank()) {
            return this.animationprocedure;
        }

        return switch (controller) {

            case "singing" -> stripAnimationRequest(this.animation_singing);

            default -> this.animationprocedure;
        };
    }

    // =====================================================
    // GECKOLIB CONTROLLERS
    // =====================================================

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {

        // Singing animation.
        data.add(
                new AnimationController<>(
                        "singing",
                        4,
                        this::controllerPredicate_singing
                )
                .setAnimationSpeed(0.75D)
                .setParticleKeyframeHandler(this::spawnKeyframeParticle)
        );

        // Walking and idle animations.
        data.add(
                new AnimationController<>(
                        "movement",
                        4,
                        this::movementPredicate
                ).setParticleKeyframeHandler(this::spawnKeyframeParticle)
        );

        // Other procedure animations.
        data.add(
                new AnimationController<>(
                        "procedure",
                        4,
                        this::procedurePredicate
                ).setParticleKeyframeHandler(this::spawnKeyframeParticle)
        );
    }

    // =====================================================
    // PARTICLE KEYFRAMES
    // =====================================================

    private static final Set<String> UNKNOWN_PARTICLE_EFFECTS =
            ConcurrentHashMap.newKeySet();

    private void spawnKeyframeParticle(
            KeyFrameEvent<SingerEntity, ParticleKeyframeData> event
    ) {

        String effect = event.keyframeData().getEffect();

        Identifier id = Identifier.tryParse(effect);

        if (id != null
                && BuiltInRegistries.PARTICLE_TYPE.getValue(id) instanceof SimpleParticleType particle) {

            this.level().addParticle(
                    particle,
                    this.getX(),
                    this.getY() + this.getBbHeight() * 0.5,
                    this.getZ(),
                    0,
                    0,
                    0
            );

        } else if (UNKNOWN_PARTICLE_EFFECTS.add(effect)) {

            LegosSiftMod.LOGGER.warn(
                    "Particle keyframe effect \"{}\" in Singer animations is not a registered particle id, so nothing is spawned",
                    effect
            );
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}