package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.events.my_events.ClientsideBlockPlaceEvent;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;


@Packet("block_place_packet")
public class BlockPlacePacket extends FDPacket {

    private int id;
    private BlockPos pos;

    public BlockPlacePacket(BlockPos pos, BlockState state){
        this.pos = pos;
        this.id = Block.getId(state);
    }
    public BlockPlacePacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }

//    public static void handle(BlockPlacePacket packet, PlayPayloadContext ctx) {
//        NeoForge.EVENT_BUS.post(new ClientsideBlockPlaceEvent(Block.stateById(packet.id),packet.pos));
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        super.clientPlayHandle(ctx);
        NeoForge.EVENT_BUS.post(new ClientsideBlockPlaceEvent(Block.stateById(id),pos));

    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
