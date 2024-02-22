package com.finderfeed.solarcraft.registries.sounds;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SCSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT,"solarcraft");

    public static final DeferredHolder<SoundEvent,SoundEvent> SOLAR_STRIKE_SOUND = SOUND_EVENTS.register("solar_ray_sound",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation("solarcraft","solar_strike_explosion_sound")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SOLAR_STRIKE_BUILD_SOUND = SOUND_EVENTS.register("solar_ray_buildup_sound",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation("solarcraft","solar_strike_buildup")));



    public static final DeferredHolder<SoundEvent,SoundEvent> CROSSBOW_SHOOT_SOUND = SOUND_EVENTS.register("crossbow_shoot",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"crossbow_shoot")));
    public static final DeferredHolder<SoundEvent,SoundEvent> CROSSBOW_CHARGING = SOUND_EVENTS.register("crossbow_charge",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"crossbow_charge")));
    public static final DeferredHolder<SoundEvent,SoundEvent> CROSSBOW_SHOT_IMPACT = SOUND_EVENTS.register("crossbow_shot_impact",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"crossbow_shot_impact")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SOLAR_MORTAR_SHOOT = SOUND_EVENTS.register("solar_mortar_shoot",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"solar_mortar_shoot")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SOLAR_MORTAR_PROJECTILE = SOUND_EVENTS.register("solar_mortar_projectile",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"solar_mortar_projectile")));
    public static final DeferredHolder<SoundEvent,SoundEvent> GEM_INSERT = SOUND_EVENTS.register("gem_insert",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"gem_insert")));
    public static final DeferredHolder<SoundEvent,SoundEvent> RAY_TRAP_PROC = SOUND_EVENTS.register("ray_trap_proc",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"ray_trap_proc")));
    public static final DeferredHolder<SoundEvent,SoundEvent> BUTTON_PRESS2 = SOUND_EVENTS.register("button_press2c",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"button_press2")));
    public static final DeferredHolder<SoundEvent,SoundEvent> PROGRESSION_GAIN = SOUND_EVENTS.register("progression_unlock",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"progression_unlock")));
    public static final DeferredHolder<SoundEvent,SoundEvent> ZAP_TURRET_SHOT = SOUND_EVENTS.register("zap_turret_shot",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"zap_turret_shot")));
    public static final DeferredHolder<SoundEvent,SoundEvent> CRYSTAL_HIT = SOUND_EVENTS.register("crystal_hit",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"crystal_hit")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SOLAR_EXPLOSION = SOUND_EVENTS.register("solar_explosion",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"solar_explosion")));
    public static final DeferredHolder<SoundEvent,SoundEvent> NIGHT_DIM = SOUND_EVENTS.register("night_beggining",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"night_beggining")));
    public static final DeferredHolder<SoundEvent,SoundEvent> AMBIENT_DIM_1 = SOUND_EVENTS.register("ambient_one",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"ambient_one")));
    public static final DeferredHolder<SoundEvent,SoundEvent> AMBIENT_DIM_2 = SOUND_EVENTS.register("ambient_two",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"ambient_two")));
    public static final DeferredHolder<SoundEvent,SoundEvent> LIGHTNING_GUN_SHOT = SOUND_EVENTS.register("lightning_gun_shot",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"lightning_gun_shot")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SUNSTRIKE = SOUND_EVENTS.register("sunstrike",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"sunstrike")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SHADOW_BOLT_EXPLOSION = SOUND_EVENTS.register("shadow_bolt",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"shadow_bolt")));
    public static final DeferredHolder<SoundEvent,SoundEvent> EARTHQUAKE = SOUND_EVENTS.register("earthquake",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"earthquake")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SHADOW_ZOMBIE_HURT = SOUND_EVENTS.register("shadow_zombie_hurt",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"shadow_zombie_hurt")));
    public static final DeferredHolder<SoundEvent,SoundEvent> CLEARING_CRYSTAL_OVERLOAD = SOUND_EVENTS.register("clearing_crystal_overload",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"clearing_crystal_overload")));
    public static final DeferredHolder<SoundEvent,SoundEvent> CORRUPTION_WISP_HIT = SOUND_EVENTS.register("corruption_wisp_hit",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"corruption_wisp_hit")));
    public static final DeferredHolder<SoundEvent,SoundEvent> RITUAL_TILE_STRIKE = SOUND_EVENTS.register("ritual_tile_strike",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"ritual_tile_strike")));
    public static final DeferredHolder<SoundEvent,SoundEvent> ORBITAL_EXPLOSION = SOUND_EVENTS.register("orbital_explosion",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"orbital_explosion")));
    public static final DeferredHolder<SoundEvent,SoundEvent> SOLAR_STRIKE_ATTACK = SOUND_EVENTS.register("solar_strike_attack",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"solar_strike_attack")));
    public static final DeferredHolder<SoundEvent,SoundEvent> METEORITE_IMPACT = SOUND_EVENTS.register("meteorite_impact",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"meteorite_impact")));
    public static final DeferredHolder<SoundEvent,SoundEvent> METEORITE_FALLING = SOUND_EVENTS.register("falling_meteorite",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"falling_meteorite")));


    //original: https://freesound.org/people/juskiddink/sounds/101934/
    public static final DeferredHolder<SoundEvent,SoundEvent> DIMENSION_BREAK = SOUND_EVENTS.register("dimension_break",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"dimension_break")));


    //original author: https://freesound.org/people/ejfortin/
    public static final DeferredHolder<SoundEvent,SoundEvent> BALL_LIGHTNING_BLOW = SOUND_EVENTS.register("ball_lightning_blow",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(SolarCraft.MOD_ID,"ball_lightning_blow")));
}
