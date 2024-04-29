package me.aes123.factory.serializing;

import com.google.gson.*;
import me.aes123.factory.data.EquipmentMaterialModifier;
import me.aes123.factory.data.EquipmentModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class EquipmentMaterialModifierDeserializer implements JsonDeserializer<EquipmentMaterialModifier> {
    @Override
    public EquipmentMaterialModifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        ResourceLocation location = new ResourceLocation(obj.get("item").getAsString());
        if(ForgeRegistries.ITEMS.containsKey(location))
        {
            Item item = ForgeRegistries.ITEMS.getValue(location);
            List<EquipmentModifier> modifiers = new ArrayList<>();
            JsonArray array = obj.get("modifiers").getAsJsonArray();
            for(int i = 0; i < array.size(); i++)
            {
                var modifierobj = array.get(i).getAsJsonObject();
                String modifier = modifierobj.get("id").getAsString().toUpperCase();
                EquipmentModifier.EquipmentModifierType modifierType = EquipmentModifier.EquipmentModifierType.valueOf(modifier);
                int level = modifierobj.get("level").getAsInt();
                modifiers.add(new EquipmentModifier(modifierType, level));
            }
            return new EquipmentMaterialModifier(item, modifiers);
        }

        return null;
    }
}
