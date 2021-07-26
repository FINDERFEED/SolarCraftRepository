package com.finderfeed.solarforge.SolarAbilities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarAbilities.meteorite.MeteoriteProjectile;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Explosion;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SolarAbilities {


    public static void castAbility(ServerLevel world, ServerPlayer enti, int index) {
        if (!enti.isSpectator()) {
                Abilities.getAll()[index-1].getAbility().cast(enti,world);
                for (RunicEnergy.Type type : RunicEnergy.Type.values()){
                    Helpers.updateRunicEnergyOnClient(type,RunicEnergy.getEnergy(enti,type),enti);
                }
//                if (index == 1) {
//                    FireballAbility ability = new FireballAbility();
//                    ability.cast(enti,world);
//
//                } else if (index == 2) {
//                    LightningAbility ability = new LightningAbility();
//                    ability.cast(enti,world);
//
//                } else if (index == 3) {
//                    SolarStrikeAbility ability = new SolarStrikeAbility();
//                    ability.cast(enti,world);
//
//                } else if (index == 4) {
//                    DisarmAbility ability = new DisarmAbility();
//                    ability.cast(enti,world);
//
//                } else if (index == 5) {
//                    MeteoriteAbility ability = new MeteoriteAbility();
//                    ability.cast(enti,world);
//
//                } else if (index == 6){
//                    HealAbility ability = new HealAbility();
//                    ability.cast(enti,world);
//                }else if (index == 7){
//                    AlchemistAbility ability = new AlchemistAbility();
//                    ability.cast(enti,world);
//                }else if (index == 8){
//                    DispelAbility ability = new DispelAbility();
//                    ability.cast(enti,world);
//                }

            //else{
//                double mana = CapabilitySolarMana.getSolarMana(enti).orElseThrow(RuntimeException::new).getMana();
//                if (index == 1 && mana >=50) {
//                    FireballAbility ability = new FireballAbility("fireball",50);
//                    ability.cast(enti,world);
//                    //SummonFireball(world, enti);
//                } else if (index == 2 && mana >=50) {
//
//                    struckLightning(world, enti);
//                } else if (index == 3 && mana >=1000) {
//
//                    solarStrike(world, enti);
//                } else if (index == 4 && mana >=300) {
//
//                    stunEnemies(world, enti);
//                } else if (index == 5 && mana >=500) {
//
//                    meteorStrike(world, enti);
//                }else if (index == 6 && mana >= 250){
//
//                    healPlayer(world,enti);
//                }else if (index == 7){
//                    toggleAlchemist((ServerPlayerEntity) enti);
//                }else if (index == 8 && mana >= 400){
//
//                    dispelPlayer(enti);
//                }
//            }

        }

    }

    //1
    public static void SummonFireball(ServerLevel world, Player enti) {
        if (enti.getPersistentData().getBoolean("solar_forge_can_player_use_fireball")) {
            if (!enti.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(enti).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(enti).orElseThrow(RuntimeException::new).setMana(mana - 50);
            }
            LargeFireball fireball = new LargeFireball(world, enti, enti.getLookAngle().x, enti.getLookAngle().y, enti.getLookAngle().z);
            fireball.setPos(enti.position().x + enti.getLookAngle().x * 1.5, enti.position().y + enti.getLookAngle().y * 1.5, enti.position().z + enti.getLookAngle().z * 1.5);
            fireball.explosionPower = 6;
            world.addFreshEntity(fireball);
        }
    }

    //2
    public static void struckLightning(ServerLevel world, Player entity) {

        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_lightning")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 50);
            }
            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);
            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);

            if (result.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    LightningBolt entityBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    world.addFreshEntity(entityBolt);
                    world.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 6, true, Explosion.BlockInteraction.BREAK);
                    System.out.println(entityBolt.position());
                }

            }
        }

    }

    //3
    public static void solarStrike(ServerLevel world, Player entity) {

        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_solar_strike")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 1000);
            }
            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);
            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);

            if (result.getType() == HitResult.Type.BLOCK) {

                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    SolarStrikeEntity entityBolt = new SolarStrikeEntity(SolarForge.SOLAR_STRIKE_ENTITY_REG.get(), world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                    world.addFreshEntity(entityBolt);
                }
            }
        }

    }

    //4
    public static void stunEnemies(ServerLevel world, Player entity) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_solar_stun")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 300);
            }
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.AMBIENT, 1, 1);
            List<Entity> list = world.getEntities(entity, new AABB(-8, -8, -8, 8, 8, 8).move(entity.position()), x -> x instanceof LivingEntity);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity ent = (LivingEntity) list.get(i);
                ent.addEffect(new MobEffectInstance(SolarForge.SOLAR_STUN.get(), 100, 0));
            }
        }
    }

    //5
    public static void meteorStrike(ServerLevel world, Player entity) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_meteorite")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 500);
            }
            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);

            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);

            if (result.getType() == HitResult.Type.BLOCK) {

                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    MeteoriteProjectile proj = new MeteoriteProjectile(entity, world);
                    Vector3f posVect = new Vector3f((float) entity.position().x - (float) entity.getLookAngle().x * 20, (float) entity.position().y + 64, (float) entity.position().z - (float) entity.getLookAngle().z * 20);
                    proj.setPos(posVect.x(), posVect.y(), posVect.z());
                    Vec3 velocity = new Vec3(pos.offset(0.5, 0, 0.5).getX() - posVect.x(), pos.offset(0.5, 0, 0.5).getY() - posVect.y(), pos.offset(0.5, 0, 0.5).getZ() - posVect.z());
                    proj.setDeltaMovement(velocity.normalize());
                    world.addFreshEntity(proj);
                }

            }
        }
    }
    //6
    public static void healPlayer(ServerLevel world,Player player){
        if (player.getPersistentData().getBoolean("solar_forge_can_player_use_solar_heal")) {
            if (!player.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(player).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(player).orElseThrow(RuntimeException::new).setMana(mana - 250);
            }
            player.heal(4);
        }

    }
    //7
    public static void toggleAlchemist(ServerPlayer player){
        if (player.getPersistentData().getBoolean("solar_forge_can_player_use_alchemist")){
            if (player.getPersistentData().getBoolean("is_alchemist_toggled")){
                //SolarForgePacketHandler.INSTANCE.sendTo(new ToggleAlchemistPacket(false),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                player.getPersistentData().putBoolean("is_alchemist_toggled",false);
            }else{
               // SolarForgePacketHandler.INSTANCE.sendTo(new ToggleAlchemistPacket(true),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                player.getPersistentData().putBoolean("is_alchemist_toggled",true);
            }

        }
    }
    //8
    public static void dispelPlayer(Player playerEntity){
        if (playerEntity.getPersistentData().getBoolean("solar_forge_can_player_use_solar_dispel")) {
            if (!playerEntity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(playerEntity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(playerEntity).orElseThrow(RuntimeException::new).setMana(mana - 400);
            }
            Object[] arr =  playerEntity.getActiveEffects().toArray();
            for (int i = 0; i < arr.length; i++) {
                if (!((MobEffectInstance)arr[i]).getEffect().isBeneficial()) {
                    playerEntity.removeEffect(((MobEffectInstance)arr[i]).getEffect());
                }
            }
        }
    }
}
