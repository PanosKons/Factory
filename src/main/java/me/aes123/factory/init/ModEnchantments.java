package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.enchantment.SoulboundEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MODID);

    public static RegistryObject<Enchantment> SOULBOUND = ENCHANTMENTS.register("souldbound", () -> new SoulboundEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE ,EquipmentSlot.values()));
}
