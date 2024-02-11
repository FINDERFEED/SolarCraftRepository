package com.finderfeed.solarcraft.local_library.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Math;
import org.joml.Matrix4f;

public class ShapesRenderer {


    /**
     * consumer - position color uv
     * */
    public static void renderSphere(VertexConsumer vertex, PoseStack matrices, int detalization, float radius){
        matrices.pushPose();

        Matrix4f matrix4f = matrices.last().pose();
        for (int i = 0; i < detalization-1;i++){
            float angle = i / (float)detalization * (float) Math.PI * 2 - (float) Math.PI;
            float angle2 = (i+1) / (float)detalization * (float) Math.PI * 2 - (float) Math.PI;
            for (int g = 0; g < detalization;g++){
                float anglei = i / (float)detalization * (float) Math.PI - (float) Math.PI/2f;
                float anglei2 = (i+1) / (float)detalization * (float) Math.PI - (float) Math.PI/2f;
                float x1 = radius * Math.sin(angle) * Math.cos(anglei);
                float y1 = radius * Math.sin(angle) * Math.sin(anglei);
                float z1 = radius * Math.cos(angle);
                float x2 = radius * Math.sin(angle2) * Math.cos(anglei2);
                float y2 = radius * Math.sin(angle2) * Math.sin(anglei2);
                float z2 = radius * Math.cos(angle2);

            }
        }

        matrices.popPose();
    }

}
