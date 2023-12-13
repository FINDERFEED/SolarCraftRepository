package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraShakePacket {

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

    public CameraShakePacket(FriendlyByteBuf buf){
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

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleCameraShakePacket(inTime,stayTime,outTime,spread);
        });
        ctx.get().setPacketHandled(true);
    }


}
