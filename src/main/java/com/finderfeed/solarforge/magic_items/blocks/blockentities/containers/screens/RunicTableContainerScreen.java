package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.RunicTableContainer;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packets.RunicTablePacket;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.rendering.item_renderers.TransparentItemrenderer;
import com.finderfeed.solarforge.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunicTableContainerScreen extends ContainerScreen<RunicTableContainer> {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/runic_table_gui.png");
    int relX = 0;
    int relY = 0;

    public List<ItemStack> pattern = new ArrayList<>();


    public RunicTableContainerScreen(RunicTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.inventoryLabelY+=2;
        this.titleLabelY-=25;
    }


    @Override
    protected void init() {

        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        super.init();
        pattern.clear();

        for (int a : menu.getPlayerPattern()){
            pattern.add(ProgressionHelper.RUNES[a].getDefaultInstance());
        }

        addButton(new WoodenButton(relX+72,relY+85,40,15,new TranslationTextComponent("solarcraft.runic_table"),(button)->{
            SolarForgePacketHandler.INSTANCE.sendToServer(new RunicTablePacket(menu.te.getBlockPos()));
        }));
    }

    @Override
    public void render(MatrixStack stack, int rouseX, int rouseY, float partialTicks) {
        this.renderBackground(stack);
        this.renderTooltip(stack,rouseX,rouseY);
        super.render(stack, rouseX, rouseY, partialTicks);
    }

    @Override
    protected void renderBg(MatrixStack matrices, float partialTicks, int mousex, int mousey) {
        Minecraft.getInstance().getTextureManager().bind(MAIN_SCREEN);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        blit(matrices,relX+a+3,relY+4,0,0,256,256,256,256);


        renderItem(pattern.get(0),48+a,14);
        renderItem(pattern.get(1),37+a,49);
        renderItem(pattern.get(2),48+a,84);
        renderItem(pattern.get(3),118+a,14);
        renderItem(pattern.get(4),129+a,49);
        renderItem(pattern.get(5),118+a,84);



    }

    private void renderItem(ItemStack stack,int x,int y){

        TransparentItemrenderer.INSTANCE.renderGuiItem(stack,relX+x,relY+y);
            //Minecraft.getInstance().getItemRenderer().renderGuiItem(stack,relX+x,relY+y);
    }


}
class WoodenButton extends Button{
    protected  Button.ITooltip tool;
    public static final ResourceLocation WIDGETS_SOLARFORGE = new ResourceLocation("solarforge","textures/gui/runic_table_buttons.png");

    public WoodenButton(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent p_i232255_5_, IPressable p_i232255_6_) {
        super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, p_i232255_6_);
    }

    public WoodenButton(int p_i232256_1_, int p_i232256_2_, int p_i232256_3_, int p_i232256_4_, ITextComponent p_i232256_5_, IPressable p_i232256_6_, ITooltip p_i232256_7_) {
        super(p_i232256_1_, p_i232256_2_, p_i232256_3_, p_i232256_4_, p_i232256_5_, p_i232256_6_, p_i232256_7_);
    }

    @Override
    public void renderButton(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        if (this.isHovered() && tool != null) {
            this.renderToolTip(p_230431_1_, p_230431_2_, p_230431_3_);

        }
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(WIDGETS_SOLARFORGE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(p_230431_1_, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(p_230431_1_, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(p_230431_1_, minecraft, p_230431_2_, p_230431_3_);
        int j = getFGColor();


        drawCenteredString(p_230431_1_, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void renderToolTip(MatrixStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
        if (tool != null) {
            this.tool.onTooltip(this, p_230443_1_, p_230443_2_, p_230443_3_);
        }
    }
}