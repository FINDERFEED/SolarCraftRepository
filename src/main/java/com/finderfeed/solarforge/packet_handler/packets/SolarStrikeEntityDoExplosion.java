package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

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
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientHelpers.doSolarStrikeExplosion(speed);
        });
        ctx.get().setPacketHandled(true);
    }


}
