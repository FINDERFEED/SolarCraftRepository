package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateEnergyOnClientPacket extends AbstractPacket {

    public final String id;
    public final float set;

    public UpdateEnergyOnClientPacket(RunicEnergy.Type type,float amount){
        this.id = type.id;
        this.set = amount;
    }

    public UpdateEnergyOnClientPacket(PacketBuffer buf){
        this.id = buf.readUtf();
        this.set = buf.readFloat();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeUtf(id);
        buf.writeFloat(set);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientHelpers.updateClientRunicEnergyForPlayer(set,RunicEnergy.Type.byId(id));
        });
        ctx.get().setPacketHandled(true);
    }
}
