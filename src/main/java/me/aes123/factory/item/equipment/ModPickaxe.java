package me.aes123.factory.item.equipment;

import me.aes123.factory.item.equipment.base.ModTool;
import net.minecraft.tags.BlockTags;

public class ModPickaxe extends ModTool {
    public ModPickaxe(Properties properties, int HarvestLevel) {
        super(properties, HarvestLevel, BlockTags.MINEABLE_WITH_PICKAXE);
    }
}
