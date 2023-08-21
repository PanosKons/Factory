package me.aes123.factory.screen.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class EnergyInfoArea {
    private final Rect2i area;

    public EnergyInfoArea(int xMin, int yMin, int width, int height)  {
        this.area = new Rect2i(xMin, yMin, width, height);
    }

    public List<Component> getTooltips(int energy, int maxEnergy) {
        return List.of(Component.literal(energy+"/"+maxEnergy+" FE"));
    }

    public void draw(GuiGraphics guiGraphics, int energy, int maxEnergy) {
        final int height = area.getHeight();
        int stored = (int)(height*(energy/(float)maxEnergy));
        guiGraphics.fillGradient(
                area.getX(), area.getY()+(height-stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffb51500, 0xff600b00
        );
    }
}
