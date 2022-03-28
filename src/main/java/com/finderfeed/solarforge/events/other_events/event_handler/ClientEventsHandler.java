package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.events.misc.ClientTicker;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarforge.magic.items.ModuleItem;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;

import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = SolarForge.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientEventsHandler {

    private static Map<String, ClientTicker> TICKERS = new HashMap<>();
    private static ArrayList<String> TICKERS_TO_REMOVE = new ArrayList<>();
    private static List<BlockPos> ORES_RENDER_POSITIONS = new ArrayList<>();
    private static List<BlockPos> CATALYST_RENDER_POSITIONS = new ArrayList<>();
    


    @SubscribeEvent
    public static void onPlayerLogout(final ClientPlayerNetworkEvent.LoggedOutEvent event){
        ClientHelpers.deleteCachedFragments();
    }

    @SubscribeEvent
    public static void manageTickers(TickEvent.ClientTickEvent event) {

        if (event.phase == TickEvent.Phase.START && !Minecraft.getInstance().isPaused())  {
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
                    ORES_RENDER_POSITIONS.clear();
                }
            }

            if (player.level.getGameTime() % 20 == 0){
                fillCatalystRenderPositions();

            }
        }
    }



    @SubscribeEvent
    public static void renderList(RenderLevelLastEvent event){
        PoseStack stack = event.getPoseStack();
        if (!ORES_RENDER_POSITIONS.isEmpty()){
            Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();
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

            for (BlockPos pos : ORES_RENDER_POSITIONS){
                double posx = pos.getX()-cam.getPosition().x;
                double posy = pos.getY()-cam.getPosition().y;
                double posz = pos.getZ()-cam.getPosition().z;

                renderBox(bufferBuilder,posx,posy,posz);

            }
            Tesselator.getInstance().end();
            posestack.popPose();

            RenderSystem.applyModelViewMatrix();


        }
        if (!CATALYST_RENDER_POSITIONS.isEmpty()){
            Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();

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

            for (BlockPos pos : CATALYST_RENDER_POSITIONS){
                double posx = pos.getX()-cam.getPosition().x;
                double posy = pos.getY()-cam.getPosition().y;
                double posz = pos.getZ()-cam.getPosition().z;

                renderBox(bufferBuilder,posx,posy,posz);

            }
            Tesselator.getInstance().end();
            posestack.popPose();

            RenderSystem.applyModelViewMatrix();
        }
    }

    public static void renderBox(BufferBuilder builder,double posx,double posy,double posz){
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
        ORES_RENDER_POSITIONS.clear();
        int radius = 3;
        for (int x = -radius;x <= radius;x++){
            for (int y = -radius;y <= radius;y++){
                for (int z = -radius;z <= radius;z++){
                    BlockPos pos = mainpos.offset(x,y,z);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(Tags.Blocks.ORES) || (state.getBlock() instanceof OreBlock)){
                        ORES_RENDER_POSITIONS.add(pos);
                    }
                }
            }
        }
    }

    private static void fillCatalystRenderPositions(){
        CATALYST_RENDER_POSITIONS.clear();
        Player pl = Minecraft.getInstance().player;
        if (pl.getMainHandItem().getItem() instanceof BlockItem t) {
            if ((t.getBlock().defaultBlockState().is(com.finderfeed.solarforge.registries.Tags.CATALYST)) && (t.getBlock() != BlocksRegistry.SOLAR_STONE_COLLUMN.get())) {
                for (LevelChunk c : Helpers.getSurroundingChunks(Minecraft.getInstance().level, Minecraft.getInstance().player.getOnPos())) {
                    for (BlockEntity e : c.getBlockEntities().values()) {
                        if (e instanceof InfuserTileEntity tile) {
                            if (tile.getTier() != null) {
                                CATALYST_RENDER_POSITIONS.addAll(Arrays.asList(tile.getCatalystsPositions()));
                            }
                        }
                    }
                }
            }
        }
    }






}


