package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.SolarFurnaceContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;

public class SolarFurnaceScreen extends AbstractContainerScreen<SolarFurnaceContainer> {
    public final ResourceLocation GUI = new ResourceLocation("solarforge","textures/gui/solar_furnace_gui.png");
    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarforge","textures/gui/energy_bar.png");
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
    public void render(PoseStack stack,int rouseX,int rouseY,float partialTicks){
        this.renderBackground(stack);
        super.render(stack,rouseX,rouseY,partialTicks);
        this.renderTooltip(stack,rouseX,rouseY);

    }

    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int mousex, int mousey) {

        Minecraft.getInstance().getTextureManager().bind(GUI);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        blit(matrices, relX + 3+a, relY+26, 0, 0, 176, 256);

        float percent = (float)menu.getRecipeProgress()/ menu.getMaxRecipeProgress();

        blit(matrices,relX+a+76,relY+47,177,0,(int)(22 * percent),256);
        Minecraft.getInstance().getTextureManager().bind(REQ_ENERGY);
        percent = (float)menu.getEnergy()/menu.te.getMaxEnergy();
        minecraft.getTextureManager().bind(REQ_ENERGY);
        matrices.pushPose();
        matrices.translate(relX+24+a,relY+78,0);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        blit(matrices,0,0,0,0,16,(int)(33*percent),16,33);
        matrices.popPose();

    }
}
