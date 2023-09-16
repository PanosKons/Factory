package me.aes123.factory.dungeon;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DungeonSavedData extends SavedData {
    public boolean isRunning;
    public boolean hasDungeonGenerated;

    public List<String> participatingPlayers = new ArrayList<>();
    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean("isRunning", isRunning);
        nbt.putBoolean("hasDungeonGenerated", hasDungeonGenerated);
        ListTag ls = new ListTag();
        for(var uuid :participatingPlayers)
        {
            CompoundTag tag = new CompoundTag();
            tag.putString("name", uuid);
            ls.add(tag);
        }
        nbt.put("players", ls);
        return nbt;
    }
    public DungeonSavedData()
    {
        isRunning = false;
        hasDungeonGenerated = false;
        participatingPlayers = new ArrayList<>();
    }
    public DungeonSavedData(CompoundTag nbt)
    {
        isRunning = nbt.getBoolean("isRunning");
        hasDungeonGenerated = nbt.getBoolean("hasDungeonGenerated");
        ListTag ls = nbt.getList("players", 10);
        for(int i = 0; i < ls.size(); i++)
        {
            participatingPlayers.add(ls.getCompound(i).getString("name"));
        }
    }
    public static DungeonSavedData get(ServerLevel level)
    {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(DungeonSavedData::new, DungeonSavedData::new, "dungeon");
    }
}
