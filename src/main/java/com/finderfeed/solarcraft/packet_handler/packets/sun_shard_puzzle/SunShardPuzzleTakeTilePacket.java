package com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SunShardPuzzleTakeTilePacket {

    private int x;
    private int y;
    private BlockPos tilePos;

    public SunShardPuzzleTakeTilePacket(BlockPos pos,int x,int y){
        this.tilePos = pos;

        this.x = x;
        this.y = y;
    }


    public SunShardPuzzleTakeTilePacket(FriendlyByteBuf buf){


        this.x = buf.readInt();
        this.y = buf.readInt();
        this.tilePos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){


        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeBlockPos(tilePos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null){
                Level world = sender.level;
                if (world.getBlockEntity(tilePos) instanceof SunShardPuzzleBlockEntity tile){
                    tile.onTakeTile(x,y);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
