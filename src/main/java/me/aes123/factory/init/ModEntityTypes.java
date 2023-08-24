package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.entity.GuardEntity;
import me.aes123.factory.entity.ModMinecart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);

    public static final DeferredRegister<EntityType<?>> VANILLA_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "minecraft");

    public static final RegistryObject<EntityType<GuardEntity>> GUARD = ENTITIES.register("guard", () ->
            EntityType.Builder.of(GuardEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 1.9f)
                    .build(new ResourceLocation(Main.MODID, "guard").toString()));

    public static final RegistryObject<EntityType<ModMinecart>> MINECART = VANILLA_ENTITIES.register("minecart", () ->
            EntityType.Builder.of(ModMinecart::new, MobCategory.MONSTER)
                    .sized(0.8f, 1.9f)
                    .build(new ResourceLocation("minecraft", "minecart").toString()));
}
