package net.mcreator.legossift.fluid;

import org.apache.logging.log4j.core.util.Source;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.legossift.init.LegosSiftModItems;
import net.mcreator.legossift.init.LegosSiftModFluids;
import net.mcreator.legossift.init.LegosSiftModFluidTypes;
import net.mcreator.legossift.init.LegosSiftModBlocks;

public abstract class IchorFluid extends BaseFlowingFluid {
	public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(() -> LegosSiftModFluidTypes.ICHOR_TYPE.get(), () -> LegosSiftModFluids.ICHOR.get(), () -> LegosSiftModFluids.FLOWING_ICHOR.get())
			.explosionResistance(100f).bucket(() -> LegosSiftModItems.ICHOR_BUCKET.get()).block(() -> (LiquidBlock) LegosSiftModBlocks.ICHOR.get());

	private IchorFluid() {
		super(PROPERTIES);
	}

	@Override
	public ParticleOptions getDripParticle() {
		return ParticleTypes.SOUL;
	}

	public static class Source extends IchorFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends IchorFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}