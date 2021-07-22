package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class DisarmAbility extends AbstractAbility{
    public DisarmAbility() {
        super("solar_stun",300,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.URBA,300)
        .addRunicEnergy(RunicEnergy.Type.KELDA,450));
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        super.cast(entity, world);
        if (allowed) {
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundCategory.AMBIENT, 1, 1);
            List<Entity> list = world.getEntities(entity, new AxisAlignedBB(-8, -8, -8, 8, 8, 8).move(entity.position()), x -> x instanceof LivingEntity);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity ent = (LivingEntity) list.get(i);
                ent.addEffect(new EffectInstance(SolarForge.SOLAR_STUN.get(), 100, 0));
            }
        }
    }
}
