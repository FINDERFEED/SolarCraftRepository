package com.finderfeed.solarforge.events;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RadiantChestplateMissChance {

    @SubscribeEvent
    public static void provideMissChance(final LivingAttackEvent event){
        if (event.getSource() != null && (event.getSource().getEntity() != null)){
            LivingEntity ent = event.getEntityLiving();
            ent.getArmorSlots().forEach((stack)->{
                if (stack.getItem().equals(ItemsRegister.RADIANT_CHESTPLATE.get())){
                    if (ent.level.random.nextFloat() <= 0.17){
                        event.setCanceled(true);
                    }
                }
            });
        }
    }

}
