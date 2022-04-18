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
        if (xPos == x && yPos == y) {
            double xOpeness;
            double yOpeness = startYOpeness;
            if (ticker < k) {
                double a = (ticker + pTicks) / animLength * 4;
                xOpeness = Mth.lerp(a * a, 0, maxSizeX);
            } else {
                xOpeness = maxSizeX;

                double a = FDMathHelper.clamp(0, (ticker - k + pTicks) / (animLength - k), 1);
                yOpeness = startYOpeness + Mth.lerp(a * a, 0, maxSizeY - startYOpeness);
            }
            RenderingTools.fill(matrices,xPos,yPos,xPos+xOpeness,yPos+yOpeness,0,0,0,0.9f);
        }else{
            xAndYRender(matrices,xPos,yPos,pTicks,x,y);
        }

        if (ticker >= animLength) {
            this.getComponents().render(matrices, xPos + borderWidth, yPos + borderWidth, pTicks, mousex, mousey, ticker, animLength);
        }
    }

    private void xAndYRender(PoseStack matrices, int xPos, int yPos, float pTicks, int x, int y){
        float k = animLength/4f;
        double percentXOpeness = ticker < k ? (ticker + pTicks)/animLength*4 : 1f;
        percentXOpeness *= percentXOpeness;
        double percentYOpeness = FDMathHelper.clamp(0,ticker < k ? 0f : (ticker + pTicks - k)/(animLength - k),1);
        percentYOpeness *= percentYOpeness;
        if (x > xPos){
            //X to the right
            if (x > xPos + maxSizeX){
                double xOpeness = Mth.lerp(percentXOpeness,0,maxSizeX);
                double xPos1 = xPos + maxSizeX;
                double xPos2 = xPos1 - xOpeness;
                //y block start-------------------------------------------
                if (y > yPos){
                    if (y > yPos + maxSizeX){
                        //Y below
                        double yOpeness = startYOpeness + Mth.lerp(percentYOpeness,0,maxSizeY);
                        RenderingTools.fill(matrices,xPos1,yPos + maxSizeY,xPos2,yPos + maxSizeY - yOpeness,1,1,1,0.9f);
                    }else{
                        //Y between
                        double remainingTopInitOpeness = FDMathHelper.clamp(0,startYOpeness - (yPos + maxSizeY - y),startYOpeness);
                        if (x + startYOpeness > yPos + maxSizeY){
                            double yOpenessBelow = FDMathHelper.clamp(0,startYOpeness,yPos + maxSizeY - y);
                            double yOpenessAbove = remainingTopInitOpeness + Mth.lerp(percentYOpeness,0,Math.abs(yPos - y) - remainingTopInitOpeness);


                        }else{
                            double yOpenessBelow = startYOpeness + Mth.lerp(percentYOpeness,0,Math.abs(yPos + maxSizeY - x) - startYOpeness);
                            double yOpenessAbove = Mth.lerp(percentYOpeness,0,Math.abs(yPos - y));

                        }
                    }
                }else{
                    //Y above
                    double yOpeness = startYOpeness + Mth.lerp(percentYOpeness,0,maxSizeY);
                }
                //y block end----------------------------------------------

            }else{
                //X between
                double xOpenessLeft = Mth.lerp(percentXOpeness,0,Math.abs(x - xPos));
                double xOpenessRight = Mth.lerp(percentXOpeness,0,Math.abs(maxSizeX + xPos - x));
                //y block start-------------------------------------------
                if (y > yPos){
                    if (y > yPos + maxSizeX){
                        //Y below
                        double yOpeness = startYOpeness + Mth.lerp(percentYOpeness,0,maxSizeY);
                    }else{
                        //Y between
                        double remainingTopInitOpeness = FDMathHelper.clamp(0,startYOpeness - (yPos + maxSizeY - y),startYOpeness);
                        if (x + startYOpeness > yPos + maxSizeY){
                            double yOpenessBelow = FDMathHelper.clamp(0,startYOpeness,yPos + maxSizeY - y);
                            double yOpenessAbove = remainingTopInitOpeness + Mth.lerp(percentYOpeness,0,Math.abs(yPos - y) - remainingTopInitOpeness);


                        }else{
                            double yOpenessBelow = startYOpeness + Mth.lerp(percentYOpeness,0,Math.abs(yPos + maxSizeY - x) - startYOpeness);
                            double yOpenessAbove = Mth.lerp(percentYOpeness,0,Math.abs(yPos - y));

                        }
                    }
                }else{
                    //Y above
                    double yOpeness = startYOpeness + Mth.lerp(percentYOpeness,0,maxSizeY);
                }
                //y block end----------------------------------------------
            }
        }else{
            //X to the left
            double xOpeness = Mth.lerp(percentXOpeness,0,maxSizeX);

            //y block start-------------------------------------------
            if (y > yPos){
                if (y > yPos + maxSizeX){
                    //Y below
                    double yOpeness = startYOpeness + Mth.lerp(percentYOpeness,0,maxSizeY);
                }else{
                    //Y between
                    double remainingTopInitOpeness = FDMathHelper.clamp(0,startYOpeness - (yPos + maxSizeY - y),startYOpeness);
                    if (x + startYOpeness > yPos + maxSizeY){
                        double yOpenessBelow = FDMathHelper.clamp(0,startYOpeness,yPos + maxSizeY - y);
                        double yOpenessAbove = remainingTopInitOpeness + Mth.lerp(percentYOpeness,0,Math.abs(yPos - y) - remainingTopInitOpeness);


                    }else{
                        double yOpenessBelow = startYOpeness + Mth.lerp(percentYOpeness,0,Math.abs(yPos + maxSizeY - x) - startYOpeness);
                        double yOpenessAbove = Mth.lerp(percentYOpeness,0,Math.abs(yPos - y));

                    }
                }
            }else{
                //Y above
                double yOpeness = startYOpeness + Mth.lerp(percentYOpeness,0,maxSizeY);
            }
            //y block end----------------------------------------------
        }
    }




    public BlackBackgroundTooltip setStartYOpeness(int y){
        this.startYOpeness = y;
        return this;
    }

}
