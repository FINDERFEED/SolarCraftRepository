package com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.ModelLayersRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class CorruptionWispRenderer extends MobRenderer<CorruptionWisp,CorruptionWispModel<CorruptionWisp>> {

    private static final ResourceLocation WISP = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/corruption_wisp.png");

    public CorruptionWispRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,new CorruptionWispModel<>(ctx.bakeLayer(ModelLayersRegistry.CORRUPTION_WISP_LAYER)), 0.05f);
        //this.addLayer(new Layer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(CorruptionWisp entity) {
        return WISP;
    }

    public static class Layer extends EnergySwirlLayer<CorruptionWisp,CorruptionWispModel<CorruptionWisp>>{

        private static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/corruption_wisp_layer.png");
        private EntityModel<CorruptionWisp> model;

        public Layer(RenderLayerParent<CorruptionWisp, CorruptionWispModel<CorruptionWisp>> parent) {
            super(parent);
            this.model = new CorruptionWispModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayersRegistry.CORRUPTION_WISP_LAYER));
        }

        @Override
        protected float xOffset(float time) {
            return time/200f;
        }

        @Override
        protected ResourceLocation getTextureLocation() {
            return LOC;
        }

        @Override
        protected EntityModel<CorruptionWisp> model() {
            return model;
        }
    }
}
