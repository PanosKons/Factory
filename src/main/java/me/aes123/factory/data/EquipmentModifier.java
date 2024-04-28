package me.aes123.factory.data;

import me.aes123.factory.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

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
        REINFORCED(PICKAXE, 10, DisplayFormat.PERCENTAGE),
        REACH(HOLDABLE, 20),
        MINE_AOE(HAMMER, 1, DisplayFormat.ONLY_TYPE), ATTACK_AOE(MELEE,3), THORNS(ARMORS,3),
        ATTACK_DAMAGE(WEAPONS, 10), ARMOR(ARMORS,30),
        KNOCKBACK(WEAPONS,2), KNOCKBACK_RESISTANCE(ARMORS,10),
        XP_BOOST(HOLDABLE, 50, DisplayFormat.PERCENTAGE),
        MAGNETIC(HOLDABLE, 6);

        public enum DisplayFormat
        {
            DEFAULT, PERCENTAGE, ONLY_TYPE, LEGENDARY
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
    public static final Map<Item, List<EquipmentModifier>> EQUIPMENT_MODIFIERS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(Items.COAL, List.of(new EquipmentModifier(EquipmentModifierType.MAX_DURABILITY, 1))),
            new AbstractMap.SimpleEntry<>(Items.OBSIDIAN, List.of(new EquipmentModifier(EquipmentModifierType.MAX_DURABILITY, 2))),

            new AbstractMap.SimpleEntry<>(ModItems.RUBY.get(), List.of(new EquipmentModifier(EquipmentModifierType.MENDING, 1))),

            new AbstractMap.SimpleEntry<>(Items.COPPER_INGOT, List.of(
                    new EquipmentModifier(EquipmentModifierType.ATTACK_COOLDOWN, 1),
                    new EquipmentModifier(EquipmentModifierType.MINING_SPEED, 1),
                    new EquipmentModifier(EquipmentModifierType.FIRE_RATE, 1),
                    new EquipmentModifier(EquipmentModifierType.MOVEMENT_SPEED, 1)
            )),
            new AbstractMap.SimpleEntry<>(Items.REDSTONE, List.of(
                    new EquipmentModifier(EquipmentModifierType.ATTACK_COOLDOWN, 2),
                    new EquipmentModifier(EquipmentModifierType.MINING_SPEED, 2),
                    new EquipmentModifier(EquipmentModifierType.FIRE_RATE, 2),
                    new EquipmentModifier(EquipmentModifierType.MOVEMENT_SPEED, 2)
            )),
            new AbstractMap.SimpleEntry<>(Items.GOLD_INGOT, List.of(
                    new EquipmentModifier(EquipmentModifierType.ATTACK_COOLDOWN, 3),
                    new EquipmentModifier(EquipmentModifierType.MINING_SPEED, 3),
                    new EquipmentModifier(EquipmentModifierType.FIRE_RATE, 3),
                    new EquipmentModifier(EquipmentModifierType.MOVEMENT_SPEED, 3)
            )),
            new AbstractMap.SimpleEntry<>(Items.NETHERITE_INGOT, List.of(
                    new EquipmentModifier(EquipmentModifierType.ATTACK_COOLDOWN, 5),
                    new EquipmentModifier(EquipmentModifierType.MINING_SPEED, 5),
                    new EquipmentModifier(EquipmentModifierType.FIRE_RATE, 5),
                    new EquipmentModifier(EquipmentModifierType.MOVEMENT_SPEED, 5)
            )),

            new AbstractMap.SimpleEntry<>(Items.IRON_INGOT, List.of(new EquipmentModifier(EquipmentModifierType.MAGNETIC, 1))),
            new AbstractMap.SimpleEntry<>(ModItems.STEEL_INGOT.get(), List.of(new EquipmentModifier(EquipmentModifierType.MAGNETIC, 2))),
            new AbstractMap.SimpleEntry<>(Items.ENDER_PEARL, List.of(new EquipmentModifier(EquipmentModifierType.MAGNETIC, 3))),

            new AbstractMap.SimpleEntry<>(Items.GLOWSTONE, List.of(new EquipmentModifier(EquipmentModifierType.REACH, 1))),
            new AbstractMap.SimpleEntry<>(Items.BLAZE_ROD, List.of(new EquipmentModifier(EquipmentModifierType.REACH, 2))),
            new AbstractMap.SimpleEntry<>(Items.ELYTRA, List.of(new EquipmentModifier(EquipmentModifierType.REACH, 5))),

            new AbstractMap.SimpleEntry<>(Items.AMETHYST_SHARD, List.of(new EquipmentModifier(EquipmentModifierType.XP_BOOST, 1))),
            new AbstractMap.SimpleEntry<>(Items.EMERALD, List.of(new EquipmentModifier(EquipmentModifierType.XP_BOOST, 2))),
            new AbstractMap.SimpleEntry<>(Items.NETHER_STAR, List.of(new EquipmentModifier(EquipmentModifierType.XP_BOOST, 5))),

            new AbstractMap.SimpleEntry<>(Items.QUARTZ, List.of(
                    new EquipmentModifier(EquipmentModifierType.ATTACK_DAMAGE, 1),
                    new EquipmentModifier(EquipmentModifierType.ARMOR, 1))),
            new AbstractMap.SimpleEntry<>(Items.DIAMOND, List.of(
                    new EquipmentModifier(EquipmentModifierType.ATTACK_DAMAGE, 3),
                    new EquipmentModifier(EquipmentModifierType.ARMOR, 3))),

            new AbstractMap.SimpleEntry<>(Items.SLIME_BALL, List.of(
                    new EquipmentModifier(EquipmentModifierType.KNOCKBACK, 1),
                    new EquipmentModifier(EquipmentModifierType.KNOCKBACK_RESISTANCE, 1))),

            new AbstractMap.SimpleEntry<>(Items.ANCIENT_DEBRIS, List.of(new EquipmentModifier(EquipmentModifierType.REINFORCED, 1))),

            new AbstractMap.SimpleEntry<>(Items.HEART_OF_THE_SEA, List.of(new EquipmentModifier(EquipmentModifierType.SILK_TOUCH, 1))),

            new AbstractMap.SimpleEntry<>(Items.TOTEM_OF_UNDYING, List.of(new EquipmentModifier(EquipmentModifierType.REGENERATION, 1))),
            new AbstractMap.SimpleEntry<>(Items.ENCHANTED_GOLDEN_APPLE, List.of(new EquipmentModifier(EquipmentModifierType.REGENERATION, 4))),

            new AbstractMap.SimpleEntry<>(Items.ECHO_SHARD, List.of(
                    new EquipmentModifier(EquipmentModifierType.MINE_AOE, 1),
                    new EquipmentModifier(EquipmentModifierType.ATTACK_AOE, 1),
                    new EquipmentModifier(EquipmentModifierType.THORNS, 1)
            ))
            //lapis and ghast tears
    );

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
    public void add(CompoundTag nbt)
    {
        nbt.putFloat(modifierType.toString().toLowerCase(), level);
    }
}
