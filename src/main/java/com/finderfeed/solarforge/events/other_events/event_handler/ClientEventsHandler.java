package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.events.misc.ClientTicker;
import com.finderfeed.solarforge.events.my_events.MyColorEvent;
import com.finderfeed.solarforge.events.my_events.PostColorEvent;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.items.ModuleItem;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientEventsHandler {

    private static Map<String, ClientTicker> TICKERS = new HashMap<>();
    private static ArrayList<String> TICKERS_TO_REMOVE = new ArrayList<>();
    private static List<BlockPos> RENDER_POSITIONS = new ArrayList<>();
    private static int testField = 0;

    @SubscribeEvent
    public static void manageTickers(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START)  {
            TICKERS.values().forEach((ticker)->{

                if (ticker.shouldBeRemoved()){
                    TICKERS_TO_REMOVE.add(ticker.getId());
                }
                ticker.tick();
            });
            TICKERS_TO_REMOVE.forEach((id)->{
                TICKERS.remove(id);
            });
            TICKERS_TO_REMOVE.clear();
        }
    }

    public static int getTickerValueOrAddANewOne(String id,int maxval){
        if (TICKERS.containsKey(id)){
            return TICKERS.get(id).getCurrentValue();
        }else{
            ClientTicker ticker = new ClientTicker(id,maxval);
            TICKERS.put(id,ticker);
            return 0;
        }
    }


    public static void removeTicker(String id){
        TICKERS.remove(id);
    }

    @SubscribeEvent
    public static void renderModules(ItemTooltipEvent event){
        ModuleItem.applyHoverText(event.getItemStack(),event.getToolTip());
    }


    //ender radar
    @SubscribeEvent
    public static void clientFillRenderPositions(TickEvent.ClientTickEvent event){
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null){
            if (player.level.getGameTime() % 5 == 0){
                if (player.getInventory().countItem(ItemsRegister.ENDER_RADAR.get()) > 0){
                        fillList(player.getOnPos().above(),player.level);
                }else{
                    RENDER_POSITIONS.clear();
                }
            }
        }
    }


    @SubscribeEvent
    public static void renderList(RenderWorldLastEvent event){
        PoseStack stack = event.getMatrixStack();
        if (!RENDER_POSITIONS.isEmpty()){
            Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();

            VoxelShape shape = Block.box(0,0,0,16,16,16);
//            VertexConsumer c = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.lines());
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableCull();
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            posestack.mulPoseMatrix(stack.last().pose());
            RenderSystem.applyModelViewMatrix();

            RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
            BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.lineWidth(3f);
            bufferBuilder.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);

            for (BlockPos pos : RENDER_POSITIONS){
                double posx = pos.getX()-cam.getPosition().x;
                double posy = pos.getY()-cam.getPosition().y;
                double posz = pos.getZ()-cam.getPosition().z;

                renderBox(posestack,bufferBuilder,posx,posy,posz);

            }
            Tesselator.getInstance().end();
            posestack.popPose();

            RenderSystem.applyModelViewMatrix();


        }
    }

    private static void renderBox(PoseStack matrices,BufferBuilder builder,double posx,double posy,double posz){
        builder.vertex(posx+0,posy+0,posz+0).color(255,255,255,255).normal(0,1,0).endVertex();
        builder.vertex(posx+0,posy+1,posz+0).color(255,255,255,255).normal(0,-1,0).endVertex();

        builder.vertex(posx+0,posy+0,posz+0).color(255,255,255,255).normal(1,0,0).endVertex();
        builder.vertex(posx+1,posy+0,posz+0).color(255,255,255,255).normal(-1,0,0).endVertex();

        builder.vertex(posx+0,posy+0,posz+0).color(255,255,255,255).normal(0,0,1).endVertex();
        builder.vertex(posx+0,posy+0,posz+1).color(255,255,255,255).normal(0,0,-1).endVertex();

        builder.vertex(posx+0,posy+1,posz+0).color(255,255,255,255).normal(0,0,1).endVertex();
        builder.vertex(posx+0,posy+1,posz+1).color(255,255,255,255).normal(0,0,-1).endVertex();

        builder.vertex(posx+0,posy+1,posz+0).color(255,255,255,255).normal(1,0,0).endVertex();
        builder.vertex(posx+1,posy+1,posz+0).color(255,255,255,255).normal(-1,0,0).endVertex();

        builder.vertex(posx+1,posy+1,posz+1).color(255,255,255,255).normal(0,0,-1).endVertex();
        builder.vertex(posx+1,posy+1,posz+0).color(255,255,255,255).normal(0,0,1).endVertex();

        builder.vertex(posx+1,posy+1,posz+1).color(255,255,255,255).normal(-1,0,0).endVertex();
        builder.vertex(posx+0,posy+1,posz+1).color(255,255,255,255).normal(1,0,0).endVertex();

        builder.vertex(posx+1,posy+1,posz+1).color(255,255,255,255).normal(0,-1,0).endVertex();
        builder.vertex(posx+1,posy+0,posz+1).color(255,255,255,255).normal(0,1,0).endVertex();

        builder.vertex(posx+1,posy+0,posz+1).color(255,255,255,255).normal(-1,0,0).endVertex();
        builder.vertex(posx+0,posy+0,posz+1).color(255,255,255,255).normal(1,0,0).endVertex();

        builder.vertex(posx+1,posy+0,posz+1).color(255,255,255,255).normal(0,0,-1).endVertex();
        builder.vertex(posx+1,posy+0,posz+0).color(255,255,255,255).normal(0,0,1).endVertex();

        builder.vertex(posx+0,posy+0,posz+1).color(255,255,255,255).normal(0,1,0).endVertex();
        builder.vertex(posx+0,posy+1,posz+1).color(255,255,255,255).normal(0,-1,0).endVertex();

        builder.vertex(posx+1,posy+0,posz+0).color(255,255,255,255).normal(0,1,0).endVertex();
        builder.vertex(posx+1,posy+1,posz+0).color(255,255,255,255).normal(0,-1,0).endVertex();
    }


    private static void fillList(BlockPos mainpos,Level level){
        RENDER_POSITIONS.clear();
        int radius = 3;
        for (int x = -radius;x <= radius;x++){
            for (int y = -radius;y <= radius;y++){
                for (int z = -radius;z <= radius;z++){
                    BlockPos pos = mainpos.offset(x,y,z);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(Tags.Blocks.ORES) || (state.getBlock() instanceof OreBlock)){
                        RENDER_POSITIONS.add(pos);
                    }
                }
            }
        }
    }




}


