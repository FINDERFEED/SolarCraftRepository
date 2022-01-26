package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.misc_things.AbstractSolarNetworkRepeater;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.RepeaterParentUpdateOnClient;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;


import net.minecraftforge.network.PacketDistributor;

public class SolarEnergyRepeaterBlockEntity extends AbstractSolarNetworkRepeater {



    public int update_tick=0;

    public SolarEnergyRepeaterBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.SOLAR_REPEATER.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, SolarEnergyRepeaterBlockEntity tile) {
        AbstractSolarNetworkRepeater.tick(world,pos,blockState,tile);
        if (!tile.level.isClientSide) {
            tile.update_tick++;
            if (tile.update_tick > 19) {
                SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(), tile.worldPosition.getY(), tile.worldPosition.getZ(), 40, tile.level.dimension())),
                        new RepeaterParentUpdateOnClient(tile.worldPosition,tile.connectedTo));
                tile.update_tick = 0;
            }
        }
    }

    @Override
    public AABB getRenderBoundingBox(){
        return new AABB(getBlockPos().offset(-16,-16,-16),getBlockPos().offset(16,16,16));
    }


    @Override
    public int getRadius() {
        return 16;
    }


}
