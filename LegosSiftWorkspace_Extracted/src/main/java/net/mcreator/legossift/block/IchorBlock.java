package net.mcreator.legossift.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;

import net.mcreator.legossift.procedures.IchorParticlesProcedure;
import net.mcreator.legossift.procedures.IchorDamageProcedure;
import net.mcreator.legossift.init.LegosSiftModFluids;

public class IchorBlock extends LiquidBlock {
	public IchorBlock(BlockBehaviour.Properties properties) {
		super(LegosSiftModFluids.ICHOR.get(), properties.mapColor(MapColor.WATER).strength(100f).noCollision().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
	}

	@Override
	public void entityInside(BlockState blockstate, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, boolean isPrecise) {
		super.entityInside(blockstate, world, pos, entity, insideBlockEffectApplier, isPrecise);
		IchorDamageProcedure.execute(world, entity);
	}

	@Override
	public void animateTick(BlockState blockstate, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(blockstate, world, pos, random);
		IchorParticlesProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}