package me.aes123.factory.item.recipe;

import me.aes123.factory.Main;
import me.aes123.factory.util.ModTags;
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
import net.minecraftforge.registries.ForgeRegistries;
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
            if(max_progress != 0)
                stack.setDamageValue(1000 - (1000 * progress) / max_progress);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        updateBar(stack);
        super.inventoryTick(stack, p_41405_, p_41406_, p_41407_, p_41408_);
        addItem(stack);
    }
    public void addItem(ItemStack stack)
    {
        if(stack.hasTag()){
            if(stack.getTag().getInt("count") == 0)
            {
                var item = ModTags.Items.UndiscoveredRecipeItems.get(Main.rnd.nextInt(ModTags.Items.UndiscoveredRecipeItems.size()));
                stack.getTag().putString("item", ForgeRegistries.ITEMS.getKey(item.getItem()).toString());
                stack.getTag().putInt("count", item.getCount());
            }
        }
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
                components.add(Component.literal("Requires " + itemStack.getTag().getInt("count") + " " + itemStack.getTag().getString("item") + " or 1 expertise").withStyle(ChatFormatting.GRAY));
                components.add(Component.literal("Recipe: " + recipe).withStyle(ChatFormatting.RED));
            }
            else
            {
                components.add(Component.literal("<Hold SHIFT for info>").withStyle(ChatFormatting.YELLOW));
            }
        super.appendHoverText(itemStack, level, components, flag);
    }
}
