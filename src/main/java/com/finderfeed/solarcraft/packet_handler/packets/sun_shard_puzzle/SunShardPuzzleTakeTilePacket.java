package com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("sun_shard_puzzle_take_tile_packet")
public class SunShardPuzzleTakeTilePacket extends FDPacket {

    private int x;
    private int y;
    private BlockPos tilePos;

    public SunShardPuzzleTakeTilePacket(BlockPos pos,int x,int y){
        this.tilePos = pos;

        this.x = x;
        this.y = y;
    }


    @Override
    public void read(FriendlyByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.tilePos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){


        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeBlockPos(tilePos);
    }

//    public void handle(PlayPayloadContext ctx){
//
//            ServerPlayer sender = ctx.getSender();
//            if (sender != null){
//                Level world = sender.level();
//                if (world.getBlockEntity(tilePos) instanceof SunShardPuzzleBlockEntity tile){
//                    tile.onTakeTile(x,y);
//                }
//            }
//
//    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ServerPlayer sender = (ServerPlayer) ctx.player().get();
        Level world = sender.level();
        if (world.getBlockEntity(tilePos) instanceof SunShardPuzzleBlockEntity tile){
            tile.onTakeTile(x,y);
        }
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
