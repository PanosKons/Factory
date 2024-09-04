package me.aes123.factory.mixin;

import me.aes123.factory.blockentity.ModBarrelBlockEntity;
import me.aes123.factory.item.ModBundleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void place(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir, BlockPlaceContext blockplacecontext, BlockState blockstate, BlockPos blockpos, Level level, Player player, ItemStack itemstack, BlockState blockstate1, SoundType soundtype)
    {
        if(itemstack.getItem() instanceof ModBundleItem)
            itemstack.grow(1);

        var inventory =  player.getInventory();
        if(shouldGrow(itemstack, inventory)) itemstack.grow(1);
    }

    private static boolean shouldGrow(ItemStack itemstack, Inventory inventory)
    {
        for(int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if(stack.getItem() instanceof ModBundleItem)
            {
                CompoundTag compoundtag = stack.getOrCreateTag();
                if (!compoundtag.contains("Items")) continue;
                ListTag listtag = compoundtag.getList("Items", 10);
                if (listtag.isEmpty()) continue;

                List<ItemStack> inv = new ArrayList<>();
                for(int j = 0; j < listtag.size(); j++) {
                    CompoundTag compoundtag1 = listtag.getCompound(j);
                    inv.add(ItemStack.of(compoundtag1));
                }

                Optional<ItemStack> get = inv.stream().filter(st -> ModBarrelBlockEntity.sameItem(st, itemstack)).findFirst();
                if(get.isPresent())
                {
                    get.get().shrink(1);

                    listtag.clear();
                    int r = 0;
                    for(int k = 0; k < inv.size(); k++)
                    {
                        if(inv.get(k).isEmpty()) continue;
                        CompoundTag compoundtag2 = new CompoundTag();
                        inv.get(k).save(compoundtag2);
                        listtag.add(r, compoundtag2);
                        r++;
                    }

                    compoundtag.put("Items", listtag);
                    if (listtag.isEmpty()) {
                        stack.removeTagKey("Items");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
