package me.aes123.factory.block.entity.base;

import me.aes123.factory.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class PoweredMachineBlockEntity extends MachineBlockEntity {

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    protected final ModEnergyStorage energyStorage;

    public PoweredMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState state, String translationName, AbstractContainerMenuFactory<? extends AbstractContainerMenu> factory, List<SlotType> slots, int capacity) {
        super(blockEntityType, blockPos, state, translationName, factory, slots);
        energyStorage = new ModEnergyStorage(capacity,2000)
        {
            @Override
            public void onEnergyChanged()
            {
                setChanged();
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY)
        {
            if (side == null) {
                return lazyEnergyHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("energy", energyStorage.getEnergyStored());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        energyStorage.setEnergy(nbt.getInt("energy"));
        super.load(nbt);
    }

    protected void pushEnergy() {

        // Transmit power out all sides.
        for (Direction side : Direction.values()) {
            // Get our energy handler, this will handle all sidedness tests for us.
            getCapability(ForgeCapabilities.ENERGY, side).resolve().ifPresent(selfHandler -> {
                // Get the other energy handler
                Optional<IEnergyStorage> otherHandler = getNeighbouringCapability(ForgeCapabilities.ENERGY, side).resolve();
                if (otherHandler.isPresent() && energyStorage.getEnergyStored() >= 20)
                {
                    otherHandler.get().receiveEnergy(20,false);

                    energyStorage.extractEnergy(20, false);
                }
            });
        }
    }

    private LazyOptional<IEnergyStorage> getNeighbouringCapability(Capability<IEnergyStorage> energy, Direction side) {
        for (Direction direction : Direction.values()) {
            BlockEntity neighbor = this.level.getBlockEntity(worldPosition.relative(direction));
            if(neighbor != null)
                return neighbor.getCapability(energy, side);
        }
        return LazyOptional.empty();
    }
}
