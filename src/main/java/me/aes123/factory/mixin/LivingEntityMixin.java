package me.aes123.factory.mixin;

import me.aes123.factory.init.ModMobEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Invoker("getJumpPower")
    public abstract float getJump();
    /**
     * @author
     * @reason
     */
    @Overwrite()
    protected void jumpFromGround() {
        var ent = (LivingEntity)(Object)this;

        if(ent.hasEffect(ModMobEffects.FREEZE.get())) return;

        Vec3 vec3 = ent.getDeltaMovement();
        ent.setDeltaMovement(vec3.x, (double)this.getJump(), vec3.z);
        if (ent.isSprinting()) {
            float f = ent.getYRot() * ((float)Math.PI / 180F);
            ent.setDeltaMovement(ent.getDeltaMovement().add((double)(-Mth.sin(f) * 0.2F), 0.0D, (double)(Mth.cos(f) * 0.2F)));
        }

        ent.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(ent);
    }
}
