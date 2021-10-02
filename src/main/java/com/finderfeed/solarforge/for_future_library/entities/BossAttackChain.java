package com.finderfeed.solarforge.for_future_library.entities;

import net.minecraft.nbt.CompoundTag;

import java.util.*;

public class BossAttackChain {

    private int ticker = 0;
    private int maxTimeLength;
    private final int timeBetweenAttacks;
    private final List<BossAttack> attacks;
    private Queue<BossAttack> attacksQueue = new ArrayDeque<>(20);
    private boolean attackingInProgress = true;
    private int currentWaitTime = 0;
    private boolean wasLastActionAnAttack = false;

    private BossAttackChain(Builder builder){
        this.attacks = builder.attacks;
        this.timeBetweenAttacks = builder.timeBetweenAttacks;
        int attacksTime = 0;
        for (int i = 0; i < attacks.size();i++){
            BossAttack attack = attacks.get(i);
            attacksTime+=attack.getTime();
        }
        this.maxTimeLength = (attacks.size()-1)*timeBetweenAttacks + attacksTime;
    }

    public void tick(){
        if (attackingInProgress){
            if (currentWaitTime != 0){
                currentWaitTime--;
            }else{
                if (wasLastActionAnAttack){
                    wasLastActionAnAttack = false;
                    currentWaitTime = timeBetweenAttacks;
                }else{
                    wasLastActionAnAttack = true;
                    if (!attacksQueue.isEmpty()) {
                        BossAttack attack = attacksQueue.poll();
                        currentWaitTime = attack.getTime();
                        attack.run();
                    }else{
                        attackingInProgress = false;
                    }

                }
            }

            ticker++;
        }else{
            ticker = 0;
            populateQueue();
            attackingInProgress = true;
            wasLastActionAnAttack = false;
        }
    }

    private void populateQueue(){
        Random rnd = new Random();
        List<BossAttack> copy = new ArrayList<>(attacks);
        for (int i =0 ; i <copy.size();i++){
            BossAttack attack= copy.get(rnd.nextInt(copy.size()));
            attacksQueue.offer(attack);
            copy.remove(attack);
        }
    }




    public static class Builder{

        private int timeBetweenAttacks;
        private List<BossAttack> attacks = new ArrayList<>();

        public Builder(){}

        public Builder setTimeBetweenAttacks(int timeBetweenAttacks) {
            this.timeBetweenAttacks = timeBetweenAttacks;
            return this;
        }

        public Builder addAttack(Runnable attack,int attackTime){
            attacks.add(new BossAttack(attack, attackTime));
            return this;
        }
        public BossAttackChain build(){
            return new BossAttackChain(this);
        }
    }


    private static class BossAttack{

        private final Runnable attack;
        private final int time;

        private BossAttack(Runnable attack,int time){
            this.attack =attack;
            this.time = time;
        }

        public int getTime() {
            return time;
        }

        public void run(){
            attack.run();
        }

    }


    public void save(CompoundTag tag){
        tag.putInt("boss_attack_chain_ticker",ticker);
        tag.putInt("boss_attack_chain_current_attack_wait_time",currentWaitTime);
        tag.putBoolean("boss_attack_chain_was_last_an_attack",wasLastActionAnAttack);
        tag.putBoolean("boss_attack_chain_in_progress",attackingInProgress);


    }

    public void load(CompoundTag tag){
        ticker = tag.getInt("boss_attack_chain_ticker");
        currentWaitTime = tag.getInt("boss_attack_chain_current_attack_wait_time");
        wasLastActionAnAttack = tag.getBoolean("boss_attack_chain_was_last_an_attack");
        attackingInProgress = tag.getBoolean("boss_attack_chain_in_progress");
    }
}
