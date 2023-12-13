package com.finderfeed.solarcraft.local_library.entities.bossbar.server;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.ActiveBossBar;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.registries.overlays.SolarcraftOverlays;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CustomBossEventInitPacket {

    private UUID uuid;
    private String rendererId;
    private boolean remove;
    private Component name;
    private int entityId;


    public CustomBossEventInitPacket(CustomServerBossEvent event,int entotyId,boolean remove) {
        this.remove = remove;
        this.uuid = event.getUUID();
        this.name = event.getName();
        this.rendererId = event.getRenderer();
        this.entityId = entotyId;
    }

    public CustomBossEventInitPacket(FriendlyByteBuf buf){
        this.uuid = buf.readUUID();
        this.rendererId = buf.readUtf();
        this.remove = buf.readBoolean();
        this.name = buf.readComponent();
        this.entityId = buf.readInt();
    }


    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.uuid);
        buf.writeUtf(this.rendererId);
        buf.writeBoolean(this.remove);
        buf.writeComponent(this.name);
        buf.writeInt(entityId);
    }


    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleServerBossInitPacket(uuid,name,rendererId,remove,entityId);
        });
        ctx.get().setPacketHandled(true);
    }



}
