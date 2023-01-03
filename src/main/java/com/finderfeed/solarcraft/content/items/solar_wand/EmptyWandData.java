package com.finderfeed.solarcraft.content.items.solar_wand;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EmptyWandData implements WandData<EmptyWandData>{

    public static final WandDataSerializer<EmptyWandData> SERIALIZER = new WandDataSerializer<EmptyWandData>() {
        @Override
        public EmptyWandData deserialize(CompoundTag item) {
            return new EmptyWandData();
        }

        @Override
        public void serialize(CompoundTag item, EmptyWandData data) {

        }

        @Override
        public ResourceLocation getDataName() {
            return new ResourceLocation(SolarCraft.MOD_ID,"empty_data");
        }

    };

    @Override
    public WandDataSerializer<EmptyWandData> getSerializer() {
        return SERIALIZER;
    }
}
