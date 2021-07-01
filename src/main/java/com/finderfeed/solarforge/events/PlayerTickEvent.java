package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEvent {

    @SubscribeEvent
    public static void enterIncineratedForest(final TickEvent.PlayerTickEvent event){
        PlayerEntity entity = event.player;
        if (!entity.level.isClientSide  && !Helpers.hasPlayerUnlocked(Achievement.FIND_INCINERATED_FOREST,entity) && Helpers.canPlayerUnlock(Achievement.FIND_INCINERATED_FOREST,entity)){
            Optional<MutableRegistry<Biome>> reg = entity.level.registryAccess().registry(Registry.BIOME_REGISTRY);
            if ( reg.isPresent() && entity.level.getBiome(entity.blockPosition()).equals(reg.get().get(FeaturesRegistry.MOLTEN_BIOME_KEY))){

                    Helpers.setAchievementStatus(Achievement.FIND_INCINERATED_FOREST,entity,true);
                    Helpers.triggerToast(Achievement.FIND_INCINERATED_FOREST,entity);

            }
        }
    }
}
