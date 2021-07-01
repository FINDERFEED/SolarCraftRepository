package com.finderfeed.solarforge.infusing_table_things;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateProgressOnClientPacket {
    public int maxProg;
    public int progress;
    public BlockPos pos;
    public boolean reqEnergy;
    public int energy;
    public UpdateProgressOnClientPacket(PacketBuffer buf){
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
    public void toBytes(PacketBuffer buf){
        buf.writeInt(this.maxProg);
        buf.writeInt(progress);
        buf.writeBlockPos(pos);
        buf.writeBoolean(reqEnergy);
        buf.writeInt(energy);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()->{
                ClientWorld world = Minecraft.getInstance().level;
                InfusingTableTileEntity tile = (InfusingTableTileEntity) world.getBlockEntity(pos);
                if (tile != null) {
                    tile.CURRENT_PROGRESS = progress;
                    tile.INFUSING_TIME = maxProg;
                    tile.requiresEnergy = reqEnergy;
                    tile.energy = energy;
                }

            });

        });
        ctx.get().setPacketHandled(true);
    }
}
