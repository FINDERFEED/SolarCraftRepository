package com.finderfeed.solarforge.rendering.item_renderers;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


//thx to Absolem Jackdaw(Subaraki)
public class TransparentItemrenderer extends ItemRenderer {

    public static final TransparentItemrenderer INSTANCE = new TransparentItemrenderer(Minecraft.getInstance().getTextureManager(),
            Minecraft.getInstance().getModelManager(),
            Minecraft.getInstance().getItemColors(),0.5f);

    private final float transparency;

    public TransparentItemrenderer(TextureManager p_i46552_1_, ModelManager p_i46552_2_, ItemColors p_i46552_3_,float a) {
        super(p_i46552_1_,p_i46552_2_,p_i46552_3_);
        this.transparency = a;
    }

    public void renderQuadList(MatrixStack p_229112_1_, IVertexBuilder p_229112_2_, List<BakedQuad> p_229112_3_, ItemStack stack, int p_229112_5_, int p_229112_6_)
    {

        MatrixStack.Entry matrixstack$entry = p_229112_1_.last();

        for (BakedQuad bakedquad : p_229112_3_)
        {
            p_229112_2_.addVertexData(matrixstack$entry, bakedquad, 0.5f, 0.5f, 0.5f, transparency, p_229112_5_, p_229112_6_, false);
        }

    }

    @Override
    public void render(ItemStack p_229111_1_, ItemCameraTransforms.TransformType p_229111_2_, boolean p_229111_3_, MatrixStack p_229111_4_, IRenderTypeBuffer p_229111_5_, int p_229111_6_, int p_229111_7_, IBakedModel p_229111_8_)
    {

        if (!p_229111_1_.isEmpty())
        {
            p_229111_4_.pushPose();
            boolean flag = p_229111_2_ == ItemCameraTransforms.TransformType.GUI || p_229111_2_ == ItemCameraTransforms.TransformType.GROUND
                    || p_229111_2_ == ItemCameraTransforms.TransformType.FIXED;

            p_229111_8_ = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(p_229111_4_, p_229111_8_, p_229111_2_, p_229111_3_);
            p_229111_4_.translate(-0.5D, -0.5D, -0.5D);

            if (!p_229111_8_.isCustomRenderer() && (p_229111_1_.getItem() != Items.TRIDENT || flag))
            {
                boolean flag1 = true;
                if (p_229111_2_ != ItemCameraTransforms.TransformType.GUI && !p_229111_2_.firstPerson() && p_229111_1_.getItem() instanceof BlockItem)
                {
                    Block block = ((BlockItem) p_229111_1_.getItem()).getBlock();
                    flag1 = !(block instanceof BreakableBlock) && !(block instanceof StainedGlassPaneBlock);
                }
                RenderType rendertype = flag1 ? Atlases.translucentCullBlockSheet() : Atlases.translucentItemSheet();
                IVertexBuilder ivertexbuilder;

                if (flag1)
                {
                    ivertexbuilder = getFoilBufferDirect(p_229111_5_, rendertype, true, p_229111_1_.hasFoil());
                }
                else
                {
                    ivertexbuilder = getFoilBuffer(p_229111_5_, rendertype, true, p_229111_1_.hasFoil());
                }

                this.renderModelLists(p_229111_8_, p_229111_1_, p_229111_6_, p_229111_7_, p_229111_4_, ivertexbuilder);
            }

            p_229111_4_.popPose();
        }
    }
}
