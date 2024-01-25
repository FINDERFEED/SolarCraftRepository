package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("camera_shake_packet")
public class CameraShakePacket extends FDPacket {

    private int inTime;
    private int stayTime;
    private int outTime;
    private float spread;

    public CameraShakePacket(int inTime,int stayTime,int outTime,float spread){
        this.inTime = inTime;
        this.stayTime = stayTime;
        this.outTime = outTime;
        this.spread = spread;
    }


    @Override
    public void read(FriendlyByteBuf buf) {
        this.inTime = buf.readInt();
        this.stayTime = buf.readInt();
        this.outTime = buf.readInt();
        this.spread = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(inTime);
        buf.writeInt(stayTime);
        buf.writeInt(outTime);
        buf.writeFloat(spread);
    }

//    public static void handle(CameraShakePacket packet, PlayPayloadContext context) {
//        ClientPacketHandles.handleCameraShakePacket(packet.inTime,packet.stayTime,packet.outTime,packet.spread);
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleCameraShakePacket(inTime,stayTime,outTime,spread);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
