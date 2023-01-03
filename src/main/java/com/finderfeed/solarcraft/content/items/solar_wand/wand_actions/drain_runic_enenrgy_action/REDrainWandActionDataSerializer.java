package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_wand.WandData;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class REDrainWandActionDataSerializer implements WandDataSerializer<REDrainWandActionData> {

    public static final REDrainWandActionDataSerializer SERIALIZER = new REDrainWandActionDataSerializer();

    @Override
    public REDrainWandActionData deserialize(CompoundTag item) {
        return new REDrainWandActionData(item.getString("re_type"));
    }

    @Override
    public void serialize(CompoundTag item, REDrainWandActionData data) {
        item.putString("re_type",data.getTypeToDrain());
    }

    @Override
    public ResourceLocation getDataName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"re_drain_type");
    }
}
