package com.finderfeed.solarforge.local_library.client.tooltips;

import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
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
        double xOpeness;
        double yOpeness = startYOpeness;
        if (ticker < animLength/4){
            double a = (ticker + pTicks)/animLength*4;
            xOpeness = Mth.lerp(a*a,0,maxSizeX);
        }else{
            xOpeness = maxSizeX;
            double k = animLength/4d;
            double a = FinderfeedMathHelper.clamp(0,(ticker - k + pTicks)/(animLength - k),1);
            yOpeness = startYOpeness + Mth.lerp(a*a,0,maxSizeY - startYOpeness);
        }
        int xPos = calculateXRenderPos(x);
        int yPos = calculateYRenderPos(y);

        RenderingTools.fill(matrices,xPos,yPos,xPos+xOpeness,yPos+yOpeness,0,0,0,0.9f);
        if (ticker >= animLength) {
            this.getComponents().render(matrices, xPos + borderWidth, yPos + borderWidth, pTicks, mousex, mousey, ticker, animLength);
        }
    }




    public BlackBackgroundTooltip setStartYOpeness(int y){
        this.startYOpeness = y;
        return this;
    }

}
