package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.other_events.ModelRegistryEvents;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;



public class AncientFragmentISTER extends ItemStackTileEntityRenderer {


    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrices, IRenderTypeBuffer buffer, int light, int overlay) {
        if (transformType == ItemCameraTransforms.TransformType.NONE){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.NONE,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemCameraTransforms.TransformType.FIXED){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.FIXED,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemCameraTransforms.TransformType.GROUND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.GROUND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemCameraTransforms.TransformType.GUI){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0);
            renderItem(matrices,stack, ItemCameraTransforms.TransformType.GUI,buffer,light,overlay);
            matrices.popPose();

            CompoundNBT nbt = stack.getTagElement(ProgressionHelper.TAG_ELEMENT);
            if (nbt != null) {
                AncientFragment frag = AncientFragment.getFragmentByID(nbt.getString(ProgressionHelper.FRAG_ID));
                if (frag != null) {
                    matrices.pushPose();
                    matrices.scale(0.5f,0.5f,0.5f);
                    matrices.translate(1.5,0.5,2);

                    Minecraft.getInstance().getItemRenderer().render(frag.getIcon().getDefaultInstance(), ItemCameraTransforms.TransformType.GUI,false,matrices,buffer,light,overlay,
                            Minecraft.getInstance().getItemRenderer().getModel(frag.getIcon().getDefaultInstance(),null,null));
                    matrices.popPose();
                }
            }
        }
    }

    public static void renderItem(MatrixStack matrices, ItemStack stack, ItemCameraTransforms.TransformType type,IRenderTypeBuffer buffer,int light, int overlay){
        Minecraft.getInstance().getItemRenderer().render(stack,type,false,matrices,buffer,light,overlay,
                Minecraft.getInstance().getModelManager().getModel(ModelRegistryEvents.ANCIENT_FRAGMENT_MODEL));
    }
}
