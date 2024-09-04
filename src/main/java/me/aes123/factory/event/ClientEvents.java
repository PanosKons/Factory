package me.aes123.factory.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.aes123.factory.Main;
import me.aes123.factory.init.ModMenuTypes;
import me.aes123.factory.item.equipment.ModHammer;
import me.aes123.factory.screen.*;
import me.aes123.factory.util.ILevelRenderer;
import me.aes123.factory.util.KeyBinding;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents
    {
        @SubscribeEvent
        public static void blockHighLight(RenderHighlightEvent.Block event) {
            if(event.getCamera().getEntity() instanceof Player player && player.getInventory().getSelected().getItem() instanceof ModHammer hammer)
            {
                ILevelRenderer renderer = ((ILevelRenderer)(event.getLevelRenderer()));
                Level level = renderer.getLevel();
                var renderBuffers = renderer.getRenderBuffers();
                if(!hammer.isCorrectToolForDrops(player.getInventory().getSelected(), level.getBlockState(event.getTarget().getBlockPos()))) return;
                Vec3 vec3 = event.getCamera().getPosition();
                double d0 = vec3.x();
                double d1 = vec3.y();
                double d2 = vec3.z();
                var progress = renderer.getBlockDestructionProgresses().get(player.getId());
                int k = progress == null ? -1 : progress.getProgress();
                System.out.println(k);
                for(BlockPos blockPos : hammer.getBlocksToBeDestroyed(player.getInventory().getSelected(), event.getTarget().getBlockPos(), player, level))
                {
                    BlockState state = level.getBlockState(blockPos);
                    if (!state.isAir() && level.getWorldBorder().isWithinBounds(blockPos)) {
                        VertexConsumer vertexconsumer2 = event.getMultiBufferSource().getBuffer(RenderType.lines());
                        Camera cam = event.getCamera();
                        renderer.hitOutline(event.getPoseStack(), vertexconsumer2, cam.getEntity(), cam.getPosition().x(), cam.getPosition().y(), cam.getPosition().z(), blockPos, state);

                        if(k >= 0) {
                            event.getPoseStack().pushPose();
                            event.getPoseStack().translate((double) blockPos.getX() - d0, (double) blockPos.getY() - d1, (double) blockPos.getZ() - d2);
                            PoseStack.Pose posestack$pose1 = event.getPoseStack().last();
                            VertexConsumer vertexconsumer1 = new SheetedDecalTextureGenerator(renderBuffers.crumblingBufferSource().getBuffer(ModelBakery.DESTROY_TYPES.get(k)), posestack$pose1.pose(), posestack$pose1.normal(), 1.0F);
                            net.minecraftforge.client.model.data.ModelData modelData = level.getModelDataManager().getAt(blockPos);
                            Minecraft.getInstance().getBlockRenderer().renderBreakingTexture(state, blockPos, level, event.getPoseStack(), vertexconsumer1, modelData == null ? net.minecraftforge.client.model.data.ModelData.EMPTY : modelData);
                            event.getPoseStack().popPose();
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            /*if(KeyBinding.SHOP_KEY.consumeClick())
            {
                var minecraft = Minecraft.getInstance();
                if(minecraft.screen == null)
                {
                    minecraft.setScreen(new ShopScreen());
                }
            }*/
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SHOP_KEY);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.ASSEMBLER_MENU.get(), AssemblerScreen::new);
            MenuScreens.register(ModMenuTypes.DISCOVERY_STATION_MENU.get(), DiscoveryStationScreen::new);
            MenuScreens.register(ModMenuTypes.EQUIPMENT_STATION_MENU.get(), EquipmentStationScreen::new);
            MenuScreens.register(ModMenuTypes.ENCHANTMENT_MENU.get(), ModEnchantmentScreen::new);
        }
    }
}
