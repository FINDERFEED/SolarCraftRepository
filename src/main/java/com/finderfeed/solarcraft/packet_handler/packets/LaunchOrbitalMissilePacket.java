package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.SolarOrbitalMissileLauncherTileEntity;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("launch_orbital_missile")
public class LaunchOrbitalMissilePacket extends FDPacket {

    private BlockPos tilePos;
    private boolean cancel;
    private SolarOrbitalMissileLauncherTileEntity.MissileData data;

    public LaunchOrbitalMissilePacket(BlockPos tile, int xDest, int zDest, int radius, int depth,boolean cancel){
        this.tilePos = tile;
        this.data = new SolarOrbitalMissileLauncherTileEntity.MissileData(xDest, zDest, radius, depth);
        this.cancel = cancel;
    }



    @Override
    public void read(FriendlyByteBuf buf) {
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


//    public void handle(PlayPayloadContext ctx) {
//
//            ServerPlayer sender = ctx.getSender();
//            Level level = sender.level();
//
//            if (!SolarcraftConfig.IS_ORBITAL_MISSILE_LAUNCHER_ALLOWED.get()){
//                sender.sendSystemMessage(Component.translatable("solarcraft.message.block_disabled").withStyle(ChatFormatting.RED));
//                return;
//            }
//
//            if (level.getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity tile){
//                if (Multiblocks.ORBITAL_MISSILE_LAUNCHER.check(level,tilePos,true)) {
//                    level.getChunk(data.xDest() >> 4,data.zDest() >> 4).setLoaded(true);
//                    if (!this.cancel) {
//                        this.setExplosion(sender, tile);
//                    } else {
//                        tile.setMissileData(null);
//                    }
//                }else{
//                    sender.sendSystemMessage(Component.translatable("solarcraft.message.incorrect_structure").withStyle(ChatFormatting.RED));
//                }
//            }
//
//    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        ServerPlayer sender = (ServerPlayer) ctx.player().get();
        Level level = sender.level();

        if (!SolarcraftConfig.IS_ORBITAL_MISSILE_LAUNCHER_ALLOWED.get()){
            sender.sendSystemMessage(Component.translatable("solarcraft.message.block_disabled").withStyle(ChatFormatting.RED));
            return;
        }

        if (level.getBlockEntity(tilePos) instanceof SolarOrbitalMissileLauncherTileEntity tile){
            if (Multiblocks.ORBITAL_MISSILE_LAUNCHER.check(level,tilePos,true)) {
                level.getChunk(data.xDest() >> 4,data.zDest() >> 4).setLoaded(true);
                if (!this.cancel) {
                    this.setExplosion(sender, tile);
                } else {
                    tile.setMissileData(null);
                }
            }else{
                sender.sendSystemMessage(Component.translatable("solarcraft.message.incorrect_structure").withStyle(ChatFormatting.RED));
            }
        }
    }

    private void setExplosion(ServerPlayer sender, SolarOrbitalMissileLauncherTileEntity tile){
        if (this.data.isValid()) {
            if (tile.getMissileData() == null) {
                tile.setMissileData(this.data);
            }else{
                sender.sendSystemMessage(Component.translatable("solarcraft.tile.orbital_missile.launch.is_already_in_process").withStyle(ChatFormatting.RED));

            }
        } else {
            tile.setMissileData(null);
            sender.sendSystemMessage(Component.translatable("solarcraft.tile.orbital_missile.launch.data_not_correct").withStyle(ChatFormatting.RED));
        }
    }


    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
