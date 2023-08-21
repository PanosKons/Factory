package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);
    public static final RegistryObject<BlockEntityType<AssemblerBlockEntity>> ASSEMBLER_BLOCK_ENTITY = BLOCK_ENTITIES.register("assembler_block_entity", ()-> BlockEntityType.Builder.of(AssemblerBlockEntity::new, ModBlocks.ASSEMBLER.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("generator_block_entity", ()-> BlockEntityType.Builder.of(GeneratorBlockEntity::new, ModBlocks.GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY = BLOCK_ENTITIES.register("crusher_block_entity", ()-> BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocks.CRUSHER.get()).build(null));
    public static final RegistryObject<BlockEntityType<QuarryBlockEntity>> QUARRY_BLOCK_ENTITY = BLOCK_ENTITIES.register("quarry_block_entity", ()-> BlockEntityType.Builder.of(QuarryBlockEntity::new, ModBlocks.QUARRY.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModBarrelBlockEntity>> IMPROVED_BARREL_BLOCK_ENTITY = BLOCK_ENTITIES.register("improved_barrel_block_entity", () -> BlockEntityType.Builder.of(ModBarrelBlockEntity::new, ModBlocks.IMPROVED_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModBarrelBlockEntity>> PROFOUND_BARREL_BLOCK_ENTITY = BLOCK_ENTITIES.register("profound_barrel_block_entity", () -> BlockEntityType.Builder.of(ModBarrelBlockEntity::new, ModBlocks.PROFOUND_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModBarrelBlockEntity>> REINFORCED_BARREL_BLOCK_ENTITY = BLOCK_ENTITIES.register("reinforced_barrel_block_entity", () -> BlockEntityType.Builder.of(ModBarrelBlockEntity::new, ModBlocks.REINFORCED_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<DiscoveryStationBlockEntity>> DISCOVERY_STATION_BLOCK_ENTITY = BLOCK_ENTITIES.register("discovery_station_block_entity", () -> BlockEntityType.Builder.of(DiscoveryStationBlockEntity::new, ModBlocks.DISCOVERY_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<EquipmentStationBlockEntity>> EQUIPMENT_STATION_BLOCK_ENTITY = BLOCK_ENTITIES.register("equipment_station_block_entity", () -> BlockEntityType.Builder.of(EquipmentStationBlockEntity::new, ModBlocks.EQUIPMENT_STATION.get()).build(null));
}
