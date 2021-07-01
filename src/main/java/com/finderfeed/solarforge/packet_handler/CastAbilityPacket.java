package com.finderfeed.solarforge.packet_handler;

import com.finderfeed.solarforge.SolarAbilities.SolarAbilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CastAbilityPacket {
    private final int index;
    public CastAbilityPacket(PacketBuffer buf){
        index = buf.readInt();

    }
    public CastAbilityPacket(int index){
    this.index = index;
    }
    public void toBytes(PacketBuffer buf){
        buf.writeInt(index);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayerEntity enti = ctx.get().getSender();
            PlayerEntity entity = (PlayerEntity)enti;

            SolarAbilities.castAbility(enti.getLevel(),enti,enti.getPersistentData().getInt("solar_forge_ability_binded_"+Integer.toString(index)));
        });
        ctx.get().setPacketHandled(true);
    }
}
