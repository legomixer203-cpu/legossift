package net.mcreator.singerdemo.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.singerdemo.SingerdemoMod;

import java.util.function.Function;

public class SingerdemoModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(SingerdemoMod.MODID);
	public static final DeferredItem<Item> SOUL_BLOCK;
	public static final DeferredItem<Item> SINGER_SPAWN_EGG;
	static {
		SOUL_BLOCK = block(SingerdemoModBlocks.SOUL_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));
		SINGER_SPAWN_EGG = register("singer_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(SingerdemoModEntities.SINGER.get())));
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
}