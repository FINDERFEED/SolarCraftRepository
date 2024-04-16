package com.finderfeed.solarcraft.content.abilities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.ToggleableAbilityPacket;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Locale;

public class AbilityHelper {


    public static void sendMessages(Player player, AbstractAbility ability){
        for (RunicEnergy.Type type : ability.getCastCost().getSetTypes()){
            float amount = RunicEnergy.getEnergy(player,type);
            if (amount < ability.getCastCost().get(type)){
                player.sendSystemMessage(Component.translatable("solarcraft.not_enought_runic_energy")
                        .append(Component.literal(" " + type.id.toUpperCase(Locale.ROOT) + ", "))
                        .append(Component.translatable("solarcraft.not_enought_runic_energy_needed"))
                        .append(ability.getCastCost().get(type) + ""));
            }
        }
    }

    public static boolean isAbilityUsable(Player player, AbstractAbility ability,boolean sendMessages){
        if (player.isCreative()) return true;
        if (isAbilityBought(player,ability)){
            RunicEnergyCost cost = ability.getCastCost();
            for (RunicEnergy.Type type : cost.getSetTypes()){
                if (RunicEnergy.getEnergy(player,type) < cost.get(type)){
                    if (sendMessages) sendMessages(player,ability);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void spendAbilityEnergy(Player player,AbstractAbility ability){
        if (player.isCreative()) return;
        RunicEnergyCost cost = ability.getCastCost();
        for (RunicEnergy.Type type : cost.getSetTypes()){
            RunicEnergy.spendEnergy(player,cost.get(type),type);
            Helpers.updateRunicEnergyOnClient(type,RunicEnergy.getEnergy(player,type),player);
        }
    }

    public static void refundEnergy(Player player,AbstractAbility ability){
        if (player.isCreative()) return;
        RunicEnergyCost cost = ability.getCastCost();
        for (RunicEnergy.Type type : cost.getSetTypes()){
            RunicEnergy.givePlayerEnergy(player,cost.get(type),type);
            Helpers.updateRunicEnergyOnClient(type,RunicEnergy.getEnergy(player,type),player);
        }
    }


    public static boolean isAbilityBought(Player player,AbstractAbility ability){
        return player.getPersistentData().getBoolean(SolarCraftTags.CAN_PLAYER_USE +ability.id);
    }

    public static String getBindedAbilityID(Player player,int index){
        return player.getPersistentData().getString("solar_forge_ability_binded_" + index);
    }

    public static void castAbility(ServerLevel world, ServerPlayer enti, String id) {
        if (!enti.isSpectator()) {
            AbstractAbility ability = AbilitiesRegistry.getAbilityByID(id);
            if (ability != null){
                ability.cast(enti,world);
                for (RunicEnergy.Type type : RunicEnergy.Type.values()) {
                    Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(enti, type), enti);
                }
            }
        }
    }

    public static void sendTogglePacket(ServerPlayer player, ToggleableAbility ability,boolean toggle){
        FDPacketUtil.sendToPlayer(player,new ToggleableAbilityPacket(ability,toggle));
//        SCPacketHandler.INSTANCE.sendTo(new ToggleableAbilityPacket(ability,toggle),player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
    }

    public static void setAbilityUsable(Player player,AbstractAbility ability,boolean usable){
        player.getPersistentData().putBoolean(SolarCraftTags.CAN_PLAYER_USE + ability.id, usable);
    }
}
