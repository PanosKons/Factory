package me.aes123.factory.util;

import me.aes123.factory.Main;
import me.aes123.factory.init.ModEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModTags {
    public static class Blocks
    {

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Main.MODID, name));
        }
    }
    public static class Items
    {
        public static final List<ItemStack> UndiscoveredRecipeItems = List.of(
                new ItemStack(net.minecraft.world.item.Items.DIAMOND, 2),
                new ItemStack(net.minecraft.world.item.Items.NETHERITE_SCRAP, 1),
                new ItemStack(net.minecraft.world.item.Items.SUGAR, 12),
                new ItemStack(net.minecraft.world.item.Items.SADDLE, 1),
                new ItemStack(net.minecraft.world.item.Items.BONE, 16),
                new ItemStack(net.minecraft.world.item.Items.BONE, 15),
                new ItemStack(net.minecraft.world.item.Items.BONE, 18),
                new ItemStack(net.minecraft.world.item.Items.WHITE_WOOL, 4),
                new ItemStack(net.minecraft.world.item.Items.STICK, 6),
                new ItemStack(net.minecraft.world.item.Items.EMERALD, 2),
                new ItemStack(net.minecraft.world.item.Items.SUGAR, 12),
                new ItemStack(net.minecraft.world.item.Items.SADDLE, 1),
                new ItemStack(net.minecraft.world.item.Items.BONE, 16),
                new ItemStack(net.minecraft.world.item.Items.BONE, 15),
                new ItemStack(net.minecraft.world.item.Items.BONE, 18),
                new ItemStack(net.minecraft.world.item.Items.WHITE_WOOL, 4),
                new ItemStack(net.minecraft.world.item.Items.STICK, 6),
                new ItemStack(net.minecraft.world.item.Items.EMERALD, 2),
                new ItemStack(net.minecraft.world.item.Items.HEART_OF_THE_SEA, 1),
                new ItemStack(net.minecraft.world.item.Items.DRAGON_HEAD, 1),
                new ItemStack(net.minecraft.world.item.Items.BAMBOO, 12),
                new ItemStack(net.minecraft.world.item.Items.DIAMOND_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.DIAMOND_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.DIAMOND_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.GOLDEN_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.GOLDEN_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.GOLDEN_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.IRON_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.IRON_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.IRON_HORSE_ARMOR, 1),
                new ItemStack(net.minecraft.world.item.Items.NAME_TAG, 1),
                new ItemStack(net.minecraft.world.item.Items.STRING, 16),
                new ItemStack(net.minecraft.world.item.Items.STRING, 18),
                new ItemStack(net.minecraft.world.item.Items.STRING, 12),
                new ItemStack(net.minecraft.world.item.Items.IRON_INGOT, 7),
                new ItemStack(net.minecraft.world.item.Items.STRING, 16),
                new ItemStack(net.minecraft.world.item.Items.BONE, 16),
                new ItemStack(net.minecraft.world.item.Items.PUMPKIN, 10),
                new ItemStack(net.minecraft.world.item.Items.MELON, 10),
                new ItemStack(net.minecraft.world.item.Items.DAMAGED_ANVIL, 1),
                new ItemStack(net.minecraft.world.item.Items.DAMAGED_ANVIL, 1),
                new ItemStack(net.minecraft.world.item.Items.HAY_BLOCK, 8),
                new ItemStack(net.minecraft.world.item.Items.HAY_BLOCK, 13),
                new ItemStack(net.minecraft.world.item.Items.CACTUS, 14),
                new ItemStack(net.minecraft.world.item.Items.CACTUS, 12),
                new ItemStack(net.minecraft.world.item.Items.FEATHER, 2),
                new ItemStack(net.minecraft.world.item.Items.FEATHER, 4),
                new ItemStack(net.minecraft.world.item.Items.STRING, 16),
                new ItemStack(net.minecraft.world.item.Items.STRING, 18),
                new ItemStack(net.minecraft.world.item.Items.STRING, 12),
                new ItemStack(net.minecraft.world.item.Items.IRON_INGOT, 7),
                new ItemStack(net.minecraft.world.item.Items.STRING, 16),
                new ItemStack(net.minecraft.world.item.Items.BONE, 16),
                new ItemStack(net.minecraft.world.item.Items.PUMPKIN, 10),
                new ItemStack(net.minecraft.world.item.Items.MELON, 10),
                new ItemStack(net.minecraft.world.item.Items.DAMAGED_ANVIL, 1),
                new ItemStack(net.minecraft.world.item.Items.DAMAGED_ANVIL, 1),
                new ItemStack(net.minecraft.world.item.Items.HAY_BLOCK, 8),
                new ItemStack(net.minecraft.world.item.Items.HAY_BLOCK, 13),
                new ItemStack(net.minecraft.world.item.Items.CACTUS, 14),
                new ItemStack(net.minecraft.world.item.Items.CACTUS, 12),
                new ItemStack(net.minecraft.world.item.Items.FEATHER, 2),
                new ItemStack(net.minecraft.world.item.Items.FEATHER, 4),
                new ItemStack(net.minecraft.world.item.Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 1),
                new ItemStack(net.minecraft.world.item.Items.CHAIN, 4),
                new ItemStack(net.minecraft.world.item.Items.BELL, 2),
                new ItemStack(net.minecraft.world.item.Items.BELL, 1),
                new ItemStack(net.minecraft.world.item.Items.BELL, 2),
                new ItemStack(net.minecraft.world.item.Items.BELL, 1),
                new ItemStack(net.minecraft.world.item.Items.BLAZE_ROD, 3),
                new ItemStack(net.minecraft.world.item.Items.POISONOUS_POTATO, 2),
                new ItemStack(net.minecraft.world.item.Items.ENDER_EYE, 2),
                new ItemStack(net.minecraft.world.item.Items.ENDER_PEARL, 2),
                new ItemStack(net.minecraft.world.item.Items.ENDER_PEARL, 10),
                new ItemStack(net.minecraft.world.item.Items.FEATHER, 2),
                new ItemStack(net.minecraft.world.item.Items.KELP, 22),
                new ItemStack(net.minecraft.world.item.Items.KELP, 18),
                new ItemStack(net.minecraft.world.item.Items.BLUE_CANDLE, 6),
                new ItemStack(net.minecraft.world.item.Items.HONEYCOMB, 3),
                new ItemStack(net.minecraft.world.item.Items.HONEY_BOTTLE, 3),
                new ItemStack(net.minecraft.world.item.Items.FEATHER, 2),
                new ItemStack(net.minecraft.world.item.Items.KELP, 22),
                new ItemStack(net.minecraft.world.item.Items.KELP, 18),
                new ItemStack(net.minecraft.world.item.Items.BLUE_CANDLE, 6),
                new ItemStack(net.minecraft.world.item.Items.HONEYCOMB, 3),
                new ItemStack(net.minecraft.world.item.Items.HONEY_BOTTLE, 3),
                new ItemStack(net.minecraft.world.item.Items.SCULK, 9),
                new ItemStack(net.minecraft.world.item.Items.SCULK_SENSOR, 4),
                new ItemStack(net.minecraft.world.item.Items.REDSTONE, 22),
                new ItemStack(net.minecraft.world.item.Items.REDSTONE, 19),
                new ItemStack(net.minecraft.world.item.Items.AMETHYST_SHARD, 6),
                new ItemStack(net.minecraft.world.item.Items.AMETHYST_SHARD, 8),
                new ItemStack(net.minecraft.world.item.Items.SOUL_LANTERN, 8),
                new ItemStack(net.minecraft.world.item.Items.SOUL_LANTERN, 6),
                new ItemStack(net.minecraft.world.item.Items.CARROT, 12),
                new ItemStack(net.minecraft.world.item.Items.CARROT, 10),
                new ItemStack(net.minecraft.world.item.Items.CARROT, 22),
                new ItemStack(net.minecraft.world.item.Items.CARROT, 30),
                new ItemStack(net.minecraft.world.item.Items.CARROT, 5),
                new ItemStack(net.minecraft.world.item.Items.WHEAT_SEEDS, 40),
                new ItemStack(net.minecraft.world.item.Items.CARROT, 54),
                new ItemStack(net.minecraft.world.item.Items.POTATO, 12),
                new ItemStack(net.minecraft.world.item.Items.POTATO, 10),
                new ItemStack(net.minecraft.world.item.Items.POTATO, 22),
                new ItemStack(net.minecraft.world.item.Items.POTATO, 30),
                new ItemStack(net.minecraft.world.item.Items.WHEAT_SEEDS, 5),
                new ItemStack(net.minecraft.world.item.Items.POTATO, 40),
                new ItemStack(net.minecraft.world.item.Items.POTATO, 54),
                new ItemStack(net.minecraft.world.item.Items.BEETROOT, 12),
                new ItemStack(net.minecraft.world.item.Items.WHEAT_SEEDS, 10),
                new ItemStack(net.minecraft.world.item.Items.BEETROOT, 22),
                new ItemStack(net.minecraft.world.item.Items.BEETROOT, 30),
                new ItemStack(net.minecraft.world.item.Items.BEETROOT, 5),
                new ItemStack(net.minecraft.world.item.Items.WHEAT_SEEDS, 40),
                new ItemStack(net.minecraft.world.item.Items.BEETROOT, 54),
                new ItemStack(net.minecraft.world.item.Items.WHEAT, 12),
                new ItemStack(net.minecraft.world.item.Items.WHEAT, 10),
                new ItemStack(net.minecraft.world.item.Items.WHEAT, 22),
                new ItemStack(net.minecraft.world.item.Items.WHEAT, 30),
                new ItemStack(net.minecraft.world.item.Items.WHEAT_SEEDS, 5),
                new ItemStack(net.minecraft.world.item.Items.WHEAT, 40),
                new ItemStack(net.minecraft.world.item.Items.WHEAT, 54),
                new ItemStack(net.minecraft.world.item.Items.PAPER, 3),
                new ItemStack(net.minecraft.world.item.Items.LIGHT_BLUE_STAINED_GLASS, 12),
                new ItemStack(net.minecraft.world.item.Items.GLASS, 12),
                new ItemStack(net.minecraft.world.item.Items.POPPY, 12),
                new ItemStack(net.minecraft.world.item.Items.DANDELION, 12),
                new ItemStack(net.minecraft.world.item.Items.SWEET_BERRIES, 17),
                new ItemStack(net.minecraft.world.item.Items.PAPER, 3),
                new ItemStack(net.minecraft.world.item.Items.LIGHT_BLUE_STAINED_GLASS, 12),
                new ItemStack(net.minecraft.world.item.Items.GLASS, 12),
                new ItemStack(net.minecraft.world.item.Items.POPPY, 12),
                new ItemStack(net.minecraft.world.item.Items.DANDELION, 12),
                new ItemStack(net.minecraft.world.item.Items.SWEET_BERRIES, 17),
                new ItemStack(net.minecraft.world.item.Items.TURTLE_EGG, 1),
                new ItemStack(net.minecraft.world.item.Items.SNIFFER_EGG, 1)
        );
        public static final List<Enchantment> AllowedEnchantments = List.of(
                Enchantments.FALL_PROTECTION,
                Enchantments.RESPIRATION,
                Enchantments.AQUA_AFFINITY,
                Enchantments.DEPTH_STRIDER,
                Enchantments.FROST_WALKER,
                Enchantments.BINDING_CURSE,
                Enchantments.VANISHING_CURSE,
                Enchantments.SOUL_SPEED,
                Enchantments.SWIFT_SNEAK,
                Enchantments.LOYALTY,
                Enchantments.RIPTIDE,
                Enchantments.CHANNELING,
                ModEnchantments.SOULBOUND.get(),
                ModEnchantments.FROST_ASPECT.get(),
                ModEnchantments.LEACH.get(),
                Enchantments.FIRE_PROTECTION,
                ModEnchantments.HEALTH_BOOST.get(),
                ModEnchantments.TELEPORT.get(),
                ModEnchantments.CHARGE.get()

        );
        public static TagKey<Item> BOOSTER_ITEMS = tag("booster_items");
        public static TagKey<Item> FORBIDDEN_ITEMS = tag("forbidden_items");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Main.MODID, name));
        }
    }
    public static List<String> COMMON_CHESTS = new ArrayList<>();
    public static List<String> RARE_CHESTS = new ArrayList<>();
    public static List<String> EPIC_CHESTS = new ArrayList<>();
    public static List<String> LEGENDARY_CHESTS = new ArrayList<>();
    public static List<String> ARCHAEOLOGY = new ArrayList<>();
}
