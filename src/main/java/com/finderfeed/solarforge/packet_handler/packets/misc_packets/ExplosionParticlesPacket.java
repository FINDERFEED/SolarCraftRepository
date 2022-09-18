package com.finderfeed.solarforge.packet_handler.packets.misc_packets;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

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
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientHelpers.doExplosionParticles(pos);
        });
        ctx.get().setPacketHandled(true);
    }

    public static void send(Level world, Vec3 pos){
        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(
                PacketDistributor.TargetPoint.p(pos.x,pos.y,pos.z,100,world.dimension())),new ExplosionParticlesPacket(pos));
    }
}
