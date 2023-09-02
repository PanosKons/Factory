package me.aes123.factory.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.aes123.factory.data.EquipmentModifier;
import me.aes123.factory.item.equipment.base.ModEquipmentItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.Random;

import static me.aes123.factory.data.EquipmentModifier.EquipmentModifierType.*;

public class ModTotem extends ModEquipmentItem {
    private final List<EquipmentModifier> possiblePassiveRolls = List.of(
            new EquipmentModifier(PLAYER_SPEED,1),
            new EquipmentModifier(PLAYER_SPEED,2),
            new EquipmentModifier(NIGHT_VISION,1),
            new EquipmentModifier(BLOCK_REACH,1),
            new EquipmentModifier(BLOCK_REACH,2),
            new EquipmentModifier(LUCK,1),
            new EquipmentModifier(REGENERATION,1),
            new EquipmentModifier(REGENERATION,2),
            new EquipmentModifier(STABILIZED,1),
            new EquipmentModifier(ABSORB_DAMAGE_CHANCE,1),
            new EquipmentModifier(ABSORB_DAMAGE_CHANCE,2),
            new EquipmentModifier(SLOWER_HUNGER,1)
    );
    private final List<EquipmentModifier> possibleActiveRolls = List.of(
            new EquipmentModifier(WEATHER_CONTROL,1),
            new EquipmentModifier(TIME_CONTROL,1),
            new EquipmentModifier(ENTITY_HIGHLIGHTER,1)
    );

    public ModTotem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotIndex, boolean b) {
        if(entity instanceof Player player)
        {
            boolean flag1 = player.getItemInHand(InteractionHand.MAIN_HAND) == stack;
            boolean flag2 = player.getItemInHand(InteractionHand.OFF_HAND) == stack;
            if((flag1 || flag2) && addDurabilityTick(stack)) takeDurabilityDamage(stack, player, flag1 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND ,1);
        }
        super.inventoryTick(stack, level, entity, slotIndex, b);
    }

    private boolean addDurabilityTick(ItemStack stack) {
        boolean ret = false;
        int holdTicks = stack.getTag().getInt("hold_time");
        holdTicks++;
        if(holdTicks > 20 * 10)
        {
            holdTicks = 0;
            ret = true;
        }
        stack.getTag().putInt("hold_time", holdTicks);
        return ret;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack)
    {
        if(stack.hasTag() && (equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND))
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            if(getModifierValue(PLAYER_SPEED, stack) > 0) builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_PLAYER_SPEED_UUID, "Speed modifier", (getModifierValue(PLAYER_SPEED, stack) - 1) / 10.0f, AttributeModifier.Operation.ADDITION));
            if(getModifierValue(BLOCK_REACH, stack) > 0) builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(BASE_BLOCK_REACH_UUID, "Reach modifier", getModifierValue(BLOCK_REACH, stack) - 4.5f, AttributeModifier.Operation.ADDITION));
            if(getModifierValue(ENTITY_REACH, stack) > 0) builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_REACH_UUID, "Reach modifier",getModifierValue(ENTITY_REACH, stack) - 3.0f, AttributeModifier.Operation.ADDITION));
            if(getModifierValue(LUCK, stack) > 0) builder.put(Attributes.LUCK, new AttributeModifier(BASE_LUCK_UUID, "Luck modifier",getModifierValue(LUCK, stack), AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void addDefaultStats(ItemStack stack) {
        var nbt = stack.getTag();
        var rnd = new Random(nbt.getInt("seed"));
        nbt.putInt("HideFlags", 26);
        int max_durability = 200 + rnd.nextInt(800);
        nbt.putInt("base_max_durability", max_durability);

        possiblePassiveRolls.get(rnd.nextInt(possiblePassiveRolls.size())).add(nbt);
        possibleActiveRolls.get(rnd.nextInt(possibleActiveRolls.size())).add(nbt);

        setDurability((int)getModifierValue(MAX_DURABILITY, stack), stack);
    }
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged && super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }
}
