package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import com.finderfeed.solarcraft.local_library.bedrock_loader.JsonHelper;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.*;

import java.lang.Math;
import java.util.Arrays;
import java.util.List;

public class FDCube {

    private static final float POS_MULTIPLIER = 1/16f;
    private FDFace[] faces = new FDFace[6];

    private FDCube(FDFace... faces){
        this.faces[0] = faces[0];
        this.faces[1] = faces[1];
        this.faces[2] = faces[2];
        this.faces[3] = faces[3];
        this.faces[4] = faces[4];
        this.faces[5] = faces[5];
    }



    public void render(PoseStack matrices, VertexConsumer vertex, int light, int overlay,float r,float g,float b,float a){
        matrices.pushPose();

        Matrix4f m = matrices.last().pose();
        Matrix3f n = matrices.last().normal();

        for (FDFace fdFace : faces){

            Vector3f normal = n.transform(new Vector3f(
                    (float)fdFace.getNormal().x,
                    (float)fdFace.getNormal().y,
                    (float)fdFace.getNormal().z
            ));

            for (FDVertex v : fdFace.getVertices()){
                Vec3 vertexPos = v.getPosition();
                vertex.vertex(m,(float)vertexPos.x * POS_MULTIPLIER,(float)vertexPos.y * POS_MULTIPLIER,(float)vertexPos.z * POS_MULTIPLIER);
                vertex.color(r,g,b,a);
                vertex.uv(v.getU(),v.getV());
                vertex.overlayCoords(overlay);
                vertex.uv2(light);
                vertex.normal(normal.x, normal.y, normal.z);
                vertex.endVertex();
            }
        }

        matrices.popPose();
    }


    /*
    north
    east
    south
    west
    up
    down
     */

    private static final Vec3[] normals = {
            new Vec3(0,0,-1),
            new Vec3(1,0,0),
            new Vec3(0,0,1),
            new Vec3(-1,0,0),
            new Vec3(0,1,0),
            new Vec3(0,-1,0),
    };


    public static FDCube fromJson(JsonObject scube,int textureWidth,int textureHeight,float scale){
        Vec3 origin = JsonHelper.parseVec3(scube.getAsJsonArray("origin"));
        Vec3 size = JsonHelper.parseVec3(scube.getAsJsonArray("size"));
        Vec3 pivot = JsonHelper.parseVec3(scube,"pivot");
        Vec3 rotation = JsonHelper.parseVec3(scube,"rotation");
        // transformations around cube pivot
        PoseStack matrices = new PoseStack();
        matrices.pushPose();
        matrices.translate(pivot.x,pivot.y,pivot.z);
        boolean shouldRotate = rotation.x != 0 || rotation.y != 0 || rotation.z != 0;
        if (shouldRotate) {
            matrices.mulPose(new Quaternionf().rotationZYX((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y), (float) Math.toRadians(rotation.z)));
        }
        Matrix4f m = matrices.last().pose();
        //center and size transformations
        Vec3 cubeCenter = origin.add(size.multiply(0.5,0.5,0.5));
        Vec3 between = origin.subtract(cubeCenter).multiply(
                scale - 1,
                scale - 1,
                scale - 1);
        origin = origin.add(between);
        size = size.multiply(scale,scale,scale);
        //transformed vertex positions
        Vec3 v1 = mul(origin,m);
        Vec3 v2 = mul(origin.add(size.x,0,0),m);
        Vec3 v3 = mul(origin.add(0,size.y,0),m);
        Vec3 v4 = mul(origin.add(0,0,size.z),m);
        Vec3 v5 = mul(origin.add(size.x,size.y,0),m);
        Vec3 v6 = mul(origin.add(0,size.y,size.z),m);
        Vec3 v7 = mul(origin.add(size.x,0,size.z),m);
        Vec3 v8 = mul(origin.add(size),m);
        matrices.popPose();
        //transformed normals
        matrices.pushPose();
        if (shouldRotate) {
            matrices.mulPose(new Quaternionf().rotationZYX((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y), (float) Math.toRadians(rotation.z)));
        }
        Matrix4f mn = matrices.last().pose();
        List<Vec3> tnormals = Arrays.stream(normals).map(normal->mul(normal,mn)).toList();
        matrices.popPose();

        JsonObject faceDatas = scube.getAsJsonObject("uv");
        FDFace north = createFace(v1,v2,v5,v3,tnormals.get(0),faceDatas.getAsJsonObject("north"),textureWidth,textureHeight);
        FDFace east = createFace(v2,v7,v8,v5,tnormals.get(1),faceDatas.getAsJsonObject("east"),textureWidth,textureHeight);
        FDFace south = createFace(v7,v4,v6,v8,tnormals.get(2),faceDatas.getAsJsonObject("south"),textureWidth,textureHeight);
        FDFace west = createFace(v4,v1,v3,v6,tnormals.get(3),faceDatas.getAsJsonObject("west"),textureWidth,textureHeight);
        FDFace up = createFace(v3,v5,v8,v6,tnormals.get(4),faceDatas.getAsJsonObject("up"),textureWidth,textureHeight);
        FDFace down = createFace(v1,v2,v7,v4,tnormals.get(5),faceDatas.getAsJsonObject("down"),textureWidth,textureHeight);
        return new FDCube(north,east,south,west,up,down);
    }

    private static FDFace createFace(Vec3 v1,Vec3 v2,Vec3 v3,Vec3 v4,Vec3 normal,JsonObject faceData,int texWidth,int texHeight){
        Vec2 uv = JsonHelper.parseVec2(faceData.getAsJsonArray("uv"));
        Vec2 uv_size = JsonHelper.parseVec2(faceData.getAsJsonArray("uv_size"));
        uv = new Vec2(uv.x / texWidth,uv.y / texHeight).add(new Vec2(0,1));
        uv_size = new Vec2(uv_size.x / texWidth,uv_size.y / texHeight * -1);

        FDVertex vertex1 = new FDVertex(uv.x + uv_size.x,uv.y + uv_size.y,v1);
        FDVertex vertex2 = new FDVertex(uv.x ,uv.y + uv_size.y,v2);
        FDVertex vertex3 = new FDVertex(uv.x ,uv.y,v3);
        FDVertex vertex4 = new FDVertex(uv.x + uv_size.x,uv.y,v4);
        return new FDFace(normal,vertex1,vertex2,vertex3,vertex4);
    }



    private static Vec3 mul(Vec3 v,Matrix4f matrix4f){
        Vector4f vc = fromVec3ToVec4(v).mul(matrix4f);
        return new Vec3(vc.x,vc.y,vc.z);
    }
    private static Vector4f fromVec3ToVec4(Vec3 v){
        return new Vector4f((float)v.x,(float)v.y,(float)v.z,1.0f);
    }

}
