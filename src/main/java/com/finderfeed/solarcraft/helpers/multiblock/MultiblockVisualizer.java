package com.finderfeed.solarcraft.helpers.multiblock;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.screens.PositionBlockStateTileEntity;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = SolarCraft.MOD_ID,value = Dist.CLIENT)
public class MultiblockVisualizer {

    //Based on Patchouli's visualizer. I haven't ctrl+c ctrl+v anything!

    private static VisualizedStructure multiblock = null;
    private static BlockPos visualizingAnchor = null;
    private static TransparentBuffers buffers = null;

    @SubscribeEvent
    public static void render(RenderLevelStageEvent event){

        if (Minecraft.useShaderTransparency()){
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        }else{
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) return;
        }

        Player player = Minecraft.getInstance().player;
        Level world = Minecraft.getInstance().level;
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (player == null || world == null || multiblock == null) return;
        initBuffer();


//        PoseStack posestack = RenderSystem.getModelViewStack();
//        posestack.pushPose();
//        posestack.mulPoseMatrix(event.getPoseStack().last().pose());
//        RenderSystem.applyModelViewMatrix();
        Vec3 pos = camera.getPosition();

        if (visualizingAnchor != null){
            if (!player.isShiftKeyDown()) {
                renderMultiblock(event.getPoseStack(), multiblock, world, Helpers.posToVec(visualizingAnchor).subtract(pos), event.getPartialTick());
            }
        }else{
            Vec3 look = player.getLookAngle().multiply(4,4,4);
            double eyes = player.getStandingEyeHeight(player.getPose(),player.getDimensions(player.getPose()));
            Vec3 e = player.position().add(0,eyes,0);
            ClipContext context = new ClipContext(e,e.add(look), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
            BlockHitResult result = world.clip(context);
            renderMultiblock(event.getPoseStack(),multiblock,world,Helpers.posToVec(result.getBlockPos()
                            .relative(result.getDirection()))
                    .subtract(pos),event.getPartialTick());
        }
        buffers.endBatch();
//        posestack.popPose();
//        RenderSystem.applyModelViewMatrix();
    }

    @SubscribeEvent
    public static void setPos(PlayerInteractEvent.RightClickBlock block){
        if (block.getFace() == null || block.getHand() != InteractionHand.MAIN_HAND || Minecraft.getInstance().player == null) return;

        if (Minecraft.getInstance().hitResult instanceof BlockHitResult blockRes){
            BlockPos playerPos = blockRes.getBlockPos();
            BlockPos eventPos = block.getPos();
            if (!playerPos.equals(eventPos)){
                return;
            }
        }

        if (Minecraft.getInstance().player.getMainHandItem().is(Items.AIR)) {
            if (!Minecraft.getInstance().player.isShiftKeyDown()) {
                if (visualizingAnchor == null) {
                    visualizingAnchor = block.getPos().relative(block.getFace());
                    Minecraft.getInstance().player.swing(InteractionHand.MAIN_HAND);
                }
            }else{
                multiblock = null;
                visualizingAnchor = null;
                Minecraft.getInstance().player.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @SubscribeEvent
    public static void updateGhostsOnPlace(BlockEvent.EntityPlaceEvent event){
        if (visualizingAnchor != null && Minecraft.getInstance().level != null && multiblock != null){
            multiblock.updateGhosts(visualizingAnchor,Minecraft.getInstance().level,event.getPos(),event.getPlacedBlock());
        }
    }

    @SubscribeEvent
    public static void updateGhostsOnBreak(BlockEvent.BreakEvent event){
        if (visualizingAnchor != null && Minecraft.getInstance().level != null && multiblock != null){
            multiblock.updateGhosts(visualizingAnchor,Minecraft.getInstance().level,event.getPos(), Blocks.AIR.defaultBlockState());
        }
    }

    @SubscribeEvent
    public static void removeMultiblock(ClientPlayerNetworkEvent.LoggingOut event){
        multiblock = null;
        visualizingAnchor = null;
    }

    private static void initBuffer(){
        if (buffers == null){
            MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
            BufferBuilder builder = source.builder;
            Map<RenderType, BufferBuilder> fixed = source.fixedBuffers;
            Map<RenderType, BufferBuilder> newMap = new Object2ObjectLinkedOpenHashMap<>();
            for (Map.Entry<RenderType,BufferBuilder> entry : fixed.entrySet()){
                newMap.put(TransparentRenderType.get(entry.getKey()),entry.getValue());
            }
            buffers = new TransparentBuffers(builder,newMap);
        }
    }

    public static void renderMultiblock(PoseStack matrices, VisualizedStructure structure, Level world,Vec3 renderPos,float pTicks){
        BlockRenderDispatcher d = Minecraft.getInstance().getBlockRenderer();
        BlockEntityRenderDispatcher td = Minecraft.getInstance().getBlockEntityRenderDispatcher();


        for (GhostBlock block : structure.getBlocksToRender()){
            if (block.state.isAir() || block.status == GhostBlock.PLACED) continue;
            matrices.pushPose();
            Vec3 pos = renderPos.add(Helpers.posToVec(block.pos));
            matrices.translate(pos.x,pos.y,pos.z);
            int overlay = block.status == GhostBlock.NEUTRAL ? OverlayTexture.NO_OVERLAY : OverlayTexture.RED_OVERLAY_V;
            if (block.status == GhostBlock.WRONG){
                matrices.translate(-0.025,-0.025,-0.025);
                matrices.scale(1.05f,1.05f,1.05f);
            }
            d.renderSingleBlock(block.state,matrices,buffers,0xf000f0, overlay, ModelData.EMPTY,null);
            if (block.tile != null){
                BlockEntityRenderer<BlockEntity> renderer = td.getRenderer(block.tile);
                if (renderer != null){
                    renderer.render(block.tile,pTicks,matrices,buffers, LightTexture.FULL_BRIGHT, overlay);
                }
            }
            matrices.popPose();
        }
    }


    public static void setMultiblock(@Nullable MultiblockStructure multiblock) {
        if (multiblock == null){
            multiblock = null;
            visualizingAnchor = null;
            return;
        }
        MultiblockVisualizer.multiblock = new VisualizedStructure(multiblock);
        MultiblockVisualizer.visualizingAnchor = null;
    }



    private static class TransparentBuffers extends MultiBufferSource.BufferSource{

        protected TransparentBuffers(BufferBuilder p_109909_, Map<RenderType, BufferBuilder> p_109910_) {
            super(p_109909_, p_109910_);
        }


        @Override
        public VertexConsumer getBuffer(RenderType type) {
            return super.getBuffer(TransparentRenderType.get(type));
        }
    }

    private static class TransparentRenderType extends RenderType{

        private static Map<RenderType,RenderType> types = new HashMap<>();

        public TransparentRenderType(RenderType init) {
            super(init.toString() + "_transparent_blocks_solarcraft", init.format(), init.mode(), init.bufferSize(), init.affectsCrumbling(),
                    true, () -> {
                init.setupRenderState();
//                RenderSystem.disableDepthTest();
//                RenderSystem.depthMask(true);
                RenderSystem.setShaderColor(1, 1, 1, 0.45F);
                RenderSystem.enableBlend();
            }, () -> {
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
//                RenderSystem.enableDepthTest();
                init.clearRenderState();
            });
        }

        public static RenderType get(RenderType type){
            if (type instanceof TransparentRenderType) return type;
            return types.computeIfAbsent(type, TransparentRenderType::new);
        }
    }

    private static class VisualizedStructure{

        private List<GhostBlock> blocksToRender = new ArrayList<>();

        public VisualizedStructure(MultiblockStructure structure){
            for (PositionBlockStateTileEntity block : structure.getBlocks()){
                if (block.state.isAir()) continue;
                GhostBlock b = new GhostBlock();
                b.pos = Helpers.vecToPos(block.pos.add(Helpers.posToVec(structure.getCenterOffset())));
                b.state = block.state;
                if (block.state.getBlock() instanceof EntityBlock en){
                    b.tile = en.newBlockEntity(BlockPos.ZERO,block.state);
                    b.tile.setLevel(Minecraft.getInstance().level);
                }
                blocksToRender.add(b);
            }
        }

        public List<GhostBlock> getBlocksToRender() {
            return blocksToRender;
        }

        public void updateGhosts(BlockPos relativeWorldPos,Level world,BlockPos changedPos,BlockState newState){
            for (GhostBlock block : getBlocksToRender()){
                BlockPos worldPos = relativeWorldPos.offset(block.pos);
                BlockState state = world.getBlockState(worldPos);
                if (Helpers.equalsBlockPos(worldPos,changedPos)){
                    state = newState;
                }
                if (!state.isAir()) {
                    if (state != block.state) {
                        block.status = GhostBlock.WRONG;
                    }else{
                        block.status = GhostBlock.PLACED;
                    }
                }else{
                    block.status = GhostBlock.NEUTRAL;
                }

            }
        }
    }

    private static class GhostBlock{
        public static final int PLACED = 0;
        public static final int WRONG = 1;
        public static final int NEUTRAL = 2;

        public BlockEntity tile;
        public BlockState state;
        public BlockPos pos;
        public int status = NEUTRAL;
    }
}
