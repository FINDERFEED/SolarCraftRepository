package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import net.minecraft.core.BlockPos;
import java.util.function.Supplier;



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
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(()->{
            ClientHelpers.updateEnergyTypeOnClient(pos,id);
        });
        ctx.setPacketHandled(true);
    }
}
