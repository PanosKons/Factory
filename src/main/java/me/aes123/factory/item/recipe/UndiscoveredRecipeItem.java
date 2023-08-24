package me.aes123.factory.item.recipe;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UndiscoveredRecipeItem extends Item {
    public UndiscoveredRecipeItem(Properties properties) {
        super(properties.defaultDurability(1000));
    }

    public static void updateBar(ItemStack stack)
    {
        if(stack.hasTag()) {
            int progress = stack.getTag().getInt("progress");
            int max_progress = stack.getTag().getInt("max_progress");
            stack.setDamageValue(1000 - (1000 * progress) / max_progress);
        }
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        updateBar(p_41404_);
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(!itemStack.hasTag()) {
            itemStack.getOrCreateTag().putString("recipe","null");
            itemStack.getTag().putInt("progress",0);
            itemStack.getTag().putInt("max_progress",10);
        }
            int progress = itemStack.getTag().getInt("progress");
            int max_progress = itemStack.getTag().getInt("max_progress");
            String recipe = itemStack.getTag().getString("recipe");
            if(Screen.hasShiftDown())
            {
                components.add(Component.literal("Progress: " + progress + "/" + max_progress).withStyle(ChatFormatting.GREEN));
                components.add(Component.literal("Recipe: " + recipe).withStyle(ChatFormatting.RED));
            }
            else
            {
                components.add(Component.literal("<Hold SHIFT for info>").withStyle(ChatFormatting.YELLOW));
            }
        super.appendHoverText(itemStack, level, components, flag);
    }
}
