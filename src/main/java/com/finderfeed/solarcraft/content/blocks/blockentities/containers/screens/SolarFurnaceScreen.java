package com.finderfeed.solarcraft.content.blocks.blockentities.containers.screens;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.SolarFurnaceContainer;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.Component;

public class SolarFurnaceScreen extends AbstractContainerScreen<SolarFurnaceContainer> {
    public final ResourceLocation GUI = new ResourceLocation("solarcraft","textures/gui/solar_furnace_gui.png");
    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarcraft","textures/gui/energy_bar.png");
    int relX;
    int relY;

    public SolarFurnaceScreen(SolarFurnaceContainer p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        super.init();
    }

    @Override
    public void render(GuiGraphics graphics, int rouseX, int rouseY, float partialTicks){
        this.renderBackground(graphics);
        super.render(graphics,rouseX,rouseY,partialTicks);
        this.renderTooltip(graphics,rouseX,rouseY);

    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mousex, int mousey) {
        ClientHelpers.bindText(GUI);

        PoseStack matrices = graphics.pose();
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        RenderingTools.blitWithBlend(matrices, relX + 3+a, relY+26, 0, 0, 176, 256,256,256,0,1f);

        float percent = (float)menu.getRecipeProgress()/ menu.getMaxRecipeProgress();

        RenderingTools.blitWithBlend(matrices,relX+a+76,relY+47,177,0,(int)(22 * percent),256,256,256,0,1f);
        ClientHelpers.bindText(REQ_ENERGY);
        percent = (float)menu.getEnergy()/menu.te.getMaxSolarEnergy();
        matrices.pushPose();
        matrices.translate(relX+24+a,relY+78,0);
//        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),180));
        RenderingTools.blitWithBlend(matrices,0,0,0,0,16,(int)(33*percent),16,33,0,1f);
        matrices.popPose();

    }
}
