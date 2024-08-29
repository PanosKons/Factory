package me.aes123.factory.mixin;

import net.minecraft.world.entity.projectile.ShulkerBullet;
import org.checkerframework.checker.units.qual.C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ShulkerBullet.class)
public class ShulkerBulletMixin {
    @ModifyConstant( method = "onHitEntity", constant = @Constant(floatValue = 4.0F))
    private float get(float value) {
        return 16.0F;
    }

}
