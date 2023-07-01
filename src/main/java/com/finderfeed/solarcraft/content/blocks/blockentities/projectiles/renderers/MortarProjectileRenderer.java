package com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers;

import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
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
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;





public class MortarProjectileRenderer extends EntityRenderer<MortarProjectile> {
    public final ResourceLocation RAY = new ResourceLocation("solarcraft","textures/misc/crossbow_shot_texture.png");
    public final ModelPart ray;

    public static LayerDefinition createLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("mortar", CubeListBuilder.create().addBox(-16,-16,-16,16,16,16), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition,16,16);
    }

    public MortarProjectileRenderer(EntityRendererProvider.Context p_i46179_1_) {
        super(p_i46179_1_);
        ray = p_i46179_1_.bakeLayer(ModelLayersRegistry.MORTAR_PROJ_LAYER);
        ray.setPos(8,8,8);
    }

    @Override
    public void render(MortarProjectile p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack matrices, MultiBufferSource p_225623_5_, int p_225623_6_) {


        float time = (p_225623_1_.level.getGameTime() + p_225623_2_);
//        matrices.mulPose(Vector3f.XN.rotationDegrees(time%360));
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XN(),time%360));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZN(),time%360));
        ray.render(matrices, p_225623_5_.getBuffer(RenderType.text(RAY)),p_225623_6_,p_225623_6_);

        super.render(p_225623_1_, p_225623_2_, p_225623_3_, matrices, p_225623_5_, p_225623_6_);
    }

    @Override
    public boolean shouldRender(MortarProjectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(MortarProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
