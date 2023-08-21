package me.aes123.factory.screen;

import me.aes123.factory.Main;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DiscoveryStationScreen extends AbstractContainerScreen<DiscoveryStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/discovery_station_gui.png");
    public DiscoveryStationScreen(DiscoveryStationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init()
    {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0,0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }
    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 80, y + 35, 176, 14, menu.getScaledProgress(), 17);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY,delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}