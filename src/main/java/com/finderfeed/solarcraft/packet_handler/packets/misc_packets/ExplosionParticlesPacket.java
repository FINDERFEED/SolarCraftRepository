package com.finderfeed.solarcraft.packet_handler.packets.misc_packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import java.util.function.Supplier;

@Packet("explostion_particles_packet")
public class ExplosionParticlesPacket extends FDPacket {
    private Vec3 pos;

    public ExplosionParticlesPacket(FriendlyByteBuf buf) {
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
//    public void handle(PlayPayloadContext ctx){
//
//            ClientHelpers.doExplosionParticles(pos);
//
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.doExplosionParticles(pos);
    }

    public static void send(Level world, Vec3 pos){
        PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.x,pos.y,pos.z,100,world.dimension()).get()).send(new ExplosionParticlesPacket(pos));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
