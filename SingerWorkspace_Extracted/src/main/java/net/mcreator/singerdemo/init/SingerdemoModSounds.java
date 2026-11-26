/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.singerdemo.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.mcreator.singerdemo.SingerdemoMod;

public class SingerdemoModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, SingerdemoMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> SINGERIDLE = REGISTRY.register("singeridle", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("singerdemo", "singeridle")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SINGERHURT = REGISTRY.register("singerhurt", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("singerdemo", "singerhurt")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SINGERSING = REGISTRY.register("singersing", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("singerdemo", "singersing")));
}