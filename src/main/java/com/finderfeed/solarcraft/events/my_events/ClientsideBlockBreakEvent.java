package com.finderfeed.solarcraft.events.my_events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.eventbus.api.Event;

public class ClientsideBlockBreakEvent extends Event {

    private BlockState brokenState;
    private BlockPos pos;

    public ClientsideBlockBreakEvent(BlockState state,BlockPos pos){
        this.pos = pos;
        this.brokenState = state;
    }


    public BlockPos getPos() {
        return pos;
    }


    public BlockState getBrokenState() {
        return brokenState;
    }
}
