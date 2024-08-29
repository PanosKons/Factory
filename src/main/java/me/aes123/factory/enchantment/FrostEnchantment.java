package me.aes123.factory.enchantment;

import me.aes123.factory.init.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FrostEnchantment extends Enchantment {
    public FrostEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] slot) {
        super(rarity, enchantmentCategory, slot);
    }
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public void doPostAttack(LivingEntity livingEntity, Entity entity, int level) {
        super.doPostAttack(livingEntity, entity, level);
        if(entity instanceof LivingEntity target)
        {
            target.addEffect(new MobEffectInstance(ModMobEffects.FREEZE.get(), 3 * 20, level - 1));
        }
    }
}
