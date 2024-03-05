package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Queue;
import java.util.Stack;

@Packet("memory_puzzle_open_screen")
public class MemoryPuzzleOpenScreenPacket extends FDPacket {

    private Stack<Integer> values;
    private BlockPos tile;
    public MemoryPuzzleOpenScreenPacket(BlockPos tile,Stack<Integer> values){
        this.tile = tile;
        this.values = new Stack<>();
        for (int val : values){
            this.values.push(val);
        }
    }

    public MemoryPuzzleOpenScreenPacket(FriendlyByteBuf buf){
        this.tile = buf.readBlockPos();
        int len = buf.readInt();
        this.values = new Stack<>();
        for (int i = 0; i < len;i++){
            values.push(buf.readInt());
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(tile);
        buf.writeInt(values.size());
        for (int val : values){
            buf.writeInt(val);
        }
    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        super.clientPlayHandle(ctx);
        ClientPacketHandles.memoryPuzzleOpenScreenPacketHandle(tile,values);
    }
}
