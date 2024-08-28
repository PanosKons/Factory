package me.aes123.factory.mixin;

import me.aes123.factory.blockentity.ModBarrelBlockEntity;
import me.aes123.factory.util.IHopperBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin implements IHopperBlockEntity {
    public int moveItemDelay = 8;
    @Redirect(method = "tryMoveInItem", at = @At(value  = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;setCooldown(I)V"))
    private static void setCoolDown0(HopperBlockEntity instance, int p_59396_) {
        instance.setCooldown(((HopperBlockEntityMixin)(Object)(instance)).moveItemDelay);
    }
    @Redirect(method = "tryMoveItems", at = @At(value  = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;setCooldown(I)V"))
    private static void setCoolDown1(HopperBlockEntity instance, int p_59396_) {
        instance.setCooldown(((HopperBlockEntityMixin)(Object)(instance)).moveItemDelay);
    }
    @ModifyConstant(method = "isOnCustomCooldown", constant = @Constant(intValue = 8))
    private int getSpeed2(int value) {
        return moveItemDelay;
    }

    @Inject(method = "saveAdditional", at = @At(value = "TAIL"))
    public void saveAdditional1(CompoundTag compoundTag, CallbackInfo ci)
    {
        compoundTag.putInt("MoveItemDelay", moveItemDelay);
    }
    @Inject(method = "load", at = @At(value = "TAIL"))
    public void load1(CompoundTag compoundTag, CallbackInfo ci)
    {
        moveItemDelay = compoundTag.getInt("MoveItemDelay");
    }

    @Shadow
    private static ItemStack tryMoveInItem(@Nullable Container p_59321_, Container p_59322_, ItemStack p_59323_, int p_59324_, @Nullable Direction p_59325_) {
        return null;
    }

    @Redirect(method = "ejectItems", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/VanillaInventoryCodeHooks;insertHook(Lnet/minecraft/world/level/block/entity/HopperBlockEntity;)Z"), remap = false)
    private static boolean insert(HopperBlockEntity hopper) {

        Direction hopperFacing = (Direction)hopper.getBlockState().getValue(HopperBlock.FACING);

        double x = hopper.getLevelX() + (double)hopperFacing.getStepX();
        double y = hopper.getLevelY() + (double)hopperFacing.getStepY();
        double z = hopper.getLevelZ() + (double)hopperFacing.getStepZ();
        Direction side = hopperFacing.getOpposite();

        boolean isModBarrel = false;

        Optional<ImmutablePair<IItemHandler, BlockEntity>> ret = Optional.empty();;
        {
            int i = Mth.floor(x);
            int j = Mth.floor(y);
            int k = Mth.floor(z);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState state = hopper.getLevel().getBlockState(blockpos);
            if (state.hasBlockEntity()) {
                BlockEntity blockEntity = hopper.getLevel().getBlockEntity(blockpos);
                if (blockEntity != null) {

                    if(blockEntity instanceof ModBarrelBlockEntity) isModBarrel = true;

                    ret = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, side).map((capability) -> ImmutablePair.of(capability, blockEntity));
                }
            }
        }

        boolean finalIsModBarrel = isModBarrel;
        return ret.map((destinationResult) -> {
            IItemHandler itemHandler = destinationResult.getKey();
            Object destination = destinationResult.getValue();

            boolean isFull = true;
            for(int slot = 0; slot < itemHandler.getSlots(); ++slot) {
                ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
                if (stackInSlot.isEmpty() || stackInSlot.getCount() < itemHandler.getSlotLimit(slot)) {
                    isFull = false;
                    break;
                }
            }


            if (isFull) {
                return false;
            } else {
                for(int i = 0; i < hopper.getContainerSize(); ++i) {
                    if (!hopper.getItem(i).isEmpty()) {
                        if(finalIsModBarrel)
                        {
                            boolean barrelOk = false;
                            for(int slot = 0; slot < itemHandler.getSlots(); ++slot) {
                                ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
                                if (stackInSlot.is(hopper.getItem(i).getItem())) {
                                    barrelOk = true;
                                    break;
                                }
                            }
                            if(barrelOk == false) return true;
                        }



                            ItemStack originalSlotContents = hopper.getItem(i).copy();
                            ItemStack insertStack = hopper.removeItem(i, 1);

                            for (int slot = 0; slot < itemHandler.getSlots() && !insertStack.isEmpty(); ++slot) {

                                {
                                    var destInventory = itemHandler;
                                    var stack = insertStack;
                                    ItemStack itemstack = destInventory.getStackInSlot(slot);
                                    if (destInventory.insertItem(slot, stack, true).isEmpty()) {
                                        boolean insertedItem = false;

                                        boolean isEmpty = true;
                                        for (int s = 0; s < itemHandler.getSlots(); ++s) {
                                            ItemStack stackInSlot = itemHandler.getStackInSlot(s);
                                            if (stackInSlot.getCount() > 0) {
                                                isEmpty = false;
                                                break;
                                            }
                                        }


                                        boolean inventoryWasEmpty = isEmpty;
                                        if (itemstack.isEmpty()) {
                                            destInventory.insertItem(slot, stack, false);
                                            stack = ItemStack.EMPTY;
                                            insertedItem = true;
                                        } else if (ItemHandlerHelper.canItemStacksStack(itemstack, stack)) {
                                            int originalSize = stack.getCount();
                                            stack = destInventory.insertItem(slot, stack, false);
                                            insertedItem = originalSize < stack.getCount();
                                        }

                                        if (insertedItem && inventoryWasEmpty && destination instanceof HopperBlockEntity) {
                                            HopperBlockEntity destinationHopper = (HopperBlockEntity) destination;
                                            if (!destinationHopper.isOnCustomCooldown()) {
                                                int k = 0;
                                                if (hopper instanceof HopperBlockEntity && destinationHopper.getLastUpdateTime() >= ((HopperBlockEntity) hopper).getLastUpdateTime()) {
                                                    k = 1;
                                                }

                                                destinationHopper.setCooldown(8 - k);
                                            }
                                        }
                                    }
                                    insertStack = stack;
                                }

                            }
                            ItemStack remainder = insertStack;

                            if (remainder.isEmpty()) {
                                return true;
                            }

                            hopper.setItem(i, originalSlotContents);
                        }

                }

                return false;
            }
        }).orElse(false);
    }


    @Override
    public void setMoveItemDelay(int value)
    {
        moveItemDelay = value;
    }
}
