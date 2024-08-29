package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.effect.FreezeEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MODID);
    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze",() -> new FreezeEffect(MobEffectCategory.HARMFUL, 9154528).addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(Attributes.JUMP_STRENGTH, "7107DE5E-7CE8-0011-940E-514C1F160890", (double)-10.0F, AttributeModifier.Operation.ADDITION));
}
