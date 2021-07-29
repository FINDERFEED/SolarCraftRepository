package com.finderfeed.solarforge;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.*;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.Window;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;

import com.mojang.math.Vector3f;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.List;




public class RenderingTools {

    public static final ResourceLocation TEST = new ResourceLocation("solarforge","textures/gui/solar_furnace_gui.png");
    public static final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/ray_into_skyy.png");

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
}
