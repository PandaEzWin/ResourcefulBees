package com.teamresourceful.resourcefulbees.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ToggleItemImageButton extends ToggleImageButton {


    private final int itemX;
    private final int itemY;
    private final ItemStack item;

    public ToggleItemImageButton(int xIn, int yIn, int xOffset, boolean enabled, ButtonTemplate template, IPressable pressable, Component tooltip, int itemX, int itemY, ItemStack item) {
        super(xIn, yIn, xOffset, enabled, template, pressable, tooltip);
        this.itemX = itemX;
        this.itemY = itemY;
        this.item = item;
    }

    @Override
    public void renderButton(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
        if (this.item != null)
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(this.item, this.x + this.itemX, this.y + this.itemY);
        RenderSystem.enableDepthTest();
    }
}
