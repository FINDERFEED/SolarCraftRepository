package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class UpdateEnergyOnClientPacket extends AbstractPacket {

    public final String id;
    public final float set;

    public UpdateEnergyOnClientPacket(RunicEnergy.Type type,float amount){
        this.id = type.id;
        this.set = amount;
    }

    public UpdateEnergyOnClientPacket(FriendlyByteBuf buf){
        this.id = buf.readUtf();
        this.set = buf.readFloat();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        buf.writeFloat(set);
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(()->{
            ClientHelpers.updateClientRunicEnergyForPlayer(set,RunicEnergy.Type.byId(id));
        });
        ctx.setPacketHandled(true);
    }
}
