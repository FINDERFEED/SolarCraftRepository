package com.finderfeed.solarforge.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractEnergyGeneratorTileEntity;
import com.finderfeed.solarforge.misc_things.AbstractSolarCore;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateCoreOnClient {
    public final int index;
    public final BlockPos pos;
    public final BlockPos pos2;
    public final boolean remove;
    public UpdateCoreOnClient(BlockPos pos,BlockPos pos2,int index,boolean remove){
        this.pos = pos;
        this.pos2 = pos2;
        this.index = index;
        this.remove = remove;
    }
    public UpdateCoreOnClient(PacketBuffer buf) {

        pos = buf.readBlockPos();
        pos2 = buf.readBlockPos();
        index = buf.readInt();
        remove = buf.readBoolean();

    }

    public void toBytes(PacketBuffer buf) {

        buf.writeBlockPos(pos);
        buf.writeBlockPos(pos2);
        buf.writeInt(index);
        buf.writeBoolean(remove);
    }


    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientHelpers.updateCoreOnClient(pos,pos2,index,remove);
        });
        ctx.get().setPacketHandled(true);
    }
}
