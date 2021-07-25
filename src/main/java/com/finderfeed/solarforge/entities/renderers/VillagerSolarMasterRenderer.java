package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.entities.VillagerSolarMaster;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.resources.ResourceLocation;

public class VillagerSolarMasterRenderer extends MobRenderer<VillagerSolarMaster, VillagerModel<VillagerSolarMaster>> {
    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("solarforge","textures/entities/solarcraft_master_villager.png");

    public VillagerSolarMasterRenderer(EntityRenderDispatcher p_i50961_1_) {
        super(p_i50961_1_, new VillagerModel<VillagerSolarMaster>(0), 0.5f);

    }



    @Override
    public ResourceLocation getTextureLocation(VillagerSolarMaster p_110775_1_) {
        return VILLAGER_BASE_SKIN;
    }
}
