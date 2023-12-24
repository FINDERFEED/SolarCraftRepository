package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.events.my_events.ClientsideBlockPlaceEvent;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class BlockPlacePacket {

    private int id;
    private BlockPos pos;

    public BlockPlacePacket(BlockPos pos, BlockState state){
        this.pos = pos;
        this.id = Block.getId(state);
    }

    public BlockPlacePacket(FriendlyByteBuf buf){
        this.id = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            NeoForge.EVENT_BUS.post(new ClientsideBlockPlaceEvent(Block.stateById(id),pos));
        });
        ctx.get().setPacketHandled(true);
    }

}
