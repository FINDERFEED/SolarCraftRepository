package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FDModelPart {

    public float x = 0;
    public float y = 0;
    public float z = 0;

    public float xRot;
    public float yRot;
    public float zRot;

    private float scaleX;
    private float scaleY;
    private float scaleZ;
    public final Vec3 pivot;

    public final List<FDCube> cubes;

    public final Map<String,ModelPart> children;

    private FDModelPart(List<FDCube> cubes, Vec3 pivot, float xRot, float yRot, float zRot, Map<String,ModelPart> children){
        this.pivot = pivot;
        this.cubes = Collections.unmodifiableList(cubes);
        this.children = Collections.unmodifiableMap(children);
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
    }


    public void render(PoseStack matrices, VertexConsumer vertex, int light, int overlay,float r,float g,float b,float a){
        matrices.pushPose();



        matrices.popPose();
    }


    private void translateAndRotate(PoseStack matrices){
        matrices.translate(pivot.x/16,pivot.y/16,pivot.z/16);
        matrices.translate(x/16,y/16,z/16);
        if (xRot != 0 || yRot != 0 || zRot != 0){
            matrices.mulPose(new Quaternionf().rotationZYX(zRot,yRot,xRot));
        }
        matrices.scale(scaleX,scaleY,scaleZ);
    }

}
