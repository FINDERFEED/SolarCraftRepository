package com.finderfeed.solarcraft.events.other_events.event_handler;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.config.JsonConfig;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarcraft.content.items.TotemOfImmortality;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.vein_miner.IllidiumPickaxe;
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
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.*;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.effects.SCEffects;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.util.List;


@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SCEventHandler {
    public static final ResourceKey<Level> RADIANT_LAND_KEY = ResourceKey.create(Registries.DIMENSION,new ResourceLocation("solarcraft","radiant_land"));
    public static List<AbstractAbility> ALLOWED_ABILITIES_DURING_BOSSFIGHT = List.of(
            AbilitiesRegistry.HEAL,
            AbilitiesRegistry.DISPEL
    );

    @SubscribeEvent
    public static void playerHarvestCheck(PlayerEvent.BreakSpeed event){
        if (event.getPosition().isEmpty()) return;
        BlockPos pos = event.getPosition().get();
        Player pla = event.getEntity();
        if (!pla.level().isClientSide){
            if (pla.level().getBlockEntity(pos) instanceof OwnedBlock block){
                if (!pla.getUUID().equals(block.getOwner())){
                     event.setCanceled(true);
                }
            }


        }
        if (pla.level().getBlockState(pos).is(SCBlocks.CLEARING_RITUAL_CRYSTAL.get())){
            if (pla.level().getBlockState(pos.below(2)).is(SCBlocks.CRYSTAL_ENERGY_VINES.get())
                    || (pla.level().getBlockEntity(pos) instanceof ClearingRitualCrystalTile tile && tile.isCorrupted())) {

                event.setNewSpeed(-1);
            }
        }
    }





    @SubscribeEvent
    public static void respawn(PlayerEvent.PlayerRespawnEvent event){
        if (event.getEntity() instanceof ServerPlayer player){
            Helpers.updateProgressionsOnClient(player);
            for (RunicEnergy.Type type : RunicEnergy.Type.getAll()) {
                Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(player, type), player);
            }
            Helpers.updateFragmentsOnClient(player);
        }
    }



    @SubscribeEvent
    public static void retainAbilities(final PlayerEvent.Clone event) {
        Player peorig = event.getOriginal();
        Player playernew = event.getEntity();
        if (!event.isWasDeath()) {
            for (AbstractAbility ability : AbilitiesRegistry.getAllAbilities()){
                AbilityHelper.setAbilityUsable(playernew,ability,AbilityHelper.isAbilityBought(peorig,ability));
            }

            playernew.getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY, peorig.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY));
            RunicEnergy.handleCloneEvent(event);
        }


        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()) {
            if (RunicEnergy.hasFoundType(peorig,type)) {
                RunicEnergy.setFound(playernew, type);
            }
            Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(peorig, type), peorig);
        }
        playernew.getPersistentData().putString("solar_forge_ability_binded_1", peorig.getPersistentData().getString("solar_forge_ability_binded_1"));
        playernew.getPersistentData().putString("solar_forge_ability_binded_2", peorig.getPersistentData().getString("solar_forge_ability_binded_2"));
        playernew.getPersistentData().putString("solar_forge_ability_binded_3", peorig.getPersistentData().getString("solar_forge_ability_binded_3"));
        playernew.getPersistentData().putString("solar_forge_ability_binded_4", peorig.getPersistentData().getString("solar_forge_ability_binded_4"));

        if (playernew instanceof ServerPlayer s){
            AbilitiesRegistry.ALCHEMIST.setToggled(s, false);
        }


        for (Progression a : Progression.allProgressions){
            Helpers.setProgressionCompletionStatus(a,playernew,Helpers.hasPlayerCompletedProgression(a,peorig));

        }
        if (!playernew.level().isClientSide) {
            Helpers.updateProgressionsOnClient((ServerPlayer) playernew);
        }
        if (playernew instanceof ServerPlayer player) {
            for (AncientFragment fragment : AncientFragment.getAllFragments()) {
                if (AncientFragmentHelper.doPlayerHasFragment(peorig, fragment)) {
                    AncientFragmentHelper.givePlayerFragment(fragment, playernew);
                }
            }
            Helpers.updateFragmentsOnClient(player);
        }


    }


    @SubscribeEvent
    public static void damageTaken(final LivingDamageEvent event){
        if ((event.getEntity()).hasEffect(SCEffects.IMMORTALITY_EFFECT.get()) ){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void procImmortalityTotem(final LivingDeathEvent event){
        if (event.getEntity() instanceof Player && !event.getEntity().level().isClientSide){
            Player player = (Player) event.getEntity();
            int slot = findImmortalityTotem(player);
            if (slot != -10000){
                player.addEffect(new MobEffectInstance(SCEffects.IMMORTALITY_EFFECT.get(),400,0));
                player.setHealth(player.getMaxHealth());

                player.getInventory().setItem(slot, ItemStack.EMPTY);
                ServerLevel world = (ServerLevel)player.level();
                world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.TOTEM_USE, SoundSource.AMBIENT,0.5f,0.5f);
                FDPacketUtil.sendToPlayer((ServerPlayer) player,new ProcImmortalityTotemAnimation());
//                SCPacketHandler.INSTANCE.sendTo(new ProcImmortalityTotemAnimation(),((ServerPlayer)player).connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);

                event.setCanceled(true);
            }
        }

    }


    public static int findImmortalityTotem(Player player){
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).getItem() instanceof TotemOfImmortality) {
                return i;
            }
        }
        return -10000;
    }

    @SubscribeEvent
    public static void livingAttackEvent(final LivingAttackEvent event){
        if (event.getSource() != null) {
            Entity ent = event.getSource().getEntity();
            if (ent instanceof LivingEntity livingEnt) {
                if (livingEnt.hasEffect(SCEffects.SOLAR_STUN.get())) {
                    event.setCanceled(true);
                }
            }
        }

        if (event.getSource() != null && (event.getSource().getEntity() != null)){
            LivingEntity ent = event.getEntity();
            ent.getArmorSlots().forEach((stack)->{
                if (stack.getItem().equals(SCItems.RADIANT_CHESTPLATE.get())){
                    if (ent.level().random.nextFloat() <= 0.17){
                        event.setCanceled(true);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void playerTickEvent(final TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            Level world = player.level();

            if (player instanceof ServerPlayer) {
                if (player.level().getGameTime() % 200 == 0) {
                    Helpers.updateFragmentsOnClient((ServerPlayer) player);
                    Helpers.updateProgressionsOnClient((ServerPlayer) player);
                }
                if (player.level().getGameTime() % 20 == 0) {
                    if (player.level().dimension() == Level.NETHER) {
                        Helpers.fireProgressionEvent(player, Progression.ENTER_NETHER);
                    }
                }
            }

            long actualtime = world.getDayTime()%24000;
            if (world.isClientSide && !Helpers.isDay(world)) {
                if ((world.dimension() == RADIANT_LAND_KEY) && !ClientHelpers.isIsRadiantLandCleaned()) {

                    if ((actualtime % 13000 == 0)) {
                        player.sendSystemMessage(Component.translatable("radiant_dimension.nightfall").withStyle(ChatFormatting.RED));
                        ClientHelpers.playsoundInEars(SCSounds.NIGHT_DIM.get(), 1, 1);
                    } else if ((actualtime % 14400 == 0)) {

                        ClientHelpers.playsoundInEars(SCSounds.AMBIENT_DIM_1.get(), 1, 1);
                    } else if ((actualtime % 16800 == 0)) {
                        ClientHelpers.playsoundInEars(SCSounds.AMBIENT_DIM_2.get(), 1, 1);
                    } else if ((actualtime % 20000) == 0) {
                        ClientHelpers.playsoundInEars(SCSounds.AMBIENT_DIM_1.get(), 1, 1);
                    }
                }
            }

            if (!world.isClientSide && !player.isCreative()) {

                if ((world.dimension() == RADIANT_LAND_KEY) && (world.getGameTime() % 20 == 1) && !(actualtime % 24000 <= 13000)
                 && Helpers.collectTilesInChunks(SCTileEntities.CLEARING_RITUAL_MAIN_BLOCK.get(),player.level(),player.getOnPos(),2).isEmpty()
                  && !Helpers.isRadiantLandCleanedServer((ServerLevel) world)) {
                    if (world.canSeeSky(player.getOnPos().above())) {
                        player.addEffect(new MobEffectInstance(SCEffects.STAR_GAZE_EFFECT.get(), 400, 0));
                    }
                }

                if (player.hasEffect(SCEffects.STAR_GAZE_EFFECT.get())) {
                    if (world.getGameTime() % 80 == 1) {
                        DamageSource src = SCDamageSources.STARGAZE;
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



                AttributeInstance attr = player.getAttribute(NeoForgeMod.BLOCK_REACH.value());
                AttributeInstance attr1 = player.getAttribute(NeoForgeMod.ENTITY_REACH.value());
                if (attr != null) {
                    if (FDMathHelper.PlayerThings.doPlayerHasItem(player.getInventory(), SCItems.REACH_GLOVES.get())) {
                        if (!attr.hasModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER)) {
                            attr.addTransientModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER);
                        }
                        if (!attr1.hasModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER)) {
                            attr1.addTransientModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER);
                        }
                    } else {
                        if (attr.hasModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER)) {
                            attr.removeModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER.getId());
                        }
                        if (attr1.hasModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER)) {
                            attr1.removeModifier(SolarCraftAttributeModifiers.REACH_2_MODIFIER.getId());
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
        if (!player.level().isClientSide){
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
        Helpers.updateProgressionsOnClient((ServerPlayer)playerEntity );
        Helpers.forceChunksReload((ServerPlayer) playerEntity);
        Helpers.triggerProgressionShader(playerEntity);

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void assignOwner(BlockEvent.EntityPlaceEvent event){
        if ((event.getEntity() instanceof Player player) && (player.level().getBlockEntity(event.getPos()) instanceof OwnedBlock tile)){
            tile.setOwner(player.getUUID());
        }
    }


    @SubscribeEvent
    public static void handleMagicResistanceAttribute(LivingDamageEvent event){
        DamageSource src = event.getSource();
        LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide &&
                (src.is(DamageTypeTags.BYPASSES_ARMOR) || src == entity.level().damageSources().magic()) &&
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
        Level world = event.getEntity().level();
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
            if (event.getPlacedBlock().is(Tags.CATALYST) && event.getPlacedBlock().getBlock() != SCBlocks.MAGISTONE_COLUMN.get()) {
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
        Vec3 pos = event.getExplosion().center();
        if (!event.getLevel().isClientSide) {
            if (isExplosionBlockerAround(event.getLevel(),pos)){
                event.getAffectedBlocks().clear();
            }
        }
    }
    public static boolean isExplosionBlockerAround(Level world,Vec3 pos){
        if (!world.isClientSide) {
            for (LevelChunk chunk : Helpers.getChunksInRadius(world, new BlockPos((int)pos.x,(int)pos.y,(int)pos.z), 2)) {
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



    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void breakEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer){
            FDPacketUtil.sendToPlayer(serverPlayer,new BlockBreakPacket(event.getPos(),event.getState()));
//            SCPacketHandler.INSTANCE.sendTo(new BlockBreakPacket(event.getPos(),event.getState()),
//                    serverPlayer.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        }
        if (event.getPlayer() instanceof ServerPlayer player) {
            if (AbilitiesRegistry.ALCHEMIST.isToggled(player) && !event.getPlayer().isDeadOrDying() ) {
                if (AbilityHelper.isAbilityUsable(player,AbilitiesRegistry.ALCHEMIST,false)) {
                    LevelAccessor world = event.getLevel();
                    BlockPos pos = event.getPos();
                    world.setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
                    world.addFreshEntity(new ExperienceOrb((Level) world, pos.getX(), pos.getY(), pos.getZ(), 10));
                }
            }
        }
        ItemStack stack = event.getPlayer().getMainHandItem();
        if (stack.getItem() instanceof IllidiumPickaxe pick){
            if (!ItemRunicEnergy.spendEnergy(pick.getCost(),stack,pick, event.getPlayer())){
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void sendClientPlaceEvent(BlockEvent.EntityPlaceEvent event){
        if (event.getEntity() instanceof ServerPlayer player){
            FDPacketUtil.sendToPlayer(player,new BlockPlacePacket( event.getPos(),event.getState()));
//            SCPacketHandler.INSTANCE.sendTo(new BlockPlacePacket( event.getPos(),event.getState()),
//                    player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
        }
    }

    @SubscribeEvent
    public static void preventBreakingBlocks(BlockEvent.BreakEvent event){

        if ((event.getPlayer().getMainHandItem().getItem() instanceof FragmentItem fragmentItem) && !event.getPlayer().level().isClientSide){
            if (event.getPlayer().isCreative()) return;

            if (fragmentItem.getNeededFragment() != null) {
                if (!AncientFragmentHelper.doPlayerHasFragment(event.getPlayer(), fragmentItem.getNeededFragment())) {
                    event.getPlayer().sendSystemMessage(Component.translatable("solarcraft.item_unknown").withStyle(ChatFormatting.RED));
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void preventItemUsing(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() instanceof FragmentItem fragmentItem && !event.getEntity().level().isClientSide) {
            if (event.getEntity().isCreative()) return;

            if (fragmentItem.getNeededFragment() != null) {
                if (!AncientFragmentHelper.doPlayerHasFragment(event.getEntity(), fragmentItem.getNeededFragment())) {
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
                if (!AncientFragmentHelper.doPlayerHasFragment(player, fragmentItem.getNeededFragment())) {
                    player.sendSystemMessage(Component.translatable("solarcraft.item_unknown").withStyle(ChatFormatting.RED));
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void handleEvasion(LivingDamageEvent event){
        LivingEntity living = event.getEntity();
        if (!living.level().isClientSide){
            if (living.hasEffect(SCEffects.EVASION.get())){
                int level = living.getActiveEffectsMap().get(SCEffects.EVASION.get()).getAmplifier();
                if (living.level().random.nextDouble() <= 0.2 * (level + 1)){
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void cancelFallDamage(LivingFallEvent event){
        if (event.getEntity() instanceof Player player){
            if (player.level().isClientSide) return;
            if (player.getItemBySlot(EquipmentSlot.CHEST).is(SCItems.DIVINE_CHESTPLATE.get())){
                event.setDamageMultiplier(0);
            }
        }
    }


    @SubscribeEvent
    public static void equipmentChangedEvent(LivingEquipmentChangeEvent event){
        if (event.getEntity() instanceof ServerPlayer player){
            if (event.getSlot() == EquipmentSlot.CHEST){
                if (event.getTo().is(SCItems.DIVINE_CHESTPLATE.get()) && !event.getFrom().is(SCItems.DIVINE_CHESTPLATE.get())){
                    if (!player.isCreative() && !player.isSpectator()) {
                        DisablePlayerFlightPacket.send(player, false);
                    }
                    if (player.getAbilities().getFlyingSpeed() < 0.1f) {
                        player.getAbilities().setFlyingSpeed(0.10f);
                    }
                }else if (event.getFrom().is(SCItems.DIVINE_CHESTPLATE.get()) && !event.getTo().is(SCItems.DIVINE_CHESTPLATE.get())){
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
                    float energyCost = armor.getCost().get(RunicEnergy.Type.ARDO);
                    float maxBlockedDamage = ItemRunicEnergy.getRunicEnergyFromItem(stack, RunicEnergy.Type.ARDO) / energyCost;

                    float m = Math.min(maxBlockedDamage,damageAmount);
                    ItemRunicEnergy.removeRunicEnergy(stack,armor, RunicEnergy.Type.ARDO,m*energyCost);

                    damageAmount -= maxBlockedDamage;
                }
            }
            event.setAmount(Math.max(0,damageAmount));
        }
    }



//    @SubscribeEvent
//    public static void registerReloadableResourceListeners(AddReloadListenerEvent event){
//        event.addListener(PuzzleTemplateManager.INSTANCE);
//    }

    @SubscribeEvent
    public static void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() != null) {
            Player player = event.getEntity();
            if (player instanceof  ServerPlayer sPlayer) {

                if (!Helpers.getPlayerSolarcraftTag(sPlayer).getBoolean("received_lexicon")){
                    if (sPlayer.addItem(SCItems.SOLAR_LEXICON.get().getDefaultInstance())){
                        Helpers.getPlayerSolarcraftTag(sPlayer).putBoolean("received_lexicon",true);
                    }

                }

                for (JsonConfig config : ConfigRegistry.POST_LOAD_CONFIGS.values()){
                    config.deserialize(config.getJson());
                }
                for (JsonConfig config : ConfigRegistry.EARLY_LOAD_CONFIGS.values()){
                    config.deserialize(config.getJson());
                }

//                FDPacketUtil.sendToPlayer(sPlayer,new SendConfigsToClientPacket());
//                SCPacketHandler.INSTANCE.sendTo(new SendConfigsToClientPacket(),sPlayer.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);


//                for (RunicEnergy.Type type : RunicEnergy.Type.values()) {
//                    Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(player, type), player);
//                }
//                Helpers.updateProgressionsOnClient(sPlayer);

                if (JsonFragmentsHelper.fragmentsShouldBeRead()) {
                    List<AncientFragment> fragsDes = AncientFragment.deserializeFragments(JsonFragmentsHelper.readFragments());

                    if (fragsDes != null) {
                        AncientFragment.ALL_FRAGMENTS.addAll(fragsDes);
                    }
                }

//                for (ToggleableAbility ability : AbilitiesRegistry.getToggleableAbilities()) {
//                    AbilityHelper.sendTogglePacket(sPlayer,ability,ability.isToggled(sPlayer));
//                }

//                JsonFragmentsHelper.sendUpdatePacketToClient(sPlayer);
                if (EnchanterConfigInit.shouldBeRead()) {
                    EnchanterConfigInit.readJson();
                }
//                Helpers.updateFragmentsOnClient(sPlayer);
//                Helpers.updateClientRadiantLandStateForPlayer(sPlayer);
                AncientFragment.initFragmentsMap();


                for (AncientFragment fr : AncientFragment.ALL_FRAGMENTS){
                    fr.getReferences();
                }


            }

        }
    }

    @SubscribeEvent
    public static void initServerConfigs(ServerStartedEvent event){
        if (JsonFragmentsHelper.fragmentsShouldBeRead()){
            List<AncientFragment> fragsDes = AncientFragment.deserializeFragments(JsonFragmentsHelper.readFragments());

            if (fragsDes != null) {
                AncientFragment.ALL_FRAGMENTS.addAll(fragsDes);
            }
        }
        if (EnchanterConfigInit.shouldBeRead()) {
            EnchanterConfigInit.readJson();
        }
    }
}
