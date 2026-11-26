package net.mcreator.legossift.client;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.legossift.init.LegosSiftModEntities;
import net.mcreator.legossift.client.renderer.SproutRenderer;
import net.mcreator.legossift.client.renderer.SlabberRenderer;
import net.mcreator.legossift.client.renderer.SeedlingRenderer;
import net.mcreator.legossift.client.renderer.NuzzleRenderer;
import net.mcreator.legossift.client.renderer.BlubRenderer;
import net.mcreator.legossift.client.renderer.SingerRenderer;

@EventBusSubscriber(Dist.CLIENT)
public class AnimatedEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(LegosSiftModEntities.BLUB.get(), BlubRenderer::new);
		event.registerEntityRenderer(LegosSiftModEntities.SLABBER.get(), SlabberRenderer::new);
		event.registerEntityRenderer(LegosSiftModEntities.NUZZLE.get(), NuzzleRenderer::new);
		event.registerEntityRenderer(LegosSiftModEntities.SEEDLING.get(), SeedlingRenderer::new);
		event.registerEntityRenderer(LegosSiftModEntities.SPROUT.get(), SproutRenderer::new);
		event.registerEntityRenderer(LegosSiftModEntities.SINGER.get(), SingerRenderer::new);
	}
}