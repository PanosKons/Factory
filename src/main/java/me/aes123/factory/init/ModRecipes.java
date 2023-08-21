package me.aes123.factory.init;

import me.aes123.factory.Main;
import me.aes123.factory.recipe.AssemblerRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MODID);

    public static final RegistryObject<RecipeSerializer<AssemblerRecipe>> ASSEMBLING = SERIALIZERS.register("assembling", () -> AssemblerRecipe.Serializer.INSTANCE);
}
