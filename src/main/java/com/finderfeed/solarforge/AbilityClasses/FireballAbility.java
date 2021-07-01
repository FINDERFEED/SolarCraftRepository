package com.finderfeed.solarforge.AbilityClasses;

import com.finderfeed.solarforge.capability_mana.CapabilitySolarMana;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FireballAbility extends AbstractAbility{

    public FireballAbility(String id, int manacost) {
        super(id, manacost);
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_"+id) &&
                (CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana() > manacost || entity.isCreative())) {
            FireballEntity fireball = new FireballEntity(world, entity, entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z);
            fireball.setPos(entity.position().x + entity.getLookAngle().x * 1.5, entity.position().y + entity.getLookAngle().y * 1.5, entity.position().z + entity.getLookAngle().z * 1.5);
            fireball.explosionPower = 6;
            world.addFreshEntity(fireball);
            super.cast(entity,world);
        }
    }
}
