package com.teamresourceful.resourcefulbees.common.item;

import com.teamresourceful.resourcefulbees.common.lib.constants.BeeConstants;
import com.teamresourceful.resourcefulbees.common.lib.constants.NBTConstants;
import com.teamresourceful.resourcefulbees.common.lib.constants.TranslationConstants;
import com.teamresourceful.resourcefulbees.common.utils.BeeInfoUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BeeBox extends Item {

    final boolean isTemp;

    public BeeBox(Properties properties, boolean isTemp) {
        super(properties);
        this.isTemp = isTemp;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            Level worldIn = context.getLevel();
            ItemStack stack = context.getItemInHand();
            if (!context.getPlayer().isShiftKeyDown()) return InteractionResult.FAIL;
            if (worldIn.isClientSide() || !isFilled(stack)) return InteractionResult.FAIL;
            BlockPos pos = context.getClickedPos();
            List<Entity> entities = getEntitiesFromStack(stack, worldIn, true);
            for (Entity entity : entities) {
                if (entity != null) {
                    BlockPos blockPos = pos.relative(context.getClickedFace());
                    entity.absMoveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
                    worldIn.addFreshEntity(entity);
                    if (entity instanceof Bee bee) BeeJar.updateCapturedBee(bee, player);
                }
            }
            if (isTemp) stack.shrink(1);
            else stack.setTag(null);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public @NotNull List<Entity> getEntitiesFromStack(ItemStack stack, Level world, boolean withInfo) {
        CompoundTag tag = stack.getTag();
        List<Entity> entities = new ArrayList<>();
        if (tag != null) {
            ListTag bees = tag.getList(NBTConstants.NBT_BEES, 10);
            bees.stream()
                    .map(CompoundTag.class::cast)
                    .forEach(compoundNBT -> {
                        EntityType<?> type = EntityType.byString(compoundNBT.getCompound(NBTConstants.ENTITY_DATA).getString("id")).orElse(null);
                        if (type != null) {
                            Entity entity = type.create(world);
                            if (entity != null && withInfo) entity.load(compoundNBT.getCompound(NBTConstants.ENTITY_DATA));
                            entities.add(entity);
                        }
                    });
        }
        return entities;
    }

    @NotNull
    @Override
    public InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, LivingEntity targetIn, @NotNull InteractionHand hand) {
        if (targetIn.getCommandSenderWorld().isClientSide() || (!(targetIn instanceof Bee target) || !targetIn.isAlive())) {
            return InteractionResult.FAIL;
        }
        if (isTemp) return InteractionResult.FAIL;

        CompoundTag tag = stack.getOrCreateTag();

        ListTag bees = tag.contains(NBTConstants.NBT_BEES) ? tag.getList(NBTConstants.NBT_BEES, 10) : new ListTag();

        if (bees.size() == BeeConstants.MAX_BEES_BEE_BOX) return InteractionResult.FAIL;
        CompoundTag entityData = new CompoundTag();
        entityData.put(NBTConstants.ENTITY_DATA, BeeInfoUtils.createJarBeeTag(target, NBTConstants.NBT_ID));
        bees.add(entityData);
        tag.put(NBTConstants.NBT_BEES, bees);
        stack.setTag(tag);
        player.setItemInHand(hand, stack);
        player.swing(hand);
        target.discard();
        return InteractionResult.PASS;
    }

    public static boolean isFilled(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTag() && stack.getTag() != null && stack.getTag().contains(NBTConstants.NBT_BEES);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, world, tooltip, tooltipFlag);
        if (isTemp) {
            tooltip.add(TranslationConstants.Items.TEMP_INFO.withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add(TranslationConstants.Items.INFO.withStyle(ChatFormatting.GOLD));
        }
        if (Screen.hasShiftDown() && isFilled(stack)) {
            tooltip.add(TranslationConstants.Items.BEES.withStyle(ChatFormatting.YELLOW));

            //noinspection ConstantConditions
            stack.getTag().getList(NBTConstants.NBT_BEES, 10).stream()
                    .map(CompoundTag.class::cast)
                    .forEach(compoundNBT -> {
                        String id = compoundNBT.getCompound(NBTConstants.ENTITY_DATA).getString("id");
                        EntityType<?> entityType = BeeInfoUtils.getEntityType(id);
                        Component name = entityType == null ? new TextComponent("NULL_ENTITY") : entityType.getDescription();
                        tooltip.add(new TextComponent("  - ").append(name).withStyle(ChatFormatting.WHITE));
                    });
        } else if (isFilled(stack)) {
            tooltip.add(TranslationConstants.Items.MORE_INFO.withStyle(ChatFormatting.YELLOW));
        }
    }
}
