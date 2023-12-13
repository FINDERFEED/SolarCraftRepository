package com.finderfeed.solarcraft.registries.effects;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.effects.SolarCraftEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.neoforged.neoforge.registries.DeferredRegister;


public class SCEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, SolarCraft.MOD_ID);
    public static final DeferredHolder<MobEffect,MobEffect> IMMORTALITY_EFFECT = EFFECTS.register("immortality",()-> new SolarCraftEffect(MobEffectCategory.BENEFICIAL,0x737373));
    public static final DeferredHolder<MobEffect,MobEffect> STAR_GAZE_EFFECT = EFFECTS.register("star_gaze",()-> new SolarCraftEffect(MobEffectCategory.HARMFUL,0x29004a));
    public static final DeferredHolder<MobEffect,MobEffect> EVASION = EFFECTS.register("evasion",()-> new SolarCraftEffect(MobEffectCategory.BENEFICIAL,0x25db00));
    public static final DeferredHolder<MobEffect,MobEffect> ULDERA_CRYSTAL_PRESENCE = EFFECTS.register("uldera_crystal_presence",()-> new SolarCraftEffect(MobEffectCategory.HARMFUL,0x29004a));

}
