package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.local_library.entities.BossAttackChain;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

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

        if (!level.isClientSide){
//            BOSS_ATTACK_CHAIN.tick();
            LivingEntity target = getTarget();
            if (target != null){
                this.lookControl.setLookAt(target.position().add(0,target.getBbHeight()/2,0));
//                this.lookAt(EntityAnchorArgument.Anchor.EYES,target.position());
            }
        }
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
        super.tick();
    }



    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(this,Player.class,true));
        super.registerGoals();
    }

    public InterpolatedValue getOrCreateAnimationValue(String str, InterpolatedValue value){
        return ANIMATION_VALUES.computeIfAbsent(str,(s)->value);
    }

    @Override
    public boolean save(CompoundTag tag) {
        BOSS_ATTACK_CHAIN.save(tag);
        return super.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        BOSS_ATTACK_CHAIN.load(tag);
        super.load(tag);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static class AttackType{

    }
}
