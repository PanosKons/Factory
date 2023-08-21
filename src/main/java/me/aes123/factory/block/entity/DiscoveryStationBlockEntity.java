package me.aes123.factory.block.entity;

import me.aes123.factory.block.entity.base.MachineBlockEntity;
import me.aes123.factory.block.entity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.init.ModItems;
import me.aes123.factory.item.recipe.UndiscoveredRecipeItem;
import me.aes123.factory.screen.DiscoveryStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;
import java.util.Optional;

public class DiscoveryStationBlockEntity extends MachineBlockEntity {
    private int progress = 0;
    private int maxProgress = 20000;
    public DiscoveryStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.DISCOVERY_STATION_BLOCK_ENTITY.get(), pos , state, "container.factory.discovery_station", (id, inventory, entity, data) -> new DiscoveryStationMenu(id, inventory, entity, data),
                List.of(SlotType.SPECIAL, SlotType.INPUT, SlotType.OUTPUT));

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DiscoveryStationBlockEntity.this.progress;
                    case 1 -> DiscoveryStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DiscoveryStationBlockEntity.this.progress = value;
                    case 1 -> DiscoveryStationBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

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

    @Override
    protected void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            return;
        }
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (inventory.getItem(1).isEmpty() && inventory.getItem(0).getItem() == ModItems.UNDISCOVERED_RECIPE.get()) {

            if(inventory.getItem(0).hasTag() &&inventory.getItem(0).getTag().contains("progress") && inventory.getItem(0).getTag().contains("max_progress") && inventory.getItem(0).getTag().contains("recipe")) {
                progress = inventory.getItem(0).getTag().getInt("progress");
                maxProgress = inventory.getItem(0).getTag().getInt("max_progress");

                if (inventory.getItem(2).getItem() == ModItems.EXPERTISE.get()) {
                    itemHandler.extractItem(2, 1, false);
                    progress++;
                    if (progress >= maxProgress) {
                        ItemStack stack = new ItemStack(ModItems.DISCOVERED_RECIPE.get());
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("recipe", inventory.getItem(0).getTag().getString("recipe"));
                        stack.setTag(nbt);
                        itemHandler.setStackInSlot(1, stack);

                        itemHandler.extractItem(0, 1, false);
                    } else {
                        ItemStack stack = inventory.getItem(0).copy();
                        stack.getTag().putInt("progress", progress);
                        UndiscoveredRecipeItem.updateBar(stack);
                        itemHandler.setStackInSlot(0, stack);
                    }
                }
            }
            setChanged(level, pos, state);
        } else {
            progress = 0;
            maxProgress = 0;
            setChanged(level, pos, state);
        }
    }
}
