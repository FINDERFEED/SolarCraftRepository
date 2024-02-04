package com.finderfeed.solarcraft.events.other_events.event_handler;


import com.finderfeed.solarcraft.SolarCraft;
//import com.finderfeed.solarcraft.client.model_loaders.RadiantBlocksModelLoader;
import com.finderfeed.solarcraft.client.rendering.radiant_texture.RadiantTextureSpriteSource;
import com.finderfeed.solarcraft.client.tooltips.RETooltipComponent;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarCraftButton;
import com.finderfeed.solarcraft.content.items.ModuleItem;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.LexiconScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconScreenHandler;
import com.finderfeed.solarcraft.content.items.solar_wand.client.WandModeSelectionScreen;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.screen_constructor.BasicBuildableScreen;
import com.finderfeed.solarcraft.local_library.screen_constructor.RenderableComponentInstance;
import com.finderfeed.solarcraft.local_library.screen_constructor.ScreenDataBuilder;
import com.finderfeed.solarcraft.local_library.screen_constructor.WidgetInstance;
import com.finderfeed.solarcraft.local_library.screen_constructor.renderable_component_instances.ImageComponent;
import com.finderfeed.solarcraft.misc_things.CameraShake;
import com.finderfeed.solarcraft.misc_things.Flash;
import com.finderfeed.solarcraft.misc_things.IScrollable;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CastAbilityPacket;
import com.finderfeed.solarcraft.packet_handler.packets.RequestLoginDataPacket;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.lwjgl.glfw.GLFW;

import java.util.*;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientEventsHandler {

    private static List<BlockPos> ORES_RENDER_POSITIONS = new ArrayList<>();
    private static List<BlockPos> CATALYST_RENDER_POSITIONS = new ArrayList<>();

    public static SolarLexiconScreenHandler SOLAR_LEXICON_SCREEN_HANDLER = new SolarLexiconScreenHandler();

    public static Set<Integer> pressedKeys = new HashSet<>();




    @SubscribeEvent
    public static void onScreenOpening(ScreenEvent.Opening event){
        if (event.getNewScreen() instanceof LexiconScreen scr){
            SOLAR_LEXICON_SCREEN_HANDLER.onNewScreenOpened(scr);
        }
    }

    @SubscribeEvent
    public static void handleScreenKeyInputs(ScreenEvent.KeyPressed.Pre event){
        pressedKeys.add(event.getKeyCode());
        if (event.getKeyCode() == GLFW.GLFW_KEY_ESCAPE && SOLAR_LEXICON_SCREEN_HANDLER.escapePressed()){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handleScreenKeyReleases(ScreenEvent.KeyReleased.Pre event){
        pressedKeys.remove(event.getKeyCode());
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        if (event.phase == TickEvent.Phase.END){
            for (int i : pressedKeys){
                if (Minecraft.getInstance().screen instanceof IScrollable){
                    ((IScrollable) Minecraft.getInstance().screen).performScroll(i);
                }
            }
        }
    }

    @SubscribeEvent
    public static void handleKeyInputs(final InputEvent.Key event){

        if (Minecraft.getInstance().screen != null) return;

        if (SCClientModEventHandler.FIRST_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){
            FDPacketUtil.sendToServer(new CastAbilityPacket(1));
//            SCPacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(1));
        }
        if (SCClientModEventHandler.SECOND_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            FDPacketUtil.sendToServer(new CastAbilityPacket(2));

        }
        if (SCClientModEventHandler.THIRD_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            FDPacketUtil.sendToServer(new CastAbilityPacket(3));

        }
        if (SCClientModEventHandler.FORTH_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            FDPacketUtil.sendToServer(new CastAbilityPacket(4));

        }

        if (SCClientModEventHandler.GUI_ABILITY_BUY_SCREEN.isDown() && event.getAction() == GLFW.GLFW_PRESS){
            ClientHelpers.requestAbilityScreen(false);
        }

        if (SCClientModEventHandler.GUI_WAND_MODE_SELECTION.isDown() && event.getAction() == GLFW.GLFW_PRESS
                && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getMainHandItem().is(SCItems.SOLAR_WAND.get())){
            Minecraft.getInstance().setScreen(new WandModeSelectionScreen());
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void addREComponentsToItems(RenderTooltipEvent.GatherComponents event){
        ItemStack item = event.getItemStack();
        RunicEnergyCost cost;
        if (Minecraft.getInstance().player == null) return;

        if (!Helpers.hasPlayerCompletedProgression(Progression.RUNIC_ENERGY_REPEATER,Minecraft.getInstance().player)) return;

        if (!item.isEmpty() && (cost = ConfigRegistry.ITEM_RE_CONFIG.getItemCost(item.getItem())) != null){
            var list = event.getTooltipElements();
            list.add(Either.right(new RETooltipComponent(cost)));
            if (Screen.hasShiftDown()){
                for (RunicEnergy.Type type : cost.getSetTypes()){
                    Component c = Component.literal(type.toString().toUpperCase(Locale.ROOT)+ ": " + "%.1f".formatted(cost.get(type))).withStyle(ChatFormatting.GOLD);
                    list.add(Either.left(c));
                }
            }else{
                list.add(Either.left(Component.literal("[SHIFT]").withStyle(ChatFormatting.DARK_GRAY)));
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerLogout(final ClientPlayerNetworkEvent.LoggingOut event){
        ClientHelpers.deleteCachedFragments();
        SOLAR_LEXICON_SCREEN_HANDLER.onLogout();

    }

    @SubscribeEvent
    public static void onPlayerLogin(final ClientPlayerNetworkEvent.LoggingIn event){
        FDPacketUtil.sendToServer(new RequestLoginDataPacket());
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
            if (player.level().getGameTime() % 5 == 0){
                if (player.getInventory().countItem(SCItems.ENDER_RADAR.get()) > 0){
                        fillList(player.getOnPos().above(),player.level());
                }else{
                    ORES_RENDER_POSITIONS.clear();
                }
            }

            if (player.level().getGameTime() % 20 == 0){
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
//            RenderSystem.disableTexture();
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
//            RenderSystem.disableTexture();
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
            if ((t.getBlock().defaultBlockState().is(com.finderfeed.solarcraft.registries.Tags.CATALYST)) && (t.getBlock() != SCBlocks.SOLAR_STONE_COLLUMN.get())) {
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

//    @SubscribeEvent
//    public static void initiateClientDamageSources(ClientPlayerNetworkEvent.LoggingIn event){
//        SolarcraftDamageSources.initializeDamageSources(event.getPlayer().clientLevel);
//    }

//    @SubscribeEvent
//    public static void cameraShake(ViewportEvent.ComputeCameraAngles event){
//
//        if (Minecraft.getInstance().level == null || cameraShakeEffect == null) return;
//        Random random = new Random(Minecraft.getInstance().level.getGameTime()*1233);
//
//        float spread = cameraShakeEffect.getMaxSpread();
//        float mod = 1f;
//        int time = cameraShakeEffect.getTicker();
//        if (time <= cameraShakeEffect.getInTime()){
//            mod = time / (float) cameraShakeEffect.getInTime();
//        }else if (time >= cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime()){
//            mod = (time - (cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime()) )/(float) cameraShakeEffect.getOutTime();
//        }
//
//
//        spread *= mod;
//        float rx = random.nextFloat()*spread*2 - spread;
//        float ry = random.nextFloat()*spread*2 - spread;
//        event.setPitch(event.getPitch() + rx);
//        event.setYaw(event.getYaw() + ry);
//    }
}


