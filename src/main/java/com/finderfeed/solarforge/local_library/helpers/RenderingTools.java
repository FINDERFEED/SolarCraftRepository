package com.finderfeed.solarforge.local_library.helpers;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.screens.PositionBlockStateTileEntity;
import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.events.my_events.MyColorEvent;
import com.finderfeed.solarforge.events.my_events.PostColorEvent;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserScreen;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.client.rendering.rendertypes.RadiantPortalRendertype;
import com.finderfeed.solarforge.client.rendering.deprecated_shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.client.rendering.deprecated_shaders.post_chains.UniformPlusPlus;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.*;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3d;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;

import com.mojang.math.Vector3f;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.MinecraftForge;

import java.util.*;
import java.util.function.Consumer;




public class RenderingTools {

    public static final ResourceLocation TEST = new ResourceLocation("solarforge","textures/gui/solar_furnace_gui.png");
    public static final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/ray_into_skyy.png");
    public static final ResourceLocation SHADERED_RAY = new ResourceLocation("solarforge","textures/misc/shadered_ray.png");
    public static final ResourceLocation HP_BAR = new ResourceLocation("solarforge","textures/misc/hp_bar.png"); //0.875
    public static final ResourceLocation RUNIC_ENERGY_BARS_GUI = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/infuser_energy_gui.png");
    public static final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/runic_energy_bar.png");

    public static void drawBoundedText(PoseStack matrices,int posx,int posy,int bound,String s,int color){
        int iter = 0;
        for (String str : RenderingTools.splitString(s,bound)){
            Gui.drawString(matrices,Minecraft.getInstance().font,str,posx,posy + iter * 9,color);
            iter++;
        }
    }

    public static void drawBoundedTextObfuscated(PoseStack matrices,int posx,int posy,int bound,Component component,int color,int ticker){
        int iter = 0;
        int remainingOpenedSymbols = ticker;
        for (String str : RenderingTools.splitString(component.getString(),bound)){
            if (remainingOpenedSymbols >= str.length()){
                Gui.drawString(matrices,Minecraft.getInstance().font,str,posx,posy + iter * 9,color);
                remainingOpenedSymbols -= str.length();
            }else if (remainingOpenedSymbols != 0){
                Gui.drawString(matrices,Minecraft.getInstance().font,new TextComponent(str.substring(0,remainingOpenedSymbols)).withStyle(ChatFormatting.RESET)
                        .append(new TextComponent("a").withStyle(ChatFormatting.OBFUSCATED)),posx,posy + iter * 9,color);
                remainingOpenedSymbols = 0;
            }
            iter++;
        }
    }



    public static void addActivePostShader(UniformPlusPlus uniformPlusPlus,PostChainPlusUltra shader){
        if (ClientHelpers.isShadersEnabled()) {
            RenderEventsHandler.ACTIVE_SHADERS.put(uniformPlusPlus, shader);
        }
    }


    public static List<Component> renderRunicEnergyGui(PoseStack matrices, int x, int y, int mousex, int mousey, RunicEnergyContainer current, RunicEnergyCost simulate, float maximum){
        if (Minecraft.getInstance().screen == null) return List.of();
        ClientHelpers.bindText(RUNIC_ENERGY_BARS_GUI);
        Gui.blit(matrices, x, y, 0, 0, 77, 182, 77, 182);

        List<Component> tooltips = new ArrayList<>();

        if (simulate != null) {
            RenderSystem.enableBlend();
            renderEnergyBar(matrices, x + 12, y + 12, simulate.get(RunicEnergy.Type.ZETA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(matrices, x + 12 + 16, y + 12, simulate.get(RunicEnergy.Type.TERA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(matrices, x + 12 + 16*2, y + 12, simulate.get(RunicEnergy.Type.KELDA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(matrices, x + 12 + 16*3-1, y + 12, simulate.get(RunicEnergy.Type.GIRO), true,mousex,mousey,maximum,tooltips);

            renderEnergyBar(matrices, x + 12, y + 96, simulate.get(RunicEnergy.Type.ARDO), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(matrices, x + 12 + 16, y + 96, simulate.get(RunicEnergy.Type.FIRA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(matrices, x + 12 + 16*2, y + 96, simulate.get(RunicEnergy.Type.URBA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(matrices, x + 12 + 16*3-1, y + 96, simulate.get(RunicEnergy.Type.ULTIMA), true,mousex,mousey,maximum,tooltips);
            RenderSystem.disableBlend();
        }

        renderEnergyBar(matrices, x + 12, y + 12, current.get(RunicEnergy.Type.ZETA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(matrices, x + 12 + 16, y + 12, current.get(RunicEnergy.Type.TERA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(matrices, x + 12 + 16*2, y + 12, current.get(RunicEnergy.Type.KELDA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(matrices, x + 12 + 16*3-1, y + 12, current.get(RunicEnergy.Type.GIRO), false,mousex,mousey,maximum,tooltips);

        renderEnergyBar(matrices, x + 12, y + 96, current.get(RunicEnergy.Type.ARDO), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(matrices, x + 12 + 16, y + 96, current.get(RunicEnergy.Type.FIRA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(matrices, x + 12 + 16*2, y + 96, current.get(RunicEnergy.Type.URBA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(matrices, x + 12 + 16*3-1, y + 96, current.get(RunicEnergy.Type.ULTIMA), false,mousex,mousey,maximum,tooltips);

//        renderEnergyBar(matrices, x - 28 - 16 + 1 + 69, y + 61 +8, maximum, false,mousex,mousey,maximum,tooltips);
//        renderEnergyBar(matrices, x - 44 - 16 + 1 + 69, y + 61 +8, maximum, false,mousex,mousey,maximum,tooltips);
//        renderEnergyBar(matrices, x - 12 - 16 + 1 + 69, y + 145 +8,maximum, false,mousex,mousey,maximum,tooltips);
//        renderEnergyBar(matrices, x - 28 - 16 + 1 + 69, y + 145 +8,maximum, false,mousex,mousey,maximum,tooltips);
//        renderEnergyBar(matrices, x - 44 - 16 + 1 + 69, y + 145 +8,maximum, false,mousex,mousey,maximum,tooltips);
//        renderEnergyBar(matrices, x - 12 + 69 , y + 61 +8, maximum, false,mousex,mousey,maximum,tooltips);
//        renderEnergyBar(matrices, x - 12 + 69 , y + 145 +8, maximum, false,mousex,mousey,maximum,tooltips);
        return tooltips;
    }

    private static void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, float energyAmount, boolean simulate,int mousex,int mousey,float maxEnergy,List<Component> tooltips){
        matrices.pushPose();

//        int texturex = Math.round(energyAmount/maxEnergy*60);
//        matrices.translate(offsetx,offsety,0);
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(90));
//        if (!simulate) {
//            if (mousex > offsetx && mousex < offsetx + 6 && mousey > offsety-60 && mousey < offsety){
//                tooltips.add(new TextComponent(energyAmount + "/" + maxEnergy));
//            }
//            Minecraft.getInstance().screen.blit(matrices, 0, 0, 0, 0, texturex, 6);
//        }else{
//            InfuserScreen.blitm(matrices, 0, 0, 0, 0, texturex, 6,60,6);
//        }


        int k = 60;
        float energy = (energyAmount / maxEnergy) * k;
        if (!simulate){
            if (isMouseInBorders(mousex,mousey,offsetx,offsety,offsetx + 6,offsety + k)){
                tooltips.add(new TextComponent(energyAmount + "/" + maxEnergy));
            }
            Gui.fill(matrices,offsetx,offsety  + k,offsetx + 6,(int)(offsety + k - energy),0xffffff00);
        }else{
            Gui.fill(matrices,offsetx,offsety  + k,offsetx + 6,(int)(offsety + k - energy),0x90ffff00);
        }

        matrices.popPose();
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


    public static void drawLine(PoseStack stack,int x1,int y1,int x2,int y2,int red,int green,int blue){

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


    public static void renderScaledGuiItem(ItemStack stack, int x, int y,float scale,double zOffset) {
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)x, (double)y, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset + zOffset));
        posestack.translate(8.0D*scale, 8.0D*scale, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F*scale, 16.0F*scale, 16.0F*scale);

        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();

        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        BakedModel p_115131_ = Minecraft.getInstance().getItemRenderer().getModel(stack,null,null,0);
        boolean flag = !p_115131_.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        render(stack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, p_115131_,scale);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
    private static void render(ItemStack stack, ItemTransforms.TransformType transforms, boolean idk, PoseStack matrices, MultiBufferSource src, int light, int overlay, BakedModel model,float scale) {
        if (!stack.isEmpty()) {
            matrices.pushPose();
            boolean flag = transforms == ItemTransforms.TransformType.GUI || transforms == ItemTransforms.TransformType.GROUND || transforms == ItemTransforms.TransformType.FIXED;


            model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrices, model, transforms, idk);
            matrices.translate(-0.5D, -0.5D, -0.5D);

            if (!model.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (transforms != ItemTransforms.TransformType.GUI && !transforms.firstPerson() && stack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)stack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                if (model.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(Minecraft.getInstance().getItemRenderer(), model, stack, matrices, src, light, overlay, flag1); }
                else {
                    RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, flag1);
                    VertexConsumer vertexconsumer;
                    if (stack.is(Items.COMPASS) && stack.hasFoil()) {
                        matrices.pushPose();
                        PoseStack.Pose posestack$pose = matrices.last();
                        if (transforms == ItemTransforms.TransformType.GUI) {
                            posestack$pose.pose().multiply(0.5F);
                        } else if (transforms.firstPerson()) {
                            posestack$pose.pose().multiply(0.75F);
                        }

                        if (flag1) {
                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBufferDirect(src, rendertype, posestack$pose);
                        } else {
                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBuffer(src, rendertype, posestack$pose);
                        }

                        matrices.popPose();
                    } else if (flag1) {
                        vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBufferDirect(src, rendertype, true, stack.hasFoil());
                    } else {
                        vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBuffer(src, rendertype, true, stack.hasFoil());
                    }

                    Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, light, overlay, matrices, vertexconsumer);
                }
            } else {
                net.minecraftforge.client.RenderProperties.get(stack).getItemStackRenderer().renderByItem(stack, transforms, matrices, src, light, overlay);
            }

            matrices.popPose();
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
            double[] first = FDMathHelper.polarToCartesian(radius,Math.toRadians(i*angle));
            double[] second = FDMathHelper.polarToCartesian(radius,Math.toRadians(i*angle+angle));
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

    public static void renderStringObfuscated(PoseStack matrices,int x,int y,Component component,int ticker,int color){
        String s = component.getString();
        if (s.isEmpty()) return;
        if (ticker < s.length()) {
            Gui.drawString(matrices, Minecraft.getInstance().font, new TextComponent(component.getString().substring(0, ticker))
                    .withStyle(ChatFormatting.RESET).append("a").withStyle(ChatFormatting.OBFUSCATED), x, y, color);
        }else{
            Gui.drawString(matrices, Minecraft.getInstance().font, component.getString(), x, y, color);
        }
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
    public static void applyMovementMatrixRotations(PoseStack matrices, Vec3 vec){
        double angleY = Math.toDegrees(Math.atan2(vec.x,vec.z));
        double angleX = Math.toDegrees(Math.atan2(Math.sqrt(vec.x*vec.x + vec.z*vec.z),vec.y));
        matrices.mulPose(Vector3f.YP.rotationDegrees((float)angleY));
        matrices.mulPose(Vector3f.XP.rotationDegrees((float)angleX));
    }

    public static float getTime(Level level,float pticks){
        return level.getGameTime() + pticks;
    }


    public static boolean isMouseInBorders(int mx, int my, int x1, int y1, int x2, int y2){
        return (mx >= x1 && mx <= x2) && (my >= y1 && my <= y2);
    }


    public static void fill(PoseStack matrices,double x1,double y1,double x2,double y2,float r,float g,float b,float a){
        if (x1 > x2){
            double k = x1;
            x1 = x2;
            x2 = k;
        }
        if (y1 > y2){
            double k = y1;
            y1 = y2;
            y2 = k;
        }
        Matrix4f matrix4f = matrices.last().pose();
        BufferBuilder builder = Tesselator.getInstance().getBuilder();

        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);

        builder.vertex(matrix4f,(float)x1,(float)y1,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x1,(float)y2,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)y2,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)y1,0).color(r,g,b,a).endVertex();

        builder.end();
        BufferUploader.end(builder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
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

    //using my render tooltip thing because currently post events are removed. (copied from screen class and a bit modified with events)
    public static void renderTooltipInternal(PoseStack p_169384_, List<ClientTooltipComponent> p_169385_, int mousex, int mousey, CustomTooltip tooltip) {
        if (!p_169385_.isEmpty()) {

            int i = 0;
            int j = p_169385_.size() == 1 ? -2 : 0;

            for(ClientTooltipComponent clienttooltipcomponent : p_169385_) {
                int k = clienttooltipcomponent.getWidth(Minecraft.getInstance().font);
                if (k > i) {
                    i = k;
                }

                j += clienttooltipcomponent.getHeight();
            }

            int j2 = mousex + 12;
            int k2 = mousey - 12;
            if (j2 + i > Minecraft.getInstance().screen.width) {
                j2 -= 28 + i;
            }

            if (k2 + j + 6 > Minecraft.getInstance().screen.height) {
                k2 = Minecraft.getInstance().screen.height - j - 6;
            }

            p_169384_.pushPose();
            int l = -267386864;
            int i1 = 1347420415;
            int j1 = 1344798847;
            int k1 = 400;
            float f = Minecraft.getInstance().getItemRenderer().blitOffset;
            Minecraft.getInstance().getItemRenderer().blitOffset = 400.0F;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            Matrix4f matrix4f = p_169384_.last().pose();
            MyColorEvent colorEvent = new MyColorEvent(Items.AIR.getDefaultInstance(), p_169384_, j2, k2, Minecraft.getInstance().font,p_169385_,tooltip);
            MinecraftForge.EVENT_BUS.post(colorEvent);

            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 4, j2 + i + 3, k2 - 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundStart());
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 + j + 3, j2 + i + 3, k2 + j + 4, 400, colorEvent.getBackgroundEnd(), colorEvent.getBackgroundEnd());
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
            fillGradient(matrix4f, bufferbuilder, j2 - 4, k2 - 3, j2 - 3, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
            fillGradient(matrix4f, bufferbuilder, j2 + i + 3, k2 - 3, j2 + i + 4, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + j + 3 - 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderEnd());
            fillGradient(matrix4f, bufferbuilder, j2 + i + 2, k2 - 3 + 1, j2 + i + 3, k2 + j + 3 - 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderEnd());
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 - 3 + 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderStart());
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 + j + 2, j2 + i + 3, k2 + j + 3, 400, colorEvent.getBorderEnd(), colorEvent.getBorderEnd());
            RenderSystem.enableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            bufferbuilder.end();
            BufferUploader.end(bufferbuilder);
            RenderSystem.disableBlend();
            RenderSystem.enableTexture();



            MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            p_169384_.translate(0.0D, 0.0D, 400.0D);
            int l1 = k2;

            for(int i2 = 0; i2 < p_169385_.size(); ++i2) {
                ClientTooltipComponent clienttooltipcomponent1 = p_169385_.get(i2);
                clienttooltipcomponent1.renderText(Minecraft.getInstance().font, j2, l1, matrix4f, multibuffersource$buffersource);
                l1 += clienttooltipcomponent1.getHeight() + (i2 == 0 ? 2 : 0);
            }

            multibuffersource$buffersource.endBatch();



            p_169384_.popPose();

            l1 = k2;

            for(int l2 = 0; l2 < p_169385_.size(); ++l2) {
                ClientTooltipComponent clienttooltipcomponent2 = p_169385_.get(l2);
                clienttooltipcomponent2.renderImage(Minecraft.getInstance().font, j2, l1, p_169384_, Minecraft.getInstance().getItemRenderer(), 400);
                l1 += clienttooltipcomponent2.getHeight() + (l2 == 0 ? 2 : 0);
            }

            Minecraft.getInstance().getItemRenderer().blitOffset = f;
            PostColorEvent event = new PostColorEvent(p_169384_, j2, k2, Minecraft.getInstance().font,p_169385_,i,j,tooltip);
            MinecraftForge.EVENT_BUS.post(event);
        }


    }
    protected static void fillGradient(Matrix4f p_93124_, BufferBuilder p_93125_, int p_93126_, int p_93127_, int p_93128_, int p_93129_, int p_93130_, int p_93131_, int p_93132_) {
        float f = (float)(p_93131_ >> 24 & 255) / 255.0F;
        float f1 = (float)(p_93131_ >> 16 & 255) / 255.0F;
        float f2 = (float)(p_93131_ >> 8 & 255) / 255.0F;
        float f3 = (float)(p_93131_ & 255) / 255.0F;
        float f4 = (float)(p_93132_ >> 24 & 255) / 255.0F;
        float f5 = (float)(p_93132_ >> 16 & 255) / 255.0F;
        float f6 = (float)(p_93132_ >> 8 & 255) / 255.0F;
        float f7 = (float)(p_93132_ & 255) / 255.0F;
        p_93125_.vertex(p_93124_, (float)p_93128_, (float)p_93127_, (float)p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_93124_, (float)p_93126_, (float)p_93127_, (float)p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_93124_, (float)p_93126_, (float)p_93129_, (float)p_93130_).color(f5, f6, f7, f4).endVertex();
        p_93125_.vertex(p_93124_, (float)p_93128_, (float)p_93129_, (float)p_93130_).color(f5, f6, f7, f4).endVertex();
    }


    public static class StructureRenderer{
        public static List<PositionBlockStateTileEntity> prepareList(Multiblock m){

            List<PositionBlockStateTileEntity> toReturn = new ArrayList<>();
            String[][] struct = m.struct;
            double heightOffset = (float)struct.length/2;
            double xzoffset = (float)struct[0].length/2;
            for (int i = 0;i < struct.length;i++){
                String[] layer = struct[i];
                for (int g = 0;g < layer.length;g++){
                    String row = layer[g];
                    for (int d = 0;d < row.length();d++){
                        char c = row.charAt(d);
                        if (c != ' '){
                            BlockState state = m.getBlockByCharacter(c);
                            toReturn.add(new PositionBlockStateTileEntity(new Vec3(d-xzoffset,i-heightOffset,g-xzoffset),state));
                        }
                    }
                }
            }
            return toReturn;
        }

        public static void render(PoseStack matrices, List<PositionBlockStateTileEntity> list,float partialTicks,BlockAndTintGetter getter,double relX,double relY){
            matrices.pushPose();
            MultiBufferSource src = Minecraft.getInstance().renderBuffers().bufferSource();
            BlockEntityRenderDispatcher d = Minecraft.getInstance().getBlockEntityRenderDispatcher();
            list.forEach((box)->{
                box.render(matrices, partialTicks, getter, src, d);
            });
            list.forEach((box)->{
                box.renderTile(matrices, partialTicks, getter, src, d);
            });
            matrices.popPose();
            PoseStack stack = RenderSystem.getModelViewStack();
            stack.pushPose();
            stack.translate(relX,relY,100);
            stack.scale(8,8,8);
            stack.scale(1,-1,1);
            RenderSystem.applyModelViewMatrix();
            Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
            stack.popPose();
            RenderSystem.applyModelViewMatrix();


        }


    }

    public static class OptimizedBlockstateItemRenderer{

        private Map<Item,List<List<BakedQuad>>> ITEMS = new HashMap<>();

        public OptimizedBlockstateItemRenderer(){

        }


        private void renderModelLists(BakedModel model, ItemStack item, int light, int overlay, PoseStack matrices, VertexConsumer vertex) {
            Random random = new Random();
            Item r = item.getItem();
            if (!ITEMS.containsKey(r)){
                ArrayList<List<BakedQuad>> add = new ArrayList<>();
                for (Direction direction : List.of(Direction.UP,Direction.NORTH,Direction.EAST)) {
                    add.add(model.getQuads((BlockState) null, direction, random, EmptyModelData.INSTANCE));
                }
                ITEMS.put(r,add);
            }
            for (List<BakedQuad> quads : ITEMS.get(r)){
                Minecraft.getInstance().getItemRenderer().renderQuadList(matrices, vertex, quads, item, light, overlay);
            }
//            for(Direction direction : List.of(Direction.UP,Direction.NORTH,Direction.EAST)) {
//
//                    random.setSeed(42L);
//                    Minecraft.getInstance().getItemRenderer().renderQuadList(matrices, vertex, model.getQuads((BlockState) null, direction, random, EmptyModelData.INSTANCE), item, light, overlay);
//
//            }

//            random.setSeed(42L);
//            Minecraft.getInstance().getItemRenderer().renderQuadList(matrices, vertex, model.getQuads((BlockState)null, (Direction)null, random,EmptyModelData.INSTANCE), item, light, overlay);
        }



        private void render(ItemStack item, ItemTransforms.TransformType transform, boolean p_115146_, PoseStack matrices, MultiBufferSource src, int light, int overlay, BakedModel model) {
            if (!item.isEmpty()) {
                matrices.pushPose();
                boolean flag = transform == ItemTransforms.TransformType.GUI || transform == ItemTransforms.TransformType.GROUND || transform == ItemTransforms.TransformType.FIXED;
                if (flag) {
                    if (item.is(Items.TRIDENT)) {
                        model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
                    } else if (item.is(Items.SPYGLASS)) {
                        model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
                    }
                }

                model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrices, model, transform, p_115146_);
                matrices.translate(-0.5D, -0.5D, -0.5D);
                if (!model.isCustomRenderer() && (!item.is(Items.TRIDENT) || flag)) {
                    boolean flag1;
                    if (transform != ItemTransforms.TransformType.GUI && !transform.firstPerson() && item.getItem() instanceof BlockItem) {
                        Block block = ((BlockItem)item.getItem()).getBlock();
                        flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                    } else {
                        flag1 = true;
                    }
                    if (model.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(Minecraft.getInstance().getItemRenderer(),model, item, matrices, src, light, overlay, flag1); }
                    else {
                        RenderType rendertype = ItemBlockRenderTypes.getRenderType(item, flag1);
                        VertexConsumer vertexconsumer;
                        if (item.is(Items.COMPASS) && item.hasFoil()) {
                            matrices.pushPose();
                            PoseStack.Pose posestack$pose = matrices.last();
                            if (transform == ItemTransforms.TransformType.GUI) {
                                posestack$pose.pose().multiply(0.5F);
                            } else if (transform.firstPerson()) {
                                posestack$pose.pose().multiply(0.75F);
                            }

                            if (flag1) {
                                vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBufferDirect(src, rendertype, posestack$pose);
                            } else {
                                vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBuffer(src, rendertype, posestack$pose);
                            }

                            matrices.popPose();
                        } else if (flag1) {
                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBufferDirect(src, rendertype, true, item.hasFoil());
                        } else {
                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBuffer(src, rendertype, true, item.hasFoil());
                        }

                        renderModelLists(model, item, light, overlay, matrices, vertexconsumer);
                    }
                } else {
                    net.minecraftforge.client.RenderProperties.get(item).getItemStackRenderer().renderByItem(item, transform, matrices, src, light, overlay);
                }

                matrices.popPose();
            }
        }


        public void renderGuiItem(ItemStack p_115124_, int p_115125_, int p_115126_) {
            renderGuiItem(p_115124_, p_115125_, p_115126_, Minecraft.getInstance().getItemRenderer().getModel(p_115124_, (Level)null, (LivingEntity)null, 0));
        }

        protected void renderGuiItem(ItemStack p_115128_, int p_115129_, int p_115130_, BakedModel p_115131_) {
            Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            posestack.translate((double)p_115129_, (double)p_115130_, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
            posestack.translate(8.0D, 8.0D, 0.0D);
            posestack.scale(1.0F, -1.0F, 1.0F);
            posestack.scale(16.0F, 16.0F, 16.0F);
            RenderSystem.applyModelViewMatrix();
            PoseStack posestack1 = new PoseStack();
            MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            boolean flag = !p_115131_.usesBlockLight();
            if (flag) {
                Lighting.setupForFlatItems();
            }

            render(p_115128_, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, p_115131_);
            multibuffersource$buffersource.endBatch();
            RenderSystem.enableDepthTest();
            if (flag) {
                Lighting.setupFor3DItems();
            }

            posestack.popPose();
            RenderSystem.applyModelViewMatrix();
        }
    }


    public static class LineRenderer{

        public static BufferBuilder preparePositionColorNormal(){
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableCull();
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            RenderSystem.applyModelViewMatrix();

            RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
            BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.lineWidth(3f);
            bufferBuilder.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
            return bufferBuilder;
        }

        public static void end(){
            PoseStack posestack = RenderSystem.getModelViewStack();
            Tesselator.getInstance().end();
            posestack.popPose();

            RenderSystem.applyModelViewMatrix();
        }
    }


}
