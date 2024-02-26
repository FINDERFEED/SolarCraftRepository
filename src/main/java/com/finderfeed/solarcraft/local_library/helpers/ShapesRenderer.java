package com.finderfeed.solarcraft.local_library.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ShapesRenderer {

    public static final VertexConstructor POSITION_COLOR = ((vertex, matrix,vx, vy, vz, r, g, b, a, u, v,light) -> {
       vertex.vertex(matrix,vx,vy,vz).color(r,g,b,a).endVertex();
    });

    public static final VertexConstructor POSITION_COLOR_UV = ((vertex, matrix,vx, vy, vz, r, g, b, a, u, v,light) -> {
       vertex.vertex(matrix,vx,vy,vz).color(r,g,b,a).uv(u,v).endVertex();
    });

    public static final VertexConstructor POSITION_COLOR_UV_LIGHTMAP = ((vertex, matrix,vx, vy, vz, r, g, b, a, u, v,light) -> {
       vertex.vertex(matrix,vx,vy,vz).color(r,g,b,a).uv(u,v).uv2(light).overlayCoords(OverlayTexture.NO_OVERLAY).endVertex();
    });


    public static void renderSphere(VertexConstructor v,VertexConsumer vertex, PoseStack matrices, int detalization, float radius,float r,float g,float b,float a,int light,boolean invert){
        matrices.pushPose();

        Matrix4f matrix4f = matrices.last().pose();

        for (int longs = 0; longs < detalization;longs++){
            float pl1 = longs / (float) detalization;
            float pl2 = (longs + 1) / (float) detalization;
            float theta = pl1 * (float) Math.PI;
            float thetaNext = pl2 * (float) Math.PI;
            float tsin = Math.sin(theta);
            float tnsin = Math.sin(thetaNext);
            float tcos = Math.cos(theta);
            float tncos = Math.cos(thetaNext);
            for (int lats = 0; lats < detalization;lats++){
                float pa1 = lats / (float) detalization;
                float pa2 = (lats + 1) / (float) detalization;
                float phi = pa1 * (float) Math.PI * 2;
                float phiNext = pa2 * (float) Math.PI * 2;
                float psin = Math.sin(phi);
                float pnsin = Math.sin(phiNext);
                float pcos = Math.cos(phi);
                float pncos = Math.cos(phiNext);

                Vector3f p1 = new Vector3f(
                        radius * tsin * pcos,
                        radius * tcos,
                        radius * tsin * psin
                );

                Vector3f p2 = new Vector3f(
                        radius * tnsin * pcos,
                        radius * tncos,
                        radius * tnsin * psin
                );

                Vector3f p3 = new Vector3f(
                        radius * tsin * pncos,
                        radius * tcos,
                        radius * tsin * pnsin
                );

                Vector3f p4 = new Vector3f(
                        radius * tnsin * pncos,
                        radius * tncos,
                        radius * tnsin * pnsin
                );
                if (invert) {
                    v.process(vertex, matrix4f, p1.x, p1.y, p1.z, r, g, b, a, pa1, pl1, light);
                    v.process(vertex, matrix4f, p2.x, p2.y, p2.z, r, g, b, a, pa1, pl2, light);
                    v.process(vertex, matrix4f, p4.x, p4.y, p4.z, r, g, b, a, pa2, pl2, light);
                    v.process(vertex, matrix4f, p3.x, p3.y, p3.z, r, g, b, a, pa2, pl1, light);
                }else {
                    v.process(vertex, matrix4f, p3.x, p3.y, p3.z, r, g, b, a, pa2, pl1, light);
                    v.process(vertex, matrix4f, p4.x, p4.y, p4.z, r, g, b, a, pa2, pl2, light);
                    v.process(vertex, matrix4f, p2.x, p2.y, p2.z, r, g, b, a, pa1, pl2, light);
                    v.process(vertex, matrix4f, p1.x, p1.y, p1.z, r, g, b, a, pa1, pl1, light);
                }

            }
        }


        matrices.popPose();
    }

    @FunctionalInterface
    public interface VertexConstructor {

        void process(VertexConsumer vertex,Matrix4f mat,float vx,float vy,float vz,float r,float g,float b,float a,float u,float v,int light);

    }
}
