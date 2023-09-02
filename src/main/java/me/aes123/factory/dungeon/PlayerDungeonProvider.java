package me.aes123.factory.dungeon;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDungeonProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerDungeon> PLAYER_DUNGEON = CapabilityManager.get(new CapabilityToken<>(){});

    private PlayerDungeon playerDungeon = null;
    private final LazyOptional<PlayerDungeon> optional = LazyOptional.of(this::createPlayerDungeon);

    private PlayerDungeon createPlayerDungeon() {
        if(this.playerDungeon == null)
        {
            this.playerDungeon = new PlayerDungeon();
        }

        return this.playerDungeon;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_DUNGEON)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerDungeon().save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerDungeon().load(nbt);
    }
}
