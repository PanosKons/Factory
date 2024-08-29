package me.aes123.factory.mixin;

import me.aes123.factory.util.IBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import javax.annotation.Nullable;

@Mixin(BlockEntity.class)
public interface BlockEntityMixin {
    @Accessor("type") @Final
    @Mutable
    void be_setType(BlockEntityType<?> blockEntityType);
}
