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

public class DivineBootsModel extends HumanoidModel<LivingEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarCraft.MOD_ID, "divine_boots"), "main");

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
