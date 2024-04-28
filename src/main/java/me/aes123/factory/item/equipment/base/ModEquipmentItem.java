package me.aes123.factory.item.equipment.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.aes123.factory.init.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
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
        if(enchantment == Enchantments.SWEEPING_EDGE && getModifierLevel(ATTACK_AOE,stack) > 0) return getModifierLevel(ATTACK_AOE,stack);
        if(enchantment == Enchantments.KNOCKBACK && getModifierLevel(KNOCKBACK, stack) > 0) return getModifierLevel(KNOCKBACK,stack);
        return 0;
    }

    @Override
    public Map<Enchantment, Integer> getAllEnchantments(ItemStack stack) {
        Map<Enchantment, Integer> map = new HashMap<>();
        if(getModifierValue(SILK_TOUCH, stack) > 0) map.put(Enchantments.SILK_TOUCH, 1);
        if(getModifierLevel(ATTACK_AOE,stack) > 0) map.put(Enchantments.SWEEPING_EDGE, getModifierLevel(ATTACK_AOE,stack));
        if(getModifierLevel(KNOCKBACK,stack) > 0) map.put(Enchantments.KNOCKBACK, getModifierLevel(KNOCKBACK,stack));
        return map;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack)
    {
        if(stack.hasTag())
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            if(equipmentSlot.isArmor() && getModifierValue(MOVEMENT_SPEED, stack) > 0) builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_PLAYER_SPEED_UUID, "Speed modifier", (getModifierValue(MOVEMENT_SPEED, stack) - 1) / 100.0f, AttributeModifier.Operation.ADDITION));
            if(equipmentSlot == EquipmentSlot.MAINHAND && getModifierValue(REACH, stack) > 0)
            {
                builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(BASE_BLOCK_REACH_UUID, "Reach modifier", getModifierValue(REACH, stack) - 4.5f, AttributeModifier.Operation.ADDITION));
                builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_REACH_UUID, "Reach modifier",getModifierValue(REACH, stack) - 3.0f - 1.5f, AttributeModifier.Operation.ADDITION));
            }
            if(equipmentSlot.isArmor() && getModifierValue(REGENERATION, stack) > 0) builder.put(ModAttributes.REGENERATION.get(), new AttributeModifier(BASE_REGENERATION_UUID, "Regeneration modifier", getModifierValue(REGENERATION, stack), AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
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
