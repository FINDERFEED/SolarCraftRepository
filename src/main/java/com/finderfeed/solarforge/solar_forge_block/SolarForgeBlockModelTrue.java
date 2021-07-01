package com.finderfeed.solarforge.solar_forge_block;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SolarForgeBlockModelTrue extends Model {
	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;

	public SolarForgeBlockModelTrue() {
		super(RenderType::text);
		this.texWidth = 256;
		this.texHeight = 256;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 179).addBox(-16.0F, -7.0F, -16.0F, 32.0F, 3.0F, 32.0F, 0.0F, false);
		bb_main.texOffs(0, 137).addBox(-9.0F, -17.0F, -9.0F, 18.0F, 10.0F, 18.0F, 0.0F, false);
		bb_main.texOffs(199, 10).addBox(-5.0F, -27.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
		bb_main.texOffs(156, 41).addBox(-12.0F, -35.0F, -12.0F, 24.0F, 8.0F, 24.0F, 0.0F, false);
		bb_main.texOffs(102, 0).addBox(6.0F, -40.0F, -6.0F, 4.0F, 5.0F, 12.0F, 0.0F, false);
		bb_main.texOffs(224, 168).addBox(-10.0F, -40.0F, -6.0F, 4.0F, 5.0F, 12.0F, 0.0F, false);
		bb_main.texOffs(162, 141).addBox(-6.0F, -40.0F, -10.0F, 12.0F, 5.0F, 4.0F, 0.0F, false);
		bb_main.texOffs(105, 26).addBox(-6.0F, -40.0F, 6.0F, 12.0F, 5.0F, 4.0F, 0.0F, false);
		bb_main.texOffs(35, 0).addBox(-8.0F, -52.0F, -4.0F, 4.0F, 12.0F, 8.0F, 0.0F, false);
		bb_main.texOffs(48, 50).addBox(4.0F, -52.0F, -4.0F, 4.0F, 12.0F, 8.0F, 0.0F, false);
		bb_main.texOffs(0, 47).addBox(-4.0F, -52.0F, 4.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		bb_main.texOffs(231, 141).addBox(-4.0F, -52.0F, -8.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, 0.0F, 0.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, -0.7854F, 0.0F);
		cube_r1.texOffs(125, 200).addBox(-8.0F, -4.0F, -24.0F, 16.0F, 4.0F, 48.0F, 0.0F, false);
		cube_r1.texOffs(0, 234).addBox(-24.0F, -4.0F, -8.0F, 48.0F, 4.0F, 16.0F, 0.0F, false);
	}



	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}