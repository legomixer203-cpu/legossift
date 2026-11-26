package net.mcreator.singerdemo.entity;

import net.mcreator.singerdemo.procedures.SingerTickProcedure;
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

/** Peaceful Singer mob. The tick procedure handles its song and Soul Block effect. */
public class SingerEntity extends PathfinderMob {
    public SingerEntity(EntityType<? extends SingerEntity> entityType, Level level) {
        super(entityType, level);
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
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            SingerTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        }
    }
}
