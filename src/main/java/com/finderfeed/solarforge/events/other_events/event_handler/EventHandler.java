package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.graalvm.compiler.serviceprovider.ServiceProvider;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event){
        event.getGeneration().addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, FeaturesRegistry.ENERGY_PYLON_CONFIGURED);
        if (event.getCategory() == Biome.Category.PLAINS){
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,FeaturesRegistry.RUNIC_TREE_FEATURE);
        }
    }


    @SubscribeEvent
    public static void progressionUnlockEvent(ProgressionUnlockEvent event){
        Achievement ach = event.getProgression();
        PlayerEntity playerEntity = event.getPlayer();
        if (!Helpers.hasPlayerUnlocked(ach,playerEntity) && Helpers.canPlayerUnlock(ach,playerEntity)){
            Helpers.setAchievementStatus(ach, playerEntity,true);
            Helpers.triggerToast(ach, playerEntity);
            Helpers.updateProgression((ServerPlayerEntity)playerEntity );
            Helpers.forceChunksReload((ServerPlayerEntity) playerEntity);
            Helpers.triggerProgressionShader(playerEntity);

            if (ach.equals(Achievement.FRAGMENT)){
                ItemStack frag = ItemsRegister.INFO_FRAGMENT.get().getDefaultInstance();
                ProgressionHelper.applyTagToFragment(frag, AncientFragment.FRAGMENT);
                ItemEntity entity = new ItemEntity(playerEntity.level,playerEntity.getX(),playerEntity.getY()+0.3f,playerEntity.getZ(),frag);

                ItemStack frag2 = ItemsRegister.INFO_FRAGMENT.get().getDefaultInstance();
                ProgressionHelper.applyTagToFragment(frag2, AncientFragment.RUNIC_TABLE);
                ItemEntity entity2 = new ItemEntity(playerEntity.level,playerEntity.getX(),playerEntity.getY()+0.3f,playerEntity.getZ(),frag2);
                ProgressionHelper.givePlayerFragment(AncientFragment.FRAGMENT,playerEntity);
                ProgressionHelper.givePlayerFragment(AncientFragment.RUNIC_TABLE,playerEntity);
                playerEntity.level.addFreshEntity(entity);
                playerEntity.level.addFreshEntity(entity2);
            }else if (ach.equals(Achievement.RUNE_ENERGY_DEPOSIT)){
                ItemStack frag = ItemsRegister.INFO_FRAGMENT.get().getDefaultInstance();
                ProgressionHelper.applyTagToFragment(frag, AncientFragment.WAND);
                ItemEntity entity = new ItemEntity(playerEntity.level,playerEntity.getX(),playerEntity.getY()+0.3f,playerEntity.getZ(),frag);
                ProgressionHelper.givePlayerFragment(AncientFragment.WAND,playerEntity);
                playerEntity.level.addFreshEntity(entity);
            }
        }

    }

}
