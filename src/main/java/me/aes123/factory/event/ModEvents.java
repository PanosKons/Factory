package me.aes123.factory.event;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.aes123.factory.Main;
import me.aes123.factory.entity.GuardEntity;
import me.aes123.factory.init.ModEntityTypes;
import me.aes123.factory.util.EquipmentStationMaterial;
import me.aes123.factory.util.EquipmentStationModifier;
import me.aes123.factory.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onReload(AddReloadListenerEvent event)
    {
        try {
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("factory:custom/equipment_station/modifiers.json")).get().open()));
                JsonObject data = GsonHelper.parse(reader);

                Map<Item, EquipmentStationModifier> map = Maps.newHashMap();
                for (var entry : GsonHelper.getAsJsonArray(data, "item_modifiers")) {
                    JsonObject obj = entry.getAsJsonObject();
                    map.put(ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(obj, "item"))), new EquipmentStationModifier(GsonHelper.getAsString(obj, "modifier"), GsonHelper.getAsInt(obj, "value")));
                }
                EquipmentStationModifier.EQUIPMENT_STATION_MODIFIERS = map;
            }
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("factory:custom/equipment_station/materials.json")).get().open()));
                JsonObject data = GsonHelper.parse(reader);

                Map<Item, EquipmentStationMaterial> map = Maps.newHashMap();
                for (var entry : GsonHelper.getAsJsonArray(data, "item_materials")) {
                    JsonObject obj = entry.getAsJsonObject();
                    map.put(ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(obj, "item"))), new EquipmentStationMaterial(GsonHelper.getAsInt(obj, "base_speed"), GsonHelper.getAsInt(obj, "base_durability")));
                }
                EquipmentStationMaterial.EQUIPMENT_STATION_MATERIALS = map;
            }
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
            {
                JsonObject data = GsonHelper.parse(new BufferedReader(new InputStreamReader(Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("factory:custom/forbidden_items.json")).get().open())));
                for (var entry : GsonHelper.getAsJsonArray(data, "values")) {
                    ModTags.FORBIDDEN_ITEMS.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getAsString())));
                }
            }

        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event)
    {
        // new MerchantOffer(Payment, product, maxUses, VillagerXp, villager_discounts);
        if(event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).clear();
            trades.get(2).clear();
            trades.get(3).clear();
            trades.get(4).clear();
            trades.get(5).clear();
        }
        if(event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).clear();
            trades.get(2).clear();
            trades.get(3).clear();
            trades.get(4).clear();
            trades.get(5).clear();
        }
    }

}
