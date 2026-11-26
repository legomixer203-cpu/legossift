package net.mcreator.legossift.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class WispLeavesBlock extends Block {
	public WispLeavesBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.GRASS).strength(1f, 10f).requiresCorrectToolForDrops());
	}
}