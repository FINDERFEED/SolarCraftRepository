package com.finderfeed.solarforge.registries.effects;

import com.finderfeed.solarforge.SolarAbilities.SolarStunEffect;
import com.finderfeed.solarforge.magic_items.effects.ImmortalityEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectsRegister {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS,"solarforge");
    public static final RegistryObject<Effect> IMMORTALITY_EFFECT = EFFECTS.register("immortality",()-> new ImmortalityEffect(EffectType.BENEFICIAL,0x737373));


}
