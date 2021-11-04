package com.finderfeed.solarforge.events;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.events.my_events.MyColorEvent;
import com.finderfeed.solarforge.events.my_events.PostColorEvent;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


//code is mine but idea was taken from SSKirillSS(Relics mod)
@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class TooltipEventHandler {

    @SubscribeEvent
    public static void renderTooltips(RenderTooltipEvent.Pre event){
        if (event.getStack().getItem() instanceof ICustomTooltip tooltip) {
            RenderingTools.renderTooltipInternal(event.getMatrixStack(), event.getComponents(), event.getX(), event.getY(),tooltip.getTooltip());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void changeColor(MyColorEvent color){
        color.setBackground(color.getTooltip().getBackgroundColor());
        color.setBorderStart(color.getTooltip().getBorderColorStart());
        color.setBorderEnd(color.getTooltip().getBorderColorEnd());
    }

    @SubscribeEvent
    public static void postColorEvent(PostColorEvent event){
        int relX = event.getX()-4;
        int relY = event.getY()-4;
        int sizeX = event.getSizeX()+4;
        int sizeY = event.getSizeY()+4;
        PoseStack stack = event.getMatrixStack();
        CustomTooltip tooltip = event.getTooltip();
        renderTop(stack,tooltip,relX,relY,sizeX,sizeY);
        renderBottom(stack,tooltip,relX,relY,sizeX,sizeY);
        renderCorners(stack,tooltip,relX,relY,sizeX,sizeY);
    }

    private static void renderTop(PoseStack matrices,CustomTooltip tooltip,int relX,int relY,int sizeX,int sizeY){
        matrices.pushPose();
        tooltip.bindTop();
        int posX = calculateXTopRenderPosition(relX,relX+sizeX,tooltip.getTopTextureWidth());
        int posY = calculateYTopRenderPosition(relY,tooltip.getTopTextureHeight());
        Gui.blit(matrices,posX,posY+tooltip.getyOffsetTop(),0,0,tooltip.getTopTextureWidth(),tooltip.getTopTextureHeight(),tooltip.getTopTextureWidth(),tooltip.getTopTextureHeight());
        matrices.popPose();
    }

    private static void renderBottom(PoseStack matrices,CustomTooltip tooltip,int relX,int relY,int sizeX,int sizeY){
        matrices.pushPose();
        tooltip.bindBottom();
        int posX = calculateXBottomRenderPosition(relX,relX+sizeX,tooltip.getBottomTextureWidth());
        int posY = calculateYBottomRenderPosition(relY,sizeY,tooltip.getBottomTextureHeight());
        Gui.blit(matrices,posX,posY+tooltip.getyOffsetBottom(),0,0,tooltip.getBottomTextureWidth(),tooltip.getBottomTextureHeight(),tooltip.getBottomTextureWidth(),tooltip.getBottomTextureHeight());
        matrices.popPose();
    }
    //in blit first matrix second/third position, 4 5 sdvig, 5 6 skolko vzyat ot teksturi, 7 8 razmer etoy teksturi
    private static void renderCorners(PoseStack matrices,CustomTooltip tooltip,int relX,int relY,int sizeX,int sizeY){
        matrices.pushPose();
        tooltip.bindCorners();

        int dim = tooltip.getCornerDimensions();
        int half = dim/2;
        int doubled = dim*2;
        Gui.blit(matrices,relX-half,relY-half,0,0,dim,dim,doubled,doubled);
        int[] leftBottom = calculateLeftBottomCornerPos(relX,relY,sizeY,dim);
        Gui.blit(matrices,leftBottom[0],leftBottom[1],0,dim,dim,dim,doubled,doubled);
        int[] rightTop = calculateRightTopCornerPos(relX,relY,sizeX,dim);
        Gui.blit(matrices,rightTop[0],rightTop[1],dim,0,dim,dim,doubled,doubled);
        int[] rightBottom = calculateRightBottomCornerPos(relX,relY,sizeX,sizeY,dim);
        Gui.blit(matrices,rightBottom[0],rightBottom[1],dim,dim,dim,dim,doubled,doubled);


        matrices.popPose();
    }

    private static int calculateXTopRenderPosition(int start,int end,int texWidth){
        int center = Math.abs((end-start)/2);
        int half = texWidth/2;
        return start+center-half;
    }

    private static int calculateYTopRenderPosition(int yPos,int texHeight){
        return yPos-texHeight/2-4;
    }

    private static int calculateXBottomRenderPosition(int start,int end,int texWidth){
        int center = Math.abs((end-start)/2);
        int half = texWidth/2;
        return start+center-half;
    }

    private static int calculateYBottomRenderPosition(int yPos,int ySize,int texHeight){
        return yPos + ySize+1;
    }

    private static int[] calculateLeftBottomCornerPos(int relX,int relY,int sizeY,int cornerDimensions){
        return new int[]{relX-cornerDimensions/2,relY+sizeY+1};
    }

    private static int[] calculateRightTopCornerPos(int relX,int relY,int sizeX,int cornerDimensions){
        return new int[]{relX+sizeX +1 ,relY-cornerDimensions/2};
    }

    private static int[] calculateRightBottomCornerPos(int relX,int relY,int sizeX,int sizeY,int cornerDimensions){
        return new int[]{relX+sizeX+1,relY+sizeY+1};
    }


}
