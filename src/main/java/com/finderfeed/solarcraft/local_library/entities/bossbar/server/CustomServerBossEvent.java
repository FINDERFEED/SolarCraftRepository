package com.finderfeed.solarcraft.local_library.entities.bossbar.server;

import com.finderfeed.solarcraft.packet_handler.SolarCraftPacketHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.network.NetworkDirection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomServerBossEvent {

    private Set<ServerPlayer> players = new HashSet<>();
    private Component name;
    private String renderer;
    private final UUID uuid;

    public CustomServerBossEvent(Component name,String rendererId){
        this.name = name;
        this.uuid = Mth.createInsecureUUID();
        this.renderer = rendererId;
    }


    public void addPlayer(ServerPlayer player){
        if (players.add(player)){

            SolarCraftPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,-1,false),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }


    public void removePlayer(ServerPlayer player){
        if (players.remove(player)){

            SolarCraftPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,-1,true),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public void addPlayer(ServerPlayer player,int entityId){
        if (players.add(player)){

            SolarCraftPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,entityId,false),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }


    public void removePlayer(ServerPlayer player,int entityId){
        if (players.remove(player)){

            SolarCraftPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,entityId,true),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public void setProgress(float progress){
        for (ServerPlayer player : players){

            SolarCraftPacketHandler.INSTANCE.sendTo(new ServerBossEventUpdateProgress(this.uuid,progress),player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    public Component getName() {
        return name;
    }

    public String getRenderer() {
        return renderer;
    }
}
