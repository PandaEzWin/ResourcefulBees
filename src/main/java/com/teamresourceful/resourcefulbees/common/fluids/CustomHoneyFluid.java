package com.teamresourceful.resourcefulbees.common.fluids;

import com.teamresourceful.resourcefulbees.api.honeydata.HoneyFluidData;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateContainer;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

public abstract class CustomHoneyFluid extends ForgeFlowingFluid {

    private final HoneyFluidData data;

    protected CustomHoneyFluid(Properties properties, HoneyFluidData data) {
        super(properties);
        this.data = data;
    }

    public HoneyFluidData getHoneyData() {
        return data;
    }

    public static class Flowing extends CustomHoneyFluid {

        public Flowing(Properties properties, HoneyFluidData data) {
            super(properties, data);
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        @Override
        protected void createFluidStateDefinition(StateContainer.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(@NotNull FluidState state) {
            return false;
        }
    }

    public static class Source extends CustomHoneyFluid {

        public Source(Properties properties, HoneyFluidData honeyData) {
            super(properties, honeyData);
        }

        public int getAmount(@NotNull FluidState state) {
            return 8;
        }

        public boolean isSource(@NotNull FluidState state) {
            return true;
        }
    }
}
