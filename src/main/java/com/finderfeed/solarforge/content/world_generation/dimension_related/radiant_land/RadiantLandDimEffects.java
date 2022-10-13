package com.finderfeed.solarforge.content.world_generation.dimension_related.radiant_land;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.phys.Vec3;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RadiantLandDimEffects extends DimensionSpecialEffects {
    public RadiantLandDimEffects() {
        super(-500, true, SkyType.NONE, false,false);
    }

    @Nonnull
    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 vec, float fl) {
        return vec.multiply((double)(fl * 0.94F + 0.06F), (double)(fl * 0.94F + 0.06F), (double)(fl * 0.91F + 0.09F));
    }

    @Override
    public boolean isFoggyAt(int p_108874_, int p_108875_) {
        return false;
    }


    public static final RenderSky skyHandler = new RenderSky();

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        skyHandler.render(ticks,partialTick,poseStack,level,Minecraft.getInstance());
        return true;
    }

    @Nullable
    @Override
    public float[] getSunriseColor(float p_108872_, float p_108873_) {
        return super.getSunriseColor(p_108872_,p_108873_);
    }
}

class RenderSky{
    public static final ResourceLocation STARGAZE = new ResourceLocation("solarforge","textures/environment/test_stargaze.png");
    public static final ResourceLocation SUNGAZE = new ResourceLocation("solarforge","textures/environment/test_sungaze.png");
    public static final ResourceLocation BROKEN_SKY = new ResourceLocation("solarforge","textures/environment/broken_sky.png");
    public static final ResourceLocation MOON = new ResourceLocation("solarforge","textures/environment/moon.png");


    public void render(int ticks, float partialTicks, PoseStack matrixStack, ClientLevel world, Minecraft mc) {
        Tesselator tes = Tesselator.getInstance();
        BufferBuilder builder = tes.getBuilder();
        //RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        //0.25 - start of night
        //0.75 - end of night
        float timeOfDay = world.getTimeOfDay(partialTicks);

        RenderSystem.enableTexture();
        if (!ClientHelpers.isIsRadiantLandCleaned()) {
            matrixStack.pushPose();
            matrixStack.scale(3, 1, 3);

            ClientHelpers.bindText(BROKEN_SKY);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            if ((timeOfDay >= 0.25) && timeOfDay < 0.5) {

                renderBrokenSky(matrixStack, builder, (timeOfDay - 0.25f) * 4f);
            } else if ((timeOfDay >= 0.5) && (timeOfDay < 0.75)) {
                renderBrokenSky(matrixStack, builder, (0.75f - timeOfDay) * 4f);
            }

            tes.end();
            matrixStack.popPose();
        }
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(timeOfDay * 360.0F));
        Matrix4f mat = matrixStack.last().pose();
        ClientHelpers.bindText(ClientHelpers.isIsRadiantLandCleaned() ? MOON : STARGAZE);



        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.vertex(mat,-25,-80,25).uv(0,1).endVertex();
        builder.vertex(mat,25,-80,25).uv(1,1).endVertex();
        builder.vertex(mat,25,-80,-25).uv(1,0).endVertex();
        builder.vertex(mat,-25,-80,-25).uv(0,0).endVertex();
        tes.end();

        ClientHelpers.bindText(SUNGAZE);

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.vertex(mat,-25,80,-25).uv(0,0).endVertex();
        builder.vertex(mat,25,80,-25).uv(1,0).endVertex();
        builder.vertex(mat,25,80,25).uv(1,1).endVertex();
        builder.vertex(mat,-25,80,25).uv(0,1).endVertex();
        tes.end();
        matrixStack.popPose();
        RenderSystem.disableTexture();
        //RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    private void renderBrokenSky(PoseStack matrixStack,BufferBuilder bufferbuilder,float opacityFactor){
        float mod = 0.8f;
        for(int i = 0; i < 4; ++i) {
            matrixStack.pushPose();
            if (i == 0) {
                matrixStack.scale(1,2,1);
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

            }

            if (i == 1) {
                matrixStack.scale(1,2,1);
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            }

            if (i == 2) {
                matrixStack.scale(1,2,1);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }

            if (i == 3) {
                matrixStack.scale(1,2,1);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = matrixStack.last().pose();

            bufferbuilder.vertex(matrix4f,-100,-100,-100).uv(0.0F, 0.0F).color(mod, mod, mod, opacityFactor).endVertex();
            bufferbuilder.vertex(matrix4f,-100,-100,100).uv(0.0F, 1.0F).color(mod, mod, mod, opacityFactor).endVertex();
            bufferbuilder.vertex(matrix4f,100,-100,100).uv(1.0F, 1.0F).color(mod, mod, mod, opacityFactor).endVertex();
            bufferbuilder.vertex(matrix4f,100,-100,-100).uv(1.0F, 0.0F).color(mod, mod, mod, opacityFactor).endVertex();
            matrixStack.popPose();
        }

        matrixStack.pushPose();
        Matrix4f matrix4f = matrixStack.last().pose();
        matrixStack.scale(1,2,1);
        bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(mod, mod, mod, opacityFactor).endVertex();
        bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(mod, mod, mod, opacityFactor).endVertex();
        bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(mod, mod, mod, opacityFactor).endVertex();
        bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(1.0F, 0.0F).color(mod, mod, mod, opacityFactor).endVertex();

        matrixStack.mulPose(Vector3f.XN.rotationDegrees(180));
        bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(mod, mod, mod, opacityFactor).endVertex();
        bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(mod, mod, mod, opacityFactor).endVertex();
        bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(mod, mod, mod, opacityFactor).endVertex();
        bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(1.0F, 0.0F).color(mod, mod, mod, opacityFactor).endVertex();
        matrixStack.popPose();



    }
}
