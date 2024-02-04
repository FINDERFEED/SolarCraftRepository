package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity;

import com.finderfeed.solarcraft.config.PuzzlePatternsConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.PuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzleOpenScreen;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;


import java.util.ArrayList;
import java.util.List;

public class SunShardPuzzleBlockEntity extends PuzzleBlockEntity {

    private Puzzle puzzle;

    public SunShardPuzzleBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.SUN_SHARD_PUZZLE_TILE.get(), p_155229_, p_155230_);
    }

    public void onPutTile(ServerPlayer player,PuzzleTile tile,int x,int y){
        var map = puzzle.getRemainingTypes();
        int amount = map.get(tile.getTileType());
        if (amount > 0) {
            boolean put = puzzle.putTileAtPos(tile, x, y);
            if (put) {
                map.put(tile.getTileType(),amount - 1);
                boolean completed = puzzle.checkCompleted();
                if (completed) {
                    Helpers.fireProgressionEvent(player, Progression.GIANT_VAULT);
                    this.solve(false);
                }
            }
        }
    }

    public void onTakeTile(int x,int y){
        PuzzleTile tile = puzzle.getTileAtPos(x,y);
        if (tile != null && !tile.isFixed()){
            var map = puzzle.getRemainingTypes();
            int amount = map.get(tile.getTileType());
            map.put(tile.getTileType(),amount + 1);
            puzzle.setTileAtPos(null,x,y);
        }
    }





    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void onUse(Player player){
        if (!level.isClientSide && !this.isSolved()){
            if (puzzle == null){
                PuzzlePatternsConfig config = ConfigRegistry.PUZZLE_PATTERNS;
                List<String> templates = new ArrayList<>(config.getAllTemplates());
                templates.remove("template_null");
                String template = templates.get(player.level().random.nextInt(templates.size()));
                puzzle = new Puzzle(template);
            }
            FDPacketUtil.sendToPlayer((ServerPlayer) player,new SunShardPuzzleOpenScreen(puzzle,getBlockPos()));
//            SCPacketHandler.INSTANCE.sendTo(new SunShardPuzzleOpenScreen(puzzle,getBlockPos()),
//                    ((ServerPlayer)player).connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        }
    }



    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (puzzle != null){
            puzzle.serialize("puzzle",tag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("puzzle")){
            puzzle = Puzzle.deserialize("puzzle",tag);
        }
    }
}
