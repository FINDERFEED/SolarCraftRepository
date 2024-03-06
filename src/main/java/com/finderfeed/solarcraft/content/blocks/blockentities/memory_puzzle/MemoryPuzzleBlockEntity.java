package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CloseClientScreenPacket;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryPuzzleBlockEntity extends SolarcraftBlockEntity {
    private MemoryPuzzle puzzle;
    private List<UUID> used = new ArrayList<>();
    private boolean canOpenScreen = true;
    private int ticker = 0;

    public MemoryPuzzleBlockEntity(BlockPos pos, BlockState state) {
        super(SCTileEntities.MEMORY_PUZZLE.get(), pos, state);
    }


    public static void tick(MemoryPuzzleBlockEntity tile, Level level){
        if (!level.isClientSide){




        }
    }



    private void processCompletedPuzzle(){
        if (puzzle.isCompleted()){

        }else{
            canOpenScreen = true;
            ticker = 0;
        }
    }

    public void pushValue(ServerPlayer player, int value){
        AtomicBoolean stageCompleted = new AtomicBoolean();
        if (!puzzle.solve(value,false,stageCompleted)){
            puzzle.initiatePuzzle(false);
            MemoryPuzzleUpdatePacket packet = new MemoryPuzzleUpdatePacket(value,puzzle.getValues(),false);
            FDPacketUtil.sendToPlayer(player,packet);
        }else{
            CustomPacketPayload payload;
            if (!stageCompleted.get()) {
                payload = new MemoryPuzzleUpdatePacket(value, null, true);
            }else{
                if (puzzle.isCompleted()){
                    payload = new CloseClientScreenPacket();
                    puzzle.initiatePuzzle(true);
                }else {
                    puzzle.initiatePuzzle(false);
                    payload = new MemoryPuzzleUpdatePacket(value, puzzle.getValues(), true);
                }
            }
            FDPacketUtil.sendToPlayer(player,payload);
        }

        if (puzzle.isCompleted()){
            used.add(player.getUUID());
        }
        this.setChanged();
    }

    public void onUse(ServerPlayer player){
        if (puzzle == null){
            puzzle = new MemoryPuzzle(8,4,2);
            puzzle.initiatePuzzle(false);
        }else{
            puzzle.initiatePuzzle(false);
        }
        this.setChanged();
        MemoryPuzzleOpenScreenPacket packet = new MemoryPuzzleOpenScreenPacket(this.getBlockPos(),puzzle.getValues());
        FDPacketUtil.sendToPlayer(player,packet);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundNBTHelper.saveUUIDList(tag,used,"uuids");
        if (puzzle != null){
            puzzle.serialize(tag,"puzzle");
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("puzzle")){
            this.puzzle = MemoryPuzzle.deserialize(tag,"puzzle");
        }
        this.used = CompoundNBTHelper.loadUUIDList(tag,"uuids");
    }
}
