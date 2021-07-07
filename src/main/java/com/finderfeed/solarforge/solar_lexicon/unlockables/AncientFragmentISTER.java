package com.finderfeed.solarforge.solar_lexicon.unlockables;

import com.finderfeed.solarforge.other_events.ModelRegistryEvents;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.lwjgl.opengl.GL11;



public class AncientFragmentISTER extends ItemStackTileEntityRenderer {


    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrices, IRenderTypeBuffer buffer, int light, int overlay) {
        renderItem(matrices,stack,transformType,buffer,light,overlay);
        if (transformType == ItemCameraTransforms.TransformType.GUI){
            CompoundNBT nbt = stack.getTagElement(ProgressionHelper.TAG_ELEMENT);
            if (nbt != null) {
                AncientFragment frag = AncientFragment.getFragmentByID(nbt.getString(ProgressionHelper.FRAG_ID));
                if (frag != null) {
                    GL11.glScalef(0.3f, 0.3f, 0.3f);

                    Minecraft.getInstance().getItemRenderer().renderGuiItem(frag.getIcon().getDefaultInstance(),0,0);
                }
            }
        }
    }

    public static void renderItem(MatrixStack matrices, ItemStack stack, ItemCameraTransforms.TransformType type,IRenderTypeBuffer buffer,int light, int overlay){
        Minecraft.getInstance().getItemRenderer().render(stack,type,true,matrices,buffer,light,overlay,
                Minecraft.getInstance().getModelManager().getModel(ModelRegistryEvents.ANCIENT_FRAGMENT_MODEL));
    }
}
