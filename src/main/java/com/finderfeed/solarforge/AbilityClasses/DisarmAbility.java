package com.finderfeed.solarforge.AbilityClasses;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
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
    public DisarmAbility(String id, int manacost) {
        super(id, manacost);
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_"+id) &&
                (CapabilitySolarMana.getSolarMana(entity).orElseThrow(RuntimeException::new).getMana() > manacost || entity.isCreative())) {

            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundCategory.AMBIENT, 1, 1);
            List<Entity> list = world.getEntities(entity, new AxisAlignedBB(-8, -8, -8, 8, 8, 8).move(entity.position()), x -> x instanceof LivingEntity);
            for (int i = 0; i < list.size(); i++) {
                LivingEntity ent = (LivingEntity) list.get(i);
                ent.addEffect(new EffectInstance(SolarForge.SOLAR_STUN.get(), 100, 0));
            }
            super.cast(entity, world);
        }

    }
}
