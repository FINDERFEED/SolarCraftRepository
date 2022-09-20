package com.finderfeed.solarforge.events.other_events;


import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.abilities.AbilityHelper;
import com.finderfeed.solarforge.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarforge.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarforge.config.JsonFragmentsHelper;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = SolarForge.MOD_ID)
public class OnPlayerJoin {

    @SubscribeEvent
    public static void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() != null) {
            Player player = event.getPlayer();
            if (player instanceof  ServerPlayer sPlayer) {
                if (!sPlayer.getPersistentData().getBoolean("recieved_solar_lexicon")){
                    sPlayer.addItem(SolarcraftItems.SOLAR_LEXICON.get().getDefaultInstance());
                }

                for (RunicEnergy.Type type : RunicEnergy.Type.values()) {
                    Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(event.getPlayer(), type), event.getPlayer());
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

}
