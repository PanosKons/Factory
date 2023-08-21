package me.aes123.factory.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoldItem extends Item {
    public MoldItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(stack.hasTag()) {
            int size = stack.getTag().getInt("size");
            components.add(Component.literal("Size: " + size).withStyle(ChatFormatting.RED));
        }
        super.appendHoverText(stack, level, components, flag);
    }
}
