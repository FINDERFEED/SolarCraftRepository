package com.finderfeed.solarcraft.client.models.divine_armor;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.items.divine_armor.BaseDivineArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DivineChestplateModel extends HumanoidModel<LivingEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SolarCraft.MOD_ID, "divine_chestplate"), "main");

    private ItemStack stack;


    private ModelPart rightwing1;
    private ModelPart rightwing2;
    private ModelPart rightwing3;
    private ModelPart rightwing4;
    private ModelPart rightwing5;
    private ModelPart rightwing6;
    private ModelPart rightwing7;
    private ModelPart rightwing8;
    private ModelPart leftwing1;
    private ModelPart leftwing2;
    private ModelPart leftwing3;
    private ModelPart leftwing4;
    private ModelPart leftwing5;
    private ModelPart leftwing6;
    private ModelPart leftwing7;
    private ModelPart leftwing8;

    private ModelPart[] rightwings;
    private ModelPart[] leftwings;


    public DivineChestplateModel(ModelPart root, ItemStack itemStack) {
        super(root, RenderType::entityTranslucent);
        this.stack = itemStack;
        ModelPart wings = root.getChild("body").getChild("wings");
        rightwing1 = wings.getChild("rightwing1");
        rightwing2 = wings.getChild("rightwing2");
        rightwing3 = wings.getChild("rightwing3");
        rightwing4 = wings.getChild("rightwing4");
        rightwing5 = wings.getChild("rightwing5");
        rightwing6 = wings.getChild("rightwing6");
        rightwing7 = wings.getChild("rightwing7");
        rightwing8 = wings.getChild("rightwing8");
        leftwing1 = wings.getChild("leftwing1");
        leftwing2 = wings.getChild("leftwing2");
        leftwing3 = wings.getChild("leftwing3");
        leftwing4 = wings.getChild("leftwing4");
        leftwing5 = wings.getChild("leftwing5");
        leftwing6 = wings.getChild("leftwing6");
        leftwing7 = wings.getChild("leftwing7");
        leftwing8 = wings.getChild("leftwing8");
        rightwings = new ModelPart[]{rightwing1, rightwing2, rightwing3, rightwing4,
                rightwing5,rightwing6,rightwing7,rightwing8};
        leftwings = new ModelPart[]{leftwing1, leftwing2, leftwing3, leftwing4,
                leftwing5,leftwing6,leftwing7,leftwing8};
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(),PartPose.ZERO);
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(),PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(33, 44)
                .addBox(-5.0F+1, -2.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(17, 57)
                .addBox(-6.0F+1, -3.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(55, 25)
                .addBox(2.0F-1, -3.5F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44)
                .addBox(0.0F-1, -2.5F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-3.0F, 0.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 0).addBox(2.0F, 0.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 33).addBox(1.0F, 4.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-2.0F, 4.0F, -3.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(41, 33).addBox(-1.0F, 8.0F, -3.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 42).addBox(-2.5F, 3.0F, 2.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 49).addBox(-2.5F, 3.0F, 2.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create().texOffs(69, 24).addBox(-3.0F, 2.0F, 3.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-4.0F, 1.0F, 4.5F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(77, 47).addBox(-1.0F, 0.0F, 5.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing1 = wings.addOrReplaceChild("rightwing1", CubeListBuilder.create().texOffs(20, 70).addBox(-3.0F, 0.0F, 5.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing2 = wings.addOrReplaceChild("rightwing2", CubeListBuilder.create().texOffs(72, 0).addBox(-5.0F, -1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing3 = wings.addOrReplaceChild("rightwing3", CubeListBuilder.create().texOffs(51, 65).addBox(-7.0F, 0.0F, 4.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing4 = wings.addOrReplaceChild("rightwing4", CubeListBuilder.create().texOffs(71, 59).addBox(-9.0F, 1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing5 = wings.addOrReplaceChild("rightwing5", CubeListBuilder.create().texOffs(31, 60).addBox(-11.0F, -1.0F, 4.5F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing6 = wings.addOrReplaceChild("rightwing6", CubeListBuilder.create().texOffs(6, 73).addBox(-13.0F, -1.0F, 5.5F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing8 = wings.addOrReplaceChild("rightwing8", CubeListBuilder.create().texOffs(77, 15).addBox(-19.0F, -1.0F, 5.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightwing7 = wings.addOrReplaceChild("rightwing7", CubeListBuilder.create().texOffs(28, 74).addBox(-17.0F, 2.0F, 4.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(30, 0).addBox(-15.0F, -1.0F, 4.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing1 = wings.addOrReplaceChild("leftwing1", CubeListBuilder.create().texOffs(12, 70).addBox(1.0F, 0.0F, 5.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing2 = wings.addOrReplaceChild("leftwing2", CubeListBuilder.create().texOffs(71, 70).addBox(3.0F, -1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing3 = wings.addOrReplaceChild("leftwing3", CubeListBuilder.create().texOffs(61, 65).addBox(5.0F, 0.0F, 4.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing4 = wings.addOrReplaceChild("leftwing4", CubeListBuilder.create().texOffs(71, 48).addBox(7.0F, 1.0F, 5.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing5 = wings.addOrReplaceChild("leftwing5", CubeListBuilder.create().texOffs(41, 60).addBox(9.0F, -1.0F, 4.5F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing6 = wings.addOrReplaceChild("leftwing6", CubeListBuilder.create().texOffs(0, 73).addBox(11.0F, -1.0F, 5.5F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing8 = wings.addOrReplaceChild("leftwing8", CubeListBuilder.create().texOffs(75, 11).addBox(15.0F, -1.0F, 5.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftwing7 = wings.addOrReplaceChild("leftwing7", CubeListBuilder.create().texOffs(38, 74).addBox(15.0F, 2.0F, 4.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(13.0F, -1.0F, 4.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));





        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(),PartPose.offset(-2.0F, 12.0F, 0.0F));
        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(),PartPose.offset(2.0F, 12.0F, 0.0F));


        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vConsumer, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
        if (stack.getItem() instanceof BaseDivineArmor armor) {
            int tick = armor.getTick(stack);
            int step = stack.getOrCreateTagElement(SolarCraftTags.DIVINE_ARMOR_TAG).getBoolean("direction") ? 1 : -1;
            float pTick = Minecraft.getInstance().isPaused() || (tick == 10 || tick == 0) ? 0 : Minecraft.getInstance().getFrameTime();
            float percent = (tick ) / 20f;
            for (int i = 1; i <= 8; i++) {
                int idx = i - 1;
                float d = 0.05f;
                float v = 0.1f;
                leftwings[idx].x = -i * (d * 20f) + percent * (i * v * 20f);
                rightwings[idx].x = +i * (d * 20f) - percent * (i * v * 20f);
                leftwings[idx].x += -(v * 20f) * percent;
                rightwings[idx].x -= -(v * 20f) * percent;
                leftwings[idx].z = d + percent * 6f;
                rightwings[idx].z = d + percent * 6f;
            }
        }
        super.renderToBuffer(matrices, vConsumer, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
    }
}