package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.effects.SCEffects;
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
        super("solar_stun",new RunicEnergyCost()
        .set(RunicEnergy.Type.URBA,450)
        .set(RunicEnergy.Type.KELDA,600),45000);
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.AMBIENT, 1, 1);
            List<Entity> list = world.getEntities(entity, new AABB(-8, -8, -8, 8, 8, 8).move(entity.position()), x -> x instanceof LivingEntity);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity ent = (LivingEntity) list.get(i);
                ent.addEffect(new MobEffectInstance(SCEffects.SOLAR_STUN.get(), 100, 0));
            }
            AbilityHelper.spendAbilityEnergy(entity,this);
        }
    }
}
