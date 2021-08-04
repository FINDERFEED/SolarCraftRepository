package com.finderfeed.solarforge.magic_items.runic_network.repeater;

import com.finderfeed.solarforge.for_future_library.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

//AND DESERT YOU! NEVER GONNA MAKE YOU CRY, NEVER GONNA SAY GOODBYE, NEVER GONNA TELL A LIE
public class RepeaterRenderer implements BlockEntityRenderer<BaseRepeaterTile> {


    public RepeaterRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(BaseRepeaterTile tile, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {



        if (tile.getRepeaterConnection() != null){
            Vec3 tilepos = new Vec3(tile.getBlockPos().getX() +0.5,tile.getBlockPos().getY() +0.5,tile.getBlockPos().getZ() +0.5);
            Vec3 targetPos = new Vec3(tile.getRepeaterConnection().getX() +0.5,tile.getRepeaterConnection().getY() +0.5,tile.getRepeaterConnection().getZ() +0.5);
            Vec3 vector = new Vec3((targetPos.x - tilepos.x), (targetPos.y - tilepos.y), (targetPos.z - tilepos.z));
            Vec3 horizontalVector = new Vec3(targetPos.x - tilepos.x, 0, targetPos.z - tilepos.z);

            float length =(float) vector.length();

            poseStack.pushPose();
            poseStack.translate(0,0.1,0);
            RenderingTools.renderRay(poseStack,multiBufferSource,0.25f,length,
                    (stack)->{
                        if (horizontalVector.x >= 0) {
                            stack.mulPose(Vector3f.YN.rotationDegrees((float) Math.toDegrees(Math.acos(-horizontalVector.normalize().z))));
                        } else {
                            stack.mulPose(Vector3f.YN.rotationDegrees(180 + (float) Math.toDegrees(Math.acos(horizontalVector.normalize().z))));
                        }

                        stack.mulPose(Vector3f.XN.rotationDegrees((float) Math.toDegrees(Math.acos(vector.normalize().y))));

                    }
                ,true,1,v);
            poseStack.popPose();
        }
    }
}
