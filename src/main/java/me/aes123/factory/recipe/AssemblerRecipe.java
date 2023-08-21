package me.aes123.factory.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.aes123.factory.Main;
import me.aes123.factory.init.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class AssemblerRecipe implements Recipe<SimpleContainer> {
    final int width;
    final int height;
    final NonNullList<Ingredient> recipeItems;
    final ItemStack output;
    final ItemStack requiredRecipe;
    private final ResourceLocation id;
    final int speed;

    public AssemblerRecipe(ResourceLocation id, ItemStack output,
                                    NonNullList<Ingredient> recipeItems, int width, int height, int speed, ItemStack requiredRecipe) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.requiredRecipe = requiredRecipe;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if(requiredRecipe != null && !(requiredRecipe.getItem().equals(container.getItem(10).getItem()) && requiredRecipe.getTag().equals(container.getItem(10).getTag()))) return false;
        int CONTAINER_WIDTH = 3;
        int CONTAINER_HEIGHT = 3;
        for(int i = 0; i <= CONTAINER_WIDTH - this.width; ++i) {
            for(int j = 0; j <= CONTAINER_HEIGHT - this.height; ++j) {
                if (this.matches(container, i, j, true, CONTAINER_WIDTH, CONTAINER_HEIGHT)) {
                    return true;
                }

                if (this.matches(container, i, j, false,CONTAINER_WIDTH, CONTAINER_HEIGHT)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matches(SimpleContainer container, int p_44172_, int p_44173_, boolean p_44174_, int containerWidth, int containerHeight) {
        for(int i = 0; i < containerWidth; ++i) {
            for(int j = 0; j < containerHeight; ++j) {
                int k = i - p_44172_;
                int l = j - p_44173_;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (p_44174_) {
                        ingredient = this.recipeItems.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.width);
                    }
                }

                if (!ingredient.test(container.getItem(i + j * containerWidth))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess access) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getSpeed() {return speed;}

    public static class Type implements RecipeType<AssemblerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "assembling";
    }


    public static class Serializer implements RecipeSerializer<AssemblerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Main.MODID, "assembling");


       @Override
        public AssemblerRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {

            Map<String, Ingredient> map = keyFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "key"));
            String[] astring = shrink(patternFromJson(GsonHelper.getAsJsonArray(serializedRecipe, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            int speed = GsonHelper.getAsInt(serializedRecipe, "speed");

            ItemStack requiredRecipe = new ItemStack(ModItems.DISCOVERED_RECIPE.get(),1);
            CompoundTag nbt = new CompoundTag();
            String required_recipe = GsonHelper.getAsString(serializedRecipe, "required_recipe");
            nbt.putString("required_recipe", required_recipe);
            requiredRecipe.setTag(nbt);
            if(required_recipe.isEmpty() || required_recipe.equals("none")) requiredRecipe = null;

            NonNullList<Ingredient> nonnulllist = dissolvePattern(astring, map, i, j);
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));
            return new AssemblerRecipe(recipeId,itemstack,nonnulllist,i,j,speed,requiredRecipe);
        }
        static NonNullList<Ingredient> dissolvePattern(String[] p_44203_, Map<String, Ingredient> p_44204_, int p_44205_, int p_44206_) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_44205_ * p_44206_, Ingredient.EMPTY);
            Set<String> set = Sets.newHashSet(p_44204_.keySet());
            set.remove(" ");

            for(int i = 0; i < p_44203_.length; ++i) {
                for(int j = 0; j < p_44203_[i].length(); ++j) {
                    String s = p_44203_[i].substring(j, j + 1);
                    Ingredient ingredient = p_44204_.get(s);
                    if (ingredient == null) {
                        throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                    }

                    set.remove(s);
                    nonnulllist.set(j + p_44205_ * i, ingredient);
                }
            }

            if (!set.isEmpty()) {
                throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
            } else {
                return nonnulllist;
            }
        }
        private static int firstNonSpace(String p_44185_) {
            int i;
            for(i = 0; i < p_44185_.length() && p_44185_.charAt(i) == ' '; ++i) {
            }

            return i;
        }
        private static int lastNonSpace(String p_44201_) {
            int i;
            for(i = p_44201_.length() - 1; i >= 0 && p_44201_.charAt(i) == ' '; --i) {
            }

            return i;
        }
        static String[] shrink(String... p_44187_) {
            int i = Integer.MAX_VALUE;
            int j = 0;
            int k = 0;
            int l = 0;

            for(int i1 = 0; i1 < p_44187_.length; ++i1) {
                String s = p_44187_[i1];
                i = Math.min(i, firstNonSpace(s));
                int j1 = lastNonSpace(s);
                j = Math.max(j, j1);
                if (j1 < 0) {
                    if (k == i1) {
                        ++k;
                    }

                    ++l;
                } else {
                    l = 0;
                }
            }

            if (p_44187_.length == l) {
                return new String[0];
            } else {
                String[] astring = new String[p_44187_.length - l - k];

                for(int k1 = 0; k1 < astring.length; ++k1) {
                    astring[k1] = p_44187_[k1 + k].substring(i, j + 1);
                }

                return astring;
            }
        }
        static Map<String, Ingredient> keyFromJson(JsonObject p_44211_) {
            Map<String, Ingredient> map = Maps.newHashMap();

            for(Map.Entry<String, JsonElement> entry : p_44211_.entrySet()) {
                if (entry.getKey().length() != 1) {
                    throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                }

                if (" ".equals(entry.getKey())) {
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
                }

                map.put(entry.getKey(), Ingredient.fromJson(entry.getValue(), false));
            }

            map.put(" ", Ingredient.EMPTY);
            return map;
        }
        static String[] patternFromJson(JsonArray p_44197_) {
            int MAX_HEIGHT = 3;
            int MAX_WIDTH = 3;
            String[] astring = new String[p_44197_.size()];
            if (astring.length > MAX_HEIGHT) {
                throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
            } else if (astring.length == 0) {
                throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
            } else {
                for (int i = 0; i < astring.length; ++i) {
                    String s = GsonHelper.convertToString(p_44197_.get(i), "pattern[" + i + "]");
                    if (s.length() > MAX_WIDTH) {
                        throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                    }

                    if (i > 0 && astring[0].length() != s.length()) {
                        throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                    }

                    astring[i] = s;
                }

                return astring;
            }
        }

        public AssemblerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int i = buf.readVarInt();
            int j = buf.readVarInt();
            int speed = buf.readVarInt();
            ItemStack requiredRecipe = buf.readItem();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(buf));
            }

            ItemStack itemstack = buf.readItem();
            return new AssemblerRecipe(id, itemstack, nonnulllist,i,j, speed,requiredRecipe);
        }

        public void toNetwork(FriendlyByteBuf buf, AssemblerRecipe recipe) {
            buf.writeVarInt(recipe.width);
            buf.writeVarInt(recipe.height);
            buf.writeVarInt(recipe.speed);
            buf.writeItem(recipe.requiredRecipe);

            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.output);
        }
    }
}
