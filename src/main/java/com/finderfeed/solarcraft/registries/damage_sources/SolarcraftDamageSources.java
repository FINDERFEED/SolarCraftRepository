package com.finderfeed.solarcraft.registries.damage_sources;

import net.minecraft.world.damagesource.DamageSource;

public class SolarcraftDamageSources {
    public static final DamageSource STARGAZE = new DamageSource("solarcraft_stargaze");
    public static final DamageSource SHADOW = new DamageSource("solarcraft_shadow");
    public static final DamageSource RUNIC_MAGIC = new DamageSource("runic_magic").setMagic().bypassArmor();
    public static final DamageSource ORBITAL_EXPLOSION = new DamageSource("orbital_explosion").setMagic().bypassArmor();
}
