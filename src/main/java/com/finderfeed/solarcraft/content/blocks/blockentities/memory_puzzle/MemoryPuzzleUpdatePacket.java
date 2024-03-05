package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Stack;

@Packet("memory_puzzle_update_packet")
public class MemoryPuzzleUpdatePacket extends FDPacket {

    private int clickedValue;
    private boolean wasTrue;
    private Stack<Integer> values;

    public MemoryPuzzleUpdatePacket(int clickedValue,Stack<Integer> values,boolean wasTrue){
        this.values = new Stack<>();
        this.wasTrue = wasTrue;
        if (values != null) {
            for (int val : values) {
                this.values.push(val);
            }
        }
        this.clickedValue = clickedValue;
    }
    public MemoryPuzzleUpdatePacket(FriendlyByteBuf buf){
        this.wasTrue = buf.readBoolean();
        this.clickedValue = buf.readInt();
        int len = buf.readInt();
        if (len != -1) {
            this.values = new Stack<>();
            for (int i = 0; i < len; i++) {
                values.push(buf.readInt());
            }
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(wasTrue);
        buf.writeInt(clickedValue);
        if (values != null) {
            buf.writeInt(values.size());
            for (int val : values) {
                buf.writeInt(val);
            }
        }else{
            buf.writeInt(-1);
        }
    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.memoryPuzzleUpdatePacketHandle(values,clickedValue,wasTrue);
    }
}
