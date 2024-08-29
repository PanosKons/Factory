package me.aes123.factory.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ModMinecart extends Minecart {

    public float speedModifier = 1.0f;

    public ModMinecart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public static ModMinecart create(Level p_38473_, double p_38474_, double p_38475_, double p_38476_)
    {
        return new ModMinecart(p_38473_, p_38474_, p_38475_, p_38476_);
    }
    protected ModMinecart(Level p_38473_, double p_38474_, double p_38475_, double p_38476_) {
        super(p_38473_, p_38474_, p_38475_, p_38476_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("MaxSpeedModifier", this.speedModifier);
    }

    @Override
    public void destroy(DamageSource p_38115_) {
        this.kill();
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            ItemStack itemstack = new ItemStack(this.getDropItem());
            CompoundTag nbt = new CompoundTag();
            nbt.putFloat("MaxSpeedModifier", speedModifier);
            itemstack.setTag(nbt);
            if (this.hasCustomName()) {
                itemstack.setHoverName(this.getCustomName());
            }

            this.spawnAtLocation(itemstack);
        }
    }

    @Override
    public float getMaxCartSpeedOnRail() {
        return 1000.0f;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.speedModifier = compoundTag.getFloat("MaxSpeedModifier");
        if(this.speedModifier == 0.0f) this.speedModifier = 1.0f;
    }

    @Override
    public void moveMinecartOnRail(BlockPos pos) {
        AbstractMinecart mc = this;
        double d24 = mc.isVehicle() ? 0.75D * this.speedModifier : 1.0D * this.speedModifier;
        double d25 = mc.getMaxSpeedWithRail();
        Vec3 vec3d1 = mc.getDeltaMovement();
        mc.move(MoverType.SELF, new Vec3(Mth.clamp(d24 * vec3d1.x, -d25, d25), 0.0D, Mth.clamp(d24 * vec3d1.z, -d25, d25)));
    }

    @Override
    protected double getMaxSpeed() {
        return 1000.0f;
    }
}
