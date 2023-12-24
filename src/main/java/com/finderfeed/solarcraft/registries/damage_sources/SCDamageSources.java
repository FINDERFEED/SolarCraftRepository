package com.finderfeed.solarcraft.registries.damage_sources;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartedEvent;


@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SCDamageSources {

    public static final ResourceKey<DamageType> STARGAZE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"stargaze"));
    public static final ResourceKey<DamageType> SHADOW_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"shadow"));
    public static final ResourceKey<DamageType> RUNIC_MAGIC_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"runic_magic"));
    public static final ResourceKey<DamageType> ORBITAL_EXPLOSION_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"orbital_explosion"));
    public static final ResourceKey<DamageType> PLAYER_ATTACK_ARMOR_PIERCE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"player_attack_armor_pierce"));
    public static final ResourceKey<DamageType> MOB_ATTACK_ARMOR_PIERCE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"mob_attack_armor_pierce"));
    public static final ResourceKey<DamageType> MOB_ATTACK_ARMOR_PIERCE_PROJECTILE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"mob_attack_armor_pierce_projectile"));
    public static final ResourceKey<DamageType> MOB_ATTACK_ALL_RESISTANCE_IGNORE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(SolarCraft.MOD_ID,"mob_attack_all_resistance_ignore"));

    public static DamageSource STARGAZE;
    public static DamageSource SHADOW;
    public static DamageSource RUNIC_MAGIC;
    public static DamageSource ORBITAL_EXPLOSION;
    private static EntityDamageSource PLAYER_ATTACK_ARMOR_PIERCE;


    public static DamageSource playerArmorPierce(Player player){
        return PLAYER_ATTACK_ARMOR_PIERCE.create(player);
    }

    private static EntityDamageSource MOB_ATTACK_ARMOR_PIERCE;
    private static EntityDamageSource MOB_ATTACK_ALL_RESISTANCE_IGNORE;

    public static DamageSource livingArmorPierce(LivingEntity attacker){
        return MOB_ATTACK_ARMOR_PIERCE.create(attacker);
    }

    public static DamageSource livingAllResistanceIgnore(LivingEntity attacker){
        return MOB_ATTACK_ALL_RESISTANCE_IGNORE.create(attacker);
    }

    private static EntityDamageSource MOB_ATTACK_ARMOR_PIERCE_PROJECTILE;
    public static DamageSource livingArmorPierceProjectile(LivingEntity attacker){
        return MOB_ATTACK_ARMOR_PIERCE_PROJECTILE.create(attacker);
    }
    public static boolean initializedServerside = false;
    @SubscribeEvent
    public static void initiateDamageSources(ServerStartedEvent event){
            initializedServerside = true;
            initializeDamageSources(event.getServer().registryAccess());
    }
    public static void initializeDamageSources(RegistryAccess access){
        Registry<DamageType> types = access.registryOrThrow(Registries.DAMAGE_TYPE);
        STARGAZE = new DamageSource(types.getHolderOrThrow(STARGAZE_TYPE));
        SHADOW = new DamageSource(types.getHolderOrThrow(SHADOW_TYPE));
        RUNIC_MAGIC = new DamageSource(types.getHolderOrThrow(RUNIC_MAGIC_TYPE));
        ORBITAL_EXPLOSION = new DamageSource(types.getHolderOrThrow(ORBITAL_EXPLOSION_TYPE));
        PLAYER_ATTACK_ARMOR_PIERCE = new EntityDamageSource(types.getHolderOrThrow(PLAYER_ATTACK_ARMOR_PIERCE_TYPE));
        MOB_ATTACK_ARMOR_PIERCE = new EntityDamageSource(types.getHolderOrThrow(MOB_ATTACK_ARMOR_PIERCE_TYPE));
        MOB_ATTACK_ARMOR_PIERCE_PROJECTILE = new EntityDamageSource(types.getHolderOrThrow(MOB_ATTACK_ARMOR_PIERCE_PROJECTILE_TYPE));
        MOB_ATTACK_ALL_RESISTANCE_IGNORE = new EntityDamageSource(types.getHolderOrThrow(MOB_ATTACK_ALL_RESISTANCE_IGNORE_TYPE));
    }




    public static class EntityDamageSource{
        private Holder<DamageType> damageTypeHolder;

        public EntityDamageSource(Holder<DamageType> typeHolder){
            this.damageTypeHolder = typeHolder;
        }

        public DamageSource create(Entity attacker){
            return new DamageSource(damageTypeHolder,attacker);
        }
    }


}
