package com.finderfeed.solarforge.abilities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.abilities.meteorite.MeteoriteProjectile;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;

import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Explosion;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SolarAbilities {


    public static void castAbility(ServerLevel world, ServerPlayer enti, String id) {
        if (!enti.isSpectator()) {
            if (Abilities.BY_IDS.containsKey(id)) {
                Abilities.BY_IDS.get(id).getAbility().cast(enti, world);
                for (RunicEnergy.Type type : RunicEnergy.Type.values()) {
                    Helpers.updateRunicEnergyOnClient(type, RunicEnergy.getEnergy(enti, type), enti);
                }
            }
        }
    }

}
