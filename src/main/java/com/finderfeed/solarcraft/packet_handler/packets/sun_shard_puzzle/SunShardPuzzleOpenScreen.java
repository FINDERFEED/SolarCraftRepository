package com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client.SunShardPuzzleScreen;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SunShardPuzzleOpenScreen {

    private Puzzle puzzle;
    private BlockPos pos;

    public SunShardPuzzleOpenScreen(Puzzle puzzle, BlockPos tilePosition){
        this.pos = tilePosition;
        this.puzzle = puzzle;
    }

    public SunShardPuzzleOpenScreen(FriendlyByteBuf buf){
        BlockPos pos = buf.readBlockPos();
        CompoundTag tag = buf.readAnySizeNbt();
        this.puzzle = Puzzle.deserialize("puzzle",tag);
        this.pos = pos;
    }

    public void toBytes(FriendlyByteBuf buf){
        CompoundTag tag = new CompoundTag();
        puzzle.serialize("puzzle",tag);
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }


    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleSunShardOpenScreenPacket(puzzle,pos);
        });
        ctx.get().setPacketHandled(true);
    }



}
