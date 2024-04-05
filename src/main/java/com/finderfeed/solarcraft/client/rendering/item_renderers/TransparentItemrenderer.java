package com.finderfeed.solarcraft.client.rendering.item_renderers;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


//thx to Absolem Jackdaw(Subaraki)
public class TransparentItemrenderer extends ItemRenderer {

    public static final TransparentItemrenderer INSTANCE = new TransparentItemrenderer(Minecraft.getInstance().getTextureManager(),
            Minecraft.getInstance().getModelManager(),
            Minecraft.getInstance().getItemColors(),
            Minecraft.getInstance().getItemRenderer().getBlockEntityRenderer(),0.5f);

    private final float transparency;

    public TransparentItemrenderer(TextureManager p_266774_, ModelManager p_266850_, ItemColors p_267016_, BlockEntityWithoutLevelRenderer p_267049_,float t) {
        super(Minecraft.getInstance(), p_266774_, p_266850_, p_267016_, p_267049_);
        this.transparency = t;
    }


    public void renderQuadList(PoseStack p_229112_1_, VertexConsumer p_229112_2_, List<BakedQuad> p_229112_3_, ItemStack stack, int p_229112_5_, int p_229112_6_)
    {

        PoseStack.Pose matrixstack$entry = p_229112_1_.last();

        for (BakedQuad bakedquad : p_229112_3_)
        {

            p_229112_2_.putBulkData(matrixstack$entry, bakedquad, 0.5f, 0.5f, 0.5f, transparency, p_229112_5_, p_229112_6_, false);
        }

    }

    @Override
    public void render(ItemStack p_229111_1_, ItemDisplayContext p_229111_2_, boolean p_229111_3_, PoseStack p_229111_4_, MultiBufferSource p_229111_5_, int p_229111_6_, int p_229111_7_, BakedModel p_229111_8_)
    {

        if (!p_229111_1_.isEmpty())
        {
            p_229111_4_.pushPose();
            boolean flag = p_229111_2_ == ItemDisplayContext.GUI || p_229111_2_ == ItemDisplayContext.GROUND
                    || p_229111_2_ == ItemDisplayContext.FIXED;

            p_229111_8_ = net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms(p_229111_4_, p_229111_8_, p_229111_2_, p_229111_3_);
            p_229111_4_.translate(-0.5D, -0.5D, -0.5D);

            if (!p_229111_8_.isCustomRenderer() && (p_229111_1_.getItem() != Items.TRIDENT || flag))
            {
                boolean flag1 = true;
                if (p_229111_2_ != ItemDisplayContext.GUI && !p_229111_2_.firstPerson() && p_229111_1_.getItem() instanceof BlockItem)
                {
                    Block block = ((BlockItem) p_229111_1_.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                }
                RenderType rendertype = flag1 ? Sheets.translucentCullBlockSheet() : Sheets.translucentItemSheet();
                VertexConsumer ivertexbuilder;

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
