package com.finderfeed.solarforge.magic_items.items.item_models;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;

public class SolarGodShield extends Model {
	private final ModelPart bb_main;
	private final ModelPart cube_r1;

	public SolarGodShield() {
		super(RenderType::text);
		this.texWidth = 128;
		this.texHeight = 128;

		bb_main = new ModelPart(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(19, 4).addBox(-2.0F, -23.0F, 0.0F, 4.0F, 18.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(48, 10).addBox(-4.0F, -24.0F, 0.5F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(69, 3).addBox(2.0F, -24.0F, 0.5F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(78, 4).addBox(4.0F, -25.0F, 1.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(60, 3).addBox(-6.0F, -25.0F, 1.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(86, 4).addBox(6.0F, -24.0F, 1.5F, 2.0F, 16.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(8, 5).addBox(-8.0F, -24.0F, 1.5F, 2.0F, 16.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(33, 3).addBox(-2.0F, -18.0F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r1 = new ModelPart(this);
		cube_r1.setPos(0.0F, 0.0F, 0.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 1.5708F, 0.0F);
		cube_r1.texOffs(33, 17).addBox(-6.0F, -17.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
		cube_r1.texOffs(48, 3).addBox(-5.0F, -17.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(33, 10).addBox(-5.0F, -17.0F, 2.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
	}



	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}