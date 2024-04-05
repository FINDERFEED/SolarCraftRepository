package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle;

import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Optional;

@Packet("memory_puzzle_value_packet")
public class MemoryPuzzleValuePacket extends FDPacket {

    private int value;
    private BlockPos tilePos;

    public MemoryPuzzleValuePacket(BlockPos pos,int value){
        this.tilePos = pos;
        this.value = value;
    }
    public MemoryPuzzleValuePacket(FriendlyByteBuf buf){
        this.tilePos = buf.readBlockPos();
        this.value = buf.readInt();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(tilePos);
        buf.writeInt(value);
    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        Optional<Player> p;
        if ((p = ctx.player()).isPresent() && p.get() instanceof ServerPlayer player){
            Level level = player.level;
            if (level.getBlockEntity(tilePos) instanceof MemoryPuzzleBlockEntity tile){
                tile.pushValue(player,value);
            }
        }
    }
}
