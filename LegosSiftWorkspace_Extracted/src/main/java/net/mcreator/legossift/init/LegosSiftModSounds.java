/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.legossift.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.mcreator.legossift.LegosSiftMod;

public class LegosSiftModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, LegosSiftMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> BLUBIDLE = REGISTRY.register("blubidle", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "blubidle")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SIFTMUSIC = REGISTRY.register("siftmusic", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "siftmusic")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SLABBERSOUNDS = REGISTRY.register("slabbersounds", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "slabbersounds")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SIFTAMBIENT = REGISTRY.register("siftambient", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "siftambient")));
	public static final DeferredHolder<SoundEvent, SoundEvent> NUZZLESOUNDS = REGISTRY.register("nuzzlesounds", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "nuzzlesounds")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SEEDLINGSOUNDS = REGISTRY.register("seedlingsounds", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "seedlingsounds")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SPROUTSOUNDS = REGISTRY.register("sproutsounds", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "sproutsounds")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SINGERIDLE = REGISTRY.register("singeridle", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "singeridle")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SINGERHURT = REGISTRY.register("singerhurt", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "singerhurt")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SINGERSING = REGISTRY.register("singersing", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("legos_sift", "singersing")));
}