package me.aes123.factory.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.aes123.factory.Main;
import me.aes123.factory.init.ModItems;
import me.aes123.factory.util.ModTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddItemModifier extends LootModifier {
    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
            .fieldOf("item").forGetter(m -> m.item)).apply(inst, AddItemModifier::new)));
    private final Item item;


    protected AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

        ObjectArrayList<ItemStack> modifiedLoot = new ObjectArrayList<>();

        for (ItemStack stack : generatedLoot)
        {
            if(!stack.is(ModTags.Items.FORBIDDEN_ITEMS))
            {
                modifiedLoot.add(stack);
            }
            else
            {
                if(ModTags.COMMON_CHESTS.contains(context.getQueriedLootTableId().toString())) {
                    modifiedLoot.add(new ItemStack(Items.IRON_INGOT));
                }
                if(ModTags.RARE_CHESTS.contains(context.getQueriedLootTableId().toString())) {
                    modifiedLoot.add(new ItemStack(Items.EMERALD));
                }
                if(ModTags.EPIC_CHESTS.contains(context.getQueriedLootTableId().toString())) {
                    if(context.getRandom().nextFloat() > 0.3f) modifiedLoot.add(new ItemStack(Items.DIAMOND));
                    else modifiedLoot.add(new ItemStack(Items.NETHERITE_SCRAP));
                }
                if(ModTags.LEGENDARY_CHESTS.contains(context.getQueriedLootTableId().toString())) {
                    if(context.getRandom().nextFloat() > 0.3f) modifiedLoot.add(new ItemStack(Items.NETHERITE_SCRAP));
                    else modifiedLoot.add(new ItemStack(Items.NETHERITE_INGOT));
                }
            }
        }


        if(ModTags.COMMON_CHESTS.contains(context.getQueriedLootTableId().toString()))
        {
            if(context.getRandom().nextFloat() >= 0.85f) {
                modifiedLoot.add(new ItemStack(item));
            }
            if(context.getRandom().nextFloat() >= 0.95f) {
                int level = 1;
                while (context.getRandom().nextFloat() >= 0.95f) {
                    level++;
                }
                modifiedLoot.add(addEnchantedBook(level));
            }
            if(context.getRandom().nextFloat() >= 0.95f) {
                modifiedLoot.add(new ItemStack(ModItems.WEAK_BOOSTER.get()));
            }
        }
        if(ModTags.RARE_CHESTS.contains(context.getQueriedLootTableId().toString()))
        {
            if(context.getRandom().nextFloat() >= 0.65f) {
                modifiedLoot.add(new ItemStack(item));
            }
            if(context.getRandom().nextFloat() >= 0.7f) {
                int level = 1;
                while (context.getRandom().nextFloat() >= 0.85f) {
                    level++;
                }
                modifiedLoot.add(addEnchantedBook(level));
            }
            if(context.getRandom().nextFloat() >= 0.8f) {
                modifiedLoot.add(new ItemStack(ModItems.WEAK_BOOSTER.get()));
            }
            if(context.getRandom().nextFloat() >= 0.95f) {
                modifiedLoot.add(new ItemStack(ModItems.STRONG_BOOSTER.get()));
            }
        }
        if(ModTags.EPIC_CHESTS.contains(context.getQueriedLootTableId().toString()))
        {
            if(context.getRandom().nextFloat() >= 0.3f) {
                modifiedLoot.add(new ItemStack(item));
            }
            if(context.getRandom().nextFloat() >= 0.3f) {
                int level = 1;
                while (context.getRandom().nextFloat() >= 0.65f) {
                    level++;
                }
                modifiedLoot.add(addEnchantedBook(level));
            }
            if(context.getRandom().nextFloat() >= 0.8f) {
                modifiedLoot.add(new ItemStack(ModItems.WEAK_BOOSTER.get()));
            }
            if(context.getRandom().nextFloat() >= 0.9f) {
                modifiedLoot.add(new ItemStack(ModItems.STRONG_BOOSTER.get()));
            }
            if(context.getRandom().nextFloat() >= 0.95f) {
                modifiedLoot.add(new ItemStack(ModItems.REINFORCED_BOOSTER.get()));
            }
        }
        if(ModTags.LEGENDARY_CHESTS.contains(context.getQueriedLootTableId().toString()))
        {
            if(context.getRandom().nextFloat() >= 0.1f) {
                modifiedLoot.add(new ItemStack(item));
            }
            if(context.getRandom().nextFloat() >= 0.2f) {
                int level = 1;
                while (context.getRandom().nextFloat() >= 0.4f) {
                    level++;
                }
                modifiedLoot.add(addEnchantedBook(level));
            }
            if(context.getRandom().nextFloat() >= 0.8f) {
                modifiedLoot.add(new ItemStack(ModItems.WEAK_BOOSTER.get()));
            }
            if(context.getRandom().nextFloat() >= 0.8f) {
                modifiedLoot.add(new ItemStack(ModItems.STRONG_BOOSTER.get()));
            }
            if(context.getRandom().nextFloat() >= 0.9f) {
                modifiedLoot.add(new ItemStack(ModItems.REINFORCED_BOOSTER.get()));
            }
        }
        if(ModTags.ARCHAEOLOGY.contains(context.getQueriedLootTableId().toString()))
        {
            float rnd = context.getRandom().nextFloat();
            if(rnd >= 0.9f) {
                int level = 1;
                while (context.getRandom().nextFloat() >= 0.4f) {
                    level++;
                }
                modifiedLoot.clear();
                modifiedLoot.add(addEnchantedBook(level));
            }
            else if(rnd >= 0.85f)
            {
                modifiedLoot.clear();
                modifiedLoot.add(new ItemStack(ModItems.EXPERTISE.get()));
            }
        }



        return modifiedLoot;
    }

    public ItemStack addEnchantedBook(int level)
    {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        var enchantment = ModTags.Items.AllowedEnchantments.get(Main.rnd.nextInt(ModTags.Items.AllowedEnchantments.size()));
        EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, Math.min(enchantment.getMaxLevel(),level)));
        return stack;
    }
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}