package net.mcreator.singerdemo.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class SoulBlockBlock extends Block {
	public SoulBlockBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1.5f, 3f).lightLevel(blockstate -> 5).postProcess((bs, br, bp) -> bp).emissiveRendering((bs, br, bp) -> true));
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 15;
	}
}