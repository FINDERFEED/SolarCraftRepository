package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateTypeOnClientPacket extends AbstractPacket {

    public final String id;
    public final BlockPos pos;

    public UpdateTypeOnClientPacket(BlockPos pos,String id){
        this.pos = pos;
        this.id = id;
    }


    public UpdateTypeOnClientPacket(PacketBuffer buf){
        id = buf.readUtf();
        pos = buf.readBlockPos();
    }


    @Override
    public void toBytes(PacketBuffer buf) {
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
