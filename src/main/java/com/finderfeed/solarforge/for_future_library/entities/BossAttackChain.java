package com.finderfeed.solarforge.for_future_library.entities;

import net.minecraft.nbt.CompoundTag;

import java.util.*;

public class BossAttackChain {

    private final Map<String,BossAttack> BOSS_ATTACK_SERIALIZE_MAP;
    private BossAttack currentAttack;
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
        for (BossAttack attack : attacks) {
            attacksTime += attack.getTime();
        }
        this.maxTimeLength = (attacks.size()-1)*timeBetweenAttacks + attacksTime;
        this.BOSS_ATTACK_SERIALIZE_MAP = builder.ID_ATTACKS_MAP;
    }

    public void tick(){
        if (attackingInProgress){
            if ((currentAttack != null) && (currentAttack.shouldRunEveryTick())){
                currentAttack.run();
            }
            if (currentWaitTime != 0){
                currentWaitTime--;
            }else{
                if (wasLastActionAnAttack){
                    wasLastActionAnAttack = false;
                    currentWaitTime = timeBetweenAttacks;
                    currentAttack = null;
                }else{
                    wasLastActionAnAttack = true;
                    if (!attacksQueue.isEmpty()) {
                        BossAttack attack = attacksQueue.poll();
                        currentWaitTime = attack.getTime();
                        attack.run();
                        currentAttack = attack;
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
        private Map<String,BossAttack> ID_ATTACKS_MAP = new HashMap<>();

        public Builder(){}

        public Builder setTimeBetweenAttacks(int timeBetweenAttacks) {
            this.timeBetweenAttacks = timeBetweenAttacks;
            return this;
        }

        public Builder addAttack(String name,Runnable attack,int attackTime,boolean everyTick){
            BossAttack attackToAdd =new BossAttack(attack, attackTime,everyTick,name);
            this.ID_ATTACKS_MAP.put(name,attackToAdd);
            attacks.add(attackToAdd);
            return this;
        }
        public BossAttackChain build(){
            return new BossAttackChain(this);
        }
    }


    private static class BossAttack{

        private final Runnable attack;
        private final int time;
        private final boolean everyTick;
        private final String id;
        private BossAttack(Runnable attack,int time,boolean everyTick,String name){
            this.id = name;
            this.everyTick = everyTick;
            this.attack =attack;
            this.time = time;
        }

        public boolean shouldRunEveryTick(){
            return everyTick;
        }

        public String getSerializationId(){
            return id;
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
        if (currentAttack != null) {
            tag.putString("boss_attack_chain_current_attack", currentAttack.getSerializationId());
        }
        serializeQueue(tag);
    }

    public void load(CompoundTag tag){
        deserializeQueue(tag);
        ticker = tag.getInt("boss_attack_chain_ticker");
        currentWaitTime = tag.getInt("boss_attack_chain_current_attack_wait_time");
        wasLastActionAnAttack = tag.getBoolean("boss_attack_chain_was_last_an_attack");
        attackingInProgress = tag.getBoolean("boss_attack_chain_in_progress");
        String key = tag.getString("boss_attack_chain_current_attack");
        currentAttack = BOSS_ATTACK_SERIALIZE_MAP.getOrDefault(key, null);
    }

    private void deserializeQueue(CompoundTag tag){
        int length = tag.getInt("boss_attack_chain_length");
        if (length != 0){
            this.attacksQueue = new ArrayDeque<>(20);
            for (int i = 1;i < length;i++){
                attacksQueue.offer(BOSS_ATTACK_SERIALIZE_MAP.get(tag.getString("boss_attack_chain_queue_"+i)));
            }
        }
    }


    private void serializeQueue(CompoundTag tag){
        if (!attacksQueue.isEmpty()){
            tag.putInt("boss_attack_chain_length",attacksQueue.size());
            int id = 1;
            for (BossAttack attack : attacksQueue){
                tag.putString("boss_attack_chain_queue_"+id ,attack.getSerializationId());
                id++;
            }
        }
    }

}
