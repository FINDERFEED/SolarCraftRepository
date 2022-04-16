package com.finderfeed.solarforge.magic.blocks.infusing_table_things;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.AbstractRunicEnergyContainerRCBERenderer;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.finderfeed.solarforge.world_generation.structures.Structures;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class InfuserRenderer extends AbstractRunicEnergyContainerRenderer<InfuserTileEntity> {
    public final ResourceLocation text = new ResourceLocation("solarforge","textures/misc/solar_infuser_ring.png");
    public final ResourceLocation fancyRing = new ResourceLocation("solarforge","textures/misc/fancy_ring_1.png");
    public InfuserRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);

    }

    @Override
    public void render(InfuserTileEntity tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int light2) {
        super.render(tile,partialTicks,matrices,buffer,light,light2);
        float time = (tile.getLevel().getGameTime()+partialTicks) ;
        if (tile.RECIPE_IN_PROGRESS) {
            matrices.pushPose();

            matrices.translate(0, -0.20, 0);
            float bigRing = 33 - (time + 15) % 67;
//            if (bigRing >= 0) {
//                matrices.translate(0, bigRing / 100, 0);
//            } else {
//                matrices.translate(0, -bigRing / 100, 0);
//            }
            matrices.translate(0,Math.sin(time/20)*0.15,0);
            drawRing(partialTicks, matrices, buffer, light, light2, 0.75f,time*2);
            matrices.popPose();

            matrices.pushPose();

            matrices.translate(0, -0.20, 0);
//            float centerRing = 33 - (time + 30) % 67;
//            if (centerRing >= 0) {
//                matrices.translate(0, centerRing / 100, 0);
//            } else {
//                matrices.translate(0, -centerRing / 100, 0);
//            }
            matrices.translate(0,Math.sin(time/20 + Math.PI/2f)*0.15,0);
            drawRing(partialTicks, matrices, buffer, light, light2, 0.525f,time*2+90);
            matrices.popPose();

            matrices.pushPose();

            matrices.translate(0, -0.20, 0);
//            float smallRing = 33 - (time + 45) % 67;
//            if (smallRing >= 0) {
//                matrices.translate(0, smallRing / 100, 0);
//            } else {
//                matrices.translate(0, -smallRing / 100, 0);
//            }
            matrices.translate(0,Math.sin(time/20 + Math.PI)*0.15,0);
            drawRing(partialTicks, matrices, buffer, light, light2, 0.30f,time*2+270);
            matrices.popPose();
            matrices.pushPose();

            VertexConsumer vertex = buffer.getBuffer(RenderType.text(fancyRing));
            matrices.translate(0.5,0.001,0.5);
            matrices.mulPose(Vector3f.YP.rotationDegrees(time % 360));
            PoseStack.Pose entry = matrices.last();
            vertex.vertex(entry.pose(),-1.25F,0,-1.25F).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F,0,-1.25F).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F,0, 1.25F).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(),-1.25F,0, 1.25F).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(entry.pose(),-1.25F,0, 1.25F).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F,0, 1.25F).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F,0,-1.25F).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(),-1.25F,0,-1.25F).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();


            matrices.mulPose(Vector3f.YP.rotationDegrees(-(time % 360)*2));
            vertex.vertex(entry.pose(),-2.8F,0,-2.8F).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F,0,-2.8F).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F,0, 2.8F).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(),-2.8F,0, 2.8F).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(entry.pose(),-2.8F,0, 2.8F).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F,0, 2.8F).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F,0,-2.8F).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(),-2.8F,0,-2.8F).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            matrices.popPose();

        }
        matrices.pushPose();

        if (!tile.getItem(tile.outputSlot()).isEmpty()) {
            matrices.translate(0.5, 0.5 + Math.sin(time/20)*0.05, 0.5);
            matrices.mulPose(Vector3f.YP.rotationDegrees((time % 360) * 2f));
            Minecraft.getInstance().getItemRenderer().render(tile.getItem(tile.outputSlot()), ItemTransforms.TransformType.GROUND, true,
                    matrices, buffer, light, light2, Minecraft.getInstance().getItemRenderer().getModel(tile.getItem(tile.outputSlot()), null, null,0));
        }else{
            matrices.translate(0.5, 0.5 + Math.sin(time/20)*0.05, 0.5);
            matrices.mulPose(Vector3f.YP.rotationDegrees((time % 360) * 2f));
            Minecraft.getInstance().getItemRenderer().render(tile.getItem(tile.inputSlot()), ItemTransforms.TransformType.GROUND, true,
                    matrices, buffer, light, light2, Minecraft.getInstance().getItemRenderer().getModel(tile.getItem(tile.inputSlot()), null, null,0));
        }

        matrices.popPose();


        float razn = tile.INFUSING_TIME - tile.CURRENT_PROGRESS;
        if (tile.RECIPE_IN_PROGRESS && (razn <= 100)) {
            matrices.pushPose();
            List<ItemStack> stacks = tile.getItems();
            BlockPos[] offsets = Structures.infusingPoolsPositions(BlockPos.ZERO);

            float rotValue = (float)tile.getRotationValue().getValue() ;

            matrices.translate(0.5,0.5,0.5);
            matrices.mulPose(Vector3f.YN.rotationDegrees(rotValue * 360));
            for (int i = 0; i < 14; i++) {
                int iter = i;
                //kostyli nashe vse!
                if (i == 6) continue;
                if (i > 6) iter--;
                ItemStack item = stacks.get(i);
                if (!item.isEmpty()) {
                    BlockPos p = offsets[iter];
                    Vec3 v = new Vec3(p.getX(), p.getY(), p.getZ()).multiply(1 - rotValue, 1 - rotValue, 1 - rotValue);
                    matrices.pushPose();

                    matrices.translate(v.x, v.y, v.z);
                    RenderingTools.applyMovementMatrixRotations(matrices, v.normalize().reverse());
                    Minecraft.getInstance().getItemRenderer().render(item, ItemTransforms.TransformType.GROUND, true,
                            matrices, buffer, light, light2, Minecraft.getInstance().getItemRenderer().getModel(item, null, null, 0));
                    matrices.popPose();
                }
            }
            matrices.popPose();
        }
    }

    public void drawRing(float partialTicks,PoseStack stack,MultiBufferSource buffer,int light,int light2,float scaleFactor,float angle){
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(text));
        stack.translate(0.5,0.8,0.5);
        stack.mulPose(Vector3f.YP.rotationDegrees(angle));
        PoseStack.Pose entry = stack.last();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
    }
}
