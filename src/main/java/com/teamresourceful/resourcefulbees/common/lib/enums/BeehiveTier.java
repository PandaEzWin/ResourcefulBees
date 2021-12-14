package com.teamresourceful.resourcefulbees.common.lib.enums;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefulbees.common.registry.minecraft.ModItems;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum BeehiveTier implements IExtensibleEnum, StringRepresentable {
    NEST("nest", 2, 4, 2.0, ModItems.T0_NEST_ITEMS), //nest
    T1_HIVE("t1_hive", 4, 8, 1.3, ModItems.T1_NEST_ITEMS), //normal
    T2_HIVE("t2_hive", 6, 16, 1.2, ModItems.T2_NEST_ITEMS), //T2
    T3_HIVE("t3_hive", 8, 32, 1.0, ModItems.T3_NEST_ITEMS); //T3

    public static final Codec<BeehiveTier> CODEC = IExtensibleEnum.createCodecForExtensibleEnum(BeehiveTier::values, BeehiveTier::byName);
    private static final Map<String, BeehiveTier> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(BeehiveTier::getName, tier -> tier));
    private final String name;
    private final int maxBees;
    private final int maxCombs;
    private final double timeModifier;
    private final DeferredRegister<Item> displayItems;

    BeehiveTier(String name, int maxBees, int maxCombs, double timeModifier, DeferredRegister<Item> displayItems) {
        this.name = name;
        this.maxBees = maxBees;
        this.maxCombs = maxCombs;
        this.timeModifier = timeModifier;
        this.displayItems = displayItems;
    }

    public String getName() {
        return name;
    }

    public int getMaxBees() {
        return maxBees;
    }

    public int getMaxCombs() {
        return maxCombs;
    }

    public double getTimeModifier() {
        return timeModifier;
    }

    public DeferredRegister<Item> getDisplayItems() {
        return displayItems;
    }

    public static BeehiveTier byName(String s) {
        return BY_NAME.get(s);
    }

    @SuppressWarnings("unused")
    public static BeehiveTier create(String name, String id, int maxBees, int maxCombs, double timeModifier, DeferredRegister<Item> displayItems) {
        throw new IllegalStateException("Enum not extended");
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
