package net.mcreator.singerdemo.procedures;

import net.mcreator.singerdemo.init.SingerdemoModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;

/**
 * Server-side Singer behavior: periodic singing and a soul-particle
 * materialization sequence that places one Soul Block after 50 ticks.
 */
public final class SingerTickProcedure {
    private SingerTickProcedure() {
    }

    /** Compatibility method for any old MCreator-generated no-argument call. */
    public static void execute() {
        // Intentionally empty. SingerEntity calls the parameterized overload below.
    }

    /**
     * Called once per server tick from SingerEntity.
     *
     * @param world entity world
     * @param x entity X position
     * @param y entity Y position
     * @param z entity Z position
     * @param entity Singer instance
     */
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || !(world instanceof ServerLevel serverLevel)) {
            return;
        }

        // One song every 600 ticks (30 seconds at 20 TPS).
        int cycle = Math.floorMod(entity.tickCount, 600);

        if (cycle == 0) {
            serverLevel.playSound(null, entity.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME,
                    SoundSource.AMBIENT, 0.9F, 0.82F);
            serverLevel.sendParticles(ParticleTypes.NOTE,
                    entity.getX(), entity.getY() + 1.5D, entity.getZ(),
                    10, 0.35D, 0.25D, 0.35D, 0.02D);
        }

        // Materialization lasts 50 ticks (2.5 seconds) after the song begins.
        if (cycle >= 0 && cycle < 50) {
            if (entity instanceof Mob mob) {
                mob.getNavigation().stop();
            }

            BlockPos target = entity.blockPosition().relative(entity.getDirection());
            BlockPos floor = target.below();
            boolean validSpot = serverLevel.isEmptyBlock(target)
                    && serverLevel.getBlockState(floor).isFaceSturdy(serverLevel, floor, Direction.UP);

            if (validSpot) {
                double progress = cycle / 49.0D;
                double radius = 0.92D * (1.0D - progress) + 0.035D;

                // Seven soul motes spiral inward and rise toward the future block.
                for (int mote = 0; mote < 7; mote++) {
                    double angle = cycle * 0.29D + mote * (Math.PI * 2.0D / 7.0D);
                    double px = target.getX() + 0.5D + Math.cos(angle) * radius;
                    double py = target.getY() + 0.06D + progress * 0.78D + (mote % 3) * 0.055D;
                    double pz = target.getZ() + 0.5D + Math.sin(angle) * radius;

                    serverLevel.sendParticles(ParticleTypes.SOUL, px, py, pz,
                            1, 0.0D, 0.012D, 0.0D, 0.0D);
                    if ((cycle + mote) % 5 == 0) {
                        serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz,
                                1, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
                }

                if (cycle == 49) {
                    // Use MCreator's registered block holder; do not look it up as an Optional registry value.
                    serverLevel.setBlock(target, SingerdemoModBlocks.SOUL_BLOCK.get().defaultBlockState(), 3);
                    serverLevel.sendParticles(ParticleTypes.SOUL,
                            target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D,
                            36, 0.32D, 0.38D, 0.32D, 0.045D);
                    serverLevel.playSound(null, target, SoundEvents.AMETHYST_BLOCK_CHIME,
                            SoundSource.BLOCKS, 0.9F, 1.25F);
                }
            }
        }
    }
}
