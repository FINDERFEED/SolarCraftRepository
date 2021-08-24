package com.finderfeed.solarforge.magic_items.blocks.rendering_models;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

import java.util.ArrayList;
import java.util.List;

public class RadiantPortal extends Model {

	List<ModelPart> toRender = new ArrayList<>();

//	private final ModelPart bone;
//	private final ModelPart cube_r1;
//	private final ModelPart cube_r2;
//	private final ModelPart cube_r3;
//	private final ModelPart cube_r4;
//	private final ModelPart cube_r5;
//	private final ModelPart cube_r6;
//	private final ModelPart cube_r7;
//	private final ModelPart cube_r8;
//	private final ModelPart cube_r9;
//	private final ModelPart cube_r10;
//	private final ModelPart cube_r11;
//	private final ModelPart cube_r12;
//	private final ModelPart cube_r13;
//	private final ModelPart cube_r14;


	public static LayerDefinition createLayers() {
		MeshDefinition definition = new MeshDefinition();
		PartDefinition parts = definition.getRoot();
		parts.addOrReplaceChild("1",new CubeListBuilder().texOffs(80, 0).addBox(-19.0F, -4.0F, 8.0F, 20.0F, 2.0F, 4.0F, true), PartPose.ZERO);
		parts.addOrReplaceChild("2",new CubeListBuilder().texOffs(78, 6).addBox(-19.0F, -2.0F, 7.5F, 20.0F, 2.0F, 5.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("3",new CubeListBuilder().texOffs(82, 44).addBox(-19.0F, -6.0F, 8.5F, 20.0F, 2.0F, 3.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("4",new CubeListBuilder().texOffs(78, 49).addBox(-19.0F, -50.2F, 7.5F, 20.0F, 3.0F, 5.0F, false), PartPose.ZERO);
		parts.addOrReplaceChild("5",new CubeListBuilder().texOffs(84, 57).addBox(-19.0F, -47.2F, 9.0F, 20.0F, 1.0F, 2.0F, false), PartPose.ZERO);

		parts.addOrReplaceChild("6",new CubeListBuilder().texOffs(78, 119).addBox(-34.0F, -30.1F, -2.5F, 20.0F, 3.0F, 5.0F,  true), PartPose.ZERO);
		parts.addOrReplaceChild("7",new CubeListBuilder().texOffs(84, 100).addBox(-34.0F, -27.1F, -1.0F, 20.0F, 1.0F, 2.0F,  true), PartPose.ZERO);

		parts.addOrReplaceChild("8",new CubeListBuilder().texOffs(78, 111).addBox(11.25F, 8.325F, -2.5F, 20.0F, 3.0F, 5.0F,  true), PartPose.ZERO);
		parts.addOrReplaceChild("9",new CubeListBuilder().texOffs(84, 97).addBox(11.25F, 7.325F, -1.0F, 20.0F, 1.0F, 2.0F,  true), PartPose.ZERO);

		parts.addOrReplaceChild("10",new CubeListBuilder().texOffs(78, 103).addBox(-4.0F, -3.0F, -2.5F, 20.0F, 3.0F, 5.0F,  true), PartPose.ZERO);
		parts.addOrReplaceChild("11",new CubeListBuilder().texOffs(84, 94).addBox(-4.0F, -4.0F, -1.0F, 20.0F, 1.0F, 2.0F,  true), PartPose.ZERO);

		parts.addOrReplaceChild("12",new CubeListBuilder().texOffs(84, 91).addBox(14.0F, -27.1F, -1.0F, 20.0F, 1.0F, 2.0F,  false), PartPose.ZERO);
		parts.addOrReplaceChild("13",new CubeListBuilder().texOffs(78, 83).addBox(14.0F, -30.1F, -2.5F, 20.0F, 3.0F, 5.0F,  false), PartPose.ZERO);

		parts.addOrReplaceChild("14",new CubeListBuilder().texOffs(84, 79).addBox(-31.25F, 7.325F, -1.0F, 20.0F, 1.0F, 2.0F,  false), PartPose.ZERO);
		parts.addOrReplaceChild("15",new CubeListBuilder().texOffs(78, 71).addBox(-31.25F, 8.325F, -2.5F, 20.0F, 3.0F, 5.0F,  false), PartPose.ZERO);

		parts.addOrReplaceChild("16",new CubeListBuilder().texOffs(84, 68).addBox(-16.0F, -4.0F, -1.0F, 20.0F, 1.0F, 2.0F,  false), PartPose.ZERO);
		parts.addOrReplaceChild("17",new CubeListBuilder().texOffs(78, 60).addBox(-16.0F, -3.0F, -2.5F, 20.0F, 3.0F, 5.0F,  false), PartPose.ZERO);

//		parts.addOrReplaceChild("18",new CubeListBuilder().texOffs(84, 68).addBox(-16.0F, -4.0F, -1.0F, 20.0F, 1.0F, 2.0F,  false), PartPose.ZERO);
//		parts.addOrReplaceChild("19",new CubeListBuilder().texOffs(78, 60).addBox(-16.0F, -3.0F, -2.5F, 20.0F, 3.0F, 5.0F,  false), PartPose.ZERO);

		parts.addOrReplaceChild("18",new CubeListBuilder().texOffs(74, 35).addBox(-1.9893F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F, false), PartPose.ZERO);

		parts.addOrReplaceChild("19",new CubeListBuilder().texOffs(102, 37).addBox(-5.0107F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F,  true), PartPose.ZERO);

		parts.addOrReplaceChild("20",new CubeListBuilder().texOffs(74, 28).addBox(-5.0107F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F,  true), PartPose.ZERO);

		parts.addOrReplaceChild("21",new CubeListBuilder().texOffs(102, 29).addBox(-1.9893F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F,  false), PartPose.ZERO);

		parts.addOrReplaceChild("22",new CubeListBuilder().texOffs(74, 21).addBox(-5.0107F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F,  true), PartPose.ZERO);

		parts.addOrReplaceChild("23",new CubeListBuilder().texOffs(102, 21).addBox(-1.9893F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F,  false), PartPose.ZERO);

		parts.addOrReplaceChild("24",new CubeListBuilder().texOffs(75, 13).addBox(-5.0107F, -2.0107F, -3.0F, 7.0F, 1.0F, 6.0F,  true), PartPose.ZERO);

		parts.addOrReplaceChild("25",new CubeListBuilder().texOffs(102, 13).addBox(-1.9893F, -2.0107F, -3.0F, 7.0F, 1.0F, 6.0F,  false), PartPose.ZERO);
		return LayerDefinition.create(definition,128,128);
	}


	public RadiantPortal(ModelPart part) {
		super(RenderType::text);
		for (int i = 1;i <= 5;i++) {
			ModelPart child = part.getChild(String.valueOf(i));
			toRender.add(child);
			child.setPos(9.0F, 24.0F, -10.0F);
		}
		for (int i = 6;i <= 7;i++) {
			ModelPart child = part.getChild(String.valueOf(i));
			toRender.add(child);
			child.setPos(9.0F+3.65f, 24.0F-5, -10.0F+10f);
			setRotationAngle(child, 0.0F, 0.0F, 0.7854F);
		}
		for (int i = 8;i <= 9;i++) {
			ModelPart child = part.getChild(String.valueOf(i));
			toRender.add(child);
			child.setPos(9.0F+3.65f, 24.0F-5, -10.0F+10f);
			setRotationAngle(child, 0.0F, 0.0F, -1.5708F);
		}
		for (int i = 10;i <= 11;i++) {
			ModelPart child = part.getChild(String.valueOf(i));
			toRender.add(child);
			child.setPos(9.0F+3.65f, 24.0F-5, -10.0F+10f);
			setRotationAngle(child, 0.0F, 0.0F, -0.7854F);
		}
		for (int i = 12;i <= 13;i++) {
			ModelPart child = part.getChild(String.valueOf(i));
			toRender.add(child);
			child.setPos(9.0F-21.65f, 24.0F-5, -10.0F+10f);
			setRotationAngle(child, 0.0F, 0.0F, -0.7854F);
		}
		for (int i = 14;i <= 15;i++) {
			ModelPart child = part.getChild(String.valueOf(i));
			toRender.add(child);
			child.setPos(9.0F-21.65f, 24.0F-5, -10.0F+10f);
			setRotationAngle(child, 0.0F, 0.0F, 1.5708F);
		}
		for (int i = 16;i <= 17;i++) {
			ModelPart child = part.getChild(String.valueOf(i));
			toRender.add(child);
			child.setPos(9.0F-21.65f, 24.0F-5, -10.0F+10f);
			setRotationAngle(child, 0.0F, 0.0F, 0.7854F);
		}
//		for (int i = 18;i <= 19;i++) {
//			ModelPart child = part.getChild(String.valueOf(i));
//			toRender.add(child);
//			child.setPos(9.0F-21.65f, 24.0F-5, -10.0F+10f);
//			setRotationAngle(child, 0.0F, 0.0F,  0.7854F);
//		}
//		bone = new ModelRenderer(this);
//		bone.setPos(9.0F, 24.0F, -10.0F);
//		bone.texOffs(80, 0).addBox(-19.0F, -4.0F, 8.0F, 20.0F, 2.0F, 4.0F, 0.0F, true);
//		bone.texOffs(78, 6).addBox(-19.0F, -2.0F, 7.5F, 20.0F, 2.0F, 5.0F, 0.0F, false);
//		bone.texOffs(82, 44).addBox(-19.0F, -6.0F, 8.5F, 20.0F, 2.0F, 3.0F, 0.0F, false);
//		bone.texOffs(78, 49).addBox(-19.0F, -50.2F, 7.5F, 20.0F, 3.0F, 5.0F, 0.0F, false);
//		bone.texOffs(84, 57).addBox(-19.0F, -47.2F, 9.0F, 20.0F, 1.0F, 2.0F, 0.0F, false);
//
//		cube_r1 = new ModelRenderer(this);
//		cube_r1.setPos(3.65F, -5.0F, 10.0F);
//		bone.addChild(cube_r1);
//		setRotationAngle(cube_r1, 0.0F, 0.0F, 0.7854F);
//		cube_r1.texOffs(78, 119).addBox(-34.0F, -30.1F, -2.5F, 20.0F, 3.0F, 5.0F, 0.0F, true);67
//		cube_r1.texOffs(84, 100).addBox(-34.0F, -27.1F, -1.0F, 20.0F, 1.0F, 2.0F, 0.0F, true);
//
//		cube_r2 = new ModelRenderer(this);
//		cube_r2.setPos(3.65F, -5.0F, 10.0F);
//		bone.addChild(cube_r2);
//		setRotationAngle(cube_r2, 0.0F, 0.0F, -1.5708F);
//		cube_r2.texOffs(78, 111).addBox(11.25F, 8.325F, -2.5F, 20.0F, 3.0F, 5.0F, 0.0F, true);89
//		cube_r2.texOffs(84, 97).addBox(11.25F, 7.325F, -1.0F, 20.0F, 1.0F, 2.0F, 0.0F, true);
//
//		cube_r3 = new ModelRenderer(this);
//		cube_r3.setPos(3.65F, -5.0F, 10.0F);
//		bone.addChild(cube_r3);
//		setRotationAngle(cube_r3, 0.0F, 0.0F, -0.7854F);
//		cube_r3.texOffs(78, 103).addBox(-4.0F, -3.0F, -2.5F, 20.0F, 3.0F, 5.0F, 0.0F, true);1011
//		cube_r3.texOffs(84, 94).addBox(-4.0F, -4.0F, -1.0F, 20.0F, 1.0F, 2.0F, 0.0F, true);
//
//		cube_r4 = new ModelRenderer(this);
//		cube_r4.setPos(-21.65F, -5.0F, 10.0F);
//		bone.addChild(cube_r4);
//		setRotationAngle(cube_r4, 0.0F, 0.0F, -0.7854F);
//		cube_r4.texOffs(84, 91).addBox(14.0F, -27.1F, -1.0F, 20.0F, 1.0F, 2.0F, 0.0F, false);1213
//		cube_r4.texOffs(78, 83).addBox(14.0F, -30.1F, -2.5F, 20.0F, 3.0F, 5.0F, 0.0F, false);
//
//		cube_r5 = new ModelRenderer(this);
//		cube_r5.setPos(-21.65F, -5.0F, 10.0F);
//		bone.addChild(cube_r5);
//		setRotationAngle(cube_r5, 0.0F, 0.0F, 1.5708F);
//		cube_r5.texOffs(84, 79).addBox(-31.25F, 7.325F, -1.0F, 20.0F, 1.0F, 2.0F, 0.0F, false);1415
//		cube_r5.texOffs(78, 71).addBox(-31.25F, 8.325F, -2.5F, 20.0F, 3.0F, 5.0F, 0.0F, false);
//
//		cube_r6 = new ModelRenderer(this);
//		cube_r6.setPos(-21.65F, -5.0F, 10.0F);
//		bone.addChild(cube_r6);
//		setRotationAngle(cube_r6, 0.0F, 0.0F, 0.7854F);
//		cube_r6.texOffs(84, 68).addBox(-16.0F, -4.0F, -1.0F, 20.0F, 1.0F, 2.0F, 0.0F, false);1617
//		cube_r6.texOffs(78, 60).addBox(-16.0F, -3.0F, -2.5F, 20.0F, 3.0F, 5.0F, 0.0F, false);
//
		ModelPart child = part.getChild("18");
		toRender.add(child);
		child.setPos(9.0F-31.6793F, 24.0F-25.8787F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  -2.3562F);
//		cube_r7 = new ModelRenderer(this);
//		cube_r7.setPos(-31.6793F, -25.8787F, 10.0F);
//		bone.addChild(cube_r7);
//		setRotationAngle(cube_r7, 0.0F, 0.0F, -2.3562F);
//		cube_r7.texOffs(74, 35).addBox(-1.9893F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
//
		child = part.getChild("19");
		toRender.add(child);
		child.setPos(9.0F-31.6793F, 24.0F-25.8787F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  -0.7854F);
//		cube_r8 = new ModelRenderer(this);
//		cube_r8.setPos(-31.6793F, -25.8787F, 10.0F);
//		bone.addChild(cube_r8);
//		setRotationAngle(cube_r8, 0.0F, 0.0F, -0.7854F);
//		cube_r8.texOffs(102, 37).addBox(-5.0107F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, true);<--
//
		child = part.getChild("20");
		toRender.add(child);
		child.setPos(9.0F+13.6793F, 24.0F-25.8787F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  2.3562F);
//		cube_r9 = new ModelRenderer(this);
//		cube_r9.setPos(13.6793F, -25.8787F, 10.0F);
//		bone.addChild(cube_r9);
//		setRotationAngle(cube_r9, 0.0F, 0.0F, 2.3562F);
//		cube_r9.texOffs(74, 28).addBox(-5.0107F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, true);
//
		child = part.getChild("21");
		toRender.add(child);
		child.setPos(9.0F+13.6793F, 24.0F-25.8787F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  0.7854F);
//		cube_r10 = new ModelRenderer(this);
//		cube_r10.setPos(13.6793F, -25.8787F, 10.0F);
//		bone.addChild(cube_r10);
//		setRotationAngle(cube_r10, 0.0F, 0.0F, 0.7854F);
//		cube_r10.texOffs(102, 29).addBox(-1.9893F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
//
		child = part.getChild("22");
		toRender.add(child);
		child.setPos(9.0F-9.0707F, 24.0F-48.8787F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  0.7854F);
//		cube_r11 = new ModelRenderer(this);
//		cube_r11.setPos(-9.0707F, -48.8787F, 10.0F);
//		bone.addChild(cube_r11);
//		setRotationAngle(cube_r11, 0.0F, 0.0F, 0.7854F);
//		cube_r11.texOffs(74, 21).addBox(-5.0107F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, true);
//
		child = part.getChild("23");
		toRender.add(child);
		child.setPos(9.0F-9.0707F, 24.0F-48.8787F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  -0.7854F);
//		cube_r12 = new ModelRenderer(this);
//		cube_r12.setPos(-9.0707F, -48.8787F, 10.0F);
//		bone.addChild(cube_r12);
//		setRotationAngle(cube_r12, 0.0F, 0.0F, -0.7854F);
//		cube_r12.texOffs(102, 21).addBox(-1.9893F, 1.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
//
		child = part.getChild("24");
		toRender.add(child);
		child.setPos(9.0F-9.0707F, 24.0F-3.1213F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  -0.7854F);
//		cube_r13 = new ModelRenderer(this);
//		cube_r13.setPos(-9.0707F, -3.1213F, 10.0F);
//		bone.addChild(cube_r13);
//		setRotationAngle(cube_r13, 0.0F, 0.0F, -0.7854F);
//		cube_r13.texOffs(75, 13).addBox(-5.0107F, -2.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, true);
//
		child = part.getChild("25");
		toRender.add(child);
		child.setPos(9.0F-9.0707F, 24.0F-3.1213F, -10.0F+10f);
		setRotationAngle(child, 0.0F, 0.0F,  0.7854F);
//		cube_r14 = new ModelRenderer(this);
//		cube_r14.setPos(-9.0707F, -3.1213F, 10.0F);
//		bone.addChild(cube_r14);
//		setRotationAngle(cube_r14, 0.0F, 0.0F, 0.7854F);
//		cube_r14.texOffs(102, 13).addBox(-1.9893F, -2.0107F, -3.0F, 7.0F, 1.0F, 6.0F, 0.0F, false);
	}




	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		toRender.forEach((model)->{
			model.render(matrixStack,buffer,packedLight,packedOverlay);
		});
//		toRender.get(0).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(1).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(2).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(3).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(4).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(5).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(6).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(7).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(8).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(9).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(10).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(11).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(12).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(13).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(14).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(15).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(16).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(17).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(18).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(19).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(20).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(21).render(matrixStack,buffer,packedLight,packedOverlay);
//
//		toRender.get(21).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(22).render(matrixStack,buffer,packedLight,packedOverlay);
//
//		toRender.get(23).render(matrixStack,buffer,packedLight,packedOverlay);
//		toRender.get(24).render(matrixStack,buffer,packedLight,packedOverlay);
		//toRender.get(25).render(matrixStack,buffer,packedLight,packedOverlay);
		//toRender.get(26).render(matrixStack,buffer,packedLight,packedOverlay);

	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}