package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class SunShardPuzzleScreen extends DefaultScreen {

    private Puzzle localPuzzle;
    private PuzzleTile heldTile = null;
    private BlockPos tilePos;


    public SunShardPuzzleScreen(Puzzle puzzle, BlockPos tilePosition){
        this.localPuzzle = puzzle;
        this.tilePos = tilePosition;
    }



    @Override
    protected void init() {
        super.init();
    }


    @Override
    public void tick() {
        super.tick();
        Level level = Minecraft.getInstance().level;
        if (!(level.getBlockEntity(tilePos) instanceof SunShardPuzzleBlockEntity)){
            Minecraft.getInstance().setScreen(null);
        }
    }

    @Override
    public int getScreenWidth() {
        return 200;
    }

    @Override
    public int getScreenHeight() {
        return 200;
    }
}
