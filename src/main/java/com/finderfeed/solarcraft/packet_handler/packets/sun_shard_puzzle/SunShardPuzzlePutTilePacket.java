package com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public class SunShardPuzzlePutTilePacket {

    private PuzzleTile tile;
    private int x;
    private int y;
    private BlockPos tilePos;

    public SunShardPuzzlePutTilePacket(BlockPos pos, PuzzleTile tile,int x,int y){
        this.tilePos = pos;
        this.tile = tile;
        this.x = x;
        this.y = y;
    }


    public SunShardPuzzlePutTilePacket(FriendlyByteBuf buf){
        CompoundTag tag = buf.readNbt();
        tile = PuzzleTile.deserialize(tag);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.tilePos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        CompoundTag tag = new CompoundTag();
        tile.serialize(tag);
        buf.writeNbt(tag);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeBlockPos(tilePos);
    }

    public void handle(PlayPayloadContext ctx){
        
            ServerPlayer sender = ctx.getSender();
            if (sender != null){
                Level world = sender.level();
                if (world.getBlockEntity(tilePos) instanceof SunShardPuzzleBlockEntity tile){
                    tile.onPutTile(sender,this.tile,x,y);
                }
            }
        });
        
    }

}
