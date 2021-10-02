package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.entities.CrystalBossEntity;
import com.finderfeed.solarforge.events.other_events.ModelRegistryEvents;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class CrystalBossRenderer extends EntityRenderer<CrystalBossEntity> {
    public CrystalBossRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(CrystalBossEntity boss, float idk, float pticks, PoseStack matrices, MultiBufferSource buffer, int light) {
        matrices.pushPose();
        float time = (boss.level.getGameTime() + pticks) % 360;
        RenderingTools.renderObjModel(ModelRegistryEvents.CRYSTAL_BOSS,matrices,buffer,light, OverlayTexture.NO_OVERLAY,(pose)->{
            pose.translate(0,3.5f,0);
            pose.mulPose(Vector3f.YN.rotationDegrees(time));
        });
        matrices.popPose();
        super.render(boss, idk, pticks, matrices, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalBossEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
