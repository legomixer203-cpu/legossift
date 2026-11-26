package net.mcreator.singerdemo.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;

import net.mcreator.singerdemo.init.SingerdemoModSounds;
import net.mcreator.singerdemo.procedures.SingerTickProcedure;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import java.util.List;
import java.util.function.Supplier;

/**
 * GeckoLib Singer mob. Keeps the existing wandering AI, attributes, and
 * Soul Block procedure while adding synchronized, randomly selected songs.
 */
public class SingerEntity extends PathfinderMob implements GeoEntity {
    private static final RawAnimation IDLE_ANIMATION =
            RawAnimation.begin().thenLoop("animation.singer.idle");
    private static final RawAnimation WALK_ANIMATION =
            RawAnimation.begin().thenLoop("animation.singer.walk");
    private static final RawAnimation SING_ANIMATION =
            RawAnimation.begin().thenLoop("animation.singer.sing");

    private static final float SING_CHANCE_PER_ROLL = 0.20F;
    private static final int MIN_SING_CHECK_DELAY = 20 * 8;
    private static final int MAX_SING_CHECK_DELAY = 20 * 16;

    /*
     * Replace these example durations with the actual length of each OGG file.
     * Minecraft runs at 20 ticks per second.
     */
    private static final List<SingerSong> SINGING_CLIPS = List.of(
            new SingerSong(SingerdemoModSounds.SINGER_SONG_1, 8 * 20),
            new SingerSong(SingerdemoModSounds.SINGER_SONG_2, 14 * 20),
            new SingerSong(SingerdemoModSounds.SINGER_SONG_3, 23 * 20),
            new SingerSong(SingerdemoModSounds.SINGER_SONG_4, 5 * 20)
    );

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private int singingTicksRemaining = 0;
    private int singingCheckCooldown = MIN_SING_CHECK_DELAY;
    private int lastSongIndex = -1;

    private record SingerSong(Supplier<SoundEvent> sound, int durationTicks) {
    }

    public SingerEntity(EntityType<? extends SingerEntity> entityType, Level level) {
        super(entityType, level);
        this.singingCheckCooldown = MIN_SING_CHECK_DELAY
                + this.getRandom().nextInt(MAX_SING_CHECK_DELAY - MIN_SING_CHECK_DELAY + 1);
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        // No custom spawn placement rules.
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Locomotion is independent from singing, so the Singer can keep walking
        // while the singing controller plays its upper-body/head animation.
        controllers.add(
                new AnimationController<SingerEntity>("movement", 4, state ->
                        state.isMoving()
                                ? state.setAndContinue(WALK_ANIMATION)
                                : state.setAndContinue(IDLE_ANIMATION)
                ),
                new AnimationController<SingerEntity>("singing", 2, state -> PlayState.STOP)
                        .triggerableAnim("sing", SING_ANIMATION)
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            SingerTickProcedure.execute(
                    this.level(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    this
            );
            this.tickSinging();
        }
    }

    /** Runs only on the server; GeckoLib syncs the triggered animation to clients. */
    private void tickSinging() {
        if (this.singingTicksRemaining > 0) {
            this.singingTicksRemaining--;
            if (this.singingTicksRemaining == 0) {
                this.stopTriggeredAnim("singing", "sing");
            }
            return;
        }

        this.singingCheckCooldown--;
        if (this.singingCheckCooldown > 0) {
            return;
        }

        this.singingCheckCooldown = MIN_SING_CHECK_DELAY
                + this.getRandom().nextInt(MAX_SING_CHECK_DELAY - MIN_SING_CHECK_DELAY + 1);

        if (this.getRandom().nextFloat() >= SING_CHANCE_PER_ROLL || SINGING_CLIPS.isEmpty()) {
            return;
        }

        int selectedIndex = this.getRandom().nextInt(SINGING_CLIPS.size());
        if (SINGING_CLIPS.size() > 1 && selectedIndex == this.lastSongIndex) {
            selectedIndex = (selectedIndex + 1) % SINGING_CLIPS.size();
        }
        this.lastSongIndex = selectedIndex;

        SingerSong selectedSong = SINGING_CLIPS.get(selectedIndex);
        this.singingTicksRemaining = Math.max(1, selectedSong.durationTicks());

        // Start audio and the network-synchronized GeckoLib animation together.
        this.level().playSound(
                null,
                this,
                selectedSong.sound().get(),
                SoundSource.NEUTRAL,
                1.0F,
                1.0F
        );
        this.triggerAnim("singing", "sing");
    }
}
