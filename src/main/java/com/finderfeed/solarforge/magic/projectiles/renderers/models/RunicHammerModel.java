package com.finderfeed.solarforge.magic.projectiles.renderers.models;


import com.finderfeed.solarforge.SolarForge;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RunicHammerModel extends Model {
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/runic_hammer.png");
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarForge.MOD_ID, "runic_axe"), "main");
	private final ModelPart bb_main;

	public RunicHammerModel(ModelPart root) {
		super(RenderType::text);
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -23.0F, -1.0F, 2.0F, 36.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 0).addBox(-0.5F, -21.0F, -3.0F, 1.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 16).addBox(-0.5F, -20.0F, -5.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 17).addBox(-1.0F, -21.0F, -7.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 17).addBox(-1.0F, -21.0F, 5.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 5).addBox(-0.5F, -20.0F, 3.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(-1.5F, 13.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}



	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, buffer, packedLight, packedOverlay);
	}
}