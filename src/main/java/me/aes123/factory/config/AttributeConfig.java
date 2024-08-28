/*The MIT License Copyright (c) <year> <copyright holders> Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
package me.aes123.factory.config;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.aes123.factory.Main;
import me.aes123.factory.mixin.RangedAttributeMixin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AttributeConfig {
        @Expose
        private Map<String, Entry> attributes = new HashMap<>();

        public void applyChanges() {
            Main.LOGGER.info("Applying changes for {} attributes.", attributes.size());
            for (Map.Entry<String, Entry> configEntry : attributes.entrySet()) {

                final ResourceLocation attributeId = ResourceLocation.tryParse(configEntry.getKey());
                if (attributeId != null && BuiltInRegistries.ATTRIBUTE.containsKey(attributeId)) {

                    final Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(attributeId);

                    if (attribute instanceof RangedAttribute ranged) {

                        final double minValue = configEntry.getValue().min.value;
                        final double maxValue = configEntry.getValue().max.value;

                        if (minValue > maxValue) {

                            Main.LOGGER.error("Attribute {} was configured to have a minimum value higher than it's maximum. This is not permitted!", attributeId);
                            continue;
                        }

                        final RangedAttributeMixin accessor = (RangedAttributeMixin) (Object) attribute;

                        if (minValue != ranged.getMinValue()) {

                            Main.LOGGER.debug("Modifying minimum value for {} from {} to {}.", attributeId, Main.FORMAT.format(ranged.getMinValue()), Main.FORMAT.format(minValue));
                            accessor.attributefix$setMinValue(minValue);
                        }

                        if (maxValue != ranged.getMaxValue()) {

                            Main.LOGGER.debug("Modifying maximum value for {} from {} to {}.", attributeId, Main.FORMAT.format(ranged.getMaxValue()), Main.FORMAT.format(maxValue));
                            accessor.attributefix$setMaxValue(maxValue);
                        }
                    }
                }
            }
        }

        public static AttributeConfig load(File configFile) {
            final AttributeConfig config = new AttributeConfig();

            // Load/Generate the default values.
            for (Attribute attribute : BuiltInRegistries.ATTRIBUTE) {

                if (attribute instanceof RangedAttribute ranged) {

                    final ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey(attribute);
                    config.attributes.put(id.toString(), new Entry(id, ranged));
                }
            }

            Main.LOGGER.info("Loaded values for {} compatible attributes.", config.attributes.size());

            // Attempt to load existing config file
            if (configFile.exists()) {

                try (FileReader reader = new FileReader(configFile)) {

                    final Map<String, Entry> configValues = Main.GSON.fromJson(reader, AttributeConfig.class).attributes;

                    for (Map.Entry<String, Entry> configEntry : configValues.entrySet()) {

                        final ResourceLocation attributeId = ResourceLocation.tryParse(configEntry.getKey());

                        if (attributeId == null) {

                            Main.LOGGER.error("Attribute ID '{}' is not a valid. This entry will be ignored.", configEntry.getKey());
                        }

                        else if (!BuiltInRegistries.ATTRIBUTE.containsKey(attributeId)) {

                            Main.LOGGER.error("Attribute ID '{}' does not belong to a known attribute. This entry will be ignored.", configEntry.getKey());
                        }

                        if (configEntry.getValue().min.value > configEntry.getValue().max.value) {

                            Main.LOGGER.error("Attribute ID '{}' has a max value that is less than its minimum value!", configEntry.getKey());
                        }

                        // Prevent data loss by including the user data even if it's invalid. Additional checks will be required when applying this later on.
                        config.attributes.put(configEntry.getKey(), configEntry.getValue());
                    }

                    Main.LOGGER.info("Loaded {} values from config.", configValues.size());
                }

                catch (IOException e) {

                    Main.LOGGER.error("Could not read config file {}. Defaults will be used.", configFile.getAbsolutePath());
                    Main.LOGGER.trace("Failed to read config file.", e);
                }
            }

            else {

                Main.LOGGER.info("Creating a new config file at {}.", configFile.getAbsolutePath());
                configFile.getParentFile().mkdirs();
            }

            try (FileWriter writer = new FileWriter(configFile)) {

                Main.GSON.toJson(config, writer);
                Main.LOGGER.info("Saving config file. {} entries.", config.attributes.size());
            }

            catch (IOException e) {

                Main.LOGGER.error("Could not write config file '{}'!", configFile.getAbsolutePath());
                Main.LOGGER.trace("Failed to read config file.", e);
            }


            return config;
        }

        /**
         * Map of Attributes to new default values.<br> Any attribute not in this map will retain the declared default
         * value, but can still be changed via config.
         */
        private static final Map<Attribute, Double> NEW_DEFAULT_VALUES = ImmutableMap.of(
                Attributes.MAX_HEALTH, 1_000_000D,
                Attributes.ARMOR, 1_000_000D,
                Attributes.ARMOR_TOUGHNESS, 1_000_000D,
                Attributes.ATTACK_DAMAGE, 1_000_000D,
                Attributes.ATTACK_KNOCKBACK, 1_000_000D
        );

        public static class Entry {

            @Expose
            private boolean enabled;

            @Expose
            private DoubleValue min;

            @Expose
            private DoubleValue max;

            public Entry(ResourceLocation id, RangedAttribute attribute) {

                this.enabled = "minecraft".equals(id.getNamespace());
                this.min = new DoubleValue(attribute.getMinValue(), attribute.getMinValue());
                this.max = new DoubleValue(attribute.getMaxValue(), NEW_DEFAULT_VALUES.getOrDefault(attribute, attribute.getMaxValue()));
            }

            public boolean isEnabled() {

                return this.isEnabled();
            }
        }

        public static class DoubleValue {

            @Expose
            @SerializedName("default")
            private double defaultValue;

            @Expose
            private double value;

            public DoubleValue(double defaultValue, double value) {

                this.defaultValue = defaultValue;
                this.value = value;
            }
        }
    }
