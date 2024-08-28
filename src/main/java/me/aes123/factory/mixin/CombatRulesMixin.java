package me.aes123.factory.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static java.lang.Math.PI;
import static java.lang.Math.atan;

@Mixin(CombatRules.class)
public class CombatRulesMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite()
    public static float getDamageAfterAbsorb(float damage, float armor, float armorToughness) {
        //float toughness = 2.0F + armorToughness / 4.0F;
        //float f1 = Mth.clamp(armor - damage / toughness, armor * 0.2F, 20.0F);
        //return damage * (1.0F - f1 / 25.0F);
        return damage * (1 - (float)atan(armor * armor / 500) * 2 / (float)PI);
    }
}
