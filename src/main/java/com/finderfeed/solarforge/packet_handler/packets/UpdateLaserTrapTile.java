package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateLaserTrapTile {

    public final int updateIt;
    public final BlockPos pos;
    public UpdateLaserTrapTile(int i,BlockPos pos) {
        this.updateIt = i;
        this.pos = pos;
    }

    public UpdateLaserTrapTile(PacketBuffer buf) {
        updateIt = buf.readInt();
        pos = buf.readBlockPos();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(updateIt);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHelpers.updateIntegerLASERTRAP(pos,updateIt);
        });
        ctx.get().setPacketHandled(true);
    }
}