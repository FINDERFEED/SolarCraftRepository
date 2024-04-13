package com.finderfeed.solarcraft.config.json_config;

import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.SCConfigs;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("json_config_update_packet")
public class JsonConfigUpdatePacket extends FDPacket {
    private JsonObject object;
    private String configName;
    public JsonConfigUpdatePacket(JsonConfig config){
        this.object = config.getLoadedJson();
        this.configName = config.getName();
    }

    public JsonConfigUpdatePacket(FriendlyByteBuf buf){
        this.object = JsonParser.parseString(buf.readUtf()).getAsJsonObject();
        this.configName = buf.readUtf();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(object.toString());
        buf.writeUtf(configName);
    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        JsonConfig config = SCConfigs.CONFIG_REGISTRY.get(configName);
        if (config != null){
            config.parseJson(object);
        }
    }
}
