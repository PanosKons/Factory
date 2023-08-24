package me.aes123.factory.mixin;

import me.aes123.factory.util.IBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements IBlockEntity {
    @Shadow BlockEntityType<?> type;
    @Nullable
    BlockEntityType<?> overridenType;

    @Override
    public void setType(BlockEntityType<?> type) {
        this.overridenType = type;
    }

    public BlockEntityType<?> getType() {
        return overridenType == null ? this.type : this.overridenType;
    }
}
