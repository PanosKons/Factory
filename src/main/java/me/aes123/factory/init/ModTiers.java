package me.aes123.factory.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
    WEAK_WOOD(0, 4, 2.0F, 0.0F, 15, () -> {
        return Ingredient.of(ItemTags.PLANKS);
    }),
    WEAK_STONE(1, 20, 4.0F, 1.0F, 5, () -> {
        return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
    });

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private ModTiers(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_, Supplier<Ingredient> p_43337_) {
        this.level = p_43332_;
        this.uses = p_43333_;
        this.speed = p_43334_;
        this.damage = p_43335_;
        this.enchantmentValue = p_43336_;
        this.repairIngredient = new LazyLoadedValue<>(p_43337_);
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @org.jetbrains.annotations.Nullable public net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> getTag() {
        return switch(this)
                {
                    case WEAK_WOOD -> Tags.Blocks.NEEDS_WOOD_TOOL;
                    case WEAK_STONE -> BlockTags.NEEDS_STONE_TOOL;
                };
    }
}
