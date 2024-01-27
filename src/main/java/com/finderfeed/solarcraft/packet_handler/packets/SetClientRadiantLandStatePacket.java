package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("set_client_radiant_land_state_packet")
public class SetClientRadiantLandStatePacket extends FDPacket {

    private boolean cleaned;

    public SetClientRadiantLandStatePacket(boolean cleaned){
        this.cleaned = cleaned;
    }

    public SetClientRadiantLandStatePacket(FriendlyByteBuf buf) {
        this.cleaned = buf.readBoolean();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(cleaned);
    }

//    public void handle(PlayPayloadContext ctx) {
//
//            ClientHelpers.setClientRadiantLandState(cleaned);
//
//    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.setClientRadiantLandState(cleaned);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
