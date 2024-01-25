package com.finderfeed.solarcraft.packet_handler.packets.misc_packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("solar_strike_entity_do_explosion_packet")
public class SolarStrikeEntityDoExplosion extends FDPacket {

    private Vec3 speed;


    public SolarStrikeEntityDoExplosion(Vec3 speed){
        this.speed = speed;
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        speed = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());

    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(speed.x);
        buf.writeDouble(speed.y);
        buf.writeDouble(speed.z);
    }

//    public void handle(PlayPayloadContext ctx) {
//
//            ClientHelpers.doSolarStrikeExplosion(speed);
//
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.doSolarStrikeExplosion(speed);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
