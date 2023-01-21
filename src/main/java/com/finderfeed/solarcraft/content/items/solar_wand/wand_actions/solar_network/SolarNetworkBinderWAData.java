package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.solar_network;

import com.finderfeed.solarcraft.content.items.solar_wand.WandData;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SolarNetworkBinderWAData implements WandData<SolarNetworkBinderWAData> {

    @Nullable public BlockPos firstPos;


    public SolarNetworkBinderWAData(@Nullable BlockPos first){
        this.firstPos = first;
    }



    @Override
    public WandDataSerializer<SolarNetworkBinderWAData> getSerializer() {
        return SolarNetworkBinderWASerializer.SERIALIZER;
    }
}
