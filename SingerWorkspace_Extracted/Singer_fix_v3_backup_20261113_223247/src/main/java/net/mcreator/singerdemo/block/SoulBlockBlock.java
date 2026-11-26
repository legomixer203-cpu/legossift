package net.mcreator.singerdemo.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * The Soul Block used by Singer's materialization effect.
 * NeoForge 26.1.x requires a registry key on block properties.
 */
public class SoulBlockBlock extends Block {
    public SoulBlockBlock(Identifier id) {
        super(BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, id))
                .destroyTime(1.5F)
                .explosionResistance(3.0F)
                .lightLevel(state -> 5));
    }
}
