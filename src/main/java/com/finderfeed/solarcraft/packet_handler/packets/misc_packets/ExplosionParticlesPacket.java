package com.finderfeed.solarcraft.packet_handler.packets.misc_packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import java.util.function.Supplier;

public class ExplosionParticlesPacket {
    private Vec3 pos;

    public ExplosionParticlesPacket(FriendlyByteBuf buf){
        this.pos = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
    }
    public ExplosionParticlesPacket(Vec3 pos){
        this.pos = pos;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
    }
    public void handle(PlayPayloadContext ctx){
        
            ClientHelpers.doExplosionParticles(pos);
        });
        
    }

    public static void send(Level world, Vec3 pos){
        SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(
                PacketDistributor.TargetPoint.p(pos.x,pos.y,pos.z,100,world.dimension())),new ExplosionParticlesPacket(pos));
    }
}
