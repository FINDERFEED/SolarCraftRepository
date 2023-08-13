package com.finderfeed.solarcraft.registries.effects;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.effects.SolarCraftEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SCEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SolarCraft.MOD_ID);
    public static final RegistryObject<MobEffect> IMMORTALITY_EFFECT = EFFECTS.register("immortality",()-> new SolarCraftEffect(MobEffectCategory.BENEFICIAL,0x737373));
    public static final RegistryObject<MobEffect> STAR_GAZE_EFFECT = EFFECTS.register("star_gaze",()-> new SolarCraftEffect(MobEffectCategory.HARMFUL,0x29004a));
    public static final RegistryObject<MobEffect> EVASION = EFFECTS.register("evasion",()-> new SolarCraftEffect(MobEffectCategory.BENEFICIAL,0x25db00));
    public static final RegistryObject<MobEffect> ULDERA_CRYSTAL_PRESENCE = EFFECTS.register("uldera_crystal_presence",()-> new SolarCraftEffect(MobEffectCategory.HARMFUL,0x29004a));

}
