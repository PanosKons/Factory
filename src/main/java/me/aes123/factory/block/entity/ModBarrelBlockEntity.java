package me.aes123.factory.block.entity;

import me.aes123.factory.block.ModBarrelBlock;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.item.ModBundleItem;
import me.aes123.factory.util.ModRarity;
import me.aes123.factory.world.inventory.ModChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModBarrelBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items;
    private ModRarity rarity;
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
            ModBarrelBlockEntity.this.playSound(p_155064_, SoundEvents.BARREL_OPEN);
            ModBarrelBlockEntity.this.updateBlockState(p_155064_, true);
        }

        protected void onClose(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {
            ModBarrelBlockEntity.this.playSound(p_155074_, SoundEvents.BARREL_CLOSE);
            ModBarrelBlockEntity.this.updateBlockState(p_155074_, false);
        }

        protected void openerCountChanged(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
        }

        protected boolean isOwnContainer(Player p_155060_) {
            if (p_155060_.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu)p_155060_.containerMenu).getContainer();
                return container == ModBarrelBlockEntity.this;
            } else {
                return false;
            }
        }
    };
    private static BlockEntityType<?> determineBlockEntity(BlockState blockState)
    {
        return switch (((ModBarrelBlock)blockState.getBlock()).rarity) {
            case IMPROVED -> ModBlockEntityType.IMPROVED_BARREL_BLOCK_ENTITY.get();
            case PROFOUND -> ModBlockEntityType.PROFOUND_BARREL_BLOCK_ENTITY.get();
            case REINFORCED -> ModBlockEntityType.REINFORCED_BARREL_BLOCK_ENTITY.get();
        };

    }
    public ModBarrelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(determineBlockEntity(blockState), blockPos, blockState);
        this.rarity = ((ModBarrelBlock)blockState.getBlock()).rarity;
        int inventorySize = switch(this.rarity)
        {
            case IMPROVED -> 36;
            case PROFOUND -> 45;
            case REINFORCED -> 54;
        };
        this.items = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
    }

    protected void saveAdditional(CompoundTag p_187459_) {
        super.saveAdditional(p_187459_);
        if (!this.trySaveLootTable(p_187459_)) {
            ContainerHelper.saveAllItems(p_187459_, this.items);
        }

    }

    public void load(CompoundTag p_155055_) {
        super.load(p_155055_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_155055_)) {
            ContainerHelper.loadAllItems(p_155055_, this.items);
        }

    }

    public int getContainerSize() {
        return items.size();
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> p_58610_) {
        this.items = p_58610_;
    }

    protected Component getDefaultName() {
        return Component.translatable("container.factory." + rarity.name + "_barrel");
    }

    protected AbstractContainerMenu createMenu(int p_58598_, Inventory inventory) {
        return switch(this.rarity) {
                    case IMPROVED -> ModChestMenu.fourRows(p_58598_, inventory, this);
                    case PROFOUND -> ModChestMenu.fiveRows(p_58598_, inventory, this);
                    case REINFORCED -> ModChestMenu.sixRows(p_58598_, inventory, this);
                };
    }

    public void startOpen(Player p_58616_) {
        if (!this.remove && !p_58616_.isSpectator()) {
            this.openersCounter.incrementOpeners(p_58616_, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void stopOpen(Player p_58614_) {
        if (!this.remove && !p_58614_.isSpectator()) {
            this.openersCounter.decrementOpeners(p_58614_, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    void updateBlockState(BlockState p_58607_, boolean p_58608_) {
        this.level.setBlock(this.getBlockPos(), p_58607_.setValue(ModBarrelBlock.OPEN, Boolean.valueOf(p_58608_)), 3);
    }

    void playSound(BlockState p_58601_, SoundEvent p_58602_) {
        Vec3i vec3i = p_58601_.getValue(ModBarrelBlock.FACING).getNormal();
        double d0 = (double)this.worldPosition.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double d1 = (double)this.worldPosition.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double d2 = (double)this.worldPosition.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        this.level.playSound((Player)null, d0, d1, d2, p_58602_, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    public void addPlayerItems(Level level, BlockPos pos, BlockState state, Player player)
    {
        if(level.isClientSide()) {
            return;
        }
        for(int i =0; i < player.getInventory().getContainerSize(); i++)
        {
            ItemStack stack = player.getInventory().getItem(i);
            if(stack.getItem() instanceof BundleItem || stack.getItem() instanceof ModBundleItem)
            {

                CompoundTag compoundtag = stack.getOrCreateTag();
                if (!compoundtag.contains("Items")) continue;
                ListTag listtag = compoundtag.getList("Items", 10);
                if (listtag.isEmpty()) continue;

                List<ItemStack> inv = new ArrayList<>();
                for(int j = 0; j < listtag.size(); j++) {
                    CompoundTag compoundtag1 = listtag.getCompound(j);
                    inv.add(ItemStack.of(compoundtag1));
                }

                for(int j = 0; j < inv.size(); j++) {
                    addPlayerItem(inv.get(j));
                }

                listtag.clear();
                for(int k = 0; k < inv.size(); k++)
                {
                    if(inv.get(k).isEmpty()) continue;
                    CompoundTag compoundtag2 = new CompoundTag();
                    inv.get(k).save(compoundtag2);
                    listtag.add(k, compoundtag2);
                }

                compoundtag.put("Items", listtag);
                if (listtag.isEmpty()) {
                    stack.removeTagKey("Items");
                }

            }
            addPlayerItem(stack);
        }
        setChanged(level,pos,state);
    }

    private void addPlayerItem(ItemStack stack)
    {
        if(stack.isEmpty()) return;
        if(stack.isStackable() == false) return;
        if(items.stream().anyMatch(itemStack -> itemStack.getItem() == stack.getItem()) == false) return;
        while(stack.getCount() > 0) {
            int index = getFirstAvaiableIndex(items, stack);
            if (index == -1) return;
            int transferCount = Math.min(stack.getMaxStackSize() - items.get(index).getCount(), stack.getCount());
            int totalCount = items.get(index).getCount() + transferCount;
            items.set(index, stack.copy());
            items.get(index).setCount(totalCount);
            stack.setCount(stack.getCount() - transferCount);
        }
    }

    private static int getFirstAvaiableIndex(NonNullList<ItemStack> items, ItemStack stack) {
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getCount() < items.get(i).getMaxStackSize() && sameItem(items.get(i), stack)) return i;
        }
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).isEmpty()) return i;
        }
        return -1;
    }

    public static boolean sameItem(ItemStack f, ItemStack s)
    {
        boolean cond1 = f.hasTag() == true && s.hasTag() == true && f.getTag() == s.getTag();
        boolean cond2 = f.hasTag() == false && s.hasTag() == false;

        return f.getItem() == s.getItem() && (cond1 || cond2);
    }
}
