package me.aes123.factory.enchantment;

import me.aes123.factory.init.ModItems;
import me.aes123.factory.item.equipment.ModGun;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TeleportEnchantment extends Enchantment {
    public TeleportEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] slot) {
        super(rarity, enchantmentCategory, slot);
    }
    public int getMaxLevel() {
        return 20;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ModGun;
    }
}
