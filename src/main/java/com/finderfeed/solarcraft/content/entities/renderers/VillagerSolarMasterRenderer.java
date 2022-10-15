package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.VillagerSolarMaster;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;

public class VillagerSolarMasterRenderer extends MobRenderer<VillagerSolarMaster, VillagerModel<VillagerSolarMaster>> {
    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("solarcraft","textures/entities/solarcraft_master_villager.png");

    public VillagerSolarMasterRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new VillagerModel<>(ctx.bakeLayer(ModelLayers.VILLAGER)), 0.5f);
        this.addLayer(new CustomHeadLayer(this, ctx.getModelSet(),ctx.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer(this,ctx.getItemInHandRenderer()));
    }



    @Override
    public ResourceLocation getTextureLocation(VillagerSolarMaster p_110775_1_) {
        return VILLAGER_BASE_SKIN;
    }
}
