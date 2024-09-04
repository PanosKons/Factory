package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.screen.*;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MODID);

    public static final RegistryObject<MenuType<AssemblerMenu>> ASSEMBLER_MENU =
            registerMenuType(AssemblerMenu::new, "assembler_menu");
    public static final RegistryObject<MenuType<DiscoveryStationMenu>> DISCOVERY_STATION_MENU =
            registerMenuType(DiscoveryStationMenu::new, "discovery_station_menu");
    public static final RegistryObject<MenuType<EquipmentStationMenu>> EQUIPMENT_STATION_MENU =
            registerMenuType(EquipmentStationMenu::new, "equipment_station_menu");
    public static final RegistryObject<MenuType<ModEnchantmentMenu>> ENCHANTMENT_MENU =
            registerMenuType(ModEnchantmentMenu::new, "enchanting_table_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

}
