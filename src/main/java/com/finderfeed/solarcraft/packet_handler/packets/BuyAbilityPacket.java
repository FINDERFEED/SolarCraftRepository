package com.finderfeed.solarcraft.packet_handler.packets;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.apache.logging.log4j.Level;

@Packet("buy_ability_packet")
public class BuyAbilityPacket extends FDPacket {
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

    public static void handle(BuyAbilityPacket packet, PlayPayloadContext ctx){
        ServerPlayer player = (ServerPlayer) ctx.player().get();
        try {
            AbstractAbility ability = AbilitiesRegistry.getAbilityByID(packet.id);
            int en = packet.getPlayerEnergy(player);
            if (en >= ability.buyCost){
                if (!AbilityHelper.isAbilityBought(player,ability)) {
                    packet.spendEnergy(player, ability.buyCost);
                    AbilityHelper.setAbilityUsable(player,ability,true);
                }
            }
        }catch (Exception e){
            SolarCraft.LOGGER.log(Level.ERROR,"Exception caught during BuyAbilityPacket handling.");
            e.printStackTrace();
        }
    }

    private void spendEnergy(ServerPlayer player,int toSpend){
        player.getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY,getPlayerEnergy(player)-toSpend);
    }

    private int getPlayerEnergy(ServerPlayer player){
        return player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}