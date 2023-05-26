package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarOrbitalMissileLauncherTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LaunchOrbitalMissilePacket {

    private BlockPos tilePos;
    private boolean cancel;
    private SolarOrbitalMissileLauncherTileEntity.MissileData data;

    public LaunchOrbitalMissilePacket(BlockPos tile, int xDest, int zDest, int radius, int depth,boolean cancel){
        this.tilePos = tile;
        this.data = new SolarOrbitalMissileLauncherTileEntity.MissileData(xDest, zDest, radius, depth);
        this.cancel = cancel;
    }

    public LaunchOrbitalMissilePacket(FriendlyByteBuf buf){
        this.tilePos = buf.readBlockPos();
        this.data = new SolarOrbitalMissileLauncherTileEntity.MissileData(
          buf.readInt(),
          buf.readInt(),
          buf.readInt(),
          buf.readInt()
        );
        this.cancel = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(tilePos);
        buf.writeInt(data.xDest());
        buf.writeInt(data.zDest());
        buf.writeInt(data.radius());
        buf.writeInt(data.depth());
        buf.writeBoolean(this.cancel);
    }


    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ServerPlayer sender = ctx.get().getSender();
            Level level = sender.level;

            level.getChunk(data.xDest() >> 4,data.zDest() >> 4).setLoaded(true);

            if (level.getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity tile){
                if (!this.cancel) {
                    this.setExplosion(sender,tile);
                }else{
                    tile.setMissileData(null);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private void setExplosion(ServerPlayer sender,SolarOrbitalMissileLauncherTileEntity tile){
        if (this.data.isValid()) {
            if (tile.getMissileData() == null) {
                tile.setMissileData(this.data);
            }else{
                sender.sendSystemMessage(Component.translatable("solarcraft.tile.orbital_missile.launch.is_already_in_process"));

            }
        } else {
            tile.setMissileData(null);
            sender.sendSystemMessage(Component.translatable("solarcraft.tile.orbital_missile.launch.data_not_correct"));
        }
    }


}
