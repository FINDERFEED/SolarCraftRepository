package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SendConfigsToClientPacket {

    private List<String> idJson;

    public SendConfigsToClientPacket(){
        idJson = new ArrayList<>();
        for (var entry : ConfigRegistry.EARLY_LOAD_CONFIGS.entrySet()){
            idJson.add(entry.getKey());
            idJson.add(entry.getValue().getJson().toString());
        }
        for (var entry : ConfigRegistry.POST_LOAD_CONFIGS.entrySet()){
            idJson.add(entry.getKey());
            idJson.add(entry.getValue().getJson().toString());
        }
    }

    public SendConfigsToClientPacket(FriendlyByteBuf buf){
        int size = buf.readInt();
        idJson = new ArrayList<>();
        for (int i = 0; i < size;i++){
            idJson.add(buf.readUtf());
        }
    }


    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(idJson.size());
        for (String s : idJson) {
            buf.writeUtf(s);
        }
    }


    public void handle(PlayPayloadContext ctx) {
        
            ClientPacketHandles.handleClientConfigsPacket(idJson);
        });
        
    }
}
