package net.mcreator.legossift.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class SiftStoneBlock extends Block {
	public SiftStoneBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1.5f, 10f).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM));
	}
}