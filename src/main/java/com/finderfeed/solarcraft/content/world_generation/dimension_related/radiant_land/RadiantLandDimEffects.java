package com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.CoreShaders;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.helpers.ShapesRenderer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;


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
        return super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
    }

//    @Override
//    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
//        skyHandler.render(ticks,partialTick,poseStack,level,Minecraft.getInstance());
//        return true;
//    }

    @Nullable
    @Override
    public float[] getSunriseColor(float p_108872_, float p_108873_) {
        return super.getSunriseColor(p_108872_,p_108873_);
    }
}

class RenderSky{
    public static final ResourceLocation STARGAZE = new ResourceLocation(SolarCraft.MOD_ID,"textures/environment/test_stargaze.png");
    public static final ResourceLocation SUNGAZE = new ResourceLocation(SolarCraft.MOD_ID,"textures/environment/test_sungaze.png");
    public static final ResourceLocation BROKEN_SKY = new ResourceLocation(SolarCraft.MOD_ID,"textures/environment/broken_sky.png");
    public static final ResourceLocation BROKEN_SKY_1 = new ResourceLocation(SolarCraft.MOD_ID,"textures/environment/broken_sky_1.png");
    public static final ResourceLocation BROKEN_SKY_2 = new ResourceLocation(SolarCraft.MOD_ID,"textures/environment/broken_sky_2.png");
    public static final ResourceLocation MOON = new ResourceLocation(SolarCraft.MOD_ID,"textures/environment/moon.png");


    public void render(int ticks, float partialTicks, PoseStack matrixStack, ClientLevel world, Minecraft mc) {
        Tesselator tes = Tesselator.getInstance();
        BufferBuilder builder = tes.getBuilder();
        //RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        //0.25 - start of night
        //0.75 - end of night
        float timeOfDay = world.getTimeOfDay(partialTicks);

        if (!ClientHelpers.isIsRadiantLandCleaned()) {
            matrixStack.pushPose();

            if ((timeOfDay >= 0.25) && timeOfDay < 0.5) {

                renderBrokenSky(matrixStack, builder, (timeOfDay - 0.25f) * 4f);
            } else if ((timeOfDay >= 0.5) && (timeOfDay < 0.75)) {
                renderBrokenSky(matrixStack, builder, (0.75f - timeOfDay) * 4f);
            }


            matrixStack.popPose();
        }
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        matrixStack.pushPose();

        matrixStack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),-90));
        matrixStack.mulPose(RenderingTools.rotationDegrees(RenderingTools.XP(),timeOfDay * 360.0F));
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

        RenderSystem.disableBlend();
    }

    private void renderBrokenSky(PoseStack matrixStack,BufferBuilder bufferbuilder,float opacityFactor){
        Level level = Minecraft.getInstance().level;
        RenderSystem.setShader(()->CoreShaders.SC_POSITION_COLOR_TEX_LIGHTMAP);
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
        opacityFactor *= 0.25f;
        float r = 120/255f;
        float g = 0/255f * 0.2f;
        float b = 230/255f;
        float time = level.getGameTime()/20f;
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        matrixStack.pushPose();
        ClientHelpers.bindText(BROKEN_SKY_1);
        matrixStack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),time));

        ShapesRenderer.renderSphere(ShapesRenderer.POSITION_COLOR_UV_LIGHTMAP,
                bufferbuilder,matrixStack,20,50,r,g,b,opacityFactor, LightTexture.FULL_BRIGHT);
        matrixStack.popPose();
        BufferUploader.drawWithShader(bufferbuilder.end());



        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        matrixStack.pushPose();
        ClientHelpers.bindText(BROKEN_SKY_2);
        matrixStack.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),time));




        ShapesRenderer.renderSphere(ShapesRenderer.POSITION_COLOR_UV_LIGHTMAP,
                bufferbuilder,matrixStack,20,51,r,g,b,opacityFactor, LightTexture.FULL_BRIGHT);
        matrixStack.popPose();
        BufferUploader.drawWithShader(bufferbuilder.end());

        RenderSystem.enableCull();
    }
}
