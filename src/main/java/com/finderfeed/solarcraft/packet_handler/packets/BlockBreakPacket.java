package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.events.my_events.ClientsideBlockBreakEvent;
import com.finderfeed.solarcraft.events.my_events.ClientsideBlockPlaceEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.MinecraftForge;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlockBreakPacket {

    private int id;
    private BlockPos pos;

    public BlockBreakPacket(BlockPos pos, BlockState state){
        this.pos = pos;
        this.id = Block.getId(state);
    }

    public BlockBreakPacket(FriendlyByteBuf buf){
        this.id = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            NeoForge.EVENT_BUS.post(new ClientsideBlockBreakEvent(Block.stateById(id),pos));
        });
        ctx.get().setPacketHandled(true);
    }
}
