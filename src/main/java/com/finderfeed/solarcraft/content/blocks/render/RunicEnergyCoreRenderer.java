package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.blocks.blockentities.RunicEnergyCoreTile;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Random;

public class RunicEnergyCoreRenderer extends AbstractRunicEnergyContainerRenderer<RunicEnergyCoreTile> {

    public static final ResourceLocation RING = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/re_core/ring_part.png");
    public static final ResourceLocation CORE = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/re_core/core.png");

    public RunicEnergyCoreRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RunicEnergyCoreTile tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        super.render(tile, pticks, matrices, buffer, light, overlay);

        if (tile.hasLevel() && tile.shouldFunction()) {
            float time = RenderingTools.getTime(tile.getLevel(),pticks);
            Random random = new Random(tile.getLevel().getGameTime() * 1231);
            VertexConsumer vertex = buffer.getBuffer(RenderType.lightning());
            matrices.pushPose();
            matrices.translate(0.5, 0.5, 0.5);
            //core
            matrices.pushPose();
            Quaternion cam = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            matrices.mulPose(cam);
            matrices.scale(3f,3f,3f);
            Matrix4f m = matrices.last().pose();

            float alpha = random.nextFloat() * 0.5f + 0.5f;
            float r = 0.4f;

            for (int i = 0; i <= 3;i++) {
                matrices.mulPose(Vector3f.ZP.rotationDegrees(i*90));
                vertex.vertex(m, 0, r, 0).color(1f, 1f, 0f, 0f).endVertex();
                vertex.vertex(m, r, r, 0).color(1f, 1f, 0f, 0).endVertex();
                vertex.vertex(m, r, 0, 0).color(1f, 1f, 0f, 0f).endVertex();
                vertex.vertex(m, 0, 0, 0).color(1f, 1f, 0f, alpha).endVertex();
            }


            matrices.popPose();

            //rings
            VertexConsumer c = buffer.getBuffer(RenderType.text(RING));
            time = time*5f;
            matrices.pushPose();

            matrices.mulPose(Vector3f.ZP.rotationDegrees(time));

            renderRing(matrices,c,2.25f);
            matrices.popPose();
            matrices.pushPose();
            matrices.mulPose(Vector3f.XP.rotationDegrees(time));

            renderRing(matrices,c,1.75f);
            matrices.popPose();
            matrices.popPose();
        }

    }

    private void renderRing(PoseStack matrices,VertexConsumer vertex,float rad){
        float angle = (float)(Math.PI/4);
        float hypo = rad / (float)Math.cos(Math.PI/8);
        float len = Mth.sqrt(hypo*hypo - rad*rad);
        float h = len * 0.15625f;
        Matrix4f m = matrices.last().pose();
        for (int i = 0;i <= 8;i++){
            float currentAngle = angle*i;

            float x2 = rad * (float)Math.cos(currentAngle) - (-len)*(float)Math.sin(currentAngle);
            float z2 = rad * (float)Math.sin(currentAngle) + (-len)*(float)Math.cos(currentAngle);
            float x1 = rad * (float)Math.cos(currentAngle) - len*(float)Math.sin(currentAngle);
            float z1 = rad * (float)Math.sin(currentAngle) + len*(float)Math.cos(currentAngle);


            vertex.vertex(m,x1,-h,z1).color(1,1,1,1f).uv(1,1).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(m,x1,h,z1).color(1,1,1,1f).uv(1,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(m,x2,h,z2).color(1,1,1,1f).uv(0,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(m,x2,-h,z2).color(1,1,1,1f).uv(0,1).uv2(LightTexture.FULL_BRIGHT).endVertex();

            vertex.vertex(m,x2,-h,z2).color(1,1,1,1f).uv(0,1).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(m,x2,h,z2).color(1,1,1,1f).uv(0,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(m,x1,h,z1).color(1,1,1,1f).uv(1,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(m,x1,-h,z1).color(1,1,1,1f).uv(1,1).uv2(LightTexture.FULL_BRIGHT).endVertex();



        }
    }

}
