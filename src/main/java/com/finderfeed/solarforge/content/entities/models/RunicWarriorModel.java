package com.finderfeed.solarforge.content.entities.models;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.entities.RunicWarrior;
import com.finderfeed.solarforge.local_library.other.MemorizedModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class RunicWarriorModel extends EntityModel<RunicWarrior> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarForge.MOD_ID, "runic_warrior"), "main");
	private final ModelPart head;
	private final ModelPart hands;

	private final MemorizedModelPart mhands;

	public RunicWarriorModel(ModelPart root) {
		this.head = root.getChild("head");
		this.hands = root.getChild("hands");
		this.mhands = new MemorizedModelPart(hands);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 36).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition hands = partdefinition.addOrReplaceChild("hands", CubeListBuilder.create().texOffs(32, 28).addBox(-12.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 32).addBox(6.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition cube_r1 = hands.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-12.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 8).addBox(6.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r2 = hands.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 16).addBox(6.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-12.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.3562F));

		PartDefinition cube_r3 = hands.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 28).addBox(-12.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 24).addBox(6.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(RunicWarrior entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		head.yRot = (float)Math.toRadians(netHeadYaw);
		head.xRot = (float)Math.toRadians(headPitch);
		hands.zRot = mhands.getInitRotZ() + (float)Math.toRadians((ageInTicks % 360)*4);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
		hands.render(poseStack, buffer, packedLight, packedOverlay);
	}
}