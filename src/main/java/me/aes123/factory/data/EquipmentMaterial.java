package me.aes123.factory.data;

import net.minecraft.nbt.CompoundTag;

import java.util.Map;

public class EquipmentMaterial {
    public static Map<String, EquipmentMaterial> EQUIPMENT_MATERIALS = Map.of(
            "iron", new EquipmentMaterial(6,0.6f,6,3,1,900,8, 300),
            "gold", new EquipmentMaterial(12,0.5f,5,2,0.75f,90,6, 30),
            "diamond", new EquipmentMaterial(8,0.6f,8,4,0.9f,3600,10, 1200),
            "netherite", new EquipmentMaterial(9,0.6f,10,5,0.85f,9000,12, 3000)
    );

    public final float baseMiningSpeed;
    public final float baseMeleeAttackCooldown;
    public final float baseMeleeAttackDamage;
    public final float baseArmorPoints;
    public final float baseFireRate;
    public final float baseProjectileAttackDamage;
    public final int baseDurability;
    public final int baseArmorDurability;

    public EquipmentMaterial(float baseMiningSpeed, float baseMeleeAttackCooldown, float baseMeleeAttackDamage, float baseArmorPoints, float baseFireRate, int baseDurability, float baseProjectileAttackDamage, int baseArmorDurability) {
        this.baseMiningSpeed = baseMiningSpeed;
        this.baseMeleeAttackCooldown = baseMeleeAttackCooldown;
        this.baseMeleeAttackDamage = baseMeleeAttackDamage;
        this.baseArmorPoints = baseArmorPoints;
        this.baseFireRate = baseFireRate;
        this.baseProjectileAttackDamage = baseProjectileAttackDamage;
        this.baseDurability = baseDurability;
        this.baseArmorDurability = baseArmorDurability;
    }
    public void addBaseMaterial(CompoundTag nbt, String toolName, boolean equipmentStationCraft) {

        nbt.putInt("HideFlags", 26);
        int durability = equipmentStationCraft ? baseDurability : baseDurability / 3;
        int armorDurability = equipmentStationCraft ? baseArmorDurability : baseArmorDurability / 3;
        nbt.putInt("max_durability", durability);

        if(EquipmentModifier.TOOLS.contains(toolName))
        {
            nbt.putFloat("mining_speed", baseMiningSpeed);
        }
        if(EquipmentModifier.MELEE.contains(toolName))
        {
            nbt.putFloat("attack_cooldown", baseMeleeAttackCooldown);
            nbt.putFloat("attack_damage", baseMeleeAttackDamage);
        }
        if(EquipmentModifier.ARMORS.contains(toolName))
        {
            nbt.putFloat("armor", baseArmorPoints);
            nbt.putInt("durability", armorDurability);
        }
        if(EquipmentModifier.PROJECTILE.contains(toolName))
        {
            nbt.putFloat("fire_rate", baseFireRate);
            nbt.putFloat("attack_damage", baseProjectileAttackDamage);
        }

        nbt.putInt("durability", nbt.getInt("max_durability"));
    }
}
