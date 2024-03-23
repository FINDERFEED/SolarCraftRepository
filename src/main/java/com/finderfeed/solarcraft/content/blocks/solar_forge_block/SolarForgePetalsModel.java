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

public class SolarForgePetalsModel extends Model {
	private final List<ModelPart> bb_main = new ArrayList<>();
	private final List<ModelPart> cube_r1 = new ArrayList<>();
	private final List<ModelPart> cube_r2 = new ArrayList<>();

	public SolarForgePetalsModel(ModelPart part) {
		super(RenderType::entityTranslucent);
		for (int i = 1;i <= 9;i++){
			bb_main.add(part.getChild("part"+i));
			bb_main.get(i-1).setPos(9.5625F, 24-22.8125F, 0.0F);
			setRotationAngle(bb_main.get(i-1), 0.0F, 0.0F, -0.2618F);
		}
		for (int i = 10;i <= 17;i++){
			bb_main.add(part.getChild("part"+i));
			bb_main.get(i-1).setPos(-9.5625F, 24-22.8125F, 0.0F);
			setRotationAngle(bb_main.get(i-1), 0.0F, 0.0F, 0.2618F);
		}
//		this.texWidth = 256;
//		this.texHeight = 256;
//
//		bb_main = new ModelPart(this);
//		bb_main.setPos(0.0F, 24.0F, 0.0F);
//
//
//		cube_r1 = new ModelPart(this);
//		cube_r1.setPos(9.5625F, -22.8125F, 0.0F);
//		bb_main.addChild(cube_r1);
//		setRotationAngle(cube_r1, 0.0F, 0.0F, -0.2618F);
//		cube_r1.texOffs(184, 20).addBox(4.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, 0.0F, false);
//		cube_r1.texOffs(155, 148).addBox(8.4375F, -4.1875F, -2.0F, 2.0F, 17.0F, 4.0F, 0.0F, false);
//		cube_r1.texOffs(25, 16).addBox(5.4375F, 12.8125F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, false);
//		cube_r1.texOffs(86, 101).addBox(5.4375F, -11.1875F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, false);
//		cube_r1.texOffs(62, 28).addBox(3.4375F, -16.1875F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
//		cube_r1.texOffs(31, 200).addBox(6.4375F, -6.1875F, -5.0F, 2.0F, 21.0F, 10.0F, 0.0F, false);
//		cube_r1.texOffs(0, 55).addBox(3.4375F, 15.8125F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, false);
//		cube_r1.texOffs(107, 23).addBox(3.4375F, -12.1875F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, false);
//		cube_r1.texOffs(184, 20).addBox(4.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, 0.0F, false);
//
//		cube_r2 = new ModelPart(this);
//		cube_r2.setPos(-9.5625F, -22.8125F, 0.0F);
//		bb_main.addChild(cube_r2);
//		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.2618F);
//		cube_r2.texOffs(25, 16).addBox(-7.4375F, 12.8125F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, true);
//		cube_r2.texOffs(86, 101).addBox(-7.4375F, -11.1875F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, true);
//		cube_r2.texOffs(62, 28).addBox(-4.4375F, -16.1875F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, true);
//		cube_r2.texOffs(31, 200).addBox(-8.4375F, -6.1875F, -5.0F, 2.0F, 21.0F, 10.0F, 0.0F, true);
//		cube_r2.texOffs(0, 55).addBox(-5.4375F, 15.8125F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, true);
//		cube_r2.texOffs(107, 23).addBox(-5.4375F, -12.1875F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, true);
//		cube_r2.texOffs(184, 20).addBox(-6.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, 0.0F, true);
//		cube_r2.texOffs(155, 148).addBox(-10.4375F, -4.1875F, -2.0F, 2.0F, 17.0F, 4.0F, 0.0F, true);
	}

	public static LayerDefinition createLayer(){
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition parts = mesh.getRoot();
		parts.addOrReplaceChild("part1", CubeListBuilder.create().texOffs(184, 20).addBox(4.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part2", CubeListBuilder.create().texOffs(155, 148).addBox(8.4375F, -4.1875F, -2.0F, 2.0F, 17.0F, 4.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part3", CubeListBuilder.create().texOffs(25, 16).addBox(5.4375F, 12.8125F, -3.0F, 2.0F, 7.0F, 6.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part4", CubeListBuilder.create().texOffs(86, 101).addBox(5.4375F, -11.1875F, -3.0F, 2.0F, 7.0F, 6.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part5", CubeListBuilder.create().texOffs(62, 28).addBox(3.4375F, -16.1875F, -2.0F, 1.0F, 4.0F, 4.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part6", CubeListBuilder.create().texOffs(31, 200).addBox(6.4375F, -6.1875F, -5.0F, 2.0F, 21.0F, 10.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part7", CubeListBuilder.create().texOffs(0, 55).addBox(3.4375F, 15.8125F, -4.0F, 2.0F, 5.0F, 8.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part8", CubeListBuilder.create().texOffs(107, 23).addBox(3.4375F, -12.1875F, -4.0F, 2.0F, 5.0F, 8.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("part9", CubeListBuilder.create().texOffs(184, 20).addBox(4.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, false), PartPose.ZERO);

		parts.addOrReplaceChild("part10", CubeListBuilder.create().texOffs(25, 16).addBox(-7.4375F, 12.8125F, -3.0F, 2.0F, 7.0F, 6.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("part11", CubeListBuilder.create().texOffs(86, 101).addBox(-7.4375F, -11.1875F, -3.0F, 2.0F, 7.0F, 6.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("part12", CubeListBuilder.create().texOffs(62, 28).addBox(-4.4375F, -16.1875F, -2.0F, 1.0F, 4.0F, 4.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("part13", CubeListBuilder.create().texOffs(31, 200).addBox(-8.4375F, -6.1875F, -5.0F, 2.0F, 21.0F, 10.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("part14", CubeListBuilder.create().texOffs(0, 55).addBox(-5.4375F, 15.8125F, -4.0F, 2.0F, 5.0F, 8.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("part15", CubeListBuilder.create().texOffs(107, 23).addBox(-5.4375F, -12.1875F, -4.0F, 2.0F, 5.0F, 8.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("part16", CubeListBuilder.create().texOffs(184, 20).addBox(-6.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("part17", CubeListBuilder.create().texOffs(155, 148).addBox(-10.4375F, -4.1875F, -2.0F, 2.0F, 17.0F, 4.0F, true), PartPose.ZERO);
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