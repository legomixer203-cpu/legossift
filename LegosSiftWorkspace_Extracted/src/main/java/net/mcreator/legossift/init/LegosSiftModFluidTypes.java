/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.legossift.init;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.fluids.FluidType;

import net.mcreator.legossift.fluid.types.IchorFluidType;
import net.mcreator.legossift.LegosSiftMod;

public class LegosSiftModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, LegosSiftMod.MODID);
	public static final DeferredHolder<FluidType, FluidType> ICHOR_TYPE = REGISTRY.register("ichor", IchorFluidType::new);
}