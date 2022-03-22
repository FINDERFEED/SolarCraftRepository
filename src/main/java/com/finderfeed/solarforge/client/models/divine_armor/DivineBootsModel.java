package com.finderfeed.solarforge.client.models.divine_armor;


import com.finderfeed.solarforge.SolarForge;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class DivineBootsModel extends HumanoidModel<LivingEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarForge.MOD_ID, "divine_boots"), "main");

    public DivineBootsModel(ModelPart root) {
        super(root, RenderType::entityTranslucent);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(),PartPose.ZERO);
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(),PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(),PartPose.offset(-4.0F, 2.0F, 0.0F));

        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(),PartPose.offset(4.0F, 2.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(),PartPose.offset(0.0F, 0.0F, 0.0F));



        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(),PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition cube_r1 = rightLeg.addOrReplaceChild("cube_r1", CubeListBuilder.create(),PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3883F, 0.0F, 0.0F));

        PartDefinition rightBoot = rightLeg.addOrReplaceChild("rightBoot", CubeListBuilder.create().texOffs(28, 33).addBox(-2.025F, 8.05F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = rightBoot.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(48, 44).addBox(-2.525F, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(21, 20).addBox(-2.525F, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 60).addBox(1.525F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(47, 12).addBox(-2.0F, 0.0F, -2.55F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition cube_r3 = leftLeg.addOrReplaceChild("cube_r3", CubeListBuilder.create(),PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3883F, 0.0F, 0.0F));

        PartDefinition leftBoot = leftLeg.addOrReplaceChild("leftBoot", CubeListBuilder.create().texOffs(42, 0).addBox(-1.975F, 8.05F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = leftBoot.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(55, 0).addBox(1.525F, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(7, 60).addBox(1.525F, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


}
