package com.finderfeed.solarforge.other_events;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.solar_lexicon.packets.UpdateAllProgressionOnClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge")
public class ShowWelcomeMessageEvent {

    @SubscribeEvent
    public static void sendMessages(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() != null) {
            PlayerEntity player = event.getPlayer();
            event.getPlayer().sendMessage(new TranslationTextComponent("solarcraft.welcome_message"), event.getPlayer().getUUID());
            event.getPlayer().sendMessage(new TranslationTextComponent("solarcraft.welcome_message2"), event.getPlayer().getUUID());
            if (!player.level.isClientSide) {
                Helpers.updateProgression((ServerPlayerEntity) player);
            }
        }
    }
}
