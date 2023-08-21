package me.aes123.factory.integration;

import me.aes123.factory.Main;
import me.aes123.factory.init.ModBlocks;
import me.aes123.factory.recipe.AssemblerRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

    public class AssemblerRecipeCategory implements IRecipeCategory<AssemblerRecipe> {
        public final static ResourceLocation UID = new ResourceLocation(Main.MODID, "assembling");
        public final static ResourceLocation TEXTURE =
                new ResourceLocation(Main.MODID, "textures/gui/assembler_gui.png");

        private final IDrawable background;
        private final IDrawable icon;

        public AssemblerRecipeCategory(IGuiHelper helper) {
            this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
            this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ASSEMBLER.get()));
        }

        @Override
        public RecipeType<AssemblerRecipe> getRecipeType() {
            return JEIFactoryModPlugin.INFUSION_TYPE;
        }

        @Override
        public Component getTitle() {
            return Component.translatable("container.factory.assembler");
        }

        @Override
        public IDrawable getBackground() {
            return this.background;
        }

        @Override
        public IDrawable getIcon() {
            return this.icon;
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, AssemblerRecipe recipe, IFocusGroup focuses) {
            builder.addSlot(RecipeIngredientRole.INPUT, 30, 17).addIngredients(recipe.getIngredients().get(0));
            builder.addSlot(RecipeIngredientRole.INPUT, 48, 17).addIngredients(recipe.getIngredients().get(1));
            builder.addSlot(RecipeIngredientRole.INPUT, 66, 17).addIngredients(recipe.getIngredients().get(2));
            builder.addSlot(RecipeIngredientRole.INPUT, 30, 35).addIngredients(recipe.getIngredients().get(3));
            builder.addSlot(RecipeIngredientRole.INPUT, 48, 35).addIngredients(recipe.getIngredients().get(4));
            builder.addSlot(RecipeIngredientRole.INPUT, 66, 35).addIngredients(recipe.getIngredients().get(5));
            builder.addSlot(RecipeIngredientRole.INPUT, 30, 53).addIngredients(recipe.getIngredients().get(6));
            builder.addSlot(RecipeIngredientRole.INPUT, 48, 53).addIngredients(recipe.getIngredients().get(7));
            builder.addSlot(RecipeIngredientRole.INPUT, 66, 53).addIngredients(recipe.getIngredients().get(8));


            builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 35).addItemStack(recipe.getResultItem(null));
        }
    }
