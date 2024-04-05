package com.finderfeed.solarcraft.local_library.entities;

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
    private final Runnable betweenAttacks;

    private BossAttackChain(Builder builder){
        this.attacks = builder.attacks;
        this.timeBetweenAttacks = builder.timeBetweenAttacks;
        int attacksTime = 0;
        for (BossAttack attack : attacks) {
            attacksTime += attack.getTime();
        }
        this.maxTimeLength = (attacks.size()-1)*timeBetweenAttacks + attacksTime;
        this.BOSS_ATTACK_SERIALIZE_MAP = builder.ID_ATTACKS_MAP;
        this.betweenAttacks = builder.betweenAttacks;
    }

    public void tick(){
        if (attackingInProgress){
            if ((currentAttack != null) && (currentAttack.getRepeatInterval() != -1)){
                if (ticker % currentAttack.getRepeatInterval() == 0) {
                    currentAttack.run();
                }
            }
            if (currentWaitTime > 0){
                currentWaitTime--;
            }else{
                if (wasLastActionAnAttack){
                    if (currentAttack != null) {
                        if (currentAttack.hasPostEffect()) {
                            currentAttack.runPostEffect();
                        }
                    }
                    if (betweenAttacks != null){
                        betweenAttacks.run();
                    }
                    wasLastActionAnAttack = false;
                    currentWaitTime = timeBetweenAttacks;
                    currentAttack = null;
                }else{
                    wasLastActionAnAttack = true;
                    if (!attacksQueue.isEmpty()) {
                        BossAttack attack = attacksQueue.poll();
                        currentWaitTime = attack.getTime();
                        ticker = 0;
                        currentAttack = attack;
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

    public int getCurrentWaitTime() {
        return currentWaitTime;
    }

    public int getTicker() {
        return ticker;
    }

    /**
     * Its either attack time or time between attacks.
     */
    public void setCurrentWaitTime(int currentWaitTime) {
        this.currentWaitTime = currentWaitTime;
    }

    private void populateQueue(){
        Random rnd = new Random();
        List<BossAttack> copy = new ArrayList<>(attacks);
        copy.sort((attack1,attack2)->{
            return attack1.priority - attack2.priority;
        });


        List<BossAttack> filtered;
        while ((filtered = getNextAttacksForRandoming(copy)) != null) {
            int filteredSize = filtered.size();
            if (filteredSize != 0) {
                for (int i = 0; i < filteredSize; i++) {
                    BossAttack attack = filtered.get(rnd.nextInt(filtered.size()));
                    attacksQueue.offer(attack);
                    filtered.remove(attack);
                }
            }
        }

    }


    private List<BossAttack> getNextAttacksForRandoming(List<BossAttack> a){
        List<BossAttack> attacksToCopyTo = new ArrayList<>();
        if (a.size() != 0) {
            int intToSeek = a.get(0).priority;
            for (BossAttack h : a) {
                if (h.priority == intToSeek) {
                    attacksToCopyTo.add(h);
                }else{
                    break;
                }
            }
        }else{
            return null;
        }
        a.removeAll(attacksToCopyTo);
        return attacksToCopyTo;
    }

    public BossAttack getAttackByID(String id){
        return BOSS_ATTACK_SERIALIZE_MAP.get(id);
    }

    public List<BossAttack> getAttacks() {
        return attacks;
    }

    public BossAttack getCurrentAttack() {
        return currentAttack;
    }

    public static class Builder{

        private int timeBetweenAttacks;
        private List<BossAttack> attacks = new ArrayList<>();
        private Map<String,BossAttack> ID_ATTACKS_MAP = new HashMap<>();
        private Runnable betweenAttacks = null;

        public Builder(){}

        public Builder setTimeBetweenAttacks(int timeBetweenAttacks) {
            this.timeBetweenAttacks = timeBetweenAttacks;
            return this;
        }

        public Builder addAttack(String name,Runnable attack,int attackTime,Integer attackInterval,int priority){
            BossAttack attackToAdd;
            if (attackInterval != null){
                attackToAdd  =new BossAttack(attack, attackTime,attackInterval,name,priority);
            }else{
                attackToAdd = new BossAttack(attack, attackTime,-1,name,priority);
            }
            this.ID_ATTACKS_MAP.put(name,attackToAdd);
            attacks.add(attackToAdd);
            return this;
        }

        public Builder addPostEffectToAttack(String id,Runnable postEffect){
            this.ID_ATTACKS_MAP.get(id).addPostEffect(postEffect);
            return this;
        }

        public Builder addAftermathAttack(Runnable run){
            this.betweenAttacks = run;
            return this;
        }

        public BossAttackChain build(){
            return new BossAttackChain(this);
        }
    }


    public static class BossAttack {

        private final Runnable attack;
        private final int time;
        private final int tick;
        private final String id;
        private Runnable postEffect = null;
        private int priority;

        private BossAttack(Runnable attack,int time,int everyTick,String name,int priority){
            this.id = name;
            this.tick = everyTick;
            this.attack =attack;
            this.time = time;
            this.priority = priority;
        }

        private void runPostEffect(){
            postEffect.run();
        }

        private void addPostEffect(Runnable postEffect) {
            this.postEffect = postEffect;
        }

        private boolean hasPostEffect(){
            return postEffect != null;
        }

        public int getRepeatInterval(){
            return tick;
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
            for (int i = 1;i <= length;i++){
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
