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

        float d = 0.1f;
        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(),PartPose.offset(-2.0F, 12.0F, 0.0F));


        PartDefinition rightBoot = rightLeg.addOrReplaceChild("rightBoot", CubeListBuilder.create().texOffs(0, 85).addBox(-3.0F+d, 8.05F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = rightBoot.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(104, 54).addBox(-3.525F+d, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(93, 72).addBox(-3.525F+d, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(),PartPose.offset(2.0F, 12.0F, 0.0F));


        PartDefinition leftBoot = leftLeg.addOrReplaceChild("leftBoot", CubeListBuilder.create().texOffs(80, 0).addBox(-2.025F-d, 8.05F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = leftBoot.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(118, 1).addBox(2.525F-d, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(118, 106).addBox(2.525F-d, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


}
//    public static LayerDefinition createBodyLayer() {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition partdefinition = meshdefinition.getRoot();
//        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(),PartPose.ZERO);
//        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(),PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(),PartPose.offset(-4.0F, 2.0F, 0.0F));
//
//        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(),PartPose.offset(4.0F, 2.0F, 0.0F));
//
//        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(),PartPose.offset(0.0F, 0.0F, 0.0F));
//
//
//
//        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(),PartPose.offset(-2.0F, 12.0F, 0.0F));
//
//        PartDefinition cube_r1 = rightLeg.addOrReplaceChild("cube_r1", CubeListBuilder.create(),PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3883F, 0.0F, 0.0F));
//
//        PartDefinition rightBoot = rightLeg.addOrReplaceChild("rightBoot", CubeListBuilder.create().texOffs(28, 33).addBox(-2.025F, 8.05F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition cube_r2 = rightBoot.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(48, 44).addBox(-2.525F, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
//                .texOffs(21, 20).addBox(-2.525F, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
//
//        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 60).addBox(1.525F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
//                .texOffs(47, 12).addBox(-2.0F, 0.0F, -2.55F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));
//
//        PartDefinition cube_r3 = leftLeg.addOrReplaceChild("cube_r3", CubeListBuilder.create(),PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3883F, 0.0F, 0.0F));
//
//        PartDefinition leftBoot = leftLeg.addOrReplaceChild("leftBoot", CubeListBuilder.create().texOffs(42, 0).addBox(-1.975F, 8.05F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition cube_r4 = leftBoot.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(55, 0).addBox(1.525F, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
//                .texOffs(7, 60).addBox(1.525F, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
//
//        return LayerDefinition.create(meshdefinition, 128, 128);
//    }

//    public static LayerDefinition createBodyLayer() {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition partdefinition = meshdefinition.getRoot();
//
//        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
//                .texOffs(16, 33).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
//                .texOffs(26, 20).addBox(-3.0F, -10.0F, -1.025F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
//                .texOffs(38, 33).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
//                .texOffs(77, 19).addBox(-1.5F, -10.0F, -5.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
//                .texOffs(65, 13).addBox(-2.0F, -11.0F, 5.0F, 4.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
//                .texOffs(30, 1).addBox(4.0F, -8.0F, -5.0F, 1.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
//                .texOffs(55, 38).addBox(-4.0F, -8.0F, -6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
//                .texOffs(26, 25).addBox(-1.0F, -6.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 18).addBox(-4.0F, -4.0F, -5.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 9).addBox(1.0F, -4.0F, -5.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
//                .texOffs(77, 39).addBox(-4.0F, -6.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
//                .texOffs(18, 37).addBox(2.0F, -6.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 11).addBox(-5.0F, -2.0F, -3.75F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
//                .texOffs(53, 57).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightArm = partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(33, 44).addBox(-5.0F, -2.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
//                .texOffs(17, 57).addBox(-6.0F, -3.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));
//
//        PartDefinition leftArm = partdefinition.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(55, 25).addBox(2.0F, -3.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 44).addBox(0.0F, -2.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));
//
//        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 43).addBox(-3.0F, 0.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(42, 0).addBox(2.0F, 0.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(28, 33).addBox(1.0F, 4.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 20).addBox(-2.0F, 4.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(41, 33).addBox(-1.0F, 8.0F, -3.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(66, 42).addBox(-2.5F, 3.0F, 2.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(20, 49).addBox(-2.5F, 3.0F, 2.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create().texOffs(69, 24).addBox(-3.0F, 2.0F, 3.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 37).addBox(-4.0F, 1.0F, 4.5F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(77, 47).addBox(-1.0F, 0.0F, 5.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing1 = wings.addOrReplaceChild("rightwing1", CubeListBuilder.create().texOffs(20, 70).addBox(-3.0F, 0.0F, 5.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing2 = wings.addOrReplaceChild("rightwing2", CubeListBuilder.create().texOffs(72, 0).addBox(-5.0F, -1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing3 = wings.addOrReplaceChild("rightwing3", CubeListBuilder.create().texOffs(51, 65).addBox(-7.0F, 0.0F, 4.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing4 = wings.addOrReplaceChild("rightwing4", CubeListBuilder.create().texOffs(71, 59).addBox(-9.0F, 1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing5 = wings.addOrReplaceChild("rightwing5", CubeListBuilder.create().texOffs(31, 60).addBox(-11.0F, -1.0F, 4.5F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing6 = wings.addOrReplaceChild("rightwing6", CubeListBuilder.create().texOffs(6, 73).addBox(-13.0F, -1.0F, 5.5F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing8 = wings.addOrReplaceChild("rightwing8", CubeListBuilder.create().texOffs(77, 15).addBox(-19.0F, -1.0F, 5.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightwing7 = wings.addOrReplaceChild("rightwing7", CubeListBuilder.create().texOffs(28, 74).addBox(-17.0F, 2.0F, 4.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
//                .texOffs(30, 0).addBox(-15.0F, -1.0F, 4.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing1 = wings.addOrReplaceChild("leftwing1", CubeListBuilder.create().texOffs(12, 70).addBox(1.0F, 0.0F, 5.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing2 = wings.addOrReplaceChild("leftwing2", CubeListBuilder.create().texOffs(71, 70).addBox(3.0F, -1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing3 = wings.addOrReplaceChild("leftwing3", CubeListBuilder.create().texOffs(61, 65).addBox(5.0F, 0.0F, 4.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing4 = wings.addOrReplaceChild("leftwing4", CubeListBuilder.create().texOffs(71, 48).addBox(7.0F, 1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing5 = wings.addOrReplaceChild("leftwing5", CubeListBuilder.create().texOffs(41, 60).addBox(9.0F, -1.0F, 4.5F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing6 = wings.addOrReplaceChild("leftwing6", CubeListBuilder.create().texOffs(0, 73).addBox(11.0F, -1.0F, 5.5F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing8 = wings.addOrReplaceChild("leftwing8", CubeListBuilder.create().texOffs(75, 11).addBox(15.0F, -1.0F, 5.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition leftwing7 = wings.addOrReplaceChild("leftwing7", CubeListBuilder.create().texOffs(38, 74).addBox(15.0F, 2.0F, 4.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 0).addBox(13.0F, -1.0F, 4.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition rightLeg = partdefinition.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(60, 0).addBox(-2.525F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
//                .texOffs(53, 44).addBox(-2.0F, 0.0F, -2.55F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
//
//        PartDefinition cube_r1 = rightLeg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(73, 32).addBox(-1.0F, 2.0F, -0.3F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3883F, 0.0F, 0.0F));
//
//        PartDefinition rightBoot = rightLeg.addOrReplaceChild("rightBoot", CubeListBuilder.create().texOffs(0, 85).addBox(-3.0F, 8.05F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition cube_r2 = rightBoot.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(104, 54).addBox(-3.525F, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
//                .texOffs(93, 72).addBox(-3.525F, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
//
//        PartDefinition leftLeg = partdefinition.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 60).addBox(1.525F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
//                .texOffs(47, 12).addBox(-2.0F, 0.0F, -2.55F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));
//
//        PartDefinition cube_r3 = leftLeg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, 2.0F, -0.3F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3883F, 0.0F, 0.0F));
//
//        PartDefinition leftBoot = leftLeg.addOrReplaceChild("leftBoot", CubeListBuilder.create().texOffs(80, 0).addBox(-2.025F, 8.05F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//        PartDefinition cube_r4 = leftBoot.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(118, 1).addBox(2.525F, 8.675F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
//                .texOffs(118, 106).addBox(2.525F, 9.675F, -4.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
//
//        return LayerDefinition.create(meshdefinition, 128, 128);
//    }