package me.aes123.factory.dungeon;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class DungeonSavedData extends SavedData {
    public boolean isRunning;
    public boolean hasDungeonGenerated;
    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean("isRunning", isRunning);
        nbt.putBoolean("hasDungeonGenerated", hasDungeonGenerated);
        return nbt;
    }
    public DungeonSavedData()
    {
        isRunning = false;
        hasDungeonGenerated = false;
    }
    public DungeonSavedData(CompoundTag nbt)
    {
        isRunning = nbt.getBoolean("isRunning");
        hasDungeonGenerated = nbt.getBoolean("hasDungeonGenerated");
    }
    public static DungeonSavedData get(ServerLevel level)
    {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(DungeonSavedData::new, DungeonSavedData::new, "dungeon");
    }
}
