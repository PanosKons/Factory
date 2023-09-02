package me.aes123.factory.dungeon;

import me.aes123.factory.screen.ShopScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.ArrayList;
import java.util.List;

public class PlayerDungeon {
    public int exp;
    public List<ShopScreen.SelectionIcon> modifiers = List.of(ShopScreen.SelectionIcon.values());
    public void copyFrom(PlayerDungeon source) {
        this.exp = source.exp;
    }
    public void save(CompoundTag nbt)
    {
        nbt.putInt("exp", exp);
        ListTag ls = new ListTag();
        for(int i = 0; i < modifiers.size(); i++)
        {
            var tag = new CompoundTag();
            tag.putString("id", modifiers.get(i).toString().toLowerCase());
            ls.add(tag);
        }
        nbt.put("modifiers", ls);
    }
    public void load(CompoundTag nbt)
    {
        modifiers = new ArrayList<>();
        exp = nbt.getInt("exp");
        ListTag ls = nbt.getList("modifiers", 10);
        for(int i = 0; i < ls.size(); i++)
        {
            ShopScreen.SelectionIcon.getFromName(ls.getCompound(i).getString("id")).ifPresent(modifier -> modifiers.add(modifier));
        }
    }
}
