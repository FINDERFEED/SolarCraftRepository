package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class DisarmAbility extends AbstractAbility{
    public DisarmAbility() {
        super("solar_stun",300,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.URBA,300)
        .addRunicEnergy(RunicEnergy.Type.KELDA,450),45000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        super.cast(entity, world);
        if (allowed) {
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.AMBIENT, 1, 1);
            List<Entity> list = world.getEntities(entity, new AABB(-8, -8, -8, 8, 8, 8).move(entity.position()), x -> x instanceof LivingEntity);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity ent = (LivingEntity) list.get(i);
                ent.addEffect(new MobEffectInstance(SolarForge.SOLAR_STUN.get(), 100, 0));
            }
        }
    }
}
