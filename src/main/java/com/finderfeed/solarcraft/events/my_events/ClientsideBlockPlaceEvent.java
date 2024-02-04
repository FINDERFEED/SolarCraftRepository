package com.finderfeed.solarcraft.events.my_events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;

public class ClientsideBlockPlaceEvent extends Event {


    private BlockState placedState;
    private BlockPos pos;

    public ClientsideBlockPlaceEvent(BlockState state,BlockPos pos){
        this.pos = pos;
        this.placedState = state;
    }


    public BlockPos getPos() {
        return pos;
    }


    public BlockState getPlacedState() {
        return placedState;
    }
}
