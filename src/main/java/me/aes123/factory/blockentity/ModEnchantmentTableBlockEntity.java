package me.aes123.factory.blockentity;

import me.aes123.factory.block.ModEnchantmentTableBlock;
import me.aes123.factory.blockentity.base.SlotType;
import me.aes123.factory.init.ModBlockEntityType;
import me.aes123.factory.init.ModBlocks;
import me.aes123.factory.item.BoosterItem;
import me.aes123.factory.item.equipment.base.IEquipmentItem;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static me.aes123.factory.data.EquipmentModifier.EquipmentModifierType.MAX_DURABILITY;

public class ModEnchantmentTableBlockEntity extends BlockEntity implements Nameable {
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

    public ModEnchantmentTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", Component.Serializer.toJson(this.name));
        }

    }
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(nbt.getString("CustomName"));
        }
    }

    private int getXpCapacity(int enchantingPower)
    {
        if(enchantingPower <= 5)
        {
            return 1000 * enchantingPower;
        }
        else if(enchantingPower <= 10)
        {
            return 5000 + 2000 * (enchantingPower - 5);
        }
        else
        {
            return 15000 + 5000 * (enchantingPower - 10);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState p_155506_, ModEnchantmentTableBlockEntity p_155507_)
    {
    }
    public static void bookAnimationTick(Level level, BlockPos pos, BlockState p_155506_, ModEnchantmentTableBlockEntity p_155507_) {

        p_155507_.oOpen = p_155507_.open;
        p_155507_.oRot = p_155507_.rot;
        Player player = level.getNearestPlayer((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 3.0D, false);
        if (player != null) {
            double d0 = player.getX() - ((double)pos.getX() + 0.5D);
            double d1 = player.getZ() - ((double)pos.getZ() + 0.5D);
            p_155507_.tRot = (float)Mth.atan2(d1, d0);
            p_155507_.open += 0.1F;
            if (p_155507_.open < 0.5F || RANDOM.nextInt(40) == 0) {
                float f1 = p_155507_.flipT;

                do {
                    p_155507_.flipT += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while(f1 == p_155507_.flipT);
            }
        } else {
            p_155507_.tRot += 0.02F;
            p_155507_.open -= 0.1F;
        }

        while(p_155507_.rot >= (float)Math.PI) {
            p_155507_.rot -= ((float)Math.PI * 2F);
        }

        while(p_155507_.rot < -(float)Math.PI) {
            p_155507_.rot += ((float)Math.PI * 2F);
        }

        while(p_155507_.tRot >= (float)Math.PI) {
            p_155507_.tRot -= ((float)Math.PI * 2F);
        }

        while(p_155507_.tRot < -(float)Math.PI) {
            p_155507_.tRot += ((float)Math.PI * 2F);
        }

        float f2;
        for(f2 = p_155507_.tRot - p_155507_.rot; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
        }

        while(f2 < -(float)Math.PI) {
            f2 += ((float)Math.PI * 2F);
        }

        p_155507_.rot += f2 * 0.4F;
        p_155507_.open = Mth.clamp(p_155507_.open, 0.0F, 1.0F);
        ++p_155507_.time;
        p_155507_.oFlip = p_155507_.flip;
        float f = (p_155507_.flipT - p_155507_.flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -0.2F, 0.2F);
        p_155507_.flipA += (f - p_155507_.flipA) * 0.9F;
        p_155507_.flip += p_155507_.flipA;
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
