package net.mcreator.legossift.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.mcreator.legossift.LegosSiftMod;

/**
 * Singer sound events are registered separately from MCreator's generated
 * LegosSiftModSounds class so they survive MCreator regeneration.
 */
public final class SingerSoundEvents {
    public static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(Registries.SOUND_EVENT, LegosSiftMod.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SINGERIDLE =
            register("singeridle");

    public static final DeferredHolder<SoundEvent, SoundEvent> SINGERHURT =
            register("singerhurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> SINGERSING =
            register("singersing");

    private SingerSoundEvents() {
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(
                Identifier.fromNamespaceAndPath(LegosSiftMod.MODID, name)
        ));
    }
}
