package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;


import java.util.function.Supplier;


import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class UpdateTypeOnClientPacket extends AbstractPacket {

    public final String id;
    public final BlockPos pos;

    public UpdateTypeOnClientPacket(BlockPos pos,String id){
        this.pos = pos;
        this.id = id;
    }


    public UpdateTypeOnClientPacket(FriendlyByteBuf buf){
        id = buf.readUtf();
        pos = buf.readBlockPos();
    }


    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        buf.writeBlockPos(pos);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientHelpers.updateEnergyTypeOnClient(pos,id);
        });
        ctx.get().setPacketHandled(true);
    }
}
