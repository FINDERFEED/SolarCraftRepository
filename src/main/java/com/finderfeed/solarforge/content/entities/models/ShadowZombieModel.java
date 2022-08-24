package com.finderfeed.solarforge.content.entities.models;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.entities.ShadowZombie;
import com.finderfeed.solarforge.registries.ModelLayersRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class ShadowZombieModel extends AbstractZombieModel<ShadowZombie> {

    public static final ResourceLocation LAYER = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/shadow_zombie_layer.png");
    public static final ResourceLocation MODEL = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/shadow_zombie.png");

    public ShadowZombieModel(ModelPart part) {
        super(part);
    }

    @Override
    public boolean isAggressive(ShadowZombie zombie) {
        return zombie.isAggressive();
    }





    public static class ShadowZombieLayer extends EnergySwirlLayer<ShadowZombie,ShadowZombieModel>{

        private EntityModel<ShadowZombie> model;

        public ShadowZombieLayer(RenderLayerParent<ShadowZombie, ShadowZombieModel> parent) {
            super(parent);
            this.model = new ShadowZombieModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayersRegistry.SHADOW_ZOMBIE_LAYER));
        }

        @Override
        protected float xOffset(float idk) {

            return idk / 1000f;
        }

        @Override
        protected ResourceLocation getTextureLocation() {
            return LAYER;
        }

        @Override
        protected EntityModel<ShadowZombie> model() {
            return model;
        }
    }
}
