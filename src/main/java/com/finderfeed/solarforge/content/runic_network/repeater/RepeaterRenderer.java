package com.finderfeed.solarforge.content.runic_network.repeater;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

//AND DESERT YOU! NEVER GONNA MAKE YOU CRY, NEVER GONNA SAY GOODBYE, NEVER GONNA TELL A LIE
public class RepeaterRenderer implements BlockEntityRenderer<BaseRepeaterTile> {


    public RepeaterRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(BaseRepeaterTile tile, float v, PoseStack matrices, MultiBufferSource multiBufferSource, int i, int i1) {
        matrices.pushPose();
        if (tile.getConnections() != null){
            tile.getConnections().forEach((pos)->{
                Vec3 tilepos = new Vec3(tile.getBlockPos().getX() +0.5,tile.getBlockPos().getY() +0.5,tile.getBlockPos().getZ() +0.5);
                Vec3 vector = Helpers.getBlockCenter(pos).subtract(tilepos);


                RenderingTools.renderRay(matrices,multiBufferSource,0.25f,(float)vector.length(),(mat)->{
                    RenderingTools.applyMovementMatrixRotations(mat,vector.normalize());
                },false,0,v);
            });
        }
        matrices.popPose();
//        if (tile.getConnections() != null){
//            Vec3 tilepos = new Vec3(tile.getBlockPos().getX() +0.5,tile.getBlockPos().getY() +0.5,tile.getBlockPos().getZ() +0.5);
//            Vec3 targetPos = new Vec3(tile.getRepeaterConnection().getX() +0.5,tile.getRepeaterConnection().getY() +0.5,tile.getRepeaterConnection().getZ() +0.5);
//            Vec3 vector = new Vec3((targetPos.x - tilepos.x), (targetPos.y - tilepos.y), (targetPos.z - tilepos.z));
//            Vec3 horizontalVector = new Vec3(targetPos.x - tilepos.x, 0, targetPos.z - tilepos.z);
//
//            float length =(float) vector.length();
//
//            poseStack.pushPose();
//            poseStack.translate(0,0.1,0);
//            RenderingTools.renderRay(poseStack,multiBufferSource,0.25f,length,
//                    (stack)->{
//                        if (horizontalVector.x >= 0) {
//                            stack.mulPose(Vector3f.YN.rotationDegrees((float) Math.toDegrees(Math.acos(-horizontalVector.normalize().z))));
//                        } else {
//                            stack.mulPose(Vector3f.YN.rotationDegrees(180 + (float) Math.toDegrees(Math.acos(horizontalVector.normalize().z))));
//                        }
//
//                        stack.mulPose(Vector3f.XN.rotationDegrees((float) Math.toDegrees(Math.acos(vector.normalize().y))));
//
//                    }
//                ,true,1,v);
//            poseStack.popPose();
//
//            poseStack.pushPose();
//
//            tile.getConnectedEnergyConsumers().forEach((pos)->{
//                Vec3 tilepos1 = new Vec3(tile.getBlockPos().getX() +0.5,tile.getBlockPos().getY() +0.5,tile.getBlockPos().getZ() +0.5);
//                Vec3 targetPos1 = new Vec3(pos.getX() +0.5,pos.getY() +0.5,pos.getZ() +0.5);
//                Vec3 vector1 = new Vec3((targetPos1.x - tilepos1.x), (targetPos1.y - tilepos1.y), (targetPos1.z - tilepos1.z));
//                Vec3 horizontalVector1 = new Vec3(targetPos1.x - tilepos1.x, 0, targetPos1.z - tilepos1.z);
//
//                float length1 =(float) vector1.length();
//
//                poseStack.pushPose();
//                poseStack.translate(0,0.1,0);
//                RenderingTools.renderRay(poseStack,multiBufferSource,0.25f,length1,
//                        (stack)->{
//                            if (horizontalVector1.x >= 0) {
//                                stack.mulPose(Vector3f.YN.rotationDegrees((float) Math.toDegrees(Math.acos(-horizontalVector1.normalize().z))));
//                            } else {
//                                stack.mulPose(Vector3f.YN.rotationDegrees(180 + (float) Math.toDegrees(Math.acos(horizontalVector1.normalize().z))));
//                            }
//
//                            stack.mulPose(Vector3f.XN.rotationDegrees((float) Math.toDegrees(Math.acos(vector1.normalize().y))));
//
//                        }
//                        ,true,1,v);
//                poseStack.popPose();
//            });
//
//
//            poseStack.popPose();
//        }
    }
}
