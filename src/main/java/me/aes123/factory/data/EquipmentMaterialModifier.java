package me.aes123.factory.data;

import net.minecraft.world.item.Item;

import java.util.List;

public class EquipmentMaterialModifier {

    public Item item;
    public List<EquipmentModifier> modifiers;

    public EquipmentMaterialModifier(Item item, List<EquipmentModifier> modifiers)
    {
        this.item = item;
        this.modifiers = modifiers;
    }

    public static List<EquipmentMaterialModifier> EQUIPMENT_MATERIAL_MODIFIERS;
}
