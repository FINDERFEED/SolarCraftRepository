package com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client.SunShardPuzzleScreen;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("sun_shard_puzzle_open_screen")
public class SunShardPuzzleOpenScreen extends FDPacket {

    private Puzzle puzzle;
    private BlockPos pos;

    public SunShardPuzzleOpenScreen(Puzzle puzzle, BlockPos tilePosition){
        this.pos = tilePosition;
        this.puzzle = new Puzzle(puzzle);
    }


    public SunShardPuzzleOpenScreen(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        CompoundTag tag = buf.readNbt();
        this.puzzle = Puzzle.deserialize("puzzle",tag);
        this.pos = pos;
    }

    public void toBytes(FriendlyByteBuf buf){
        CompoundTag tag = new CompoundTag();
        puzzle.serialize("puzzle",tag);
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleSunShardOpenScreenPacket(puzzle,pos);

    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
        
    }



}
