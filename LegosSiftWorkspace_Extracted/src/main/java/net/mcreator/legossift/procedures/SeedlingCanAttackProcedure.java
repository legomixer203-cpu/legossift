package net.mcreator.legossift.procedures;

import net.minecraft.world.entity.Entity;

public class SeedlingCanAttackProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.getPersistentData().getDoubleOr("seedling_aggressive", 0) == 1;
	}
}