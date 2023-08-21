package me.aes123.factory.util;

import net.minecraft.world.item.Item;

import java.util.Map;

public class EquipmentStationModifier {
    public static Map<Item, EquipmentStationModifier> EQUIPMENT_STATION_MODIFIERS = Map.of();

    public String modifierType;
    public int value;

    public EquipmentStationModifier(String modifierType, int value)
    {
        this.modifierType = modifierType;
        this.value = value;
    }
}
