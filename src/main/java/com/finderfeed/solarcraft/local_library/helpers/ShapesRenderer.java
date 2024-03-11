package com.finderfeed.solarcraft.local_library.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ShapesRenderer {

    private static final Vec3 UP = new Vec3(0,1,0);

    public static final VertexConstructor POSITION_COLOR = ((vertex, matrix,vx, vy, vz, r, g, b, a, u, v,light) -> {
       vertex.vertex(matrix,vx,vy,vz).color(r,g,b,a).endVertex();
    });

    public static final VertexConstructor POSITION_COLOR_UV = ((vertex, matrix,vx, vy, vz, r, g, b, a, u, v,light) -> {
       vertex.vertex(matrix,vx,vy,vz).color(r,g,b,a).uv(u,v).endVertex();
    });

    public static final VertexConstructor POSITION_COLOR_UV_LIGHTMAP = ((vertex, matrix,vx, vy, vz, r, g, b, a, u, v,light) -> {
       vertex.vertex(matrix,vx,vy,vz).color(r,g,b,a).uv(u,v).uv2(light).overlayCoords(OverlayTexture.NO_OVERLAY).endVertex();
    });

    public static void renderQuad(VertexConstructor v,VertexConsumer vertex,PoseStack matrices,float size,float roll,float r,float g,float b,float a,int light,Vec3 dir){
        matrices.pushPose();
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),roll));
        RenderingTools.applyMovementMatrixRotations(matrices,dir);
        Matrix4f m = matrices.last().pose();
        v.process(vertex,m,-size,0,-size,r,g,b,a,0,0,light);
        v.process(vertex,m,size,0,-size,r,g,b,a,1,0,light);
        v.process(vertex,m,size,0,size,r,g,b,a,1,1,light);
        v.process(vertex,m,-size,0,size,r,g,b,a,0,1,light);

        v.process(vertex,m,-size,0,size,r,g,b,a,0,1,light);
        v.process(vertex,m,size,0,size,r,g,b,a,1,1,light);
        v.process(vertex,m,size,0,-size,r,g,b,a,1,0,light);
        v.process(vertex,m,-size,0,-size,r,g,b,a,0,0,light);

        matrices.popPose();
    }


    public static void renderTrail(VertexConstructor v,VertexConsumer consumer,PoseStack matrices,Trail trail,float rad,float pticks,boolean cubic,float r,float g,float b,float a,int light){
        matrices.pushPose();

        Matrix4f mat = matrices.last().pose();
        Vec3[] p = trail.getTrailPointsForRendering(pticks);
        for (int i = 0; i < p.length - 1;i++){
            float rad1 = rad;
            float rad2 = rad;
            float per1 = (1 - i/((float)p.length - 1));
            float per2 = (1 - (i+1)/((float)p.length - 1));
            if (!cubic){
                rad1 = per1 * rad;
                rad2 = per2 * rad;
            }
            Vec3 point1 = p[i];
            Vec3 point2 = p[i + 1];
            Vec3 speed1;
            Vec3 speed2;
            if (i == 0){
                speed2 = point1.subtract(point2).normalize();
                speed1 = speed2;
            }else{
                Vec3 prev = p[i - 1];
                speed2 = point1.subtract(point2).normalize();
                speed1 = prev.subtract(point1).normalize();
            }


            Vec3 renderDir1 = UP.cross(speed1).normalize();
            Vec3 renderDir2 = UP.cross(speed2).normalize();

            renderDir1 = speed1.cross(renderDir1).normalize();
            renderDir2 = speed2.cross(renderDir2).normalize();

            Vec3 dir1 = renderDir1;
            Vec3 dir2 = renderDir2;
            Vec3 p1 = point1.add(dir1);
            Vec3 p2 = point2.add(dir2);
            Vec3 p3 = point2.add(dir2.reverse());
            Vec3 p4 = point1.add(dir1.reverse());
            v.process(consumer,mat,(float)p1.x,(float)p1.y,(float)p1.z,r,g,b,a,per1,1,light);
            v.process(consumer,mat,(float)p2.x,(float)p2.y,(float)p2.z,r,g,b,a,per2,1,light);
            v.process(consumer,mat,(float)p3.x,(float)p3.y,(float)p3.z,r,g,b,a,per2,0,light);
            v.process(consumer,mat,(float)p4.x,(float)p4.y,(float)p4.z,r,g,b,a,per1,0,light);

            v.process(consumer,mat,(float)point1.x-0.1f,(float)point1.y,(float)point1.z-0.1f,1,0,0,1,per1,1,light);
            v.process(consumer,mat,(float)point1.x+0.1f,(float)point1.y,(float)point1.z-0.1f,1,0,0,1,per2,1,light);
            v.process(consumer,mat,(float)point1.x+0.1f,(float)point1.y,(float)point1.z+0.1f,1,0,0,1,per2,0,light);
            v.process(consumer,mat,(float)point1.x-0.1f,(float)point1.y,(float)point1.z+0.1f,1,0,0,1,per1,0,light);

        }


        matrices.popPose();
    }

    public static void renderSphere(VertexConstructor v,VertexConsumer vertex, PoseStack matrices, int detalization, float radius,float r,float g,float b,float a,int light){
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

                v.process(vertex, matrix4f, p3.x, p3.y, p3.z, r, g, b, a, pa2, pl1, light);
                v.process(vertex, matrix4f, p4.x, p4.y, p4.z, r, g, b, a, pa2, pl2, light);
                v.process(vertex, matrix4f, p2.x, p2.y, p2.z, r, g, b, a, pa1, pl2, light);
                v.process(vertex, matrix4f, p1.x, p1.y, p1.z, r, g, b, a, pa1, pl1, light);


            }
        }


        matrices.popPose();
    }

    @FunctionalInterface
    public interface VertexConstructor {

        void process(VertexConsumer vertex,Matrix4f mat,float vx,float vy,float vz,float r,float g,float b,float a,float u,float v,int light);

    }
}
