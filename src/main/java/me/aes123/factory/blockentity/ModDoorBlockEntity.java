package me.aes123.factory.blockentity;

import me.aes123.factory.init.ModBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModDoorBlockEntity extends BlockEntity {
    public int doorCost = 0;
    public String doorName = "";
    public ModDoorBlockEntity(BlockPos blockPos, BlockState state) {
        super(ModBlockEntityType.DOOR_BLOCK_ENTITY.get(), blockPos, state);
    }
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("door_cost", doorCost);
        nbt.putString("doorName", doorName);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        doorCost = nbt.getInt("doorCost");
        doorName = nbt.getString("doorName");
        super.load(nbt);
    }

    public void use(ServerLevel level, ServerPlayer player) {
        System.out.println("re");
    }
}
