package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.item.ArtifactItem;
import me.aes123.factory.item.ModBundleItem;
import me.aes123.factory.item.ModMinecartItem;
import me.aes123.factory.item.MoldItem;
import me.aes123.factory.item.recipe.DiscoveredRecipeItem;
import me.aes123.factory.item.recipe.UndiscoveredRecipeItem;
import me.aes123.factory.item.tool.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.maven.artifact.Artifact;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    //ITEMS
    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food((new FoodProperties.Builder().alwaysEat()
                    .nutrition(3).saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HEAL, 1,0), 1)
                    .build()))));
    public static final RegistryObject<Item> BLANK_CARD = ITEMS.register("blank_card",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<UndiscoveredRecipeItem> UNDISCOVERED_RECIPE = ITEMS.register("undiscovered_recipe", () -> new UndiscoveredRecipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DiscoveredRecipeItem> DISCOVERED_RECIPE = ITEMS.register("discovered_recipe", () -> new DiscoveredRecipeItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<MoldItem> PICKAXE_MOLD = ITEMS.register("pickaxe_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> AXE_MOLD = ITEMS.register("axe_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> SWORD_MOLD = ITEMS.register("sword_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> HOE_MOLD = ITEMS.register("hoe_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> SHOVEL_MOLD = ITEMS.register("shovel_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> HAMMER_MOLD = ITEMS.register("hammer_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> EXPERTISE = ITEMS.register("expertise", () -> new Item(new Item.Properties()));
    public static final RegistryObject<ModBundleItem> IMPROVED_BUNDLE = ITEMS.register("improved_bundle", () -> new ModBundleItem(new Item.Properties(), 128));
    public static final RegistryObject<ModBundleItem> PROFOUND_BUNDLE = ITEMS.register("profound_bundle", () -> new ModBundleItem(new Item.Properties(), 256));
    public static final RegistryObject<ModBundleItem> REINFORCED_BUNDLE = ITEMS.register("reinforced_bundle", () -> new ModBundleItem(new Item.Properties(), 512));
    public static final RegistryObject<ArtifactItem> ARTIFACT = ITEMS.register("artifact", () -> new ArtifactItem(new Item.Properties().defaultDurability(1000)));


    public static final RegistryObject<Item> GUARD_SPAWN_EGG = ITEMS.register("guard_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.GUARD, 0xD57E36, 0x1D0D00, new Item.Properties()));

    public static final RegistryObject<BlockItem> EQUIPMENT_STATION_ITEM = ITEMS.register("equipment_station", () -> new BlockItem(ModBlocks.EQUIPMENT_STATION.get(),new Item.Properties()));
    public static final RegistryObject<BlockItem> ASSEMBLER_ITEM = ITEMS.register("assembler", () -> new BlockItem(ModBlocks.ASSEMBLER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> GENERATOR_ITEM = ITEMS.register("generator", () -> new BlockItem(ModBlocks.GENERATOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> QUARRY_ITEM = ITEMS.register("quarry", () -> new BlockItem(ModBlocks.QUARRY.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CRUSHER_ITEM = ITEMS.register("crusher", () -> new BlockItem(ModBlocks.CRUSHER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DISCOVERY_STATION_ITEM = ITEMS.register("discovery_station", () -> new BlockItem(ModBlocks.DISCOVERY_STATION.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> IMPROVED_BARREL_ITEM = ITEMS.register("improved_barrel", () -> new BlockItem(ModBlocks.IMPROVED_BARREL.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PROFOUND_BARREL_ITEM = ITEMS.register("profound_barrel", () -> new BlockItem(ModBlocks.PROFOUND_BARREL.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> REINFORCED_BARREL_ITEM = ITEMS.register("reinforced_barrel", () -> new BlockItem(ModBlocks.REINFORCED_BARREL.get(), new Item.Properties()));



    //VANILLA ITEMS

    public static final RegistryObject<Item> WOODEN_PICKAXE = VANILLA_ITEMS.register("wooden_pickaxe",
            () -> new PickaxeItem(ModTiers.WEAK_WOOD, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_AXE = VANILLA_ITEMS.register("wooden_axe",
            () -> new AxeItem(ModTiers.WEAK_WOOD, 6, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_SWORD = VANILLA_ITEMS.register("wooden_sword",
            () -> new SwordItem(ModTiers.WEAK_WOOD, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_SHOVEL = VANILLA_ITEMS.register("wooden_shovel",
            () -> new ShovelItem(ModTiers.WEAK_WOOD, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_HOE = VANILLA_ITEMS.register("wooden_hoe",
            () -> new HoeItem(ModTiers.WEAK_WOOD, 0, -3.0F, new Item.Properties()));

    public static final RegistryObject<Item> STONE_PICKAXE = VANILLA_ITEMS.register("stone_pickaxe",
            () -> new PickaxeItem(ModTiers.WEAK_STONE, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_AXE = VANILLA_ITEMS.register("stone_axe",
            () -> new AxeItem(ModTiers.WEAK_STONE, 7, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_SWORD = VANILLA_ITEMS.register("stone_sword",
            () -> new SwordItem(ModTiers.WEAK_STONE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_SHOVEL = VANILLA_ITEMS.register("stone_shovel",
            () -> new ShovelItem(ModTiers.WEAK_STONE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> STONE_HOE = VANILLA_ITEMS.register("stone_hoe",
            () -> new HoeItem(ModTiers.WEAK_STONE, -1, -2.0F, new Item.Properties()));

    public static final RegistryObject<ModPickaxe> GOLDEN_PICKAXE = VANILLA_ITEMS.register("golden_pickaxe",
            () -> new ModPickaxe(new Item.Properties(), 1));
    public static final RegistryObject<ModAxe> GOLDEN_AXE = VANILLA_ITEMS.register("golden_axe",
            () -> new ModAxe(new Item.Properties(),1));
    public static final RegistryObject<ModSword> GOLDEN_SWORD = VANILLA_ITEMS.register("golden_sword",
            () -> new ModSword(new Item.Properties(),1));
    public static final RegistryObject<ModShovel> GOLDEN_SHOVEL = VANILLA_ITEMS.register("golden_shovel",
            () -> new ModShovel(new Item.Properties(),1));
    public static final RegistryObject<ModHoe> GOLDEN_HOE = VANILLA_ITEMS.register("golden_hoe",
            () -> new ModHoe(new Item.Properties(),1));

    public static final RegistryObject<ModPickaxe> DIAMOND_PICKAXE = VANILLA_ITEMS.register("diamond_pickaxe",
            () -> new ModPickaxe(new Item.Properties(),2));
    public static final RegistryObject<ModAxe> DIAMOND_AXE = VANILLA_ITEMS.register("diamond_axe",
            () -> new ModAxe(new Item.Properties(),2));
    public static final RegistryObject<ModSword> DIAMOND_SWORD = VANILLA_ITEMS.register("diamond_sword",
            () -> new ModSword(new Item.Properties(),2));
    public static final RegistryObject<ModShovel> DIAMOND_SHOVEL = VANILLA_ITEMS.register("diamond_shovel",
            () -> new ModShovel(new Item.Properties(),2));
    public static final RegistryObject<ModHoe> DIAMOND_HOE = VANILLA_ITEMS.register("diamond_hoe",
            () -> new ModHoe(new Item.Properties(),2));

    public static final RegistryObject<ModPickaxe> NETHERITE_PICKAXE = VANILLA_ITEMS.register("netherite_pickaxe",
            () -> new ModPickaxe(new Item.Properties(), 3));
    public static final RegistryObject<ModAxe> NETHERITE_AXE = VANILLA_ITEMS.register("netherite_axe",
            () -> new ModAxe(new Item.Properties(),3));
    public static final RegistryObject<ModSword> NETHERITE_SWORD = VANILLA_ITEMS.register("netherite_sword",
            () -> new ModSword(new Item.Properties(),3));
    public static final RegistryObject<ModShovel> NETHERITE_SHOVEL = VANILLA_ITEMS.register("netherite_shovel",
            () -> new ModShovel(new Item.Properties(),3));
    public static final RegistryObject<ModHoe> NETHERITE_HOE = VANILLA_ITEMS.register("netherite_hoe",
            () -> new ModHoe(new Item.Properties(),3));

    public static final RegistryObject<ModPickaxe> IRON_PICKAXE = VANILLA_ITEMS.register("iron_pickaxe",
            () -> new ModPickaxe(new Item.Properties(), 1));
    public static final RegistryObject<ModAxe> IRON_AXE = VANILLA_ITEMS.register("iron_axe",
            () -> new ModAxe(new Item.Properties(),1));
    public static final RegistryObject<ModSword> IRON_SWORD = VANILLA_ITEMS.register("iron_sword",
            () -> new ModSword(new Item.Properties(),1));
    public static final RegistryObject<ModShovel> IRON_SHOVEL = VANILLA_ITEMS.register("iron_shovel",
            () -> new ModShovel(new Item.Properties(),1));
    public static final RegistryObject<ModHoe> IRON_HOE = VANILLA_ITEMS.register("iron_hoe",
            () -> new ModHoe(new Item.Properties(),1));

    public static final RegistryObject<ModMinecartItem> MINECART_ITEM = VANILLA_ITEMS.register("minecart",
            () -> new ModMinecartItem(AbstractMinecart.Type.RIDEABLE, (new Item.Properties()).stacksTo(1)));
}