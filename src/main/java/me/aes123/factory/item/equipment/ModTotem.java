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
    Random rnd = new Random(0);
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
            if((flag1 || flag2) && rnd.nextInt(20 * 10) == 0) takeDurabilityDamage(stack, player, flag1 ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND ,1);
            if((flag1 || flag2))
            {
                if(stack.getTag().getFloat("night_vision") > 0) player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1, 0, false, false));
                if(stack.getTag().getFloat("regeneration") > 0) player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int)stack.getTag().getFloat("regeneration"), 0, false, false));
                if(stack.getTag().getFloat("luck") > 0) player.addEffect(new MobEffectInstance(MobEffects.LUCK, (int)stack.getTag().getFloat("luck"), 0, false, false));
            }
        }
        super.inventoryTick(stack, level, entity, slotIndex, b);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack)
    {
        if(stack.hasTag() && (equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND))
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            if(stack.getTag().getFloat("player_speed") > 0) builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_PLAYER_SPEED_UUID, "Speed modifier", (stack.getTag().getFloat("player_speed") - 1) / 10.0f, AttributeModifier.Operation.ADDITION));
            if(stack.getTag().getFloat("block_reach") > 0) builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(BASE_BLOCK_REACH_UUID, "Reach modifier", stack.getTag().getFloat("block_reach") - 4.5f, AttributeModifier.Operation.ADDITION));
            if(stack.getTag().getFloat("entity_reach") > 0) builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_REACH_UUID, "Reach modifier", stack.getTag().getFloat("entity_reach") - 3.0f, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void addDefaultStats(ItemStack stack) {
        var nbt = stack.getTag();

        nbt.putInt("HideFlags", 26);
        int max_durability = 200 + rnd.nextInt(800);
        nbt.putInt("max_durability", max_durability);

        possiblePassiveRolls.get(rnd.nextInt(possiblePassiveRolls.size())).add(nbt);
        possibleActiveRolls.get(rnd.nextInt(possibleActiveRolls.size())).add(nbt);

        nbt.putInt("durability", nbt.getInt("max_durability"));
    }
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged && super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }
}
