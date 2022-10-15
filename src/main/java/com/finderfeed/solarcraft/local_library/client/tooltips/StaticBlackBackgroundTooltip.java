package com.finderfeed.solarcraft.local_library.client.tooltips;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;

public class StaticBlackBackgroundTooltip extends AnimatedTooltip{




    public StaticBlackBackgroundTooltip(int borderX1, int borderY1, int borderX2, int borderY2, int borderWidth) {
        super(borderX1, borderY1, borderX2, borderY2, 1, borderWidth);
        this.ticker = 1;
    }

    @Override
    public void render(PoseStack matrices, int x, int y, float pTicks, int mousex, int mousey) {
        int xPos = calculateXRenderPos(x);
        int yPos = calculateYRenderPos(y);
        //double percentXOpeness = 1f;
        //percentXOpeness *= percentXOpeness;
        //double percentYOpeness = 1f;
        //percentYOpeness *= percentYOpeness;

        double xStartRenderPos = FDMathHelper.clamp(borderX1,x,borderX2);
        double yStartRenderPos = FDMathHelper.clamp(borderY1,y,borderY2);

        double xLeftOpeness = Math.abs(xStartRenderPos - xPos);
        double xRightOpeness = Math.abs(xPos + maxSizeX - xStartRenderPos);

        double yDownOpeness = Math.abs(yPos + maxSizeY - yStartRenderPos);
        double yUpOpeness = Math.abs(yStartRenderPos - yPos);

        RenderingTools.fill(matrices,xStartRenderPos - xLeftOpeness,yStartRenderPos,xStartRenderPos,yStartRenderPos + yDownOpeness,0,0,0,0.94f);
        RenderingTools.fill(matrices,xStartRenderPos,yStartRenderPos,xStartRenderPos + xRightOpeness,yStartRenderPos + yDownOpeness,0,0,0,0.94f);
        RenderingTools.fill(matrices,xStartRenderPos - xLeftOpeness,yStartRenderPos,xStartRenderPos,yStartRenderPos - yUpOpeness,0,0,0,0.94f);
        RenderingTools.fill(matrices,xStartRenderPos,yStartRenderPos,xStartRenderPos + xRightOpeness,yStartRenderPos - yUpOpeness,0,0,0,0.94f);
        this.getComponents().render(matrices, xPos + borderWidth, yPos + borderWidth, pTicks, mousex, mousey, 1, 1);

    }







}
