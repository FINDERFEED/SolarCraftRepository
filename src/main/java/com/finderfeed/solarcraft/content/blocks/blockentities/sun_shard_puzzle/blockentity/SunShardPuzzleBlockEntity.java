package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.config.PuzzlePatternsConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.PuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.content.blocks.primitive.SunShardLockBlock;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CloseClientScreenPacket;
import com.finderfeed.solarcraft.packet_handler.packets.CloseSunShardScreenPacket;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzleOpenScreen;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SunShardPuzzleBlockEntity extends SolarcraftBlockEntity {

    private Puzzle puzzle;
    private List<UUID> usedPlayers = new ArrayList<>();

    private int rewardTicker = -1;

    public SunShardPuzzleBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.SUN_SHARD_PUZZLE_TILE.get(), p_155229_, p_155230_);
    }


    public static void tick(SunShardPuzzleBlockEntity tile,BlockState state){
        if (!tile.level.isClientSide){
            tile.giveReward();
        }
    }

    private void giveReward(){
        if (rewardTicker >= 0) {
            BlockState state = this.getBlockState();
            Direction dir = state.getValue(SunShardLockBlock.FACING);
            Vec3i n = dir.getNormal();
            Vec3 v = Helpers.getBlockCenter(this.getBlockPos()).add(n.getX(), n.getY(), n.getZ());
            if (rewardTicker > 0) {
                ((ServerLevel) level).sendParticles(
                        new BallParticleOptions(0.25f, 255, 255, 0, 20, true, false),
                        v.x, v.y, v.z, 10, 0.25, 0.25, 0.25, 0
                );
            } else if (rewardTicker == 0) {
                ItemEntity entity = new ItemEntity(level,v.x,v.y,v.z, SCItems.SUN_SHARD.get().getDefaultInstance());
                entity.setDeltaMovement(0,0,0);
                level.addFreshEntity(entity);
            }
            rewardTicker--;
        }
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
                    for (ServerPlayer p : level.getEntitiesOfClass(ServerPlayer.class,new AABB(Helpers.getBlockCenter(this.getBlockPos()),Helpers.getBlockCenter(this.getBlockPos())).inflate(10))){
                        FDPacketUtil.sendToPlayer(p,new CloseSunShardScreenPacket());
                    }
                    usedPlayers.add(player.getUUID());
                    rewardTicker = 40;
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

    public void onUse(ServerPlayer player){
        if (!level.isClientSide && !usedPlayers.contains(player.getUUID())){
            if (puzzle == null || puzzle.checkCompleted()){
                PuzzlePatternsConfig config = ConfigRegistry.PUZZLE_PATTERNS;
                List<String> templates = new ArrayList<>(config.getAllTemplates());
                templates.remove("template_null");
                String template = templates.get(player.level().random.nextInt(templates.size()));
                puzzle = new Puzzle(template);
            }
            FDPacketUtil.sendToPlayer((ServerPlayer) player,new SunShardPuzzleOpenScreen(puzzle,getBlockPos()));
        }
    }



    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (puzzle != null){
            puzzle.serialize("puzzle",tag);
        }
        tag.putInt("rewardTicker",rewardTicker);
        CompoundNBTHelper.saveUUIDList(tag,usedPlayers,"used");
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("puzzle")){
            puzzle = Puzzle.deserialize("puzzle",tag);
        }
        if (tag.contains("rewardTicker")) {
            this.rewardTicker = tag.getInt("rewardTicker");
        }
        this.usedPlayers = CompoundNBTHelper.loadUUIDList(tag,"used");
    }
}
