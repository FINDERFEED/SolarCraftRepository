package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticle;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("flash_packet")
public class FlashPacket extends FDPacket {

    private int inTime;
    private int stayTime;
    private int outTime;

    public FlashPacket(int inTime,int stayTime,int outTime){
        this.inTime = inTime;
        this.stayTime = stayTime;
        this.outTime = outTime;
    }

    public FlashPacket(FriendlyByteBuf buf) {
        this.inTime = buf.readInt();
        this.stayTime = buf.readInt();
        this.outTime = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(inTime);
        buf.writeInt(stayTime);
        buf.writeInt(outTime);
    }

/*    public static void handle(FlashPacket packet,PlayPayloadContext ctx) {
        ClientPacketHandles.handleFlashPacket(packet.inTime,packet.stayTime,packet.outTime);
    }*/

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleFlashPacket(inTime,stayTime,outTime);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }

}
