package com.finderfeed.solarcraft.config;

import com.finderfeed.solarcraft.config.json_config.reflective.ConfigValue;
import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveJsonConfig;

public class SCItemConfig extends ReflectiveJsonConfig {


    @ConfigValue
    public float illidiumPickaxeDigCost = 2f;

    @ConfigValue
    public float illidiumPickaxeEnergyCapacity = 5000f;

    @ConfigValue
    public float illidiumAxeCharcoalDropChance = 0.2f;

    @ConfigValue
    public float qualadiumAxeDuplicationChance = 0.2f;

    @ConfigValue
    public float illidiumHoeAbilityCost = 75;

    @ConfigValue
    public float illidiumHoeEnergyCapacity = 3000f;

    @ConfigValue
    public float illidiumSwordHealChance = 0.1f;

    @ConfigValue
    public float ballLightningDamage = 10f;

    @ConfigValue
    public float ballLightningExplosionRadius = 10f;

    @ConfigValue
    public float lightningEmitterCost = 50f;

    @ConfigValue
    public float qualadiumPickaxeRewardChance = 0.2f;

    @ConfigValue
    public float radiantChestplateDamage = 1.5f;

    @ConfigValue
    public float radiantChestplateEvasionChance = 0.17f;

    @ConfigValue
    public float solarGodBowDamage = 10f;

    @ConfigValue
    public float solarGodBowUpgradeDamage = 5f;

    @ConfigValue
    public int solarGodBowOnFireTime = 8;

    @ConfigValue
    public int solarGodBowSlownessTime = 80;

    @ConfigValue
    public int solarGodPickaxeExpDropChance = 20;

    @ConfigValue
    public float solarGodSwordMagicDamageBonus = 5;

    @ConfigValue
    public float solarGodSwordProjectileDamage = 5;

    @ConfigValue
    public float solarGodSwordProjectileDamageBonus = 5;

    @ConfigValue
    public int solarGodSwordProjectileExplosionPower = 3;

    @ConfigValue
    public int solarGodSwordCooldown = 200;

    @ConfigValue
    public int totemOfImmortalityEffectTime = 400;

    @ConfigValue
    public float solarCrossbowDamageGain = 3.5f;

    @ConfigValue
    public float solarCrossbowShotCost = 100f;

    @ConfigValue
    public float solarCrossbowMaxDamage = 120f;

    @ConfigValue
    public float solarCrossbowRecoilStrength = 2f;

    @ConfigValue
    public float solarCrossbowRunicEnergyCapacity = 5000f;



    public SCItemConfig() {
        super("item_config");
    }
}
