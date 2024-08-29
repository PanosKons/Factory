package me.aes123.factory.mixin;

import me.aes123.factory.init.ModEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "restoreFrom", at = @At("HEAD"))
    public void restore(ServerPlayer player, boolean p_9017_, CallbackInfo ci)
    {
        if (!((ServerPlayer)(Object)this).level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || player.isSpectator() && !p_9017_)
        {
            var other = player.getInventory();
            Inventory inv = ((ServerPlayer)(Object)this).getInventory();
            for(int i = 0; i < inv.getContainerSize(); ++i) {
                if(other.getItem(i).getAllEnchantments().containsKey(ModEnchantments.SOULBOUND.get()))
                    inv.setItem(i, other.getItem(i));
            }

            inv.selected = other.selected;
        }
    }
}
