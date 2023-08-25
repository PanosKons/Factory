package me.aes123.factory.blockentity;

import me.aes123.factory.blockentity.base.MachineBlockEntity;
import me.aes123.factory.blockentity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.recipe.AssemblerRecipe;
import me.aes123.factory.screen.AssemblerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AssemblerBlockEntity extends MachineBlockEntity {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 1000;
    public AssemblerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.ASSEMBLER_BLOCK_ENTITY.get(), pos , state,"container.factory.assembler",(id, inventory, entity, data) -> new AssemblerMenu(id, inventory, entity, data),
                List.of(SlotType.INPUT, SlotType.INPUT, SlotType.INPUT,
                        SlotType.INPUT, SlotType.INPUT, SlotType.INPUT,
                        SlotType.INPUT, SlotType.INPUT, SlotType.INPUT,
                        SlotType.OUTPUT, SlotType.SPECIAL
                        ));
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AssemblerBlockEntity.this.progress;
                    case 1 -> AssemblerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AssemblerBlockEntity.this.progress = value;
                    case 1 -> AssemblerBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AssemblerMenu(id,inventory,this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("progress", this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        progress = nbt.getInt("progress");
        super.load(nbt);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AssemblerBlockEntity entity) {
        if(level.isClientSide()) {
            return;
        }

        Optional<AssemblerRecipe> recipe = getRecipe(entity);
        if(recipe.isPresent()) {
            entity.progress += recipe.get().getSpeed();
            setChanged(level, pos, state);

            if(entity.progress >= entity.maxProgress)
            {
                craftItem(entity, recipe.get());
            }
        }
        else {
            entity.resetProgress();
            setChanged(level,pos,state);
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(AssemblerBlockEntity entity, AssemblerRecipe recipe) {

            for(int i = 0; i < 9; i++)
            {
                entity.itemHandler.extractItem(i,1,false);
            }

            ItemStack newItemStack = new ItemStack(recipe.getResultItem(null).getItem(), entity.itemHandler.getStackInSlot(9).getCount() + recipe.getResultItem(null).getCount());



            newItemStack.setTag(recipe.getResultItem(null).getTag());
            entity.itemHandler.setStackInSlot(9, newItemStack );
            entity.resetProgress();
    }

    private static Optional<AssemblerRecipe> getRecipe(AssemblerBlockEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<AssemblerRecipe> recipe = level.getRecipeManager().getRecipeFor(AssemblerRecipe.Type.INSTANCE,inventory,level);

        if(recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory,recipe.get().getResultItem(null).getCount()) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem(null))) return recipe;
        return Optional.empty();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return (inventory.getItem(9).getItem() == itemStack.getItem() && inventory.getItem(9).getTag().equals(itemStack.getTag())) || inventory.getItem(9).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int InsertCount) {
        return inventory.getItem(9).getMaxStackSize() >= inventory.getItem(9).getCount() + InsertCount;
    }
}
