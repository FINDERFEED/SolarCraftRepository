package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.SolarCraftPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class TeleportEntityPacket {

    private int entityID;
    private Vec3 position;

    public TeleportEntityPacket(FriendlyByteBuf buf){
        this.entityID = buf.readInt();
        this.position = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
    }
    public TeleportEntityPacket(Entity entity,Vec3 position){
        this.entityID = entity.getId();
        this.position = position;

    }
    public void toBytes(FriendlyByteBuf buf){
       buf.writeInt(entityID);
       buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientHelpers.handleTeleportEntityPacket(entityID,position);
        });
        ctx.get().setPacketHandled(true);
    }

    public static void sendPacket(ServerLevel world,Entity entity,Vec3 position){
        Vec3 ePos  = entity.position();
        SolarCraftPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(ePos.x,ePos.y,ePos.z,100,world.dimension())),
                new TeleportEntityPacket(entity,position));
    }

}
