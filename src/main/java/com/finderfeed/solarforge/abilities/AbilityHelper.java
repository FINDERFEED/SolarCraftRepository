package com.finderfeed.solarforge.abilities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarforge.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.ToggleableAbilityPacket;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;

import java.util.Locale;

public class AbilityHelper {


    public static void notEnoughEnergyMessage(Player player,AbstractAbility ability){
        for (RunicEnergy.Type type : ability.getCost().getSetTypes()){
            float pl = RunicEnergy.getEnergy(player,type);
            if (pl < ability.getCost().get(type)){
                player.sendMessage(new TranslatableComponent("solarcraft.not_enough_runic_energy").append(" " + type.id.toUpperCase(Locale.ROOT)),
                        player.getUUID());
            }
        }
    }

    public static boolean isAbilityUsable(Player player, AbstractAbility ability){
        if (player.isCreative()) return true;
        if (isAbilityBought(player,ability)){
            RunicEnergyCost cost = ability.cost;
            for (RunicEnergy.Type type : cost.getSetTypes()){
                if (RunicEnergy.getEnergy(player,type) < cost.get(type)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void spendAbilityEnergy(Player player,AbstractAbility ability){
        if (player.isCreative()) return;
        RunicEnergyCost cost = ability.cost;
        for (RunicEnergy.Type type : cost.getSetTypes()){
            RunicEnergy.spendEnergy(player,cost.get(type),type);
            Helpers.updateRunicEnergyOnClient(type,RunicEnergy.getEnergy(player,type),player);
        }
    }

    public static void refundEnergy(Player player,AbstractAbility ability){
        if (player.isCreative()) return;
        RunicEnergyCost cost = ability.cost;
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
        SolarForgePacketHandler.INSTANCE.sendTo(new ToggleableAbilityPacket(ability,toggle),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void setAbilityUsable(Player player,AbstractAbility ability,boolean usable){
        player.getPersistentData().putBoolean(SolarCraftTags.CAN_PLAYER_USE + ability.id, usable);
    }
}
