package net.mcreator.legossift.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class IchorParticlesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.SOUL, (x + Mth.nextDouble(RandomSource.create(), 0.1, 0.9)), (y + 0.9), (z + Mth.nextDouble(RandomSource.create(), 0.1, 0.9)), 5, 3, 3, 3, 1);
	}
}