package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarAbilities.Abilities;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.SolarCraftAttributeModifiers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.InfuserBlock;
import com.finderfeed.solarforge.magic_items.items.ExperienceCrystal;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.registries.SolarcraftDamageSources;
import com.finderfeed.solarforge.registries.Tags;
import com.finderfeed.solarforge.registries.attributes.AttributesRegistry;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.effects.EffectsRegister;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
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
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.List;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    public static final ResourceKey<Level> RADIANT_LAND_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,new ResourceLocation("solarforge","radiant_land"));

    public static List<AbstractAbility> ALLOWED_ABILITIES_DURING_BOSSFIGHT = List.of(
            Abilities.HEAL.getAbility(),
            Abilities.DISPEL.getAbility()
    );

    @SubscribeEvent
    public static void playerHarvestCheck(PlayerEvent.BreakSpeed event){
        Player pla = event.getPlayer();
        if (!pla.level.isClientSide){
            if (pla.level.getBlockEntity(event.getPos()) instanceof OwnedBlock block){
                if (!pla.getUUID().equals(block.getOwner())){
                     event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void playerTickEvent(final TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            Level world = player.level;
            long actualtime = world.getDayTime()%24000;
            if (world.isClientSide && !Helpers.isDay(world)) {
                if ((world.dimension() == RADIANT_LAND_KEY)) {

                    if ((actualtime % 13000 == 0)) {
                        player.sendMessage(new TranslatableComponent("radiant_dimension.nightfall").withStyle(ChatFormatting.RED), player.getUUID());
                        ClientHelpers.playsoundInEars(Sounds.NIGHT_DIM.get(), 1, 1);
                    } else if ((actualtime % 14400 == 0)) {

                        ClientHelpers.playsoundInEars(Sounds.AMBIENT_DIM_1.get(), 1, 1);
                    } else if ((actualtime % 16800 == 0)) {
                        ClientHelpers.playsoundInEars(Sounds.AMBIENT_DIM_2.get(), 1, 1);
                    } else if ((actualtime % 20000) == 0) {
                        ClientHelpers.playsoundInEars(Sounds.AMBIENT_DIM_1.get(), 1, 1);
                    }
                }
            }

            if (!world.isClientSide && !player.isCreative()) {

                if ((world.getGameTime() % 20 == 1) && !(actualtime % 24000 <= 13000) && (world.dimension() == RADIANT_LAND_KEY)) {
                    if (!Helpers.playerInBossfight(player)) {
                        player.addEffect(new MobEffectInstance(EffectsRegister.STAR_GAZE_EFFECT.get(), 400, 0));
                    }
                }

                if (player.hasEffect(EffectsRegister.STAR_GAZE_EFFECT.get())) {
                    if (world.getGameTime() % 80 == 1) {
                        DamageSource src = SolarcraftDamageSources.STARGAZE.bypassArmor().setMagic();
                        player.hurt(src, 4);
                    }
                }
            }


            if (!world.isClientSide && (world.getGameTime() % 20 == 1)) {
                if (world.dimension() == Level.END){
                    Helpers.fireProgressionEvent(player,Progression.ENTER_END);
                }
                if (world.dimension() == RADIANT_LAND_KEY){
                    Helpers.fireProgressionEvent(player,Progression.RADIANT_LAND);
                }


                AttributeInstance attr = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
                if (attr != null) {
                    if (FinderfeedMathHelper.PlayerThings.doPlayerHasItem(player.getInventory(), ItemsRegister.REACH_GLOVES.get())) {
                        if (!attr.hasModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER)) {
                            attr.addTransientModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER);
                        }
                    } else {
                        if (attr.hasModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER)) {
                            attr.removeModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER);
                        }
                    }
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
        if (event.getCategory() == Biome.BiomeCategory.THEEND){
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeaturesRegistry.ENDER_CRACKS);
        }


    }

    private static boolean notNone(BiomeLoadingEvent event){
        return event.getCategory() != Biome.BiomeCategory.NONE;
    }

    @SubscribeEvent
    public static void progressionUnlockEvent(ProgressionUnlockEvent event){
        Progression ach = event.getProgression();
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
    public static void assignOwner(BlockEvent.EntityPlaceEvent event){
        if ((event.getEntity() instanceof Player player)
                && (player.level.getBlockEntity(event.getPos()) instanceof OwnedBlock tile)){
            tile.setOwner(player.getUUID());
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
        int count = playerEntity.getInventory().countItem(SolarForge.INFUSING_STAND_ITEM.get());


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

    @SubscribeEvent
    public static void handleMagicResistanceAttribute(LivingDamageEvent event){
        DamageSource src = event.getSource();
        LivingEntity entity = event.getEntityLiving();
        if (!entity.level.isClientSide &&
                (src.isMagic()) &&
                (entity.getAttributes().hasAttribute(AttributesRegistry.MAGIC_RESISTANCE.get()))){
            AttributeInstance attr = entity.getAttribute(AttributesRegistry.MAGIC_RESISTANCE.get());
            if (attr != null){
                double res = 1-attr.getValue()/100;
                event.setAmount((float)(event.getAmount()*res));
            }
        }
    }


    @SubscribeEvent
    public static void killEvent(LivingDeathEvent event){
        DamageSource src = event.getSource();
        Entity killer = src.getEntity();
        LivingEntity deadEntity = event.getEntityLiving();
        Level world = event.getEntityLiving().level;
        if (!world.isClientSide && killer != null){
            if (killer instanceof  Player pl){
                if (deadEntity instanceof WitherBoss){
                    Helpers.fireProgressionEvent(pl,Progression.KILL_WITHER);
                }
                if (deadEntity instanceof EnderDragon){
                    Helpers.fireProgressionEvent(pl,Progression.KILL_DRAGON);
                }
            }
        }
    }



    public static ItemEntity createItemEntity(Player playerEntity,ItemStack stack){
        return new ItemEntity(playerEntity.level,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(),stack);
    }

    @SubscribeEvent
    public static void catalystsProgression(BlockEvent.EntityPlaceEvent event){
        if (event.getEntity() instanceof Player pl){
            if (event.getPlacedBlock().is(Tags.CATALYST) && event.getPlacedBlock().getBlock() != BlocksRegistry.SOLAR_STONE_COLLUMN.get()) {
                if (!Helpers.hasPlayerUnlocked(Progression.CATALYSTS, pl)) {
                    for (int x = -10; x < 10;x++){
                        for (int z = -10; z < 10;z++){
                            for (int height = 2; height > -5;height--){
                                if (event.getWorld().getBlockState(event.getPos().offset(x,height,z)).getBlock() instanceof InfuserBlock){
                                    Helpers.fireProgressionEvent(pl,Progression.CATALYSTS);
                                }
                            }
                        }
                    }
                }
            }
        }
    }



}
