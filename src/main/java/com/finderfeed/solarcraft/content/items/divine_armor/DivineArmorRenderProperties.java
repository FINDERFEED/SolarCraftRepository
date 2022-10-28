package com.finderfeed.solarcraft.content.items.divine_armor;

import com.finderfeed.solarcraft.client.models.divine_armor.DivineBootsModel;
import com.finderfeed.solarcraft.client.models.divine_armor.DivineChestplateModel;
import com.finderfeed.solarcraft.client.models.divine_armor.DivineHelmetModel;
import com.finderfeed.solarcraft.client.models.divine_armor.DivineLeggingsModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class

DivineArmorRenderProperties implements IClientItemExtensions {

    public static DivineArmorRenderProperties INSTANCE = new DivineArmorRenderProperties();

    public static ModelPart MODEL_PART_HELMET;
    public static ModelPart MODEL_PART_CHESTPLATE;
    public static ModelPart MODEL_PART_LEGGINGS;
    public static ModelPart MODEL_PART_BOOTS;

    @Override
    public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        HumanoidModel<?> model = original;
        EntityModelSet set = Minecraft.getInstance().getEntityModels();
        switch (equipmentSlot) {
            case HEAD -> {
                if (MODEL_PART_HELMET == null) MODEL_PART_HELMET = set.bakeLayer(DivineHelmetModel.LAYER_LOCATION);
                model = new DivineHelmetModel(MODEL_PART_HELMET);
            }
            case CHEST -> {
                if (MODEL_PART_CHESTPLATE == null) MODEL_PART_CHESTPLATE = set.bakeLayer(DivineChestplateModel.LAYER_LOCATION);
                model = new DivineChestplateModel(MODEL_PART_CHESTPLATE,itemStack);
            }
            case LEGS -> {
                if (MODEL_PART_LEGGINGS == null) MODEL_PART_LEGGINGS = set.bakeLayer(DivineLeggingsModel.LAYER_LOCATION);
                model = new DivineLeggingsModel(MODEL_PART_LEGGINGS);
            }
            case FEET -> {
                if (MODEL_PART_BOOTS == null) MODEL_PART_BOOTS = set.bakeLayer(DivineBootsModel.LAYER_LOCATION);
                model = new DivineBootsModel(MODEL_PART_BOOTS);
            }
        }

        return model;
    }


}
