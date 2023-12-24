package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.not_alive.MyFallingBlockEntity;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.joml.Vector3f;


public class MyFallingBlockEntityRenderer extends EntityRenderer<MyFallingBlockEntity> {


    private final BlockRenderDispatcher dispatcher;

    public MyFallingBlockEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.dispatcher = ctx.getBlockRenderDispatcher();
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(MyFallingBlockEntity entity, float p_114635_, float p_114636_, PoseStack matrices, MultiBufferSource p_114638_, int p_114639_) {
        BlockState blockstate = entity.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = entity.level;
            if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                matrices.pushPose();
                BlockPos blockpos = new BlockPos((int)entity.getX(), (int)entity.getBoundingBox().maxY, (int)entity.getZ());
                matrices.translate(0.5,0.5,0.5);

                float time = (entity.level.getGameTime() + p_114636_)*30%360;
                Vec3 speed = entity.getDeltaMovement().multiply(1,0,1).normalize();
                Vec3 rotateAround = speed.yRot((float)Math.toRadians(90));
//                matrices.mulPose(new Vector3f((float)rotateAround.x,(float)rotateAround.y,(float)rotateAround.z).rotationDegrees(time));

                matrices.mulPose(RenderingTools.rotationDegrees(new Vector3f((float)rotateAround.x,(float)rotateAround.y,(float)rotateAround.z),time));

                matrices.translate(-0.5D, -0.5D, -0.5D);
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(entity.getStartPos())), ModelData.EMPTY))
                    this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, matrices, p_114638_.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(entity.getStartPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
                matrices.popPose();
                super.render(entity, p_114635_, p_114636_, matrices, p_114638_, p_114639_);
            }
        }
    }


//    public void render(MyFallingBlockEntity entity, float p_114635_, float p_114636_, PoseStack matrices, MultiBufferSource p_114638_, int p_114639_) {
//        BlockState blockstate = entity.getBlockState();
//        if (blockstate.getRenderShape() == RenderShape.MODEL) {
//            Level level = entity.getLevel();
//            if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
//                matrices.pushPose();
//                BlockPos blockpos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
//                matrices.translate(0.5,0.5,0.5);
//
//                float time = (entity.level.getGameTime() + p_114636_)*30%360;
//                Vec3 speed = entity.getDeltaMovement().multiply(1,0,1).normalize();
//                Vec3 rotateAround = speed.yRot((float)Math.toRadians(90));
//                matrices.mulPose(new Vector3f((float)rotateAround.x,(float)rotateAround.y,(float)rotateAround.z).rotationDegrees(time));
//
//                matrices.translate(-0.5,-0.5,-0.5);
//
//                BlockRenderDispatcher blockrenderdispatcher = Minecraft.getInstance().getBlockRenderer();
//                for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.chunkBufferLayers()) {
//                    if (ItemBlockRenderTypes.canRenderInLayer(blockstate, type)) {
//                        net.minecraftforge.client.ForgeHooksClient.setRenderType(type);
//                        blockrenderdispatcher.getModelRenderer().tesselateBlock(level,
//                                blockrenderdispatcher.getBlockModel(blockstate), blockstate, blockpos, matrices, p_114638_.getBuffer(type),
//                                true, new Random(),
//                                blockstate.getSeed(entity.getStartPos()), OverlayTexture.NO_OVERLAY);
//
//
//                    }
//                }
//                net.minecraftforge.client.ForgeHooksClient.setRenderType(null);
//                matrices.popPose();
//                super.render(entity, p_114635_, p_114636_, matrices, p_114638_, p_114639_);
//            }
//        }
//    }
    @Override
    public ResourceLocation getTextureLocation(MyFallingBlockEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
