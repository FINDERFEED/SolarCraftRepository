package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import java.util.function.Supplier;

@Packet("shadow_bolt_explosion_packet")
public class ShadowBoltExplosionPacket extends FDPacket {


    public Vec3 pos;

    public ShadowBoltExplosionPacket(Vec3 pos){
        this.pos = pos;
    }


    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = new Vec3(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);

    }

//    public void handle(PlayPayloadContext ctx) {
//
//            ClientHelpers.handleShadowBoltExplosion(pos);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.handleShadowBoltExplosion(pos);
    }

    public static void send(Level level, Vec3 pos){
        PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.x,pos.y,pos.z,50,level.dimension()).get()).send(new ShadowBoltExplosionPacket(pos));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
