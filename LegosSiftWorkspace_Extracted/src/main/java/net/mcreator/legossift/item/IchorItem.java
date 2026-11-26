package net.mcreator.legossift.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.legossift.init.LegosSiftModFluids;

public class IchorItem extends BucketItem {
	public IchorItem(Item.Properties properties) {
		super(LegosSiftModFluids.ICHOR.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)

		);
	}
}