package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;


import java.util.function.Supplier;


import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class TileEnergyGeneratorUpdate extends AbstractPacket {

    public final int index;
    public final BlockPos pos;
    public final BlockPos pos2;
    public final boolean remove;
    public TileEnergyGeneratorUpdate(BlockPos pos,BlockPos pos2,int index,boolean remove){
        this.pos = pos;
        this.pos2 = pos2;
        this.index = index;
        this.remove = remove;
    }
    public TileEnergyGeneratorUpdate(FriendlyByteBuf buf) {

        pos = buf.readBlockPos();
        pos2 = buf.readBlockPos();
        index = buf.readInt();
        remove = buf.readBoolean();

    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {

        buf.writeBlockPos(pos);
        buf.writeBlockPos(pos2);
        buf.writeInt(index);
        buf.writeBoolean(remove);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientHelpers.updateGeneratorOnClient(pos,pos2,index,remove);
        });
        ctx.get().setPacketHandled(true);
    }
}
