package com.finderfeed.solarcraft.config;

import com.finderfeed.solarcraft.config.json_config.reflective.ConfigValue;
import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveJsonConfig;
import com.finderfeed.solarcraft.content.abilities.AbilityStats;
import com.finderfeed.solarcraft.content.abilities.DefaultAbilityStats;
import com.finderfeed.solarcraft.content.abilities.ability_classes.*;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;

public class SCAbilitiesConfig extends ReflectiveJsonConfig {


    @ConfigValue
    public AbilityStats alchemistAbilityStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost().set(RunicEnergy.Type.TERA,2.5f),25000))
                    .addStat(AlchemistAbility.EXPERIENCE_PER_BLOCK,10f);

    @ConfigValue
    public AbilityStats disarmAbilityStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost()
                    .set(RunicEnergy.Type.URBA,450)
                    .set(RunicEnergy.Type.KELDA,600),45000))
                    .addStat(DisarmAbility.DISARM_RADIUS,8f)
                    .addStat(DisarmAbility.DISARM_DURATION,100f);
    @ConfigValue
    public AbilityStats dispelAbilityStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost()
                    .set(RunicEnergy.Type.FIRA,350)
                    .set(RunicEnergy.Type.ZETA,500)
                    .set(RunicEnergy.Type.KELDA,100),20000));
    @ConfigValue
    public AbilityStats fireballAbilityStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost()
                    .set(RunicEnergy.Type.FIRA,200)
                    .set(RunicEnergy.Type.ARDO,100),15000))
                    .addStat(FireballAbility.EXPLOSION_POWER,6f);
    @ConfigValue
    public AbilityStats healAbilityStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost()
                    .set(RunicEnergy.Type.ZETA,200)
                    .set(RunicEnergy.Type.URBA,350)
                    .set(RunicEnergy.Type.ARDO,400),15000))
                    .addStat(HealAbility.HEAL_AMOUNT,4f);
    @ConfigValue
    public AbilityStats lightningAbilityStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost()
                    .set(RunicEnergy.Type.KELDA,800)
                    .set(RunicEnergy.Type.URBA,250),30000))
                    .addStat(LightningAbility.EXPLOSION_POWER_TOP,6f)
                    .addStat(LightningAbility.EXPLOSION_POWER_BOTTOM,4f);
    @ConfigValue
    public AbilityStats magicBoltStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,100), 40000))
                    .addStat(MagicBoltAbility.DAMAGE,5f);
    @ConfigValue
    public AbilityStats meteoriteStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost()
                    .set(RunicEnergy.Type.ZETA,1000)
                    .set(RunicEnergy.Type.KELDA,300),50000))
                    .addStat(MeteoriteAbility.DAMAGE,50f);
    @ConfigValue
    public AbilityStats solarStrikeStats =
            new AbilityStats(new DefaultAbilityStats(new RunicEnergyCost()
                    .set(RunicEnergy.Type.FIRA,5000)
                    .set(RunicEnergy.Type.ARDO,2000)
                    .set(RunicEnergy.Type.KELDA,1000),100000))
                    .addStat(SolarStrikeAbility.DAMAGE,300f);

    public SCAbilitiesConfig() {
        super("abilities_config");
    }

}
