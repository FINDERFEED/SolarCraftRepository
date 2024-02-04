package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("request_login_data")
public class RequestLoginDataPacket extends FDPacket {

    public RequestLoginDataPacket(){}

    public RequestLoginDataPacket(FriendlyByteBuf buf){

    }

    @Override
    public void write(FriendlyByteBuf buf) {

    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        var opt = ctx.player();
        if (opt.isPresent() && opt.get() instanceof ServerPlayer sPlayer){
            FDPacketUtil.sendToPlayer(sPlayer,new SendConfigsToClientPacket());


            for (RunicEnergy.Type type : RunicEnergy.Type.values()) {
                Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(sPlayer, type), sPlayer);
            }
            Helpers.updateProgressionsOnClient(sPlayer);

            for (ToggleableAbility ability : AbilitiesRegistry.getToggleableAbilities()) {
                AbilityHelper.sendTogglePacket(sPlayer,ability,ability.isToggled(sPlayer));

            }
            JsonFragmentsHelper.sendUpdatePacketToClient(sPlayer);
            Helpers.updateFragmentsOnClient(sPlayer);
            Helpers.updateClientRadiantLandStateForPlayer(sPlayer);
        }
    }
}
