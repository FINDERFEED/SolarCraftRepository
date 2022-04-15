package com.finderfeed.solarforge.local_library.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;

public abstract class TextureScreenParticle extends ScreenParticle{

    private int rCol;
    private int gCol;
    private int bCol;
    private int alpha;

    public TextureScreenParticle(int lifetime,double x, double y, double xSpeed, double ySpeed, double xAcceleration, double yAcceleration,int rCol,int gCol,int bCol,int alpha) {
        super(lifetime,x, y, xSpeed, ySpeed, xAcceleration, yAcceleration);
        this.rCol = rCol;
        this.gCol = gCol;
        this.bCol = bCol;
        this.alpha = alpha;
    }
    public TextureScreenParticle(int lifetime,double x, double y, double xSpeed, double ySpeed,int rCol,int gCol,int bCol,int alpha) {
        super(lifetime,x, y, xSpeed, ySpeed, 0,0);
        this.rCol = rCol;
        this.gCol = gCol;
        this.bCol = bCol;
        this.alpha = alpha;
    }

    @Override
    public void render(VertexConsumer vertex, float partialTicks) {
        double x1 = Mth.lerp(partialTicks,xOld,this.x);
        double y1 = Mth.lerp(partialTicks,yOld,this.y);
        double x2 = x1 + size;
        double y2 = y1 + size;

        vertex.vertex(x1,y1,500).uv(0,1).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(x2,y1,500).uv(1,1).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(x1,y2,500).uv(0,0).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(x2,y2,500).uv(1,0).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();

    }


}
