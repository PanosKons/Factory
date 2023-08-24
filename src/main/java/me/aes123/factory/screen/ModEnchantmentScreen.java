package me.aes123.factory.screen;

import me.aes123.factory.Main;
import me.aes123.factory.screen.renderer.EnergyInfoArea;
import me.aes123.factory.screen.renderer.XPInfoArea;
import me.aes123.factory.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class ModEnchantmentScreen extends AbstractContainerScreen<ModEnchantmentMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/enchanting_table_gui.png");
    private XPInfoArea xpInfoArea;
    public ModEnchantmentScreen(ModEnchantmentMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init()
    {
        super.init();
        assignXpInfoArea();
    }

    private void assignXpInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        xpInfoArea = new XPInfoArea(x + 60, y + 21, 108, 50);
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderEnergyAreaToolTips(gui, mouseX, mouseY, x,y);
        super.renderLabels(gui, mouseX, mouseY);
    }

    private void renderEnergyAreaToolTips(GuiGraphics gui, int mouseX, int mouseY, int x, int y) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, 60, 21, 108, 50)) {
            gui.renderTooltip(this.font,xpInfoArea.getTooltips(menu.getXp(), menu.getMaxXp()), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0,0, imageWidth, imageHeight);
        xpInfoArea.draw(guiGraphics, menu.getXp(), menu.getMaxXp());
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