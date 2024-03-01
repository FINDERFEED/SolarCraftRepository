package com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.helpers.ShapesRenderer;
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
import net.minecraft.world.phys.Vec3;


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
    public void render(MortarProjectile entity, float p_225623_2_, float p_225623_3_, PoseStack matrices, MultiBufferSource src, int light) {


        if (entity.trail != null){
            ShapesRenderer.renderTrail(ShapesRenderer.POSITION_COLOR,src.getBuffer(SCRenderTypes.LIGHTING_NO_CULL),matrices,entity.trail,0.5f,false,1,1,1,0.5f,light);
        }
        float time = (entity.level.getGameTime() + p_225623_2_);
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XN(),time%360));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZN(),time%360));
        //ray.render(matrices, src.getBuffer(RenderType.text(RAY)),light,light);

        super.render(entity, p_225623_2_, p_225623_3_, matrices, src, light);
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
