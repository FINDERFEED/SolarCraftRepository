package com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("open_puzzle_screen_packet")
public class OpenPuzzleScreenPacket extends FDPacket {

    private BlockPos tilePos;

    public OpenPuzzleScreenPacket(CrystalEnergyVinesTile tile){
        this.tilePos = tile.getBlockPos();
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.tilePos = buf.readBlockPos();

    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(tilePos);
    }

//    public void handle(PlayPayloadContext ctx){
//        ctx.enqueueWork(()-> ClientHelpers.handlePuzzlePacket(tilePos));
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.handlePuzzlePacket(tilePos);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
