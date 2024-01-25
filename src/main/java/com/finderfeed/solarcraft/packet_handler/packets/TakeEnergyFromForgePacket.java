package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockEntity;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("take_energy_from_forge_packet")
public class TakeEnergyFromForgePacket extends FDPacket {

    public BlockPos pos;
    @Override
    public void read(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
    }

    public TakeEnergyFromForgePacket(BlockPos pos){
        this.pos = pos;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(pos);
    }
    public void handle(PlayPayloadContext ctx){
        
            ServerPlayer player = (ServerPlayer) ctx.player().get();
            ServerLevel level = (ServerLevel) player.level();
            if (level.getBlockEntity(pos) instanceof SolarForgeBlockEntity forge){
                int penergy = player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY);
                player.getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY,penergy+forge.getCurrentEnergy());
                forge.SOLAR_ENERGY_LEVEL = 0;
            }
    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        this.handle(ctx);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
