package me.aes123.factory.util;

import net.minecraft.world.item.Item;

import java.util.Map;

public class EquipmentStationMaterial {
    public static Map<Item, EquipmentStationMaterial> EQUIPMENT_STATION_MATERIALS = Map.of();

    public int baseSpeed;
    public int baseDurability;

    public EquipmentStationMaterial(int baseSpeed, int baseDurability)
    {
        this.baseSpeed = baseSpeed;
        this.baseDurability = baseDurability;
    }
}
