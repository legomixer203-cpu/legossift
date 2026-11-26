package net.mcreator.singerdemo.entity;

import net.mcreator.singerdemo.init.SingerdemoModEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

/** Registers the vanilla attributes required for the Singer mob. */
@EventBusSubscriber(modid = "singerdemo", bus = EventBusSubscriber.Bus.MOD)
public final class SingerAttributeRegistration {
    private SingerAttributeRegistration() {
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SingerdemoModEntities.SINGER.get(), SingerEntity.createAttributes().build());
    }
}
