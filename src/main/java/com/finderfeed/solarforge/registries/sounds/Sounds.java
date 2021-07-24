package com.finderfeed.solarforge.registries.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Sounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,"solarforge");
    public static final RegistryObject<SoundEvent> CROSSBOW_SHOOT_SOUND = SOUND_EVENTS.register("crossbow_shoot",()-> new SoundEvent(new ResourceLocation("solarforge","crossbow_shoot")));
    public static final RegistryObject<SoundEvent> CROSSBOW_CHARGING = SOUND_EVENTS.register("crossbow_charge",()-> new SoundEvent(new ResourceLocation("solarforge","crossbow_charge")));
    public static final RegistryObject<SoundEvent> CROSSBOW_SHOT_IMPACT = SOUND_EVENTS.register("crossbow_shot_impact",()-> new SoundEvent(new ResourceLocation("solarforge","crossbow_shot_impact")));
    public static final RegistryObject<SoundEvent> SOLAR_MORTAR_SHOOT = SOUND_EVENTS.register("solar_mortar_shoot",()-> new SoundEvent(new ResourceLocation("solarforge","solar_mortar_shoot")));
    public static final RegistryObject<SoundEvent> SOLAR_MORTAR_PROJECTILE = SOUND_EVENTS.register("solar_mortar_projectile",()-> new SoundEvent(new ResourceLocation("solarforge","solar_mortar_projectile")));
    public static final RegistryObject<SoundEvent> GEM_INSERT = SOUND_EVENTS.register("gem_insert",()-> new SoundEvent(new ResourceLocation("solarforge","gem_insert")));
    public static final RegistryObject<SoundEvent> RAY_TRAP_PROC = SOUND_EVENTS.register("ray_trap_proc",()-> new SoundEvent(new ResourceLocation("solarforge","ray_trap_proc")));
    public static final RegistryObject<SoundEvent> BUTTON_PRESS2 = SOUND_EVENTS.register("button_press2c",()-> new SoundEvent(new ResourceLocation("solarforge","button_press2")));
    public static final RegistryObject<SoundEvent> PROGRESSION_GAIN = SOUND_EVENTS.register("progression_unlock",()-> new SoundEvent(new ResourceLocation("solarforge","progression_unlock")));
}
