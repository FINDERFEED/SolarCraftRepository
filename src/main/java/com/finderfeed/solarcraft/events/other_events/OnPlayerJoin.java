package com.finderfeed.solarcraft.events.other_events;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = SolarCraft.MOD_ID)
public class OnPlayerJoin {
/*
    @SubscribeEvent
    public static void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() != null) {
            Player player = event.getEntity();
            if (player instanceof  ServerPlayer sPlayer) {

                for (RunicEnergy.Type type : RunicEnergy.Type.values()) {
                    Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(player, type), player);
                }
                Helpers.updateProgression(sPlayer);

                if (JsonFragmentsHelper.fragmentsShouldBeRead()) {
                    List<AncientFragment> fragsDes = AncientFragment.deserializeFragments(JsonFragmentsHelper.readFragments());

                    if (fragsDes != null) {
                        AncientFragment.ALL_FRAGMENTS.addAll(fragsDes);
                    }
                }
                for (ToggleableAbility ability : AbilitiesRegistry.getToggleableAbilities()) {
                    AbilityHelper.sendTogglePacket(sPlayer,ability,ability.isToggled(sPlayer));
                }

                JsonFragmentsHelper.sendUpdatePacketToClient(sPlayer);
                if (EnchanterConfigInit.shouldBeRead()) {
                    EnchanterConfigInit.readJson();
                }
                Helpers.updateFragmentsOnClient(sPlayer);
                Helpers.updateClientRadiantLandStateForPlayer(sPlayer);
                AncientFragment.initFragmentsMap();
                for (AncientFragment fr : AncientFragment.ALL_FRAGMENTS){
                    fr.getReferences();
                }
            }

        }
    }

    @SubscribeEvent
    public static void initServerConfigs(ServerStartedEvent event){
        if (JsonFragmentsHelper.fragmentsShouldBeRead()){
            List<AncientFragment> fragsDes = AncientFragment.deserializeFragments(JsonFragmentsHelper.readFragments());

            if (fragsDes != null) {
                AncientFragment.ALL_FRAGMENTS.addAll(fragsDes);
            }
        }
        if (EnchanterConfigInit.shouldBeRead()) {
            EnchanterConfigInit.readJson();
        }
    }
*/
}
