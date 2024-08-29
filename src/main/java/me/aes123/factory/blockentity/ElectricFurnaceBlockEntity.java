package me.aes123.factory.blockentity;

import me.aes123.factory.block.GeneratorBlock;
import me.aes123.factory.blockentity.base.PoweredMachineBlockEntity;
import me.aes123.factory.blockentity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.recipe.AssemblerRecipe;
import me.aes123.factory.screen.ElectricFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static net.minecraft.world.item.crafting.RecipeType.SMELTING;

public class ElectricFurnaceBlockEntity extends PoweredMachineBlockEntity {
    private static final int ENERGY_REQ = 60;
    public int litTime;
    public int litDuration = 0;
    public int cookingProgress;
    public int cookingTotalTime;
    public int temperature;
    private BlockPos pos = this.getBlockPos().below();
    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;
    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(null /*ModBlockEntityType.ELECTRIC_FURNACE_BLOCK_ENTITY.get()*/, pos , state, "container.factory.electric_furnace",(id, inventory, entity, data) -> new ElectricFurnaceMenu(id, inventory, entity, data), List.of(SlotType.INPUT, SlotType.SPECIAL, SlotType.OUTPUT), 100000);
        this.quickCheck = RecipeManager.createCheck(SMELTING);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ElectricFurnaceBlockEntity.this.cookingProgress;
                    case 1 -> ElectricFurnaceBlockEntity.this.cookingTotalTime;
                    case 2 -> ElectricFurnaceBlockEntity.this.litTime;
                    case 3 -> ElectricFurnaceBlockEntity.this.litDuration;
                    case 4 -> ElectricFurnaceBlockEntity.this.energyStorage.getEnergyStored();
                    case 5 -> ElectricFurnaceBlockEntity.this.energyStorage.getMaxEnergyStored();
                    case 6 -> ElectricFurnaceBlockEntity.this.temperature;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ElectricFurnaceBlockEntity.this.cookingProgress = value;
                    case 1 -> ElectricFurnaceBlockEntity.this.cookingTotalTime = value;
                    case 2 -> ElectricFurnaceBlockEntity.this.litTime = value;
                    case 3 -> ElectricFurnaceBlockEntity.this.litDuration = value;
                    case 4 -> ElectricFurnaceBlockEntity.this.energyStorage.setEnergy(value);
                    case 5 -> {}
                    case 6 -> ElectricFurnaceBlockEntity.this.temperature = value;
                }
            }

            @Override
            public int getCount() {
                return 7;
            }
        };
    }
    protected int getBurnDuration(ItemStack p_58343_) {
        if (p_58343_.isEmpty()) {
            return 0;
        } else {
            return net.minecraftforge.common.ForgeHooks.getBurnTime(p_58343_, SMELTING);
        }
    }
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("BurnTime", this.litTime);
        nbt.putInt("CookTime", this.cookingProgress);
        nbt.putInt("CookTimeTotal", this.cookingTotalTime);
        nbt.putInt("Temperature", this.temperature);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.litTime = nbt.getInt("BurnTime");
        this.cookingProgress = nbt.getInt("CookTime");
        this.cookingTotalTime = nbt.getInt("CookTimeTotal");
        this.temperature = nbt.getInt("Temperature");
        var items = NonNullList.withSize(2, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, items);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, ElectricFurnaceBlockEntity entity) {
        if(level.isClientSide()) {
            return;
        }
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        int burnDuration = inventory.getItem(1).getItem() != Items.LAVA_BUCKET ? ForgeHooks.getBurnTime(inventory.getItem(1),RecipeType.SMELTING) : 0;
        if(entity.litTime == 0) {
            if (burnDuration > 0) {
                entity.litDuration = burnDuration;
                entity.itemHandler.extractItem(1, 1, false);
                entity.litTime = entity.litDuration;
                BlockEntity.setChanged(level, pos, state);
            }
        }
        else
        {
            entity.litTime--;
            entity.energyStorage.receiveEnergy(20,false);
            BlockEntity.setChanged(level, pos, state);
        }

        Optional<SmeltingRecipe> recipe = getRecipe(entity);
        if(recipe.isPresent()) {
            entity.cookingTotalTime = recipe.get().getCookingTime();
            if(entity.energyStorage.getEnergyStored() >= ENERGY_REQ)
            {
                entity.cookingProgress += 2;
                entity.energyStorage.extractEnergy(ENERGY_REQ, false);
            }

            if(entity.cookingProgress >= entity.cookingTotalTime)
            {
                craftItem(entity, recipe.get());
            }
        }
        else {
            entity.resetProgress();
        }

        setChanged(level, pos, state);
    }

    private void resetProgress() {
        this.cookingProgress = 0;
    }

    private static void craftItem(ElectricFurnaceBlockEntity entity, SmeltingRecipe recipe) {

        entity.itemHandler.extractItem(0,1,false);

        ItemStack newItemStack = new ItemStack(recipe.getResultItem(null).getItem(), entity.itemHandler.getStackInSlot(2).getCount() + recipe.getResultItem(null).getCount());

        newItemStack.setTag(recipe.getResultItem(null).getTag());
        entity.itemHandler.setStackInSlot(2, newItemStack );
        entity.resetProgress();
    }

    private static Optional<SmeltingRecipe> getRecipe(ElectricFurnaceBlockEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<SmeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(SMELTING, inventory, level);

        if(recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory,recipe.get().getResultItem(null).getCount()) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem(null))) return recipe;
        return Optional.empty();
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return ModBarrelBlockEntity.sameItem(inventory.getItem(2), itemStack) || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int InsertCount) {
        return inventory.getItem(2).getMaxStackSize() >= inventory.getItem(2).getCount() + InsertCount;
    }
}