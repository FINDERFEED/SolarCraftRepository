package com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers;

import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.TurretProjectile;
import com.finderfeed.solarcraft.registries.ModelLayersRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;


public class AbstractTurretProjectileRenderer extends EntityRenderer<Projectile> {
    public final ResourceLocation RAY = new ResourceLocation("solarcraft","textures/misc/crossbow_shot_texture.png");

    public final ModelPart ray;

    public static LayerDefinition createLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("turret", CubeListBuilder.create().addBox(-4,-4,-4,4,4,4), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition,16,16);
    }

    public AbstractTurretProjectileRenderer(EntityRendererProvider.Context p_i46179_1_) {
        super(p_i46179_1_);
        ray = p_i46179_1_.bakeLayer(ModelLayersRegistry.TURRET_PROJ_LAYER);
        ray.setPos(2,2,2);
    }

    @Override
    public void render(Projectile entity, float partialTicks, float idk, PoseStack matrices, MultiBufferSource buffer, int light) {
        float time = (entity.level().getGameTime() + partialTicks);
//        matrices.mulPose(Vector3f.XN.rotationDegrees(time%360));
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));
        matrices.pushPose();
        matrices.translate(0,0.12,0);
        ray.render(matrices, buffer.getBuffer(RenderType.text(RAY)),light,getPackedLightCoords(entity,idk));
        matrices.popPose();
        super.render(entity,partialTicks,idk,matrices,buffer,light);
    }

    @Override
    public boolean shouldRender(Projectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(Projectile p_110775_1_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
