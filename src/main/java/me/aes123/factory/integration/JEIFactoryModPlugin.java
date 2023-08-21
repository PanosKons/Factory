package me.aes123.factory.integration;

import me.aes123.factory.Main;
import me.aes123.factory.recipe.AssemblerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIFactoryModPlugin implements IModPlugin {
    public static RecipeType<AssemblerRecipe> INFUSION_TYPE =
            new RecipeType<>(AssemblerRecipeCategory.UID, AssemblerRecipe.class);

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
        registration.addRecipes(INFUSION_TYPE, recipesInfusing);
    }
}