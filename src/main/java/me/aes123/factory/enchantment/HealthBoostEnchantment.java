package me.aes123.factory.enchantment;

import me.aes123.factory.init.ModItems;
import me.aes123.factory.item.equipment.ModArmor;
import me.aes123.factory.item.equipment.ModSword;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class HealthBoostEnchantment extends Enchantment {
    public HealthBoostEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] slot) {
        super(rarity, enchantmentCategory, slot);
    }
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.is(ModItems.IRON_CHESTPLATE.get()) || stack.is(ModItems.GOLD_CHESTPLATE.get()) || stack.is(ModItems.DIAMOND_CHESTPLATE.get()) || stack.is(ModItems.NETHERITE_CHESTPLATE.get());
    }
}
