package com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables;

import com.finderfeed.solarforge.events.other_events.ModelRegistryEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
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
            renderItem(matrices,stack, ItemTransforms.TransformType.NONE,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.FIXED){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.FIXED,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.GROUND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.GROUND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND,buffer,light,overlay);
            matrices.popPose();
        }
        if (transformType == ItemTransforms.TransformType.GUI){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0);
            renderItem(matrices,stack, ItemTransforms.TransformType.GUI,buffer,light,overlay);
            matrices.popPose();

            CompoundTag nbt = stack.getTagElement(ProgressionHelper.TAG_ELEMENT);

            if (nbt != null) {
                AncientFragment frag = AncientFragment.getFragmentByID(nbt.getString(ProgressionHelper.FRAG_ID));
                if (frag != null) {
                    matrices.pushPose();
                    matrices.scale(0.5f,0.5f,0.5f);
                    matrices.translate(1.5,0.5,2);

                    Minecraft.getInstance().getItemRenderer().render(frag.getIcon().getDefaultInstance(), ItemTransforms.TransformType.GUI,false,matrices,buffer,light,overlay,
                            Minecraft.getInstance().getItemRenderer().getModel(frag.getIcon().getDefaultInstance(),null,null,0));
                    matrices.popPose();
                }
            }
        }
    }

    public static void renderItem(PoseStack matrices, ItemStack stack, ItemTransforms.TransformType type,MultiBufferSource buffer,int light, int overlay){
        Minecraft.getInstance().getItemRenderer().render(stack,type,false,matrices,buffer,light,overlay,
                Minecraft.getInstance().getModelManager().getModel(ModelRegistryEvents.ANCIENT_FRAGMENT_MODEL));
    }
}
