package net.mcreator.singerdemo.init;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.singerdemo.entity.SingerEntity;

@EventBusSubscriber(value = Dist.CLIENT)
public class EntityAnimationFactory {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		if (event == null || event.getEntity() == null)
			return;
		if (event.getEntity() instanceof SingerEntity syncable) {
			String animation = syncable.getSyncedAnimation();
			if (!animation.equals("undefined")) {
				syncable.getEntityData().set(SingerEntity.ANIMATION, "undefined");
				syncable.animationprocedure = animation;
			}
			syncable.applySyncedControllerAnimations();
		}
	}
}