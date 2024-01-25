package com.finderfeed.solarcraft.packet_handler.packets.misc_packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public class SolarStrikeEntityDoExplosion extends AbstractPacket {

    private final Vec3 speed;


    public SolarStrikeEntityDoExplosion(Vec3 speed){
        this.speed = speed;
    }

    public SolarStrikeEntityDoExplosion(FriendlyByteBuf buf){
        speed = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(speed.x);
        buf.writeDouble(speed.y);
        buf.writeDouble(speed.z);
    }

    @Override
    public void handle(PlayPayloadContext ctx) {
        
            ClientHelpers.doSolarStrikeExplosion(speed);
        });
        
    }


}
