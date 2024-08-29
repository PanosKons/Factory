package me.aes123.factory.item;

import me.aes123.factory.entity.ModMinecart;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static me.aes123.factory.data.EquipmentModifier.EquipmentModifierType.MAX_DURABILITY;

public class ModMinecartItem extends MinecartItem {
    final AbstractMinecart.Type modType;
    public ModMinecartItem(AbstractMinecart.Type p_42938_, Properties p_42939_) {
        super(p_42938_, p_42939_);
        modType = p_42938_;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag p_41424_) {
        super.appendHoverText(stack, level, components, p_41424_);
        if(stack.hasTag())
            components.add(Component.literal("Max Speed Modifier: " + stack.getTag().getInt("MaxSpeedModifier")).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public InteractionResult useOn(UseOnContext p_42943_) {
        Level level = p_42943_.getLevel();
        BlockPos blockpos = p_42943_.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (!blockstate.is(BlockTags.RAILS)) {
            return InteractionResult.FAIL;
        } else {
            ItemStack itemstack = p_42943_.getItemInHand();
            if (!level.isClientSide) {
                RailShape railshape = blockstate.getBlock() instanceof BaseRailBlock ? ((BaseRailBlock)blockstate.getBlock()).getRailDirection(blockstate, level, blockpos, null) : RailShape.NORTH_SOUTH;
                double d0 = 0.0D;
                if (railshape.isAscending()) {
                    d0 = 0.5D;
                }

                AbstractMinecart abstractminecart = ModMinecart.create(level, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.0625D + d0, (double)blockpos.getZ() + 0.5D);
                if (itemstack.hasCustomHoverName()) {
                    abstractminecart.setCustomName(itemstack.getHoverName());
                }

                if(abstractminecart instanceof ModMinecart cart && itemstack.hasTag()) {
                    cart.speedModifier = itemstack.getTag().getFloat("MaxSpeedModifier");
                }

                level.addFreshEntity(abstractminecart);
                level.gameEvent(GameEvent.ENTITY_PLACE, blockpos, GameEvent.Context.of(p_42943_.getPlayer(), level.getBlockState(blockpos.below())));
            }

            itemstack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }
}
