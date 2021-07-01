package com.finderfeed.solarforge.packets;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlaySoundPacket {

    public BlockPos pos;
    public float pitch;
    public float volume;
    public int id;

    public PlaySoundPacket(float volume,float pitch,int id,BlockPos pos) {
        this.pitch = pitch;
        this.volume = volume;
        this.id = id;
        this.pos = pos;
    }

    public PlaySoundPacket(PacketBuffer buf) {
        this.volume = buf.readFloat();
        this.pitch = buf.readFloat();
        this.id = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(volume);
        buf.writeFloat(pitch);
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHelpers.playSoundAtPos(pos,id,pitch,volume);
        });
        ctx.get().setPacketHandled(true);
    }
}