package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.helpers.Helpers;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Puzzle {

    public static final int PUZZLE_SIZE = 20;

    //y x
    private PuzzleTile[][] tiles;
    private String templateId;
    private List<PuzzleTile> remainingTiles;

    private PuzzleTile[][] defaultTemplate;


    private Puzzle(){}

    public Puzzle(String defTemplateId,int tileExtractAmount){
        Random random = new Random();
        this.templateId = defTemplateId;
        PuzzleTile[][] defaultTemplate = PuzzleTemplateManager.INSTANCE.getDefaultTemplate(defTemplateId);
        this.defaultTemplate = defaultTemplate;
        this.tiles = new PuzzleTile[defaultTemplate.length][defaultTemplate.length];
        Helpers.copyDoubleArray(this.defaultTemplate,this.tiles);

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
        }
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

        for (int y = 0; y < PUZZLE_SIZE;y++){
            for (int x = 0; x < PUZZLE_SIZE;x++){
                CompoundTag tilec = tag.getCompound(t + "");
                if (!tilec.getString("type").equals("null")){
                    PuzzleTile tile = PuzzleTile.deserialize(tilec);
                    tiles[y][x] = tile;
                }else{
                    remainingTiles.add(new PuzzleTile(defaultTemplate[y][x]));
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
        return puzzle;
    }

    private record PuzzlePiece(PuzzleTile tile,int x,int y){}
}
