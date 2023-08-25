package me.aes123.factory.item.equipment.base;

import me.aes123.factory.Main;
import me.aes123.factory.data.EquipmentMaterial;
import me.aes123.factory.data.EquipmentModifier;
import me.aes123.factory.item.equipment.ModTotem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModEquipmentItem extends Item implements Vanishable {
    private static final DecimalFormat df = new DecimalFormat();
    protected static final UUID BASE_PLAYER_SPEED_UUID = UUID.fromString("CB3F65D3-145C-4F38-A437-9C13A33DB5CF");
    protected static final UUID BASE_BLOCK_REACH_UUID = UUID.fromString("CB3F65D3-245D-4F28-A437-9C13A44DB2CF");
    protected static final UUID BASE_ENTITY_REACH_UUID = UUID.fromString("CB3F55D3-245D-4E28-B438-9C13A44DB2CF");

    public ModEquipmentItem(Properties properties) {
        super(properties.defaultDurability(1000));
        df.setMaximumFractionDigits(2);
    }

    public void updateDurabilityBar(ItemStack stack)
    {
        int durability = stack.getTag().getInt("durability");
        int maxDurability = stack.getTag().getInt("max_durability");
        stack.setDamageValue(1000 - (1000 * durability) / maxDurability);
    }
    public void takeDurabilityDamage(ItemStack stack, LivingEntity entity, EquipmentSlot slot, int damage)
    {
        int durability = stack.getTag().getInt("durability");
        int maxDurability = stack.getTag().getInt("max_durability");
        durability -= damage;
        if (durability > 0)
        {
            stack.getTag().putInt("durability", durability);
            stack.setDamageValue(1000 - (1000 * durability) / maxDurability);
        }
        else
        {
            stack.hurtAndBreak(1000, entity, (e) -> {
                e.broadcastBreakEvent(slot);
            });
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotIndex, boolean selected) {
        if(stack.getTag().getFloat("night_vision") > 0);

        super.inventoryTick(stack, level, entity, slotIndex, selected);
    }

    @Override
    public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        if(enchantment == Enchantments.SILK_TOUCH && stack.getTag().getFloat("silk_touch") > 0) return 1;
        if(enchantment == Enchantments.THORNS) return (int)stack.getTag().getFloat("thorns");
        return 0;
    }

    @Override
    public Map<Enchantment, Integer> getAllEnchantments(ItemStack stack) {
        Map<Enchantment, Integer> map = Map.of();
        if(stack.getTag().getFloat("silk_touch") > 0) map.put(Enchantments.SILK_TOUCH, 1);
        if(stack.getTag().getFloat("thorns") > 0) ;//map.put(Enchantments.THORNS, (int)stack.getTag().getFloat("thorns")); ////////////////////////////////
        return map;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        if(stack.getTag().getInt("max_durability") == 0) addDefaultStats(stack);
        components.add(Component.literal("Durability " + stack.getTag().getInt("durability") + "/" + stack.getTag().getInt("max_durability")).withStyle(ChatFormatting.GRAY));

        if(Screen.hasShiftDown())
        {
            var map = getEquipmentStats(stack);
            for(var entry : map.entrySet())
            {
                String snake_case = entry.getKey().toString().toLowerCase();
                String[] parts = snake_case.split("_");
                String displayName = "";
                for (int i = 0; i < parts.length; i++){

                    String string = parts[i];
                    String temp = string.substring(0, 1).toUpperCase()+string.substring(1);
                    displayName = displayName.concat(temp + " ");
                }

                if(entry.getKey() != EquipmentModifier.EquipmentModifierType.MAX_DURABILITY)
                    switch (entry.getKey().format)
                    {
                        case DEFAULT -> components.add(Component.literal(displayName + df.format(entry.getValue())).withStyle(ChatFormatting.GRAY));
                        case PERCENTAGE -> components.add(Component.literal(displayName + df.format(entry.getValue()) + "%").withStyle(ChatFormatting.GRAY));
                        case ONLY_TYPE -> components.add(Component.literal(displayName).withStyle(ChatFormatting.GREEN));
                        case LEGENDARY -> components.add(Component.literal(displayName).withStyle(ChatFormatting.DARK_RED));
                    }
            }

            if(stack.getTag().getFloat("xp_cost") <= 0)
            {
                stack.getTag().putFloat("xp_cost", 1.0f);
            }
            components.add(Component.literal("XP Cost " + df.format(stack.getTag().getFloat("xp_cost"))).withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        else
        {
            components.add(Component.literal("<Hold SHIFT for info>").withStyle(ChatFormatting.YELLOW));
        }
    }

    public void addDefaultStats(ItemStack stack) {
        String[] ls = (ForgeRegistries.ITEMS.getKey(stack.getItem()).toString().split(":"))[1].split("_");
        String lss = ls[0].equals("golden") ? "gold" : ls[0];
        EquipmentMaterial.EQUIPMENT_MATERIALS.get(lss).addBaseMaterial(stack.getTag() ,ls[1],false);
    }

    public Map<EquipmentModifier.EquipmentModifierType, Float> getEquipmentStats(ItemStack stack)
    {
        Map<EquipmentModifier.EquipmentModifierType, Float> ret = new HashMap<>();
        for(var modifierType : EquipmentModifier.EquipmentModifierType.values())
        {
            float value = stack.getTag().getFloat(modifierType.toString().toLowerCase());
            if(value > 0.0f) ret.put(modifierType,value);
        }
        return ret;
    }
}
