package com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ImageComponent extends BaseComponent{

    private Image image;

    public ImageComponent(ContentAlignment alignment, Image image) {
        super(alignment, image.getXSize(), image.getYSize());
        this.image = image;
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float pTicksint, int mx, int my, int ticker, int animationLength) {
        PoseStack matrices = graphics.pose();
        int[] xy = this.getAlignment().getCoords(this,x,y);
        xy[0] += getInnerBorder();
        xy[1] += getInnerBorder();
        matrices.pushPose();
        RenderSystem.setShaderTexture(0,image.getLocation());
        RenderingTools.blitWithBlend(matrices,xy[0],xy[1],0,0,image.getXSize(),image.getYSize(),image.getXSize(),image.getYSize(),0,1f);
        matrices.popPose();
    }

    public static class Image{

        private final ResourceLocation location;
        private final int xSize;
        private final int ySize;

        public Image(ResourceLocation location,int xSize,int ySize){
            this.xSize = xSize;
            this.ySize = ySize;
            this.location = location;
        }

        public int getXSize() {
            return xSize;
        }

        public int getYSize() {
            return ySize;
        }

        public ResourceLocation getLocation() {
            return location;
        }
    }
}
