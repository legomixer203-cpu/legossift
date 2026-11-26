package net.mcreator.legossift.init;

import net.neoforged.neoforge.transfer.fluid.BucketResourceHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.legossift.item.IchorItem;
import net.mcreator.legossift.LegosSiftMod;

import java.util.function.Function;

@EventBusSubscriber
public class LegosSiftModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(LegosSiftMod.MODID);
	public static final DeferredItem<Item> BLUB_SPAWN_EGG;
	public static final DeferredItem<Item> DARK_SIFT_GRASS_BLOCK;
	public static final DeferredItem<Item> LIGHT_SIFT_GRASS_BLOCK;
	public static final DeferredItem<Item> WISP_LEAVES;
	public static final DeferredItem<Item> WISP_LOGS;
	public static final DeferredItem<Item> WISP_PLANKS;
	public static final DeferredItem<Item> SIFT_STONE;
	public static final DeferredItem<Item> SLABBER_SPAWN_EGG;
	public static final DeferredItem<Item> SIFT_DIRT;
	public static final DeferredItem<Item> NUZZLE_SPAWN_EGG;
	public static final DeferredItem<Item> TEAL_SIFT_GRASS_BLOCK;
	public static final DeferredItem<Item> PINK_AND_TEAL_GRASS_BLOCK;
	public static final DeferredItem<Item> SEEDLING_SPAWN_EGG;
	public static final DeferredItem<Item> SPROUT_SPAWN_EGG;
	public static final DeferredItem<Item> ICHOR_BUCKET;
	public static final DeferredItem<Item> SIFT_SAND;
	public static final DeferredItem<Item> SAND_COVERED_SIFT_STONE;
	static {
		BLUB_SPAWN_EGG = register("blub_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(LegosSiftModEntities.BLUB.get())));
		DARK_SIFT_GRASS_BLOCK = block(LegosSiftModBlocks.DARK_SIFT_GRASS_BLOCK);
		LIGHT_SIFT_GRASS_BLOCK = block(LegosSiftModBlocks.LIGHT_SIFT_GRASS_BLOCK);
		WISP_LEAVES = block(LegosSiftModBlocks.WISP_LEAVES);
		WISP_LOGS = block(LegosSiftModBlocks.WISP_LOGS);
		WISP_PLANKS = block(LegosSiftModBlocks.WISP_PLANKS);
		SIFT_STONE = block(LegosSiftModBlocks.SIFT_STONE);
		SLABBER_SPAWN_EGG = register("slabber_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(LegosSiftModEntities.SLABBER.get())));
		SIFT_DIRT = block(LegosSiftModBlocks.SIFT_DIRT);
		NUZZLE_SPAWN_EGG = register("nuzzle_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(LegosSiftModEntities.NUZZLE.get())));
		TEAL_SIFT_GRASS_BLOCK = block(LegosSiftModBlocks.TEAL_SIFT_GRASS_BLOCK);
		PINK_AND_TEAL_GRASS_BLOCK = block(LegosSiftModBlocks.PINK_AND_TEAL_GRASS_BLOCK);
		SEEDLING_SPAWN_EGG = register("seedling_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(LegosSiftModEntities.SEEDLING.get())));
		SPROUT_SPAWN_EGG = register("sprout_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(LegosSiftModEntities.SPROUT.get())));
		ICHOR_BUCKET = register("ichor_bucket", IchorItem::new);
		SIFT_SAND = block(LegosSiftModBlocks.SIFT_SAND);
		SAND_COVERED_SIFT_STONE = block(LegosSiftModBlocks.SAND_COVERED_SIFT_STONE);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, Item.Properties::new);
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), () -> properties);
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerItem(Capabilities.Fluid.ITEM, (stack, access) -> new BucketResourceHandler(access), ICHOR_BUCKET.get());
	}
}