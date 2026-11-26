/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.singerdemo.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.mcreator.singerdemo.block.SoulBlockBlock;
import net.mcreator.singerdemo.SingerdemoMod;

import java.util.function.Function;

public class SingerdemoModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(SingerdemoMod.MODID);
	public static final DeferredBlock<Block> SOUL_BLOCK;
	static {
		SOUL_BLOCK = register("soul_block", SoulBlockBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}