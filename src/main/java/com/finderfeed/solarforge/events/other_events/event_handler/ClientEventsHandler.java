package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarforge.content.items.ModuleItem;
import com.finderfeed.solarforge.events.misc.ClientTicker;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.misc_things.CameraShake;
import com.finderfeed.solarforge.misc_things.Flash;
import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = SolarForge.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientEventsHandler {

    private static Map<String, ClientTicker> TICKERS = new HashMap<>();
    private static ArrayList<String> TICKERS_TO_REMOVE = new ArrayList<>();
    private static List<BlockPos> ORES_RENDER_POSITIONS = new ArrayList<>();
    private static List<BlockPos> CATALYST_RENDER_POSITIONS = new ArrayList<>();
    


    @SubscribeEvent
    public static void onPlayerLogout(final ClientPlayerNetworkEvent.LoggingOut event){
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
                if (player.getInventory().countItem(SolarcraftItems.ENDER_RADAR.get()) > 0){
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
    public static void renderList(RenderLevelStageEvent event){
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
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
                    if (state.is(Tags.Blocks.ORES)){
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
            if ((t.getBlock().defaultBlockState().is(com.finderfeed.solarforge.registries.Tags.CATALYST)) && (t.getBlock() != SolarcraftBlocks.SOLAR_STONE_COLLUMN.get())) {
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




    public static Flash currentFlashEffect = null;
    public static CameraShake cameraShakeEffect = null;

//    @SubscribeEvent
//    public static void renderFlash(RenderGameOverlayEvent event){
//        if (currentFlashEffect == null) return;
//        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;
//        PoseStack matrices = event.getMatrixStack();
//        float scaledHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
//        float scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
//        float alpha = 1f;
//        if (currentFlashEffect.getTicker() <= currentFlashEffect.getInTime()){
//            alpha = currentFlashEffect.getTicker() / (float) currentFlashEffect.getInTime();
//        }else if (currentFlashEffect.getTicker() >= currentFlashEffect.getAllTime() - currentFlashEffect.getOutTime()){
//            alpha = 1f - (currentFlashEffect.getTicker() - currentFlashEffect.getStayTime() - currentFlashEffect.getInTime())/(float)currentFlashEffect.getOutTime();
//        }
//        RenderSystem.enableBlend();
//        RenderSystem.disableTexture();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        BufferBuilder b = Tesselator.getInstance().getBuilder();
//        b.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);
//
//        Matrix4f m = matrices.last().pose();
//
//        b.vertex(m,0,scaledHeight,0).color(1f,1f,1f,alpha).endVertex();
//        b.vertex(m,scaledWidth,scaledHeight,0).color(1f,1f,1f,alpha).endVertex();
//        b.vertex(m,scaledWidth,0,0).color(1f,1f,1f,alpha).endVertex();
//        b.vertex(m,0,0,0).color(1f,1f,1f,alpha).endVertex();
////        b.end();
//        BufferUploader.drawWithShader(b.end());
//        RenderSystem.enableTexture();
//        RenderSystem.disableBlend();
//    }

    @SubscribeEvent
    public static void tickFlashAndCameraShake(TickEvent.PlayerTickEvent event){
        if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END){
            if (currentFlashEffect != null) {
                currentFlashEffect.tick();
                if (currentFlashEffect.isFinished()) {
                    currentFlashEffect = null;
                }
            }
            if (cameraShakeEffect != null){
                cameraShakeEffect.tick();
                if (cameraShakeEffect.isFinished()){
                    cameraShakeEffect = null;
                }
            }

        }
    }

    public static void setCurrentFlashEffect(Flash currentFlashEffect) {
        ClientEventsHandler.currentFlashEffect = currentFlashEffect;
    }

    public static void setCameraShakeEffect(CameraShake cameraShakeEffect) {
        ClientEventsHandler.cameraShakeEffect = cameraShakeEffect;
    }

    @SubscribeEvent
    public static void cameraShake(ViewportEvent.ComputeCameraAngles event){

        if (Minecraft.getInstance().level == null || cameraShakeEffect == null) return;
        Random random = new Random(Minecraft.getInstance().level.getGameTime()*1233);

        float spread = cameraShakeEffect.getMaxSpread();
        float mod = 1f;
        int time = cameraShakeEffect.getTicker();
        if (time <= cameraShakeEffect.getInTime()){
            mod = time / (float) cameraShakeEffect.getInTime();
        }else if (time >= cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime()){
            mod = (time - (cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime()) )/(float) cameraShakeEffect.getOutTime();
        }


        spread *= mod;
        float rx = random.nextFloat()*spread*2 - spread;
        float ry = random.nextFloat()*spread*2 - spread;
        event.setPitch(event.getPitch() + rx);
        event.setYaw(event.getYaw() + ry);
    }
}


