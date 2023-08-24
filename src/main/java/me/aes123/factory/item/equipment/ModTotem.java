package me.aes123.factory.item.equipment;

import me.aes123.factory.Main;
import me.aes123.factory.data.EquipmentModifier;
import me.aes123.factory.item.equipment.base.ModEquipmentItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

import static me.aes123.factory.data.EquipmentModifier.EquipmentModifierType.*;

public class ModTotem extends ModEquipmentItem {

    private final List<EquipmentModifier> possiblePassiveRolls = List.of(
            new EquipmentModifier(PLAYER_SPEED,1),
            new EquipmentModifier(PLAYER_SPEED,2),
            new EquipmentModifier(NIGHT_VISION,1),
            new EquipmentModifier(PLAYER_REACH,1),
            new EquipmentModifier(PLAYER_REACH,2),
            new EquipmentModifier(PLAYER_LUCK,1),
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
            new EquipmentModifier(TOTEM_OF_UNDYING,1),
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
            if((flag1 || flag2) && Main.rnd.nextInt(20 * 10) == 0) takeDurabilityDamage(stack, player, flag1 ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND ,1);
        }
        super.inventoryTick(stack, level, entity, slotIndex, b);
    }

    @Override
    public void addDefaultStats(ItemStack stack) {
        var nbt = stack.getTag();

        nbt.putInt("HideFlags", 26);
        int max_durability = 200 + Main.rnd.nextInt(800);
        nbt.putInt("max_durability", max_durability);

        possiblePassiveRolls.get(Main.rnd.nextInt(possiblePassiveRolls.size())).add(nbt);
        possibleActiveRolls.get(Main.rnd.nextInt(possibleActiveRolls.size())).add(nbt);

        nbt.putInt("durability", nbt.getInt("max_durability"));
    }
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged && super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }
}
