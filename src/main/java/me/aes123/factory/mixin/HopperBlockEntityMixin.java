package me.aes123.factory.mixin;

import me.aes123.factory.util.IHopperBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin implements IHopperBlockEntity {
    public int moveItemDelay = 8;
    @Redirect(method = "tryMoveInItem", at = @At(value  = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;setCooldown(I)V"))
    private static void setCoolDown0(HopperBlockEntity instance, int p_59396_) {
        instance.setCooldown(((HopperBlockEntityMixin)(Object)(instance)).moveItemDelay);
    }
    @Redirect(method = "tryMoveItems", at = @At(value  = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;setCooldown(I)V"))
    private static void setCoolDown1(HopperBlockEntity instance, int p_59396_) {
        instance.setCooldown(((HopperBlockEntityMixin)(Object)(instance)).moveItemDelay);
    }
    @ModifyConstant(method = "isOnCustomCooldown", constant = @Constant(intValue = 8))
    private int getSpeed2(int value) {
        return moveItemDelay;
    }

    @Inject(method = "saveAdditional", at = @At(value = "TAIL"))
    public void saveAdditional1(CompoundTag compoundTag, CallbackInfo ci)
    {
        compoundTag.putInt("MoveItemDelay", moveItemDelay);
    }
    @Inject(method = "load", at = @At(value = "TAIL"))
    public void load1(CompoundTag compoundTag, CallbackInfo ci)
    {
        moveItemDelay = compoundTag.getInt("MoveItemDelay");
    }

    @Override
    public void setMoveItemDelay(int value)
    {
        moveItemDelay = value;
    }
}
