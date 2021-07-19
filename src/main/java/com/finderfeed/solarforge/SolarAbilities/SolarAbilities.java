package com.finderfeed.solarforge.SolarAbilities;

import com.finderfeed.solarforge.AbilityClasses.*;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class SolarAbilities {


    public static void castAbility(ServerWorld world, ServerPlayerEntity enti, int index) {
        if (!enti.isSpectator()) {

                if (index == 1) {
                    FireballAbility ability = new FireballAbility("fireball",50);
                    ability.cast(enti,world);

                } else if (index == 2) {
                    LightningAbility ability = new LightningAbility("lightning",50);
                    ability.cast(enti,world);

                } else if (index == 3) {
                    SolarStrikeAbility ability = new SolarStrikeAbility("solar_strike",1000);
                    ability.cast(enti,world);

                } else if (index == 4) {
                    DisarmAbility ability = new DisarmAbility("solar_stun",300);
                    ability.cast(enti,world);

                } else if (index == 5) {
                    MeteoriteAbility ability = new MeteoriteAbility("meteorite",500);
                    ability.cast(enti,world);

                } else if (index == 6){
                    HealAbility ability = new HealAbility("solar_heal",250);
                    ability.cast(enti,world);
                }else if (index == 7){
                    AlchemistAbility ability = new AlchemistAbility("alchemist",0);
                    ability.cast(enti,world);
                }else if (index == 8){
                    DispelAbility ability = new DispelAbility("solar_dispel",400);
                    ability.cast(enti,world);
                }

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
    public static void SummonFireball(ServerWorld world, PlayerEntity enti) {
        if (enti.getPersistentData().getBoolean("solar_forge_can_player_use_fireball")) {
            if (!enti.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(enti).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(enti).orElseThrow(RuntimeException::new).setMana(mana - 50);
            }
            FireballEntity fireball = new FireballEntity(world, enti, enti.getLookAngle().x, enti.getLookAngle().y, enti.getLookAngle().z);
            fireball.setPos(enti.position().x + enti.getLookAngle().x * 1.5, enti.position().y + enti.getLookAngle().y * 1.5, enti.position().z + enti.getLookAngle().z * 1.5);
            fireball.explosionPower = 6;
            world.addFreshEntity(fireball);
        }
    }

    //2
    public static void struckLightning(ServerWorld world, PlayerEntity entity) {

        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_lightning")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 50);
            }
            Vector3d vec = entity.getLookAngle().multiply(200, 200, 200);
            RayTraceContext ctx = new RayTraceContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity);
            BlockRayTraceResult result = world.clip(ctx);

            if (result.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    LightningBoltEntity entityBolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    world.addFreshEntity(entityBolt);
                    world.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 6, true, Explosion.Mode.BREAK);
                    System.out.println(entityBolt.position());
                }

            }
        }

    }

    //3
    public static void solarStrike(ServerWorld world, PlayerEntity entity) {

        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_solar_strike")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 1000);
            }
            Vector3d vec = entity.getLookAngle().multiply(200, 200, 200);
            RayTraceContext ctx = new RayTraceContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity);
            BlockRayTraceResult result = world.clip(ctx);

            if (result.getType() == RayTraceResult.Type.BLOCK) {

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
    public static void stunEnemies(ServerWorld world, PlayerEntity entity) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_solar_stun")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 300);
            }
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundCategory.AMBIENT, 1, 1);
            List<Entity> list = world.getEntities(entity, new AxisAlignedBB(-8, -8, -8, 8, 8, 8).move(entity.position()), x -> x instanceof LivingEntity);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity ent = (LivingEntity) list.get(i);
                ent.addEffect(new EffectInstance(SolarForge.SOLAR_STUN.get(), 100, 0));
            }
        }
    }

    //5
    public static void meteorStrike(ServerWorld world, PlayerEntity entity) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_meteorite")) {
            if (!entity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).setMana(mana - 500);
            }
            Vector3d vec = entity.getLookAngle().multiply(200, 200, 200);

            RayTraceContext ctx = new RayTraceContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity);
            BlockRayTraceResult result = world.clip(ctx);

            if (result.getType() == RayTraceResult.Type.BLOCK) {

                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    MeteoriteProjectile proj = new MeteoriteProjectile(entity, world);
                    Vector3f posVect = new Vector3f((float) entity.position().x - (float) entity.getLookAngle().x * 20, (float) entity.position().y + 64, (float) entity.position().z - (float) entity.getLookAngle().z * 20);
                    proj.setPos(posVect.x(), posVect.y(), posVect.z());
                    Vector3d velocity = new Vector3d(pos.offset(0.5, 0, 0.5).getX() - posVect.x(), pos.offset(0.5, 0, 0.5).getY() - posVect.y(), pos.offset(0.5, 0, 0.5).getZ() - posVect.z());
                    proj.setDeltaMovement(velocity.normalize());
                    world.addFreshEntity(proj);
                }

            }
        }
    }
    //6
    public static void healPlayer(ServerWorld world,PlayerEntity player){
        if (player.getPersistentData().getBoolean("solar_forge_can_player_use_solar_heal")) {
            if (!player.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(player).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(player).orElseThrow(RuntimeException::new).setMana(mana - 250);
            }
            player.heal(4);
        }

    }
    //7
    public static void toggleAlchemist(ServerPlayerEntity player){
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
    public static void dispelPlayer(PlayerEntity playerEntity){
        if (playerEntity.getPersistentData().getBoolean("solar_forge_can_player_use_solar_dispel")) {
            if (!playerEntity.isCreative()) {
                double mana = CapabilitySolarMana.getSolarMana(playerEntity).orElseThrow(RuntimeException::new).getMana();
                CapabilitySolarMana.getSolarMana(playerEntity).orElseThrow(RuntimeException::new).setMana(mana - 400);
            }
            Object[] arr =  playerEntity.getActiveEffects().toArray();
            for (int i = 0; i < arr.length; i++) {
                if (!((EffectInstance)arr[i]).getEffect().isBeneficial()) {
                    playerEntity.removeEffect(((EffectInstance)arr[i]).getEffect());
                }
            }
        }
    }
}
