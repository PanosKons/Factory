package me.aes123.factory.block;

import me.aes123.factory.blockentity.ModEnchantmentTableBlockEntity;
import me.aes123.factory.blockentity.base.MachineBlockEntity;
import me.aes123.factory.init.ModBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ModEnchantmentTableBlock extends EnchantmentTableBlock {
    public ModEnchantmentTableBlock(Properties properties) {
        super(properties);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ModEnchantmentTableBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? createTickerHelper(blockEntityType, ModBlockEntityType.ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), MachineBlockEntity::clientTick) : createTickerHelper(blockEntityType, ModBlockEntityType.ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), MachineBlockEntity::serverTick);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof ModEnchantmentTableBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (ModEnchantmentTableBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack p_52967_) {
        if (p_52967_.hasCustomHoverName()) {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof ModEnchantmentTableBlockEntity) {
                ((ModEnchantmentTableBlockEntity)blockentity).setCustomName(p_52967_.getHoverName());
            }
        }
    }
}
