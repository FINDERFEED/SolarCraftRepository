package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.Registry;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

//@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEvent {



    @SubscribeEvent
    public void enterIncineratedForest(final TickEvent.PlayerTickEvent event){
        Player entity = event.player;
        if (!entity.level.isClientSide ){
            Optional<? extends Registry<Biome>> reg = entity.level.registryAccess().registry(Registry.BIOME_REGISTRY);
            if ( reg.isPresent() && entity.level.getBiome(entity.blockPosition()).equals(reg.get().get(FeaturesRegistry.MOLTEN_BIOME_KEY))){

                Helpers.fireProgressionEvent(entity,Achievement.FIND_INCINERATED_FOREST);

            }

            if (entity.level.dimension() == Level.NETHER){
                Helpers.fireProgressionEvent(entity,Achievement.ENTER_NETHER);
            }
        }


    }
}
