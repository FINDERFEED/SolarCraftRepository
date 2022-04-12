package com.finderfeed.solarforge.magic.blocks.render;

import com.finderfeed.solarforge.magic.blocks.blockentities.AuraHealerTile;
import com.finderfeed.solarforge.magic.blocks.rendering_models.AuraHealerModel;
import com.finderfeed.solarforge.registries.ModelLayersRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class AuraHealerRenderer implements BlockEntityRenderer<AuraHealerTile> {




    public final AuraHealerModel model;
    public final ResourceLocation res = new ResourceLocation("solarforge","textures/block/aura_healer_block.png");
    public AuraHealerRenderer(BlockEntityRendererProvider.Context ctx) {
        model = new AuraHealerModel(ctx.bakeLayer(ModelLayersRegistry.AURA_HEALER_LAYER));
    }

    @Override
    public void render(AuraHealerTile tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int p_225616_5_, int p_225616_6_) {


        float time = (tile.getLevel().getGameTime()+partialTicks);
        matrices.pushPose();


        
        matrices.mulPose(Vector3f.XN.rotationDegrees(180));
        matrices.translate(0.5,-1.85,-0.5);
//        float bigTing = 22 - (time + 15) % 45;
//        if (bigTing >= 0) {
//            matrices.translate(0, bigTing / 100, 0);
//        } else {
//            matrices.translate(0, -bigTing / 100, 0);
//        }
        matrices.translate(0,0.2 + Math.sin(time/10)*0.06,0);


        model.renderToBuffer(matrices,buffer.getBuffer(RenderType.text(res)),p_225616_5_,p_225616_6_,1,1,1,1);
;


        matrices.popPose();
    }
}
