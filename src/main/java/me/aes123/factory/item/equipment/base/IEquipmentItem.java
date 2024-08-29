package me.aes123.factory.item.equipment.base;

import me.aes123.factory.Main;
import me.aes123.factory.data.EquipmentMaterial;
import me.aes123.factory.data.EquipmentModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static me.aes123.factory.data.EquipmentModifier.EquipmentModifierType.MAX_DURABILITY;

public interface IEquipmentItem {
    UUID BASE_BLOCK_REACH_UUID = UUID.fromString("CB3F65D3-245D-4F28-A437-9C13A44DB2CF");
    UUID BASE_ENTITY_REACH_UUID = UUID.fromString("CB3F55D3-245D-4E28-B438-9C13A44DB2CF");
    default void updateDurabilityBar(ItemStack stack)
    {
        if((int)getModifierValue(MAX_DURABILITY, stack) == 0) return;
        stack.setDamageValue(100000 - (100000 * getDurability(stack)) / (int)getModifierValue(MAX_DURABILITY, stack));
    }
    default int getDurability(ItemStack stack)
    {
        return stack.getTag().getInt("durability");
    }
    default void setDurability(int value, ItemStack stack)
    {
        stack.getTag().putInt("durability", value);
    }
    default float getModifierValue(EquipmentModifier.EquipmentModifierType modifierType, ItemStack stack)
    {
        float baseValue = stack.getTag().getFloat("base_" + modifierType.toString().toLowerCase());
        return new EquipmentModifier(modifierType, stack.getTag().getInt(modifierType.toString().toLowerCase())).getValue(baseValue) + baseValue;
    }
    default int getModifierLevel(EquipmentModifier.EquipmentModifierType modifierType, ItemStack stack)
    {
        return stack.getTag().getInt(modifierType.toString().toLowerCase());
    }
    default String getToolMaterial(ItemStack stack)
    {
        String[] ls = (ForgeRegistries.ITEMS.getKey(stack.getItem()).toString().split(":"))[1].split("_");
        return ls[0].equals("golden") ? "gold" : ls[0];
    }
    default String getToolName(ItemStack stack)
    {
        return (ForgeRegistries.ITEMS.getKey(stack.getItem()).toString().split(":"))[1].split("_")[1];
    }
    static String intToRoman(int num)
    {
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLetters = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder roman = new StringBuilder();
        for(int i=0;i<values.length;i++)
        {
            while(num >= values[i])
            {
                num = num - values[i];
                roman.append(romanLetters[i]);
            }
        }
        return roman.toString();
    }
    default void appendText(ItemStack stack, List<Component> components)
    {
        if(getDurability(stack) == 0) return;

        components.add(Component.literal("Durability " + getDurability(stack) + "/" + (int)getModifierValue(MAX_DURABILITY, stack)).withStyle(ChatFormatting.GRAY));
        //components.add(Component.literal("Instability " + Main.df.format(stack.getTag().getFloat("xp_cost"))).withStyle(ChatFormatting.LIGHT_PURPLE));

        var allModifiers = EquipmentModifier.EquipmentModifierType.values();
        Arrays.stream(allModifiers).sorted(Comparator.comparingInt(a -> a.format.ordinal()));
        for(var modifierType : allModifiers)
        {
            if(modifierType == MAX_DURABILITY) continue;
            int level = getModifierLevel(modifierType, stack);
            if(level == 0) continue;
            String snake_case = modifierType.toString().toLowerCase();
            String[] parts = snake_case.split("_");
            String displayName = "";
            for (int i = 0; i < parts.length; i++){

                String string = parts[i];
                String temp = string.substring(0, 1).toUpperCase()+string.substring(1);
                displayName = displayName.concat(temp + " ");
            }
            switch (modifierType.format)
            {
                case DEFAULT -> components.add(Component.literal(displayName + intToRoman(level)).withStyle(ChatFormatting.GRAY));
                case ONLY_TYPE -> components.add(Component.literal(displayName).withStyle(ChatFormatting.GREEN));
            }
        }
    }
    default void takeDurabilityDamage(ItemStack stack, LivingEntity entity, InteractionHand slot, int damage)
    {
        int durability = getDurability(stack);
        if (durability > 0)
        {
            setDurability(durability - damage, stack);
            stack.setDamageValue(1000 - (1000 * (durability - damage)) / (int)getModifierValue(MAX_DURABILITY, stack));
        }
        if(durability - damage <= 0)
        {
            stack.hurtAndBreak(1, entity, (e) -> {
                e.broadcastBreakEvent(slot);
            });
        }
    }
    default void takeDurabilityDamage(ItemStack stack, LivingEntity entity, EquipmentSlot slot, int damage)
    {
        int durability = getDurability(stack);
        if (durability > 0)
        {
            setDurability(durability - damage, stack);
            stack.setDamageValue(1000 - (1000 * (durability- damage)) / (int)getModifierValue(MAX_DURABILITY, stack));
        }
    }
    default void addDefaultStats(ItemStack stack) {

        EquipmentMaterial.EQUIPMENT_MATERIALS.get(getToolMaterial(stack)).applyBaseStats(stack.getTag(), getToolName(stack),false);
    }
    default void tick(ItemStack stack)
    {
        if(getModifierValue(MAX_DURABILITY, stack) == 0){
            addDefaultStats(stack);
            stack.getTag().putFloat("xp_cost", 1.0f);
        }
    }
}
