package com.teamresourceful.resourcefulbees.api.beedata.outputs;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefulbees.api.beedata.CodecUtils;
import com.teamresourceful.resourcefulbees.common.utils.RandomCollection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Unmodifiable;

@Unmodifiable
public class FluidOutput extends AbstractOutput {

    public static final Codec<FluidOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecUtils.FLUID_STACK_CODEC.fieldOf("fluid").orElse(FluidStack.EMPTY).forGetter(FluidOutput::getFluidStack),
            Codec.doubleRange(1.0d, Double.MAX_VALUE).fieldOf("weight").orElse(1.0d).forGetter(FluidOutput::getWeight),
            Codec.doubleRange(0.0d, 1.0d).fieldOf("chance").orElse(1.0d).forGetter(FluidOutput::getChance)
    ).apply(instance, FluidOutput::new));

    public static final Codec<RandomCollection<FluidOutput>> RANDOM_COLLECTION_CODEC = CodecUtils.createSetCodec(FluidOutput.CODEC).comapFlatMap(FluidOutput::convertOutputSetToRandCol, FluidOutput::convertOutputRandColToSet);

    public static final FluidOutput EMPTY = new FluidOutput(FluidStack.EMPTY, 0, 0);

    private final FluidStack fluid;

    public FluidOutput(FluidStack fluid, double weight, double chance) {
        super(weight, chance);
        this.fluid = fluid;
    }

    public CompoundTag getCompoundNBT() {
        return fluid.getTag();
    }

    public FluidStack getFluidStack() {
        return fluid.copy();
    }

    public int getAmount() {
        return fluid.getAmount();
    }

    public Fluid getFluid() {
        return fluid.getFluid();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Fluid", getFluid())
                .add("Amount", getAmount())
                .add("Chance", getChance())
                .add("Weight", getWeight()).toString();
    }
}
