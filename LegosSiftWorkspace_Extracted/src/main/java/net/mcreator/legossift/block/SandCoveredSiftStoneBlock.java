package net.mcreator.legossift.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.util.TriState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class SandCoveredSiftStoneBlock extends Block {
	public SandCoveredSiftStoneBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1.5f, 10f).requiresCorrectToolForDrops());
	}

	@Override
	public TriState canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction direction, BlockState plant) {
		return TriState.TRUE;
	}
}