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
        Path guidePath = PATH_TO_FRAGMENTS_JSON.resolve("how_to_create_fragment.txt");
        try {
        if (!Files.exists(filePath)){
            Files.createDirectories(filePath.getParent());
            Writer writer = Files.newBufferedWriter(filePath,StandardOpenOption.CREATE);
            JsonObject object = new JsonObject();
            object.add("fragments",new JsonArray());
            SERIALIZER.toJson(object,writer);
            writer.flush();
            writer.close();
        }
        if (!Files.exists(guidePath)){
            Files.createDirectories(guidePath.getParent());
            Writer writer = Files.newBufferedWriter(guidePath,StandardOpenOption.CREATE);
            writer.write("""
                    How to create a fragment. (This thing is experimental and may contain bugs)
                    Fragments json IS NOT RELOADABLE!
                    Restart mc or server to append changes.
                    Also if you removed or added a fragment delete your solar lexicon and make new (or tell player to do it).
                    First of all start by
                    (// - those are comments, don't write them in json file)
                    {
                        "type": "" //a type field declares what type of fragment you are creating. (Possible values: "information","item","items")
                    }
                    Information type format:
                    {
                        "type": "information",
                        "translation_id": "",               //translation id that needs to be translated in lang files. Defines the name of the fragment. (String)
                        "unique_id": "",                    //unique id is the unique identifier of the fragment(ah yes logic). Should be different than all other fragments. (String)
                        "category_base": "",                //defines the category in solar lexicon where that fragment will be located. (Possible values: scroll down)
                        "sub_category_base": "",            //defines the subcategory in category where that fragment will be located. (Possible values: scroll down)
                        "progression_stage": "",            //defines what progression stage is needed to be completed to unlock this fragment. (Possible values: scroll down)
                        "translation_id_lore": "",          //translation id that needs to be translated in lang files. Defines the lore inside the fragment. (String)
                        "icon": {
                          "item": ""                        //item to be used as an icon (String that should match any of existing item in the game)
                        },
                        "priority": 1                       //priority to unlock this fragment (for example if there are a fragment with priority 1 and 2, the 1 will be opened first) (Integer)
                    }
                    Items type format:
                    {
                        "type": "information",
                        "translation_id": "",               //Translation id that needs to be translated in lang files. Defines the name of the fragment. (String)
                        "unique_id": "",                    //Unique id is the unique identifier of the fragment(ah yes logic). Should be different than all other fragments. (String)
                        "category_base": "",                //Defines the category in solar lexicon where that fragment will be located. (Possible values: scroll down)
                        "sub_category_base": "",            //Defines the subcategory in category where that fragment will be located. (Possible values: scroll down)
                        "progression_stage": "",            //Defines what progression stage is needed to be completed to unlock this fragment. (Possible values: scroll down)
                        "translation_id_lore": "",          //Translation id that needs to be translated in lang files. Defines the lore inside the fragment. (String)
                        "recipe_type": "",                  //Recipe type to use (Possible values: "infusing","infusing_crafting") (String)
                                                            //For datapack makers: Note that you cant add more than 1 infusing/infusing crafting/solar smelting recipe for a single item
                        "items": [                          //An array of items to show inside the fragment. Icon for this fragment will be the first item in array
                                                            //(recipes for them will be searched automatically, if recipe for an item doesn't exist, your minecraft will crash if you open a page)
                          {"item": ""},
                          {"item": ""},
                          {"item": ""}
                        ],
                        "priority": 1                       //Priority to unlock this fragment (for example if there are a fragment with priority 1 and 2, the 1 will be opened first) (Integer)
                    }
                    Item type format:
                    {
                        "type": "information",
                        "translation_id": "",               //Translation id that needs to be translated in lang files. Defines the name of the fragment. (String)
                        "unique_id": "",                    //Unique id is the unique identifier of the fragment(ah yes logic). Should be different than all other fragments. (String)
                        "category_base": "",                //Defines the category in solar lexicon where that fragment will be located. (Possible values: scroll down)
                        "sub_category_base": "",            //Defines the subcategory in category where that fragment will be located. (Possible values: scroll down)
                        "progression_stage": "",            //Defines what progression stage is needed to be completed to unlock this fragment. (Possible values: scroll down)
                        "translation_id_lore": "",          //Translation id that needs to be translated in lang files. Defines the lore inside the fragment. (String)
                        "recipe_type": "",                  //Recipe type to use (Possible values: "infusing","smelting","infusing_crafting") (String)
                                                            //For datapack makers: Note that you cant add more than 1 infusing/infusing crafting/solar smelting recipe for a single item
                        "recipe_item": {                    //An item to show inside the fragment. Will be set as icon too.
                                                            //(recipe for it will be searched automatically, if recipe doesn't exist, your minecraft will crash if you open a page)
                          "item": ""
                        },
                        "priority": 1                       //Priority to unlock this fragment (for example if there are a fragment with priority 1 and 2, the 1 will be opened first) (Integer)
                    }
                    POSSIBLE VALUES:
                    Progression Stage (more info can be found inside ProgressionStage.java class):
                    PRE_BEGGINING
                    BEGGINING
                    BEGGINING_2
                    PRE_FORGE
                    FORGE
                    AFTER_INFUSER
                    AFTER_CATALYSTS
                    PRE_LENS
                    AFTER_LENS
                    SOLAR_ENERGY
                    DIMENSION
                    
                    Category Base:
                    BEGGINING_INFO
                    EXPLORATION
                    ARMOR
                    STRUCTURES
                    UPGRADES
                    RUNIC_ENERGY
                    BEGINNER
                    SKILLED
                    MASTER
                    MIDGAME
                    
                    Sub category base:
                    BEGGINING
                    BASIC_DUSTS
                    WORLD
                    ARMOR
                    STRUCTURES
                    BEGINNER_ITEMS
                    BEGINNER_BLOCKS
                    BEGINNER_MATERIALS
                    RUNIC_ENERGY_EXPLORATION
                    RUNIC_ENERGY_TRANSMITTING
                    SKILLED_ITEMS
                    SKILLED_BLOCKS
                    SKILLED_MATERIALS
                    MASTER_ITEMS
                    MASTER_ENERGY
                    MASTER_MATERIALS
                    UPGRADES
                    RADIANT_LAND
                    
                    An example of fragments json:
                    {
                      "fragments": [
                        {
                          "type": "items",
                          "translation_id": "test_fragment.items",
                          "unique_id": "test_fragment_items",
                          "category_base": "exploration",
                          "sub_category_base": "beggining",
                          "progression_stage": "pre_beggining",
                          "recipe_type": "infusing",
                          "items": [
                            {"item": "solarforge:illidium_sword"},
                            {"item": "solarforge:qualadium_sword"},
                            {"item": "solarforge:turret_radar"}
                          ],
                          "translation_id_lore": "testlore",
                          "priority": 1
                        },
                        {
                          "type": "information",
                          "translation_id": "test_fragment.info",
                          "unique_id": "test_fragment_info",
                          "category_base": "exploration",
                          "sub_category_base": "beggining",
                          "progression_stage": "pre_beggining",
                          "translation_id_lore": "testlore_info",
                          "icon": {
                            "item": "minecraft:dirt"
                          },
                          "priority": 1
                        },
                        {
                          "type": "item",
                          "translation_id": "test_fragment.item",
                          "unique_id": "dirt_block",
                          "category_base": "exploration",
                          "sub_category_base": "beggining",
                          "progression_stage": "pre_beggining",
                          "recipe_type": "infusing",
                          "recipe_item": {
                            "item": "minecraft:dirt"
                          },
                          "translation_id_lore": "testlore_item",
                          "priority": 1
                        }
                      ]
                    }
                    """);
            writer.flush();
            writer.close();
        }


        }catch (IOException e){
            e.printStackTrace();
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