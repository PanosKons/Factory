package me.aes123.factory.networking.packet;

import me.aes123.factory.dungeon.PlayerDungeon;
import me.aes123.factory.dungeon.PlayerDungeonProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collections;
import java.util.function.Supplier;

public class ModifierSelectionC2SPacket {
    public final int selectedIndex;
    public ModifierSelectionC2SPacket(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    public ModifierSelectionC2SPacket(FriendlyByteBuf friendlyByteBuf) {
        selectedIndex = friendlyByteBuf.readVarInt();
    }
    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeVarInt(selectedIndex);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = (ServerLevel) player.level();
            LazyOptional<PlayerDungeon> cap = Minecraft.getInstance().player.getCapability(PlayerDungeonProvider.PLAYER_DUNGEON);
            cap.ifPresent(playerDungeon -> {
                if(selectedIndex < playerDungeon.modifiers.size()) {
                    playerDungeon.modifiers.get(selectedIndex).command.command(player);
                    Collections.shuffle(playerDungeon.modifiers);
                }
            });
        });
        return true;
    }
}
