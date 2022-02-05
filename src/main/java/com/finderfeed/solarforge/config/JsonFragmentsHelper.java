package com.finderfeed.solarforge.config;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.SendFragmentsToClientPacket;
import com.google.gson.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkDirection;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class JsonFragmentsHelper {


    private static JsonObject SERVERSIDE_FRAGMENTS_JSON;

    private static final Gson SERIALIZER = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final Path PATH_TO_FRAGMENTS_JSON = FMLPaths.CONFIGDIR.get()
            .resolve("solarcraft_custom_fragments");
    public static void setupJSON(){
        Path filePath = PATH_TO_FRAGMENTS_JSON.resolve("custom_fragments.json");
        if (!Files.exists(filePath)){
            try {
                Files.createDirectories(filePath.getParent());
                Writer writer = Files.newBufferedWriter(filePath,StandardOpenOption.CREATE);
                JsonObject object = new JsonObject();
                object.add("fragments",new JsonArray());
                SERIALIZER.toJson(object,writer);
                writer.flush();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * ONLY ON SERVER SIDE!
     */
    @Nullable
    public static List<JsonObject> readFragments(){
        SolarForge.LOGGER.log(Level.INFO,"Reading fragments from JSON");
        Path filePath = PATH_TO_FRAGMENTS_JSON.resolve("custom_fragments.json");
        if (Files.exists(filePath)){
            try {
                Reader reader = Files.newBufferedReader(filePath);
                JsonObject object = SERIALIZER.fromJson(reader,JsonObject.class);
                reader.close();
                SERVERSIDE_FRAGMENTS_JSON = object;
                JsonArray array = object.getAsJsonArray("fragments");
                List<JsonObject> l = new ArrayList<>();
                for (JsonElement e : array){
                    l.add(e.getAsJsonObject());
                }
                SolarForge.LOGGER.log(Level.INFO,"Fragments reading complete");
                return l;
            }catch (IOException e){
                SolarForge.LOGGER.log(Level.INFO,"Error while reading fragments json, IOException");
                e.printStackTrace();
                return null;
            }
        }else{
            SolarForge.LOGGER.log(Level.ERROR,"Error while reading fragments json, file does not exist");
            return null;
        }
    }

    public static void sendUpdatePacketToClient(ServerPlayer serverPlayer){
        JsonObject o;
        if (SERVERSIDE_FRAGMENTS_JSON == null){
            SolarForge.LOGGER.log(Level.ERROR,"Server read fragments json file incorrectly, sending empty packet.");
            o = new JsonObject();
            o.add("fragments",new JsonArray());
        }else{
            o = SERVERSIDE_FRAGMENTS_JSON;
        }
        SolarForgePacketHandler.INSTANCE.sendTo(new SendFragmentsToClientPacket(o),serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);

    }

    public static List<JsonObject> serializedFragmentsArray(JsonObject object){
        JsonArray array = object.getAsJsonArray("fragments");
        List<JsonObject> l = new ArrayList<>();
        for (JsonElement e : array){
            l.add(e.getAsJsonObject());
        }
        return l;
    }

    public static String jsonToString(JsonObject object){
        return object.toString();
    }
    public static JsonObject jsonFromString(String json){
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public static boolean fragmentsShouldBeRead(){
        return SERVERSIDE_FRAGMENTS_JSON == null;
    }




}
