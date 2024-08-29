package me.aes123.factory.mixin;

import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ThornsEnchantment.class)
public abstract class ThornsEnchantmentMixin {
    @ModifyConstant(method = "doPostHurt", constant = @Constant(intValue = 2))
    public int method(int constant)
    {
        return 0;
    }
}
