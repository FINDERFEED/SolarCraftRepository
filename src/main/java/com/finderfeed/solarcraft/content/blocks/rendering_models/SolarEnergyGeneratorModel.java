package com.finderfeed.solarcraft.content.blocks.rendering_models;// Made with Blockbench 3.8.4
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

public class SolarEnergyGeneratorModel extends Model {
	private final List<ModelPart> core = new ArrayList<>();
	private final List<ModelPart> ring1 = new ArrayList<>();
	private final List<ModelPart> ring2 = new ArrayList<>();
	private final List<ModelPart> base = new ArrayList<>();


	public SolarEnergyGeneratorModel(ModelPart part) {
		super(RenderType::entityTranslucent);
		for (int i = 1;i <= 3;i++){
			core.add(part.getChild("core"+i));
			core.get(i-1).setPos(0,0,0);
		}
		for (int i = 1;i <= 12;i++){
			ring1.add(part.getChild("ring"+i));
			ring1.get(i-1).setPos(0,0,0);
		}
		for (int i = 1;i <= 12;i++){
			ring2.add(part.getChild("ringsecond"+i));
			ring2.get(i-1).setPos(0,0,0);
			setRotationAngle(ring2.get(i-1), 0.0F, -1.5708F, 0.0F);
		}
		for (int i = 1;i <= 8;i++){
			base.add(part.getChild("base"+i));
			base.get(i-1).setPos(0,24,0);
		}
		for (int i = 1;i <= 5;i++){
			base.add(part.getChild("cube"+i));
			base.get(i+7).setPos(0,24f-9.3f,0);
			setRotationAngle(base.get(i+7), 0.0F, 0.0F, -3.1416F);
		}
//		texWidth = 128;
//		texHeight = 128;
//
//		core = new ModelPart(this);
//		core.setPos(0.0F, 0, 0.0F);
//		core.texOffs(120, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
//		core.texOffs(107, 0).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
//		core.texOffs(93, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
//
//		ring1 = new ModelPart(this);
//		ring1.setPos(0.0F, 0, 0.0F);
//		ring1.texOffs(85, 0).addBox(4.0F, -11.5F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, true);
//		ring1.texOffs(77, 0).addBox(3.0F, -7.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//		ring1.texOffs(77, 4).addBox(2.0F, -6.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//		ring1.texOffs(64, 0).addBox(-2.0F, -5.5F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
//		ring1.texOffs(69, 4).addBox(-3.0F, -6.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring1.texOffs(111, 5).addBox(-4.0F, -7.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring1.texOffs(122, 7).addBox(-5.0F, -11.5F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
//		ring1.texOffs(114, 10).addBox(-4.0F, -12.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring1.texOffs(122, 14).addBox(-3.0F, -13.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring1.texOffs(109, 15).addBox(-2.0F, -14.5F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
//		ring1.texOffs(107, 10).addBox(2.0F, -13.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//		ring1.texOffs(100, 10).addBox(3.0F, -12.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//
//		ring2 = new ModelPart(this);
//		ring2.setPos(0.0F, 0, 0.0F);
//		setRotationAngle(ring2, 0.0F, -1.5708F, 0.0F);
//		ring2.texOffs(93, 8).addBox(5.0F, -3.5F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, true);
//		ring2.texOffs(86, 8).addBox(4.0F, 2.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//		ring2.texOffs(86, 12).addBox(3.0F, 3.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//		ring2.texOffs(109, 19).addBox(-3.0F, 4.5F, -1.0F, 6.0F, 1.0F, 2.0F, 0.0F, false);
//		ring2.texOffs(100, 18).addBox(-4.0F, 3.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring2.texOffs(92, 17).addBox(-5.0F, 2.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring2.texOffs(122, 23).addBox(-6.0F, -3.5F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
//		ring2.texOffs(77, 8).addBox(-5.0F, -4.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring2.texOffs(69, 8).addBox(-4.0F, -5.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
//		ring2.texOffs(104, 24).addBox(-3.0F, -6.5F, -1.0F, 6.0F, 1.0F, 2.0F, 0.0F, false);
//		ring2.texOffs(113, 29).addBox(3.0F, -5.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//		ring2.texOffs(122, 32).addBox(4.0F, -4.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
//
//		base = new ModelPart(this);
//		base.setPos(0.0F, 24.0F, 0.0F);
//		base.texOffs(64, 108).addBox(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F, 0.0F, false);
//		base.texOffs(88, 95).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
//		base.texOffs(104, 73).addBox(-3.0F, -18.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
//		base.texOffs(112, 62).addBox(-2.0F, -22.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
//		base.texOffs(115, 54).addBox(3.0F, -4.0F, -6.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//		base.texOffs(115, 43).addBox(3.0F, -4.0F, 3.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//		base.texOffs(102, 36).addBox(-6.0F, -4.0F, 3.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//		base.texOffs(101, 49).addBox(-6.0F, -4.0F, -6.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//
//		cube_r1 = new ModelPart(this);
//		cube_r1.setPos(0.0F, -9.3F, 0.0F);
//		base.addChild(cube_r1);
//		setRotationAngle(cube_r1, 0.0F, 0.0F, -3.1416F);
//		cube_r1.texOffs(101, 49).addBox(-6.0F, 5.8F, -6.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//		cube_r1.texOffs(102, 36).addBox(-6.0F, 5.8F, 3.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//		cube_r1.texOffs(115, 43).addBox(3.0F, 5.8F, 3.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//		cube_r1.texOffs(115, 54).addBox(3.0F, 5.8F, -6.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
//		cube_r1.texOffs(88, 95).addBox(-5.0F, 6.8F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
	}

	public static LayerDefinition createLayers(){
		MeshDefinition def = new MeshDefinition();
		PartDefinition p = def.getRoot();
		p.addOrReplaceChild("core1", CubeListBuilder.create().texOffs(120, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.ZERO);
		p.addOrReplaceChild("core2", CubeListBuilder.create().texOffs(107, 0).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, false), PartPose.ZERO);
		p.addOrReplaceChild("core3", CubeListBuilder.create().texOffs(93, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, false), PartPose.ZERO);

		p.addOrReplaceChild("ring1",CubeListBuilder.create().texOffs(85, 0).addBox(4.0F, -11.5F, -1.0F, 1.0F, 4.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ring2",CubeListBuilder.create().texOffs(77, 0).addBox(3.0F, -7.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ring3",CubeListBuilder.create().texOffs(77, 4).addBox(2.0F, -6.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ring4",CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -5.5F, -1.0F, 4.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ring5",CubeListBuilder.create().texOffs(69, 4).addBox(-3.0F, -6.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ring6",CubeListBuilder.create().texOffs(111, 5).addBox(-4.0F, -7.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ring7",CubeListBuilder.create().texOffs(122, 7).addBox(-5.0F, -11.5F, -1.0F, 1.0F, 4.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ring8",CubeListBuilder.create().texOffs(114, 10).addBox(-4.0F, -12.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ring9",CubeListBuilder.create().texOffs(122, 14).addBox(-3.0F, -13.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ring10",CubeListBuilder.create().texOffs(109, 15).addBox(-2.0F, -14.5F, -1.0F, 4.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ring11",CubeListBuilder.create().texOffs(107, 10).addBox(2.0F, -13.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ring12",CubeListBuilder.create().texOffs(100, 10).addBox(3.0F, -12.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);

		p.addOrReplaceChild("ringsecond1",CubeListBuilder.create().texOffs(93, 8).addBox(5.0F, -3.5F, -1.0F, 1.0F, 6.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond2",CubeListBuilder.create().texOffs(86, 8).addBox(4.0F, 2.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond3",CubeListBuilder.create().texOffs(86, 12).addBox(3.0F, 3.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond4",CubeListBuilder.create().texOffs(109, 19).addBox(-3.0F, 4.5F, -1.0F, 6.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond5",CubeListBuilder.create().texOffs(100, 18).addBox(-4.0F, 3.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond6",CubeListBuilder.create().texOffs(92, 17).addBox(-5.0F, 2.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond7",CubeListBuilder.create().texOffs(122, 23).addBox(-6.0F, -3.5F, -1.0F, 1.0F, 6.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond8",CubeListBuilder.create().texOffs(77, 8).addBox(-5.0F, -4.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond9",CubeListBuilder.create().texOffs(69, 8).addBox(-4.0F, -5.5F, -1.0F, 1.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond10",CubeListBuilder.create().texOffs(104, 24).addBox(-3.0F, -6.5F, -1.0F, 6.0F, 1.0F, 2.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond11",CubeListBuilder.create().texOffs(113, 29).addBox(3.0F, -5.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);
		p.addOrReplaceChild("ringsecond12",CubeListBuilder.create().texOffs(122, 32).addBox(4.0F, -4.5F, -1.0F, 1.0F, 1.0F, 2.0F, true),PartPose.ZERO);

		p.addOrReplaceChild("base1",CubeListBuilder.create().texOffs(64, 108).addBox(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("base2",CubeListBuilder.create().texOffs(88, 95).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 1.0F, 10.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("base3",CubeListBuilder.create().texOffs(104, 73).addBox(-3.0F, -18.0F, -3.0F, 6.0F, 1.0F, 6.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("base4",CubeListBuilder.create().texOffs(112, 62).addBox(-2.0F, -22.0F, -2.0F, 4.0F, 4.0F, 4.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("base5",CubeListBuilder.create().texOffs(115, 54).addBox(3.0F, -4.0F, -6.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("base6",CubeListBuilder.create().texOffs(115, 43).addBox(3.0F, -4.0F, 3.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("base7",CubeListBuilder.create().texOffs(102, 36).addBox(-6.0F, -4.0F, 3.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("base8",CubeListBuilder.create().texOffs(101, 49).addBox(-6.0F, -4.0F, -6.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);

		p.addOrReplaceChild("cube1",CubeListBuilder.create().texOffs(101, 49).addBox(-6.0F, 5.8F, -6.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("cube2",CubeListBuilder.create().texOffs(102, 36).addBox(-6.0F, 5.8F, 3.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("cube3",CubeListBuilder.create().texOffs(115, 43).addBox(3.0F, 5.8F, 3.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("cube4",CubeListBuilder.create().texOffs(115, 54).addBox(3.0F, 5.8F, -6.0F, 3.0F, 1.0F, 3.0F, false),PartPose.ZERO);
		p.addOrReplaceChild("cube5",CubeListBuilder.create().texOffs(88, 95).addBox(-5.0F, 6.8F, -5.0F, 10.0F, 1.0F, 10.0F, false),PartPose.ZERO);
		return LayerDefinition.create(def,128,128);
	}

	public void renderCore(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		core.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
	}
	public void renderRing1(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		ring1.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
	}
	public void renderRing2(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		ring2.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
	}
	public void renderBase(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		base.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		core.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
		ring1.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
		ring2.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
		base.forEach((model)->{model.render(matrixStack, buffer, packedLight, packedOverlay);});
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}