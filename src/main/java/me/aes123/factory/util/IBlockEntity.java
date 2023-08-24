package me.aes123.factory.util;

import net.minecraft.world.level.block.entity.BlockEntityType;

public interface IBlockEntity {
    void setType(BlockEntityType<?> type);
}
