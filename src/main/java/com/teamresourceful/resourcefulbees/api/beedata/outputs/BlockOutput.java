package com.teamresourceful.resourcefulbees.api.beedata.outputs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Optional;

@Unmodifiable
public class BlockOutput extends AbstractOutput {

    public static final Codec<BlockOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.BLOCK.byNameCodec().fieldOf("block").orElse(Blocks.AIR).forGetter(BlockOutput::getBlock),
            CompoundTag.CODEC.optionalFieldOf("tag").forGetter(BlockOutput::getCompoundNBT),
            Codec.doubleRange(1.0d, Double.MAX_VALUE).fieldOf("weight").orElse(1.0d).forGetter(BlockOutput::getWeight),
            Codec.doubleRange(0.0d, 1.0).fieldOf("chance").orElse(1.0d).forGetter(BlockOutput::getChance)
    ).apply(instance, BlockOutput::new));

    private final Block block;
    private final Optional<CompoundTag> compoundNBT;

    public BlockOutput(Block block, Optional<CompoundTag> compoundNBT, double weight, double chance) {
        super(weight, chance);
        this.block = block;
        this.compoundNBT = compoundNBT;
    }

    public Optional<CompoundTag> getCompoundNBT() {
        return compoundNBT;
    }

    public Block getBlock() {
        return block;
    }
}
