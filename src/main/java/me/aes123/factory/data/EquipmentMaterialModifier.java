package me.aes123.factory.data;

import me.aes123.factory.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

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
