package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("update_energy_on_client_packet")
public class UpdateEnergyOnClientPacket extends FDPacket {

    public String id;
    public float set;

    public UpdateEnergyOnClientPacket(RunicEnergy.Type type,float amount){
        this.id = type.id;
        this.set = amount;
    }


    @Override
    public void read(FriendlyByteBuf buf) {
        this.id = buf.readUtf();
        this.set = buf.readFloat();
    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        buf.writeFloat(set);
    }


//    public void handle(PlayPayloadContext ctx) {
//
//            ClientHelpers.updateClientRunicEnergyForPlayer(set,RunicEnergy.Type.byId(id));
//        }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.updateClientRunicEnergyForPlayer(set,RunicEnergy.Type.byId(id));

    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

        this.toBytes(friendlyByteBuf);

    }
}
