package me.aes123.factory.item.recipe;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscoveredRecipeItem extends Item {

    public DiscoveredRecipeItem(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(!itemStack.hasTag()) {
            itemStack.getOrCreateTag().putString("recipe","null");
        }
        String recipe = itemStack.getTag().getString("recipe");
        components.add(Component.literal("Recipe: " + recipe).withStyle(ChatFormatting.RED));
        super.appendHoverText(itemStack, level, components, flag);
    }
}
