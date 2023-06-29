package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.ClearingRitual;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Random;

public class ClearingRitualTileRenderer extends TileEntityRenderer<ClearingRitualMainTile> {

    public static final int RAY_ANIMATION_TICKS = 200;
    public static final int DIMENSION_CRACK_ANIMATION_TICKS = 100;

    private static final ResourceLocation RAY = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/solar_strike_ray.png");
    private static final ResourceLocation SKY_BREAK = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/dimension_sky_break.png");
    private static final ResourceLocation STAR = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/star.png");

    public ClearingRitualTileRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ClearingRitualMainTile tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        matrices.pushPose();
        if (tile.ritual.ritualOnline() ){

            if (ClearingRitual.MAX_TIME - tile.ritual.getCurrentTime() <= RAY_ANIMATION_TICKS){
                matrices.pushPose();
                int time = (RAY_ANIMATION_TICKS - DIMENSION_CRACK_ANIMATION_TICKS) - (FDMathHelper.clamp(DIMENSION_CRACK_ANIMATION_TICKS,ClearingRitual.MAX_TIME - tile.ritual.getCurrentTime(),RAY_ANIMATION_TICKS) - DIMENSION_CRACK_ANIMATION_TICKS);

                double height = 200f*((time + pticks)/(RAY_ANIMATION_TICKS - DIMENSION_CRACK_ANIMATION_TICKS));

                Vec3 camPos = Minecraft.getInstance().getCameraEntity().position();
                Vec3 tilepos = Helpers.getBlockCenter(tile.getBlockPos());
                Vec3 betweenHorizontal = camPos.subtract(tilepos).multiply(1,0,1).normalize();
                //ray
                VertexConsumer vertex = buffer.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(RAY));
                matrices.translate(0.5,0.5,0.5);
                matrices.pushPose();
                double angle = Math.atan2(betweenHorizontal.x,betweenHorizontal.z);
//                matrices.mulPose(Vector3f.YP.rotationDegrees((float)Math.toDegrees(angle)));
                matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),(float)Math.toDegrees(angle)));
                Matrix4f m = matrices.last().pose();
                vertex.vertex(m,0.25f,0,0).color(1f,1f,1f,1f).uv(0,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                vertex.vertex(m,0.25f,(float)height,0).color(1f,1f,1f,1f).uv(1,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                vertex.vertex(m,-0.25f,(float)height,0).color(1f,1f,1f,1f).uv(1,1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                vertex.vertex(m,-0.25f,0,0).color(1f,1f,1f,1f).uv(0,1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                matrices.popPose();

                matrices.pushPose();
                matrices.translate(0,height,0);
                matrices.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
                m = matrices.last().pose();
                vertex = buffer.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(STAR));
                float r = 2f;
                vertex.vertex(m,-r,r,0).color(1f,1f,0f,1f).uv(0, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                vertex.vertex(m,r,r,0).color(1f,1f,0f,1f).uv(1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                vertex.vertex(m,r,-r,0).color(1f,1f,0f,1f).uv(1, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                vertex.vertex(m,-r,-r,0).color(1f,1f,0f,1f).uv(0, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();

                matrices.popPose();
                if (ClearingRitual.MAX_TIME - tile.ritual.getCurrentTime() <= DIMENSION_CRACK_ANIMATION_TICKS){
                    int time2 = DIMENSION_CRACK_ANIMATION_TICKS - FDMathHelper.clamp(0,ClearingRitual.MAX_TIME - tile.ritual.getCurrentTime(),DIMENSION_CRACK_ANIMATION_TICKS);
                    double alpha = time2/(float)DIMENSION_CRACK_ANIMATION_TICKS;
                    vertex = buffer.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(SKY_BREAK));
                    Matrix4f m2 = matrices.last().pose();
                    float r3 = 100f;
                    vertex.vertex(m2,-r3,200,-r3).color(1f,0.8f,0.15f,(float)alpha).uv(0,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                    vertex.vertex(m2,r3,200,-r3).color(1f,0.8f,0.15f,(float)alpha).uv(1,0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                    vertex.vertex(m2,r3,200,r3).color(1f,0.8f,0.15f,(float)alpha).uv(1,1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                    vertex.vertex(m2,-r3,200,r3).color(1f,0.8f,0.15f,(float)alpha).uv(0,1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                }
                matrices.popPose();
            }

            List<Vec3> positions = ClearingRitual.crystalOffsets();
            Random random = new Random(tile.getLevel().getGameTime());
            for (int i = 0; i < positions.size()-1;i++) {
                RenderingTools.Lightning2DRenderer.renderLightning(matrices, buffer, 3, 0.5f, 0.25f,
                        positions.get(i),positions.get(i+1),random,1f,1f,0f);
            }
            RenderingTools.Lightning2DRenderer.renderLightning(matrices, buffer, 3, 0.5f, 0.25f,
                    positions.get(positions.size()-1),positions.get(0),random,1f,1f,0f);
        }
        matrices.popPose();
        this.renderModel(matrices,buffer,LightTexture.FULL_BRIGHT,overlay,pticks,tile);

    }


    private void renderModel(PoseStack matrices,MultiBufferSource src,int light,int overlay,float pticks,ClearingRitualMainTile tile){
        matrices.pushPose();
        float worldtime = tile.getLevel().getDayTime() % 24000;
        float time = RenderingTools.getTime(Minecraft.getInstance().level,pticks);

        if (!(worldtime >= 16500 && worldtime <= 19500)){
            time = 0;
        }

        float rotation = time % 360 * (tile.ritual.ritualOnline() ? 8 : 1);

        matrices.translate(0.5,0,0.5);
        matrices.scale(0.5f,0.5f,0.5f);
        matrices.pushPose();
        RenderingTools.renderEntityObjModel(OBJModels.CLEARING_RITUAL_MAIN_BLOCK_LOWER,matrices,src,1,1,1,light,overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0,2,0);
//        matrices.mulPose(Vector3f.YP.rotationDegrees(rotation));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),rotation));
        RenderingTools.renderEntityObjModel(OBJModels.CLEARING_RITUAL_MAIN_BLOCK_PETALS,matrices,src,1,1,1,light,overlay);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0,4,0);
        matrices.scale(0.7f,0.7f,0.7f);
//        matrices.mulPose(Vector3f.YP.rotationDegrees(-rotation));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),-rotation));
        RenderingTools.renderEntityObjModel(OBJModels.CLEARING_RITUAL_MAIN_BLOCK_TOP,matrices,src,1,1,1,light,overlay);
        matrices.popPose();
        matrices.popPose();

    }

    @Override
    public boolean shouldRenderOffScreen(ClearingRitualMainTile tile) {
        return true;
    }

    @Override
    public boolean shouldRender(ClearingRitualMainTile tile, Vec3 idk) {
        return true;
    }
}
