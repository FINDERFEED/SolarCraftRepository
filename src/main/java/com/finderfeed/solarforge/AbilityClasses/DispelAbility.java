package com.finderfeed.solarforge.AbilityClasses;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.server.ServerWorld;

public class DispelAbility extends AbstractAbility{
    public DispelAbility(String id, int manacost) {
        super(id, manacost);
    }

    @Override
    public void cast(ServerPlayerEntity playerEntity, ServerWorld world) {
        if (playerEntity.getPersistentData().getBoolean("solar_forge_can_player_use_"+id)) {

            Object[] arr =  playerEntity.getActiveEffects().toArray();
            for (int i = 0; i < arr.length; i++) {
                if (!((EffectInstance)arr[i]).getEffect().isBeneficial()) {
                    playerEntity.removeEffect(((EffectInstance)arr[i]).getEffect());
                }
            }
            super.cast(playerEntity, world);
        }

    }
}
