package me.aes123.factory.event;

import me.aes123.factory.Main;
import me.aes123.factory.config.AttributeConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.loading.FMLPaths;

//@Mod(Main.MODID)
@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LoadEvents {
    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {

        AttributeConfig.load(FMLPaths.CONFIGDIR.get().resolve(Main.MODID + ".json").toFile()).applyChanges();
    }
}
