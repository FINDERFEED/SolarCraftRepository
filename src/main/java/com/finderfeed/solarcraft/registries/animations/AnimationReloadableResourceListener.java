package com.finderfeed.solarcraft.registries.animations;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.packets.AnimationsPacket;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.google.gson.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.network.NetworkDirection;

import java.util.HashMap;
import java.util.Map;

public class AnimationReloadableResourceListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public static AnimationReloadableResourceListener INSTANCE = new AnimationReloadableResourceListener();

    private final Map<ResourceLocation, Animation> animations = new HashMap<>();
    private final Map<ResourceLocation, JsonObject> serialized_animations = new HashMap<>();

    private AnimationReloadableResourceListener() {
        super(GSON, "animations");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> loadedObjects, ResourceManager manager, ProfilerFiller p_10795_) {
        animations.clear();
        serialized_animations.clear();
        for (var entry : loadedObjects.entrySet()){
            ResourceLocation location = entry.getKey();
            String modid = location.getNamespace();
            JsonObject object = entry.getValue().getAsJsonObject();
            if (!object.get("format_version").getAsString().equals("1.8.0")){
                throw new IllegalStateException("Loaded animation: \"" + location + "\" is not 1.8.0 format!");
            }
            JsonObject animations = object.getAsJsonObject("animations");
            for (var property : animations.entrySet()){
                ResourceLocation animationName = new ResourceLocation(modid,property.getKey());
                Animation animation = Animation.parseAnimation(animationName,property.getValue().getAsJsonObject());
                this.animations.put(animationName,animation);
                this.serialized_animations.put(animationName,property.getValue().getAsJsonObject());
                SCBedrockAnimations.ANIMATIONS.assignValue(animationName,animation);
            }
        }
    }

    public void replaceAnimations(CompoundTag data){
        animations.clear();
        serialized_animations.clear();
        for (String key : data.getAllKeys()){
            ResourceLocation location = new ResourceLocation(key);
            String json = data.getString(key);
            JsonObject object = JsonParser.parseString(json).getAsJsonObject();
            Animation animation = Animation.parseAnimation(location,object);
            animations.put(location,animation);
            serialized_animations.put(location,object);
            SCBedrockAnimations.ANIMATIONS.assignValue(location,animation);
        }
    }

    public void sendAnimationsPacket(ServerPlayer player){
        SCPacketHandler.INSTANCE.sendTo(new AnimationsPacket(this.serialized_animations),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public Animation getAnimation(ResourceLocation location){
        return this.animations.get(location);
    }

}
