package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.local_library.entities.BossAttackChain;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunicElementalBoss extends Mob {

    private Map<String,InterpolatedValue> ANIMATION_VALUES = new HashMap<>();
    public BossAttackChain BOSS_ATTACK_CHAIN = new BossAttackChain.Builder().build();

    public RunicElementalBoss(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    @Override
    public void tick() {
        super.tick();


        if (level.isClientSide){
            List<String> delete = new ArrayList<>();
            for (Map.Entry<String,InterpolatedValue> entry : ANIMATION_VALUES.entrySet()){
                if (entry.getValue().canBeDeleted()){
                    delete.add(entry.getKey());
                }
                entry.getValue().tick();
            }
            for (String s : delete){
                ANIMATION_VALUES.remove(s);
            }
        }
    }

    public InterpolatedValue getOrCreateAnimationValue(String str, InterpolatedValue value){
        return ANIMATION_VALUES.computeIfAbsent(str,(s)->value);
    }
}
