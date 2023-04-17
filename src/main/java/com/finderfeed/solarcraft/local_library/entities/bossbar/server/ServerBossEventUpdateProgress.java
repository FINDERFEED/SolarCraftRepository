package com.finderfeed.solarcraft.local_library.entities.bossbar.server;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.ActiveBossBar;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ServerBossEventUpdateProgress {

    private UUID id;
    private float progress;

    public ServerBossEventUpdateProgress(UUID id, float progress){
        this.id = id;
        this.progress = progress;
    }


    public ServerBossEventUpdateProgress(FriendlyByteBuf buf){
        this.id = buf.readUUID();
        this.progress = buf.readFloat();

    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(id);
        buf.writeFloat(progress);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleServerBossEventUpdateProgressPacket(id,progress);
        });
        ctx.get().setPacketHandled(true);
    }

}
