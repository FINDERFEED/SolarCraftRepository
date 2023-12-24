package com.finderfeed.solarcraft.packet_handler.packets;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class TriggerEnergyTypeToast {
    public final String id;

    public TriggerEnergyTypeToast(FriendlyByteBuf buf){
        this.id = buf.readUtf();
    }
    public TriggerEnergyTypeToast(String id){
        this.id = id;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
    }
    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()->{
            ClientHelpers.addEnergyTypeToast(id);
        });
        ctx.setPacketHandled(true);
    }
}
