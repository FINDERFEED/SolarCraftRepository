package com.finderfeed.solarforge.magic.blocks.render;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.blocks.blockentities.RadiantPortalTile;
import com.finderfeed.solarforge.client.rendering.rendertypes.RadiantPortalRendertype;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RadiantPortalTileRender implements BlockEntityRenderer<RadiantPortalTile> {


    public static final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/radiant_portal.png");


    public RadiantPortalTileRender(BlockEntityRendererProvider.Context ctx){

    }

    @Override
    public void render(RadiantPortalTile tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5f,0.6f,0.5f);
        matrices.scale(1.35f,1.35f,1.35f);


        Matrix4f mat = matrices.last().pose();
        float time = (tile.getLevel().getGameTime() +partialTicks)/10;



        RadiantPortalRendertype.WATER_SHADER.safeGetUniform("time").set(time);
        RadiantPortalRendertype.WATER_SHADER.safeGetUniform("modelview").set(mat);
        RadiantPortalRendertype.WATER_SHADER.safeGetUniform("sinModifier").set(0.04f);
        RadiantPortalRendertype.WATER_SHADER.safeGetUniform("intensity").set(30f);
        VertexConsumer vertex = buffer.getBuffer(RadiantPortalRendertype.textWithWaterShader(LOC));

        float r;
        float gr;
        float b;
        if (Helpers.isDay(tile.getLevel())){
            r = 1;
            gr = 1;
            b = 0;
        }else{
            r = 0.5f;
            gr = 0;
            b = 1f;
        }

        float mod = 0.05f;
        for (float i = -1;i < 1;i +=mod) {
            float uvValue = (i+1)*0.5f;
            for (float g = -1;g < 1;g +=mod) {
                float uvValueg = (g+1)*0.5f;
                float rast = (float)Math.sqrt(i*i + g*g);

                if (rast <= 1.05f) {
                    vertex.vertex( i, g, 0).color(r, gr, b, 1f).uv(uvValue, uvValueg).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                    vertex.vertex( i, g + mod, 0).color(r, gr, b, 1f).uv(uvValue, uvValueg + mod * 0.5f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                    vertex.vertex( i + mod, g + mod, 0).color(r, gr, b, 1f).uv(uvValue + mod * 0.5f, uvValueg + mod * 0.5f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                    vertex.vertex( i + mod, g, 0).color(r, gr, b, 1f).uv(uvValue + mod * 0.5f, uvValueg).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

                    vertex.vertex( i + mod, g, 0).color(r, gr, b, 1f).uv(uvValue + mod * 0.5f, uvValueg).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                    vertex.vertex( i + mod, g + mod, 0).color(r, gr, b, 1f).uv(uvValue + mod * 0.5f, uvValueg + mod * 0.5f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                    vertex.vertex( i, g + mod, 0).color(r, gr, b, 1f).uv(uvValue, uvValueg + mod * 0.5f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                    vertex.vertex( i, g, 0).color(r, gr, b, 1f).uv(uvValue, uvValueg).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                }
            }

        }
        matrices.popPose();
    }
}
