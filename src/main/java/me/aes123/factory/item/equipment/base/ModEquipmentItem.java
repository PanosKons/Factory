package me.aes123.factory.item.equipment.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.*;

import static me.aes123.factory.data.EquipmentModifier.EquipmentModifierType.*;

public class ModEquipmentItem extends Item implements Vanishable, IEquipmentItem {

    public ModEquipmentItem(Properties properties) {
        super(properties.defaultDurability(1000));
    }
    @Override
    public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        if(enchantment == Enchantments.SILK_TOUCH && getModifierValue(SILK_TOUCH, stack) > 0) return 1;
        return 0;
    }

    @Override
    public Map<Enchantment, Integer> getAllEnchantments(ItemStack stack) {
        Map<Enchantment, Integer> map = new HashMap<>();
        if(getModifierValue(SILK_TOUCH, stack) > 0) map.put(Enchantments.SILK_TOUCH, 1);
        return map;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        tick(stack);
        super.inventoryTick(stack, p_41405_, p_41406_, p_41407_, p_41408_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        appendText(stack,components);
    }
}
