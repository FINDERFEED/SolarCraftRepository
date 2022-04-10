package com.finderfeed.solarforge.magic.blocks.render;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.blockentities.UlderaPylon;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class UlderaPylonRenderer extends TileEntityRenderer<UlderaPylon> {

    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/misc/uldera_turret.png");

    public UlderaPylonRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(UlderaPylon pylon, float pTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        if (pylon.getLevel() == null) return;
        matrices.pushPose();
        float time = RenderingTools.getTime(pylon.getLevel(),pTicks);
        VertexConsumer vertex = src.getBuffer(RenderType.text(LOC));
        matrices.translate(0.5,0.5 + Math.sin(time/10)*0.1f,0.5);



        Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        matrices.mulPose(quaternion);

        matrices.scale(1.5f,1.5f,1.5f);
        Matrix4f matrix = matrices.last().pose();
        vertex.vertex(matrix, -0.5f,0.5f,0).color(128, 0, 200, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,0.5f,0).color(128, 0, 200, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,-0.5f,0).color(128, 0, 200, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  -0.5f,-0.5f,0).color(128, 0, 200, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        matrices.popPose();

//        matrices.pushPose();
//        matrices.translate(0.5,0.5,0.5);
//        matrices.mulPose(quaternion);
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));
//        Matrix4f matrix2 = matrices.last().pose();
//        matrices.scale(1.5f,1.5f,1.5f);
//        vertex.vertex(matrix2, -0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(matrix2,  0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(matrix2,  0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(matrix2,  -0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//
//        matrices.popPose();
    }
}
