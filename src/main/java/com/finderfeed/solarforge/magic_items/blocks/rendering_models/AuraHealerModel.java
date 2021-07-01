package com.finderfeed.solarforge.magic_items.blocks.rendering_models;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class AuraHealerModel extends Model {
	private final ModelRenderer bb_main;

	public AuraHealerModel() {
		super(RenderType::text);
		texWidth = 64;
		texHeight = 64;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(54, 49).addBox(-2.0F, -14.0F, 0.0F, 4.0F, 14.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(50, 21).addBox(-2.0F, -13.0F, -1.0F, 4.0F, 12.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(0, 50).addBox(-3.0F, -14.0F, -1.0F, 1.0F, 11.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(23, 39).addBox(2.0F, -14.0F, -1.0F, 1.0F, 11.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(34, 38).addBox(3.0F, -15.0F, -1.0F, 2.0F, 10.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(0, 35).addBox(-5.0F, -15.0F, -1.0F, 2.0F, 10.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(11, 29).addBox(5.0F, -14.0F, -1.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(12, 42).addBox(-6.0F, -14.0F, -1.0F, 1.0F, 8.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(36, 27).addBox(6.0F, -13.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(21, 19).addBox(-7.0F, -13.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
		bb_main.texOffs(50, 41).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 2.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(46, 37).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(30, 0).addBox(-8.0F, -12.0F, 0.0F, 16.0F, 4.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(58, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 8).addBox(-1.0F, -10.5F, -2.0F, 2.0F, 6.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(52, 9).addBox(-2.0F, -11.0F, -2.0F, 1.0F, 5.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(26, 54).addBox(1.0F, -11.0F, -2.0F, 1.0F, 5.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(12, 55).addBox(2.0F, -10.5F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(39, 55).addBox(-3.0F, -10.5F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
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