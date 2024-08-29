package me.aes123.factory.mixin;

import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnderMan.class)
public abstract class EnderManMixin {

    @Overwrite()
    protected void registerGoals() {
        var enderMan = (EnderMan)(Object)this;
        enderMan.goalSelector.addGoal(0, new FloatGoal(enderMan));
        enderMan.goalSelector.addGoal(2, new MeleeAttackGoal(enderMan, 1.0D, true));
        enderMan.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(enderMan, 1.0D, 0.0F));
        enderMan.goalSelector.addGoal(8, new LookAtPlayerGoal(enderMan, Player.class, 8.0F));
        enderMan.goalSelector.addGoal(8, new RandomLookAroundGoal(enderMan));
        enderMan.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(enderMan, Player.class, true));
        enderMan.targetSelector.addGoal(2, new HurtByTargetGoal(enderMan));
        enderMan.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(enderMan, Endermite.class, true, false));
        enderMan.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(enderMan, false));
    }
}
