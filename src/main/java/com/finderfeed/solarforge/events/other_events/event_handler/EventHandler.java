package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarAbilities.Abilities;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.SolarCraftAttributeModifiers;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.local_library.OwnedBlock;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic.blocks.blockentities.ExplosionBlockerBlockEntity;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserBlock;
import com.finderfeed.solarforge.magic.items.ExperienceCrystal;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.FragmentItem;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.registries.SolarcraftDamageSources;
import com.finderfeed.solarforge.registries.Tags;
import com.finderfeed.solarforge.registries.attributes.AttributesRegistry;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.effects.EffectsRegister;
import com.finderfeed.solarforge.registries.features.configured.ConfiguredFeatures;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarforge.world_generation.structures.SolarForgeStructureFeatures;
import com.finderfeed.solarforge.world_generation.structures.SolarForgeStructures;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.item.ItemStack;

import net.minecraft.ChatFormatting;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    public static final ResourceKey<Level> RADIANT_LAND_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,new ResourceLocation("solarforge","radiant_land"));
    public static final ResourceKey<Biome> RADIANT_LAND_BIOME_KEY = ResourceKey.create(Registry.BIOME_REGISTRY,new ResourceLocation("solarforge","radiant_land"));
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
    public static void addStructures(WorldEvent.Load event){
        if (event.getWorld() instanceof ServerLevel serverLevel) {
            StructureSettings s = serverLevel.getChunkSource().getGenerator().getSettings();

                ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> map = ImmutableMap.builder();
                s.configuredStructures.forEach(map::put);
                addStructureToBiomes(s,SolarForgeStructures.DIM_SHARD_STRUCTURE.get(), SolarForgeStructureFeatures.CONF_DIM_SHARD_STRUCT, map, Biomes.JUNGLE);
                addStructureToBiomes(s,SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get(), SolarForgeStructureFeatures.CONF_DUNGEON_ONE, map, Biomes.DESERT);
                addStructureToBiomes(s,SolarForgeStructures.CHARGING_STATION.get(), SolarForgeStructureFeatures.CONF_DUNGEON_CHARGING_STATION, map, Biomes.PLAINS);
                addStructureToBiomes(s,SolarForgeStructures.DUNGEON_MAZE.get(), SolarForgeStructureFeatures.CONF_DUNGEON_MAZE, map, Biomes.SAVANNA);
                addStructureToBiomes(s,SolarForgeStructures.MAGICIAN_TOWER.get(), SolarForgeStructureFeatures.CONF_MAGICIAN_TOWER, map,Biomes.JAGGED_PEAKS,Biomes.STONY_PEAKS);
                addStructureToBiomes(s,SolarForgeStructures.CRYSTAL_BOSS_ROOM.get(), SolarForgeStructureFeatures.CONF_CRYSTAL_BOSS_ROOM, map, RADIANT_LAND_BIOME_KEY);

                s.configuredStructures = map.build();

        }
    }

    private static void addStructureToBiomes(StructureSettings s,StructureFeature<?> feature,
                                             ConfiguredStructureFeature<?,?> configuredStruct,
                                             ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> map,
                                             ResourceKey<Biome>... biomes){
        if (!s.configuredStructures.containsKey(feature)) {
            ImmutableMultimap.Builder<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> secondMap = ImmutableMultimap.builder();
            for (ResourceKey<Biome> key : biomes) {
                secondMap.put(configuredStruct, key);
            }
            map.put(feature, secondMap.build());
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
                    if (world.canSeeSky(player.getOnPos().above())) {
                        player.addEffect(new MobEffectInstance(EffectsRegister.STAR_GAZE_EFFECT.get(), 400, 0));
                    }
                }

                if (player.hasEffect(EffectsRegister.STAR_GAZE_EFFECT.get())) {
                    if (world.getGameTime() % 80 == 1) {
                        DamageSource src = SolarcraftDamageSources.STARGAZE.bypassArmor().setMagic();
                        player.hurt(src, 6);
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
        BiomeGenerationSettingsBuilder b = event.getGeneration();
        if ( (event.getCategory() != Biome.BiomeCategory.NETHER) && (event.getCategory() != Biome.BiomeCategory.THEEND) && notNone(event)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeaturesRegistry.ENERGY_PYLON_CONFIGURED);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,FeaturesRegistry.EMPTY_CRYSTALS_PLACEMENT);
        }
        if (event.getCategory() == Biome.BiomeCategory.PLAINS){
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,FeaturesRegistry.RUNIC_TREE_FEATURE);
        }
        if ( (event.getCategory() != Biome.BiomeCategory.NETHER) && (event.getCategory() != Biome.BiomeCategory.THEEND)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,FeaturesRegistry.LENSING_CRYSTAL_ORE_PLACEMENT);
        }
        if (event.getCategory() == Biome.BiomeCategory.THEEND){
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeaturesRegistry.ENDER_CRACKS);
        }
        //TODO:delete when incinerated forest returns
        if (event.getCategory() == Biome.BiomeCategory.PLAINS){
            event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,FeaturesRegistry.MOLTEN_FOREST_RUINS_CONFIGURED);
            b.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION,FeaturesRegistry.LUNAR_LILY_FEATURE_PLACEMENT);
        }

        if (event.getCategory() == Biome.BiomeCategory.DESERT){
            event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ConfiguredFeatures.SOLAR_FLOWER_FEATURE);
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

    @SubscribeEvent
    public static void cancelExplosionsInRangeOfExplosionBlocker(ExplosionEvent.Detonate event){
        Vec3 pos = event.getExplosion().getPosition();
        if (!event.getWorld().isClientSide) {
            if (isExplosionBlockerAround(event.getWorld(),pos)){
                event.getAffectedBlocks().clear();
            }
        }
    }
    public static boolean isExplosionBlockerAround(Level world,Vec3 pos){
        if (!world.isClientSide) {
            for (LevelChunk chunk : Helpers.getChunksInRadius(world, new BlockPos(pos), 2)) {
                for (BlockEntity e : chunk.getBlockEntities().values()) {
                    if (e instanceof ExplosionBlockerBlockEntity blocker) {
                        if (blocker.isFunctioning()) {
                            if (Helpers.getBlockCenter(blocker.getBlockPos()).subtract(pos).multiply(1, 0, 1).length() <= ExplosionBlockerBlockEntity.DEFENDING_RADIUS + 1) {
                                return true;
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    @SubscribeEvent
    public static void preventBreakingBlocks(BlockEvent.BreakEvent event){
        if ((event.getPlayer().getMainHandItem().getItem() instanceof FragmentItem fragmentItem) && !event.getPlayer().level.isClientSide){
            if (event.getPlayer().isCreative()) return;

            if (fragmentItem.getNeededFragment() != null) {
                if (!ProgressionHelper.doPlayerHasFragment(event.getPlayer(), fragmentItem.getNeededFragment())) {
                    event.getPlayer().sendMessage(new TranslatableComponent("solarcraft.item_unknown").withStyle(ChatFormatting.RED),event.getPlayer().getUUID());
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void preventItemUsing(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() instanceof FragmentItem fragmentItem && !event.getPlayer().level.isClientSide) {
            if (event.getPlayer().isCreative()) return;

            if (fragmentItem.getNeededFragment() != null) {
                if (!ProgressionHelper.doPlayerHasFragment(event.getPlayer(), fragmentItem.getNeededFragment())) {
                    event.getPlayer().sendMessage(new TranslatableComponent("solarcraft.item_unknown").withStyle(ChatFormatting.RED), event.getPlayer().getUUID());
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void preventBlockPlacing(BlockEvent.EntityPlaceEvent event){
        Entity e = event.getEntity();
        if (event.getPlacedBlock().getBlock().asItem() instanceof FragmentItem fragmentItem && e instanceof Player player){
            if (player.isCreative()) return;

            if (fragmentItem.getNeededFragment() != null) {
                if (!ProgressionHelper.doPlayerHasFragment(player, fragmentItem.getNeededFragment())) {
                    player.sendMessage(new TranslatableComponent("solarcraft.item_unknown").withStyle(ChatFormatting.RED), player.getUUID());
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void handleEvasion(LivingDamageEvent event){
        LivingEntity living = event.getEntityLiving();
        if (!living.level.isClientSide){
            if (living.hasEffect(EffectsRegister.EVASION.get())){
                int level = living.getActiveEffectsMap().get(EffectsRegister.EVASION.get()).getAmplifier();
                if (living.level.random.nextDouble() <= 0.2 * (level + 1)){
                    event.setCanceled(true);
                }
            }
        }
    }



}
