package com.finderfeed.solarforge.magic_items.items.projectiles.renderers;

import com.finderfeed.solarforge.SolarAbilities.MeteoriteProjectile;
import com.finderfeed.solarforge.magic_items.items.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class BlockBoomerangProjectileRenderer extends EntityRenderer<BlockBoomerangProjectile> {


    public BlockBoomerangProjectileRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);

    }
    @Override
    public void render(BlockBoomerangProjectile entity, float p_225623_2_, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int light) {
        matrices.pushPose();
        matrices.mulPose(Vector3f.YN.rotationDegrees((entity.level.getGameTime()+partialTicks)*15%360));

        matrices.mulPose(Vector3f.XN.rotationDegrees(90));
        if (entity.getEntityData().get(entity.BLOCK_ID) != -1000){

            Minecraft.getInstance().getItemRenderer().render(Block.stateById(entity.getEntityData().get(entity.BLOCK_ID)).getBlock().asItem().getDefaultInstance(), ItemCameraTransforms.TransformType.FIXED,false,
                    matrices,buffer,light,light,Minecraft.getInstance().getItemRenderer().getModel(Block.stateById(entity.getEntityData().get(entity.BLOCK_ID)).getBlock().asItem().getDefaultInstance(),null,null));
        }
        Minecraft.getInstance().getItemRenderer().render(ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(), ItemCameraTransforms.TransformType.FIXED,false,
                matrices,buffer,light,light,Minecraft.getInstance().getItemRenderer().getModel(ItemsRegister.BLOCK_BOOMERANG.get().getDefaultInstance(),null,null));
        matrices.popPose();
    }

    @Override
    public boolean shouldRender(BlockBoomerangProjectile p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(BlockBoomerangProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
