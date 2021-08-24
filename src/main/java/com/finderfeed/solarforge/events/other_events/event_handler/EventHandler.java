package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.SolarForgeMana;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.magic_items.items.ExperienceCrystal;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.registries.SolarcraftDamageSources;
import com.finderfeed.solarforge.registries.effects.EffectsRegister;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.item.ItemStack;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    public static final ResourceKey<Level> RADIANT_LAND_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,new ResourceLocation("solarforge","radiant_land"));




    @SubscribeEvent
    public static void playerTickEvent(final TickEvent.PlayerTickEvent event){
        Player player = event.player;
        Level world = player.level;

        if (!world.isClientSide && !player.isCreative()){
            if ((world.getGameTime() % 20 == 1)&& !Helpers.isDay(world) &&  (world.dimension() == RADIANT_LAND_KEY)){
                player.addEffect(new MobEffectInstance(EffectsRegister.STAR_GAZE_EFFECT.get(),400,0));
            }

            if (player.hasEffect(EffectsRegister.STAR_GAZE_EFFECT.get())){
                if (world.getGameTime() % 80 == 1){
                    DamageSource src = SolarcraftDamageSources.STARGAZE.bypassArmor().setMagic();
                    player.hurt(src,4);
                }
            }
        }
    }



    @SubscribeEvent
    public static void handleExperienceCrystal(PlayerXpEvent.PickupXp event){
        ExperienceOrb orb = event.getOrb();
        Player player = event.getPlayer();
        if (!player.level.isClientSide){
            if (ExperienceCrystal.consumeExperience(player,orb.value)) {
                orb.remove(Entity.RemovalReason.DISCARDED);
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event){
        if ( (event.getCategory() != Biome.BiomeCategory.NETHER) && (event.getCategory() != Biome.BiomeCategory.THEEND) && notNone(event))
        event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeaturesRegistry.ENERGY_PYLON_CONFIGURED);
        if (event.getCategory() == Biome.BiomeCategory.PLAINS){
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,FeaturesRegistry.RUNIC_TREE_FEATURE);
        }
    }

    private static boolean notNone(BiomeLoadingEvent event){
        return event.getCategory() != Biome.BiomeCategory.NONE;
    }

    @SubscribeEvent
    public static void progressionUnlockEvent(ProgressionUnlockEvent event){
        Achievement ach = event.getProgression();
        Player playerEntity = event.getPlayer();
        if (!Helpers.hasPlayerUnlocked(ach,playerEntity) && Helpers.canPlayerUnlock(ach,playerEntity)){
            Helpers.setAchievementStatus(ach, playerEntity,true);
            Helpers.triggerToast(ach, playerEntity);
            Helpers.updateProgression((ServerPlayer)playerEntity );
            Helpers.forceChunksReload((ServerPlayer) playerEntity);
            Helpers.triggerProgressionShader(playerEntity);
        }

    }

    @SubscribeEvent
    public static void cancelCrafting(TickEvent.PlayerTickEvent event){
        Player playerEntity = event.player;
        if (!playerEntity.level.isClientSide) {
            if (playerEntity.tickCount % 40 == 0) {
                deleteSolarForge(playerEntity);
                deleteInfuser(playerEntity);
            }
        }
    }

    @SubscribeEvent
    public static void cancelCrafting2(BlockEvent.EntityPlaceEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof  Player){
            Player playerEntity = (Player) entity;
            if (!playerEntity.isCreative()) {
                if (event.getPlacedBlock().is(SolarForge.SOLAR_FORGE.get()) && !ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.SOLAR_FORGE)) {
                    event.setCanceled(true);

                } else if (event.getPlacedBlock().is(SolarForge.SOLAR_INFUSER.get()) && !ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.SOLAR_INFUSER)) {
                    event.setCanceled(true);
                }
            }
        }
    }


    public static void deleteSolarForge(Player playerEntity){
        int count = playerEntity.getInventory().countItem(SolarForge.SOLAR_FORGE_ITEM.get());


        if ((count > 0) && !ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.SOLAR_FORGE) && !playerEntity.isCreative() && !playerEntity.isSpectator()) {
            Inventory inventory = playerEntity.getInventory();
            for (int i = 0;i < count;i++){
                int slot = inventory.findSlotMatchingUnusedItem(SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance());
                inventory.setItem(slot,ItemStack.EMPTY);
                playerEntity.level.addFreshEntity(createItemEntity(playerEntity,new ItemStack(Blocks.IRON_BLOCK,4)));
                playerEntity.level.addFreshEntity(createItemEntity(playerEntity,new ItemStack(Blocks.OBSIDIAN,4)));
                playerEntity.level.addFreshEntity(createItemEntity(playerEntity,new ItemStack(SolarForge.TEST_ITEM.get(),1)));
            }
            playerEntity.sendMessage(
                    new TextComponent("Solar forge not allowed "+AncientFragment.SOLAR_FORGE.getTranslation().getString().toUpperCase()+" fragment is not unlocked")
                            .withStyle(ChatFormatting.RED),
                    playerEntity.getUUID());
        }

    }
    public static void deleteInfuser(Player playerEntity){
        int count = playerEntity.getInventory().countItem(SolarForge.SOLAR_FORGE_ITEM.get());


        if ((count > 0) && !ProgressionHelper.doPlayerHasFragment(playerEntity, AncientFragment.SOLAR_INFUSER) && !playerEntity.isCreative() && !playerEntity.isSpectator()) {
            Inventory inventory = playerEntity.getInventory();
            for (int i = 0;i < count;i++){
                int slot = inventory.findSlotMatchingUnusedItem(SolarForge.INFUSING_STAND_ITEM.get().getDefaultInstance());
                inventory.setItem(slot,ItemStack.EMPTY);
                playerEntity.level.addFreshEntity(createItemEntity(playerEntity,new ItemStack(Blocks.IRON_BLOCK,3)));
                playerEntity.level.addFreshEntity(createItemEntity(playerEntity,new ItemStack(ItemsRegister.SOLAR_STONE.get(),3)));
                playerEntity.level.addFreshEntity(createItemEntity(playerEntity,new ItemStack(SolarForge.TEST_ITEM.get(),1)));
            }
            playerEntity.sendMessage(
                    new TextComponent("Infuser not allowed "+AncientFragment.SOLAR_INFUSER.getTranslation().getString().toUpperCase()+" fragment is not unlocked")
                            .withStyle(ChatFormatting.RED),
                    playerEntity.getUUID());
        }

    }
    public static ItemEntity createItemEntity(Player playerEntity,ItemStack stack){
        return new ItemEntity(playerEntity.level,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(),stack);
    }



}
