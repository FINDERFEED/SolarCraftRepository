package com.finderfeed.solarcraft.client.models.divine_armor;


import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class DivineHelmetModel extends HumanoidModel<LivingEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarCraft.MOD_ID, "divine_helmet"), "main");


    public DivineHelmetModel(ModelPart root) {
        super(root, RenderType::entityTranslucent);

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(),PartPose.ZERO);
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(16, 33).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(26, 20).addBox(-3.0F, -10.0F, -1.025F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(38, 33).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(77, 19).addBox(-1.5F, -10.0F, -5.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(65, 13).addBox(-2.0F, -11.0F, 5.0F, 4.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 1).addBox(4.0F, -8.0F, -5.0F, 1.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(55, 38).addBox(-4.0F, -8.0F, -6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 25).addBox(-1.0F, -6.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-4.0F, -4.0F, -5.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(1.0F, -4.0F, -5.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(77, 39).addBox(-4.0F, -6.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 37).addBox(2.0F, -6.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-5.0F, -2.0F, -3.75F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(53, 57).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(),PartPose.offset(-4.0F, 2.0F, 0.0F));
        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(),PartPose.offset(4.0F, 2.0F, 0.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(),PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(),PartPose.offset(-2.0F, 12.0F, 0.0F));
        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(),PartPose.offset(2.0F, 12.0F, 0.0F));


        return LayerDefinition.create(meshdefinition, 128, 128);
    }

}