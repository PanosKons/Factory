package me.aes123.factory.block;

import me.aes123.factory.entity.ModMinecart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModPoweredRailBlock extends PoweredRailBlock{
    public ModPoweredRailBlock(Properties properties, boolean isPoweredRail) {
        super(properties, isPoweredRail);
    }
    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        if (cart instanceof ModMinecart modCart) return cart.isInWater() ? 0.2f * modCart.speedModifier : 0.4f * modCart.speedModifier;
        if (cart instanceof MinecartFurnace) return cart.isInWater() ? 0.15f : 0.2f;
        return cart.isInWater() ? 0.2f : 0.4f;
    }
}
