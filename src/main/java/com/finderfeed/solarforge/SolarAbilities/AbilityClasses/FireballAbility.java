package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.server.level.ServerLevel;

public class FireballAbility extends AbstractAbility{

    public FireballAbility() {
        super("fireball",50, new RunicEnergyCostConstructor()
                .addRunicEnergy(RunicEnergy.Type.FIRA,200)
                .addRunicEnergy(RunicEnergy.Type.ARDO,100),15000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        super.cast(entity,world);
        if (allowed) {
            LargeFireball fireball = new LargeFireball(world, entity, entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z,6);
            fireball.setPos(entity.position().x + entity.getLookAngle().x * 1.5, entity.position().y + entity.getLookAngle().y * 1.5, entity.position().z + entity.getLookAngle().z * 1.5);
            world.addFreshEntity(fireball);

        }
    }
}
