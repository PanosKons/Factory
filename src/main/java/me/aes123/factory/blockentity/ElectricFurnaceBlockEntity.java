package me.aes123.factory.blockentity;

import me.aes123.factory.blockentity.base.PoweredMachineBlockEntity;
import me.aes123.factory.blockentity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.screen.ElectricFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ElectricFurnaceBlockEntity extends PoweredMachineBlockEntity {
    private static final int ENERGY_REQ = 60;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;
    private BlockPos pos = this.getBlockPos().below();
    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.ELECTRIC_FURNACE_BLOCK_ENTITY.get(), pos , state, "container.factory.electric_furnace",(id, inventory, entity, data) -> new ElectricFurnaceMenu(id, inventory, entity, data), List.of(SlotType.INPUT, SlotType.SPECIAL), 100000);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ElectricFurnaceBlockEntity.this.progress;
                    case 1 -> ElectricFurnaceBlockEntity.this.maxProgress;
                    case 2 -> ElectricFurnaceBlockEntity.this.energyStorage.getEnergyStored();
                    case 3 -> ElectricFurnaceBlockEntity.this.energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ElectricFurnaceBlockEntity.this.progress = value;
                    case 1 -> ElectricFurnaceBlockEntity.this.maxProgress = value;
                    case 2 -> ElectricFurnaceBlockEntity.this.energyStorage.setEnergy(value);
                    case 3 -> {}
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("progress", this.progress);
        nbt.putInt("max_progress", this.maxProgress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        progress = nbt.getInt("progress");
        maxProgress = nbt.getInt("max_progress");
        super.load(nbt);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, ElectricFurnaceBlockEntity entity) {
        if(level.isClientSide()) {
            return;
        }
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        if(entity.energyStorage.getEnergyStored() >= ENERGY_REQ && level.hasNeighborSignal(pos)) {
            for (int y = entity.pos.getY(); y > -64; y--) {
                var bp = new BlockPos(entity.pos.getX(), y, entity.pos.getZ());
                if (level.getBlockState(bp).getBlock() != Blocks.AIR && level.getBlockState(bp).getBlock() != Blocks.BEDROCK) {
                    level.destroyBlock(bp, false);
                    entity.pos = bp.below();
                    entity.energyStorage.extractEnergy(ENERGY_REQ, false);
                    break;
                }

            }
        }
        setChanged(level, pos, state);
    }


}