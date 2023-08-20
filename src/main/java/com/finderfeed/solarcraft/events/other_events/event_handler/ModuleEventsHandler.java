package com.finderfeed.solarcraft.events.other_events.event_handler;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.entities.ShadowZombie;
import com.finderfeed.solarcraft.content.items.ModuleItem;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModuleEventsHandler {


    @SubscribeEvent
    public static void handleFurySwipes(AttackEntityEvent event){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        if (!player.level.isClientSide){
            ItemStack stack = player.getMainHandItem();
            if ((stack.getItem() instanceof SwordItem)  && (entity instanceof LivingEntity target)){
                if (hasModule(SCItems.FURY_SWIPES_MODULE.get(),stack)){
                    if (Helpers.isVulnerable(target)) {
                    DamageSource src = player.level.damageSources().playerAttack(player);

                        float bonusdmg = target.getPersistentData().getFloat(SolarCraftTags.FURY_SWIPES_DAMAGE);
                        float percent = player.getAttackStrengthScale(0);
                        target.hurt(src, bonusdmg * percent);
                        target.invulnerableTime = 0;
                        if (bonusdmg < 10) {
                            target.getPersistentData().putFloat(SolarCraftTags.FURY_SWIPES_DAMAGE, bonusdmg + 0.5f);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void handlePosioningBlade(AttackEntityEvent event){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        if (!player.level.isClientSide){
            ItemStack stack = player.getMainHandItem();
            if ((stack.getItem() instanceof SwordItem)  && (entity instanceof LivingEntity target)){
                if (hasModule(SCItems.POISONING_BLADE_MODULE.get(),stack)){
                    if (!target.hasEffect(MobEffects.POISON)){
                        target.addEffect(new MobEffectInstance(MobEffects.POISON,160,1));
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void handleDisarmingThorns(LivingDamageEvent event){
        DamageSource src = event.getSource();
        LivingEntity entity = event.getEntity();
        if (!entity.level.isClientSide && (src.getEntity() instanceof LivingEntity attacker) ){
            if (doesArmorHaveModule(SCItems.DISARMING_THORNS_MODULE.get(),entity.getArmorSlots()) != 0){
                if (entity.level.random.nextFloat() <= (float)SolarcraftConfig.DISARM_CHANCE_MODULE.get()/100){
                    if (!attacker.hasEffect(SolarCraft.SOLAR_STUN.get())){
                        attacker.addEffect(new MobEffectInstance(SolarCraft.SOLAR_STUN.get(),40,0));
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void handleMinerModule(PlayerInteractEvent.RightClickItem event){
        Player player = event.getEntity();
        Level world = player.level;
        InteractionHand hand = event.getHand();
        if (!world.isClientSide){
            ItemStack stack = player.getItemInHand(hand);
            if ((stack.getItem() instanceof PickaxeItem) && hasModule(SCItems.PICKAXE_MINER_ABILITY_MODULE.get(),stack)){
                if (RunicEnergy.spendEnergy(player,300, RunicEnergy.Type.URBA)){
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,400,1));
                }
            }
        }
    }


    @SubscribeEvent
    public static void handleMagicDamageBonus(AttackEntityEvent event){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        if (!player.level.isClientSide){
            ItemStack stack = player.getMainHandItem();
            if ((stack.getItem() instanceof SwordItem)  && (entity instanceof LivingEntity target)){
                if (hasModule(SCItems.MAGIC_DAMAGE_MODULE_5.get(),stack)){
                    if (Helpers.isVulnerable(target)) {
                        float modifier = player.getAttackStrengthScale(0);
                        DamageSource src = SCDamageSources.playerArmorPierce(player);
                        if (target instanceof ShadowZombie){
                            src = SCDamageSources.RUNIC_MAGIC;
                        }
                        target.hurt(src, modifier * 5);
                        target.invulnerableTime = 0;
                    }
                }
            }
        }
    }



    @SubscribeEvent
    public static void handleAOEAttackAbility(PlayerInteractEvent.RightClickItem event){
        Player player = event.getEntity();
        Level world = player.level;
        InteractionHand hand = event.getHand();
        if (!world.isClientSide){
            ItemStack stack = player.getItemInHand(hand);
            if ((stack.getItem() instanceof SwordItem sword) &&  hasModule(SCItems.SWORD_AOE_ATTACK.get(),stack)){
                AABB aabb = new AABB(player.position().add(-2.5,0,-2.5),player.position().add(2.5,player.getBbHeight(),2.5));
                world.getEntitiesOfClass(LivingEntity.class,aabb,(entity)-> !entity.equals(player)).forEach((livingEntity) -> {
                    livingEntity.hurt(world.damageSources().playerAttack(player),sword.getDamage()/2);
                });
                player.getCooldowns().addCooldown(sword,100);
                ((ServerLevel)world).playSound(null,player, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.AMBIENT,0.5f,1);
            }
        }
    }

    @SubscribeEvent
    public static void handleSwordAutohealEvent(LivingAttackEvent event){
        DamageSource src = event.getSource();
        if (src != null){
            if ((src.getEntity() instanceof Player player) && !player.level.isClientSide ){
                ItemStack stack = player.getMainHandItem();
                if (!stack.isEmpty()){
                    if (hasModule(SCItems.SWORD_AUTOHEAL_MODULE.get(),stack)){
                        if (player.level.random.nextFloat() <= ((float) SolarcraftConfig.AUTOHEAL_CHANCE.get()/100) ){
                            stack.hurt(-2,player.level.random,(ServerPlayer) player);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void handleBlessedModule(TickEvent.PlayerTickEvent event){
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.level.isClientSide) {
                if (event.player.level.getGameTime() % 20 == 1) {
                    ServerPlayer entity = (ServerPlayer) event.player;
                    entity.getArmorSlots().forEach((stack) -> {
                        if (hasModule(SCItems.BLESSED_MODULE.get(), stack)) {
                            if (Helpers.isDay(event.player.level)) {
                                if (entity.level.random.nextFloat() <= (float)SolarcraftConfig.BLESSED_CHANCE.get()/100) {
                                    stack.hurt(-1, entity.level.random, entity);
                                }
                            }
                        }
                    });

                }
            }
        }
    }



    @SubscribeEvent
    public static void handle10PhysicalDefenceModule(LivingDamageEvent event){
        DamageSource src = event.getSource();
        if (!event.getEntity().level.isClientSide) {
            if (!(src == event.getEntity().level.damageSources().magic()) && !src.is(DamageTypeTags.BYPASSES_ARMOR)) {
                LivingEntity entity = event.getEntity();
                entity.getArmorSlots().forEach((stack) -> {
                    if (hasModule(SCItems.PHYSICAL_DEFENCE_MODULE_10.get(), stack)) {
                        float amount = event.getAmount();
                        event.setAmount(amount * 0.9f);
                    }
                });
            }
        }
    }

    public static int doesArmorHaveModule(ModuleItem item,Iterable<ItemStack> armor){
        int a = 0;
        for (ItemStack stack : armor){
            if (!stack.isEmpty() && hasModule(item,stack)){
                a++;
            }
        }
        return a;
    }

    public static boolean hasModule(ModuleItem item, ItemStack target){
        return target.getTagElement(item.getSubTag()) != null;
    }
}
