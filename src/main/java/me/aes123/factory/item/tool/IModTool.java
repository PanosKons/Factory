package me.aes123.factory.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;


public interface IModTool{
    default void takeDurabilityDamage(ItemStack stack, LivingEntity entity)
    {
        if(stack.hasTag() == false) return;
        int durability = stack.getTag().getInt("durability");
        int maxDurability = stack.getTag().getInt("max_durability");
        durability--;
        if (durability > 0)
        {
            stack.getTag().putInt("durability",durability);
            stack.setDamageValue(1000 - (1000 * durability) / maxDurability);
        }
        else
        {
            stack.hurtAndBreak(1000, entity, (e) -> {
                e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
    }
    default void addHoverText(ItemStack itemStack, List<Component> components)
    {
        if(itemStack.hasTag() != false)
        {
            int durability = itemStack.getTag().getInt("durability");
            int max_durability = itemStack.getTag().getInt("max_durability");
            int speed = itemStack.getTag().getInt("speed");
            if(Screen.hasShiftDown())
            {
                components.add(Component.literal("Durability: " + durability + "/" + max_durability).withStyle(ChatFormatting.GREEN));
                components.add(Component.literal("Speed: " + speed).withStyle(ChatFormatting.RED));
            }
            else
            {
                components.add(Component.literal("<Hold SHIFT for info>").withStyle(ChatFormatting.YELLOW));
            }
        }
    }
}
