package me.aes123.factory.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class XPHudOverlay {
    public static final IGuiOverlay HUD_XP = ((gui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;
        int y = height / 2;

        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 1.0F);
        String text = "Hello";
        //guiGraphics.drawString(Minecraft.getInstance().font, text, x , y, 16777215);
    });

}
