package com.finderfeed.solarcraft.local_library.entities.bossbar.server;

import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
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
            FDPacketUtil.sendToPlayer(player,new CustomBossEventInitPacket(this,-1,false));
//            SCPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,-1,false),player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        }
    }


    public void removePlayer(ServerPlayer player){
        if (players.remove(player)){
            FDPacketUtil.sendToPlayer(player,new CustomBossEventInitPacket(this,-1,true));
//            SCPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,-1,true),player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public void addPlayer(ServerPlayer player,int entityId){
        if (players.add(player)){
            FDPacketUtil.sendToPlayer(player,new CustomBossEventInitPacket(this,entityId,false));
//            SCPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,entityId,false),player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        }
    }


    public void removePlayer(ServerPlayer player,int entityId){
        if (players.remove(player)){
            FDPacketUtil.sendToPlayer(player,new CustomBossEventInitPacket(this,entityId,true));

//            SCPacketHandler.INSTANCE.sendTo(new CustomBossEventInitPacket(this,entityId,true),player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public void setProgress(float progress){
        for (ServerPlayer player : players){
            FDPacketUtil.sendToPlayer(player,new ServerBossEventUpdateProgress(this.uuid,progress));
//            SCPacketHandler.INSTANCE.sendTo(new ServerBossEventUpdateProgress(this.uuid,progress),player.connection.connection,PlayNetworkDirection.PLAY_TO_CLIENT);
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
