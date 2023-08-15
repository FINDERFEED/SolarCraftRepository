package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.content.entities.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;

import net.minecraft.resources.ResourceLocation;


public class BlockBoomerangProjectileRenderer extends EntityRenderer<BlockBoomerangProjectile> {


    public BlockBoomerangProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);

    }
    @Override
    public void render(BlockBoomerangProjectile entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
        matrices.pushPose();
//        matrices.mulPose(Vector3f.YN.rotationDegrees((entity.level.getGameTime()+partialTicks)*15%360));

        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),(entity.level.getGameTime()+partialTicks)*15%360));
//        matrices.mulPose(Vector3f.XN.rotationDegrees(90));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XN(),90));
        int id;
        if ((id = entity.getEntityData().get(entity.BLOCK_ID)) != -1000){

            Minecraft.getInstance().getItemRenderer().render(Block.stateById(id).getBlock().asItem().getDefaultInstance(), ItemDisplayContext.FIXED,false,
                    matrices,buffer,light, OverlayTexture.NO_OVERLAY,
                    Minecraft.getInstance().getItemRenderer().getModel(Block.stateById(id).getBlock().asItem().getDefaultInstance(),null,null,0));
        }
        Minecraft.getInstance().getItemRenderer().render(SCItems.BLOCK_BOOMERANG.get().getDefaultInstance(), ItemDisplayContext.FIXED,false,
                matrices,buffer,light,getPackedLightCoords(entity,partialTicks),Minecraft.getInstance().getItemRenderer().getModel(SCItems.BLOCK_BOOMERANG.get().getDefaultInstance(),null,null,0));
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
