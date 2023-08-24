package me.aes123.factory.mixin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.aes123.factory.util.ILevelRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin implements ILevelRenderer {
    @Shadow
    protected ClientLevel level;
    @Shadow
    protected abstract void renderHitOutline(PoseStack p_109638_, VertexConsumer p_109639_, Entity p_109640_, double p_109641_, double p_109642_, double p_109643_, BlockPos p_109644_, BlockState p_109645_);

    @Shadow
    private RenderBuffers renderBuffers;
    @Shadow
    private Int2ObjectMap<BlockDestructionProgress> destroyingBlocks;

    @Override
    public Level getLevel()
    {
        return level;
    }
    @Override
    public void hitOutline(PoseStack p_109638_, VertexConsumer p_109639_, Entity p_109640_, double p_109641_, double p_109642_, double p_109643_, BlockPos p_109644_, BlockState p_109645_)
    {
        renderHitOutline(p_109638_,p_109639_,p_109640_,p_109641_,p_109642_,p_109643_,p_109644_, p_109645_);
    }
    @Override
    public RenderBuffers getRenderBuffers()
    {
        return renderBuffers;
    }
    @Override
    public Int2ObjectMap<BlockDestructionProgress> getBlockDestructionProgresses()
    {
        return destroyingBlocks;
    }
}
