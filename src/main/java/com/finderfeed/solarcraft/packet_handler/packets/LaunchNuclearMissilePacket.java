package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarNuclearMissileLauncherTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LaunchNuclearMissilePacket {

    private BlockPos tilePos;
    private SolarNuclearMissileLauncherTileEntity.MissileData data;

    public LaunchNuclearMissilePacket(BlockPos tile, int xDest, int zDest, int radius, int depth){
        this.tilePos = tile;
        this.data = new SolarNuclearMissileLauncherTileEntity.MissileData(xDest, zDest, radius, depth);
    }

    public LaunchNuclearMissilePacket(FriendlyByteBuf buf){
        this.tilePos = buf.readBlockPos();
        this.data = new SolarNuclearMissileLauncherTileEntity.MissileData(
          buf.readInt(),
          buf.readInt(),
          buf.readInt(),
          buf.readInt()
        );
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(tilePos);
        buf.writeInt(data.xDest());
        buf.writeInt(data.zDest());
        buf.writeInt(data.radius());
        buf.writeInt(data.depth());
    }


    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ServerPlayer sender = ctx.get().getSender();
            Level level = sender.level;

            level.getChunk(data.xDest() >> 4,data.zDest() >> 4).setLoaded(true);

            if (level.getBlockEntity(tilePos) instanceof SolarNuclearMissileLauncherTileEntity tile){
                if (this.data.isValid()){
                    tile.setMissileData(this.data);
                }else{
                    tile.setMissileData(null);

                }
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
