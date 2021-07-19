package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SolarForgePetalsTrue extends Model {
	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;

	public SolarForgePetalsTrue() {
		super(RenderType::text);
		this.texWidth = 256;
		this.texHeight = 256;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(9.5625F, -22.8125F, 0.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -0.2618F);
		cube_r1.texOffs(184, 20).addBox(4.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, 0.0F, false);
		cube_r1.texOffs(155, 148).addBox(8.4375F, -4.1875F, -2.0F, 2.0F, 17.0F, 4.0F, 0.0F, false);
		cube_r1.texOffs(25, 16).addBox(5.4375F, 12.8125F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, false);
		cube_r1.texOffs(86, 101).addBox(5.4375F, -11.1875F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, false);
		cube_r1.texOffs(62, 28).addBox(3.4375F, -16.1875F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
		cube_r1.texOffs(31, 200).addBox(6.4375F, -6.1875F, -5.0F, 2.0F, 21.0F, 10.0F, 0.0F, false);
		cube_r1.texOffs(0, 55).addBox(3.4375F, 15.8125F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, false);
		cube_r1.texOffs(107, 23).addBox(3.4375F, -12.1875F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, false);
		cube_r1.texOffs(184, 20).addBox(4.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-9.5625F, -22.8125F, 0.0F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.2618F);
		cube_r2.texOffs(25, 16).addBox(-7.4375F, 12.8125F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, true);
		cube_r2.texOffs(86, 101).addBox(-7.4375F, -11.1875F, -3.0F, 2.0F, 7.0F, 6.0F, 0.0F, true);
		cube_r2.texOffs(62, 28).addBox(-4.4375F, -16.1875F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, true);
		cube_r2.texOffs(31, 200).addBox(-8.4375F, -6.1875F, -5.0F, 2.0F, 21.0F, 10.0F, 0.0F, true);
		cube_r2.texOffs(0, 55).addBox(-5.4375F, 15.8125F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, true);
		cube_r2.texOffs(107, 23).addBox(-5.4375F, -12.1875F, -4.0F, 2.0F, 5.0F, 8.0F, 0.0F, true);
		cube_r2.texOffs(184, 20).addBox(-6.4375F, -7.1875F, -7.0F, 2.0F, 23.0F, 14.0F, 0.0F, true);
		cube_r2.texOffs(155, 148).addBox(-10.4375F, -4.1875F, -2.0F, 2.0F, 17.0F, 4.0F, 0.0F, true);
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