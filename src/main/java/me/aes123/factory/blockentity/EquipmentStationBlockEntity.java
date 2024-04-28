package me.aes123.factory.blockentity;

import me.aes123.factory.block.EquipmentStationBlock;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.init.ModItems;
import me.aes123.factory.item.equipment.base.IEquipmentItem;
import me.aes123.factory.item.equipment.base.ModEquipmentItem;
import me.aes123.factory.screen.EquipmentStationMenu;
import me.aes123.factory.data.EquipmentMaterial;
import me.aes123.factory.data.EquipmentModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;

public class EquipmentStationBlockEntity extends BlockEntity implements MenuProvider {
    public static final Map<Item, String> ACCEPTED_MATERIALS = Map.of(Items.IRON_BLOCK, "iron_", Items.GOLD_BLOCK, "golden_", Items.DIAMOND_BLOCK, "diamond_", Items.NETHERITE_BLOCK, "netherite_");
    public static final Map<Item, String> ITEM_MATERIAL = Map.of(Items.IRON_BLOCK, "iron", Items.GOLD_BLOCK, "gold", Items.DIAMOND_BLOCK, "diamond", Items.NETHERITE_BLOCK, "netherite");
    public static final Map<Item, String> ACCEPTED_MOLDS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(ModItems.PICKAXE_MOLD.get(), "pickaxe"),
            new AbstractMap.SimpleEntry<>(ModItems.HAMMER_MOLD.get(), "hammer"),
            new AbstractMap.SimpleEntry<>(ModItems.AXE_MOLD.get(), "axe"),
            new AbstractMap.SimpleEntry<>(ModItems.SWORD_MOLD.get(), "sword"),
            new AbstractMap.SimpleEntry<>(ModItems.SHOVEL_MOLD.get(), "shovel"),
            new AbstractMap.SimpleEntry<>(ModItems.HOE_MOLD.get(), "hoe"),
            new AbstractMap.SimpleEntry<>(ModItems.PISTOL_MOLD.get(), "pistol"),
            new AbstractMap.SimpleEntry<>(ModItems.HELMET_MOLD.get(), "helmet"),
            new AbstractMap.SimpleEntry<>(ModItems.CHESTPLATE_MOLD.get(), "chestplate"),
            new AbstractMap.SimpleEntry<>(ModItems.LEGGINGS_MOLD.get(), "leggings"),
            new AbstractMap.SimpleEntry<>(ModItems.BOOTS_MOLD.get(), "boots"));
    private final ItemStackHandler itemHandler = new ItemStackHandler(13)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
            if(slot == 12)
                onOutputExtracted(this);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(slot < 10) return true; //Modifiers
            if(slot == 10) return true; // Iron, diamond, netherite blocks
            if(slot == 11) return true; // Molds, tools
            if(slot == 12) return false;
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
    public EquipmentStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.EQUIPMENT_STATION_BLOCK_ENTITY.get(), pos , state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> EquipmentStationBlockEntity.this.progress;
                    case 1 -> EquipmentStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> EquipmentStationBlockEntity.this.progress = value;
                    case 1 -> EquipmentStationBlockEntity.this.maxProgress = value;
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
        return Component.translatable("container.factory.equipment_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new EquipmentStationMenu(id,inventory,this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(EquipmentStationBlock.FACING);

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

    public static void onOutputExtracted(ItemStackHandler itemHandler)
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if(inventory.getItem(12).isEmpty() && hasRecipe(inventory))
        {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                itemHandler.extractItem(i,1,false);
            }
        }
    }
    public static void tick(Level level, BlockPos pos, BlockState state, EquipmentStationBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        if (hasRecipe(inventory)) {

            String toolName = ACCEPTED_MOLDS.get(inventory.getItem(11).getItem());
            String namespace = inventory.getItem(11).getItem() == ModItems.HAMMER_MOLD.get() || inventory.getItem(11).getItem() == ModItems.PISTOL_MOLD.get() ? "factory:" : "minecraft:";
            ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace + ACCEPTED_MATERIALS.get(inventory.getItem(10).getItem()) + toolName)));

            CompoundTag nbt = new CompoundTag();
            EquipmentMaterial.EQUIPMENT_MATERIALS.get(ITEM_MATERIAL.get(inventory.getItem(10).getItem())).addBaseMaterial(nbt, toolName, true);

            List<EquipmentModifier> modifiersToAdd = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Item item = inventory.getItem(i).getItem();
                if (!EquipmentModifier.EQUIPMENT_MODIFIERS.containsKey(item)) continue;

                List<EquipmentModifier> itemModifiers = EquipmentModifier.EQUIPMENT_MODIFIERS.get(item);

                for (var modifier : itemModifiers) {
                    //if (modifier.modifierType == null) continue;
                    if (modifier.modifierType != null && !modifier.modifierType.applicableTools.contains(toolName)) continue;
                    var optional = modifiersToAdd.stream().filter((m) -> m.modifierType == modifier.modifierType).findFirst();
                    if (optional.isPresent()) {
                        int oldLevel = optional.get().level;
                        modifiersToAdd.remove(optional.get());
                        if(modifier.modifierType != null)
                            modifiersToAdd.add(new EquipmentModifier(modifier.modifierType, Math.min(oldLevel + modifier.level, modifier.modifierType.maxLevel)));
                    } else {
                        modifiersToAdd.add(modifier);
                    }
                }
            }
            for (var modifier : modifiersToAdd) {
                modifier.add(nbt);
            }

            stack.setTag(nbt);
            var item = (IEquipmentItem)stack.getItem();
            item.setDurability((int)item.getModifierValue(EquipmentModifier.EquipmentModifierType.MAX_DURABILITY, stack) ,stack);
            entity.itemHandler.setStackInSlot(12, stack);
        }
         else{
             entity.itemHandler.setStackInSlot(12, new ItemStack(Items.AIR));
         }
    }

    public static boolean hasRecipe(SimpleContainer inventory)
    {
        int count = 0;
        for (int i = 0; i < 10; i++)
        {
            if(!inventory.getItem(i).isEmpty()) count++;
        }
        if(!inventory.getItem(11).hasTag()) return false;
        int capacity = inventory.getItem(11).getTag().getInt("capacity");
        return ACCEPTED_MATERIALS.containsKey(inventory.getItem(10).getItem()) && ACCEPTED_MOLDS.containsKey(inventory.getItem(11).getItem()) && count <= capacity;
    }
}