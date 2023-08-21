package me.aes123.factory.entity.client;

import me.aes123.factory.Main;
import me.aes123.factory.entity.GuardEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GuardModel extends GeoModel<GuardEntity> {
    @Override
    public ResourceLocation getModelResource(GuardEntity animatable) {
        return new ResourceLocation(Main.MODID, "geo/guard.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GuardEntity animatable) {
        return new ResourceLocation(Main.MODID, "textures/entity/guard.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GuardEntity animatable) {
        return new ResourceLocation(Main.MODID, "animations/guard.animation.json");
    }
    @Override
    public void setCustomAnimations(GuardEntity animatable, long instanceId, AnimationState<GuardEntity> animationState) {
        //CoreGeoBone head = getAnimationProcessor().getBone("head");

        //if (head != null) {
        //    EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        //    head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
        //    head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        //}
    }
}
