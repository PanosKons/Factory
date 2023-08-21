package me.aes123.factory.block.entity;

import me.aes123.factory.block.AssemblerBlock;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.init.ModItems;
import me.aes123.factory.recipe.AssemblerRecipe;
import me.aes123.factory.screen.AssemblerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class AssemblerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(11)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(slot < 9) return true;
            if(slot == 9) return false;
            if(slot == 10) return stack.getItem() == ModItems.DISCOVERED_RECIPE.get();
            return super.isItemValid(slot, stack);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 9, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 0 || index == 1,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 1000;
    public AssemblerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.ASSEMBLER_BLOCK_ENTITY.get(), pos , state);
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.factory.assembler");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AssemblerMenu(id,inventory,this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(AssemblerBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("Inventory", itemHandler.serializeNBT());
        nbt.putInt("progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        progress = nbt.getInt("progress");
        super.load(nbt);
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
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
