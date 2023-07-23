package com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables;

import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;


public class AncientFragmentISTER extends BlockEntityWithoutLevelRenderer {


    public AncientFragmentISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {

        if (transformType == ItemDisplayContext.NONE){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);

            renderItem(matrices,stack, ItemDisplayContext.NONE,buffer, light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        } else if (transformType == ItemDisplayContext.FIXED){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemDisplayContext.FIXED,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        } else if (transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemDisplayContext.FIRST_PERSON_LEFT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        } else if (transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        } else if (transformType == ItemDisplayContext.GROUND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemDisplayContext.GROUND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        } else if (transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        } else if (transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
            matrices.pushPose();
            matrices.translate(0.5,0.5,0.5);
            renderItem(matrices,stack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND,buffer,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        } else  if (transformType == ItemDisplayContext.GUI){


            matrices.pushPose();
            matrices.translate(0.5,0.5,0);
            MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
            Lighting.setupForFlatItems();
            renderItem(matrices,stack, ItemDisplayContext.GUI,source,light,OverlayTexture.NO_OVERLAY);
            matrices.popPose();
            source.endBatch();
            Lighting.setupFor3DItems();

            CompoundTag nbt = stack.getTagElement(AncientFragmentHelper.TAG_ELEMENT);

            if (nbt != null) {
                AncientFragment frag = AncientFragment.getFragmentByID(nbt.getString(AncientFragmentHelper.FRAG_ID));
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
                    Minecraft.getInstance().getItemRenderer().render(frag.getIcon().getDefaultInstance(), ItemDisplayContext.GUI, false, matrices, src2, light,
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

    public static void renderItem(PoseStack matrices, ItemStack stack, ItemDisplayContext type,MultiBufferSource buffer,int light, int overlay){
        Minecraft.getInstance().getItemRenderer().render(stack,type,false,matrices,buffer,light,overlay,
                Minecraft.getInstance().getModelManager().getModel(OBJModels.ANCIENT_FRAGMENT_MODEL));
    }
}
