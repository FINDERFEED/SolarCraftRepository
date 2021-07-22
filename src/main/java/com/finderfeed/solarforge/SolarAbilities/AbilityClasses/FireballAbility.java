package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.server.ServerWorld;

public class FireballAbility extends AbstractAbility{

    public FireballAbility() {
        super("fireball",50, new RunicEnergyCostConstructor()
                .addRunicEnergy(RunicEnergy.Type.FIRA,200)
                .addRunicEnergy(RunicEnergy.Type.ARDO,100));
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        super.cast(entity,world);
        if (allowed) {
            FireballEntity fireball = new FireballEntity(world, entity, entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z);
            fireball.setPos(entity.position().x + entity.getLookAngle().x * 1.5, entity.position().y + entity.getLookAngle().y * 1.5, entity.position().z + entity.getLookAngle().z * 1.5);
            fireball.explosionPower = 6;
            world.addFreshEntity(fireball);

        }
    }
}
