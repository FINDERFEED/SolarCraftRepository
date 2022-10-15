package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp;// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.other.MemorizedModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CorruptionWispModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarCraft.MOD_ID, "corruptionwispmodel"), "main");
	private final ModelPart outer_ring;
	private final ModelPart inner_ring;
	private final MemorizedModelPart mouter;
	private final MemorizedModelPart minner;

	public CorruptionWispModel(ModelPart root) {
		this.outer_ring = root.getChild("outer_ring");
		this.inner_ring = root.getChild("inner_ring");
		this.mouter = new MemorizedModelPart(outer_ring);
		this.minner = new MemorizedModelPart(inner_ring);
	}

	public static LayerDefinition createBodyLayer(float deformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition outer_ring = partdefinition.addOrReplaceChild("outer_ring", CubeListBuilder.create().texOffs(11, 6).addBox(-2.5F, -5.0F, -1.5F, 5.0F, 1.0F, 3.0F, new CubeDeformation(deformation))
		.texOffs(11, 0).addBox(-2.5F, 4.0F, -1.5F, 5.0F, 1.0F, 3.0F, new CubeDeformation(deformation)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = outer_ring.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 12).addBox(-2.0F, 4.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(deformation))
		.texOffs(12, 15).addBox(-2.0F, -5.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(deformation)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r2 = outer_ring.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 4.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(deformation))
		.texOffs(0, 15).addBox(-2.0F, -5.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(deformation)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition inner_ring = partdefinition.addOrReplaceChild("inner_ring", CubeListBuilder.create().texOffs(0, 6).addBox(-1.5F, -3.5F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(deformation))
		.texOffs(0, 0).addBox(-1.5F, 2.5F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(deformation)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r3 = inner_ring.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(10, 18).addBox(-1.0F, 2.6F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(deformation)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r4 = inner_ring.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 2.6F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(deformation)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r5 = inner_ring.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(17, 19).addBox(-1.0F, -3.6F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(deformation)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r6 = inner_ring.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(21, 15).addBox(-1.0F, -3.6F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(deformation)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		ageInTicks = ageInTicks*6;
		this.outer_ring.y = mouter.getInitY() - 3f;
		this.inner_ring.y = minner.getInitY() - 3f;
		this.outer_ring.yRot = (float) Math.toRadians(mouter.getInitRotY() + ageInTicks % 360);
		this.outer_ring.xRot = (float) Math.toRadians(mouter.getInitRotX() + ageInTicks % 360);
		this.outer_ring.zRot = (float) Math.toRadians(mouter.getInitRotZ() - ageInTicks % 360);

		this.inner_ring.yRot = (float) Math.toRadians(minner.getInitRotY() - ageInTicks % 360);
		this.inner_ring.xRot = (float) Math.toRadians(minner.getInitRotX() - ageInTicks % 360);
		this.inner_ring.zRot = (float) Math.toRadians(minner.getInitRotZ() + ageInTicks % 360);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		outer_ring.render(poseStack, buffer, packedLight, packedOverlay);
		inner_ring.render(poseStack, buffer, packedLight, packedOverlay);
	}
}