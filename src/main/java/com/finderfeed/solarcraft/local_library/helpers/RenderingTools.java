package com.finderfeed.solarcraft.local_library.helpers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarcraft.client.screens.PositionBlockStateTileEntity;
import com.finderfeed.solarcraft.events.RenderEventsHandler;
import com.finderfeed.solarcraft.events.my_events.MyColorEvent;
import com.finderfeed.solarcraft.events.my_events.PostColorEvent;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;

import com.finderfeed.solarcraft.local_library.client.screens.FDSizedScreenComponent;
import com.finderfeed.solarcraft.misc_things.PostShader;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.client.rendering.rendertypes.RadiantPortalRendertype;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.*;

import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.math.MatrixUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.*;
import org.joml.*;

import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.NeoForge;
import java.lang.Math;
import java.util.*;
import java.util.Random;
import java.util.function.Consumer;

import static net.minecraft.client.renderer.entity.ItemRenderer.*;


public class RenderingTools {

    public static final ResourceLocation WHITE_SQUARE = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/white_square.png");
    public static final ResourceLocation RAY = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/ray_into_skyy.png");
    public static final ResourceLocation SHADERED_RAY = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/shadered_ray.png");
    public static final ResourceLocation HP_BAR = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/hp_bar.png"); //0.875
    public static final ResourceLocation RUNIC_ENERGY_BARS_GUI = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/infuser_energy_gui.png");
    public static final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/runic_energy_bar.png");
    public static final ResourceLocation FANCY_BORDER_CORNERS = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/fancy_border_corners.png");
    public static final ResourceLocation FANCY_BORDER_HORIZONTAL = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/fancy_border_horizontal.png");
    public static final ResourceLocation FANCY_BORDER_VERTICAL = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/fancy_border_vertical.png");
    public static final ResourceLocation TEXT_FIELD_INNER = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/text_field_inner.png");
    public static final ResourceLocation TEXT_FIELD_CORNERS = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/text_field_corners.png");
    public static final ResourceLocation TEXT_FIELD_HORIZONTAL = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/text_field_horizontal.png");
    public static final ResourceLocation TEXT_FIELD_VERTICAL = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/text_field_vertical.png");


    public static void renderItemAndTooltip(ItemStack item,GuiGraphics graphics,int x,int y,int mx,int my,int tooltipOffset){
        if (!item.isEmpty()) {
            PoseStack matrix = graphics.pose();

            matrix.pushPose();
            graphics.renderItem(item,x,y);
            graphics.renderItemDecorations(Minecraft.getInstance().font, item, x, y);
            if (isMouseInBorders(mx, my, x, y, x + 16, y + 16)) {
                matrix.translate(0, 0, tooltipOffset);
                graphics.renderTooltip(Minecraft.getInstance().font, item, mx, my);
            }
            matrix.popPose();
        }
    }

    public static GuiGraphics createGraphics(){
        return new GuiGraphics(Minecraft.getInstance(),Minecraft.getInstance().renderBuffers().bufferSource());
    }
    public static Vector3f YP(){
        return new Vector3f(0,1,0);
    }
    public static Vector3f YN(){
        return new Vector3f(0,-1,0);
    }
    public static Vector3f XP(){
        return new Vector3f(1,0,0);
    }
    public static Vector3f XN(){
        return new Vector3f(-1,0,0);
    }
    public static Vector3f ZP(){
        return new Vector3f(0,0,1);
    }
    public static Vector3f ZN(){
        return new Vector3f(0,0,-1);
    }

    //argb
    public static int extractAlpha(int color){
        return (color >> 24) & 0x000000ff;
    }
    public static int extractRed(int color){
        return (color >> 16) & 0x000000ff;
    }

    public static int extractGreen(int color){
        return (color >> 8) & 0x000000ff;
    }
    public static int extractBlue(int color){
        return color & 0x000000ff;
    }


    public static Vector3f toV3f(Vec3 vec){
        return new Vector3f((float)vec.x,(float)vec.y,(float)vec.z);
    }
    public static Vector4f toV4f(Vec3 vec,double w){
        return new Vector4f((float)vec.x,(float)vec.y,(float)vec.z,(float)w);
    }
    public static Quaternionf rotationDegrees(Vector3f rotation, float angle){
        return new Quaternionf(new AxisAngle4f((float)Math.toRadians(angle),rotation));
    }

    public static void renderTextureFromCenter(PoseStack matrices,float centerX,float centerY,float width,float height,float scale){
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX);

        matrices.pushPose();
        matrices.translate(centerX,centerY,0);
        matrices.scale(scale,scale,scale);

        Matrix4f m = matrices.last().pose();

        float w2 = width/2;
        float h2 = height/2;

        builder.vertex(m,- w2,+ h2,0).uv(0,1).endVertex();
        builder.vertex(m,+ w2,+ h2,0).uv(1,1).endVertex();
        builder.vertex(m,+ w2,- h2,0).uv(1,0).endVertex();
        builder.vertex(m,- w2,- h2,0).uv(0,0).endVertex();

        BufferUploader.drawWithShader(builder.end());
        matrices.popPose();
    }

    public static void scissor(float x,float y, float boxX,float boxY){
        Window window = Minecraft.getInstance().getWindow();
        double scale = window.getGuiScale();
        int nx = (int)(x*scale);
        int ny = (int)(window.getHeight() - y*scale);
        int nBX = (int)(boxX * scale);
        int nBY = (int)(boxY * scale);
        RenderSystem.enableScissor(nx,ny - nBY,nBX,nBY);
    }

    public static void renderBox(PoseStack matrices,MultiBufferSource src,AABB box,float r,float g,float b,float a){
        matrices.pushPose();
        Matrix4f m = matrices.last().pose();
        VertexConsumer vertex = src.getBuffer(RenderType.lightning());

        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();





        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.minY,(float)box.minZ).color(r,g,b,a).endVertex();

        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.maxZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.maxX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();
        vertex.vertex(m,(float)box.minX,(float)box.maxY,(float)box.minZ).color(r,g,b,a).endVertex();


        matrices.popPose();
    }

    public static void renderTextField(PoseStack matrices,int xStart,int yStart,int width,int height){
        matrices.pushPose();
        ClientHelpers.bindText(TEXT_FIELD_INNER);


        blitWithBlend(matrices,xStart,yStart,0,0,width,height,4,4,0,1f);

        blitWithBlend(matrices,xStart,yStart,0,0,width,height,4,4,0,1f);


        ClientHelpers.bindText(TEXT_FIELD_CORNERS);
        blitWithBlend(matrices,xStart-13,yStart-13,0,0,13,13,26,26,0,1f);
        blitWithBlend(matrices,xStart + width,yStart-13,13,0,13,13,26,26,0,1f);
        blitWithBlend(matrices,xStart + width,yStart + height,13,13,13,13,26,26,0,1f);
        blitWithBlend(matrices,xStart-13,yStart + height,0,13,13,13,26,26,0,1f);

        ClientHelpers.bindText(TEXT_FIELD_HORIZONTAL);
        blitWithBlend(matrices,xStart,yStart - 11,0,0,width,11,72,22,0,1f);
        blitWithBlend(matrices,xStart,yStart + height,0,11,width,11,72,22,0,1f);

        ClientHelpers.bindText(TEXT_FIELD_VERTICAL);
        blitWithBlend(matrices,xStart - 11,yStart,0,0,11,height,22,63,0,1f);
        blitWithBlend(matrices,xStart + width,yStart,11,0,11,height,22,63,0,1f);
        matrices.popPose();
    }

    public static void drawFancyBorder(PoseStack matrices,int xStart,int yStart,int width,int height,int zOffset){
        matrices.pushPose();
        ClientHelpers.bindText(FANCY_BORDER_CORNERS);
        blitWithBlend(matrices,xStart,yStart,0,0,11,11,22,22,zOffset,1f);
        blitWithBlend(matrices,xStart + width - 11,yStart,11,0,11,11,22,22,zOffset,1f);
        blitWithBlend(matrices,xStart,yStart + height - 11,0,11,11,11,22,22,zOffset,1f);
        blitWithBlend(matrices,xStart + width - 11,yStart + height - 11,11,11,11,11,22,22,zOffset,1f);


        ClientHelpers.bindText(FANCY_BORDER_HORIZONTAL);
        blitWithBlend(matrices,xStart + 11,yStart + 2,0,0,width - 22,6,16,6,zOffset,1f);
        blitWithBlend(matrices,xStart + 11,yStart + 3 + height - 11,0,0,width - 22,6,16,6,zOffset,1f);

        ClientHelpers.bindText(FANCY_BORDER_VERTICAL);
        blitWithBlend(matrices,xStart + 2,yStart + 11,0,0,6,height - 22,6,16,zOffset,1f);
        blitWithBlend(matrices,xStart + 3 + width - 11,yStart + 11,0,0,6, height - 22, 6, 16,zOffset,1f);


        matrices.popPose();
    }

    public static void drawBoundedTextObfuscated(GuiGraphics g,int posx,int posy,int bound,Component component,int color,int ticker){
        int iter = 0;
        int remainingOpenedSymbols = ticker;

        for (String str : RenderingTools.splitString(component.getString(),bound)){
            if (remainingOpenedSymbols >= str.length()){
                g.drawString(Minecraft.getInstance().font,str,posx,posy + iter * 9,color);
                remainingOpenedSymbols -= str.length();
            }else if (remainingOpenedSymbols != 0){
                g.drawString(Minecraft.getInstance().font,Component.literal(str.substring(0,remainingOpenedSymbols)).withStyle(ChatFormatting.RESET)
                        .append(Component.literal("a").withStyle(ChatFormatting.OBFUSCATED)),posx,posy + iter * 9,color);
                remainingOpenedSymbols = 0;
            }
            iter++;
        }

    }

    public static void drawCenteredBoundedTextObfuscated(GuiGraphics gr,int posx,int posy,int bound,Component component,int color,int ticker){
        int iter = 0;
        int remainingOpenedSymbols = ticker;
        for (String str : RenderingTools.splitString(component.getString(),bound)){
            if (remainingOpenedSymbols >= str.length()){
                gr.drawCenteredString(Minecraft.getInstance().font,str,posx,posy + iter * 9,color);
                remainingOpenedSymbols -= str.length();
            }else if (remainingOpenedSymbols != 0){
                gr.drawCenteredString(Minecraft.getInstance().font,Component.literal(str.substring(0,remainingOpenedSymbols)).withStyle(ChatFormatting.RESET)
                        .append(Component.literal("a").withStyle(ChatFormatting.OBFUSCATED)),posx,posy + iter * 9,color);
                remainingOpenedSymbols = 0;
            }
            iter++;
        }
    }



    public static void addActivePostShader(String uniqueID,UniformPlusPlus uniformPlusPlus,PostChainPlusUltra shader){
        if (ClientHelpers.isShadersEnabled()) {
            RenderEventsHandler.ACTIVE_SHADERS.put(uniqueID,new PostShader(uniformPlusPlus, shader));
        }
    }


    public static List<Component> renderRunicEnergyGui(GuiGraphics g, int x, int y, int mousex, int mousey, RunicEnergyContainer current, RunicEnergyCost simulate, float maximum){
        if (Minecraft.getInstance().screen == null) return List.of();
        ClientHelpers.bindText(RUNIC_ENERGY_BARS_GUI);
        blitWithBlend(g.pose(),x, y, 0, 0, 77, 182, 77, 182,0,1f);


        List<Component> tooltips = new ArrayList<>();

        if (simulate != null) {
            RenderSystem.enableBlend();
            renderEnergyBar(g, x + 12, y + 12, simulate.get(RunicEnergy.Type.ZETA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(g, x + 12 + 16, y + 12, simulate.get(RunicEnergy.Type.TERA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(g, x + 12 + 16*2, y + 12, simulate.get(RunicEnergy.Type.KELDA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(g, x + 12 + 16*3-1, y + 12, simulate.get(RunicEnergy.Type.GIRO), true,mousex,mousey,maximum,tooltips);

            renderEnergyBar(g, x + 12, y + 96, simulate.get(RunicEnergy.Type.ARDO), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(g, x + 12 + 16, y + 96, simulate.get(RunicEnergy.Type.FIRA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(g, x + 12 + 16*2, y + 96, simulate.get(RunicEnergy.Type.URBA), true,mousex,mousey,maximum,tooltips);
            renderEnergyBar(g, x + 12 + 16*3-1, y + 96, simulate.get(RunicEnergy.Type.ULTIMA), true,mousex,mousey,maximum,tooltips);
            RenderSystem.disableBlend();
        }

        renderEnergyBar(g, x + 12, y + 12, current.get(RunicEnergy.Type.ZETA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(g, x + 12 + 16, y + 12, current.get(RunicEnergy.Type.TERA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(g, x + 12 + 16*2, y + 12, current.get(RunicEnergy.Type.KELDA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(g, x + 12 + 16*3-1, y + 12, current.get(RunicEnergy.Type.GIRO), false,mousex,mousey,maximum,tooltips);

        renderEnergyBar(g, x + 12, y + 96, current.get(RunicEnergy.Type.ARDO), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(g, x + 12 + 16, y + 96, current.get(RunicEnergy.Type.FIRA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(g, x + 12 + 16*2, y + 96, current.get(RunicEnergy.Type.URBA), false,mousex,mousey,maximum,tooltips);
        renderEnergyBar(g, x + 12 + 16*3-1, y + 96, current.get(RunicEnergy.Type.ULTIMA), false,mousex,mousey,maximum,tooltips);
        return tooltips;
    }

    private static void renderEnergyBar(GuiGraphics g, int offsetx, int offsety, float energyAmount, boolean simulate,int mousex,int mousey,float maxEnergy,List<Component> tooltips){
        g.pose().pushPose();
        int k = 60;
        float energy = (energyAmount / maxEnergy) * k;
        if (!simulate){
            if (isMouseInBorders(mousex,mousey,offsetx,offsety,offsetx + 6,offsety + k)){
                tooltips.add(Component.literal(energyAmount + "/" + maxEnergy));
            }
            g.fill(offsetx,offsety  + k,offsetx + 6,(int)(offsety + k - energy),0xffffff00);
        }else{
            g.fill(offsetx,offsety  + k,offsetx + 6,(int)(offsety + k - energy),0x90ffff00);
        }

        g.pose().popPose();
    }

    /**
    *   Renders ray like in solar forge
     */
    public static void renderRay(PoseStack stack, MultiBufferSource buffer, float mod, float height, Direction direction,boolean rotate,float rotationModifier,float partialTicks){
        stack.pushPose();

        stack.translate(0.5,0.5,0.5);
        if (direction.equals(Direction.DOWN)){
//            stack.mulPose(Vector3f.XN.rotationDegrees(180));
            stack.mulPose(rotationDegrees(XN(),180));
        }else if(direction.equals(Direction.NORTH)){
//            stack.mulPose(Vector3f.YP.rotationDegrees(90));
//            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(rotationDegrees(YP(),90));
            stack.mulPose(rotationDegrees(XP(),90));
        }else if(direction.equals(Direction.SOUTH)){
//            stack.mulPose(Vector3f.YP.rotationDegrees(270));
//            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(rotationDegrees(YP(),270));
            stack.mulPose(rotationDegrees(XP(),90));
        }else if(direction.equals(Direction.EAST)){
            stack.mulPose(rotationDegrees(XP(),90));
//            stack.mulPose(Vector3f.XP.rotationDegrees(90));
        }else if(direction.equals(Direction.WEST)){
//            stack.mulPose(Vector3f.YP.rotationDegrees(180));
//            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(rotationDegrees(YP(),180));
            stack.mulPose(rotationDegrees(XP(),90));
        }

        if (rotate){
//            stack.mulPose(Vector3f.YP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
            stack.mulPose(rotationDegrees(YP(),(Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
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









    private static ResourceLocation runeEnergyOverlay = new ResourceLocation("solarcraft","textures/misc/runic_energy_bar.png");
    public static void renderRuneEnergyOverlay(GuiGraphics g, int x, int y, RunicEnergy.Type type){
        g.pose().pushPose();
        g.pose().translate(x,y,0);
        g.pose().scale(0.7f,0.7f,0.7f);

        Player playerEntity = ClientHelpers.getClientPlayer();
        ClientHelpers.bindText(runeEnergyOverlay);
//        GuiComponent.blit(stack,0,0,0,0,10,60,20,60);
        blitWithBlend(g.pose(),0,0,0,0,10,60,20,60,0,1);
        float currentEnergy = RunicEnergy.getEnergy(playerEntity,type);
//        float maxEnergy = playerEntity.getPersistentData().getFloat(RunicEnergy.MAX_ENERGY_TAG); //TODO:Update max energy on client
        int tex = Math.round(50*(currentEnergy/10000));

//        g.pose().mulPose(Vector3f.ZN.rotationDegrees(180));
        g.pose().mulPose(rotationDegrees(ZN(),180));
        g.pose().translate(-10,-55,0);
//        GuiComponent.blit(g.pose(),0,0,10,0,10,tex,20,60);
        blitWithBlend(g.pose(),0,0,10,0,10,tex,20,60,0,1);

        g.pose().popPose();
        g.pose().pushPose();
        g.pose().translate(x,y,0);

        g.pose().scale(0.7f,0.7f,0.7f);

        ClientHelpers.bindText(new ResourceLocation("solarcraft", "textures/misc/tile_energy_pylon_" + type.id + ".png"));
        RenderSystem.enableBlend();
//        GuiComponent.blit(g.pose(),-3,63,0,0,16,16,16,16);
        blitWithBlend(g.pose(),-3,63,0,0,16,16,16,16,0,1);

        RenderSystem.disableBlend();
        g.pose().popPose();
    }


    public static void drawLine(PoseStack stack,int x1,int y1,int x2,int y2,int red,int green,int blue){
//        GlStateManager._disableTexture();
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
//        GlStateManager._enableTexture();
    }


    public static void renderScaledGuiItem(GuiGraphics graphics,ItemStack stack, float x, float y,float scale,double zOffset){
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack,Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
        PoseStack matrices = graphics.pose();

        matrices.pushPose();
        matrices.translate((float)(x + 8*scale), (float)(y + 8*scale), zOffset);
        matrices.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        matrices.scale(16.0F*scale, 16.0F*scale, 16.0F*scale);
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        renderItemStack(stack, ItemDisplayContext.GUI, false, matrices, graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        graphics.flush();
        if (flag) {
            Lighting.setupFor3DItems();
        }
        matrices.popPose();
    }

    public static void renderScaledGuiItemCentered(GuiGraphics graphics,ItemStack stack, float x, float y,float scale,double zOffset){
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack,Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
        PoseStack matrices = graphics.pose();

        matrices.pushPose();
        matrices.translate(x ,y , zOffset);
        matrices.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        matrices.scale(16.0F*scale, 16.0F*scale, 16.0F*scale);
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        renderItemStack(stack, ItemDisplayContext.GUI, false, matrices, graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        graphics.flush();
        if (flag) {
            Lighting.setupFor3DItems();
        }
        matrices.popPose();
    }

    @Deprecated
    public static void renderScaledGuiItem(ItemStack stack, int x, int y,float scale,double zOffset) {
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();



        posestack.translate((double)x, (double)y, (double)(100.0F  + zOffset));
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


        renderItemStack(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, p_115131_);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    @Deprecated
    public static void renderScaledGuiItemCentered(ItemStack stack, float x, float y,float scale,double zOffset) {
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();


        posestack.translate((double)x, (double)y, (double)(100.0F  + zOffset));
//        posestack.translate(4D, 4D, 0.0D);
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

        renderItemStack(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, p_115131_);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }


    public static void renderItemStack(ItemStack stack, ItemDisplayContext ctx, boolean idk, PoseStack matrices, MultiBufferSource src, int x, int y, BakedModel mdl) {
        if (!stack.isEmpty()) {
            matrices.pushPose();
            boolean flag = ctx == ItemDisplayContext.GUI || ctx == ItemDisplayContext.GROUND || ctx == ItemDisplayContext.FIXED;
            if (flag) {
                if (stack.is(Items.TRIDENT)) {
                    mdl = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(ModelResourceLocation.vanilla("trident", "inventory"));
                } else if (stack.is(Items.SPYGLASS)) {
                    mdl = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(ModelResourceLocation.vanilla("spyglass", "inventory"));
                }
            }

            mdl = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(matrices, mdl, ctx, idk);
            matrices.translate(-0.5F, -0.5F, -0.5F);
            if (!mdl.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (ctx != ItemDisplayContext.GUI && !ctx.firstPerson() && stack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)stack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                for (var model : mdl.getRenderPasses(stack, flag1)) {
                    for (var rendertype : model.getRenderTypes(stack, flag1)) {
                        VertexConsumer vertexconsumer;
                        if (( stack.is(ItemTags.COMPASSES) || stack.is(Items.CLOCK)) && stack.hasFoil()) {
                            matrices.pushPose();
                            PoseStack.Pose posestack$pose = matrices.last();
                            if (ctx == ItemDisplayContext.GUI) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
                            } else if (ctx.firstPerson()) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
                            }

                            if (flag1) {
                                vertexconsumer = getCompassFoilBufferDirect(src, rendertype, posestack$pose);
                            } else {
                                vertexconsumer = getCompassFoilBuffer(src, rendertype, posestack$pose);
                            }

                            matrices.popPose();
                        } else if (flag1) {
                            vertexconsumer = getFoilBufferDirect(src, rendertype, true, stack.hasFoil());
                        } else {
                            vertexconsumer = getFoilBuffer(src, rendertype, true, stack.hasFoil());
                        }

                        Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, x, y, matrices, vertexconsumer);
                    }
                }
            } else {
                net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, ctx, matrices, src, x, y);
            }

            matrices.popPose();
        }
    }



    public static void blitWithBlend(PoseStack matrices,float x,float y,float texPosX,float texPosY,float width,float height,float texWidth,float texHeight, float zOffset,float alpha){
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        BufferBuilder vertex = Tesselator.getInstance().getBuilder();
        float u1 = texPosX / (float)texWidth;
        float u2 = (texPosX + width) / (float)texWidth;
        float v1 = texPosY / (float)texHeight;
        float v2 = (texPosY + height) / (float)texHeight;
        Matrix4f m = matrices.last().pose();
        vertex.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR_TEX);
        vertex.vertex(m,x,y,zOffset).color(1,1,1,alpha).uv(u1,v1).endVertex();
        vertex.vertex(m,x,y + height,zOffset).color(1,1,1,alpha).uv(u1,v2).endVertex();
        vertex.vertex(m,x + width,y + height,zOffset).color(1,1,1,alpha).uv(u2,v2).endVertex();
        vertex.vertex(m,x + width,y,zOffset).color(1,1,1,alpha).uv(u2,v1).endVertex();


        BufferUploader.drawWithShader(vertex.end());
        RenderSystem.disableBlend();
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
//            stack.mulPose(Vector3f.YP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
            stack.mulPose(rotationDegrees(YP(),(Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
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



    public static void renderEntityObjModel(ResourceLocation location, PoseStack matrices, MultiBufferSource buffer, int light, int overlay, @Deprecated Consumer<PoseStack> transforms){
        RenderType t = RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS);
        List<BakedQuad> list = Minecraft.getInstance().getModelManager().getModel(location)
                .getQuads(null, null, RandomSource.create(), ModelData.EMPTY,t);

        VertexConsumer cons = buffer.getBuffer(t);
        matrices.pushPose();
        transforms.accept(matrices);
        for (BakedQuad a : list) {
            cons.putBulkData(matrices.last(), a, 1f, 1f, 1f, light, overlay);
        }
        matrices.popPose();
    }

    public static void renderEntityObjModel(ResourceLocation location, PoseStack matrices, MultiBufferSource buffer, float r, float g, float b, int light, int overlay){
        RenderType t = RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS);
        List<BakedQuad> list = Minecraft.getInstance().getModelManager().getModel(location)
                .getQuads(null, null, RandomSource.create(),ModelData.EMPTY, t);

        VertexConsumer cons = buffer.getBuffer(t);
        matrices.pushPose();

        for (BakedQuad a : list) {
            cons.putBulkData(matrices.last(), a, r, g, b, light, overlay);
        }
        matrices.popPose();
    }

    public static void renderBlockObjModel(ResourceLocation location, PoseStack matrices, MultiBufferSource buffer, float r, float g, float b, int light, int overlay){
        List<BakedQuad> list = Minecraft.getInstance().getModelManager().getModel(location)
                .getQuads(null, null, RandomSource.create(), ModelData.EMPTY,RenderType.solid());
        matrices.pushPose();

        for (BakedQuad a : list) {
            buffer.getBuffer(RenderType.solid()).putBulkData(matrices.last(), a, r, g, b, light, overlay);
        }
        matrices.popPose();

    }

    public static void renderShaderedRay(PoseStack stack, MultiBufferSource buffer, float mod, float height, Consumer<PoseStack> translations, boolean rotate, float rotationModifier, float partialTicks){
        RenderSystem.depthMask(false);
        stack.pushPose();

        stack.translate(0.5,0.5,0.5);
        translations.accept(stack);
        if (rotate){
//            stack.mulPose(Vector3f.YP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
            stack.mulPose(rotationDegrees(YP(),(Minecraft.getInstance().level.getGameTime() + partialTicks)*rotationModifier%360));
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
                SolarCraft.LOGGER.log(org.apache.logging.log4j.Level.ERROR, "Exception caught " + RenderingTools.class.toString() + " method splitString()");
                break;
            }
        }

        return deleteSpacesAtBeggining(returnable);
    }



    private static List<String> deleteSpacesAtBeggining(List<String> strings){
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
        matrices.mulPose(rotationDegrees(YP(),(float)angleY));
        matrices.mulPose(rotationDegrees(XP(),(float)angleX));
    }

    public static float getTime(Level level,float pticks){
        return level.getGameTime() + pticks;
    }


    public static boolean isMouseInBorders(int mx, int my, int x1, int y1, int x2, int y2){
        return (mx >= x1 && mx <= x2) && (my >= y1 && my <= y2);
    }
    public static boolean isMouseInBorders(int mx, int my, FDSizedScreenComponent component){
        return (mx >= 0 && mx <= component.getWidth()) &&
                (my >= 0 && my <= component.getHeight());
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
        matrices.pushPose();
        Matrix4f matrix4f = matrices.last().pose();
        BufferBuilder builder = Tesselator.getInstance().getBuilder();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);

        builder.vertex(matrix4f,(float)x1,(float)y1,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x1,(float)y2,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)y2,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)y1,0).color(r,g,b,a).endVertex();

        BufferUploader.drawWithShader(builder.end());
        RenderSystem.disableBlend();
        matrices.popPose();
    }

    public static void gradientBarHorizontal(PoseStack matrices,double x1,double y1,double x2,double y2,float r,float g,float b,float a){
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

        double sizeY = y2 - y1;

        RenderSystem.enableBlend();
//        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);

        builder.vertex(matrix4f,(float)x1,(float)y1,0).color(r,g,b,0).endVertex();
        builder.vertex(matrix4f,(float)x1,(float)(y2 - sizeY/2f),0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)(y2 - sizeY/2f),0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)y1,0).color(r,g,b,0).endVertex();

        builder.vertex(matrix4f,(float)x1,(float)(y1 + sizeY/2),0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x1,(float)y2 ,0).color(r,g,b,0).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)y2 ,0).color(r,g,b,0).endVertex();
        builder.vertex(matrix4f,(float)x2,(float)(y1 + sizeY/2),0).color(r,g,b,a).endVertex();


        BufferUploader.drawWithShader(builder.end());
//        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void gradientBarVertical(PoseStack matrices,double x1,double y1,double x2,double y2,float r,float g,float b,float a){
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
        double sizeX = x2 - x1;
        RenderSystem.enableBlend();
//        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);

        builder.vertex(matrix4f,(float)x1,(float)y1,0).color(r,g,b,0).endVertex();
        builder.vertex(matrix4f,(float)x1,(float)y2,0).color(r,g,b,0).endVertex();
        builder.vertex(matrix4f,(float)(x2 - sizeX/2f),(float)y2,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)(x2 - sizeX/2f),(float)y1,0).color(r,g,b,a).endVertex();

        builder.vertex(matrix4f,(float)(x1 + sizeX/2f),(float)y1,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)(x1 + sizeX/2f),(float)y2,0).color(r,g,b,a).endVertex();
        builder.vertex(matrix4f,(float)x2 ,(float)y2,0).color(r,g,b,0).endVertex();
        builder.vertex(matrix4f,(float)x2 ,(float)y1,0).color(r,g,b,0).endVertex();

        BufferUploader.drawWithShader(builder.end());
//        RenderSystem.enableTexture();
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
//                matrices.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
//                matrices.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
//                matrices.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
//                matrices.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
//                matrices.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
//                matrices.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
                matrices.mulPose(rotationDegrees(XP(),(random.nextFloat() * 360.0F)));
                matrices.mulPose(rotationDegrees(YP(),(random.nextFloat() * 360.0F)));
                matrices.mulPose(rotationDegrees(ZP(),(random.nextFloat() * 360.0F)));
                matrices.mulPose(rotationDegrees(XP(),(random.nextFloat() * 360.0F)));
                matrices.mulPose(rotationDegrees(YP(),(random.nextFloat() * 360.0F)));
                matrices.mulPose(rotationDegrees(ZP(),(random.nextFloat() * 360.0F + f5 * 90.0F)));
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
    //using my render tooltip thing because currently post events are removed. (copied from GuiGraphics class and a bit modified with events)

    public static void renderTooltipInternal(GuiGraphics g, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner,CustomTooltip tooltip) {
        if (!components.isEmpty()) {
            int i = 0;
            int j = components.size() == 1 ? -2 : 0;

            for(ClientTooltipComponent clienttooltipcomponent : components) {
                int k = clienttooltipcomponent.getWidth(Minecraft.getInstance().font);
                if (k > i) {
                    i = k;
                }

                j += clienttooltipcomponent.getHeight();
            }

            int i2 = i;
            int j2 = j;
            Vector2ic vector2ic = positioner.positionTooltip(g.guiWidth(), g.guiHeight(), x, y, i2, j2);
            int l = vector2ic.x();
            int i1 = vector2ic.y();
            g.pose().pushPose();
            int j1 = 400;
            g.drawManaged(() -> {
//                net.minecraftforge.client.event.RenderTooltipEvent.Color colorEvent = net.minecraftforge.client.ForgeHooksClient.onRenderTooltipColor(this.tooltipStack, this, l, i1, preEvent.getFont(), components);
                MyColorEvent event = new MyColorEvent(Items.AIR.getDefaultInstance(), g.pose(), l, i1, Minecraft.getInstance().font,components,tooltip);
                NeoForge.EVENT_BUS.post(event);
                TooltipRenderUtil.renderTooltipBackground(g, l, i1, i2, j2, 400, event.getBackgroundStart(), event.getBackgroundEnd(), event.getBorderStart(), event.getBorderEnd());
            });
            g.pose().translate(0.0F, 0.0F, 400.0F);
            int k1 = i1;

            for(int l1 = 0; l1 < components.size(); ++l1) {
                ClientTooltipComponent clienttooltipcomponent1 = components.get(l1);
                clienttooltipcomponent1.renderText(Minecraft.getInstance().font, l, k1, g.pose().last().pose(), g.bufferSource());
                k1 += clienttooltipcomponent1.getHeight() + (l1 == 0 ? 2 : 0);
            }

            k1 = i1;

            for(int k2 = 0; k2 < components.size(); ++k2) {
                ClientTooltipComponent clienttooltipcomponent2 = components.get(k2);
                clienttooltipcomponent2.renderImage(Minecraft.getInstance().font, l, k1, g);
                k1 += clienttooltipcomponent2.getHeight() + (k2 == 0 ? 2 : 0);
            }

            g.pose().popPose();
            PostColorEvent event = new PostColorEvent(g.pose(), l, i1, Minecraft.getInstance().font,components,i,j,tooltip);
            NeoForge.EVENT_BUS.post(event);
        }
    }



    public static final ResourceLocation BASIC_LEXICON_PAGE_COMPONENTS = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/basic_lexicon_page/basic_lexicon_page_corners_and_bases.png");
    public static final ResourceLocation BASIC_LEXICON_PAGE_UPDOWN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/basic_lexicon_page/basic_lexicon_page_updown.png");
    public static final ResourceLocation BASIC_LEXICON_PAGE_LEFTRIGHT = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/basic_lexicon_page/basic_lexicon_page_leftright.png");
    public static final ResourceLocation BASIC_LEXICON_PAGE_BACKGROUND = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_main_page_scrollablep.png");


    public static void renderBasicLexiconPage(PoseStack matrix,int x,int y,int infoWidth,int infoHeight){
        matrix.pushPose();
        renderBasicLexiconPageBackground(matrix,x,y,infoWidth,infoHeight);
        renderBasicLexiconPageOutline(matrix,x,y,infoWidth,infoHeight);
        matrix.popPose();
    }

    public static void renderBasicLexiconPageBackground(PoseStack matrix,int x,int y,int infoWidth,int infoHeight){
        matrix.pushPose();
        ClientHelpers.bindText(BASIC_LEXICON_PAGE_BACKGROUND);
        blitWithBlend(matrix,x,y,0,0,infoWidth,infoHeight,512,512,0,1f);
        matrix.popPose();
    }

    public static void renderBasicLexiconPageOutline(PoseStack matrix,int x,int y,int infoWidth,int infoHeight){
        matrix.pushPose();
        ClientHelpers.bindText(BASIC_LEXICON_PAGE_COMPONENTS);
        //0 0 81*13 - lower thingy
        //0 13 89*12 - upper thingy
        //89*48
        /*
        corners -
        upright - 0 25 15*23
        upleft - 15 25 15*23
        downright - 30 25 15*23
        downleft - 45 25 15*23
         */
        float xCenter = x + infoWidth / 2f;
        blitWithBlend(matrix,xCenter - 89/2f,y - 10,0,13,89,12,89,48,0,1f);
        blitWithBlend(matrix,xCenter - 81/2f,y + infoHeight - 2,0,0,81,13,89,48,0,1f);
        blitWithBlend(matrix,x - 10,y - 10,0,25,15,23,89,48,0,1f);
        blitWithBlend(matrix,x + infoWidth - 5,y - 10,15,25,15,23,89,48,0,1f);
        blitWithBlend(matrix,x - 10,y + infoHeight - 13,30,25,15,23,89,48,0,1f);
        blitWithBlend(matrix,x + infoWidth - 5,y + infoHeight - 13,45,25,15,23,89,48,0,1f);

        ClientHelpers.bindText(BASIC_LEXICON_PAGE_UPDOWN);
        //5*8
        blitWithBlend(matrix,x + 5,y - 8,0,0,infoWidth/2f - 89/2f - 5,8,5,8,0,1f);
        blitWithBlend(matrix,xCenter + 89/2f,y - 8 ,0,0,infoWidth/2f - 89/2f - 5,8,5,8,0,1f);

        blitWithBlend(matrix,x + 5,y + infoHeight ,0,0,infoWidth/2f - 81/2f - 5,8,5,8,0,1f);
        blitWithBlend(matrix,xCenter + 81/2f,y + infoHeight,0,0,infoWidth/2f - 81/2f - 5,8,5,8,0,1f);

        ClientHelpers.bindText(BASIC_LEXICON_PAGE_LEFTRIGHT);
        //8*5
        blitWithBlend(matrix,x - 8,y + 13,0,0,8,infoHeight - 26,8,5,0,1f);
        blitWithBlend(matrix,x + infoWidth,y + 13,0,0,8,infoHeight - 26,8,5,0,1f);
        matrix.popPose();
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

    public static boolean isBoxVisible(AABB box){
        LevelRenderer renderer = Minecraft.getInstance().levelRenderer;
        Frustum frustum;
        if (renderer.capturedFrustum != null){
            frustum = renderer.capturedFrustum;
        }else{
            frustum = renderer.cullingFrustum;
        }
        return frustum.isVisible(box);
    }


    public static class StructureRenderer{
        public static List<PositionBlockStateTileEntity> prepareList(MultiblockStructure m){

            List<PositionBlockStateTileEntity> toReturn = new ArrayList<>();
            String[][] struct = m.pattern;
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
                            if (state.getBlock() == SCBlocks.RUNE_ENERGY_PYLON.get()){
                                continue;
                            }
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
                render(box,matrices, partialTicks, getter, src, d);
            });
            list.forEach((box)->{
                renderTile(box,matrices, partialTicks, getter, src, d);
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

        public static void render(PositionBlockStateTileEntity block,PoseStack matrices, float partialTicks, BlockAndTintGetter getter, MultiBufferSource src, BlockEntityRenderDispatcher d){

            renderBlock(matrices,block.state, block.pos.x, block.pos.y, block.pos.z,getter);

        }
        public static void renderTile(PositionBlockStateTileEntity block,PoseStack matrices, float partialTicks, BlockAndTintGetter getter, MultiBufferSource src, BlockEntityRenderDispatcher d){
            if (block.tile != null){
                if (block.tile.getLevel() != Minecraft.getInstance().level){
                    block.tile.setLevel(Minecraft.getInstance().level);
                }
                BlockEntityRenderer<BlockEntity> renderer;
                if ((renderer = d.getRenderer(block.tile)) != null){
                    matrices.pushPose();
                    matrices.translate(block.pos.x,block.pos.y,block.pos.z);
                    renderer.render(block.tile,partialTicks,matrices,src, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
                    matrices.popPose();
                }
            }
        }

        private static void renderBlock(PoseStack matrices, BlockState state, double translatex, double translatey, double translatez, BlockAndTintGetter getter){
            matrices.pushPose();
            matrices.translate(translatex,translatey,translatez);
            BlockRenderDispatcher d = Minecraft.getInstance().getBlockRenderer();
            RenderType t = ItemBlockRenderTypes.getRenderType(state,true);
            VertexConsumer c = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(t);
            d.renderBatched(state, BlockPos.ZERO,getter,matrices,c,false,RandomSource.create(), ModelData.EMPTY,t);
            matrices.popPose();
        }


    }

    public static class OptimizedBlockstateItemRenderer{

        private Map<Item,List<List<BakedQuad>>> ITEMS = new HashMap<>();

        public OptimizedBlockstateItemRenderer(){

        }


        private void renderModelLists(BakedModel model, ItemStack item, int light, int overlay, PoseStack matrices, VertexConsumer vertex) {

            Item r = item.getItem();
            if (!ITEMS.containsKey(r)){
                ArrayList<List<BakedQuad>> add = new ArrayList<>();
                for (Direction direction : List.of(Direction.UP,Direction.NORTH,Direction.EAST)) {
                    add.add(model.getQuads((BlockState) null, direction, RandomSource.create(),ModelData.EMPTY,null));
                }
                ITEMS.put(r,add);
            }
            for (List<BakedQuad> quads : ITEMS.get(r)){
                Minecraft.getInstance().getItemRenderer().renderQuadList(matrices, vertex, quads, item, light, overlay);
            }
        }






        public void renderGuiItemScaled(GuiGraphics graphics,ItemStack p_115124_, int p_115125_, int p_115126_,float scale) {
            renderScaledGuiItem(graphics,p_115124_, p_115125_, p_115126_,scale,0);
        }



        protected void renderScaledGuiItem(GuiGraphics graphics,ItemStack stack, int x, int y,float scale,double zOffset){
            BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack,Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
            PoseStack matrices = graphics.pose();

            matrices.pushPose();
            matrices.translate(x ,y , zOffset);
            matrices.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
            matrices.scale(16.0F*scale, 16.0F*scale, 16.0F*scale);
            boolean flag = !bakedmodel.usesBlockLight();
            if (flag) {
                Lighting.setupForFlatItems();
            }

            renderItemStackOptimized(stack, ItemDisplayContext.GUI, false, matrices, graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
            graphics.flush();
            if (flag) {
                Lighting.setupFor3DItems();
            }
            matrices.popPose();
        }


        public void renderItemStackOptimized(ItemStack stack, ItemDisplayContext ctx, boolean idk, PoseStack matrices, MultiBufferSource src, int x, int y, BakedModel mdl) {
            if (!stack.isEmpty()) {
                matrices.pushPose();
                boolean flag = ctx == ItemDisplayContext.GUI || ctx == ItemDisplayContext.GROUND || ctx == ItemDisplayContext.FIXED;
                if (flag) {
                    if (stack.is(Items.TRIDENT)) {
                        mdl = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(ModelResourceLocation.vanilla("trident", "inventory"));
                    } else if (stack.is(Items.SPYGLASS)) {
                        mdl = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(ModelResourceLocation.vanilla("spyglass", "inventory"));
                    }
                }

                mdl = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(matrices, mdl, ctx, idk);
                matrices.translate(-0.5F, -0.5F, -0.5F);
                if (!mdl.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
                    boolean flag1;
                    if (ctx != ItemDisplayContext.GUI && !ctx.firstPerson() && stack.getItem() instanceof BlockItem) {
                        Block block = ((BlockItem)stack.getItem()).getBlock();
                        flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                    } else {
                        flag1 = true;
                    }
                    for (var model : mdl.getRenderPasses(stack, flag1)) {
                        for (var rendertype : model.getRenderTypes(stack, flag1)) {
                            VertexConsumer vertexconsumer;
                            if (( stack.is(ItemTags.COMPASSES) || stack.is(Items.CLOCK)) && stack.hasFoil()) {
                                matrices.pushPose();
                                PoseStack.Pose posestack$pose = matrices.last();
                                if (ctx == ItemDisplayContext.GUI) {
                                    MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
                                } else if (ctx.firstPerson()) {
                                    MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
                                }

                                if (flag1) {
                                    vertexconsumer = getCompassFoilBufferDirect(src, rendertype, posestack$pose);
                                } else {
                                    vertexconsumer = getCompassFoilBuffer(src, rendertype, posestack$pose);
                                }

                                matrices.popPose();
                            } else if (flag1) {
                                vertexconsumer = getFoilBufferDirect(src, rendertype, true, stack.hasFoil());
                            } else {
                                vertexconsumer = getFoilBuffer(src, rendertype, true, stack.hasFoil());
                            }

                            renderModelLists(model, stack, x, y, matrices, vertexconsumer);
                        }
                    }
                } else {
                    net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, ctx, matrices, src, x, y);
                }

                matrices.popPose();
            }
        }


    }







    public static class Lightning2DRenderer{


        public static void renderLightningRectangle(VertexConsumer vertex,PoseStack matrices,Vec2 init,Vec2 end,float height,float r,float g,float b){
            Matrix4f matrix4f = matrices.last().pose();
            float halfSize = height / 2f;
            float halfHalfSize = halfSize / 2f;
            float initX = init.x;
            float initY = init.y;
            float endX = end.x;
            float endY = end.y;
            vertex.vertex(matrix4f,initX,initY,                     0).color(r,g,b,1.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY + halfSize,  0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY + halfSize,    0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       0).color(r,g,b,1.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       0).color(r,g,b,1.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY + halfSize,    0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY + halfSize,  0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY,                     0).color(r,g,b,1.0f).endVertex();


            vertex.vertex(matrix4f,initX,initY,                     0).color(r,g,b,1.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY - halfSize,  0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY - halfSize,    0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       0).color(r,g,b,1.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       0).color(r,g,b,1.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY - halfSize,    0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY - halfSize,  0).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY,                     0).color(r,g,b,1.0f).endVertex();

        }

        public static void renderLightningRectangle3D(VertexConsumer vertex,PoseStack matrices,Vec3 init,Vec3 end,float height,float r,float g,float b,float a){
            Matrix4f matrix4f = matrices.last().pose();
            float halfSize = height / 2f;
            float initX =(float) init.x;
            float initY =(float) init.y;
            float initZ = (float)init.z;
            float endX = (float)end.x;
            float endY = (float)end.y;
            float endZ = (float)end.z;
            vertex.vertex(matrix4f,initX,initY,                     initZ).color(r,g,b,a).endVertex();
            vertex.vertex(matrix4f,initX,initY + halfSize,  initZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY + halfSize,    endZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       endZ).color(r,g,b,a).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       endZ).color(r,g,b,a).endVertex();
            vertex.vertex(matrix4f,endX,endY + halfSize,    endZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY + halfSize,  initZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY,                     initZ).color(r,g,b,a).endVertex();


            vertex.vertex(matrix4f,initX,initY,                     initZ).color(r,g,b,a).endVertex();
            vertex.vertex(matrix4f,initX,initY - halfSize,  initZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY - halfSize,    endZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       endZ).color(r,g,b,a).endVertex();
            vertex.vertex(matrix4f,endX,endY,                       endZ).color(r,g,b,a).endVertex();
            vertex.vertex(matrix4f,endX,endY - halfSize,    endZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY - halfSize,  initZ).color(r,g,b,0.0f).endVertex();
            vertex.vertex(matrix4f,initX,initY,                     initZ).color(r,g,b,a).endVertex();

        }



        public static List<Vec2> randomLightningBreaks(Random random,int breaksCount,float length,float maxSpread){

            float x = length / (breaksCount+1);
            List<Vec2> points = new ArrayList<>(List.of(new Vec2(0,0)));
            for (int i = 0; i < breaksCount;i++){
                float randomY = random.nextFloat() * maxSpread * (random.nextInt(3) == 0 ? 1 : -1);
                points.add(new Vec2(x * (i + 1),randomY));
            }
            points.add(new Vec2(length,0));
            return points;
        }

        private static List<Vec3> generateLightningPositions(Random random,int breaksCount,float length,float maxSpread,Vec3 init,Vec3 end){
            Vec3 between = end.subtract(init);
            List<Vec3> points = new ArrayList<>(List.of(init));
            for (int i = 0; i < breaksCount;i++){
                float randomY = random.nextFloat() * maxSpread * (random.nextInt(2) == 0 ? 1 : -1);
                double multiplier = (i+1) / (double)(breaksCount+1);
                Vec3 f = init.add(between.multiply(multiplier,multiplier,multiplier).add(0,randomY,0));
                points.add(f);
            }
            points.add(end);
            return points;
        }

        public static void renderLightning(PoseStack matrices,MultiBufferSource source,int breaksCount,float maxSpread,float rectangleHeights,Vec3 initialPos,Vec3 endPos,Random random,float r,float g,float b){
//            VertexConsumer vertex = source.getBuffer(RenderType.lightning());
//            Vec3 between = endPos.subtract(initialPos);
//            float length = (float)between.length();
//            List<Vec3> dots = generateLightningPositions(random,breaksCount,length,maxSpread,initialPos,endPos);
//            matrices.pushPose();
//            for (int i = 0; i < dots.size()-1;i++){
//                Vec3 init = dots.get(i);
//                Vec3 end = dots.get(i+1);
//                renderLightningRectangle3D(vertex,matrices,init,end,rectangleHeights,r,g,b,1f);
//            }
//            matrices.popPose();
            renderLightning(matrices,source.getBuffer(RenderType.lightning()),breaksCount,maxSpread,rectangleHeights,initialPos,endPos,random,r,g,b);
        }


        public static void renderLightning(PoseStack matrices,MultiBufferSource source,int breaksCount,float maxSpread,float rectangleHeights,Vec3 initialPos,Vec3 endPos,Random random,float r,float g,float b,float a){
            renderLightning(matrices,source.getBuffer(RenderType.lightning()),breaksCount,maxSpread,rectangleHeights,initialPos,endPos,random,r,g,b,a);
        }

        public static void renderLightning(PoseStack matrices,VertexConsumer vertex,int breaksCount,float maxSpread,float rectangleHeights,Vec3 initialPos,Vec3 endPos,Random random,float r,float g,float b){

//            Vec3 between = endPos.subtract(initialPos);
//            float length = (float)between.length();
//            List<Vec3> dots = generateLightningPositions(random,breaksCount,length,maxSpread,initialPos,endPos);
//            matrices.pushPose();
//            for (int i = 0; i < dots.size()-1;i++){
//                Vec3 init = dots.get(i);
//                Vec3 end = dots.get(i+1);
//                renderLightningRectangle3D(vertex,matrices,init,end,rectangleHeights,r,g,b,1f);
//            }
//            matrices.popPose();
            renderLightning(matrices,vertex,breaksCount,maxSpread,rectangleHeights,initialPos,endPos,random,r,g,b,1f);
        }

        public static void renderLightning(PoseStack matrices,VertexConsumer vertex,int breaksCount,float maxSpread,float rectangleHeights,Vec3 initialPos,Vec3 endPos,Random random,float r,float g,float b,float a){

            Vec3 between = endPos.subtract(initialPos);
            float length = (float)between.length();
            List<Vec3> dots = generateLightningPositions(random,breaksCount,length,maxSpread,initialPos,endPos);
            matrices.pushPose();
            for (int i = 0; i < dots.size()-1;i++){
                Vec3 init = dots.get(i);
                Vec3 end = dots.get(i+1);
                renderLightningRectangle3D(vertex,matrices,init,end,rectangleHeights,r,g,b,a);
            }
            matrices.popPose();
        }

    }

    public static class Lightning3DRenderer{


        private static final ResourceLocation WHITE = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/white_square.png");

        public static List<Vec3> generateVerticalLightningBreaks(Vec3 init, Vec3 end, int seed, int count,double spread){
            Random random = new Random(seed);
            List<Vec3> breaks = new ArrayList<>();
            breaks.add(init);
            Vec3 between = end.subtract(init);
            double len = end.subtract(init).length();
            between = between.normalize();
            double fraglen = len / (count + 1);
            for (int i = 1; i < count + 1; i++){
                Vec3 v = new Vec3(0,0,1).yRot((float)(Math.PI * 2) * random.nextFloat()).multiply(spread,spread,spread);
                Vec3 point = init.add(between.multiply(i * fraglen,i * fraglen,i * fraglen));
                breaks.add(point.add(v));
            }
            breaks.add(end);
            return breaks;
        }

        private static void placeVertices(Matrix4f mat,VertexConsumer vertex,
                                          int r,int g,int b,int a,
                                          float w,
                                          Vector3f pfi1,
                                          Vector3f pfi2,
                                          Vector3f pfi3,
                                          Vector3f pfi4,
                                          Vector3f pfe1,
                                          Vector3f pfe2,
                                          Vector3f pfe3,
                                          Vector3f pfe4
        ){

            vertex.vertex(mat,pfi1.x + w,pfi1.y,pfi1.z + w).color(r,g,b,a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfi2.x + w,pfi2.y,pfi2.z - w).color(r,g,b,a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe4.x + w,pfe4.y,pfe4.z - w).color(r,g,b,a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe1.x + w,pfe1.y,pfe1.z + w).color(r,g,b,a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();

            vertex.vertex(mat,pfi2.x + w,pfi2.y,pfi2.z - w).color(r,g,b,a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfi3.x - w,pfi3.y,pfi3.z - w).color(r,g,b,a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe3.x - w,pfe3.y,pfe3.z - w).color(r,g,b,a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe4.x + w,pfe4.y,pfe4.z - w).color(r,g,b,a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();

            vertex.vertex(mat,pfi3.x - w,pfi3.y,pfi3.z - w).color(r,g,b,a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfi4.x - w,pfi4.y,pfi4.z + w).color(r,g,b,a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe2.x - w,pfe2.y,pfe2.z + w).color(r,g,b,a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe3.x - w,pfe3.y,pfe3.z - w).color(r,g,b,a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();

            vertex.vertex(mat,pfi4.x - w,pfi4.y,pfi4.z + w).color(r,g,b,a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfi1.x + w,pfi1.y,pfi1.z + w).color(r,g,b,a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe1.x + w,pfe1.y,pfe1.z + w).color(r,g,b,a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
            vertex.vertex(mat,pfe2.x - w,pfe2.y,pfe2.z + w).color(r,g,b,a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();


        }
        private static void generateLightningCube(Matrix4f mat,VertexConsumer vertex,float lwidth,int r,int g,int b,int a,Vec3 init,Vec3 end){
            Vector3f fi = new Vector3f((float)init.x,(float)init.y,(float)init.z);
            Vector3f fe = new Vector3f((float)end.x,(float)end.y,(float)end.z);

            Vector3f pfi1 = new Vector3f(fi).add(-lwidth,0,-lwidth);
            Vector3f pfi2 = new Vector3f(fi).add(-lwidth,0,lwidth);
            Vector3f pfi3 = new Vector3f(fi).add(lwidth,0,lwidth);
            Vector3f pfi4 = new Vector3f(fi).add(lwidth,0,-lwidth);

            Vector3f pfe1 = new Vector3f(fe).add(-lwidth,0,-lwidth);
            Vector3f pfe2 = new Vector3f(fe).add(lwidth,0,-lwidth);
            Vector3f pfe3 = new Vector3f(fe).add(lwidth,0,lwidth);
            Vector3f pfe4 = new Vector3f(fe).add(-lwidth,0,lwidth);

            placeVertices(mat,vertex,255,255,255,255,lwidth / 2f,pfi1,pfi2,pfi3,pfi4, pfe1,pfe2,pfe3,pfe4);

           placeVertices(mat,vertex,r,g,b,a,0,pfi1,pfi2,pfi3,pfi4, pfe1,pfe2,pfe3,pfe4);

        }

        public static void renderLightning3D(MultiBufferSource src,PoseStack matrices,Vec3 init,Vec3 end,int seed,int breaksCount,float lwidth,double spread, int r,int g,int b,int a){
            matrices.pushPose();
            Vec3 dir = end.subtract(init);
            RenderingTools.applyMovementMatrixRotations(matrices,dir);
            end = init.add(0,dir.length(),0);
            List<Vec3> points = generateVerticalLightningBreaks(init,end,seed,breaksCount,spread);
            for (int i = 0; i < points.size() - 1;i++){
                Vec3 p1 = points.get(i);
                Vec3 p2 = points.get(i + 1);
                generateLightningCube(matrices.last().pose(),src.getBuffer(SCRenderTypes.eyesPositionColorTexLightmapNoNormal(WHITE)),lwidth,r,g,b,a,p1,p2);
            }
            matrices.popPose();
        }

    }

    public static WidgetSprites singleWidgetSprite(ResourceLocation location){
        return new WidgetSprites(location,location,location,location);
    }
}
