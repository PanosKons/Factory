package me.aes123.factory.event;

import me.aes123.factory.Main;
import me.aes123.factory.client.XPHudOverlay;
import me.aes123.factory.init.ModMenuTypes;
import me.aes123.factory.screen.*;
import me.aes123.factory.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents
    {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.SHOP_KEY.consumeClick())
            {
                var minecraft = Minecraft.getInstance();
                if(minecraft.screen == null)
                {
                    minecraft.setScreen(new ShopScreen());
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SHOP_KEY);
        }
        @SubscribeEvent
        public static void RegisterGuiOverlays(RegisterGuiOverlaysEvent event)
        {
            event.registerAboveAll("xp_overlay", XPHudOverlay.HUD_XP);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.ASSEMBLER_MENU.get(), AssemblerScreen::new);
            MenuScreens.register(ModMenuTypes.DISCOVERY_STATION_MENU.get(), DiscoveryStationScreen::new);
            MenuScreens.register(ModMenuTypes.EQUIPMENT_STATION_MENU.get(), EquipmentStationScreen::new);
            MenuScreens.register(ModMenuTypes.CRUSHER_MENU.get(), CrusherScreen::new);
            MenuScreens.register(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.ELECTRIC_FURNACE_MENU.get(), ElectricFurnaceScreen::new);
            MenuScreens.register(ModMenuTypes.ENCHANTMENT_MENU.get(), ModEnchantmentScreen::new);
        }
    }
}
