package me.aes123.factory.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ArtifactItem extends Item {

    private final Random rnd;
    public ArtifactItem(Properties properties) {
        super(properties);
        rnd = new Random();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean b) {
        if(stack.hasTag() && entity instanceof Player player && i == 0)
        {
            int speed = stack.getTag().getInt("speed");
            if(speed > 0) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, speed - 1, false, false));
            int haste = stack.getTag().getInt("haste");
            if(haste > 0) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1, haste - 1, false, false));
            if(rnd.nextInt() % (20 * 60) == 0) takeDurabilityDamage(stack, player);
        }
        super.inventoryTick(stack, level, entity, i, b);
    }

    void takeDurabilityDamage(ItemStack stack, LivingEntity entity)
    {
        if(stack.hasTag() == false) return;
        int durability = stack.getTag().getInt("durability");
        int maxDurability = stack.getTag().getInt("max_durability");
        durability--;
        if (durability > 0)
        {
            stack.getTag().putInt("durability", durability);
            stack.setDamageValue(1000 - (1000 * durability) / maxDurability);
        }
        else
        {
            stack.hurtAndBreak(1000, entity, (e) -> {
                e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag tooltipFlag) {

        if(itemStack.hasTag() != false)
        {
            int speed = itemStack.getTag().getInt("speed");
            int haste = itemStack.getTag().getInt("haste");
            int durability = itemStack.getTag().getInt("durability");
            int max_durability = itemStack.getTag().getInt("max_durability");

            if(Screen.hasShiftDown())
            {
                components.add(Component.literal("Durability: " + durability + "/" + max_durability).withStyle(ChatFormatting.GREEN));
                components.add(Component.literal("Speed: " + speed).withStyle(ChatFormatting.RED));
                components.add(Component.literal("Haste: " + haste).withStyle(ChatFormatting.GOLD));
            }
            else
            {
                components.add(Component.literal("<Hold SHIFT for info>").withStyle(ChatFormatting.YELLOW));
            }
        }
        super.appendHoverText(itemStack, p_41422_, components, tooltipFlag);
    }
}
