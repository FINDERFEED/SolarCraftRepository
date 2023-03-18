package com.finderfeed.solarcraft.events.other_events;


import com.finderfeed.solarcraft.content.items.TotemOfImmortality;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.ProcImmortalityTotemAnimation;
import com.finderfeed.solarcraft.registries.effects.SolarcraftEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.network.NetworkDirection;


@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeathEvent {


    @SubscribeEvent
    public static void procImmortalityTotem(final LivingDeathEvent event){
        if (event.getEntity() instanceof Player && !event.getEntity().level.isClientSide){
            Player player = (Player) event.getEntity();
            int slot = findImmortalityTotem(player);
            if (slot != -10000){
                player.addEffect(new MobEffectInstance(SolarcraftEffects.IMMORTALITY_EFFECT.get(),400,0));
                player.setHealth(player.getMaxHealth());

                player.getInventory().setItem(slot, ItemStack.EMPTY);
                ServerLevel world = (ServerLevel)player.level;
                world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.TOTEM_USE, SoundSource.AMBIENT,0.5f,0.5f);
                SCPacketHandler.INSTANCE.sendTo(new ProcImmortalityTotemAnimation(),((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);

                event.setCanceled(true);
            }
        }

    }


    public static int findImmortalityTotem(Player player){

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                if (player.getInventory().getItem(i).getItem() instanceof TotemOfImmortality) {
                    return i;
                }
            }



        return -10000;
    }
}
