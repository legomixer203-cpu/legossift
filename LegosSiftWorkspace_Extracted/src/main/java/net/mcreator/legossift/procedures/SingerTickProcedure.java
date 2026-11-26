package net.mcreator.legossift.procedures;

import net.mcreator.legossift.init.LegosSiftModBlocks;
import net.mcreator.legossift.init.SingerSoundEvents;
import net.mcreator.legossift.entity.SingerEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;

public final class SingerTickProcedure {

    private SingerTickProcedure() {
    }

    // ==========================================
    // CUSTOM SINGER SOUND
    // ==========================================

    private static void playSingerVoice(ServerLevel level, Entity entity) {

        // Get the registered custom singing sound.
        var sound = SingerSoundEvents.SINGERSING.get();

        System.out.println("[SINGER] Playing singing sound!");

        level.playSound(
                null,
                entity.blockPosition(),
                sound,
                SoundSource.NEUTRAL,
                1.0F,
                1.0F
        );
    }

    public static void execute() {
    }

    public static void execute(
            LevelAccessor world,
            double x,
            double y,
            double z,
            Entity entity
    ) {

        if (entity == null || !(world instanceof ServerLevel serverLevel)) {
            return;
        }

        // ==========================================
        // SINGING CYCLE
        // ==========================================

        // One complete cycle every 600 ticks.
        int cycle = Math.floorMod(entity.tickCount, 600);

        final int songLength = 180;

        // Play the custom song at the beginning.
        if (cycle == 1) {
            playSingerVoice(serverLevel, entity);
        }

        // ==========================================
        // SINGING ANIMATION
        // ==========================================

        if (entity instanceof SingerEntity singer) {

            if (cycle < songLength) {

                singer.setControllerAnimation(
                        "singing",
                        "animation.singer.sing"
                );

            } else if (!"empty".equals(
                    singer.getControllerAnimation("singing")
            )) {

                singer.setControllerAnimation(
                        "singing",
                        "empty"
                );
            }
        }

        // ==========================================
        // SINGING BEHAVIOR
        // ==========================================

        if (cycle < songLength) {

            // Stop walking while singing.
            if (entity instanceof Mob mob) {
                mob.getNavigation().stop();
            }

            // Note particles.
            if (cycle % 18 == 0) {

                serverLevel.sendParticles(
                        ParticleTypes.NOTE,
                        entity.getX(),
                        entity.getY() + 1.55D,
                        entity.getZ(),
                        6,
                        0.42D,
                        0.24D,
                        0.42D,
                        0.015D
                );
            }

            // Soul particles.
            if (cycle % 5 == 0) {

                serverLevel.sendParticles(
                        ParticleTypes.SOUL,
                        entity.getX(),
                        entity.getY() + 1.0D,
                        entity.getZ(),
                        2,
                        0.38D,
                        0.42D,
                        0.38D,
                        0.012D
                );
            }
        }

        // ==========================================
        // SOUL BLOCK MATERIALIZATION
        // ==========================================

        final int materializeStart = 130;
        final int materializeEnd = 180;

        if (cycle >= materializeStart && cycle < materializeEnd) {

            BlockPos target = entity.blockPosition()
                    .relative(entity.getDirection());

            BlockPos floor = target.below();

            boolean replaceable = serverLevel
                    .getBlockState(target)
                    .canBeReplaced();

            boolean solidGround = serverLevel
                    .getBlockState(floor)
                    .isFaceSturdy(
                            serverLevel,
                            floor,
                            Direction.UP
                    );

            if (replaceable && solidGround) {

                double progress =
                        (cycle - materializeStart) / 49.0D;

                double radius =
                        0.92D * (1.0D - progress) + 0.035D;

                // Nine orbiting soul motes.
                for (int mote = 0; mote < 9; mote++) {

                    double angle = cycle * 0.24D
                            + mote * (Math.PI * 2.0D / 9.0D);

                    double px = target.getX() + 0.5D
                            + Math.cos(angle) * radius;

                    double py = target.getY() + 0.06D
                            + progress * 0.82D
                            + (mote % 3) * 0.045D;

                    double pz = target.getZ() + 0.5D
                            + Math.sin(angle) * radius;

                    serverLevel.sendParticles(
                            ParticleTypes.SOUL,
                            px,
                            py,
                            pz,
                            1,
                            0.0D,
                            0.012D,
                            0.0D,
                            0.0D
                    );

                    if ((cycle + mote) % 4 == 0) {

                        serverLevel.sendParticles(
                                ParticleTypes.SOUL_FIRE_FLAME,
                                px,
                                py,
                                pz,
                                1,
                                0.0D,
                                0.0D,
                                0.0D,
                                0.0D
                        );
                    }
                }

                // ==========================================
                // PLACE SOUL BLOCK
                // ==========================================

                if (cycle == materializeEnd - 1) {

                    if (serverLevel.getBlockState(target).canBeReplaced()) {

                        serverLevel.setBlock(
                                target,
                                LegosSiftModBlocks.SOUL_BLOCK.get()
                                        .defaultBlockState(),
                                3
                        );

                        // Soul particle burst.
                        serverLevel.sendParticles(
                                ParticleTypes.SOUL,
                                target.getX() + 0.5D,
                                target.getY() + 0.5D,
                                target.getZ() + 0.5D,
                                42,
                                0.34D,
                                0.40D,
                                0.34D,
                                0.045D
                        );

                        // Materialization sound.
                        serverLevel.playSound(
                                null,
                                target,
                                SoundEvents.AMETHYST_BLOCK_CHIME,
                                SoundSource.BLOCKS,
                                0.9F,
                                1.32F
                        );
                    }
                }
            }
        }
    }
}