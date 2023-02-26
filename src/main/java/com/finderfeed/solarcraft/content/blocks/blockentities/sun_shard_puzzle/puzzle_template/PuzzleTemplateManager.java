package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTileTypes;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PuzzleTemplateManager extends SimplePreparableReloadListener<Map<String,JsonElement>> {

    public static final PuzzleTemplateManager INSTANCE = new PuzzleTemplateManager();

    //[y][x]
    private Map<String, PuzzleTile[][]> templates;

    private PuzzleTemplateManager(){}

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();


    public PuzzleTile[][] getDefaultTemplate(String id){
        return templates.get(id);
    }

    public List<String> getAllTemplates(){
        return templates.keySet().stream().toList();
    }

    @Override
    protected Map<String, JsonElement> prepare(ResourceManager manager, ProfilerFiller p_10797_) {

        Map<ResourceLocation,Resource> resources = manager.listResources("puzzle_templates",
                (loc)->{
            return loc.getNamespace().equals(SolarCraft.MOD_ID) && loc.getPath().endsWith(".json");
        });

        Map<String,JsonElement> elements = new HashMap<>();

        for (Map.Entry<ResourceLocation,Resource> entry : resources.entrySet()){
            Resource resource = entry.getValue();
            ResourceLocation location = entry.getKey();
            try {
                InputStream stream = resource.open();
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader reader1 = new BufferedReader(reader);
                JsonElement element = GSON.fromJson(reader1,JsonElement.class);

                String[] str = location.getPath().split("/");

                StringBuilder builder = new StringBuilder(str[str.length-1]);
                builder.delete(builder.length()-5,builder.length());

                elements.put(builder.toString(),element);

                reader1.close();
                reader.close();
                stream.close();
            }catch (IOException e){
                SolarCraft.LOGGER.log(Level.ERROR,"Failed to load template: " + location.getPath());
                e.printStackTrace();
            }
        }

        return elements;
    }

    @Override
    protected void apply(Map<String, JsonElement> map, ResourceManager manager, ProfilerFiller p_10795_) {
        templates = new HashMap<>();
        for (Map.Entry<String,JsonElement> entry : map.entrySet()){
            templates.put(entry.getKey(),loadTemplate(entry.getValue(),Puzzle.PUZZLE_SIZE));
        }
    }

    private PuzzleTile[][] loadTemplate(JsonElement element,int size){
        JsonArray array = element.getAsJsonObject().getAsJsonArray("template");
        PuzzleTile[][] puzzleTiles = new PuzzleTile[size][size];
        for (int y = 0;y < size;y++){
            for (int x = 0;x < size;x++){
                PuzzleTile tile = fromJson(array.get(y * size + x).getAsJsonObject());
                puzzleTiles[y][x] = tile;
            }
        }
        return puzzleTiles;
    }

    private PuzzleTile fromJson(JsonObject element){
        return new PuzzleTile(
                PuzzleTileTypes.getTileById(element.get("type").getAsString()),
                element.get("rotation").getAsInt(),
                element.get("fixed").getAsBoolean()
        );
    }

}
