package me.aes123.factory.mixin;

import me.aes123.factory.data.EquipmentModifier;
import me.aes123.factory.init.ModAttributes;
import me.aes123.factory.item.equipment.base.IEquipmentItem;
import me.aes123.factory.item.equipment.base.ModHandItem;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Player.class)
public abstract class PlayerMixin {
    /**
     * @author .
     * @reason .
     */
    @Overwrite()
    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.1F)
                .add(Attributes.ATTACK_SPEED)
                .add(Attributes.LUCK)
                .add(net.minecraftforge.common.ForgeMod.BLOCK_REACH.get())
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(ModAttributes.REGENERATION.get())
                .add(net.minecraftforge.common.ForgeMod.ENTITY_REACH.get());
    }
    @Inject(method = "aiStep", at = @At("TAIL"))
    void aiStep0(CallbackInfo ci)
    {
        Player instance = ((Player)(Object)(this));
        ItemStack stack = instance.getItemInHand(InteractionHand.MAIN_HAND);
        if(stack.getItem() instanceof IEquipmentItem item)
        {
            double distance = item.getModifierValue(EquipmentModifier.EquipmentModifierType.MAGNETIC, stack);
            if(distance == 0.0D) return;
            List<Entity> list = instance.level().getEntities(instance, instance.getBoundingBox().inflate(distance));
            for(Entity entity : list)
            {
                if(entity instanceof ItemEntity itemEntity)
                {
                    itemEntity.playerTouch(instance);
                }
            }
        }
    }
}
