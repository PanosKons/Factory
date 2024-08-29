package me.aes123.factory.enchantment;

import me.aes123.factory.init.ModMobEffects;
import me.aes123.factory.item.equipment.ModSword;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class LeachEnchantment extends Enchantment {
    public LeachEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] slot) {
        super(rarity, enchantmentCategory, slot);
    }
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void doPostAttack(LivingEntity livingEntity, Entity entity, int level) {
        super.doPostAttack(livingEntity, entity, level);
        if(entity instanceof LivingEntity target)
        {
            livingEntity.heal(0.2f * level);
        }
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ModSword;
    }
}
