package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticle;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlashPacket {

    private int inTime;
    private int stayTime;
    private int outTime;

    public FlashPacket(int inTime,int stayTime,int outTime){
        this.inTime = inTime;
        this.stayTime = stayTime;
        this.outTime = outTime;
    }

    public FlashPacket(FriendlyByteBuf buf){
        this.inTime = buf.readInt();
        this.stayTime = buf.readInt();
        this.outTime = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(inTime);
        buf.writeInt(stayTime);
        buf.writeInt(outTime);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleFlashPacket(inTime,stayTime,outTime);
        });
        ctx.get().setPacketHandled(true);
    }

}
