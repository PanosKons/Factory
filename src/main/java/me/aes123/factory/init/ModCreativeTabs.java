package me.aes123.factory.init;

import me.aes123.factory.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static final RegistryObject<CreativeModeTab> FACTORY_TAB = CREATIVE_MODE_TABS.register("factory_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tab.factory.factory_tab"))
                    .icon(() -> new ItemStack(ModBlocks.ASSEMBLER.get()))
                    .displayItems((displayParams, output) -> {
                        ModItems.ITEMS.getEntries().forEach(itemLike -> output.accept(itemLike.get()));
                    })
                    .build()
    );
}
