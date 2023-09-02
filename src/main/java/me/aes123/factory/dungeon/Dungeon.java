package me.aes123.factory.dungeon;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraftforge.event.server.ServerStartedEvent;

public class Dungeon {
    public static MinecraftServer server;
    public static ServerLevel dungeonLevel;
    public static DungeonSavedData dungeonData;
    public static void init(ServerStartedEvent event)
    {
        server = event.getServer();
        dungeonLevel =  server.getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation("factory:dungeon")));
        dungeonData = DungeonSavedData.get(dungeonLevel);
    }
    public static void generateDungeon()
    {
        System.out.println("generating dungeon...");

        dungeonLevel.getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING).set(false, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_DOMOBLOOT).set(false, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_MOBGRIEFING).set(false, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_DOINSOMNIA).set(false, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(false, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_WEATHER_CYCLE).set(false, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_DOFIRETICK).set(false, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_RANDOMTICKING).set(0, server);
        dungeonLevel.getGameRules().getRule(GameRules.RULE_DO_IMMEDIATE_RESPAWN).set(true, server);

        dungeonLevel.setChunkForced(0,0,true);
        dungeonLevel.setChunkForced(0,-1,true);
        dungeonLevel.setChunkForced(-1,0,true);
        dungeonLevel.setChunkForced(-1,-1,true);

        var structureOptional = dungeonLevel.getStructureManager().get(new ResourceLocation("factory:dungeon/start"));
        if(structureOptional.isPresent() == false) System.out.println("factory:dungeon/start is missing");
        BlockPos blockPos = new BlockPos(-16,120,-16);
        structureOptional.get().placeInWorld(dungeonLevel, blockPos, blockPos, (new StructurePlaceSettings()).setIgnoreEntities(false), RandomSource.create(Util.getMillis()), 2);
    }
    public static void dungeonTick()
    {
        if(dungeonData.isRunning == false) return;
    }

    public static void enterOrLeaveDungeon(ServerLevel level, ServerPlayer player) {
        if(dungeonData.isRunning == true) return;

        if(level == dungeonLevel)
        {
            BlockPos pos = player.getRespawnPosition();
            player.teleportTo(server.getLevel(player.getRespawnDimension()),pos.getX(),pos.getY(),pos.getZ(), 0, 0);
        }
        else
        {
            if(dungeonData.hasDungeonGenerated == false)
            {
                generateDungeon();
                dungeonData.hasDungeonGenerated = true;
                dungeonData.setDirty();
            }
            player.teleportTo(dungeonLevel,0, 121, 0, 0, 0);
        }
    }
}
