package me.aes123.factory.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.aes123.factory.util.ModTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        }


        if(ModTags.COMMON_CHESTS.contains(context.getQueriedLootTableId().toString()))
        {
            if(context.getRandom().nextFloat() >= 0.25f) {
                modifiedLoot.add(new ItemStack(item));
            }
        }
        if(ModTags.RARE_CHESTS.contains(context.getQueriedLootTableId().toString()))
        {
            if(context.getRandom().nextFloat() >= 0.1f) {
                modifiedLoot.add(new ItemStack(item));
            }
        }
        if(ModTags.EPIC_CHESTS.contains(context.getQueriedLootTableId().toString()))
        {
            if(context.getRandom().nextFloat() >= 0.2f) {
                modifiedLoot.add(new ItemStack(item));
            }
        }

        return modifiedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}