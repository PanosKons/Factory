package me.aes123.factory.util;

import me.aes123.factory.Main;
import me.aes123.factory.init.ModEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
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
                Enchantments.FIRE_ASPECT,
                Enchantments.LOYALTY,
                Enchantments.RIPTIDE,
                Enchantments.CHANNELING,
                ModEnchantments.SOULBOUND.get()
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
}
