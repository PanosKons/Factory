package me.aes123.factory.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    /**
     * @author .
     * @reason .
     */
    @Overwrite()
    public static float getNightVisionScale(LivingEntity p_109109_, float p_109110_) {
        return 1.0f;
    }
}
