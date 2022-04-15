package com.finderfeed.solarforge.local_library.client.particles;

import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
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
        PoseStack matrices = new PoseStack();
        matrices.pushPose();
        double s = size/2;
        double x1 = Mth.lerp(partialTicks,xOld,this.x) - s;
        double y1 = Mth.lerp(partialTicks,yOld,this.y) - s;
        double x2 = x1 + size;
        double y2 = y1 + size;
        matrices.translate(Mth.lerp(partialTicks,xOld,this.x),Mth.lerp(partialTicks,yOld,this.y),0);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks,rotationValueOld,rotationValue)));
        Matrix4f matrix4f = matrices.last().pose();
        vertex.vertex(matrix4f,(float) -s,(float) s,500).uv(0,0).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(matrix4f,(float) s,(float) s,500).uv(1,0).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(matrix4f,(float) s,(float) -s,500).uv(1,1).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(matrix4f,(float)  -s,(float) -s,500).uv(0,1).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();

        matrices.popPose();
    }


}
