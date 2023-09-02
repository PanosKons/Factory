package me.aes123.factory.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import me.aes123.factory.dungeon.PlayerDungeon;
import me.aes123.factory.dungeon.PlayerDungeonProvider;
import me.aes123.factory.networking.ModMessages;
import me.aes123.factory.networking.packet.ModifierSelectionC2SPacket;
import me.aes123.factory.util.KeyBinding;
import me.aes123.factory.util.ShopCommand;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Arrays;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ShopScreen extends Screen {
    static final ResourceLocation GAMEMODE_SWITCHER_LOCATION = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
    private static final int SPRITE_SHEET_WIDTH = 128;
    private static final int SPRITE_SHEET_HEIGHT = 128;
    private static final int SLOT_AREA = 26;
    private static final int SLOT_PADDING = 5;
    private static final int SLOT_AREA_PADDED = 31;
    private static final int HELP_TIPS_OFFSET_Y = 5;
    private static final int ALL_SLOTS_WIDTH = SelectionIcon.values().length * SLOT_AREA_PADDED - 5;
    private static final Component SELECT_KEY = Component.literal("Press [ENTER] to buy").withStyle(ChatFormatting.AQUA);
    private int currentlyHoveredIndex = 0;
    private int firstMouseX;
    private int firstMouseY;
    private boolean setFirstMousePos;
    private final SelectionSlot[] slots = new SelectionSlot[4];
    private boolean shouldClose = false;
    private boolean shouldApply = false;

    public ShopScreen() {
        super(GameNarrator.NO_TITLE);
    }

    private GameType getDefaultSelected() {
        MultiPlayerGameMode multiplayergamemode = Minecraft.getInstance().gameMode;
        GameType gametype = multiplayergamemode.getPreviousPlayerMode();
        if (gametype != null) {
            return gametype;
        } else {
            return multiplayergamemode.getPlayerMode() == GameType.CREATIVE ? GameType.SURVIVAL : GameType.CREATIVE;
        }
    }

    protected void init() {
        super.init();
        LazyOptional<PlayerDungeon> cap = Minecraft.getInstance().player.getCapability(PlayerDungeonProvider.PLAYER_DUNGEON);
        cap.ifPresent(playerDungeon -> {
            for(int i = 0; i < slots.length; ++i) {
                this.slots[i] = new SelectionSlot(playerDungeon.modifiers.get(i), this.width / 2 - ALL_SLOTS_WIDTH / 2 + i * SLOT_AREA_PADDED, this.height / 2 - SLOT_AREA_PADDED);
            }
        });

    }

    public void render(GuiGraphics p_281834_, int p_283223_, int p_282178_, float p_281339_) {
        if (!this.checkToClose()) {
            p_281834_.pose().pushPose();
            RenderSystem.enableBlend();
            int i = this.width / 2 - 62;
            int j = this.height / 2 - SLOT_AREA_PADDED - 27;
            p_281834_.blit(GAMEMODE_SWITCHER_LOCATION, i, j, 0.0F, 0.0F, 125, 75, SPRITE_SHEET_WIDTH, SPRITE_SHEET_HEIGHT);
            p_281834_.pose().popPose();
            super.render(p_281834_, p_283223_, p_282178_, p_281339_);

            LazyOptional<PlayerDungeon> cap = Minecraft.getInstance().player.getCapability(PlayerDungeonProvider.PLAYER_DUNGEON);
            cap.ifPresent(playerDungeon -> {
                p_281834_.drawCenteredString(this.font, playerDungeon.modifiers.get(currentlyHoveredIndex).getName(), this.width / 2, this.height / 2 - SLOT_AREA_PADDED - 20, -1);
            });


            p_281834_.drawCenteredString(this.font, SELECT_KEY, this.width / 2, this.height / 2 + 5, 16777215);
            if (!this.setFirstMousePos) {
                this.firstMouseX = p_283223_;
                this.firstMouseY = p_282178_;
                this.setFirstMousePos = true;
            }

            boolean flag = this.firstMouseX == p_283223_ && this.firstMouseY == p_282178_;

            for(SelectionSlot shopscreen$gamemodesslot : this.slots) {
                shopscreen$gamemodesslot.render(p_281834_, p_283223_, p_282178_, p_281339_);
                cap.ifPresent(playerDungeon -> {
                    shopscreen$gamemodesslot.setSelected(playerDungeon.modifiers.get(this.currentlyHoveredIndex) == shopscreen$gamemodesslot.icon);
                });
                if (!flag && shopscreen$gamemodesslot.isHoveredOrFocused()) {
                    cap.ifPresent(playerDungeon -> {
                        this.currentlyHoveredIndex = playerDungeon.modifiers.indexOf(shopscreen$gamemodesslot.icon);
                    });
                }
            }

        }
    }

    private void applyModifier()
    {
        ModMessages.sendToServer(new ModifierSelectionC2SPacket(currentlyHoveredIndex));
    }
    private boolean checkToClose() {
        if(shouldClose)
        {
            if(shouldApply)
            {
                this.applyModifier();
            }
            this.minecraft.setScreen(null);
            return true;
        }
        return false;
    }

    public boolean keyPressed(int p_97553_, int p_97554_, int p_97555_) {
        InputConstants.Key mouseKey = InputConstants.getKey(p_97553_, p_97554_);
        if (KeyBinding.SHOP_KEY.getKey() == InputConstants.getKey(p_97553_, p_97554_))
        {
            shouldClose = true;
            return true;
        }
        if (InputConstants.isKeyDown(this.minecraft.getWindow().getWindow(), 257))
        {
            shouldClose = true;
            shouldApply = true;
            return true;
        }
        else {
            return super.keyPressed(p_97553_, p_97554_, p_97555_);
        }
    }

    public boolean isPauseScreen() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public enum SelectionIcon {
        LOOT(Component.translatable("dungeon.factory.loot"), (player) -> System.out.println(player.getName() + " re dialexes loot"), new ItemStack(Items.BUNDLE)),
        DOOR(Component.translatable("dungeon.factory.door"), (player) -> System.out.println(player.getName() + " re dialexes door"), new ItemStack(Items.IRON_DOOR)),
        HEALTH_BOOST(Component.translatable("dungeon.factory.health_boost"), (player) -> System.out.println(player.getName() + " re dialexes health_boost"), new ItemStack(Items.GOLDEN_APPLE)),
        SLOWNESS(Component.translatable("dungeon.factory.slowness"), (player) -> System.out.println(player.getName() + " re dialexes slowness"), new ItemStack(Items.POTION));
        private static final int ICON_AREA = 16;
        protected static final int ICON_TOP_LEFT = 5;
        final Component name;
        public final ShopCommand command;
        final ItemStack renderStack;

        SelectionIcon(Component component, ShopCommand command, ItemStack stack) {
            this.name = component;
            this.command = command;
            this.renderStack = stack;
        }


        public static Optional<SelectionIcon> getFromName(String name)
        {
            return Arrays.stream(values()).filter((e) -> e.name().toLowerCase().equals(name)).findFirst();
        }

        void drawIcon(GuiGraphics graphics, int x, int y) {
            graphics.renderItem(this.renderStack, x, y);
        }

        Component getName() {
            return this.name;
        }

    }

    @OnlyIn(Dist.CLIENT)
    public class SelectionSlot extends AbstractWidget {
        final SelectionIcon icon;
        private boolean isSelected;

        public SelectionSlot(SelectionIcon p_97627_, int p_97628_, int p_97629_) {
            super(p_97628_, p_97629_, SLOT_AREA, SLOT_AREA, p_97627_.getName());
            this.icon = p_97627_;
        }

        public void renderWidget(GuiGraphics p_281380_, int p_283094_, int p_283558_, float p_282631_) {
            this.drawSlot(p_281380_);
            this.icon.drawIcon(p_281380_, this.getX() + 5, this.getY() + 5);
            if (this.isSelected) {
                this.drawSelection(p_281380_);
            }

        }

        public void updateWidgetNarration(NarrationElementOutput p_259120_) {
            this.defaultButtonNarrationText(p_259120_);
        }

        public boolean isHoveredOrFocused() {
            return super.isHoveredOrFocused() || this.isSelected;
        }

        public void setSelected(boolean p_97644_) {
            this.isSelected = p_97644_;
        }

        private void drawSlot(GuiGraphics p_281786_) {
            p_281786_.blit(ShopScreen.GAMEMODE_SWITCHER_LOCATION, this.getX(), this.getY(), 0.0F, 75.0F, SLOT_AREA, SLOT_AREA, SPRITE_SHEET_WIDTH, SPRITE_SHEET_HEIGHT);
        }

        private void drawSelection(GuiGraphics p_281820_) {
            p_281820_.blit(ShopScreen.GAMEMODE_SWITCHER_LOCATION, this.getX(), this.getY(), SLOT_AREA, 75.0F, SLOT_AREA, SLOT_AREA, SPRITE_SHEET_WIDTH, SPRITE_SHEET_HEIGHT);
        }
    }
}