package com.finderfeed.solarforge.local_library.client.tooltips;

import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;

public class BlackBackgroundTooltip extends AnimatedTooltip{

    private int startYOpeness = 0;

    public BlackBackgroundTooltip(int borderX1, int borderY1, int borderX2, int borderY2, int animationLength, int borderWidth) {
        super(borderX1, borderY1, borderX2, borderY2, animationLength, borderWidth);
    }

    @Override
    public void render(PoseStack matrices, int x, int y, float pTicks, int mousex, int mousey) {
        int xPos = calculateXRenderPos(x);
        int yPos = calculateYRenderPos(y);
        float k = animLength/4f;
//        float tick = Mth.lerp(pTicks,oldTick,ticker);
        float tick = oldTick != ticker ? oldTick < ticker ? ticker + pTicks : ticker - pTicks : ticker;

        double percentXOpeness = ticker < k ? (tick )/animLength*4 : 1f;
        percentXOpeness *= percentXOpeness;
        double percentYOpeness = FDMathHelper.clamp(0,ticker < k ? 0f : (tick - k)/(animLength - k),1);
        percentYOpeness *= percentYOpeness;

        double xStartRenderPos = FDMathHelper.clamp(borderX1,x,borderX2);
        double yStartRenderPos = FDMathHelper.clamp(borderY1,y,borderY2);

        double xLeftOpeness = Mth.lerp(percentXOpeness,0,Math.abs(xStartRenderPos - xPos));
        double xRightOpeness = Mth.lerp(percentXOpeness,0,Math.abs(xPos + maxSizeX - xStartRenderPos));

        double yDownOpeness = startYOpeness + Mth.lerp(percentYOpeness,0,Math.abs(yPos + maxSizeY - yStartRenderPos - startYOpeness));
        double yUpOpeness = Mth.lerp(percentYOpeness,0,Math.abs(yStartRenderPos - yPos));

        RenderingTools.fill(matrices,xStartRenderPos - xLeftOpeness,yStartRenderPos,xStartRenderPos,yStartRenderPos + yDownOpeness,0,0,0,0.94f);
        RenderingTools.fill(matrices,xStartRenderPos,yStartRenderPos,xStartRenderPos + xRightOpeness,yStartRenderPos + yDownOpeness,0,0,0,0.94f);
        RenderingTools.fill(matrices,xStartRenderPos - xLeftOpeness,yStartRenderPos,xStartRenderPos,yStartRenderPos - yUpOpeness,0,0,0,0.94f);
        RenderingTools.fill(matrices,xStartRenderPos,yStartRenderPos,xStartRenderPos + xRightOpeness,yStartRenderPos - yUpOpeness,0,0,0,0.94f);
        if (ticker >= animLength) {
            this.getComponents().render(matrices, xPos + borderWidth, yPos + borderWidth, pTicks, mousex, mousey, ticker, animLength);
        }
    }





    public BlackBackgroundTooltip setStartYOpeness(int y){
        this.startYOpeness = y;
        return this;
    }

}
