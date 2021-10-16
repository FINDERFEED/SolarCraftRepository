package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSpeedPacket extends AbstractPacket {

    private final Vec3 speed;


    public SetSpeedPacket(Vec3 speed){
        this.speed = speed;
    }

    public SetSpeedPacket(FriendlyByteBuf buf){
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
            ClientHelpers.getClientPlayer().setDeltaMovement(speed);
        });
        ctx.get().setPacketHandled(true);
    }


}
