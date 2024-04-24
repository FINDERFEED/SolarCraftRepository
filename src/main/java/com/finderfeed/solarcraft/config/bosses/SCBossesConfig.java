package com.finderfeed.solarcraft.config.bosses;

import com.finderfeed.solarcraft.config.json_config.reflective.ConfigValue;
import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveJsonConfig;
import com.finderfeed.solarcraft.content.entities.CrystalBossEntity;
import com.finderfeed.solarcraft.content.entities.runic_elemental.RunicElementalBoss;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaCrystalBoss;

public class SCBossesConfig extends ReflectiveJsonConfig {


    @ConfigValue
    public BossStats crystalConstruct = new BossStats()
            .addStat(CrystalBossEntity.MISSILE_DAMAGE_ID,CrystalBossEntity.MISSILE_DAMAGE)
            .addStat(CrystalBossEntity.RAY_DAMAGE_ID,CrystalBossEntity.RAY_DAMAGE)
            .addStat(CrystalBossEntity.AIR_STRIKE_DAMAGE_ID,CrystalBossEntity.AIR_STRIKE_DAMAGE)
            .addStat(CrystalBossEntity.MINES_DAMAGE_ID,CrystalBossEntity.MINES_DAMAGE)
            .addStat(CrystalBossEntity.RIP_RAY_DAMAGE_ID,CrystalBossEntity.RIP_RAY_DAMAGE);


    @ConfigValue
    public BossStats runicElemental = new BossStats()
            .addStat(RunicElementalBoss.SUNSTRIKES_DAMAGE_ID,RunicElementalBoss.SUNTRIKES_DAMAGE)
            .addStat(RunicElementalBoss.EARTHQUAKE_DAMAGE_ID,RunicElementalBoss.EARTHQUAKE_DAMAGE)
            .addStat(RunicElementalBoss.MISSILES_DAMAGE_ID,RunicElementalBoss.MISSILES_DAMAGE)
            .addStat(RunicElementalBoss.HAMMER_ATTACK_DAMAGE_ID,RunicElementalBoss.HAMMER_ATTACK_DAMAGE)
            .addStat(RunicElementalBoss.VARTH_DADER_DAMAGE_ID,RunicElementalBoss.VARTH_DADER_DAMAGE)
            .addStat(RunicElementalBoss.DAMAGE_AMPLIFICATION_BLOCK_BONUS_ID,RunicElementalBoss.DAMAGE_AMPLIFICATION_BLOCK_BONUS)
            ;

    @ConfigValue
    public BossStats ulderaCrystal = new BossStats()
            .addStat(UlderaCrystalBoss.ELECTRIC_RAIN_PROJECTILE_DAMAGE_ID,UlderaCrystalBoss.ELECTRIC_RAIN_PROJECTILE_DAMAGE)
            .addStat(UlderaCrystalBoss.HOMING_STARS_DAMAGE_ID,UlderaCrystalBoss.HOMING_STARS_DAMAGE)
            .addStat(UlderaCrystalBoss.LIGHTNING_DAMAGE_ID,UlderaCrystalBoss.LIGHTNING_DAMAGE)
            .addStat(UlderaCrystalBoss.PULL_EXPLOSION_BASE_DAMAGE_ID,UlderaCrystalBoss.PULL_EXPLOSION_BASE_DAMAGE)
            .addStat(UlderaCrystalBoss.ELECTRIC_RAIN_PROJECTILE_SPAWN_FREQUENCY_ID,UlderaCrystalBoss.ELECTRIC_RAIN_PROJECTILE_SPAWN_FREQUENCY)
            .addStat(UlderaCrystalBoss.ELECTRIC_RAIN_LIFETIME_ID,UlderaCrystalBoss.ELECTRIC_RAIN_LIFETIME)
            ;

    public SCBossesConfig() {
        super("bosses_config");
    }
}
