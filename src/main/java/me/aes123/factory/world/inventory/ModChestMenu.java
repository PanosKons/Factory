package me.aes123.factory.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

public class ModChestMenu {
    public static ChestMenu fourRows(int p_39238_, Inventory p_39239_, Container p_39240_) {
        return new ChestMenu(MenuType.GENERIC_9x4, p_39238_, p_39239_, p_39240_, 4);
    }
    public static ChestMenu fiveRows(int p_39238_, Inventory p_39239_, Container p_39240_) {
        return new ChestMenu(MenuType.GENERIC_9x5, p_39238_, p_39239_, p_39240_, 5);
    }
    public static ChestMenu sixRows(int p_39238_, Inventory p_39239_, Container p_39240_) {
        return new ChestMenu(MenuType.GENERIC_9x6, p_39238_, p_39239_, p_39240_, 6);
    }
}
