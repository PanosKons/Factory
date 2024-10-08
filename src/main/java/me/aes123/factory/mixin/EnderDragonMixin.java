package me.aes123.factory.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin {
    @Inject(method = "hurt(Lnet/minecraft/world/entity/boss/EnderDragonPart;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "HEAD"), cancellable = true)
    private void ignoreBedDamage(final EnderDragonPart p_213403_1_, final DamageSource damageSource, final float p_213403_3_, final CallbackInfoReturnable<Boolean> cir)
    {
        if (damageSource.is(DamageTypes.BAD_RESPAWN_POINT) || damageSource.getMsgId().contains("explosion"))
        {
            cir.setReturnValue(false);
        }
    }
}
