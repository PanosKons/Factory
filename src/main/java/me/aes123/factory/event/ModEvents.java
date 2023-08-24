package me.aes123.factory.event;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.aes123.factory.Main;
import me.aes123.factory.block.entity.ModBarrelBlockEntity;
import me.aes123.factory.item.ModBundleItem;
import me.aes123.factory.item.equipment.ModHammer;
import me.aes123.factory.util.ILevelRenderer;
import me.aes123.factory.util.ModTags;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onReload(AddReloadListenerEvent event)
    {
        try {
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("factory:custom/loot_table_tags/chest_common.json")).get().open())));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.COMMON_CHESTS.add(entry.getAsString());
                }
            }
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("factory:custom/loot_table_tags/chest_rare.json")).get().open())));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.RARE_CHESTS.add(entry.getAsString());
                }
            }
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("factory:custom/loot_table_tags/chest_epic.json")).get().open())));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.EPIC_CHESTS.add(entry.getAsString());
                }
            }

        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        // new MerchantOffer(Payment, product, maxUses, VillagerXp, villager_discounts);
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).clear();
            trades.get(2).clear();
            trades.get(3).clear();
            trades.get(4).clear();
            trades.get(5).clear();
        }
        if (event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).clear();
            trades.get(2).clear();
            trades.get(3).clear();
            trades.get(4).clear();
            trades.get(5).clear();
        }
    }
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

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof ModHammer hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initalBlockPos = event.getPos();

            if(!hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(initalBlockPos))) return;

            if (HARVESTED_BLOCKS.contains(initalBlockPos)) {
                return;
            }

            for (BlockPos pos : hammer.getBlocksToBeDestroyed(mainHandItem, initalBlockPos, serverPlayer, event.getLevel())) {
                // Have to add them to a Set otherwise, the same code right here will get called for each block!
                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }
    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {

        Inventory inv = event.getEntity().getInventory();
        int insertCount = 0;
        ItemStack stackToAdd = event.getStack().copy();
        for(int i = 0; i < inv.getContainerSize(); i++)
        {
            ItemStack stack = inv.getItem(i);
            if(stack.getItem() instanceof ModBundleItem) {
                int count = ModBundleItem.add(stack, stackToAdd);
                stackToAdd.shrink(count);
                insertCount += count;
            }
        }
        for(int i = 0; i < inv.getContainerSize(); i++)
        {
            if(ModBarrelBlockEntity.sameItem(inv.getItem(i), event.getStack()))
            {
                inv.getItem(i).shrink(insertCount);
            }
        }
    }
    @SubscribeEvent
    public static void getDigSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);
        if(stack.getItem() instanceof ModHammer hammer)
        {
            hammer.setDestroySpeedEvent(event, stack);
        }
    }
}
