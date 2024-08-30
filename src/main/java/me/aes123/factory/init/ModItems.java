package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.item.*;
import me.aes123.factory.item.recipe.DiscoveredRecipeItem;
import me.aes123.factory.item.recipe.UndiscoveredRecipeItem;
import me.aes123.factory.item.equipment.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    //ITEMS
    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food((new FoodProperties.Builder().alwaysEat()
                    .nutrition(3).saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3,2), 1)
                    .build()))));
    //public static final RegistryObject<CardItem> BLANK_CARD = ITEMS.register("blank_card",
    //        () -> new CardItem(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<UndiscoveredRecipeItem> UNDISCOVERED_RECIPE = ITEMS.register("undiscovered_recipe", () -> new UndiscoveredRecipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DiscoveredRecipeItem> DISCOVERED_RECIPE = ITEMS.register("discovered_recipe", () -> new DiscoveredRecipeItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<MoldItem> PICKAXE_MOLD = ITEMS.register("pickaxe_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> AXE_MOLD = ITEMS.register("axe_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> SWORD_MOLD = ITEMS.register("sword_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> HOE_MOLD = ITEMS.register("hoe_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> SHOVEL_MOLD = ITEMS.register("shovel_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> HAMMER_MOLD = ITEMS.register("hammer_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> PISTOL_MOLD = ITEMS.register("pistol_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> HELMET_MOLD = ITEMS.register("helmet_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> CHESTPLATE_MOLD = ITEMS.register("chestplate_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> LEGGINGS_MOLD = ITEMS.register("leggings_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<MoldItem> BOOTS_MOLD = ITEMS.register("boots_mold", () -> new MoldItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> EXPERTISE = ITEMS.register("expertise", () -> new Item(new Item.Properties()));
    public static final RegistryObject<ModBundleItem> IMPROVED_BUNDLE = ITEMS.register("improved_bundle", () -> new ModBundleItem(new Item.Properties(), 128));
    public static final RegistryObject<ModBundleItem> PROFOUND_BUNDLE = ITEMS.register("profound_bundle", () -> new ModBundleItem(new Item.Properties(), 256));
    public static final RegistryObject<ModBundleItem> REINFORCED_BUNDLE = ITEMS.register("reinforced_bundle", () -> new ModBundleItem(new Item.Properties(), 512));
    public static final RegistryObject<BoosterItem> WEAK_BOOSTER = ITEMS.register("weak_booster", () -> new BoosterItem(new Item.Properties().stacksTo(2)));
    public static final RegistryObject<BoosterItem> STRONG_BOOSTER = ITEMS.register("strong_booster", () -> new BoosterItem(new Item.Properties().stacksTo(4)));
    public static final RegistryObject<BoosterItem> REINFORCED_BOOSTER = ITEMS.register("reinforced_booster", () -> new BoosterItem(new Item.Properties().stacksTo(16)));
    //public static final RegistryObject<Item> RUBY_SHARD = ITEMS.register("ruby_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties()));


    public static final RegistryObject<BlockItem> EQUIPMENT_STATION_ITEM = ITEMS.register("equipment_station", () -> new BlockItem(ModBlocks.EQUIPMENT_STATION.get(),new Item.Properties()));
    public static final RegistryObject<BlockItem> ASSEMBLER_ITEM = ITEMS.register("assembler", () -> new BlockItem(ModBlocks.ASSEMBLER.get(), new Item.Properties()));
    //public static final RegistryObject<BlockItem> GENERATOR_ITEM = ITEMS.register("generator", () -> new BlockItem(ModBlocks.GENERATOR.get(), new Item.Properties()));
    //public static final RegistryObject<BlockItem> ELECTRIC_FURNACE_ITEM = ITEMS.register("electric_furnace", () -> new BlockItem(ModBlocks.ELECTRIC_FURNACE.get(), new Item.Properties()));
    //public static final RegistryObject<BlockItem> CRUSHER_ITEM = ITEMS.register("crusher", () -> new BlockItem(ModBlocks.CRUSHER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DISCOVERY_STATION_ITEM = ITEMS.register("discovery_station", () -> new BlockItem(ModBlocks.DISCOVERY_STATION.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> IMPROVED_BARREL_ITEM = ITEMS.register("improved_barrel", () -> new BlockItem(ModBlocks.IMPROVED_BARREL.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PROFOUND_BARREL_ITEM = ITEMS.register("profound_barrel", () -> new BlockItem(ModBlocks.PROFOUND_BARREL.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> REINFORCED_BARREL_ITEM = ITEMS.register("reinforced_barrel", () -> new BlockItem(ModBlocks.REINFORCED_BARREL.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RUBY_ORE_ITEM = ITEMS.register("ruby_ore", () -> new BlockItem(ModBlocks.RUBY_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> IMPROVED_HOPPER_ITEM = ITEMS.register("improved_hopper", () -> new BlockItem(ModBlocks.IMPROVED_HOPPER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PROFOUND_HOPPER_ITEM = ITEMS.register("profound_hopper", () -> new BlockItem(ModBlocks.PROFOUND_HOPPER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> REINFORCED_HOPPER_ITEM = ITEMS.register("reinforced_hopper", () -> new BlockItem(ModBlocks.REINFORCED_HOPPER.get(), new Item.Properties()));

    //public static final RegistryObject<BlockItem> GATE_BLOCK_ITEM = ITEMS.register("gate_block", () -> new BlockItem(ModBlocks.GATE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<ModHammer> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new ModHammer(new Item.Properties(), 2));
    public static final RegistryObject<ModHammer> GOLD_HAMMER = ITEMS.register("golden_hammer",
            () -> new ModHammer(new Item.Properties(), 1));
    public static final RegistryObject<ModHammer> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new ModHammer(new Item.Properties(), 3));
    public static final RegistryObject<ModHammer> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",
            () -> new ModHammer(new Item.Properties().fireResistant(), 4));

    public static final RegistryObject<ModGun> IRON_PISTOL = ITEMS.register("iron_pistol",
            () -> new ModGun(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<ModGun> GOLD_PISTOL = ITEMS.register("golden_pistol",
            () -> new ModGun(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<ModGun> DIAMOND_PISTOL = ITEMS.register("diamond_pistol",
            () -> new ModGun(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<ModGun> NETHERITE_PISTOL = ITEMS.register("netherite_pistol",
            () -> new ModGun(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> BULLET = ITEMS.register("bullet",
            () -> new Item(new Item.Properties()));

    //VANILLA ITEMS

    public static final RegistryObject<Item> TOTEM = VANILLA_ITEMS.register("totem_of_undying", () -> new Item(new Item.Properties()));
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
            () -> new ModSword(new Item.Properties()));
    public static final RegistryObject<ModShovel> GOLDEN_SHOVEL = VANILLA_ITEMS.register("golden_shovel",
            () -> new ModShovel(new Item.Properties(),1));
    public static final RegistryObject<ModHoe> GOLDEN_HOE = VANILLA_ITEMS.register("golden_hoe",
            () -> new ModHoe(new Item.Properties(),1));

    public static final RegistryObject<ModPickaxe> DIAMOND_PICKAXE = VANILLA_ITEMS.register("diamond_pickaxe",
            () -> new ModPickaxe(new Item.Properties(),3));
    public static final RegistryObject<ModAxe> DIAMOND_AXE = VANILLA_ITEMS.register("diamond_axe",
            () -> new ModAxe(new Item.Properties(),3));
    public static final RegistryObject<ModSword> DIAMOND_SWORD = VANILLA_ITEMS.register("diamond_sword",
            () -> new ModSword(new Item.Properties()));
    public static final RegistryObject<ModShovel> DIAMOND_SHOVEL = VANILLA_ITEMS.register("diamond_shovel",
            () -> new ModShovel(new Item.Properties(),3));
    public static final RegistryObject<ModHoe> DIAMOND_HOE = VANILLA_ITEMS.register("diamond_hoe",
            () -> new ModHoe(new Item.Properties(),3));

    public static final RegistryObject<ModPickaxe> NETHERITE_PICKAXE = VANILLA_ITEMS.register("netherite_pickaxe",
            () -> new ModPickaxe(new Item.Properties().fireResistant(), 4));
    public static final RegistryObject<ModAxe> NETHERITE_AXE = VANILLA_ITEMS.register("netherite_axe",
            () -> new ModAxe(new Item.Properties().fireResistant(),4));
    public static final RegistryObject<ModSword> NETHERITE_SWORD = VANILLA_ITEMS.register("netherite_sword",
            () -> new ModSword(new Item.Properties().fireResistant()));
    public static final RegistryObject<ModShovel> NETHERITE_SHOVEL = VANILLA_ITEMS.register("netherite_shovel",
            () -> new ModShovel(new Item.Properties().fireResistant(),4));
    public static final RegistryObject<ModHoe> NETHERITE_HOE = VANILLA_ITEMS.register("netherite_hoe",
            () -> new ModHoe(new Item.Properties().fireResistant(),4));

    public static final RegistryObject<ModPickaxe> IRON_PICKAXE = VANILLA_ITEMS.register("iron_pickaxe",
            () -> new ModPickaxe(new Item.Properties(), 2));
    public static final RegistryObject<ModAxe> IRON_AXE = VANILLA_ITEMS.register("iron_axe",
            () -> new ModAxe(new Item.Properties(),2));
    public static final RegistryObject<ModSword> IRON_SWORD = VANILLA_ITEMS.register("iron_sword",
            () -> new ModSword(new Item.Properties()));
    public static final RegistryObject<ModShovel> IRON_SHOVEL = VANILLA_ITEMS.register("iron_shovel",
            () -> new ModShovel(new Item.Properties(),2));
    public static final RegistryObject<ModHoe> IRON_HOE = VANILLA_ITEMS.register("iron_hoe",
            () -> new ModHoe(new Item.Properties(),2));

    public static final RegistryObject<ArmorItem> LEATHER_HELMET = VANILLA_ITEMS.register("leather_helmet",
            () -> new DyeableArmorItem(ModArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<ArmorItem> LEATHER_CHESTPLATE = VANILLA_ITEMS.register("leather_chestplate",
            () -> new DyeableArmorItem(ModArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<ArmorItem> LEATHER_LEGGINGS = VANILLA_ITEMS.register("leather_leggings",
            () -> new DyeableArmorItem(ModArmorMaterials.LEATHER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<ArmorItem> LEATHER_BOOTS = VANILLA_ITEMS.register("leather_boots",
            () -> new DyeableArmorItem(ModArmorMaterials.LEATHER, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<ArmorItem> CHAINMAIL_HELMET = VANILLA_ITEMS.register("chainmail_helmet",
            () -> new ArmorItem(ModArmorMaterials.CHAINMAIL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<ArmorItem> CHAINMAIL_CHESTPLATE = VANILLA_ITEMS.register("chainmail_chestplate",
            () -> new ArmorItem(ModArmorMaterials.CHAINMAIL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<ArmorItem> CHAINMAIL_LEGGINGS = VANILLA_ITEMS.register("chainmail_leggings",
            () -> new ArmorItem(ModArmorMaterials.CHAINMAIL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<ArmorItem> CHAINMAIL_BOOTS = VANILLA_ITEMS.register("chainmail_boots",
            () -> new ArmorItem(ModArmorMaterials.CHAINMAIL, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<ModArmor> IRON_HELMET = VANILLA_ITEMS.register("iron_helmet",
            () -> new ModArmor(ArmorItem.Type.HELMET, new Item.Properties(), SoundEvents.ARMOR_EQUIP_IRON, "iron"));
    public static final RegistryObject<ModArmor> IRON_CHESTPLATE = VANILLA_ITEMS.register("iron_chestplate",
            () -> new ModArmor(ArmorItem.Type.CHESTPLATE, new Item.Properties(), SoundEvents.ARMOR_EQUIP_IRON, "iron"));
    public static final RegistryObject<ModArmor> IRON_LEGGINGS = VANILLA_ITEMS.register("iron_leggings",
            () -> new ModArmor(ArmorItem.Type.LEGGINGS, new Item.Properties(), SoundEvents.ARMOR_EQUIP_IRON, "iron"));
    public static final RegistryObject<ModArmor> IRON_BOOTS = VANILLA_ITEMS.register("iron_boots",
            () -> new ModArmor(ArmorItem.Type.BOOTS, new Item.Properties(), SoundEvents.ARMOR_EQUIP_IRON, "iron"));

    public static final RegistryObject<ModArmor> GOLD_HELMET = VANILLA_ITEMS.register("golden_helmet",
            () -> new ModArmor(ArmorItem.Type.HELMET, new Item.Properties(), SoundEvents.ARMOR_EQUIP_GOLD, "golden"));
    public static final RegistryObject<ModArmor> GOLD_CHESTPLATE = VANILLA_ITEMS.register("golden_chestplate",
            () -> new ModArmor(ArmorItem.Type.CHESTPLATE, new Item.Properties(), SoundEvents.ARMOR_EQUIP_GOLD, "golden"));
    public static final RegistryObject<ModArmor> GOLD_LEGGINGS = VANILLA_ITEMS.register("golden_leggings",
            () -> new ModArmor(ArmorItem.Type.LEGGINGS, new Item.Properties(), SoundEvents.ARMOR_EQUIP_GOLD, "golden"));
    public static final RegistryObject<ModArmor> GOLD_BOOTS = VANILLA_ITEMS.register("golden_boots",
            () -> new ModArmor(ArmorItem.Type.BOOTS, new Item.Properties(), SoundEvents.ARMOR_EQUIP_GOLD, "golden"));

    public static final RegistryObject<ModArmor> DIAMOND_HELMET = VANILLA_ITEMS.register("diamond_helmet",
            () -> new ModArmor(ArmorItem.Type.HELMET, new Item.Properties(), SoundEvents.ARMOR_EQUIP_DIAMOND, "diamond"));
    public static final RegistryObject<ModArmor> DIAMOND_CHESTPLATE = VANILLA_ITEMS.register("diamond_chestplate",
            () -> new ModArmor(ArmorItem.Type.CHESTPLATE, new Item.Properties(), SoundEvents.ARMOR_EQUIP_DIAMOND, "diamond"));
    public static final RegistryObject<ModArmor> DIAMOND_LEGGINGS = VANILLA_ITEMS.register("diamond_leggings",
            () -> new ModArmor(ArmorItem.Type.LEGGINGS, new Item.Properties(), SoundEvents.ARMOR_EQUIP_DIAMOND, "diamond"));
    public static final RegistryObject<ModArmor> DIAMOND_BOOTS = VANILLA_ITEMS.register("diamond_boots",
            () -> new ModArmor(ArmorItem.Type.BOOTS, new Item.Properties(), SoundEvents.ARMOR_EQUIP_DIAMOND, "diamond"));

    public static final RegistryObject<ModArmor> NETHERITE_HELMET = VANILLA_ITEMS.register("netherite_helmet",
            () -> new ModArmor(ArmorItem.Type.HELMET, new Item.Properties().fireResistant(), SoundEvents.ARMOR_EQUIP_NETHERITE, "netherite"));
    public static final RegistryObject<ModArmor> NETHERITE_CHESTPLATE = VANILLA_ITEMS.register("netherite_chestplate",
            () -> new ModArmor(ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant(), SoundEvents.ARMOR_EQUIP_NETHERITE, "netherite"));
    public static final RegistryObject<ModArmor> NETHERITE_LEGGINGS = VANILLA_ITEMS.register("netherite_leggings",
            () -> new ModArmor(ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant(), SoundEvents.ARMOR_EQUIP_NETHERITE, "netherite"));
    public static final RegistryObject<ModArmor> NETHERITE_BOOTS = VANILLA_ITEMS.register("netherite_boots",
            () -> new ModArmor(ArmorItem.Type.BOOTS, new Item.Properties().fireResistant(), SoundEvents.ARMOR_EQUIP_NETHERITE, "netherite"));


    public static final RegistryObject<ModMinecartItem> MINECART_ITEM = VANILLA_ITEMS.register("minecart",
            () -> new ModMinecartItem(AbstractMinecart.Type.RIDEABLE, (new Item.Properties()).stacksTo(1)));
}
