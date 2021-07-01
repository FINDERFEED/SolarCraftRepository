package com.finderfeed.solarforge.magic_items.items.isters;

import com.finderfeed.solarforge.magic_items.items.item_models.SolarGodShield;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ShieldOfSolarGodISTER extends ItemStackTileEntityRenderer {

    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/items/solar_god_shield.png");
    public SolarGodShield model = new SolarGodShield();
    public ShieldModel test = new ShieldModel();
    public boolean isBeingUsed = false;
    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transfrom, MatrixStack matrices, IRenderTypeBuffer buffer, int light1, int light2) {
        matrices.pushPose();
        IVertexBuilder builder = buffer.getBuffer(RenderType.text(LOC));
        matrices.scale(1.0F, -1.0F, -1.0F);
        matrices.translate(0,-0.6,-0.2);
        
        if (isBeingUsed && transfrom == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND){
            matrices.translate(0,0,0.4);
            matrices.mulPose(Vector3f.YP.rotationDegrees(70));
            matrices.mulPose(Vector3f.XN.rotationDegrees(40));

        }
        if (isBeingUsed && transfrom == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND){
            matrices.translate(0,0,0.4);
            matrices.mulPose(Vector3f.YN.rotationDegrees(70));
            matrices.mulPose(Vector3f.XN.rotationDegrees(40));

        }
        if (transfrom == ItemCameraTransforms.TransformType.GROUND){
            matrices.translate(-0.5,-1.5,-0.5);
            matrices.scale(2.5f,2.5f,2.5f);
        }
        if (isBeingUsed && ((transfrom == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) ||(transfrom == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)) ){
            matrices.translate(0.1,-0.3,-0.3);
        }

        model.renderToBuffer(matrices,builder,light1,light2,1,1,1,1);


        matrices.popPose();

        super.renderByItem(stack, transfrom, matrices, buffer, light1, light2);
    }

    public void setBeingUsed(boolean beingUsed) {
        isBeingUsed = beingUsed;
    }
}
