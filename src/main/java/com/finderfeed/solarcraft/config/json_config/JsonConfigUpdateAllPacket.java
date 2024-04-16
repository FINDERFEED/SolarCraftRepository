package com.finderfeed.solarcraft.config.json_config;

import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.SCConfigs;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.ArrayList;
import java.util.List;

@Packet("update_all_configs")
public class JsonConfigUpdateAllPacket extends FDPacket {

    private List<String> names;
    private List<String> configs;
    public JsonConfigUpdateAllPacket(){
        names = new ArrayList<>();
        configs = new ArrayList<>();
        for (var entry : SCConfigs.CONFIG_REGISTRY.entrySet()){
            names.add(entry.getKey());
            configs.add(entry.getValue().getLoadedJson().toString());
        }
    }
    public JsonConfigUpdateAllPacket(FriendlyByteBuf buf){
        int namesLen = buf.readInt();
        names = new ArrayList<>();
        for (int i = 0; i < namesLen;i++){
            names.add(buf.readUtf());
        }
        configs = new ArrayList<>();
        for (int i = 0; i < namesLen;i++){
            configs.add(buf.readUtf());
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(names.size());
        for (String name : names){
            buf.writeUtf(name);
        }
        for (String sconfig : configs){
            buf.writeUtf(sconfig);
        }
    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        for (int i = 0; i < names.size();i++){
            JsonConfig config = SCConfigs.CONFIG_REGISTRY.get(names.get(i));
            JsonObject object = JsonParser.parseString(configs.get(i)).getAsJsonObject();
            config.setLoadedJson(object);
            config.parseJson(object);
        }
    }
}
