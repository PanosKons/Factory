package me.aes123.factory;

import me.aes123.factory.entity.client.GuardRenderer;
import me.aes123.factory.init.*;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "factory";

    public Main()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        //THE ORDER IS IMPORTANT
        ModItems.ITEMS.register(bus);
        ModItems.VANILLA_ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlocks.VANILLA_BLOCKS.register(bus);
        ModMenuTypes.MENUS.register(bus);
        ModBlockEntityType.BLOCK_ENTITIES.register(bus);
        ModRecipes.SERIALIZERS.register(bus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(bus);
        ModLootModifiers.LOOT_MODIFIER_SERIALIZERS.register(bus);
        ModEntityTypes.ENTITIES.register(bus);
        ModEntityTypes.VANILLA_ENTITIES.register(bus);

        GeckoLib.initialize();
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.GUARD.get(), GuardRenderer::new);
            EntityRenderers.register(ModEntityTypes.MINECART.get(), (p_174070_) -> new MinecartRenderer<>(p_174070_, ModelLayers.MINECART));
        }
    }
}