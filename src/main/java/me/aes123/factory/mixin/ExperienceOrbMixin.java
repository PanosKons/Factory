package me.aes123.factory.mixin;

import me.aes123.factory.blockentity.ModEnchantmentTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

    @Nullable
    ModEnchantmentTableBlockEntity target = null;

    @Inject(method = "scanForEntities", at = @At("TAIL"))
    void scanForEntities0(CallbackInfo ci)
    {
        ExperienceOrb instance = ((ExperienceOrb)(Object)(this));
        if(target != null && target.getBlockPos().distToCenterSqr(instance.position()) > 64.0)
        {
            target = null;
        }
        else {
            for (int x = -3; x <= 3; x++) {
                for (int y = -3; y <= 3; y++) {
                    for (int z = -3; z <= 3; z++) {
                        var blockEntity = instance.level().getBlockEntity(new BlockPos(instance.getOnPos().getX() + x, instance.getOnPos().getY() + y, instance.getOnPos().getZ() + z));
                        if (blockEntity != null && blockEntity instanceof ModEnchantmentTableBlockEntity bl) {
                            target = bl;
                            return;
                        }
                    }
                }
            }
        }
    }
    @Inject(method = "tick", at = @At("TAIL"))
    void tick(CallbackInfo ci)
    {
        ExperienceOrb instance = ((ExperienceOrb)(Object)(this));

        if (this.target != null) {

            if(target.getBlockPos().distToCenterSqr(instance.position()) < 0.3D)
            {
                target.XPstored += instance.value;
                instance.discard();
                return;
            }

            Vec3 vec3 = new Vec3(this.target.getBlockPos().getX() - instance.getX() + 0.5, this.target.getBlockPos().getY() + 1.15D - instance.getY(), this.target.getBlockPos().getZ() - instance.getZ() + 0.5);
            double d0 = vec3.lengthSqr();
            if (d0 < 64.0D) {
                double d1 = 1.0D - Math.sqrt(d0) / 8.0D;
                instance.setDeltaMovement(instance.getDeltaMovement().add(vec3.normalize().scale(d1 * d1 * 0.2D)));
            }
        }
    }
}
