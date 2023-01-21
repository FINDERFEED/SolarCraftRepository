package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.solar_network;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class SolarNetworkBinderWASerializer implements WandDataSerializer<SolarNetworkBinderWAData> {

    public static final SolarNetworkBinderWASerializer SERIALIZER = new SolarNetworkBinderWASerializer();

    @Override
    public SolarNetworkBinderWAData deserialize(CompoundTag item) {
        BlockPos pos = CompoundNBTHelper.getBlockPos("pos",item);
        return new SolarNetworkBinderWAData(
                pos.equals(BlockPos.ZERO) ? null : pos
        );
    }

    @Override
    public void serialize(CompoundTag item, SolarNetworkBinderWAData data) {
        CompoundNBTHelper.writeBlockPos("pos",data.firstPos != null ? data.firstPos : BlockPos.ZERO,item);
    }

    @Override
    public ResourceLocation getDataName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"solarcraft_solar_network_binder_wa_data");
    }
}
