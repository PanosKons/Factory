package me.aes123.factory.config;

import me.aes123.factory.data.EquipmentModifier;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactoryCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Float> NATURAL_REGENERATION_MODIFIER;

    static{
        BUILDER.push("Factory Config");

        NATURAL_REGENERATION_MODIFIER = BUILDER.comment("How fast hunger regenerates hearts")
                .define("Natural Regeneration Modifier", 1.0f);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
