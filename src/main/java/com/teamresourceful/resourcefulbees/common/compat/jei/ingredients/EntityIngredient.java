package com.teamresourceful.resourcefulbees.common.compat.jei.ingredients;

import com.teamresourceful.resourcefulbees.common.entity.passive.CustomBeeEntity;
import com.teamresourceful.resourcefulbees.common.lib.constants.TranslationConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityIngredient {

    @Nullable
    private final Entity entity;
    private final EntityType<?> entityType;
    private final float rotation;

    public EntityIngredient(EntityType<?> entityType, float rotation){
        this(entityType, rotation, Optional.empty());
    }

    public EntityIngredient(EntityType<?> entityType, float rotation, Optional<CompoundTag> nbt) {
        this.rotation = rotation;
        this.entityType = entityType;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            entity = this.entityType.create(mc.level);
            if (entity != null) nbt.ifPresent(entity::load);
        }else {
            entity = null;
        }
    }

    public float getRotation() {
        return rotation;
    }

    public @Nullable Entity getEntity() {
        return entity;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public Component getDisplayName() {
        return new TranslatableComponent(entityType.getDescriptionId());
    }

    public List<Component> getTooltip() {
        List<Component> tooltip = new ArrayList<>();

        if (entity != null) {
            if (entity instanceof CustomBeeEntity) {
                tooltip.addAll(((CustomBeeEntity) entity).getCoreData().getLore());
                tooltip.add(TranslationConstants.Jei.CLICK_INFO.withStyle(ChatFormatting.GOLD));
            }
            if (Minecraft.getInstance().options.advancedItemTooltips && entityType.getRegistryName() != null) {
                tooltip.add(new TextComponent(entityType.getRegistryName().toString()).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
        return tooltip;
    }

    @Override
    public String toString() {
        return entityType.getRegistryName() != null ? entityType.getRegistryName().toString() : entityType.toString();
    }
}
