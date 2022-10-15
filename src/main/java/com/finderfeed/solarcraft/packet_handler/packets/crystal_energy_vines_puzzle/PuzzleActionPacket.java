package com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PuzzleActionPacket {

    private BlockPos tilePos;
    private final int moveType;

    public PuzzleActionPacket(int actionType, BlockPos pos){
        this.tilePos = pos;
        this.moveType = actionType;
    }

    public PuzzleActionPacket(FriendlyByteBuf buf){
        this.tilePos = buf.readBlockPos();
        this.moveType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(tilePos);
        buf.writeInt(moveType);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> {
            ServerPlayer sender = ctx.get().getSender();
            ServerLevel world = sender.getLevel();
            if (world.getBlockEntity(tilePos) instanceof CrystalEnergyVinesTile tile){
                tile.handleAction(moveType);
                Helpers.updateTile(tile);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
