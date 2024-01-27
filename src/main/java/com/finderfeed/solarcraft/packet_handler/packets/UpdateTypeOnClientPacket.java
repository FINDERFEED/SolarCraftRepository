package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.minecraft.core.BlockPos;
import java.util.function.Supplier;



@Packet("update_type_on_client")
public class UpdateTypeOnClientPacket extends FDPacket {

    public String id;
    public BlockPos pos;

    public UpdateTypeOnClientPacket(BlockPos pos,String id){
        this.pos = pos;
        this.id = id;
    }



    public UpdateTypeOnClientPacket(FriendlyByteBuf buf) {
        id = buf.readUtf();
        pos = buf.readBlockPos();
    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        buf.writeBlockPos(pos);
    }


//    public void handle(PlayPayloadContext ctx) {
//
//            ClientHelpers.updateEnergyTypeOnClient(pos,id);
//
//
//    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.updateEnergyTypeOnClient(pos,id);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
