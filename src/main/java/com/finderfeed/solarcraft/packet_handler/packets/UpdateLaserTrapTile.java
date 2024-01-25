package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.minecraft.core.BlockPos;
import java.util.function.Supplier;



public class UpdateLaserTrapTile {

    public final int updateIt;
    public final BlockPos pos;
    public UpdateLaserTrapTile(int i,BlockPos pos) {
        this.updateIt = i;
        this.pos = pos;
    }

    public UpdateLaserTrapTile(FriendlyByteBuf buf) {
        updateIt = buf.readInt();
        pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(updateIt);
        buf.writeBlockPos(pos);
    }

    public void handle(PlayPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ClientHelpers.updateIntegerLASERTRAP(pos,updateIt);
        });
        
    }
}