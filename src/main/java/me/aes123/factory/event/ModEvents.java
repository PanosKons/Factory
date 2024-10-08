package me.aes123.factory.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.aes123.factory.Main;
import me.aes123.factory.blockentity.ModBarrelBlockEntity;
import me.aes123.factory.data.EquipmentMaterialModifier;
import me.aes123.factory.data.EquipmentModifier;
import me.aes123.factory.init.ModAttributes;
import me.aes123.factory.init.ModEnchantments;
import me.aes123.factory.item.ModBundleItem;
import me.aes123.factory.item.equipment.ModHammer;
import me.aes123.factory.item.equipment.base.IEquipmentItem;
import me.aes123.factory.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static me.aes123.factory.data.EquipmentMaterialModifier.EQUIPMENT_MATERIAL_MODIFIERS;


@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModEvents {

    private static EquipmentMaterialModifier deserializeMaterialModifiers(JsonElement json)
    {
        JsonObject obj = json.getAsJsonObject();
        ResourceLocation location = new ResourceLocation(obj.get("item").getAsString());
        if(ForgeRegistries.ITEMS.containsKey(location))
        {
            Item item = ForgeRegistries.ITEMS.getValue(location);
            List<EquipmentModifier> modifiers = new ArrayList<>();
            JsonArray array = obj.get("modifiers").getAsJsonArray();
            for(int i = 0; i < array.size(); i++)
            {
                var modifierobj = array.get(i).getAsJsonObject();
                String modifier = modifierobj.get("id").getAsString().toUpperCase();
                EquipmentModifier.EquipmentModifierType modifierType = EquipmentModifier.EquipmentModifierType.valueOf(modifier);
                int level = modifierobj.get("level").getAsInt();
                modifiers.add(new EquipmentModifier(modifierType, level));
            }
            return new EquipmentMaterialModifier(item, modifiers);
        }

        return null;
    }

    @SubscribeEvent
    public static void onReload(AddReloadListenerEvent event)
    {
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(ModEvents.class.getClassLoader()
                        .getResourceAsStream("data/factory/custom/loot_table_tags/chest_common.json")), StandardCharsets.UTF_8)));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.COMMON_CHESTS.add(entry.getAsString());
                }
            }
            {

                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(ModEvents.class.getClassLoader()
                        .getResourceAsStream("data/factory/custom/loot_table_tags/chest_rare.json")), StandardCharsets.UTF_8)));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.RARE_CHESTS.add(entry.getAsString());
                }
            }
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(ModEvents.class.getClassLoader()
                        .getResourceAsStream("data/factory/custom/loot_table_tags/chest_epic.json")), StandardCharsets.UTF_8)));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.EPIC_CHESTS.add(entry.getAsString());
                }
            }
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(ModEvents.class.getClassLoader()
                        .getResourceAsStream("data/factory/custom/loot_table_tags/chest_legendary.json")), StandardCharsets.UTF_8)));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.LEGENDARY_CHESTS.add(entry.getAsString());
                }
            }
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(ModEvents.class.getClassLoader()
                        .getResourceAsStream("data/factory/custom/loot_table_tags/archaeology.json")), StandardCharsets.UTF_8)));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.ARCHAEOLOGY.add(entry.getAsString());
                }
            }
            {
                EQUIPMENT_MATERIAL_MODIFIERS = new ArrayList<>();

                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(ModEvents.class.getClassLoader()
                        .getResourceAsStream("data/factory/custom/materials/material_modifiers.json")), StandardCharsets.UTF_8)));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    var a = deserializeMaterialModifiers(entry);
                    if(a != null)
                    {
                        EQUIPMENT_MATERIAL_MODIFIERS.add(a);
                    }
                }
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

    public static int t = 0;
    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent e)
    {
        t++;
        if(t >= 20 * 5)
        {
            var list = e.getServer().getPlayerList().getPlayers();
            for(Player player : list){
            for (int i = 0; i < 9; i++)
            {
                ItemStack stack = player.getInventory().getItem(i);
                var enchantments = stack.getAllEnchantments();
                if(enchantments.containsKey(ModEnchantments.CHARGE.get()))
                {
                    int lvl = enchantments.get(ModEnchantments.CHARGE.get());
                    stack.getTag().putFloat("charge", Math.min(stack.getTag().getFloat("charge") + lvl, 6 * lvl));
                }
            }
            }
            t = 0;
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
            if(stack.getItem() instanceof ModBundleItem && ModBundleItem.containsItem(stack, stackToAdd)) {
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
    @SubscribeEvent
    public static void regenerationAttribute(TickEvent.PlayerTickEvent event) {
        double value = event.player.getAttributeValue(ModAttributes.REGENERATION.get());
        event.player.heal((float)value / 40);
    }
    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event)
    {
        ItemStack stack = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
        if(stack.getItem() instanceof IEquipmentItem item)
        {
            float xpboost = item.getModifierValue(EquipmentModifier.EquipmentModifierType.XP_BOOST, stack);
            if(xpboost > 0)
            {
                event.setExpToDrop((int)(event.getExpToDrop() * (xpboost/100.0f + 1)));
            }
        }
    }
    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event)
    {
        if(event.getSource().getEntity() instanceof Player player)
        {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(stack.getItem() instanceof IEquipmentItem item)
            {
                float xpboost = item.getModifierValue(EquipmentModifier.EquipmentModifierType.XP_BOOST, stack);
                if(xpboost > 0 && player.level() instanceof ServerLevel level)
                {
                    ExperienceOrb.award(level, event.getEntity().getPosition(1), (int)(event.getEntity().getExperienceReward() * (xpboost/100.0f + 1)));
                }
            }
        }
    }
    @SubscribeEvent
    public static void onEatFood(LivingEntityUseItemEvent.Finish event) {
        if (null != event.getItem().getFoodProperties(event.getEntity())) {
            int nutrition = event.getItem().getFoodProperties(event.getEntity()).getNutrition();
            if (nutrition > 0) {
                event.getEntity().heal(nutrition / 4.0f);
            }
        }
    }

}
