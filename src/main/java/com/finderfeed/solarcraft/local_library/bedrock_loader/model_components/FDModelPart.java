package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FDModelPart {

    public float x = 0;
    public float y = 0;
    public float z = 0;

    public float xRot;
    public float yRot;
    public float zRot;

    public Vec3 initRotation;
    public boolean isVisible = true;

    private float scaleX = 1f;
    private float scaleY = 1f;
    private float scaleZ = 1f;
    public final Vec3 pivot;


    public final List<FDCube> cubes;

    protected final Map<String,FDModelPart> children;

    public String name;

    public FDModelPart(String name,List<FDCube> cubes, Vec3 pivot, Vec3 initRotation){
        this.pivot = pivot;
        this.name = name;
        this.cubes = Collections.unmodifiableList(cubes);
        this.children = new HashMap<>();
        this.xRot = (float)initRotation.x;
        this.yRot = (float)initRotation.y;
        this.zRot = (float)initRotation.z;
        this.initRotation = initRotation;
    }


    public void render(PoseStack matrices, VertexConsumer vertex, int light, int overlay,float r,float g,float b,float a){
        matrices.pushPose();

        if (isVisible){
            this.translateAndRotate(matrices);

            for (FDCube cube : cubes){
                cube.render(matrices,vertex,light,overlay,r,g,b,a);
            }

            for (FDModelPart child : children.values()){
                child.render(matrices,vertex,light,overlay,r,g,b,a);
            }

        }

        matrices.popPose();
    }


    private void translateAndRotate(PoseStack matrices){
        matrices.translate(x/16,y/16,z/16);
        matrices.translate(pivot.x/16,pivot.y/16,pivot.z/16);
        if (xRot != 0 || yRot != 0 || zRot != 0){
            matrices.mulPose(new Quaternionf().rotationZYX(
                    (float)Math.toRadians(zRot),
                    (float)Math.toRadians(yRot),
                    (float)Math.toRadians(xRot)));
        }
        matrices.translate(-pivot.x/16,-pivot.y/16,-pivot.z/16);

        matrices.scale(scaleX,scaleY,scaleZ);
    }


    public void reset(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.xRot = (float)initRotation.x;
        this.yRot = (float)initRotation.y;
        this.zRot = (float)initRotation.z;
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.scaleZ = 1f;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Map<String, FDModelPart> getChildren() {
        return Collections.unmodifiableMap(children);
    }
}
