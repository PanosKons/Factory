package me.aes123.factory.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

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
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.speedModifier = compoundTag.getFloat("MaxSpeedModifier");
        if(this.speedModifier == 0.0f) this.speedModifier = 1.0f;
    }
}
