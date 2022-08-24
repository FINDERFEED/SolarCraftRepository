package com.finderfeed.solarforge.content.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;


public class AncientFragmentISTER extends BlockEntityWithoutLevelRenderer {


    public AncientFragmentISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {

        if (transformType == ItemTransforms.TransformType.NONE){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);

            renderItem(matrices,stack, ItemTransforms.TransformType.NONE,buffer, light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.FIXED){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.FIXED,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.GROUND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.GROUND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.GUI){


            matrices.pushPose();
            matrices.translate(0.5,0.5,0);
            MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
            Lighting.setupForFlatItems();
            renderItem(matrices,stack, ItemTransforms.TransformType.GUI,source,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
            source.endBatch();
            Lighting.setupFor3DItems();

            CompoundTag nbt = stack.getTagElement(ProgressionHelper.TAG_ELEMENT);

            if (nbt != null) {
                AncientFragment frag = AncientFragment.getFragmentByID(nbt.getString(ProgressionHelper.FRAG_ID));
                if (frag != null) {
                    matrices.pushPose();
                    matrices.scale(0.5f,0.5f,0.5f);
                    matrices.translate(1.5,0.5,2);
                    BakedModel model = Minecraft.getInstance().getItemRenderer()
                            .getModel(frag.getIcon().getDefaultInstance(),Minecraft.getInstance().level, Minecraft.getInstance().player, 0);

                    MultiBufferSource.BufferSource src2 = Minecraft.getInstance().renderBuffers().bufferSource();
                    if (!model.usesBlockLight()){
                        Lighting.setupForFlatItems();
                    }
                    Minecraft.getInstance().getItemRenderer().render(frag.getIcon().getDefaultInstance(), ItemTransforms.TransformType.GUI, false, matrices, src2, light,
                            OverlayTexture.NO_OVERLAY, model);
                    src2.endBatch();
                    if (!model.usesBlockLight()){
                        Lighting.setupFor3DItems();
                    }

                    matrices.popPose();
                }
            }

        }
    }

    public static void renderItem(PoseStack matrices, ItemStack stack, ItemTransforms.TransformType type,MultiBufferSource buffer,int light, int overlay){
        Minecraft.getInstance().getItemRenderer().render(stack,type,false,matrices,buffer,light,overlay,
                Minecraft.getInstance().getModelManager().getModel(OBJModels.ANCIENT_FRAGMENT_MODEL));
    }
}
