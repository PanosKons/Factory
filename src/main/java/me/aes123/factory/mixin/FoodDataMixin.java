package me.aes123.factory.mixin;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 10))
    public int method0(int constant)
    {
        return 500;
    }
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 80))
    public int method(int constant)
    {
        return 4000;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite()
    public boolean needsFood() {return true;}
}
