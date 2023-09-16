package me.aes123.factory.blockentity.base;

import me.aes123.factory.block.DiscoveryStationBlock;
import me.aes123.factory.blockentity.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class MachineBlockEntity extends AbstractModBlockEntity {

    public interface AbstractContainerMenuFactory<T>
    {
        T newObject(int id, Inventory inventory, BlockEntity blockEntity, ContainerData containerData);
    }

    private String translationName;
    protected ContainerData data;
    private AbstractContainerMenuFactory<? extends AbstractContainerMenu> factory;
    protected final ItemStackHandler itemHandler;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap;

    private List<SlotType> slots;

    public MachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState state, String translationName, AbstractContainerMenuFactory<? extends AbstractContainerMenu> factory, List<SlotType> slots) {
        super(blockEntityType, blockPos, state);
        this.translationName = translationName;
        this.factory = factory;
        this.slots = slots;
        itemHandler = new ItemStackHandler(slots.size())
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return slot <= slots.size();
            }
        };
        directionWrappedHandlerMap =
                Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> this.slots.get(i) == SlotType.OUTPUT, (i, s) -> false)),
                        Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> this.slots.get(i) == SlotType.INPUT)),
                        Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> false)),
                        Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> this.slots.get(i) == SlotType.SPECIAL)),
                        Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> this.slots.get(i) == SlotType.INPUT)),
                        Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> this.slots.get(i) == SlotType.INPUT)));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(DiscoveryStationBlock.FACING);

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
    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("Inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("Inventory"));
        super.load(nbt);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(translationName);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return factory.newObject(id,inventory, this, this.data);
    }

    protected void clientTick(Level level, BlockPos pos, BlockState state) {}
    protected void serverTick(Level level, BlockPos pos, BlockState state) {}

    public static void clientTick(Level level, BlockPos pos, BlockState state, MachineBlockEntity entity)
    {
        entity.clientTick(level, pos, state);
    }
    public static void serverTick(Level level, BlockPos pos, BlockState state, MachineBlockEntity entity)
    {
        entity.serverTick(level, pos, state);
    }
}
