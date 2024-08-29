package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.enchantment.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MODID);

    public static RegistryObject<Enchantment> SOULBOUND = ENCHANTMENTS.register("soulbound", () -> new SoulboundEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE ,EquipmentSlot.values()));
    public static RegistryObject<Enchantment> FROST_ASPECT = ENCHANTMENTS.register("frost_aspect", () -> new FrostEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON ,EquipmentSlot.values()));
    public static RegistryObject<Enchantment> LEACH = ENCHANTMENTS.register("leach", () -> new LeachEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON ,EquipmentSlot.values()));
    public static RegistryObject<Enchantment> HEALTH_BOOST = ENCHANTMENTS.register("health_boost", () -> new HealthBoostEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR ,EquipmentSlot.values()));
    public static RegistryObject<Enchantment> TELEPORT = ENCHANTMENTS.register("teleport", () -> new TeleportEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON ,EquipmentSlot.values()));
    public static RegistryObject<Enchantment> CHARGE = ENCHANTMENTS.register("charge", () -> new ChargeEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE ,EquipmentSlot.values()));
}
