package com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("puzzle_action_packet")
public class PuzzleActionPacket extends FDPacket {

    private BlockPos tilePos;
    private int moveType;

    public PuzzleActionPacket(int actionType, BlockPos pos){
        this.tilePos = pos;
        this.moveType = actionType;
    }


    public PuzzleActionPacket(FriendlyByteBuf buf) {
        this.tilePos = buf.readBlockPos();
        this.moveType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(tilePos);
        buf.writeInt(moveType);
    }

//    public void handle(PlayPayloadContext ctx){
//        ctx.enqueueWork(()-> {
//            ServerPlayer sender = ctx.getSender();
//            ServerLevel world = (ServerLevel) sender.level();
//            if (world.getBlockEntity(tilePos) instanceof CrystalEnergyVinesTile tile){
//                tile.handleAction(moveType);
//                Helpers.updateTile(tile);
//            }
//        });
//
//    }


    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        ServerPlayer sender = (ServerPlayer) ctx.player().get();
        ServerLevel world = (ServerLevel) sender.level();
        if (world.getBlockEntity(tilePos) instanceof CrystalEnergyVinesTile tile){
            tile.handleAction(moveType);
            Helpers.updateTile(tile);
        }
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
