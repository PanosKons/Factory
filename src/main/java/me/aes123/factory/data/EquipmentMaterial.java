package me.aes123.factory.data;

import net.minecraft.nbt.CompoundTag;

import java.util.Map;

public class EquipmentMaterial {
    public static Map<String, EquipmentMaterial> EQUIPMENT_MATERIALS = Map.of(
            "iron", new EquipmentMaterial(6,0.6f,6,3,1,900,2, 450),
            "gold", new EquipmentMaterial(22,0.5f,5,2,0.65f,150,2, 90),
            "diamond", new EquipmentMaterial(8,0.6f,8,4,0.9f,6000,4, 3000),
            "netherite", new EquipmentMaterial(16,0.5f,16,7,0.8f,18000,8, 9000)
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
    public void applyBaseStats(CompoundTag nbt, String toolName, boolean equipmentStationCraft) {

        nbt.putInt("HideFlags", 26);
        int durability = equipmentStationCraft ? baseDurability : baseDurability / 3;
        int armorDurability = equipmentStationCraft ? baseArmorDurability : baseArmorDurability / 3;

        int toolDurability = EquipmentModifier.ARMORS.contains(toolName) ? armorDurability : durability;
        nbt.putInt("base_max_durability", toolDurability);

        if(EquipmentModifier.TOOLS.contains(toolName))
        {
            nbt.putFloat("base_mining_speed", baseMiningSpeed);
        }
        if(EquipmentModifier.MELEE.contains(toolName))
        {
            nbt.putFloat("base_attack_cooldown", baseMeleeAttackCooldown);
            nbt.putFloat("base_attack_damage", baseMeleeAttackDamage);
        }
        if(EquipmentModifier.ARMORS.contains(toolName))
        {
            nbt.putFloat("base_armor", baseArmorPoints);
        }
        if(EquipmentModifier.PROJECTILE.contains(toolName))
        {
            nbt.putFloat("base_fire_rate", baseFireRate);
            nbt.putFloat("base_attack_damage", baseProjectileAttackDamage);
        }

        nbt.putInt("durability", nbt.getInt("base_max_durability"));
    }
}
