package me.aes123.factory;

import com.mojang.logging.LogUtils;
import me.aes123.factory.client.ModEnchantTableRenderer;
import me.aes123.factory.dungeon.Dungeon;
import me.aes123.factory.entity.client.GuardRenderer;
import me.aes123.factory.init.*;
import me.aes123.factory.item.ModBundleItem;
import me.aes123.factory.networking.ModMessages;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.targets.FMLServerLaunchHandler;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.text.DecimalFormat;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "factory";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DecimalFormat df = new DecimalFormat();
    public Main()
    {
        df.setMaximumFractionDigits(2);

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
        ModAttributes.ATTRIBUTES.register(bus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.addListener(Dungeon::init);
        bus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        ModMessages.register();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        static void bundleProperty(Item item)
        {
            ItemProperties.register(item,
                    new ResourceLocation("minecraft", "filled"), (stack, level, living, id) -> ModBundleItem.getFullnessDisplay(stack));
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            event.enqueueWork(() ->
            {
                bundleProperty(ModItems.IMPROVED_BUNDLE.get());
                bundleProperty(ModItems.PROFOUND_BUNDLE.get());
                bundleProperty(ModItems.REINFORCED_BUNDLE.get());
            });

            EntityRenderers.register(ModEntityTypes.GUARD.get(), GuardRenderer::new);
            BlockEntityRenderers.register(ModBlockEntityType.ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), ModEnchantTableRenderer::new);
            EntityRenderers.register(ModEntityTypes.MINECART.get(), (p_174070_) -> new MinecartRenderer<>(p_174070_, ModelLayers.MINECART));
        }
    }
}