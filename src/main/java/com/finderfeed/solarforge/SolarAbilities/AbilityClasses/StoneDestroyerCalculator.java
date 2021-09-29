package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

public class StoneDestroyerCalculator extends ExplosionDamageCalculator {

    public static StoneDestroyerCalculator INSTANCE_05 = new StoneDestroyerCalculator(0.5f);
    public static StoneDestroyerCalculator INSTANCE_03 = new StoneDestroyerCalculator(0.3f);
    public static StoneDestroyerCalculator INSTANCE_07 = new StoneDestroyerCalculator(0.7f);
    public static StoneDestroyerCalculator INSTANCE_01 = new StoneDestroyerCalculator(0.1f);

    private float mod = 1;

    public StoneDestroyerCalculator(float mod){
        this.mod = mod;
    }

    public void setMod(float mod) {
        this.mod = mod;
    }

    @Override
    public Optional<Float> getBlockExplosionResistance(Explosion p_46099_, BlockGetter p_46100_, BlockPos p_46101_, BlockState p_46102_, FluidState p_46103_) {
        return p_46102_.isAir() && p_46103_.isEmpty() ?
                Optional.empty() :
                Optional.of(Math.max(p_46102_.getExplosionResistance(p_46100_, p_46101_, p_46099_)*mod, p_46103_.getExplosionResistance(p_46100_, p_46101_, p_46099_)*mod));
    }
}
