package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.minecraft.core.BlockPos;
import java.util.function.Supplier;



@Packet("update_laser_trap_tile")
public class UpdateLaserTrapTile extends FDPacket {

    public int updateIt;
    public BlockPos pos;
    public UpdateLaserTrapTile(int i,BlockPos pos) {
        this.updateIt = i;
        this.pos = pos;
    }
    public UpdateLaserTrapTile(FriendlyByteBuf buf) {
        updateIt = buf.readInt();
        pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(updateIt);
        buf.writeBlockPos(pos);
    }

//    public void handle(PlayPayloadContext ctx) {
//            ClientHelpers.updateIntegerLASERTRAP(pos,updateIt);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.updateIntegerLASERTRAP(pos,updateIt);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}