package me.aes123.factory.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import me.aes123.factory.Main;
import me.aes123.factory.entity.GuardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GuardRenderer extends GeoEntityRenderer<GuardEntity> {
    public GuardRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GuardModel());
    }
    @Override
    public ResourceLocation getTextureLocation(GuardEntity animatable) {
        return new ResourceLocation(Main.MODID, "textures/entity/guard.png");
    }

    @Override
    public void render(GuardEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
