package com.finderfeed.solarforge.magic_items.blocks.render;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.AuraHealerTile;
import com.finderfeed.solarforge.magic_items.blocks.rendering_models.AuraHealerModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class AuraHealerRenderer extends TileEntityRenderer<AuraHealerTile> {




    public final AuraHealerModel model = new AuraHealerModel();
    public final ResourceLocation res = new ResourceLocation("solarforge","textures/block/aura_healer_block.png");
    public AuraHealerRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(AuraHealerTile tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {


        float time = (tile.getLevel().getGameTime()+partialTicks);
        matrices.pushPose();



        matrices.mulPose(Vector3f.XN.rotationDegrees(180));
        matrices.translate(0.5,-1.85,-0.5);
        float bigTing = 22 - (time + 15) % 45;
        if (bigTing >= 0) {
            matrices.translate(0, bigTing / 100, 0);
        } else {
            matrices.translate(0, -bigTing / 100, 0);
        }


        model.renderToBuffer(matrices,buffer.getBuffer(RenderType.text(res)),p_225616_5_,p_225616_6_,1,1,1,1);
;


        matrices.popPose();
    }
}
