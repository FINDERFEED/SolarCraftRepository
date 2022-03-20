package com.finderfeed.solarforge.magic.items.divine_armor;

import com.finderfeed.solarforge.client.models.DivineArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.Nullable;

public class DivineArmorRenderProperties implements IItemRenderProperties {

    public static DivineArmorRenderProperties INSTANCE = new DivineArmorRenderProperties();

    public static ModelPart MODEL_PART;

    @Nullable
    @Override
    public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
        if (MODEL_PART == null) MODEL_PART = Minecraft.getInstance().getEntityModels().bakeLayer(DivineArmorModel.LAYER_LOCATION);
        DivineArmorModel model = new DivineArmorModel(MODEL_PART,itemStack);
        return model;
    }
}
