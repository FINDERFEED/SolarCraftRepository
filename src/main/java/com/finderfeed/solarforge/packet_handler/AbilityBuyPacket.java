package com.finderfeed.solarforge.packet_handler;

import com.finderfeed.solarforge.SolarAbilities.SolarAbilities;
import com.finderfeed.solarforge.solar_forge_block.SolarForgeBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityBuyPacket {
    private final String str;
    private final BlockPos pos;
    private final int amount;
    public AbilityBuyPacket(PacketBuffer buf){
    str = buf.readUtf(20);
    pos = buf.readBlockPos();
    amount = buf.readInt();
    }
    public AbilityBuyPacket(String str,BlockPos pos,int amount){
        this.str = str;
        this.pos = pos;
        this.amount = amount;

    }
    public void toBytes(PacketBuffer buf){
    buf.writeUtf(str);
    buf.writeBlockPos(pos);
    buf.writeInt(amount);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayerEntity enti = ctx.get().getSender();
            PlayerEntity entity = (PlayerEntity)enti;

            if (!enti.getPersistentData().getBoolean("solar_forge_can_player_use_"+str) && enti.level.getBlockEntity(pos) != null && enti.level.getBlockEntity(pos) instanceof SolarForgeBlockEntity) {
                SolarForgeBlockEntity blockEntity = (SolarForgeBlockEntity) enti.level.getBlockEntity(pos);
                if (blockEntity.getCurrentEnergy() >= amount) {
                    System.out.println("ability buy");
                    blockEntity.SOLAR_ENERGY_LEVEL-=amount;
                    enti.getPersistentData().putBoolean("solar_forge_can_player_use_" + str, true);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
