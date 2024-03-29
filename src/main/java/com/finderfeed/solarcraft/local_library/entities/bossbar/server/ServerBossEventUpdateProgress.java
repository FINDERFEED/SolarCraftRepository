package com.finderfeed.solarcraft.local_library.entities.bossbar.server;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.ActiveBossBar;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.UUID;
import java.util.function.Supplier;

@Packet("server_boss_event_update_progress")
public class ServerBossEventUpdateProgress extends FDPacket {

    private UUID id;
    private float progress;

    public ServerBossEventUpdateProgress(UUID id, float progress){
        this.id = id;
        this.progress = progress;
    }


    public ServerBossEventUpdateProgress(FriendlyByteBuf buf) {
        this.id = buf.readUUID();
        this.progress = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(id);
        buf.writeFloat(progress);
    }



    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleServerBossEventUpdateProgressPacket(id,progress);

    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
