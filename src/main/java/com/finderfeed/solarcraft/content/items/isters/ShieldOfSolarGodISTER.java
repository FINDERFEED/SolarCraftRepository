package com.finderfeed.solarcraft.content.items.isters;

import com.finderfeed.solarcraft.content.items.item_models.SolarGodShield;
import com.finderfeed.solarcraft.registries.ModelLayersRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import net.minecraft.resources.ResourceLocation;
import static com.finderfeed.solarcraft.local_library.helpers.RenderingTools.*;

public class ShieldOfSolarGodISTER extends BlockEntityWithoutLevelRenderer {

    public final ResourceLocation LOC = new ResourceLocation("solarcraft","textures/items/solar_god_shield.png");
    public SolarGodShield model;

    public boolean isBeingUsed = false;

    public ShieldOfSolarGodISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
        model = new SolarGodShield(p_172551_.bakeLayer(ModelLayersRegistry.SOLAR_GOD_SHIELD_MODEL));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transform, PoseStack matrices, MultiBufferSource buffer, int light1, int light2) {
        matrices.pushPose();
        VertexConsumer builder = buffer.getBuffer(RenderType.text(LOC));
        matrices.scale(1.0F, -1.0F, -1.0F);
        matrices.translate(0,-0.6,-0.2);
        
        if (Minecraft.getInstance().player.isUsingItem() && transform == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
            matrices.translate(0,0,0.4);
//            matrices.mulPose(Vector3f.YP.rotationDegrees(70));
//            matrices.mulPose(Vector3f.XN.rotationDegrees(40));
            matrices.mulPose(rotationDegrees(YP(),70));
            matrices.mulPose(rotationDegrees(XN(),40));

        } else if (Minecraft.getInstance().player.isUsingItem() && transform == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
            matrices.translate(0,0,0.4);
//            matrices.mulPose(Vector3f.YN.rotationDegrees(70));
//            matrices.mulPose(Vector3f.XN.rotationDegrees(40));

            matrices.mulPose(rotationDegrees(YN(),70));
            matrices.mulPose(rotationDegrees(XN(),40));

        } else if (transform == ItemDisplayContext.GROUND){
            matrices.translate(-0.5,-1.5,-0.5);
            matrices.scale(2.5f,2.5f,2.5f);
        } else if (Minecraft.getInstance().player.isUsingItem() && ((transform == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) ||(transform == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)) ){
            matrices.translate(0.1,-0.3,-0.3);
        }

        model.renderToBuffer(matrices,builder,light1,light2,1,1,1,1);


        matrices.popPose();

        super.renderByItem(stack, transform, matrices, buffer, light1, light2);
    }

    public void setBeingUsed(boolean beingUsed) {
        isBeingUsed = beingUsed;
    }
}
