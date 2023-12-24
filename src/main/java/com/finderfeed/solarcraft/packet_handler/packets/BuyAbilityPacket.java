package com.finderfeed.solarcraft.packet_handler.packets;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkEvent;
import org.apache.logging.log4j.Level;


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

    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()->{
            ServerPlayer player = ctx.getSender();
            try {
                AbstractAbility ability = AbilitiesRegistry.getAbilityByID(id);
                int en = getPlayerEnergy(player);
                if (en >= ability.buyCost){
                    if (!AbilityHelper.isAbilityBought(player,ability)) {
                        spendEnergy(player, ability.buyCost);
                        AbilityHelper.setAbilityUsable(player,ability,true);
                    }
                }
            }catch (Exception e){
                SolarCraft.LOGGER.log(Level.ERROR,"Exception caught during BuyAbilityPacket handling.");
                e.printStackTrace();
            }


        });
        ctx.setPacketHandled(true);
    }

    private void spendEnergy(ServerPlayer player,int toSpend){
        player.getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY,getPlayerEnergy(player)-toSpend);
    }

    private int getPlayerEnergy(ServerPlayer player){
        return player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY);
    }
}