package com.finderfeed.solarforge.for_future_library.helpers;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.client.rendering.rendertypes.RadiantPortalRendertype;
import com.finderfeed.solarforge.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.*;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.Window;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;

import com.mojang.math.Vector3f;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.data.ModelDataMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;




public class RenderingTools {

    public static final ResourceLocation TEST = new ResourceLocation("solarforge","textures/gui/solar_furnace_gui.png");
    public static final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/ray_into_skyy.png");
    public static final ResourceLocation SHADERED_RAY = new ResourceLocation("solarforge","textures/misc/shadered_ray.png");
    public static final ResourceLocation HP_BAR = new ResourceLocation("solarforge","textures/misc/hp_bar.png"); //0.875

    public static void addActivePostShader(UniformPlusPlus uniformPlusPlus,PostChainPlusUltra shader){
        RenderEventsHandler.ACTIVE_SHADERS.put(uniformPlusPlus,shader);
    }

    public static void renderTest(RenderGameOverlayEvent.Pre event,int tick){
        PoseStack stack = event.getMatrixStack();
        float partialTicks = event.getPartialTicks();

        Window window = event.getWindow();

        int width = window.getWidth();
        int height = window.getHeight();
        stack.pushPose();
        double scale = window.getGuiScale();
        stack.translate(width/4/scale*2,height/2/scale,0);
        stack.mulPose(Vector3f.ZP.rotationDegrees((tick + partialTicks)%360));

        Minecraft.getInstance().getTextureManager().bindForSetup(TEST);
        GuiComponent.blit(stack,-64,-64,0,0,128,128,128,128);

        stack.popPose();
        

    }

    /**
    *   Renders ray like in solar forge
     */
    public static void renderRay(PoseStack stack, MultiBufferSource buffer, float mod, float height, Direction direction,boolean rotate,float rotationModifier,float partialTicks){
        stack.pushPose();

        stack.translate(0.5,0.5,0.5);
        if (direction.equals(Direction.DOWN)){
            stack.mulPose(Vector3f.XN.rotationDegrees(180));
        }else if(direction.equals(Direction.NORTH)){
            stack.mulPose(Vector3f.YP.rotationDegrees(90));
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
        }else if(direction.equals(Direction.SOUTH)){
            stack.mulPose(Vector3f.YP.rotationDegrees(270));
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
        }else if(direction.equals(Direction.EAST)){

            stack.mulPose(Vector3f.XP.rotationDegrees(90));
        }else if(direction.equals(Direction.WEST)){
            stack.mulPose(Vector3f.YP.rotationDegrees(180));
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
        }

        if (rotate){
            stack.mulPose(Vector3f.YP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
        }

        VertexConsumer vertex = buffer.getBuffer(RenderType.text(RAY));
        Matrix4f matrix = stack.last().pose();
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //2
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //3
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //4
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        stack.popPose();
    }







    public static void renderHandManually(PoseStack matrixStack,float partialTicks){
        boolean render = Minecraft.getInstance().gameRenderer.renderHand;
        if (render){
            RenderSystem.clear(256, Minecraft.ON_OSX);
            Minecraft.getInstance().gameRenderer.renderItemInHand(matrixStack,Minecraft.getInstance().gameRenderer.getMainCamera(),partialTicks);
        }
    }


    private static ResourceLocation runeEnergyOverlay = new ResourceLocation("solarforge","textures/misc/runic_energy_bar.png");
    public static void renderRuneEnergyOverlay(PoseStack stack, int x, int y, RunicEnergy.Type type){
        stack.pushPose();
        stack.translate(x,y,0);
        stack.scale(0.7f,0.7f,0.7f);

        Player playerEntity = ClientHelpers.getClientPlayer();
        ClientHelpers.bindText(runeEnergyOverlay);
        GuiComponent.blit(stack,0,0,0,0,10,60,20,60);
        float currentEnergy = RunicEnergy.getEnergy(playerEntity,type);
//        float maxEnergy = playerEntity.getPersistentData().getFloat(RunicEnergy.MAX_ENERGY_TAG); //TODO:Update max energy on client
        int tex = Math.round(50*(currentEnergy/10000));

        stack.mulPose(Vector3f.ZN.rotationDegrees(180));
        stack.translate(-10,-55,0);
        GuiComponent.blit(stack,0,0,10,0,10,tex,20,60);
        stack.popPose();
        stack.pushPose();
        stack.translate(x,y,0);

        stack.scale(0.7f,0.7f,0.7f);

        ClientHelpers.bindText(new ResourceLocation("solarforge", "textures/misc/tile_energy_pylon_" + type.id + ".png"));
        GuiComponent.blit(stack,-3,63,0,0,16,16,16,16);
        stack.popPose();
    }
    /**
     *
     * @param p_83972_ Width
     * @param p_83973_ Height
     * @param p_83974_ Disable blend
     * @param framebuffer  Framebuffer(RenderTarget) to blit.
     */
    @Deprecated
    public static void blitFramebufferToScreen(int p_83972_, int p_83973_, boolean p_83974_, RenderTarget framebuffer) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._depthMask(false);
        GlStateManager._viewport(0, 0, p_83972_, p_83973_);
        if (p_83974_) {
            GlStateManager._disableBlend();
        }


        Matrix4f matrix4f = Matrix4f.orthographic((float)p_83972_, (float)(-p_83973_), 1000.0F, 3000.0F);
        RenderSystem.setProjectionMatrix(matrix4f);

        float f = (float)p_83972_;
        float f1 = (float)p_83973_;
        float f2 = (float)framebuffer.viewWidth / (float)framebuffer.width;
        float f3 = (float)framebuffer.viewHeight / (float)framebuffer.height;
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, (double)f1, 0.0D).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex((double)f, (double)f1, 0.0D).uv(f2, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex((double)f, 0.0D, 0.0D).uv(f2, f3).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, f3).color(255, 255, 255, 255).endVertex();
        bufferbuilder.end();
        BufferUploader._endInternal(bufferbuilder);

        GlStateManager._depthMask(true);
        GlStateManager._colorMask(true, true, true, true);
    }

    public static void drawLine(PoseStack stack,int x1,int y1,int x2,int y2,int red,int green,int blue){
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager._disableTexture();
        GlStateManager._depthMask(false);
        GlStateManager._disableCull();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        Tesselator var4 = RenderSystem.renderThreadTesselator();
        BufferBuilder var5 = var4.getBuilder();
        RenderSystem.lineWidth(2.0F);
        var5.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        Vector3d vector3f = new Vector3d(x2-x1,y2-y1,0);
        Vector3d vector3f2 = new Vector3d(x1-x2,y1-y2,0);
        var5.vertex(x1, y1, 0.0D).color(red,green,blue, 255).normal((float)vector3f.x, (float)vector3f.y, 0.0F).endVertex();
        var5.vertex((double)x2, y2, 0.0D).color(red, green, blue, 255).normal((float)vector3f2.x, (float)vector3f2.y, 0.0F).endVertex();
        var4.end();
        GlStateManager._enableCull();
        GlStateManager._depthMask(true);
        GlStateManager._enableTexture();
    }


    public static void renderScaledGuiItem(ItemStack p_115128_, int p_115129_, int p_115130_,float scale) {
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)p_115129_, (double)p_115130_, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
        posestack.translate(8.0D*scale, 8.0D*scale, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F*scale, 16.0F*scale, 16.0F*scale);

        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();

        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        BakedModel p_115131_ = Minecraft.getInstance().getItemRenderer().getModel(p_115128_,null,null,0);
        boolean flag = !p_115131_.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        render(p_115128_, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, p_115131_,scale);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
    private static void render(ItemStack p_115144_, ItemTransforms.TransformType p_115145_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_,float scale) {
        if (!p_115144_.isEmpty()) {
            p_115147_.pushPose();
            boolean flag = p_115145_ == ItemTransforms.TransformType.GUI || p_115145_ == ItemTransforms.TransformType.GROUND || p_115145_ == ItemTransforms.TransformType.FIXED;


            p_115151_ = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(p_115147_, p_115151_, p_115145_, p_115146_);
            p_115147_.translate(-0.5D, -0.5D, -0.5D);

            if (!p_115151_.isCustomRenderer() && (!p_115144_.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (p_115145_ != ItemTransforms.TransformType.GUI && !p_115145_.firstPerson() && p_115144_.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)p_115144_.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                if (p_115151_.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(Minecraft.getInstance().getItemRenderer(), p_115151_, p_115144_, p_115147_, p_115148_, p_115149_, p_115150_, flag1); }
                else {
                    RenderType rendertype = ItemBlockRenderTypes.getRenderType(p_115144_, flag1);
                    VertexConsumer vertexconsumer;
                    if (p_115144_.is(Items.COMPASS) && p_115144_.hasFoil()) {
                        p_115147_.pushPose();
                        PoseStack.Pose posestack$pose = p_115147_.last();
                        if (p_115145_ == ItemTransforms.TransformType.GUI) {
                            posestack$pose.pose().multiply(0.5F);
                        } else if (p_115145_.firstPerson()) {
                            posestack$pose.pose().multiply(0.75F);
                        }

                        if (flag1) {
                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBufferDirect(p_115148_, rendertype, posestack$pose);
                        } else {
                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBuffer(p_115148_, rendertype, posestack$pose);
                        }

                        p_115147_.popPose();
                    } else if (flag1) {
                        vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBufferDirect(p_115148_, rendertype, true, p_115144_.hasFoil());
                    } else {
                        vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBuffer(p_115148_, rendertype, true, p_115144_.hasFoil());
                    }

                    Minecraft.getInstance().getItemRenderer().renderModelLists(p_115151_, p_115144_, p_115149_, p_115150_, p_115147_, vertexconsumer);
                }
            } else {
                net.minecraftforge.client.RenderProperties.get(p_115144_).getItemStackRenderer().renderByItem(p_115144_, p_115145_, p_115147_, p_115148_, p_115149_, p_115150_);
            }

            p_115147_.popPose();
        }
    }


    /**
     *
     * Renders ray like in solar forge but with the matrix translations
     * @param translations - realize translations, e.t.c. here.
     */

    public static void renderRay(PoseStack stack, MultiBufferSource buffer, float mod, float height, Consumer<PoseStack> translations, boolean rotate, float rotationModifier, float partialTicks){
        stack.pushPose();

        stack.translate(0.5,0.5,0.5);
        translations.accept(stack);
        if (rotate){
            stack.mulPose(Vector3f.YP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
        }

        VertexConsumer vertex = buffer.getBuffer(RenderType.text(RAY));
        Matrix4f matrix = stack.last().pose();
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //2
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //3
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //4
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, 0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, height, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        stack.popPose();
    }



    public static void renderObjModel(ResourceLocation location,PoseStack matrices,MultiBufferSource buffer,int light,int overlay, @Deprecated Consumer<PoseStack> transforms){
        List<BakedQuad> list = Minecraft.getInstance().getModelManager().getModel(location)
                .getQuads(null, null, new Random(), new ModelDataMap.Builder().build());

        VertexConsumer cons = buffer.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));
        matrices.pushPose();
        transforms.accept(matrices);
        for (BakedQuad a : list) {
            cons.putBulkData(matrices.last(), a, 1f, 1f, 1f, light, overlay);
        }
        matrices.popPose();
    }

    public static void renderShaderedRay(PoseStack stack, MultiBufferSource buffer, float mod, float height, Consumer<PoseStack> translations, boolean rotate, float rotationModifier, float partialTicks){
        RenderSystem.depthMask(false);
        stack.pushPose();

        stack.translate(0.5,0.5,0.5);
        translations.accept(stack);
        if (rotate){
            stack.mulPose(Vector3f.YP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
        }
        Matrix4f matrix = stack.last().pose();


        RadiantPortalRendertype.RAY_SHADER.safeGetUniform("modelview").set(matrix);
        RadiantPortalRendertype.RAY_SHADER.safeGetUniform("modifier").set(3f);
        RadiantPortalRendertype.RAY_SHADER.safeGetUniform("heightLimit").set(200f);
        float modifier = 0.4f;
        VertexConsumer vertex = buffer.getBuffer(RadiantPortalRendertype.textWithRayShader(SHADERED_RAY));

        for (float i = 0;i < height;i+=modifier) {
            renderTube(vertex,mod,24,0.03f,i,modifier,matrix);
        }
        stack.popPose();
        RenderSystem.depthMask(true);
    }


    private static void renderTube(VertexConsumer vertex, float mod,int acurarry,double radius,double height,float secondHeight,Matrix4f mat){
        float angle = 360f/acurarry;
        float time  = (Minecraft.getInstance().level.getTimeOfDay(Minecraft.getInstance().getDeltaFrameTime())*2500)%2;


        for (int i = 0; i < acurarry;i++){
            double[] first = FinderfeedMathHelper.polarToCartesian(radius,Math.toRadians(i*angle));
            double[] second = FinderfeedMathHelper.polarToCartesian(radius,Math.toRadians(i*angle+angle));
            vertex.vertex((float)first[0],(float)height,(float)first[1]).color(255, 255, 255, 255).uv(1, 0+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex((float)first[0],(float)height+secondHeight,(float)first[1]).color(255, 255, 255, 255).uv(1, 1+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex((float)second[0],(float)height+secondHeight,(float)second[1]).color(255, 255, 255, 255).uv(0, 1+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex((float)second[0],(float)height,(float)second[1]).color(255, 255, 255, 255).uv(0, 0+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex((float)second[0],(float)height,(float)second[1]).color(255, 255, 255, 255).uv(0, 0+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex((float)second[0],(float)height+secondHeight,(float)second[1]).color(255, 255, 255, 255).uv(0, 1+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex((float)first[0],(float)height+secondHeight,(float)first[1]).color(255, 255, 255, 255).uv(1, 1+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex((float)first[0],(float)height,(float)first[1]).color(255, 255, 255, 255).uv(1, 0+time).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        }


    }

    //0.875
    public static void renderHpBar(PoseStack matrices,MultiBufferSource src,float percentage){
        matrices.pushPose();
        VertexConsumer vertex = src.getBuffer(RenderType.text(HP_BAR));
        Matrix4f mat = matrices.last().pose();


        basicVertex(mat,vertex,-2,1,0,0,0.5f);
        basicVertex(mat,vertex,2,1,0,1,0.5f);
        basicVertex(mat,vertex,2,0,0,1,0f);
        basicVertex(mat,vertex,-2,0,0,0f,0f);


        int red = Math.round((1-percentage)*255);
        int green = Math.round(percentage*255);

        matrices.translate(-1.75,0,0);
        coloredBasicVertex(mat,vertex,0,1,0,0,1f,red,green,0,255);
        coloredBasicVertex(mat,vertex,3.5*percentage,1,0,0.875f*percentage,1f,red,green,0,255);
        coloredBasicVertex(mat,vertex,3.5*percentage,0,0,0.875f*percentage,0.5f,red,green,0,255);
        coloredBasicVertex(mat,vertex,0,0,0,0,0.5f,red,green,0,255);

        matrices.popPose();
    }



    public static void basicVertex(Matrix4f mat,VertexConsumer vertex,double x,double y,double z,float u, float v){
        vertex.vertex(mat,(float)x,(float)y,(float)z).color(255, 255, 255, 255).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
    }

    public static void basicVertexNoOverlay(Matrix4f mat,VertexConsumer vertex,double x,double y,double z,float u, float v){
        vertex.vertex(mat,(float)x,(float)y,(float)z).uv(u, v).color(255, 255, 255, 255).uv2(15728880).endVertex();
    }

    public static void coloredBasicVertex(Matrix4f mat,VertexConsumer vertex,double x,double y,double z,float u, float v,int red,int green,int blue,int alpha){
        vertex.vertex(mat,(float)x,(float)y,(float)z).color(red, green, blue, alpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
    }

    public static void coloredBasicVertexNoOverlay(Matrix4f mat,VertexConsumer vertex,double x,double y,double z,float u, float v,int red,int green,int blue,int alpha){
        vertex.vertex(mat,(float)x,(float)y,(float)z).uv(u, v).color(red, green, blue, alpha).uv2(15728880).endVertex();
    }

    public static List<String> splitString(String main,int maxlen){
        List<String> returnable = new ArrayList<>();
        StringBuilder builder = new StringBuilder(main);
        while (true){
            try{
                if (builder.length() > maxlen){
                    String sub = builder.substring(0,maxlen+1);
                    if (sub.charAt(sub.length()-1) != ' '){
                        int index = findNearest_Index(sub,sub.length()-1);
                        if (index != -1){
                            returnable.add(builder.substring(0,index+1));
                            builder.delete(0,index+1);
                        }else{
                            returnable.add(sub);
                            builder.delete(0,maxlen+1);
                        }
                    }else{
                        returnable.add(sub);
                        builder.delete(0,maxlen+1);
                    }
                }else {
                    returnable.add(builder.toString());
                    break;
                }
            }catch (IndexOutOfBoundsException e){
                SolarForge.LOGGER.log(org.apache.logging.log4j.Level.ERROR, "Exception caught " + RenderingTools.class.toString() + " method splitString()");
                break;
            }
        }

        return deleteStartingProbelsSmbdyTeachHimEnglish(returnable);
    }


    private static List<String> deleteStartingProbelsSmbdyTeachHimEnglish(List<String> strings){
        List<String> toReturn = new ArrayList<>();
        for (int i = 0;i < strings.size();i++){
            StringBuilder builder = new StringBuilder(strings.get(i));
            while (true){
                if (builder.charAt(0) == ' '){
                    builder.delete(0,1);
                }else{
                    break;
                }
            }
            toReturn.add(builder.toString());
        }
        return toReturn;
    }

    private static int findNearest_Index(String origstring,int end){
        for (int i = end;i > 0;i--){
            if (origstring.charAt(i) == ' '){
                return i;
            }
        }
        return -1;
    }

    /**
        Render the upper side of the texture to top.
     */
    public static void applyMovementMatrixRotations(PoseStack matrices, Vec3 speed){
        double angleY = Math.toDegrees(Math.atan2(speed.x,speed.z));
        double angleX = Math.toDegrees(Math.atan2(Math.sqrt(speed.x*speed.x + speed.z*speed.z),speed.y));
        matrices.mulPose(Vector3f.YP.rotationDegrees((float)angleY));
        matrices.mulPose(Vector3f.XP.rotationDegrees((float)angleX));
    }

    public static float getTime(Level level,float pticks){
        return level.getGameTime() + pticks;
    }


    public static class DragonEffect {
        private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0D) / 2.0D);

        public static void render(PoseStack matrices, double intensity, MultiBufferSource buffer) {
            float f5 = ((float) intensity) / 200.0F;
            float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
            Random random = new Random(432L);
            VertexConsumer vertexconsumer2 = buffer.getBuffer(RenderType.lightning());
            matrices.pushPose();

            for (int i = 0; (float) i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
                matrices.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                matrices.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                matrices.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
                matrices.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                matrices.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                matrices.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
                float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
                float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
                Matrix4f matrix4f = matrices.last().pose();
                int j = (int) (255.0F * (1.0F - f7));
                vertex01(vertexconsumer2, matrix4f, j);
                vertex2(vertexconsumer2, matrix4f, f3, f4);
                vertex3(vertexconsumer2, matrix4f, f3, f4);
                vertex01(vertexconsumer2, matrix4f, j);
                vertex3(vertexconsumer2, matrix4f, f3, f4);
                vertex4(vertexconsumer2, matrix4f, f3, f4);
                vertex01(vertexconsumer2, matrix4f, j);
                vertex4(vertexconsumer2, matrix4f, f3, f4);
                vertex2(vertexconsumer2, matrix4f, f3, f4);
            }
            matrices.popPose();
        }
        private static void vertex01(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
            p_114220_.vertex(p_114221_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_114222_).endVertex();
        }

        private static void vertex2(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
            p_114215_.vertex(p_114216_, -HALF_SQRT_3 * p_114218_, p_114217_, -0.5F * p_114218_).color(255, 255, 0, 0).endVertex();
        }

        private static void vertex3(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
            p_114224_.vertex(p_114225_, HALF_SQRT_3 * p_114227_, p_114226_, -0.5F * p_114227_).color(255, 255, 0, 0).endVertex();
        }

        private static void vertex4(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
            p_114229_.vertex(p_114230_, 0.0F, p_114231_, 1.0F * p_114232_).color(255, 255, 0, 0).endVertex();
        }
    }

}
