package me.aes123.factory.block;

import me.aes123.factory.blockentity.GateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class GateBlock extends BaseEntityBlock {
    public GateBlock(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof GateBlockEntity gate) {
                gate.use((ServerLevel)pLevel,(ServerPlayer) pPlayer);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }


        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @javax.annotation.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GateBlockEntity(pos, state);
    }
}
