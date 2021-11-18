package com.finderfeed.solarforge.events.other_events;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.BookEntry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge")
public class ShowWelcomeMessageEvent {

    @SubscribeEvent
    public static void sendMessages(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() != null) {
            Player player = event.getPlayer();
            for (RunicEnergy.Type type : RunicEnergy.Type.values()){
                Helpers.updateRunicEnergyOnClient(type,RunicEnergy.getEnergy(event.getPlayer(),type),event.getPlayer());
            }

            if (!player.level.isClientSide) {
                Helpers.updateProgression((ServerPlayer) player);
            }


            AncientFragment.initFragmentsMap();
            BookEntry.initMap();
            ProgressionHelper.initInfRecipesMap(event.getPlayer().level);
            ProgressionHelper.initSmeltingRecipesMap(event.getPlayer().level);
            ProgressionHelper.initInfusingCraftingRecipes(event.getPlayer().level);
        }
    }
}
