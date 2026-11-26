package net.mcreator.singerdemo.procedures;

public class SingerTickProcedure {
	public static void execute() {
		if (entity != null && world instanceof net.minecraft.server.level.ServerLevel serverLevel && entity instanceof net.minecraft.world.entity.PathfinderMob singer) {
			int cycle = entity.tickCount % 600;
			if (cycle == 0) {
				singer.getNavigation().stop();
				serverLevel.playSound(null, entity.blockPosition(), net.minecraft.sounds.SoundEvents.AMETHYST_BLOCK_CHIME, net.minecraft.sounds.SoundSource.AMBIENT, 0.9F, 0.82F);
				serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.NOTE, entity.getX(), entity.getY() + 1.5D, entity.getZ(), 10, 0.35D, 0.25D, 0.35D, 0.02D);
			}
			if (cycle < 50) {
				singer.getNavigation().stop();
				net.minecraft.core.BlockPos target = entity.blockPosition().relative(entity.getDirection());
				net.minecraft.core.BlockPos floor = target.below();
				boolean validSpot = serverLevel.isEmptyBlock(target) && serverLevel.getBlockState(floor).isFaceSturdy(serverLevel, floor, net.minecraft.core.Direction.UP);
				if (validSpot) {
					double progress = cycle / 49.0D;
					double radius = 0.92D * (1.0D - progress) + 0.035D;
					for (int mote = 0; mote < 7; mote++) {
						double angle = (cycle * 0.29D) + (mote * (Math.PI * 2.0D / 7.0D));
						double px = target.getX() + 0.5D + Math.cos(angle) * radius;
						double py = target.getY() + 0.06D + progress * 0.78D + (mote % 3) * 0.055D;
						double pz = target.getZ() + 0.5D + Math.sin(angle) * radius;
						serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL, px, py, pz, 1, 0.0D, 0.012D, 0.0D, 0.0D);
						if ((cycle + mote) % 5 == 0) {
							serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
						}
					}
					if (cycle == 49) {
						net.minecraft.world.level.block.Block soulBlock = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(net.minecraft.resources.Identifier.parse("singerdemo:soul_block"));
						if (soulBlock != net.minecraft.world.level.block.Blocks.AIR) {
							serverLevel.setBlock(target, soulBlock.defaultBlockState(), 3);
							serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL, target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D, 36, 0.32D, 0.38D, 0.32D, 0.045D);
							serverLevel.playSound(null, target, net.minecraft.sounds.SoundEvents.AMETHYST_BLOCK_CHIME, net.minecraft.sounds.SoundSource.BLOCKS, 0.9F, 1.25F);
						}
					}
				}
			}
		}
	}
}