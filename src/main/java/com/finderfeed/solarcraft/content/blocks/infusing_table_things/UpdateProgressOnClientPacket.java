package com.finderfeed.solarcraft.content.blocks.infusing_table_things;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;




public class UpdateProgressOnClientPacket {
    public int maxProg;
    public int progress;
    public BlockPos pos;
    public boolean reqEnergy;
    public int energy;
    public UpdateProgressOnClientPacket(FriendlyByteBuf buf){
        this.maxProg = buf.readInt();
        this.progress = buf.readInt();
        this.pos = buf.readBlockPos();
        this.reqEnergy = buf.readBoolean();
        this.energy = buf.readInt();
    }
    public UpdateProgressOnClientPacket(int maxProg,int progress, BlockPos pos,boolean reqEnergy,int energy){
        this.maxProg = maxProg;
        this.progress = progress;
        this.pos = pos;
        this.reqEnergy = reqEnergy;
        this.energy = energy;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(this.maxProg);
        buf.writeInt(progress);
        buf.writeBlockPos(pos);
        buf.writeBoolean(reqEnergy);
        buf.writeInt(energy);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()->{
                ClientLevel world = Minecraft.getInstance().level;
                InfuserTileEntity tile = (InfuserTileEntity) world.getBlockEntity(pos);
                if (tile != null) {
                    tile.currentTime = progress;
                    tile.infusingTime = maxProg;
                    tile.requiresEnergy = reqEnergy;
                    tile.solarEnergy = energy;
                }

            });

        });
        ctx.get().setPacketHandled(true);
    }
}
