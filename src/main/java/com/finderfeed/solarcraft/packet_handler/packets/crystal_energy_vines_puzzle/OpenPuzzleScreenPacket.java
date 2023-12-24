package com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class OpenPuzzleScreenPacket {

    private BlockPos tilePos;

    public OpenPuzzleScreenPacket(CrystalEnergyVinesTile tile){
        this.tilePos = tile.getBlockPos();
    }

    public OpenPuzzleScreenPacket(FriendlyByteBuf buf){
        this.tilePos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(tilePos);
    }

    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()-> ClientHelpers.handlePuzzlePacket(tilePos));
        ctx.setPacketHandled(true);
    }

}
