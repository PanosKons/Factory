package me.aes123.factory.screen.renderer;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.util.List;

public class XPInfoArea {
    private final Rect2i area;

    public XPInfoArea(int xMin, int yMin, int width, int height)  {
        this.area = new Rect2i(xMin, yMin, width, height);
    }

    public List<Component> getTooltips(int xpstored, int maxxpstored) {
        return List.of(Component.literal(xpstored+"/"+maxxpstored+" XP"));
    }

    public void draw(GuiGraphics guiGraphics, int energy, int maxEnergy) {
        final int height = area.getHeight();
        int stored = (int)(height*(energy/(float)maxEnergy));
        guiGraphics.fillGradient(
                area.getX(), area.getY()+(height-stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffdcf108, 0xff79f108
        );
    }
}
