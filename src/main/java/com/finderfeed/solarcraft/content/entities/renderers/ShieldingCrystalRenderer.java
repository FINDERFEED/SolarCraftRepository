package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.not_alive.ShieldingCrystalCrystalBoss;
import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class ShieldingCrystalRenderer extends EntityRenderer<ShieldingCrystalCrystalBoss> {





    public ShieldingCrystalRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }



    @Override
    public void render(ShieldingCrystalCrystalBoss crystal, float p_114486_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
        if (!crystal.isDeploying()) {
            float time = (partialTicks + crystal.level.getGameTime()) % 360;
            RenderingTools.renderObjModel(OBJModels.SHIELDING_CRYSTAL, matrices, buffer, light, OverlayTexture.NO_OVERLAY, (matrix) -> {
                matrix.mulPose(Vector3f.YP.rotationDegrees(-time));
                matrix.translate(0, 1, 0);
                matrix.scale(0.5f, 0.5f, 0.5f);
            });


            for (int i = 0; i < 4; i++) {
                matrices.pushPose();
                matrices.mulPose(Vector3f.YP.rotationDegrees(90 * i + time));
                matrices.translate(0, 1, 0.6f);
                matrices.mulPose(Vector3f.XP.rotationDegrees(60));
                matrices.scale(0.35f, 0.35f, 0.35f);

                RenderingTools.renderObjModel(OBJModels.SHIELDING_CRYSTAL_SHIELD, matrices, buffer, light, OverlayTexture.NO_OVERLAY, (matrix) -> {
                });
                matrices.popPose();
            }
            matrices.pushPose();

            matrices.translate(0,2,0);
            Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            matrices.mulPose(quaternion);
            matrices.scale(0.5f,0.3f,0.5f);
            RenderingTools.renderHpBar(matrices,buffer,crystal.getHealth()/crystal.getMaxHealth());
            matrices.popPose();
        }


        super.render(crystal, p_114486_, partialTicks, matrices, buffer, light);
    }



    @Override
    public ResourceLocation getTextureLocation(ShieldingCrystalCrystalBoss p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
