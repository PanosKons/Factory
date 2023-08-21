package me.aes123.factory.event;

import me.aes123.factory.Main;
import me.aes123.factory.entity.GuardEntity;
import me.aes123.factory.init.ModEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event)
    {
        event.put(ModEntityTypes.GUARD.get(), GuardEntity.setAttributes());
    }
}
