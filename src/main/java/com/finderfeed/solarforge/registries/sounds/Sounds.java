package com.finderfeed.solarforge.registries.sounds;

import com.finderfeed.solarforge.SolarForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Sounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,"solarforge");
    public static final RegistryObject<SoundEvent> CROSSBOW_SHOOT_SOUND = SOUND_EVENTS.register("crossbow_shoot",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"crossbow_shoot")));
    public static final RegistryObject<SoundEvent> CROSSBOW_CHARGING = SOUND_EVENTS.register("crossbow_charge",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"crossbow_charge")));
    public static final RegistryObject<SoundEvent> CROSSBOW_SHOT_IMPACT = SOUND_EVENTS.register("crossbow_shot_impact",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"crossbow_shot_impact")));
    public static final RegistryObject<SoundEvent> SOLAR_MORTAR_SHOOT = SOUND_EVENTS.register("solar_mortar_shoot",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"solar_mortar_shoot")));
    public static final RegistryObject<SoundEvent> SOLAR_MORTAR_PROJECTILE = SOUND_EVENTS.register("solar_mortar_projectile",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"solar_mortar_projectile")));
    public static final RegistryObject<SoundEvent> GEM_INSERT = SOUND_EVENTS.register("gem_insert",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"gem_insert")));
    public static final RegistryObject<SoundEvent> RAY_TRAP_PROC = SOUND_EVENTS.register("ray_trap_proc",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"ray_trap_proc")));
    public static final RegistryObject<SoundEvent> BUTTON_PRESS2 = SOUND_EVENTS.register("button_press2c",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"button_press2")));
    public static final RegistryObject<SoundEvent> PROGRESSION_GAIN = SOUND_EVENTS.register("progression_unlock",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"progression_unlock")));
    public static final RegistryObject<SoundEvent> ZAP_TURRET_SHOT = SOUND_EVENTS.register("zap_turret_shot",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"zap_turret_shot")));
    public static final RegistryObject<SoundEvent> CRYSTAL_HIT = SOUND_EVENTS.register("crystal_hit",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"crystal_hit")));
    public static final RegistryObject<SoundEvent> SOLAR_EXPLOSION = SOUND_EVENTS.register("solar_explosion",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"solar_explosion")));
    public static final RegistryObject<SoundEvent> NIGHT_DIM = SOUND_EVENTS.register("night_beggining",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"night_beggining")));
    public static final RegistryObject<SoundEvent> AMBIENT_DIM_1 = SOUND_EVENTS.register("ambient_one",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"ambient_one")));
    public static final RegistryObject<SoundEvent> AMBIENT_DIM_2 = SOUND_EVENTS.register("ambient_two",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"ambient_two")));
    public static final RegistryObject<SoundEvent> LIGHTNING_GUN_SHOT = SOUND_EVENTS.register("lightning_gun_shot",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"lightning_gun_shot")));
    public static final RegistryObject<SoundEvent> SUNSTRIKE = SOUND_EVENTS.register("sunstrike",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"sunstrike")));
    public static final RegistryObject<SoundEvent> SHADOW_BOLT_EXPLOSION = SOUND_EVENTS.register("shadow_bolt",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"shadow_bolt")));
    public static final RegistryObject<SoundEvent> EARTHQUAKE = SOUND_EVENTS.register("earthquake",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"earthquake")));
    public static final RegistryObject<SoundEvent> SHADOW_ZOMBIE_HURT = SOUND_EVENTS.register("shadow_zombie_hurt",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"shadow_zombie_hurt")));
    public static final RegistryObject<SoundEvent> CLEARING_CRYSTAL_OVERLOAD = SOUND_EVENTS.register("clearing_crystal_overload",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"clearing_crystal_overload")));

    //original author: https://freesound.org/people/ejfortin/
    public static final RegistryObject<SoundEvent> BALL_LIGHTNING_BLOW = SOUND_EVENTS.register("ball_lightning_blow",()-> new SoundEvent(new ResourceLocation(SolarForge.MOD_ID,"ball_lightning_blow")));
}
