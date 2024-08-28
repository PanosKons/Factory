package me.aes123.factory.block;

import me.aes123.factory.blockentity.ModEnchantmentTableBlockEntity;
import me.aes123.factory.blockentity.base.MachineBlockEntity;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.screen.ModEnchantmentMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153182_, BlockState p_153183_, BlockEntityType<T> p_153184_) {
        return p_153182_.isClientSide ? createTickerHelper(p_153184_, ModBlockEntityType.ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), ModEnchantmentTableBlockEntity::bookAnimationTick) : createTickerHelper(p_153184_, ModBlockEntityType.ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), ModEnchantmentTableBlockEntity::serverTick);
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pLevel.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Nullable
    public MenuProvider getMenuProvider(BlockState p_52993_, Level p_52994_, BlockPos p_52995_) {
        BlockEntity blockentity = p_52994_.getBlockEntity(p_52995_);
        if (blockentity instanceof ModEnchantmentTableBlockEntity) {
            Component component = ((Nameable)blockentity).getDisplayName();
            return new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) -> {
                return new ModEnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.create(p_52994_, p_52995_));
            }, component);
        } else {
            return null;
        }
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
