package me.aes123.factory.data;

import java.util.List;

public class EquipmentModifier {
    public static final List<String> TOOLS = List.of("pickaxe", "axe", "hoe", "shovel", "hammer");
    public static final List<String> PICKAXE = List.of("pickaxe");
    public static final List<String> MELEE = List.of("sword");
    public static final List<String> HAMMER = List.of("hammer");
    public static final List<String> PROJECTILE = List.of("pistol");
    public static final List<String> WEAPONS = List.of("pistol", "sword");
    public static final List<String> ARMORS = List.of("helmet", "chestplate", "leggings", "boots");
    public static final List<String> HOLDABLE = List.of("pistol", "sword", "pickaxe", "axe", "hoe", "shovel", "hammer");
    public static final List<String> ALL = List.of("helmet", "chestplate", "leggings", "boots", "pistol", "sword", "pickaxe", "axe", "hoe", "shovel", "hammer");

    public enum EquipmentModifierType
    {
        MAX_DURABILITY(ALL,10), MENDING(ALL,1, DisplayFormat.ONLY_TYPE),
        MINING_SPEED(TOOLS,50), FIRE_RATE(PROJECTILE,50), ATTACK_COOLDOWN(MELEE,50), MOVEMENT_SPEED(ARMORS, 50),
        SILK_TOUCH(TOOLS,1, DisplayFormat.ONLY_TYPE),
        REGENERATION(ARMORS, 4),
        REINFORCED(PICKAXE, 10),
        REACH(HOLDABLE, 20),
        MINE_AOE(HAMMER, 1, DisplayFormat.ONLY_TYPE), ATTACK_AOE(MELEE,3), THORNS(ARMORS,3),
        ATTACK_DAMAGE(WEAPONS, 10), ARMOR(ARMORS,30),
        KNOCKBACK(WEAPONS,2), KNOCKBACK_RESISTANCE(ARMORS,10),
        XP_BOOST(HOLDABLE, 50),
        MAGNETIC(HOLDABLE, 6);

        public enum DisplayFormat
        {
            DEFAULT, ONLY_TYPE
        }

        public final List<String> applicableTools;
        public final int maxLevel;
        public final DisplayFormat format;

        EquipmentModifierType(List<String> applicableTools, int maxLevel)
        {
            this.applicableTools = applicableTools;
            this.maxLevel = maxLevel;
            this.format = DisplayFormat.DEFAULT;
        }
        EquipmentModifierType(List<String> applicableTools, int maxLevel, DisplayFormat format)
        {
            this.applicableTools = applicableTools;
            this.maxLevel = maxLevel;
            this.format = format;
        }
    }
    public final EquipmentModifierType modifierType;
    public final int level;

    public EquipmentModifier(EquipmentModifierType modifierType, int level)
    {
        this.modifierType = modifierType;
        this.level = level;
    }
    public float getValue(float baseValue)
    {
        if(this.level == 0) return 0.0f;

        return switch (this.modifierType)
        {
            case MAX_DURABILITY -> this.level * 0.05f * baseValue;
            case MINING_SPEED -> this.level;
            case MAGNETIC -> this.level * 2.0f;
            case REGENERATION -> this.level / 10.0f;
            case REINFORCED -> this.level * 10.0f;
            case REACH -> 4.5f + this.level * 0.5f;
            case MINE_AOE -> this.level;
            case ATTACK_COOLDOWN -> -this.level * 0.025f;
            case ATTACK_AOE -> this.level;
            case FIRE_RATE -> -this.level * 0.05f;
            case KNOCKBACK -> this.level;
            case ATTACK_DAMAGE -> this.level * 0.5f;
            case KNOCKBACK_RESISTANCE -> this.level * 0.5f;
            case ARMOR -> this.level;
            case THORNS -> this.level;
            case XP_BOOST -> this.level * 20.0f;
            case MENDING, SILK_TOUCH -> this.level;
            case MOVEMENT_SPEED -> this.level + 1;
        };
    }
}
