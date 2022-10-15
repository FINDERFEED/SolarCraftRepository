package com.finderfeed.solarcraft.events.other_events;


import com.finderfeed.solarcraft.registries.effects.SolarcraftEffects;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerDamageTakenEvent {

    @SubscribeEvent
    public static void damageTaken(final LivingDamageEvent event){
        if ((event.getEntity()).hasEffect(SolarcraftEffects.IMMORTALITY_EFFECT.get()) ){
            event.setCanceled(true);
        }
    }
}
