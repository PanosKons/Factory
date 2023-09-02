package me.aes123.factory.init;

import me.aes123.factory.Main;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Main.MODID);

    public static final RegistryObject<Attribute> REGENERATION = ATTRIBUTES.register("regeneration", () -> new RangedAttribute("factory.regeneration", 0.0D, 0.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ABSORB_DAMAGE_CHANCE = ATTRIBUTES.register("absorb_damage_chance", () -> new RangedAttribute("factory.absorb_damage_chance", 0.0D, 0.0D, 100.0D).setSyncable(true));
}
