package me.aes123.factory.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.aes123.factory.Main;
import me.aes123.factory.screen.renderer.EnergyInfoArea;
import me.aes123.factory.screen.renderer.XPInfoArea;
import me.aes123.factory.util.ModTags;
import me.aes123.factory.util.MouseUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ModEnchantmentScreen extends AbstractContainerScreen<ModEnchantmentMenu> {
    private static final ResourceLocation ENCHANTING_TABLE_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/enchanting_table_gui.png");
    private static final ResourceLocation ENCHANTING_BOOK_LOCATION = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private final RandomSource random = RandomSource.create();
    private BookModel bookModel;
    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    private ItemStack last = ItemStack.EMPTY;

    public ModEnchantmentScreen(ModEnchantmentMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight += 14;
        this.inventoryLabelY += 14;
    }

    @Override
    protected void init()
    {
        super.init();
        this.bookModel = new BookModel(this.minecraft.getEntityModels().bakeLayer(ModelLayers.BOOK));


        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
    }

    public void containerTick() {
        super.containerTick();
        this.tickBook();
    }

    public boolean mouseClicked(double p_98758_, double p_98759_, int p_98760_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        for(int k = 0; k < 3; ++k) {
            double d0 = p_98758_ - (double)(i + 60);
            double d1 = p_98759_ - (double)(j + 14 + 19 * k);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 108.0D && d1 < 19.0D && this.menu.clickMenuButton(this.minecraft.player, k)) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, k);
                return true;
            }
        }

        return super.mouseClicked(p_98758_, p_98759_, p_98760_);
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        super.renderLabels(gui, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.renderBook(guiGraphics, i, j, partialTick);
        EnchantmentNames.getInstance().initSeed(1);
        int k = this.menu.getGoldCount();

        for(int l = 0; l < 3; ++l) {
            int i1 = i + 60;
            int j1 = i1 + 20;
            int k1 = (this.menu).costs[l];
            if (k1 == 0) {
                guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i1, j + 14 + 19 * l, 0, 185 + 14, 108, 19);
            } else {
                String s = "" + k1;
                int l1 = 86 - this.font.width(s);
                FormattedText formattedtext = EnchantmentNames.getInstance().getRandomName(this.font, l1);
                int i2 = 6839882;
                if (((k < l + 1 || this.minecraft.player.experienceLevel < k1) && !this.minecraft.player.getAbilities().instabuild) || this.menu.levelClue[l] == -1) { // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
                    guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i1, j + 14 + 19 * l, 0, 185 + 14, 108, 19);
                    ///////////////////////////guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i1 + 1, j + 15 + 19 * l, 16 * l, 239 + 14, 16, 16);
                    guiGraphics.drawWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, (i2 & 16711422) >> 1);////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    i2 = 4226832;
                } else {
                    int j2 = mouseX - (i + 60);
                    int k2 = mouseY - (j + 14 + 19 * l);
                    if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
                        guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i1, j + 14 + 19 * l, 0, 204 + 14, 108, 19);
                        i2 = 16777088;
                    } else {
                        guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i1, j + 14 + 19 * l, 0, 166 + 14, 108, 19);
                    }

                    guiGraphics.blit(ENCHANTING_TABLE_LOCATION, i1 + 1, j + 15 + 19 * l, 16 * l, 223 + 14, 16, 16);
                    guiGraphics.drawWordWrap(this.font, formattedtext, j1, j + 16 + 19 * l, l1, i2);
                    i2 = 8453920;
                }

                guiGraphics.drawString(this.font, s, j1 + 86 - this.font.width(s), j + 16 + 19 * l + 7, i2);
            }
        }
    }

    private void renderBook(GuiGraphics p_289697_, int p_289667_, int p_289669_, float p_289670_) {
        float f = Mth.lerp(p_289670_, this.oOpen, this.open);
        float f1 = Mth.lerp(p_289670_, this.oFlip, this.flip);
        Lighting.setupForEntityInInventory();
        p_289697_.pose().pushPose();
        p_289697_.pose().translate((float)p_289667_ + 33.0F, (float)p_289669_ + 31.0F, 100.0F);
        float f2 = 40.0F;
        p_289697_.pose().scale(-40.0F, 40.0F, 40.0F);
        p_289697_.pose().mulPose(Axis.XP.rotationDegrees(25.0F));
        p_289697_.pose().translate((1.0F - f) * 0.2F, (1.0F - f) * 0.1F, (1.0F - f) * 0.25F);
        float f3 = -(1.0F - f) * 90.0F - 90.0F;
        p_289697_.pose().mulPose(Axis.YP.rotationDegrees(f3));
        p_289697_.pose().mulPose(Axis.XP.rotationDegrees(180.0F));
        float f4 = Mth.clamp(Mth.frac(f1 + 0.25F) * 1.6F - 0.3F, 0.0F, 1.0F);
        float f5 = Mth.clamp(Mth.frac(f1 + 0.75F) * 1.6F - 0.3F, 0.0F, 1.0F);
        this.bookModel.setupAnim(0.0F, f4, f5, f);
        VertexConsumer vertexconsumer = p_289697_.bufferSource().getBuffer(this.bookModel.renderType(ENCHANTING_BOOK_LOCATION));
        this.bookModel.renderToBuffer(p_289697_.pose(), vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_289697_.flush();
        p_289697_.pose().popPose();
        Lighting.setupFor3DItems();
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
    {
        delta = this.minecraft.getFrameTime();
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        boolean flag = this.minecraft.player.getAbilities().instabuild;
        int i = this.menu.getGoldCount();

        for(int j = 0; j < 3; ++j) {
            int k = (this.menu).costs[j];


            int id = (this.menu).enchantClue[j];
            Enchantment enchantment = id == -1? null : ModTags.Items.AllowedEnchantments.get(id);



            int l = (this.menu).levelClue[j];
            int i1 = j + 1;
            if (this.isHovering(60, 14 + 19 * j, 108, 17, (double)mouseX, (double)mouseY) && k > 0) {
                List<Component> list = Lists.newArrayList();
                list.add((Component.translatable("container.enchant.clue", enchantment == null ? "" : enchantment.getFullname(l))).withStyle(ChatFormatting.WHITE));
                if (enchantment == null) {
                    list.add(Component.literal(""));
                    list.add(Component.translatable("forge.container.enchant.limitedEnchantability").withStyle(ChatFormatting.RED));
                } else if (!flag) {
                    list.add(CommonComponents.EMPTY);
                    if (this.minecraft.player.experienceLevel < k) {
                        list.add(Component.translatable("container.enchant.level.requirement", (this.menu).costs[j]).withStyle(ChatFormatting.RED));
                    } else {
                        MutableComponent mutablecomponent;
                        if (i1 == 1) {
                            mutablecomponent = Component.translatable("container.enchant.lapis.one");
                        } else {
                            mutablecomponent = Component.translatable("container.enchant.lapis.many", i1);
                        }

                        list.add(mutablecomponent.withStyle(i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED));

                        MutableComponent mutablecomponent1 = Component.literal("Probability of success: " + (int)(100 * this.menu.getCurve((this.menu).instability[0])) + "%");
                        list.add(mutablecomponent1.withStyle(ChatFormatting.GRAY));
                        if(j == 0)
                        {
                            list.add((MutableComponent)Component.literal("Equipment might lose all enchantments").withStyle(ChatFormatting.RED));
                        }
                        if(j == 1)
                        {
                            list.add((MutableComponent)Component.literal("Equipment might become unenchantable").withStyle(ChatFormatting.RED));
                        }
                    }
                }

                guiGraphics.renderComponentTooltip(this.font, list, mouseX, mouseY);
                break;
            }
        }
    }


    public void tickBook() {
        ItemStack itemstack = this.menu.getSlot(0).getItem();
        if (!ItemStack.matches(itemstack, this.last)) {
            this.last = itemstack;

            do {
                this.flipT += (float)(this.random.nextInt(4) - this.random.nextInt(4));
            } while(this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
        }

        ++this.time;
        this.oFlip = this.flip;
        this.oOpen = this.open;
        boolean flag = false;

        for(int i = 0; i < 3; ++i) {
            if ((this.menu).costs[i] != 0) {
                flag = true;
            }
        }

        if (flag) {
            this.open += 0.2F;
        } else {
            this.open -= 0.2F;
        }

        this.open = Mth.clamp(this.open, 0.0F, 1.0F);
        float f1 = (this.flipT - this.flip) * 0.4F;
        float f = 0.2F;
        f1 = Mth.clamp(f1, -0.2F, 0.2F);
        this.flipA += (f1 - this.flipA) * 0.9F;
        this.flip += this.flipA;
    }
}