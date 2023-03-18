package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTileType;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTileTypes;
import com.finderfeed.solarcraft.helpers.Helpers;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Puzzle {

    public static final int PUZZLE_SIZE = 12;

    //y x
    private PuzzleTile[][] tiles;
    private String templateId;
    /**
     * ONLY FOR CLIENT!
     */
    private List<PuzzleTile> remainingTiles;

    /**
     * ONLY FOR SERVER!
     */
    private Map<PuzzleTileType,Integer> remainingTypes;

    private PuzzleTile[][] defaultTemplate;


    private Puzzle(){}

    public static Puzzle createTestPuzzle(){
        Puzzle puzzle = new Puzzle();
        puzzle.tiles = new PuzzleTile[PUZZLE_SIZE][PUZZLE_SIZE];
        puzzle.templateId = "null";
        puzzle.remainingTiles = new ArrayList<>();
        puzzle.defaultTemplate = new PuzzleTile[PUZZLE_SIZE][PUZZLE_SIZE];
        puzzle.remainingTypes = new HashMap<>();
        for (PuzzleTileType tileType : PuzzleTileTypes.getAllTypes()){
            for (int i = 0; i < 5;i++) {
                puzzle.remainingTiles.add(new PuzzleTile(tileType,0,false));
            }
        }
        return puzzle;
    }

    public Puzzle(String defTemplateId,int tileExtractAmount){
        Random random = new Random();
        this.templateId = defTemplateId;
        PuzzleTile[][] defaultTemplate = PuzzleTemplateManager.INSTANCE.getDefaultTemplate(defTemplateId);
        this.defaultTemplate = defaultTemplate;
        this.tiles = new PuzzleTile[defaultTemplate.length][defaultTemplate.length];
        this.remainingTypes = new HashMap<>();
        Helpers.copyMatrixArray(this.defaultTemplate,this.tiles);

        List<PuzzlePiece> pieces = new ArrayList<>();
        for (int y = 0;y < defaultTemplate.length;y++){
            for (int x = 0; x < defaultTemplate.length;x++){
                PuzzleTile tile = new PuzzleTile(defaultTemplate[y][x]);
                if (!tile.isFixed()){
                    pieces.add(new PuzzlePiece(tile,x,y));
                }
            }
        }
         int rem = tileExtractAmount;

        while (rem > 0){
            pieces.remove(random.nextInt(pieces.size()));
            rem--;
        }

        for (PuzzlePiece puzzlePiece : pieces){
            this.tiles[puzzlePiece.y][puzzlePiece.x] = null;
            this.remainingTiles.add(puzzlePiece.tile);
            if (remainingTypes.containsKey(puzzlePiece.tile.getTileType())){
                int prev = remainingTypes.get(puzzlePiece.tile.getTileType());
                remainingTypes.put(puzzlePiece.tile.getTileType(),prev+1);
            }else{
                remainingTypes.put(puzzlePiece.tile.getTileType(),1);
            }
        }
    }

    public boolean checkCompleted(){
        if (!remainingTiles.isEmpty()){
            return false;
        }
        PuzzleTile[][] template = PuzzleTemplateManager.INSTANCE.getDefaultTemplate(templateId);
        for (int x = 0; x < PUZZLE_SIZE;x++){
            for (int y = 0; y < PUZZLE_SIZE;y++){
                PuzzleTile templateTile = template[y][x];
                PuzzleTile current = getTileAtPos(x,y);
                if (!templateTile.equals(current)){
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    public PuzzleTile getTileAtPos(int x,int y){
        return tiles[y][x];
    }

    public boolean putTileAtPos(PuzzleTile tile,int x, int y){
        if (this.getTileAtPos(x,y) == null
                && x >= 0 && x < PUZZLE_SIZE
                && y >= 0 && y < PUZZLE_SIZE){
            tiles[y][x] = tile;
            return true;
        }
        return false;
    }

    public void setTileAtPos(PuzzleTile tile,int x,int y){
        tiles[y][x] = tile;
    }


    public void serialize(String name,CompoundTag save){
        CompoundTag tag = new CompoundTag();
        int t = 0;
        for (int y = 0; y < PUZZLE_SIZE;y++){
            for (int x = 0; x < PUZZLE_SIZE;x++){
                PuzzleTile tile = getTileAtPos(x,y);
                CompoundTag tilec = new CompoundTag();
                if (tile == null){
                    tilec.putString("type","null");
                }else{
                    tile.serialize(tilec);
                }
                tag.put(t + "",tilec);
                t++;
            }
        }
        tag.putString("defaultTemplate",templateId);
        save.put(name,tag);
    }

    public static Puzzle deserialize(String name,CompoundTag save){
        CompoundTag tag = save.getCompound(name);
        PuzzleTile[][] tiles = new PuzzleTile[PUZZLE_SIZE][PUZZLE_SIZE];
        int t = 0;
        String templateId = tag.getString("defaultTemplate");
        PuzzleTile[][] defaultTemplate = PuzzleTemplateManager.INSTANCE.getDefaultTemplate(templateId);

        List<PuzzleTile> remainingTiles = new ArrayList<>();

        var remainingTypes = new HashMap<PuzzleTileType,Integer>();
        for (int y = 0; y < PUZZLE_SIZE;y++){
            for (int x = 0; x < PUZZLE_SIZE;x++){
                CompoundTag tilec = tag.getCompound(t + "");
                if (!tilec.getString("type").equals("null")){
                    PuzzleTile tile = PuzzleTile.deserialize(tilec);
                    tiles[y][x] = tile;
                }else{
                    PuzzleTile tile = new PuzzleTile(defaultTemplate[y][x]);
                    remainingTiles.add(tile);
                    if (remainingTypes.containsKey(tile.getTileType())){
                        int prev = remainingTypes.get(tile.getTileType());
                        remainingTypes.put(tile.getTileType(),prev+1);
                    }else{
                        remainingTypes.put(tile.getTileType(),1);
                    }
                    tiles[y][x] = null;
                }
                t++;
            }
        }

        Puzzle puzzle = new Puzzle();
        puzzle.defaultTemplate = defaultTemplate;
        puzzle.tiles = tiles;
        puzzle.templateId = templateId;
        puzzle.remainingTiles = remainingTiles;
        puzzle.remainingTypes = remainingTypes;
        return puzzle;
    }

    public PuzzleTile[][] getTiles() {
        return tiles;
    }

    /**
     * ONLY FOR CLIENT! ON SERVER THIS DOESN'T CONTAIN ANY INFORMATION
     */
    public List<PuzzleTile> getRemainingTiles() {
        return remainingTiles;
    }

    /**
     * ONLY FOR SERVER! ON CLIENT THIS GIVES NO INFORMATION!
     */
    public Map<PuzzleTileType, Integer> getRemainingTypes() {
        return remainingTypes;
    }

    private record PuzzlePiece(PuzzleTile tile, int x, int y){}
}
