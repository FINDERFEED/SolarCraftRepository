package com.finderfeed.solarforge.registries.effects;

import com.finderfeed.solarforge.SolarAbilities.SolarStunEffect;
import com.finderfeed.solarforge.magic_items.effects.ImmortalityEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectsRegister {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,"solarforge");
    public static final RegistryObject<MobEffect> IMMORTALITY_EFFECT = EFFECTS.register("immortality",()-> new ImmortalityEffect(MobEffectCategory.BENEFICIAL,0x737373));


}
