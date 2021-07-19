package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.misc_things.AbstractSolarNetworkRepeater;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.RepeaterParentUpdateOnClient;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.network.PacketDistributor;

public class SolarEnergyRepeaterBlockEntity extends AbstractSolarNetworkRepeater {



    public int update_tick=0;

    public SolarEnergyRepeaterBlockEntity() {
        super(TileEntitiesRegistry.SOLAR_REPEATER.get());
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            update_tick++;
            if (update_tick > 19) {
                SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 40, level.dimension())),
                        new RepeaterParentUpdateOnClient(worldPosition, connectedTo));
                update_tick = 0;
            }
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox(){
        return new AxisAlignedBB(getBlockPos().offset(-16,-16,-16),getBlockPos().offset(16,16,16));
    }


    @Override
    public int getRadius() {
        return 16;
    }


}
