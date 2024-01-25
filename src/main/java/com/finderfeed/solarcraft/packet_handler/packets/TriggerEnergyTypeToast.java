package com.finderfeed.solarcraft.packet_handler.packets;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("trigger_energy_type_toast")
public class TriggerEnergyTypeToast extends FDPacket {
    public String id;
    @Override
    public void read(FriendlyByteBuf buf) {
        this.id = buf.readUtf();

    }

    public TriggerEnergyTypeToast(String id){
        this.id = id;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
    }
//    public void handle(PlayPayloadContext ctx){
//
//            ClientHelpers.addEnergyTypeToast(id);
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.addEnergyTypeToast(id);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
