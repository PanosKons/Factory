package me.aes123.factory.init;

import me.aes123.factory.util.IBlockEntity;
import me.aes123.factory.util.IHopperBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class ModHopperBlock extends HopperBlock {
    public int moveItemDelay;
    public RegistryObject<BlockEntityType<HopperBlockEntity>> type;
    public ModHopperBlock(Properties properties, RegistryObject<BlockEntityType<HopperBlockEntity>> type, int moveItemDelay) {
        super(properties);
        this.type = type;
        this.moveItemDelay = moveItemDelay;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        var entity = new HopperBlockEntity(blockPos, state);
        ((IBlockEntity)(entity)).setType(type.get());
        ((IHopperBlockEntity)(entity)).setMoveItemDelay(moveItemDelay);
        return entity;
    }
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, this.type.get(), HopperBlockEntity::pushItemsTick);
    }
}
