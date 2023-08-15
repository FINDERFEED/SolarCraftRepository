package com.finderfeed.solarcraft.content.items.solar_disc_gun;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

import static com.finderfeed.solarcraft.local_library.helpers.RenderingTools.*;


public class SolarDiscProjectileRenderer extends EntityRenderer<SolarDiscProjectile> {
    public ResourceLocation SOLAR_DISC = new ResourceLocation("solarcraft","textures/misc/solar_disc.png");


    public SolarDiscProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);

    }
    @Override
    public void render(SolarDiscProjectile entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
        matrices.pushPose();
        float time = (entity.level.getGameTime() + partialTicks)*30;
        RenderingTools.applyMovementMatrixRotations(matrices,entity.getDeltaMovement());
//        matrices.mulPose(Vector3f.ZP.rotationDegrees(time % 360));
        matrices.mulPose(rotationDegrees(ZP(),time % 360));
        Minecraft.getInstance().getItemRenderer().render(SCItems.SOLAR_DISC.get().getDefaultInstance(), ItemDisplayContext.FIXED,false,
                matrices,buffer,light,getPackedLightCoords(entity,light),Minecraft.getInstance().getItemRenderer().getModel(SCItems.SOLAR_DISC.get().getDefaultInstance(),null,null,0));

        matrices.popPose();
    }

    @Override
    public boolean shouldRender(SolarDiscProjectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(SolarDiscProjectile p_110775_1_) {
        return SOLAR_DISC;
    }


}
