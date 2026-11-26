package net.mcreator.singerdemo.client;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.singerdemo.init.SingerdemoModEntities;
import net.mcreator.singerdemo.client.renderer.SingerRenderer;

@EventBusSubscriber(Dist.CLIENT)
public class AnimatedEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(SingerdemoModEntities.SINGER.get(), SingerRenderer::new);
	}
}