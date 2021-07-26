package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;


import java.util.function.Supplier;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class RepeaterParentUpdateOnClient extends AbstractPacket {

    public final BlockPos pos;
    public final BlockPos pos2;
    public RepeaterParentUpdateOnClient(BlockPos pos,BlockPos pos2){
        this.pos = pos;
        this.pos2 = pos2;
    }

    public RepeaterParentUpdateOnClient(FriendlyByteBuf buf) {

        pos = buf.readBlockPos();
        pos2 = buf.readBlockPos();
    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {

        buf.writeBlockPos(pos);
        buf.writeBlockPos(pos2);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHelpers.updateRepeatersOnClient(pos, pos2);
        });
        ctx.get().setPacketHandled(true);
    }
}
