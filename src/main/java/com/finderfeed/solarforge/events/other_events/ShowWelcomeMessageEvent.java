package com.finderfeed.solarforge.events.other_events;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.BookEntry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge")
public class ShowWelcomeMessageEvent {

    @SubscribeEvent
    public static void sendMessages(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() != null) {
            PlayerEntity player = event.getPlayer();


            if (!player.level.isClientSide) {
                Helpers.updateProgression((ServerPlayerEntity) player);
            }


            AncientFragment.initFragmentsMap();
            BookEntry.initMap();
            ProgressionHelper.initInfRecipesMap(event.getPlayer().level);
            ProgressionHelper.initSmeltingRecipesMap(event.getPlayer().level);
        }
    }
}
