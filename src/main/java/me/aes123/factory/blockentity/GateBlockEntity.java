package me.aes123.factory.blockentity;

import me.aes123.factory.dungeon.Dungeon;
import me.aes123.factory.init.ModBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class GateBlockEntity extends BlockEntity {
    public String doorName = "";
    public List<String> playerNames = new ArrayList<String>();
    public GateBlockEntity(BlockPos blockPos, BlockState state) {
        super(ModBlockEntityType.GATE_BLOCK_ENTITY.get(), blockPos, state);
    }
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putString("door_id", doorName);
        ListTag ls = new ListTag();
        for(var uuid :playerNames)
        {
            CompoundTag tag = new CompoundTag();
            tag.putString("name", uuid);
            ls.add(tag);
        }
        nbt.put("players", ls);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        doorName = nbt.getString("door_id");
        ListTag ls = nbt.getList("players", 10);
        for(int i = 0; i < ls.size(); i++)
        {
            playerNames.add(ls.getCompound(i).getString("name"));
        }
        super.load(nbt);
    }

    public void use(ServerLevel level, ServerPlayer player) {
        if(!Dungeon.useGate(level, player, doorName, playerNames)) return;

        BlockPos pos = getBlockPos();

        for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
            for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
                for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                    level.destroyBlock(new BlockPos(x, y, z), false);
                }
            }
        }
    }
}
