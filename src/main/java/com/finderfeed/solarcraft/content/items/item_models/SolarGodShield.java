package com.finderfeed.solarcraft.content.items.item_models;// Made with Blockbench 3.8.4
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

public class SolarGodShield extends Model {
	private final List<ModelPart> bb_main = new ArrayList<>();
	//private final ModelPart cube_r1;

	public SolarGodShield(ModelPart part) {
		super(RenderType::text);
		for (int i = 1; i <= 8;i++){
			bb_main.add(part.getChild(Integer.toString(i)));
			bb_main.get(i-1).setPos(0,24,0);
		}
		for (int i = 9; i <= 11;i++){
			bb_main.add(part.getChild(Integer.toString(i)));
			bb_main.get(i-1).setPos(0,24,0);
			setRotationAngle(bb_main.get(i-1), 0.0F, 1.5708F, 0.0F);
		}
//		this.texWidth = 128;
//		this.texHeight = 128;
//
//		bb_main = new ModelPart(this);
//		bb_main.setPos(0.0F, 24.0F, 0.0F);
//		bb_main.texOffs(19, 4).addBox(-2.0F, -23.0F, 0.0F, 4.0F, 18.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(48, 10).addBox(-4.0F, -24.0F, 0.5F, 2.0F, 18.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(69, 3).addBox(2.0F, -24.0F, 0.5F, 2.0F, 18.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(78, 4).addBox(4.0F, -25.0F, 1.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(60, 3).addBox(-6.0F, -25.0F, 1.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(86, 4).addBox(6.0F, -24.0F, 1.5F, 2.0F, 16.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(8, 5).addBox(-8.0F, -24.0F, 1.5F, 2.0F, 16.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(33, 3).addBox(-2.0F, -18.0F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
//
//		cube_r1 = new ModelPart(this);
//		cube_r1.setPos(0.0F, 0.0F, 0.0F);
//		bb_main.addChild(cube_r1);
//		setRotationAngle(cube_r1, 0.0F, 1.5708F, 0.0F);
//		cube_r1.texOffs(33, 17).addBox(-6.0F, -17.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
//		cube_r1.texOffs(48, 3).addBox(-5.0F, -17.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
//		cube_r1.texOffs(33, 10).addBox(-5.0F, -17.0F, 2.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
	}

	public static LayerDefinition createLayers(){
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition part = mesh.getRoot();
		part.addOrReplaceChild("1", CubeListBuilder.create().texOffs(19, 4).addBox(-2.0F, -23.0F, 0.0F, 4.0F, 18.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("2", CubeListBuilder.create().texOffs(48, 10).addBox(-4.0F, -24.0F, 0.5F, 2.0F, 18.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("3", CubeListBuilder.create().texOffs(69, 3).addBox(2.0F, -24.0F, 0.5F, 2.0F, 18.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("4", CubeListBuilder.create().texOffs(78, 4).addBox(4.0F, -25.0F, 1.0F, 2.0F, 18.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("5", CubeListBuilder.create().texOffs(60, 3).addBox(-6.0F, -25.0F, 1.0F, 2.0F, 18.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("6", CubeListBuilder.create().texOffs(86, 4).addBox(6.0F, -24.0F, 1.5F, 2.0F, 16.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("7", CubeListBuilder.create().texOffs(8, 5).addBox(-8.0F, -24.0F, 1.5F, 2.0F, 16.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("8", CubeListBuilder.create().texOffs(33, 3).addBox(-2.0F, -18.0F, -1.0F, 4.0F, 4.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("9", CubeListBuilder.create().texOffs(33, 17).addBox(-6.0F, -17.0F, -3.0F, 1.0F, 4.0F, 6.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("10", CubeListBuilder.create().texOffs(48, 3).addBox(-5.0F, -17.0F, -3.0F, 4.0F, 4.0F, 1.0F, false), PartPose.ZERO);
		part.addOrReplaceChild("11", CubeListBuilder.create().texOffs(33, 10).addBox(-5.0F, -17.0F, 2.0F, 4.0F, 4.0F, 1.0F, false), PartPose.ZERO);
		return LayerDefinition.create(mesh,128,128);
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