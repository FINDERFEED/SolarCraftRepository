package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.abilities.AbilityHelper;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.server.level.ServerLevel;

public class FireballAbility extends AbstractAbility{

    public FireballAbility() {
        super("fireball", new RunicEnergyCost()
                .set(RunicEnergy.Type.FIRA,200)
                .set(RunicEnergy.Type.ARDO,100),15000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this)) {
            LargeFireball fireball = new LargeFireball(world, entity, entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z,6);
            fireball.setPos(entity.position().x + entity.getLookAngle().x * 1.5, entity.position().y + entity.getLookAngle().y * 1.5, entity.position().z + entity.getLookAngle().z * 1.5);
            world.addFreshEntity(fireball);
            AbilityHelper.spendAbilityEnergy(entity,this);
        }else {
            if (AbilityHelper.isAbilityBought(entity,this)){
                AbilityHelper.notEnoughEnergyMessage(entity,this);
            }
        }
    }
}
