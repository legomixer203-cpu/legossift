package net.mcreator.singerdemo.init;

import net.mcreator.singerdemo.SingerdemoMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Sound events used by the Singer's randomized song playlist. */
public final class SingerdemoModSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(Registries.SOUND_EVENT, SingerdemoMod.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SINGER_SONG_1 = register("singer_song_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> SINGER_SONG_2 = register("singer_song_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> SINGER_SONG_3 = register("singer_song_3");
    public static final DeferredHolder<SoundEvent, SoundEvent> SINGER_SONG_4 = register("singer_song_4");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(SingerdemoMod.MODID, name);
        return REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    private SingerdemoModSounds() {
    }
}
