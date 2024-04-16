package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.AbilityStats;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.server.level.ServerLevel;

public class FireballAbility extends AbstractAbility{

    public static String EXPLOSION_POWER = "fireballExplosionPower";

    public FireballAbility() {
        super("fireball");
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {
            AbilityStats stats = SCConfigs.ABILITIES.fireballAbilityStats;
            int explosionPower = (int) stats.getStat(EXPLOSION_POWER);
            LargeFireball fireball = new LargeFireball(world, entity, entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z,explosionPower);
            fireball.setPos(entity.position().x + entity.getLookAngle().x * 1.5, entity.position().y + entity.getLookAngle().y * 1.5, entity.position().z + entity.getLookAngle().z * 1.5);
            world.addFreshEntity(fireball);
            AbilityHelper.spendAbilityEnergy(entity,this);
        }
    }

    @Override
    public RunicEnergyCost getCastCost() {
        return SCConfigs.ABILITIES.fireballAbilityStats.getDefaultAbilityStats().getCastCost();
    }

    @Override
    public int getBuyCost() {
        return SCConfigs.ABILITIES.fireballAbilityStats.getDefaultAbilityStats().getBuyCost();
    }
}
