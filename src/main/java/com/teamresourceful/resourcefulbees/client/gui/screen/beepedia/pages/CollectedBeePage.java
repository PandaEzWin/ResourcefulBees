package com.teamresourceful.resourcefulbees.client.gui.screen.beepedia.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teamresourceful.resourcefulbees.client.gui.screen.beepedia.BeepediaPage;
import com.teamresourceful.resourcefulbees.client.gui.widget.BeepediaScreenArea;

public class CollectedBeePage extends BeepediaPage {

    public CollectedBeePage(BeepediaScreenArea screenArea) {
        super(screenArea);
    }

    @Override
    public void renderBackground(MatrixStack matrix, float partialTick, int mouseX, int mouseY) {

    }

    @Override
    public void renderForeground(MatrixStack matrix, int mouseX, int mouseY) {

    }

    @Override
    public void drawTooltips(MatrixStack matrixStack, int mouseX, int mouseY) {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollAmount) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return false;
    }

    @Override
    public void addSearch(BeepediaPage parent) {

    }
}
