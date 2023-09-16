package me.aes123.factory.screen;

import me.aes123.factory.Main;
import me.aes123.factory.screen.renderer.EnergyInfoArea;
import me.aes123.factory.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class ElectricFurnaceScreen extends AbstractContainerScreen<ElectricFurnaceMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/electric_furnace_gui.png");
    private EnergyInfoArea energyInfoArea;
    public ElectricFurnaceScreen(ElectricFurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init()
    {
        super.init();
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 147, y + 7, 18, 72);
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderEnergyAreaToolTips(gui, mouseX, mouseY, x,y);
        super.renderLabels(gui, mouseX, mouseY);
    }

    private void renderEnergyAreaToolTips(GuiGraphics gui, int mouseX, int mouseY, int x, int y) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, 147, 7, 18, 72)) {
            gui.renderTooltip(this.font,energyInfoArea.getTooltips(menu.getEnergy(), menu.getMaxEnergy()), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0,0, imageWidth, imageHeight);
        energyInfoArea.draw(guiGraphics, menu.getEnergy(), menu.getMaxEnergy());
        renderProgressArrow(guiGraphics, x, y);
        renderProgressLit(guiGraphics, x, y);
    }
    private void renderProgressLit(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isSmelting()) {
            guiGraphics.blit(TEXTURE, x + 56, y + 36 + 12 - menu.getLitScaledProgress(), 176,12 - menu.getLitScaledProgress(),14,menu.getLitScaledProgress() + 1);
        }
    }
    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 80, y + 35, 176, 14, menu.getArrowScaledProgress(), 17);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY,delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}