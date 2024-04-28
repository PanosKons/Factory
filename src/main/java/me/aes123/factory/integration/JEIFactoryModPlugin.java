package me.aes123.factory.integration;

import me.aes123.factory.Main;
import me.aes123.factory.recipe.AssemblerRecipe;
import me.aes123.factory.screen.AssemblerScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

import static me.aes123.factory.integration.AssemblerRecipeCategory.ASSEMBLER_TYPE;

@JeiPlugin
public class JEIFactoryModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Main.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                AssemblerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<AssemblerRecipe> recipesInfusing = rm.getAllRecipesFor(AssemblerRecipe.Type.INSTANCE);
        registration.addRecipes(ASSEMBLER_TYPE, recipesInfusing);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AssemblerScreen.class, 90, 35, 24,14, ASSEMBLER_TYPE);
    }
}