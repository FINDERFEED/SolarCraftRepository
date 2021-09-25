package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntity;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class TakeEnergyFromForgePacket {

    public final BlockPos pos;

    public TakeEnergyFromForgePacket(FriendlyByteBuf buf){
        pos = buf.readBlockPos();
    }
    public TakeEnergyFromForgePacket(BlockPos pos){
        this.pos = pos;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(pos);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer player = ctx.get().getSender();
            ServerLevel level = (ServerLevel) player.level;
            if (level.getBlockEntity(pos) instanceof SolarForgeBlockEntity forge){
                int penergy = player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY);
                player.getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY,penergy+forge.getCurrentEnergy());
                forge.SOLAR_ENERGY_LEVEL = 0;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
