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

public class AuraHealerModel extends Model {
	private List<ModelPart> toRender = new ArrayList<>();

	public AuraHealerModel(ModelPart part) {
		super(RenderType::entityTranslucent);
//		texWidth = 64;
//		texHeight = 64;
//		bb_main = new ModelPart(this);
		for (int i = 1;i <= 19;i++){
			toRender.add(part.getChild(Integer.toString(i)));
			toRender.get(i-1).setPos(0.0F, 24.0F, 0.0F);
		}

//		bb_main.texOffs(54, 49).addBox(-2.0F, -14.0F, 0.0F, 4.0F, 14.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(50, 21).addBox(-2.0F, -13.0F, -1.0F, 4.0F, 12.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(0, 50).addBox(-3.0F, -14.0F, -1.0F, 1.0F, 11.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(23, 39).addBox(2.0F, -14.0F, -1.0F, 1.0F, 11.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(34, 38).addBox(3.0F, -15.0F, -1.0F, 2.0F, 10.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(0, 35).addBox(-5.0F, -15.0F, -1.0F, 2.0F, 10.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(11, 29).addBox(5.0F, -14.0F, -1.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(12, 42).addBox(-6.0F, -14.0F, -1.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(36, 27).addBox(6.0F, -13.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(21, 19).addBox(-7.0F, -13.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
//		bb_main.texOffs(50, 41).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 2.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(46, 37).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(30, 0).addBox(-8.0F, -12.0F, 0.0F, 16.0F, 4.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(58, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
//		bb_main.texOffs(0, 8).addBox(-1.0F, -10.5F, -2.0F, 2.0F, 6.0F, 5.0F, 0.0F, false);
//		bb_main.texOffs(52, 9).addBox(-2.0F, -11.0F, -2.0F, 1.0F, 5.0F, 5.0F, 0.0F, false);
//		bb_main.texOffs(26, 54).addBox(1.0F, -11.0F, -2.0F, 1.0F, 5.0F, 5.0F, 0.0F, false);
//		bb_main.texOffs(12, 55).addBox(2.0F, -10.5F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
//		bb_main.texOffs(39, 55).addBox(-3.0F, -10.5F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
	}
	public static LayerDefinition createLayer(){
		MeshDefinition definition = new MeshDefinition();
		PartDefinition parts = definition.getRoot();
		parts.addOrReplaceChild("1",new CubeListBuilder().texOffs(54, 49).addBox(-2.0F, -14.0F, 0.0F, 4.0F, 14.0F, 1.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("2",new CubeListBuilder().texOffs(50, 21).addBox(-2.0F, -13.0F, -1.0F, 4.0F, 12.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("3",new CubeListBuilder().texOffs(0, 50).addBox(-3.0F, -14.0F, -1.0F, 1.0F, 11.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("4",new CubeListBuilder().texOffs(23, 39).addBox(2.0F, -14.0F, -1.0F, 1.0F, 11.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("5",new CubeListBuilder().texOffs(34, 38).addBox(3.0F, -15.0F, -1.0F, 2.0F, 10.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("6",new CubeListBuilder().texOffs(0, 35).addBox(-5.0F, -15.0F, -1.0F, 2.0F, 10.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("7",new CubeListBuilder().texOffs(11, 29).addBox(5.0F, -14.0F, -1.0F, 1.0F, 8.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("8",new CubeListBuilder().texOffs(12, 42).addBox(-6.0F, -14.0F, -1.0F, 1.0F, 8.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("9",new CubeListBuilder().texOffs(36, 27).addBox(6.0F, -13.0F, -1.0F, 1.0F, 6.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("10",new CubeListBuilder().texOffs(21, 19).addBox(-7.0F, -13.0F, -1.0F, 1.0F, 6.0F, 3.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("11",new CubeListBuilder().texOffs(50, 41).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 2.0F, 1.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("12",new CubeListBuilder().texOffs(46, 37).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 2.0F, 1.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("13",new CubeListBuilder().texOffs(30, 0).addBox(-8.0F, -12.0F, 0.0F, 16.0F, 4.0F, 1.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("14",new CubeListBuilder().texOffs(58, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("15",new CubeListBuilder().texOffs(0, 8).addBox(-1.0F, -10.5F, -2.0F, 2.0F, 6.0F, 5.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("16",new CubeListBuilder().texOffs(52, 9).addBox(-2.0F, -11.0F, -2.0F, 1.0F, 5.0F, 5.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("17",new CubeListBuilder().texOffs(26, 54).addBox(1.0F, -11.0F, -2.0F, 1.0F, 5.0F, 5.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("18",new CubeListBuilder().texOffs(12, 55).addBox(2.0F, -10.5F, -2.0F, 1.0F, 4.0F, 5.0F,false), PartPose.ZERO);
		parts.addOrReplaceChild("19",new CubeListBuilder().texOffs(39, 55).addBox(-3.0F, -10.5F, -2.0F, 1.0F, 4.0F, 5.0F, false), PartPose.ZERO);
		return LayerDefinition.create(definition,64,64);
	}


	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		toRender.forEach((model)->{
			model.render(matrixStack,buffer,packedLight,packedOverlay);
		});
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}


}