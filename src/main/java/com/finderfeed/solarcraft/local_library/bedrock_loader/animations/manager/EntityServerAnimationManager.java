package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.packets.RemoveEntityAnimationPacket;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.packets.StartEntityAnimationPacket;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;

public class EntityServerAnimationManager extends ServerAnimationManager{

    private Entity entity;

    public EntityServerAnimationManager(Entity entity){
        this.entity = entity;
    }


    @Override
    public void sendAnimationStartPacket(String tickerName, AnimationTicker animationTicker) {
        FDPacketUtil.sendToTrackingEntity(this.entity,new StartEntityAnimationPacket(this.entity,tickerName,animationTicker));
//        SCPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(()->this.entity),
//                new StartEntityAnimationPacket(this.entity,tickerName,animationTicker));
    }

    @Override
    public void sendAnimationStopPacket(String tickerName) {
        FDPacketUtil.sendToTrackingEntity(this.entity,new RemoveEntityAnimationPacket(this.entity,tickerName));
//        SCPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(()->this.entity),
//                new RemoveEntityAnimationPacket(this.entity,tickerName));
    }

    public void sendAllAnimations(ServerPlayer player){
        for (var entry : this.tickers.entrySet()){
            FDPacketUtil.sendToPlayer(player,new StartEntityAnimationPacket(this.entity,entry.getKey(),entry.getValue()));
//            SCPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(()->player),
//                    new StartEntityAnimationPacket(this.entity,entry.getKey(),entry.getValue()));
        }
    }
}
