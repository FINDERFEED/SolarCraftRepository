package com.finderfeed.solarcraft.content.blocks.solar_forge_block;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;

import java.util.ArrayList;
import java.util.List;

public class SolarForgeBlockModelTrue extends Model {
	private final List<ModelPart> bb_main = new ArrayList<>();
	//private final ModelPart cube_r1;

	public SolarForgeBlockModelTrue(ModelPart part) {
		super(RenderType::text);
		for (int i = 1;i <= 12;i++){
			bb_main.add(part.getChild("part"+i));
			bb_main.get(i-1).setPos(0.0F, 24.0F, 0.0F);

		}
		for (int i = 13;i <= 14;i++){
			bb_main.add(part.getChild("part"+i));
			bb_main.get(i-1).setPos(0.0F, 24.0F, 0.0F);
			setRotationAngle(bb_main.get(i-1), 0.0F, -0.7854F, 0.0F);

		}
//		this.texWidth = 256;
//		this.texHeight = 256;
//
//		bb_main = new ModelPart(this);
//		bb_main.setPos(0.0F, 24.0F, 0.0F);
//		bb_main.texOffs(0, 179).addBox(-16.0F, -7.0F, -16.0F, 32.0F, 3.0F, 32.0F, 0.0F, false);
//		bb_main.texOffs(0, 137).addBox(-9.0F, -17.0F, -9.0F, 18.0F, 10.0F, 18.0F, 0.0F, false);
//		bb_main.texOffs(199, 10).addBox(-5.0F, -27.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
//		bb_main.texOffs(156, 41).addBox(-12.0F, -35.0F, -12.0F, 24.0F, 8.0F, 24.0F, 0.0F, false);
//		bb_main.texOffs(102, 0).addBox(6.0F, -40.0F, -6.0F, 4.0F, 5.0F, 12.0F, 0.0F, false);
//		bb_main.texOffs(224, 168).addBox(-10.0F, -40.0F, -6.0F, 4.0F, 5.0F, 12.0F, 0.0F, false);
//		bb_main.texOffs(162, 141).addBox(-6.0F, -40.0F, -10.0F, 12.0F, 5.0F, 4.0F, 0.0F, false);
//		bb_main.texOffs(105, 26).addBox(-6.0F, -40.0F, 6.0F, 12.0F, 5.0F, 4.0F, 0.0F, false);
//		bb_main.texOffs(35, 0).addBox(-8.0F, -52.0F, -4.0F, 4.0F, 12.0F, 8.0F, 0.0F, false);
//		bb_main.texOffs(48, 50).addBox(4.0F, -52.0F, -4.0F, 4.0F, 12.0F, 8.0F, 0.0F, false);
//		bb_main.texOffs(0, 47).addBox(-4.0F, -52.0F, 4.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
//		bb_main.texOffs(231, 141).addBox(-4.0F, -52.0F, -8.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
//
//		cube_r1 = new ModelPart(this);
//		cube_r1.setPos(0.0F, 0.0F, 0.0F);
//		bb_main.addChild(cube_r1);
//		setRotationAngle(cube_r1, 0.0F, -0.7854F, 0.0F);
//		cube_r1.texOffs(125, 200).addBox(-8.0F, -4.0F, -24.0F, 16.0F, 4.0F, 48.0F, 0.0F, false);
//		cube_r1.texOffs(0, 234).addBox(-24.0F, -4.0F, -8.0F, 48.0F, 4.0F, 16.0F, 0.0F, false);
	}

	public static LayerDefinition createLayer(){
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition parts = mesh.getRoot();
		parts.addOrReplaceChild("part1", CubeListBuilder.create().texOffs(0, 179).addBox(-16.0F, -7.0F, -16.0F, 32.0F, 3.0F, 32.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part2", CubeListBuilder.create().texOffs(0, 137).addBox(-9.0F, -17.0F, -9.0F, 18.0F, 10.0F, 18.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part3", CubeListBuilder.create().texOffs(199, 10).addBox(-5.0F, -27.0F, -5.0F, 10.0F, 10.0F, 10.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part4", CubeListBuilder.create().texOffs(156, 41).addBox(-12.0F, -35.0F, -12.0F, 24.0F, 8.0F, 24.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part5", CubeListBuilder.create().texOffs(102, 0).addBox(6.0F, -40.0F, -6.0F, 4.0F, 5.0F, 12.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part6", CubeListBuilder.create().texOffs(224, 168).addBox(-10.0F, -40.0F, -6.0F, 4.0F, 5.0F, 12.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part7", CubeListBuilder.create().texOffs(162, 141).addBox(-6.0F, -40.0F, -10.0F, 12.0F, 5.0F, 4.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part8", CubeListBuilder.create().texOffs(105, 26).addBox(-6.0F, -40.0F, 6.0F, 12.0F, 5.0F, 4.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part9", CubeListBuilder.create().texOffs(35, 0).addBox(-8.0F, -52.0F, -4.0F, 4.0F, 12.0F, 8.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part10", CubeListBuilder.create().texOffs(48, 50).addBox(4.0F, -52.0F, -4.0F, 4.0F, 12.0F, 8.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part11", CubeListBuilder.create().texOffs(0, 47).addBox(-4.0F, -52.0F, 4.0F, 8.0F, 12.0F, 4.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part12", CubeListBuilder.create().texOffs(231, 141).addBox(-4.0F, -52.0F, -8.0F, 8.0F, 12.0F, 4.0F, false), PartPose.ZERO);

		parts.addOrReplaceChild("part13", CubeListBuilder.create().texOffs(125, 200).addBox(-8.0F, -4.0F, -24.0F, 16.0F, 4.0F, 48.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part14", CubeListBuilder.create().texOffs(0, 234).addBox(-24.0F, -4.0F, -8.0F, 48.0F, 4.0F, 16.0F, false), PartPose.ZERO);
		return LayerDefinition.create(mesh,256,256);
	}


	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.forEach((model)->model.render(matrixStack, buffer, packedLight, packedOverlay));
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}