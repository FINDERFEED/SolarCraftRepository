package com.finderfeed.solarcraft.local_library.client.particles;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

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
        matrices.translate(Mth.lerp(partialTicks,xOld,this.x),Mth.lerp(partialTicks,yOld,this.y),0);
//
//        Vector3f v = RenderingTools.ZP();
//        Quaternionf q = new Quaternionf(new AxisAngle4f(Mth.lerp(partialTicks,rotationValueOld,rotationValue),v));

//        matrices.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks,rotationValueOld,rotationValue)));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),Mth.lerp(partialTicks,rotationValueOld,rotationValue)));
        Matrix4f matrix4f = matrices.last().pose();
        vertex.vertex(matrix4f,(float) -s,(float) s,500).uv(0,0).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(matrix4f,(float) s,(float) s,500).uv(1,0).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(matrix4f,(float) s,(float) -s,500).uv(1,1).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(matrix4f,(float)  -s,(float) -s,500).uv(0,1).color(rCol,gCol,bCol,alpha).uv2(LightTexture.FULL_BRIGHT).endVertex();

        matrices.popPose();
    }


}
