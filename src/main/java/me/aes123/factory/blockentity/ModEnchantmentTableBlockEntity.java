package me.aes123.factory.blockentity;

import me.aes123.factory.block.ModEnchantmentTableBlock;
import me.aes123.factory.blockentity.base.MachineBlockEntity;
import me.aes123.factory.blockentity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.init.ModBlocks;
import me.aes123.factory.item.BoosterItem;
import me.aes123.factory.item.equipment.base.ModEquipmentItem;
import me.aes123.factory.screen.ModEnchantmentMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ModEnchantmentTableBlockEntity extends MachineBlockEntity implements Nameable {
    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    public float rot;
    public float oRot;
    public float tRot;
    private static final RandomSource RANDOM = RandomSource.create();
    private Component name;

    public int XPstored;
    public int MaxXPstored;
    private final Random rnd;

    public ModEnchantmentTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), blockPos, blockState, "container.factory.enchanting_table", (id, inventory, entity, data) -> new ModEnchantmentMenu(id, inventory, entity, data), List.of(SlotType.INPUT, SlotType.INPUT));

        rnd = new Random();

        MaxXPstored = 10000;

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ModEnchantmentTableBlockEntity.this.XPstored;
                    case 1 -> ModEnchantmentTableBlockEntity.this.MaxXPstored;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ModEnchantmentTableBlockEntity.this.XPstored = value;
                    case 1 -> ModEnchantmentTableBlockEntity.this.MaxXPstored = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("xp_stored", this.XPstored);
        nbt.putInt("max_xp_stored", this.MaxXPstored);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", Component.Serializer.toJson(this.name));
        }

    }
    public void load(CompoundTag nbt) {
        super.load(nbt);
        XPstored = nbt.getInt("xp_stored");
        MaxXPstored = nbt.getInt("max_xp_stored");
        if (nbt.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(nbt.getString("CustomName"));
        }
    }

    @Override
    protected void clientTick(Level level, BlockPos pos, BlockState state) {
        bookAnimationTick(level, pos, state);
    }
    @Override
    protected void serverTick(Level level, BlockPos pos, BlockState state) {
        if(XPstored > MaxXPstored) XPstored = MaxXPstored;
        int speed = 1;
        for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
            if (ModEnchantmentTableBlock.isValidBookShelf(level, pos, blockpos)) {
                speed += level.getBlockState(pos.offset(blockpos)).getEnchantPowerBonus(level, pos.offset(blockpos));
            }
        }
        MaxXPstored = 100000 * speed;
        Player player = level.getNearestPlayer(pos.getX(), pos.getY(),pos.getZ(), 4.0, true);
        if(player != null && player.isCrouching())
        {
            if(player.totalExperience == 0 && player.invulnerableTime <= 0)
            {
                int amount = Math.min(speed, MaxXPstored - XPstored);
                player.hurt(player.damageSources().magic(), amount);
                XPstored += amount;
            }
            else {
                int amount = Math.min(Math.min(speed, player.totalExperience), MaxXPstored - XPstored);
                if (amount > 0) {
                    player.giveExperiencePoints(-amount);
                    XPstored += amount;
                }
            }
            setChanged(level, pos, state);
        }

        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack stack = inventory.getItem(0);
        ItemStack booster = inventory.getItem(1);

        if(booster.isEmpty()) {
            if (stack.hasTag() && stack.getItem() instanceof ModEquipmentItem item && stack.getTag().getFloat("mending") >= 1.0f) {

                float cost = stack.getTag().getFloat("xp_cost");
                int durability = stack.getTag().getInt("durability");

                if (cost < XPstored && durability < stack.getTag().getInt("max_durability")) {
                    XPstored -= cost;
                    stack.getTag().putInt("durability", durability + 1);
                    item.updateDurabilityBar(stack);
                    stack.getTag().putFloat("xp_cost", cost + 0.05f);

                    itemHandler.setStackInSlot(0, stack);
                    setChanged(level, pos, state);
                }
            }
        }
        else if (booster.getItem() instanceof BoosterItem) {

        }
    }

    public void bookAnimationTick(Level level, BlockPos blockPos, BlockState blockState) {
        oOpen = open;
        oRot = rot;
        Player player = level.getNearestPlayer((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, 3.0D, false);
        if (player != null) {
            double d0 = player.getX() - ((double)blockPos.getX() + 0.5D);
            double d1 = player.getZ() - ((double)blockPos.getZ() + 0.5D);
            tRot = (float) Mth.atan2(d1, d0);
            open += 0.1F;
            if (open < 0.5F || RANDOM.nextInt(40) == 0) {
                float f1 = flipT;

                do {
                    flipT += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while(f1 == flipT);
            }
        } else {
            tRot += 0.02F;
            open -= 0.1F;
        }

        while(rot >= (float)Math.PI) {
            rot -= ((float)Math.PI * 2F);
        }

        while(rot < -(float)Math.PI) {
            rot += ((float)Math.PI * 2F);
        }

        while(tRot >= (float)Math.PI) {
            tRot -= ((float)Math.PI * 2F);
        }

        while(tRot < -(float)Math.PI) {
            tRot += ((float)Math.PI * 2F);
        }

        float f2;
        for(f2 = tRot - rot; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
        }

        while(f2 < -(float)Math.PI) {
            f2 += ((float)Math.PI * 2F);
        }

        rot += f2 * 0.4F;
        open = Mth.clamp(open, 0.0F, 1.0F);
        ++time;
        oFlip = flip;
        float f = (flipT - flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -0.2F, 0.2F);
        flipA += (f - flipA) * 0.9F;
        flip += flipA;
    }

    public Component getName() {
        return (this.name != null ? this.name : Component.translatable("container.enchant"));
    }

    public void setCustomName(@Nullable Component p_59273_) {
        this.name = p_59273_;
    }

    @Nullable
    public Component getCustomName() {
        return this.name;
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockState state = getBlockState();
        Block block = state.getBlock();
        BlockPos pos = getBlockPos();
        if (block == ModBlocks.ENCHANTING_TABLE.get())
        {
            return new AABB(pos, pos.offset(1, 1, 1));
        }
        return super.getRenderBoundingBox();
    }
}
