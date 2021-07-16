package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.RunicTableContainer;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunicTableContainerScreen extends ContainerScreen<RunicTableContainer> {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/runic_table_gui.png");
    int relX = 0;
    int relY = 0;

    List<ItemStack> pattern = new ArrayList<>();


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


        for (int a : menu.getPlayerPattern()){
            pattern.add(ProgressionHelper.RUNES[a].getDefaultInstance());
        }
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
            Minecraft.getInstance().getItemRenderer().renderGuiItem(stack,relX+x,relY+y);
    }


}
