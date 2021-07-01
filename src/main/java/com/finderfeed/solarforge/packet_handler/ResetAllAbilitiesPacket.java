package com.finderfeed.solarforge.packet_handler;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ResetAllAbilitiesPacket {

    public ResetAllAbilitiesPacket(PacketBuffer buf){


    }
    public ResetAllAbilitiesPacket(){

    }
    public void toBytes(PacketBuffer buf){

    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayerEntity enti = ctx.get().getSender();
            PlayerEntity entity = (PlayerEntity)enti;
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_fireball",false);
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_lightning",false);
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_solar_strike",false);
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_solar_stun",false);
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_meteorite",false);
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_solar_heal",false);
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_alchemist",false);
            enti.getPersistentData().putBoolean("solar_forge_can_player_use_solar_dispel",false);
            for (Achievement a : Achievement.ALL_ACHIEVEMENTS){
                Helpers.setAchievementStatus(a,entity,false);

            }

        });
        ctx.get().setPacketHandled(true);
    }
}
