package me.aes123.factory.item.equipment;

import me.aes123.factory.data.EquipmentModifier;
import me.aes123.factory.init.ModEnchantments;
import me.aes123.factory.init.ModItems;
import me.aes123.factory.item.equipment.base.ModHandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModGun extends ModHandItem {
    public static final double REACH = 100;

    public ModGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(hand != InteractionHand.MAIN_HAND) return super.use(level, player,hand);

        ItemStack stack = player.getItemInHand(hand);
        float fireRate = getModifierValue(EquipmentModifier.EquipmentModifierType.FIRE_RATE, stack);
        if(fireRate == 0.0f) return InteractionResultHolder.fail(stack);

        Inventory inv = player.getInventory();
        boolean ret = true;
        boolean teleportFlag = stack.getAllEnchantments().containsKey(ModEnchantments.TELEPORT.get());
        for(ItemStack itemstack : inv.items)
        {
            if(!((itemstack.is(ModItems.BULLET.get()) && !teleportFlag) || (itemstack.is(Items.ENDER_PEARL) && teleportFlag))) continue;
            if(ret)
            {
                ret = false;
                itemstack.shrink(1);
            }
        }
        if(ret) return super.use(level, player,hand);

        float tickTime = 1.0f;
        var eyePos = player.getEyePosition(tickTime);
        var scaledView = player.getViewVector(1.0f).scale(REACH);

        HitResult hitResult = player.pick(REACH, 1.0f, false);
        double reach = hitResult != null && hitResult.getType() != HitResult.Type.MISS ? hitResult.getLocation().distanceToSqr(eyePos) : REACH * REACH;
        EntityHitResult result = ProjectileUtil.getEntityHitResult(player, eyePos , eyePos.add(scaledView), player.getBoundingBox().expandTowards(scaledView).inflate(1.0D, 1.0D, 1.0D), (pentity) -> !pentity.isSpectator() && pentity instanceof LivingEntity, reach);

        double distanceToTarget = result == null ? hitResult.getLocation().distanceTo(eyePos) : result.getLocation().distanceTo(eyePos);
        double particleDensity = 0.1D;
        var rayDirection = scaledView.scale(particleDensity / (double)REACH);
        int iterations = (int)(distanceToTarget / particleDensity);
        for (int i = 10; i < iterations; i++)
        {
            level.addParticle(ParticleTypes.ASH,eyePos.x + rayDirection.x * i, eyePos.y + rayDirection.y * i,eyePos.z + rayDirection.z * i,0,0,0);
        }

        takeDurabilityDamage(stack, player, InteractionHand.MAIN_HAND ,1);
        float teleportDelay = 1.0f;
        if(teleportFlag) teleportDelay = (11 - stack.getAllEnchantments().get(ModEnchantments.TELEPORT.get())) * 10;
        player.getCooldowns().addCooldown(this, (int)(fireRate * 20 * teleportDelay));
        playSound(level, player.getOnPos().above());
        if(result != null && !teleportFlag)
        {
            LivingEntity entity = (LivingEntity) result.getEntity();
            entity.hurt(player.damageSources().mobAttack(player),10.0f);
        }
        else if(teleportFlag)
        {
            var x = hitResult.getLocation().x;
            var y = hitResult.getLocation().y;
            var z = hitResult.getLocation().z;
            if (player.isPassenger()) {
                player.dismountTo(x, y,z);
            } else {
                player.teleportTo(x, y,z);
            }
        }
        return InteractionResultHolder.consume(stack);
    }

    private void playSound(Level level, BlockPos blockPos) {
        RandomSource randomsource = level.getRandom();
        level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (randomsource.nextFloat() - randomsource.nextFloat()) * 0.2F + 1.0F);
    }
}
