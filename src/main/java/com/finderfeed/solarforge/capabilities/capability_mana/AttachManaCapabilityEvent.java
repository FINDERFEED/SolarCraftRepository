package com.finderfeed.solarforge.capabilities.capability_mana;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.ToggleAlchemistPacket;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.network.NetworkDirection;


public class AttachManaCapabilityEvent {
    public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof PlayerEntity) {
            PlayerManaProvider provide = new PlayerManaProvider();
            event.addCapability(new ResourceLocation("solarforge", "solar_mana_level"), provide);
            event.addListener(provide::invalidate);
        }
    }






    public static void tickEvent(final TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if (!player.level.isClientSide) {
            double mana = player.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).getMana();

                mana += 0.10d;
                if (player.inventory.contains(ItemsRegister.SOLAR_MANA_AMULET.get().getDefaultInstance())){
                    mana+=0.10d;
                }
                if (!player.isDeadOrDying()) {
                    if (mana <= 3000) {
                        player.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).setMana(mana);
                    }else {
                        player.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).setMana(3000);
                    }
                        ServerPlayerEntity playerServer = (ServerPlayerEntity) player;

                    SolarForgePacketHandler.INSTANCE.sendTo(new UpdateManaPacket(mana), playerServer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    if (player.getPersistentData().getBoolean("is_alchemist_toggled") && !player.isCreative() &&
                            player.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).getMana() >= 0.5
                    && player.getPersistentData().getBoolean("solar_forge_can_player_use_alchemist")){
                        player.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).setMana(
                        player.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).getMana()-0.5
                        );
                    }
                }





            if (player.getPersistentData().getBoolean("is_alchemist_toggled") && player.getPersistentData().getBoolean("solar_forge_can_player_use_alchemist")) {
                SolarForgePacketHandler.INSTANCE.sendTo(new ToggleAlchemistPacket(true), ((ServerPlayerEntity)event.player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            } else {
                SolarForgePacketHandler.INSTANCE.sendTo(new ToggleAlchemistPacket(false), ((ServerPlayerEntity)event.player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }

        }
    }
}
