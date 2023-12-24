package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import net.minecraft.core.BlockPos;
import java.util.function.Supplier;

@Deprecated
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

    public PlaySoundPacket(FriendlyByteBuf buf) {
        this.volume = buf.readFloat();
        this.pitch = buf.readFloat();
        this.id = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(volume);
        buf.writeFloat(pitch);
        buf.writeInt(id);
        buf.writeBlockPos(pos);
    }

    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ClientHelpers.playSoundAtPos(pos,id,pitch,volume);
        });
        ctx.setPacketHandled(true);
    }
}