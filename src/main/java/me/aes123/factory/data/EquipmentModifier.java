package me.aes123.factory.data;

import com.mojang.authlib.minecraft.TelemetrySession;
import me.aes123.factory.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class EquipmentModifier {
    public static final List<String> TOOLS = List.of("pickaxe", "axe", "hoe", "shovel", "hammer");
    public static final List<String> TIERED_TOOLS = List.of("pickaxe", "hammer");
    public static final List<String> MELEE = List.of("sword");
    public static final List<String> HAMMER = List.of("sword");
    public static final List<String> PROJECTILE = List.of("pistol");
    public static final List<String> WEAPONS = List.of("pistol", "sword");
    public static final List<String> ARMORS = List.of("helmet", "chestplate", "leggings", "boots");
    public static final List<String> HOLDABLE = List.of("pistol", "sword", "pickaxe", "axe", "hoe", "shovel", "hammer");
    public static final List<String> TOTEM = List.of("totem");
    public static final List<String> ALL = List.of("helmet", "chestplate", "leggings", "boots", "pistol", "sword", "pickaxe", "axe", "hoe", "shovel", "hammer","totem");
    public enum EquipmentModifierType
    {
        MAX_DURABILITY(ALL,10), MENDING(ALL,1, DisplayFormat.ONLY_TYPE),
        MINING_SPEED(TOOLS,30), SILK_TOUCH(TOOLS,1, DisplayFormat.ONLY_TYPE), ITEM_COLLECTOR(TOOLS, 1, DisplayFormat.ONLY_TYPE),
        REINFORCED(TIERED_TOOLS, 10),
        MINE_AOE(HAMMER, 1, DisplayFormat.ONLY_TYPE),
        ATTACK_COOLDOWN(MELEE,10), ATTACK_AOE(MELEE,3),
        FIRE_RATE(PROJECTILE,10), BULLET_PRESERVE_CHANCE(PROJECTILE,10),
        KNOCKBACK(WEAPONS,10), ATTACK_DAMAGE(WEAPONS, 10),
        KNOCKBACK_RESISTANCE(ARMORS,10), ARMOR(ARMORS,30), THORNS(ARMORS,10),
        XP_BOOST(HOLDABLE, 10, DisplayFormat.PERCENTAGE),
        PLAYER_SPEED(TOTEM, 5), NIGHT_VISION(TOTEM, 1), BLOCK_REACH(TOTEM, 5), ENTITY_REACH(TOTEM, 5), LUCK(TOTEM, 5), REGENERATION(TOTEM, 5),
        STABILIZED(TOTEM, 1), ABSORB_DAMAGE_CHANCE(TOTEM, 10), HOTBAR_ACTIVE(TOTEM, 1), SLOWER_HUNGER(TOTEM,5), MAGNET_RANGE(TOTEM, 5),
        WEATHER_CONTROL(TOTEM, 1, true), TIME_CONTROL(TOTEM, 1, true), ENTITY_HIGHLIGHTER(TOTEM, 1, true);

        public enum DisplayFormat
        {
            DEFAULT, PERCENTAGE, ONLY_TYPE, LEGENDARY
        }

        public final List<String> applicableTools;
        public final int maxLevel;
        public final boolean totemExclusive;
        public final DisplayFormat format;

        EquipmentModifierType(List<String> applicableTools, int maxLevel)
        {
            this.applicableTools = applicableTools;
            this.maxLevel = maxLevel;
            this.totemExclusive = false;
            this.format = DisplayFormat.DEFAULT;
        }
        EquipmentModifierType(List<String> applicableTools, int maxLevel, DisplayFormat format)
        {
            this.applicableTools = applicableTools;
            this.maxLevel = maxLevel;
            this.totemExclusive = false;
            this.format = format;
        }
        EquipmentModifierType(List<String> applicableTools, int maxLevel, boolean totemExclusive)
        {
            this.applicableTools = applicableTools;
            this.maxLevel = maxLevel;
            this.totemExclusive = totemExclusive;
            this.format = DisplayFormat.LEGENDARY;
        }
    }
    public static final Map<Item, List<EquipmentModifier>> EQUIPMENT_MODIFIERS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(Items.IRON_INGOT, List.of(new EquipmentModifier(EquipmentModifierType.MAX_DURABILITY, 1))),
            new AbstractMap.SimpleEntry<>(ModItems.STEEL_INGOT.get(), List.of(new EquipmentModifier(EquipmentModifierType.MAX_DURABILITY, 2))),
            new AbstractMap.SimpleEntry<>(Items.OBSIDIAN, List.of(new EquipmentModifier(EquipmentModifierType.MAX_DURABILITY, 3))),

            new AbstractMap.SimpleEntry<>(ModItems.RUBY.get(), List.of(new EquipmentModifier(EquipmentModifierType.MENDING, 1))),

            new AbstractMap.SimpleEntry<>(Items.REDSTONE, List.of(
                    new EquipmentModifier(EquipmentModifierType.MINING_SPEED, 1),
                    new EquipmentModifier(EquipmentModifierType.FIRE_RATE, 1),
                    new EquipmentModifier(EquipmentModifierType.ATTACK_COOLDOWN, 1)
            )),
            new AbstractMap.SimpleEntry<>(Items.GOLD_INGOT, List.of(
                    new EquipmentModifier(EquipmentModifierType.MINING_SPEED, 2),
                    new EquipmentModifier(EquipmentModifierType.FIRE_RATE, 2),
                    new EquipmentModifier(EquipmentModifierType.ATTACK_COOLDOWN, 2)
            )),

            new AbstractMap.SimpleEntry<>(Items.COPPER_INGOT, List.of(
                    new EquipmentModifier(EquipmentModifierType.KNOCKBACK, 1),
                    new EquipmentModifier(EquipmentModifierType.KNOCKBACK_RESISTANCE, 1)
            )),


            new AbstractMap.SimpleEntry<>(Items.EMERALD, List.of(new EquipmentModifier(EquipmentModifierType.XP_BOOST, 1))),
            new AbstractMap.SimpleEntry<>(Items.NETHER_STAR, List.of(new EquipmentModifier(EquipmentModifierType.XP_BOOST, 3))),

            new AbstractMap.SimpleEntry<>(Items.LAPIS_LAZULI, List.of(new EquipmentModifier(EquipmentModifierType.ATTACK_DAMAGE, 1))),
            new AbstractMap.SimpleEntry<>(Items.DIAMOND, List.of(new EquipmentModifier(EquipmentModifierType.ATTACK_DAMAGE, 2), new EquipmentModifier(EquipmentModifierType.SILK_TOUCH, 1))),

            new AbstractMap.SimpleEntry<>(Items.NETHERITE_INGOT, List.of(
                    new EquipmentModifier(EquipmentModifierType.REINFORCED, 1),
                    new EquipmentModifier(EquipmentModifierType.MINE_AOE, 1),
                    new EquipmentModifier(EquipmentModifierType.ATTACK_AOE, 1),
                    new EquipmentModifier(EquipmentModifierType.BULLET_PRESERVE_CHANCE, 1),
                    new EquipmentModifier(EquipmentModifierType.THORNS, 1)
            ))
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
            case ITEM_COLLECTOR -> this.level;
            case REINFORCED -> this.level * 10.0f;
            case MINE_AOE -> this.level;
            case ATTACK_COOLDOWN -> -this.level * 0.05f;
            case ATTACK_AOE -> this.level;
            case FIRE_RATE -> -this.level * 0.05f;
            case BULLET_PRESERVE_CHANCE -> 0.0F;
            case KNOCKBACK -> this.level * 0.5f;
            case ATTACK_DAMAGE -> this.level * 0.5f;
            case KNOCKBACK_RESISTANCE -> this.level * 0.5f;
            case ARMOR -> this.level;
            case THORNS -> this.level;
            case XP_BOOST -> this.level * 10.0f;
            case MENDING, SILK_TOUCH -> this.level;
            case PLAYER_SPEED -> 1 + this.level * 0.2f;
            case BLOCK_REACH -> 4.5f + this.level * 0.5f;
            case ENTITY_REACH -> 3.0f + this.level * 0.5f;
            case LUCK, REGENERATION, NIGHT_VISION -> this.level;
            case STABILIZED -> this.level;
            case ABSORB_DAMAGE_CHANCE -> this.level;
            case HOTBAR_ACTIVE -> this.level;
            case SLOWER_HUNGER -> this.level;
            case MAGNET_RANGE -> this.level;
            case WEATHER_CONTROL -> this.level;
            case TIME_CONTROL -> this.level;
            case ENTITY_HIGHLIGHTER -> this.level;
        };
    }
    public void add(CompoundTag nbt)
    {
        nbt.putFloat(modifierType.toString().toLowerCase(), level);
    }
}
