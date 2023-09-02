package me.aes123.factory.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_FACTORY = "key.category.factory.dungeon";
    public static final String KEY_OPEN_SHOP = "key.factory.open_shop";

    public static final KeyMapping SHOP_KEY = new KeyMapping(KEY_OPEN_SHOP, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY_FACTORY);
}