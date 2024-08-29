package me.aes123.factory.mixin;

import com.google.common.collect.ImmutableList;
import me.aes123.factory.enchantment.SoulboundEnchantment;
import me.aes123.factory.init.ModEnchantments;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Inventory.class)
public abstract class InventoryMixin {
    @Shadow
    private final List<NonNullList<ItemStack>> compartments;

    protected InventoryMixin(List<NonNullList<ItemStack>> compartments) {
        this.compartments = compartments;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite()
    public void dropAll(){
        for(List<ItemStack> list : this.compartments) {
            for(int i = 0; i < list.size(); ++i) {
                ItemStack itemstack = list.get(i);
                if (!itemstack.isEmpty() && !itemstack.getAllEnchantments().containsKey(ModEnchantments.SOULBOUND.get())) {
                    ((Inventory)(Object)this).player.drop(itemstack, true, false);
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }
    }
}
