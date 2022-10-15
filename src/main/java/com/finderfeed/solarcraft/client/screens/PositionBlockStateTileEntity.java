package com.finderfeed.solarcraft.client.screens;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;



import javax.annotation.Nullable;

//OnlyIn(Dist.CLIENT);
public class PositionBlockStateTileEntity {


    @Nullable
    public BlockEntity tile;
    public BlockState state;
    public Vec3 pos;

    public PositionBlockStateTileEntity(Vec3 pos,BlockState state){
        this.pos = pos;

        this.state = state;
        if (state.getBlock() instanceof EntityBlock eblock){
            BlockEntity e = eblock.newBlockEntity(BlockPos.ZERO,state);

            this.tile = e;
        }


    }

//    public void render(PoseStack matrices, float partialTicks, BlockAndTintGetter getter, MultiBufferSource src, BlockEntityRenderDispatcher d){
//
//        renderBlock(matrices,state, pos.x, pos.y, pos.z,getter);
//
//    }
//    public void renderTile(PoseStack matrices, float partialTicks, BlockAndTintGetter getter, MultiBufferSource src, BlockEntityRenderDispatcher d){
//        if (tile != null){
//            BlockEntityRenderer<BlockEntity> renderer;
//            if ((renderer = d.getRenderer(tile)) != null){
//                matrices.pushPose();
//                matrices.translate(pos.x,pos.y,pos.z);
//                renderer.render(tile,partialTicks,matrices,src, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
//                matrices.popPose();
//            }
//        }
//    }
//
//    private static void renderBlock(PoseStack matrices, BlockState state, double translatex, double translatey, double translatez, BlockAndTintGetter getter){
//        matrices.pushPose();
//        matrices.translate(translatex,translatey,translatez);
//        BlockRenderDispatcher d = Minecraft.getInstance().getBlockRenderer();
//        VertexConsumer c = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(ItemBlockRenderTypes.getRenderType(state,true));
//        d.renderBatched(state, BlockPos.ZERO,getter,matrices,c,false,Minecraft.getInstance().level.random, EmptyModelData.INSTANCE);
//        matrices.popPose();
//    }
}
