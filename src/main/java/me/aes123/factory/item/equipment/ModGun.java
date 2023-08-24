package me.aes123.factory.item.equipment;

import me.aes123.factory.item.equipment.base.ModHandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModGun extends ModHandItem {
    public static final double REACH = 100;

    public ModGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        float fireRate = stack.getOrCreateTag().getFloat("fire_rate");
        if(fireRate == 0.0f) return InteractionResultHolder.fail(stack);

        float tickTime = 1.0f;
        var eyePos = player.getEyePosition(tickTime);
        var scaledView = player.getViewVector(1.0f).scale(REACH);
        HitResult hitResult = player.pick(REACH, 1.0f, false);
        double reach = hitResult != null && hitResult.getType() != HitResult.Type.MISS ? hitResult.getLocation().distanceToSqr(eyePos) : REACH * REACH;
        EntityHitResult result = ProjectileUtil.getEntityHitResult(player, eyePos , eyePos.add(scaledView), player.getBoundingBox().expandTowards(scaledView).inflate(1.0D, 1.0D, 1.0D), (pentity) -> !pentity.isSpectator() && pentity instanceof LivingEntity, reach);
        takeDurabilityDamage(stack, player,player.getMainHandItem().getEquipmentSlot(), 1);
        player.getCooldowns().addCooldown(this, (int)(fireRate * 20));
        playSound(level, player.getOnPos().above());
        if(result != null)
        {
            LivingEntity entity = (LivingEntity) result.getEntity();
            entity.hurt(player.damageSources().mobAttack(player),10.0f);
        }
        return InteractionResultHolder.consume(stack);
    }

    private void playSound(Level level, BlockPos blockPos) {
        RandomSource randomsource = level.getRandom();
        level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (randomsource.nextFloat() - randomsource.nextFloat()) * 0.2F + 1.0F);
    }
}
