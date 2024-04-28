package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.block.*;
import me.aes123.factory.util.ModRarity;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");


    public static final RegistryObject<Block> GATE_BLOCK = BLOCKS.register("gate_block", () -> new GateBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<AssemblerBlock> ASSEMBLER = BLOCKS.register("assembler", () -> new AssemblerBlock(BlockBehaviour.Properties
            .copy(Blocks.IRON_BLOCK)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<GeneratorBlock> GENERATOR = BLOCKS.register("generator", () -> new GeneratorBlock(BlockBehaviour.Properties
            .copy(Blocks.IRON_BLOCK)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<DiscoveryStationBlock> DISCOVERY_STATION = BLOCKS.register("discovery_station", () -> new DiscoveryStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GRAY)));

    public static final RegistryObject<EquipmentStationBlock> EQUIPMENT_STATION = BLOCKS.register("equipment_station", () -> new EquipmentStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final  RegistryObject<ModBarrelBlock> IMPROVED_BARREL = BLOCKS.register("improved_barrel", () -> new ModBarrelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).instrument(NoteBlockInstrument.BASS).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL), ModRarity.IMPROVED));

    public static final  RegistryObject<ModBarrelBlock> PROFOUND_BARREL = BLOCKS.register("profound_barrel", () -> new ModBarrelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).instrument(NoteBlockInstrument.BASS).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL), ModRarity.PROFOUND));
    public static final  RegistryObject<ModBarrelBlock> REINFORCED_BARREL = BLOCKS.register("reinforced_barrel", () -> new ModBarrelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).instrument(NoteBlockInstrument.BASS).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL), ModRarity.REINFORCED));

    public static final RegistryObject<ElectricFurnaceBlock> ELECTRIC_FURNACE = BLOCKS.register("electric_furnace", () -> new ElectricFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<CrusherBlock> CRUSHER = BLOCKS.register("crusher", () -> new CrusherBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<DropExperienceBlock> RUBY_ORE = BLOCKS.register("ruby_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(10.0F, 1200.0F), UniformInt.of(50, 100)));
    public static final RegistryObject<ModHopperBlock> IMPROVED_HOPPER = BLOCKS.register("improved_hopper", () -> new ModHopperBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 4.8F).sound(SoundType.METAL).noOcclusion(), ModBlockEntityType.IMPROVED_HOPPER_BLOCK_ENTITY, 5));
    public static final RegistryObject<ModHopperBlock> PROFOUND_HOPPER = BLOCKS.register("profound_hopper", () -> new ModHopperBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 4.8F).sound(SoundType.METAL).noOcclusion(),ModBlockEntityType.PROFOUND_HOPPER_BLOCK_ENTITY,3));
    public static final RegistryObject<ModHopperBlock> REINFORCED_HOPPER = BLOCKS.register("reinforced_hopper", () -> new ModHopperBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 4.8F).sound(SoundType.METAL).noOcclusion(),ModBlockEntityType.REINFORCED_HOPPER_BLOCK_ENTITY,1));

    //VANILLA
    public static final RegistryObject<ModRailBlock> RAIL = VANILLA_BLOCKS.register("rail", () -> new ModRailBlock(BlockBehaviour.Properties.of().noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final RegistryObject<ModPoweredRailBlock> POWERED_RAIL = VANILLA_BLOCKS.register("powered_rail", () -> new ModPoweredRailBlock(BlockBehaviour.Properties.of().noCollission().strength(0.7F).sound(SoundType.METAL),true));

    //public static final RegistryObject<Block> ANVIL = VANILLA_BLOCKS.register("anvil", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)));
    public static final RegistryObject<ModEnchantmentTableBlock> ENCHANTING_TABLE = VANILLA_BLOCKS.register("enchanting_table", () -> new ModEnchantmentTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().lightLevel((p_187437_) -> 7).strength(5.0F, 1200.0F)));
}
