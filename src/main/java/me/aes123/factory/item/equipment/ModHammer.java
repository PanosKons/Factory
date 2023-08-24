package me.aes123.factory.item.equipment;

import me.aes123.factory.item.equipment.base.ModTool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModHammer extends ModTool {
    public ModHammer(Properties properties, int harvestLevel) {
        super(properties, harvestLevel, BlockTags.MINEABLE_WITH_PICKAXE);
    }
    public void setDestroySpeedEvent(PlayerEvent.BreakSpeed event, ItemStack stack) {
        BlockState state = event.getState();
        if(!isCorrectToolForDrops(stack, state)) return;
        Player player = event.getEntity();
        Level level = player.level();
        float originalHardness = state.getDestroySpeed(level, event.getPosition().get());
        float newHardness = originalHardness;
        List<BlockPos> positions = getBlocksToBeDestroyed(stack, event.getPosition().get(), player, level);
        for(int i = 0; i < positions.size(); i++)
        {
            newHardness += level.getBlockState(positions.get(i)).getDestroySpeed(level, event.getPosition().get());
        }
        event.setNewSpeed(event.getOriginalSpeed() * originalHardness / newHardness);
    }
    public List<BlockPos> getBlocksToBeDestroyed(ItemStack stack, BlockPos initalBlockPos, Player player, LevelAccessor level) {
        int range = stack.getTag().getFloat("mine_aoe") > 0.0f ? 2 : 1;
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if(traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y));
                }
            }
        }

        if(traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ()));
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                }
            }
        }
        List<BlockPos> resPositions = new ArrayList<>();
        for(BlockPos pos : positions) {
            if(pos.equals(initalBlockPos) || !isCorrectToolForDrops(stack, level.getBlockState(pos))) continue;
            resPositions.add(pos);
        }
        return resPositions;
    }
}
