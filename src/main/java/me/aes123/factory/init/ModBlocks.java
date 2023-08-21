package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.block.*;
import me.aes123.factory.util.ModRarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

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

    public static final RegistryObject<QuarryBlock> QUARRY = BLOCKS.register("quarry", () -> new QuarryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<CrusherBlock> CRUSHER = BLOCKS.register("crusher", () -> new CrusherBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<ModRailBlock> RAIL = VANILLA_BLOCKS.register("rail", () -> new ModRailBlock(BlockBehaviour.Properties.of().noCollission().strength(0.7F).sound(SoundType.METAL)));

    //public static final RegistryObject<Block> ANVIL = VANILLA_BLOCKS.register("anvil", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)));
    //public static final RegistryObject<Block> ENCHANTING_TABLE = VANILLA_BLOCKS.register("enchanting_table", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)));
}
