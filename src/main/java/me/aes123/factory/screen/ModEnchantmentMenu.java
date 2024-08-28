package me.aes123.factory.screen;

import me.aes123.factory.block.ModEnchantmentTableBlock;
import me.aes123.factory.blockentity.ModEnchantmentTableBlockEntity;
import me.aes123.factory.init.ModBlocks;
import me.aes123.factory.init.ModMenuTypes;
import me.aes123.factory.item.equipment.base.IEquipmentItem;
import me.aes123.factory.util.ModTags;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModEnchantmentMenu extends AbstractContainerMenu {
    private final Container enchantSlots = new SimpleContainer(4) {
        public void setChanged() {
            super.setChanged();
            ModEnchantmentMenu.this.slotsChanged(this);
        }
    };
    private final ContainerLevelAccess access;
    private final RandomSource random = RandomSource.create();
    private final DataSlot enchantmentSeed = DataSlot.standalone();
    public final int[] costs = new int[3];
    public final int[] enchantClue = new int[]{-1, -1, -1};
    public final int[] levelClue = new int[]{-1, -1, -1};

    public ModEnchantmentMenu(int p_39454_, Inventory p_39455_, FriendlyByteBuf friendlyByteBuf) {
        this(p_39454_, p_39455_, ContainerLevelAccess.NULL);
    }

    public ModEnchantmentMenu(int p_39457_, Inventory p_39458_, ContainerLevelAccess p_39459_) {
        super(ModMenuTypes.ENCHANTMENT_MENU.get(), p_39457_);
        this.access = p_39459_;
        this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) {
            public boolean mayPlace(ItemStack p_39508_) {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) {
            public boolean mayPlace(ItemStack p_39517_) {
                return p_39517_.is(net.minecraftforge.common.Tags.Items.ENCHANTING_FUELS);
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 2, 15, 47 + 20) {
            public boolean mayPlace(ItemStack p_39517_) {
                return p_39517_.is(Items.ENCHANTED_BOOK);
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 3, 35, 47 + 20) {
            public boolean mayPlace(ItemStack p_39517_) {
                return p_39517_.is(ModTags.Items.BOOSTER_ITEMS);
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(p_39458_, j + i * 9 + 9, 8 + j * 18, 84 + 14 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(p_39458_, k, 8 + k * 18, 142 + 14));
        }

        this.addDataSlot(DataSlot.shared(this.costs, 0));
        this.addDataSlot(DataSlot.shared(this.costs, 1));
        this.addDataSlot(DataSlot.shared(this.costs, 2));
        this.addDataSlot(this.enchantmentSeed).set(p_39458_.player.getEnchantmentSeed());
        this.addDataSlot(DataSlot.shared(this.enchantClue, 0));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 1));
        this.addDataSlot(DataSlot.shared(this.enchantClue, 2));
        this.addDataSlot(DataSlot.shared(this.levelClue, 0));
        this.addDataSlot(DataSlot.shared(this.levelClue, 1));
        this.addDataSlot(DataSlot.shared(this.levelClue, 2));
    }

    public void slotsChanged(Container container) {
        if (container == this.enchantSlots) {
            ItemStack itemToEnchant = container.getItem(0);
            ItemStack enchantedBook = container.getItem(2);
            if (enchantedBook.is(Items.ENCHANTED_BOOK) && !itemToEnchant.isEmpty() && (itemToEnchant.getItem() instanceof IEquipmentItem || itemToEnchant.is(Items.TRIDENT)) && isCompatible(enchantedBook, itemToEnchant)) {
                this.access.execute((level, blockPos) -> {
                    float enchantPower = 0;

                    for(BlockPos blockpos : ModEnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                        if (ModEnchantmentTableBlock.isValidBookShelf(level, blockPos, blockpos)) {
                            enchantPower += level.getBlockState(blockPos.offset(blockpos)).getEnchantPowerBonus(level, blockPos.offset(blockpos));
                        }
                    }

                    ListTag enchantments = EnchantedBookItem.getEnchantments(enchantedBook);
                    CompoundTag enchantment = enchantments.getCompound(0);
                    int EncLevel = enchantment.getInt("lvl");
                    String enchid = enchantment.getString("id");

                    Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchid));
                    int id = -1;
                    for (int i = 0; i < ModTags.Items.AllowedEnchantments.size(); i++){
                        if(ench == ModTags.Items.AllowedEnchantments.get(i))
                        {
                            id = i;
                            break;
                        }
                    }


                    for(int k = 0; k < 3; ++k) {
                        this.costs[k] = 10 * (k + 1); ///////////////////
                        this.enchantClue[k] = id;
                        this.levelClue[k] = EncLevel;
                    }

                    this.broadcastChanges();
                });
            } else {
                for(int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }

    }

    private boolean isCompatible(ItemStack enchantedBook, ItemStack itemToEnchant) {

        ListTag enchantments = EnchantedBookItem.getEnchantments(enchantedBook);
        CompoundTag enchantment = enchantments.getCompound(0);
        int EncLevel = enchantment.getInt("lvl");
        String enchid = enchantment.getString("id");

        Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchid));
        EnchantmentInstance enchantmentInstance = new EnchantmentInstance(ench,EncLevel);

        if(!ModTags.Items.AllowedEnchantments.contains(ench)) return false;
        if(!ench.canEnchant(itemToEnchant)) return false;
        var enchantmentss = itemToEnchant.getAllEnchantments();
        boolean ret = true;
        for(var value : enchantmentss.entrySet())
        {
            if(value.getKey() == ench) continue;
            if(!ench.isCompatibleWith(value.getKey()))
            {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public boolean clickMenuButton(Player player, int button) {
        if (button >= 0 && button < this.costs.length) {
            ItemStack itemToEnchant = this.enchantSlots.getItem(0);
            ItemStack lapis = this.enchantSlots.getItem(1);
            ItemStack enchBook = this.enchantSlots.getItem(2);
            int i = button + 1;
            if ((lapis.isEmpty() || lapis.getCount() < i) && !player.getAbilities().instabuild) {
                return false;
            } else if (this.costs[button] <= 0 || itemToEnchant.isEmpty() || (player.experienceLevel < i || player.experienceLevel < this.costs[button]) && !player.getAbilities().instabuild) {
                return false;
            } else {
                this.access.execute((level, blockPos) -> {
                    ItemStack itemForEnchant = itemToEnchant;
                    ListTag enchantments = EnchantedBookItem.getEnchantments(enchBook);
                    CompoundTag enchantment = enchantments.getCompound(0);
                    int EncLevel = enchantment.getInt("lvl");
                    String enchid = enchantment.getString("id");

                    Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchid));
                    EnchantmentInstance enchantmentInstance = new EnchantmentInstance(ench,EncLevel);
                        player.giveExperiencePoints(-(this.costs[button] + 6) * this.costs[button]);

                        int bonus = 0;
                        if(itemForEnchant.getAllEnchantments().containsKey(enchantmentInstance.enchantment) && itemForEnchant.getAllEnchantments().get(enchantmentInstance.enchantment) == enchantmentInstance.level) bonus++;
                        itemForEnchant.enchant(enchantmentInstance.enchantment, enchantmentInstance.level + bonus);

                        if (!player.getAbilities().instabuild) {
                            lapis.shrink(i);
                            enchBook.shrink(1);
                            if (lapis.isEmpty()) {
                                this.enchantSlots.setItem(1, ItemStack.EMPTY);
                            }
                            if (enchBook.isEmpty()) {
                                this.enchantSlots.setItem(2, ItemStack.EMPTY);
                            }
                        }

                        player.awardStat(Stats.ENCHANT_ITEM);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, itemForEnchant, i);
                        }

                        this.enchantSlots.setChanged();
                        this.slotsChanged(this.enchantSlots);
                        level.playSound((Player)null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);

                });
                return true;
            }
        } else {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + button);
            return false;
        }
    }

    public int getGoldCount() {
        ItemStack itemstack = this.enchantSlots.getItem(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }


    public void removed(Player p_39488_) {
        super.removed(p_39488_);
        this.access.execute((p_39469_, p_39470_) -> {
            this.clearContainer(p_39488_, this.enchantSlots);
        });
    }

    public boolean stillValid(Player p_39463_) {
        return stillValid(this.access, p_39463_, ModBlocks.ENCHANTING_TABLE.get());
    }

    public ItemStack quickMoveStack(Player p_39490_, int p_39491_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39491_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39491_ == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 40, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_39491_ == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 40, true)) {
                    return ItemStack.EMPTY;
                }
            }else if (p_39491_ == 2) {
                if (!this.moveItemStackTo(itemstack1, 2, 40, true)) {
                    return ItemStack.EMPTY;
                }
            }else if (p_39491_ == 3) {
                if (!this.moveItemStackTo(itemstack1, 2, 40, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (itemstack1.is(net.minecraftforge.common.Tags.Items.ENCHANTING_FUELS)) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            }else if (itemstack1.is(Items.ENCHANTED_BOOK)) {
                if (!this.moveItemStackTo(itemstack1, 1, 3, true)) {
                    return ItemStack.EMPTY;
                }
            }else if (itemstack1.is(ModTags.Items.BOOSTER_ITEMS)) {
                if (!this.moveItemStackTo(itemstack1, 1, 4, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1.copyWithCount(1);
                itemstack1.shrink(1);
                this.slots.get(0).setByPlayer(itemstack2);
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_39490_, itemstack1);
        }

        return itemstack;
    }
}
