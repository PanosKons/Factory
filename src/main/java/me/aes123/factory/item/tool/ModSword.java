package me.aes123.factory.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModSword extends SwordItem implements IModTool{
    public ModSword(Properties properties, int HarvestLevel) {
        super(new Tier() {
            @Override
            public int getUses() {
                return 1000;
            }

            @Override
            public float getSpeed() {
                return 1;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return HarvestLevel;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, 1, -3f, properties);

    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if(!level.isClientSide)
        {
            takeDurabilityDamage(stack, entity);
        }
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isCorrectToolForDrops(stack, state) ? stack.getTag().getFloat("speed") : 1.0F;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        addHoverText(itemStack, components);
        super.appendHoverText(itemStack, level, components, flag);
    }
}