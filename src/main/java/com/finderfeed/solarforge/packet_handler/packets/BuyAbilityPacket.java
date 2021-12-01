package com.finderfeed.solarforge.packet_handler.packets;


import com.finderfeed.solarforge.SolarAbilities.Abilities;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.SolarCraftTags;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public class BuyAbilityPacket {
    private final String id;

    public BuyAbilityPacket(FriendlyByteBuf buf){
        id = buf.readUtf();
    }
    public BuyAbilityPacket(String str){
        id = str;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            try {
                AbstractAbility ability = Abilities.BY_IDS.get(id).getAbility();
                int en = getPlayerEnergy(enti);
                if (en >= ability.buyCost){
                    if (!enti.getPersistentData().getBoolean(SolarCraftTags.CAN_PLAYER_USE+id)) {
                        spendEnergy(enti, ability.buyCost);
                        enti.getPersistentData().putBoolean(SolarCraftTags.CAN_PLAYER_USE + id, true);
                    }
                }
            }catch (Exception e){
                System.out.println("Exception caught during BuyAbilityPacket handling.");
                e.printStackTrace();
            }


        });
        ctx.get().setPacketHandled(true);
    }

    private void spendEnergy(ServerPlayer player,int toSpend){
        player.getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY,getPlayerEnergy(player)-toSpend);
    }

    private int getPlayerEnergy(ServerPlayer player){
        return player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY);
    }
}