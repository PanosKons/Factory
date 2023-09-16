package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.blockentity.*;
import me.aes123.factory.util.IBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityType {

    static HopperBlockEntity createImprovedHopperBlockEntity(BlockPos pos, BlockState state)
    {
        var entity = new HopperBlockEntity(pos, state);
        ((IBlockEntity)(entity)).setType(IMPROVED_HOPPER_BLOCK_ENTITY.get());
        return entity;
    }
    static HopperBlockEntity createProfoundHopperBlockEntity(BlockPos pos, BlockState state)
    {
        var entity = new HopperBlockEntity(pos, state);
        ((IBlockEntity)(entity)).setType(PROFOUND_HOPPER_BLOCK_ENTITY.get());
        return entity;
    }
    static HopperBlockEntity createReinforcedHopperBlockEntity(BlockPos pos, BlockState state)
    {
        var entity = new HopperBlockEntity(pos, state);
        ((IBlockEntity)(entity)).setType(REINFORCED_HOPPER_BLOCK_ENTITY.get());
        return entity;
    }

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);
    public static final RegistryObject<BlockEntityType<GateBlockEntity>> GATE_BLOCK_ENTITY = BLOCK_ENTITIES.register("gate_block_entity", ()-> BlockEntityType.Builder.of(GateBlockEntity::new, ModBlocks.GATE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<AssemblerBlockEntity>> ASSEMBLER_BLOCK_ENTITY = BLOCK_ENTITIES.register("assembler_block_entity", ()-> BlockEntityType.Builder.of(AssemblerBlockEntity::new, ModBlocks.ASSEMBLER.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("generator_block_entity", ()-> BlockEntityType.Builder.of(GeneratorBlockEntity::new, ModBlocks.GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY = BLOCK_ENTITIES.register("crusher_block_entity", ()-> BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocks.CRUSHER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE_BLOCK_ENTITY = BLOCK_ENTITIES.register("electric_furnace_block_entity", ()-> BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, ModBlocks.ELECTRIC_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModBarrelBlockEntity>> IMPROVED_BARREL_BLOCK_ENTITY = BLOCK_ENTITIES.register("improved_barrel_block_entity", () -> BlockEntityType.Builder.of(ModBarrelBlockEntity::new, ModBlocks.IMPROVED_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModBarrelBlockEntity>> PROFOUND_BARREL_BLOCK_ENTITY = BLOCK_ENTITIES.register("profound_barrel_block_entity", () -> BlockEntityType.Builder.of(ModBarrelBlockEntity::new, ModBlocks.PROFOUND_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModBarrelBlockEntity>> REINFORCED_BARREL_BLOCK_ENTITY = BLOCK_ENTITIES.register("reinforced_barrel_block_entity", () -> BlockEntityType.Builder.of(ModBarrelBlockEntity::new, ModBlocks.REINFORCED_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<DiscoveryStationBlockEntity>> DISCOVERY_STATION_BLOCK_ENTITY = BLOCK_ENTITIES.register("discovery_station_block_entity", () -> BlockEntityType.Builder.of(DiscoveryStationBlockEntity::new, ModBlocks.DISCOVERY_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<EquipmentStationBlockEntity>> EQUIPMENT_STATION_BLOCK_ENTITY = BLOCK_ENTITIES.register("equipment_station_block_entity", () -> BlockEntityType.Builder.of(EquipmentStationBlockEntity::new, ModBlocks.EQUIPMENT_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModEnchantmentTableBlockEntity>> ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("enchanting_table_block_entity", () -> BlockEntityType.Builder.of(ModEnchantmentTableBlockEntity::new, ModBlocks.ENCHANTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<HopperBlockEntity>> IMPROVED_HOPPER_BLOCK_ENTITY = BLOCK_ENTITIES.register("improved_hopper_block_entity", () -> BlockEntityType.Builder.of((pos, state) -> createImprovedHopperBlockEntity(pos, state), ModBlocks.IMPROVED_HOPPER.get()).build(null));
    public static final RegistryObject<BlockEntityType<HopperBlockEntity>> PROFOUND_HOPPER_BLOCK_ENTITY = BLOCK_ENTITIES.register("profound_hopper_block_entity", () -> BlockEntityType.Builder.of((pos, state) -> createProfoundHopperBlockEntity(pos, state), ModBlocks.PROFOUND_HOPPER.get()).build(null));
    public static final RegistryObject<BlockEntityType<HopperBlockEntity>> REINFORCED_HOPPER_BLOCK_ENTITY = BLOCK_ENTITIES.register("reinforced_hopper_block_entity", () -> BlockEntityType.Builder.of((pos, state) -> createReinforcedHopperBlockEntity(pos, state), ModBlocks.REINFORCED_HOPPER.get()).build(null));
}
