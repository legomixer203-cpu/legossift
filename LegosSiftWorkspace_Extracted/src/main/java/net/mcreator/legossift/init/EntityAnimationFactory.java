package net.mcreator.legossift.init;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.legossift.entity.SproutEntity;
import net.mcreator.legossift.entity.SlabberEntity;
import net.mcreator.legossift.entity.SeedlingEntity;
import net.mcreator.legossift.entity.NuzzleEntity;
import net.mcreator.legossift.entity.BlubEntity;

@EventBusSubscriber(value = Dist.CLIENT)
public class EntityAnimationFactory {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		if (event == null || event.getEntity() == null)
			return;
		if (event.getEntity() instanceof BlubEntity syncable) {
			String animation = syncable.getSyncedAnimation();
			if (!animation.equals("undefined")) {
				syncable.getEntityData().set(BlubEntity.ANIMATION, "undefined");
				syncable.animationprocedure = animation;
			}
			syncable.applySyncedControllerAnimations();
		}
		if (event.getEntity() instanceof SlabberEntity syncable) {
			String animation = syncable.getSyncedAnimation();
			if (!animation.equals("undefined")) {
				syncable.getEntityData().set(SlabberEntity.ANIMATION, "undefined");
				syncable.animationprocedure = animation;
			}
			syncable.applySyncedControllerAnimations();
		}
		if (event.getEntity() instanceof NuzzleEntity syncable) {
			String animation = syncable.getSyncedAnimation();
			if (!animation.equals("undefined")) {
				syncable.getEntityData().set(NuzzleEntity.ANIMATION, "undefined");
				syncable.animationprocedure = animation;
			}
			syncable.applySyncedControllerAnimations();
		}
		if (event.getEntity() instanceof SeedlingEntity syncable) {
			String animation = syncable.getSyncedAnimation();
			if (!animation.equals("undefined")) {
				syncable.getEntityData().set(SeedlingEntity.ANIMATION, "undefined");
				syncable.animationprocedure = animation;
			}
			syncable.applySyncedControllerAnimations();
		}
		if (event.getEntity() instanceof SproutEntity syncable) {
			String animation = syncable.getSyncedAnimation();
			if (!animation.equals("undefined")) {
				syncable.getEntityData().set(SproutEntity.ANIMATION, "undefined");
				syncable.animationprocedure = animation;
			}
			syncable.applySyncedControllerAnimations();
		}
	}
}