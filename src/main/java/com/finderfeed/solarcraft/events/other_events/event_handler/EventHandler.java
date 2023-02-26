package com.finderfeed.solarcraft.events.other_events.event_handler;


import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.PuzzleTemplateManager;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.SolarCraftAttributeModifiers;
import com.finderfeed.solarcraft.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarcraft.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarcraft.local_library.OwnedBlock;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.content.blocks.blockentities.ExplosionBlockerBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalTile;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserBlock;
import com.finderfeed.solarcraft.content.items.ExperienceCrystal;
import com.finderfeed.solarcraft.content.items.divine_armor.BaseDivineArmor;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.FragmentItem;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.packets.DisablePlayerFlightPacket;
import com.finderfeed.solarcraft.registries.SolarcraftDamageSources;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.effects.SolarcraftEffects;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.item.ItemStack;

import net.minecraft.ChatFormatting;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    public static final ResourceKey<Level> RADIANT_LAND_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,new ResourceLocation("solarcraft","radiant_land"));
    public static final ResourceKey<Biome> RADIANT_LAND_BIOME_KEY = ResourceKey.create(Registry.BIOME_REGISTRY,new ResourceLocation("solarcraft","radiant_land"));
    public static List<AbstractAbility> ALLOWED_ABILITIES_DURING_BOSSFIGHT = List.of(
            AbilitiesRegistry.HEAL,
            AbilitiesRegistry.DISPEL
    );

    @SubscribeEvent
    public static void playerHarvestCheck(PlayerEvent.BreakSpeed event){
        if (event.getPosition().isEmpty()) return;
        BlockPos pos = event.getPosition().get();
        Player pla = event.getEntity();
        if (!pla.level.isClientSide){
            if (pla.level.getBlockEntity(pos) instanceof OwnedBlock block){
                if (!pla.getUUID().equals(block.getOwner())){
                     event.setCanceled(true);
                }
            }


        }
        if (pla.getLevel().getBlockState(pos).is(SolarcraftBlocks.CLEARING_RITUAL_CRYSTAL.get())){
            if (pla.getLevel().getBlockState(pos.below(2)).is(SolarcraftBlocks.CRYSTAL_ENERGY_VINES.get())
                    || (pla.getLevel().getBlockEntity(pos) instanceof ClearingRitualCrystalTile tile && tile.isCorrupted())) {

                event.setNewSpeed(-1);
            }
        }
    }


//    @SubscribeEvent
//    public static void addStructures(WorldEvent.Load event){
//        if (event.getWorld() instanceof ServerLevel serverLevel) {
//            StructureSettings s = serverLevel.getChunkSource().getGenerator().getSettings();
//
//                ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> map = ImmutableMap.builder();
//                s.configuredStructures.forEach(map::put);
//                addStructureToBiomes(s,SolarForgeStructures.DIM_SHARD_STRUCTURE.get(), SolarForgeStructureFeatures.CONF_DIM_SHARD_STRUCT, map, Biomes.JUNGLE);
//                addStructureToBiomes(s,SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get(), SolarForgeStructureFeatures.CONF_DUNGEON_ONE, map, Biomes.DESERT);
//                addStructureToBiomes(s,SolarForgeStructures.CHARGING_STATION.get(), SolarForgeStructureFeatures.CONF_DUNGEON_CHARGING_STATION, map, Biomes.PLAINS);
//                addStructureToBiomes(s,SolarForgeStructures.DUNGEON_MAZE.get(), SolarForgeStructureFeatures.CONF_DUNGEON_MAZE, map, Biomes.SAVANNA);
//                addStructureToBiomes(s,SolarForgeStructures.MAGICIAN_TOWER.get(), SolarForgeStructureFeatures.CONF_MAGICIAN_TOWER, map,Biomes.JAGGED_PEAKS,Biomes.STONY_PEAKS);
//                addStructureToBiomes(s,SolarForgeStructures.CRYSTAL_BOSS_ROOM.get(), SolarForgeStructureFeatures.CONF_CRYSTAL_BOSS_ROOM, map, RADIANT_LAND_BIOME_KEY);
//                addStructureToBiomes(s,SolarForgeStructures.RUNIC_ELEMENTAL_ARENA.get(), SolarForgeStructureFeatures.RUNIC_ELEMENTAL_ARENA, map, RADIANT_LAND_BIOME_KEY);
//
//                s.configuredStructures = map.build();
//
//        }
//    }
//
//    private static void addStructureToBiomes(StructureSettings s,StructureFeature<?> feature,
//                                             ConfiguredStructureFeature<?,?> configuredStruct,
//                                             ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> map,
//                                             ResourceKey<Biome>... biomes){
//        if (!s.configuredStructures.containsKey(feature)) {
//            ImmutableMultimap.Builder<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> secondMap = ImmutableMultimap.builder();
//            for (ResourceKey<Biome> key : biomes) {
//                secondMap.put(configuredStruct, key);
//            }
//            map.put(feature, secondMap.build());
//        }
//    }


    @SubscribeEvent
    public static void playerTickEvent(final TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            Level world = player.level;
            long actualtime = world.getDayTime()%24000;
            if (world.isClientSide && !Helpers.isDay(world)) {
                if ((world.dimension() == RADIANT_LAND_KEY) && !ClientHelpers.isIsRadiantLandCleaned()) {

                    if ((actualtime % 13000 == 0)) {
                        player.sendSystemMessage(Component.translatable("radiant_dimension.nightfall").withStyle(ChatFormatting.RED));
                        ClientHelpers.playsoundInEars(SolarcraftSounds.NIGHT_DIM.get(), 1, 1);
                    } else if ((actualtime % 14400 == 0)) {

                        ClientHelpers.playsoundInEars(SolarcraftSounds.AMBIENT_DIM_1.get(), 1, 1);
                    } else if ((actualtime % 16800 == 0)) {
                        ClientHelpers.playsoundInEars(SolarcraftSounds.AMBIENT_DIM_2.get(), 1, 1);
                    } else if ((actualtime % 20000) == 0) {
                        ClientHelpers.playsoundInEars(SolarcraftSounds.AMBIENT_DIM_1.get(), 1, 1);
                    }
                }
            }

            if (!world.isClientSide && !player.isCreative()) {

                if ((world.dimension() == RADIANT_LAND_KEY) && (world.getGameTime() % 20 == 1) && !(actualtime % 24000 <= 13000)
                 && Helpers.collectTilesInChunks(SolarcraftTileEntityTypes.CLEARING_RITUAL_MAIN_BLOCK.get(),player.level,player.getOnPos(),2).isEmpty()
                  && !Helpers.isRadiantLandCleanedServer((ServerLevel) world)) {
                    if (world.canSeeSky(player.getOnPos().above())) {
                        player.addEffect(new MobEffectInstance(SolarcraftEffects.STAR_GAZE_EFFECT.get(), 400, 0));
                    }
                }

                if (player.hasEffect(SolarcraftEffects.STAR_GAZE_EFFECT.get())) {
                    if (world.getGameTime() % 80 == 1) {
                        DamageSource src = SolarcraftDamageSources.STARGAZE.bypassArmor().setMagic();
                        player.hurt(src, 6);
                    }
                }

                for (ToggleableAbility ability : AbilitiesRegistry.getToggleableAbilities()){
                    if (ability.isToggled(player)){
                        if (!AbilityHelper.isAbilityUsable(player,ability,false)){
                            ability.setToggled(player,false);
                            continue;
                        }
                        for (RunicEnergy.Type type : ability.getCost().getSetTypes()){
                            RunicEnergy.spendEnergy(player,ability.getCost().get(type),type);
                            Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(player, type), player);
                        }

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
                    if (FDMathHelper.PlayerThings.doPlayerHasItem(player.getInventory(), SolarcraftItems.REACH_GLOVES.get())) {
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
        Player player = event.getEntity();
        if (!player.level.isClientSide){
            if (ExperienceCrystal.consumeExperience(player,orb.value)) {
                orb.remove(Entity.RemovalReason.DISCARDED);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void progressionUnlockEvent(ProgressionUnlockEvent event){
        Progression ach = event.getProgression();
        Player playerEntity = event.getPlayer();
        Helpers.setProgressionCompletionStatus(ach, playerEntity,true);
        Helpers.triggerToast(ach, playerEntity);
        Helpers.updateProgression((ServerPlayer)playerEntity );
        Helpers.forceChunksReload((ServerPlayer) playerEntity);
        Helpers.triggerProgressionShader(playerEntity);

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
        LivingEntity entity = event.getEntity();
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
        LivingEntity deadEntity = event.getEntity();
        Level world = event.getEntity().level;
        if (!world.isClientSide && killer != null){
            if (killer instanceof  Player pl){
                if (deadEntity instanceof WitherBoss){
                    Helpers.fireProgressionEvent(pl,Progression.KILL_WITHER);
                } else if (deadEntity instanceof EnderDragon){
                    Helpers.fireProgressionEvent(pl,Progression.KILL_DRAGON);
                }
            }
        }
    }

    @SubscribeEvent
    public static void catalystsProgression(BlockEvent.EntityPlaceEvent event){
        if (event.getEntity() instanceof Player pl){
            if (event.getPlacedBlock().is(Tags.CATALYST) && event.getPlacedBlock().getBlock() != SolarcraftBlocks.MAGISTONE_COLUMN.get()) {
                if (!Helpers.hasPlayerCompletedProgression(Progression.CATALYSTS, pl)) {
                    for (int x = -10; x < 10;x++){
                        for (int z = -10; z < 10;z++){
                            for (int height = 2; height > -5;height--){
                                if (event.getLevel().getBlockState(event.getPos().offset(x,height,z)).getBlock() instanceof InfuserBlock){
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
        if (!event.getLevel().isClientSide) {
            if (isExplosionBlockerAround(event.getLevel(),pos)){
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
                    event.getPlayer().sendSystemMessage(Component.translatable("solarcraft.item_unknown").withStyle(ChatFormatting.RED));
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void preventItemUsing(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() instanceof FragmentItem fragmentItem && !event.getEntity().level.isClientSide) {
            if (event.getEntity().isCreative()) return;

            if (fragmentItem.getNeededFragment() != null) {
                if (!ProgressionHelper.doPlayerHasFragment(event.getEntity(), fragmentItem.getNeededFragment())) {
                    event.getEntity().sendSystemMessage(Component.translatable("solarcraft.item_unknown").withStyle(ChatFormatting.RED));
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
                    player.sendSystemMessage(Component.translatable("solarcraft.item_unknown").withStyle(ChatFormatting.RED));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void handleEvasion(LivingDamageEvent event){
        LivingEntity living = event.getEntity();
        if (!living.level.isClientSide){
            if (living.hasEffect(SolarcraftEffects.EVASION.get())){
                int level = living.getActiveEffectsMap().get(SolarcraftEffects.EVASION.get()).getAmplifier();
                if (living.level.random.nextDouble() <= 0.2 * (level + 1)){
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void cancelFallDamage(LivingFallEvent event){
        if (event.getEntity() instanceof Player player){
            if (player.level.isClientSide) return;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(SolarcraftItems.DIVINE_CHESTPLATE.get())){
                event.setDamageMultiplier(0);
            }
        }
    }


    @SubscribeEvent
    public static void equipmentChangedEvent(LivingEquipmentChangeEvent event){
        if (event.getEntity() instanceof ServerPlayer player){
            if (event.getSlot() == EquipmentSlot.CHEST){
                if (event.getTo().is(SolarcraftItems.DIVINE_CHESTPLATE.get()) && !event.getFrom().is(SolarcraftItems.DIVINE_CHESTPLATE.get())){
                    if (!player.isCreative() && !player.isSpectator()) {
                        DisablePlayerFlightPacket.send(player, false);
                    }
                    if (player.getAbilities().getFlyingSpeed() < 0.1f) {
                        player.getAbilities().setFlyingSpeed(0.10f);
                    }
                }else if (event.getFrom().is(SolarcraftItems.DIVINE_CHESTPLATE.get()) && !event.getTo().is(SolarcraftItems.DIVINE_CHESTPLATE.get())){
                    if (!player.isCreative() && !player.isSpectator() ) {
                        DisablePlayerFlightPacket.send(player, true);
                    }
                    if (player.getAbilities().getFlyingSpeed() == 0.1f) {
                        player.getAbilities().setFlyingSpeed(0.05f);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void manageDivineArmorShields(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player && !player.isCreative() && !player.isSpectator()){
            float damageAmount = event.getAmount();
            for (ItemStack stack : player.getArmorSlots()){
                if (damageAmount <= 0) break;
                if (stack.getItem() instanceof BaseDivineArmor armor){
                    float maxBlockedDamage = ItemRunicEnergy.getRunicEnergyFromItem(stack, RunicEnergy.Type.ARDO)/
                            armor.getCost().get(RunicEnergy.Type.ARDO);
                    float m = Math.min(maxBlockedDamage,damageAmount);
                    ItemRunicEnergy.removeRunicEnergy(stack,armor, RunicEnergy.Type.ARDO,m*armor.getCost()
                            .get(RunicEnergy.Type.ARDO));
                    damageAmount -= maxBlockedDamage;
                }
            }
            event.setAmount(Math.max(0,damageAmount));
        }
    }



    @SubscribeEvent
    public static void registerReloadableResourceListeners(AddReloadListenerEvent event){
        event.addListener(PuzzleTemplateManager.INSTANCE);
    }
}
