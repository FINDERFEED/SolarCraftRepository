package com.finderfeed.solarcraft.events;

import com.finderfeed.solarcraft.registries.items.SolarcraftItems;

import net.minecraft.world.entity.LivingEntity;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RadiantChestplateMissChance {

    @SubscribeEvent
    public static void provideMissChance(final LivingAttackEvent event){
        if (event.getSource() != null && (event.getSource().getEntity() != null)){
            LivingEntity ent = event.getEntity();
            ent.getArmorSlots().forEach((stack)->{
                if (stack.getItem().equals(SolarcraftItems.RADIANT_CHESTPLATE.get())){
                    if (ent.level.random.nextFloat() <= 0.17){
                        event.setCanceled(true);
                    }
                }
            });
        }
    }

}
