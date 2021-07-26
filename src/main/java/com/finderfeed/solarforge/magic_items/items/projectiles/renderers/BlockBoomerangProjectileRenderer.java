package com.finderfeed.solarforge.magic_items.items.projectiles.renderers;

import com.finderfeed.solarforge.magic_items.items.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;

import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class BlockBoomerangProjectileRenderer extends EntityRenderer<BlockBoomerangProjectile> {


    public BlockBoomerangProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);

    }
    @Override
    public void render(BlockBoomerangProjectile entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
        matrices.pushPose();
        matrices.mulPose(Vector3f.YN.rotationDegrees((entity.level.getGameTime()+partialTicks)*15%360));

        matrices.mulPose(Vector3f.XN.rotationDegrees(90));
        if (entity.getEntityData().get(entity.BLOCK_ID) != -1000){

            Minecraft.getInstance().getItemRenderer().render(Block.stateById(entity.getEntityData().get(entity.BLOCK_ID)).getBlock().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED,false,
                    matrices,buffer,light,light,Minecraft.getInstance().getItemRenderer().getModel(Block.stateById(entity.getEntityData().get(entity.BLOCK_ID)).getBlock().asItem().getDefaultInstance(),null,null,0));
        }
        Minecraft.getInstance().getItemRenderer().render(ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED,false,
                matrices,buffer,light,light,Minecraft.getInstance().getItemRenderer().getModel(ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(),null,null,0));
        matrices.popPose();
    }

    @Override
    public boolean shouldRender(BlockBoomerangProjectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(BlockBoomerangProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
