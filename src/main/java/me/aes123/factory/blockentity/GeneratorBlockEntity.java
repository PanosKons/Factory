package me.aes123.factory.blockentity;

import me.aes123.factory.block.GeneratorBlock;
import me.aes123.factory.blockentity.base.PoweredMachineBlockEntity;
import me.aes123.factory.blockentity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.screen.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

public class GeneratorBlockEntity extends PoweredMachineBlockEntity {
    private static final int ENERGY_PROD = 20;
    private int progress = 0;
    private int maxProgress = 0;
    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.GENERATOR_BLOCK_ENTITY.get(), pos , state,"container.factory.generator",(id, inventory, entity, data) -> new GeneratorMenu(id, inventory, entity, data),
                List.of(SlotType.INPUT, SlotType.SPECIAL), 60000);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GeneratorBlockEntity.this.progress;
                    case 1 -> GeneratorBlockEntity.this.maxProgress;
                    case 2 -> GeneratorBlockEntity.this.energyStorage.getEnergyStored();
                    case 3 -> GeneratorBlockEntity.this.energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GeneratorBlockEntity.this.progress = value;
                    case 1 -> GeneratorBlockEntity.this.maxProgress = value;
                    case 2 -> GeneratorBlockEntity.this.energyStorage.setEnergy(value);
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

    @Override
    protected void serverTick(Level level, BlockPos pos, BlockState state) {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        int burnTime = inventory.getItem(0).getItem() != Items.LAVA_BUCKET ? ForgeHooks.getBurnTime(inventory.getItem(0),RecipeType.SMELTING) : 0;
        if(maxProgress == 0) {
            if (burnTime > 0) {
                itemHandler.extractItem(0, 1, false);
                maxProgress = burnTime / 2;
                level.setBlock(pos, state.setValue(GeneratorBlock.LIT, true), 3);
                BlockEntity.setChanged(level, pos, state);
            }
        }
        else
        {
            progress++;
            pushEnergy();
            energyStorage.receiveEnergy(ENERGY_PROD,false);
            if(progress == maxProgress)
            {
                maxProgress = 0;
                progress = 0;
                if (burnTime <= 0) {
                    level.setBlock(pos, state.setValue(GeneratorBlock.LIT, false), 3);
                }
            }
            BlockEntity.setChanged(level, pos, state);
        }

    }
}