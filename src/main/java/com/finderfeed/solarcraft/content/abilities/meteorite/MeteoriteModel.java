package com.finderfeed.solarcraft.content.abilities.meteorite;

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

public class MeteoriteModel extends Model {

    public ModelPart meteorite;
    public static final String METEORITE = "meteorite";


    public MeteoriteModel(ModelPart part){
        super(RenderType::text);
        this.meteorite = part.getChild(METEORITE);
    }

    public static LayerDefinition createLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition part = meshDefinition.getRoot();
        part.addOrReplaceChild(METEORITE, CubeListBuilder.create().addBox(-32,-32,-32,64,64,64), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition,64,64);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, float v, float v1, float v2, float v3) {
        meteorite.render(poseStack,vertexConsumer,i,i1,v,v1,v2,v3);
    }
}
