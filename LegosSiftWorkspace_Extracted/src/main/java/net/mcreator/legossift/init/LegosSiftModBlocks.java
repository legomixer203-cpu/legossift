/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.legossift.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.mcreator.legossift.block.*;
import net.mcreator.legossift.LegosSiftMod;

import java.util.function.Function;

public class LegosSiftModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(LegosSiftMod.MODID);
	public static final DeferredBlock<Block> DARK_SIFT_GRASS_BLOCK;
	public static final DeferredBlock<Block> LIGHT_SIFT_GRASS_BLOCK;
	public static final DeferredBlock<Block> WISP_LEAVES;
	public static final DeferredBlock<Block> WISP_LOGS;
	public static final DeferredBlock<Block> WISP_PLANKS;
	public static final DeferredBlock<Block> SIFT_STONE;
	public static final DeferredBlock<Block> SIFT_DIRT;
	public static final DeferredBlock<Block> TEAL_SIFT_GRASS_BLOCK;
	public static final DeferredBlock<Block> PINK_AND_TEAL_GRASS_BLOCK;
	public static final DeferredBlock<Block> ICHOR;
	public static final DeferredBlock<Block> SIFT_SAND;
	public static final DeferredBlock<Block> SAND_COVERED_SIFT_STONE;
	static {
		DARK_SIFT_GRASS_BLOCK = register("dark_sift_grass_block", DarkSiftGrassBlockBlock::new);
		LIGHT_SIFT_GRASS_BLOCK = register("light_sift_grass_block", LightSiftGrassBlockBlock::new);
		WISP_LEAVES = register("wisp_leaves", WispLeavesBlock::new);
		WISP_LOGS = register("wisp_logs", WispLogsBlock::new);
		WISP_PLANKS = register("wisp_planks", WispPlanksBlock::new);
		SIFT_STONE = register("sift_stone", SiftStoneBlock::new);
		SIFT_DIRT = register("sift_dirt", SiftDirtBlock::new);
		TEAL_SIFT_GRASS_BLOCK = register("teal_sift_grass_block", TealSiftGrassBlockBlock::new);
		PINK_AND_TEAL_GRASS_BLOCK = register("pink_and_teal_grass_block", PinkAndTealGrassBlockBlock::new);
		ICHOR = register("ichor", IchorBlock::new);
		SIFT_SAND = register("sift_sand", SiftSandBlock::new);
		SAND_COVERED_SIFT_STONE = register("sand_covered_sift_stone", SandCoveredSiftStoneBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}