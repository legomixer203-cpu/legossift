/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.legossift.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.legossift.fluid.IchorFluid;
import net.mcreator.legossift.LegosSiftMod;

public class LegosSiftModFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, LegosSiftMod.MODID);
	public static final DeferredHolder<Fluid, FlowingFluid> ICHOR = REGISTRY.register("ichor", IchorFluid.Source::new);
	public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_ICHOR = REGISTRY.register("flowing_ichor", IchorFluid.Flowing::new);
}