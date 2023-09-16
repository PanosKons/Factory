package me.aes123.factory.blockentity;

import me.aes123.factory.blockentity.base.PoweredMachineBlockEntity;
import me.aes123.factory.blockentity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.screen.CrusherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

public class CrusherBlockEntity extends PoweredMachineBlockEntity {
    private static final int ENERGY_PROD = 20;

    private int progress = 0;
    private int maxProgress = 0;
    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.CRUSHER_BLOCK_ENTITY.get(), pos , state, "container.factory.crusher",(id, inventory, entity, data) -> new CrusherMenu(id, inventory, entity, data), List.of(SlotType.INPUT, SlotType.SPECIAL), 100000);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrusherBlockEntity.this.progress;
                    case 1 -> CrusherBlockEntity.this.maxProgress;
                    case 2 -> CrusherBlockEntity.this.energyStorage.getEnergyStored();
                    case 3 -> CrusherBlockEntity.this.energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrusherBlockEntity.this.progress = value;
                    case 1 -> CrusherBlockEntity.this.maxProgress = value;
                    case 2 -> CrusherBlockEntity.this.energyStorage.setEnergy(value);
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

    public static void tick(Level level, BlockPos pos, BlockState state, CrusherBlockEntity entity) {
        if(level.isClientSide()) {
            return;
        }

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        int burnTime = inventory.getItem(0).getItem() != Items.LAVA_BUCKET ? ForgeHooks.getBurnTime(inventory.getItem(0), RecipeType.SMELTING) : 0;
        if(entity.maxProgress == 0) {
            if (burnTime > 0) {
                entity.itemHandler.extractItem(0, 1, false);
                entity.maxProgress = burnTime / 2;
                setChanged(level, pos, state);
            }
        }
        else
        {
            entity.progress++;
            entity.energyStorage.receiveEnergy(ENERGY_PROD,false);
            if(entity.progress == entity.maxProgress)
            {
                entity.maxProgress = 0;
                entity.progress = 0;
                if (burnTime <= 0) {
                }
            }
            setChanged(level, pos, state);
        }

    }


}