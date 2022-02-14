package com.finderfeed.solarforge.entities.models;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.entities.RunicElementalBoss;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.local_library.other.EaseInOut;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import com.finderfeed.solarforge.local_library.other.MemorizedModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class RunicElementalModel extends EntityModel<RunicElementalBoss> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarForge.MOD_ID, "runicelementalmodel"), "main");
	public final ModelPart body;
	public final ModelPart head;
	public final ModelPart lefthand;
	public final ModelPart righthand;
	public final ModelPart legsrow1;
	public final ModelPart legsrow12;
	public final ModelPart legsrow2;
	public final ModelPart legsrow3;

	public final MemorizedModelPart mbody;
	public final MemorizedModelPart mhead;
	public final MemorizedModelPart mlefthand;
	public final MemorizedModelPart mrighthand;
	public final MemorizedModelPart mlegsrow1;
	public final MemorizedModelPart mlegsrow12;
	public final MemorizedModelPart mlegsrow2;
	public final MemorizedModelPart mlegsrow3;


	private RunicElementalBoss boss;

	public RunicElementalModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.lefthand = root.getChild("lefthand");
		this.righthand = root.getChild("righthand");
		this.legsrow1 = root.getChild("legsrow1");
		this.legsrow12 = root.getChild("legsrow12");
		this.legsrow2 = root.getChild("legsrow2");
		this.legsrow3 = root.getChild("legsrow3");

		this.mbody = new MemorizedModelPart(body);
		this.mhead = new MemorizedModelPart(head);
		this.mlefthand = new MemorizedModelPart(lefthand);
		this.mrighthand = new MemorizedModelPart(righthand);
		this.mlegsrow1 = new MemorizedModelPart(legsrow1);
		this.mlegsrow12 = new MemorizedModelPart(legsrow12);
		this.mlegsrow2 = new MemorizedModelPart(legsrow2);
		this.mlegsrow3 = new MemorizedModelPart(legsrow3);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -0.5F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 0).addBox(-2.505F, -1.8366F, -3.6484F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(28, 17).addBox(-1.505F, 3.1634F, -2.6484F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 10).addBox(-3.505F, -0.8366F, -2.6734F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.005F, -4.1634F, 0.6484F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(9, 18).addBox(0.025F, -0.75F, -2.25F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 13).addBox(-5.0F, -0.75F, -2.25F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.495F, -2.8366F, 1.3266F, 0.5236F, 0.0F, 0.0F));

		PartDefinition lefthand = partdefinition.addOrReplaceChild("lefthand", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, -4.5F, -1.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 5.5F, -0.5F));

		PartDefinition righthand = partdefinition.addOrReplaceChild("righthand", CubeListBuilder.create().texOffs(19, 19).addBox(-1.5F, -4.5F, -1.5F, 3.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 5.5F, -0.5F));

		PartDefinition legsrow1 = partdefinition.addOrReplaceChild("legsrow1", CubeListBuilder.create().texOffs(28, 43).addBox(-5.25F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 15).addBox(3.25F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 7).addBox(-1.0F, -2.5F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 0).addBox(-1.0F, -2.5F, 3.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 0.0F));

		PartDefinition legsrow12 = partdefinition.addOrReplaceChild("legsrow12", CubeListBuilder.create(), PartPose.offset(0.0F, 16.5F, 0.0F));

		PartDefinition cube_r3 = legsrow12.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -2.5F, -5.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(39, 36).addBox(-5.25F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(39, 29).addBox(3.25F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(39, 22).addBox(-1.0F, -2.5F, 3.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition legsrow2 = partdefinition.addOrReplaceChild("legsrow2", CubeListBuilder.create().texOffs(31, 29).addBox(-1.0F, -2.5F, -4.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(31, 36).addBox(2.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 37).addBox(-1.0F, -2.5F, 2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 39).addBox(-4.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.5F, 0.0F));

		PartDefinition legsrow3 = partdefinition.addOrReplaceChild("legsrow3", CubeListBuilder.create().texOffs(31, 22).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(RunicElementalBoss boss, float limbSwing, float limbSwingAmount, float ageIntTicks) {
		this.boss = boss;
		super.prepareMobModel(boss, limbSwing, limbSwingAmount, ageIntTicks);
	}

	@Override
	public void setupAnim(RunicElementalBoss boss, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float time = RenderingTools.getTime(boss.level, Minecraft.getInstance().getDeltaFrameTime());
		RunicElementalAnimations.RESET_EVERYTHING.animate(boss,this,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		head.yRot = (float)Math.toRadians(netHeadYaw);
		head.xRot = (float)Math.toRadians(headPitch);
		RunicElementalAnimations.IDLE.animate(boss,this,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.translate(0,-0.6,0);
		poseStack.pushPose();
		poseStack.scale(1.1f,1.1f,1.1f);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		poseStack.popPose();
		head.render(poseStack, buffer, packedLight, packedOverlay);
		lefthand.render(poseStack, buffer, packedLight, packedOverlay);
		righthand.render(poseStack, buffer, packedLight, packedOverlay);
//		legsrow1.render(poseStack, buffer, packedLight, packedOverlay);
		legsrow12.render(poseStack, buffer, packedLight, packedOverlay);
		legsrow2.render(poseStack, buffer, packedLight, packedOverlay);
		legsrow3.render(poseStack, buffer, packedLight, packedOverlay);
		poseStack.popPose();
	}
}