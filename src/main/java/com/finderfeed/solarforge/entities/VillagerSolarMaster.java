package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.*;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;

public class VillagerSolarMaster extends PathfinderMob {

    private List<UUID> TRADED_WITH = new ArrayList<>();
    private int countPlayers = 0;

    public VillagerSolarMaster(EntityType<? extends PathfinderMob> p_i48575_1_, Level p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3,new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10,new RandomStrollGoal(this,0.5));

        super.registerGoals();
    }

    public static AttributeSupplier.Builder createAttributes(){

        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.MOVEMENT_SPEED,0.5);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (!level.isClientSide && (hand.equals(InteractionHand.MAIN_HAND))) {
            ItemStack stack = Items.EMERALD.getDefaultInstance();
            stack.setCount(64);


            if (Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_LENS, player) &&
                    ((player.getMainHandItem().getItem() == Items.EMERALD) && (player.getMainHandItem().getCount() == player.getMainHandItem().getMaxStackSize()))) {
                if (TRADED_WITH.contains(player.getUUID())) {
                    player.sendMessage(new TranslatableComponent("solarcraft.already_traded"), player.getUUID());
                    playSound(SoundEvents.VILLAGER_NO,1,1);
                } else {
                    player.sendMessage(new TranslatableComponent("solarcraft.use_villager_success"), player.getUUID());
                    Helpers.fireProgressionEvent(player,Achievement.TRADE_FOR_BLUE_GEM);

                    playSound(SoundEvents.VILLAGER_YES,1,1);
                    TRADED_WITH.add(player.getUUID());
                    player.getMainHandItem().setCount(0);
                    player.addItem(new ItemStack(ItemsRegister.BLUE_GEM.get(),30));
                }

            } else if (Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_LENS, player)) {
                if (TRADED_WITH.contains(player.getUUID())) {
                    player.sendMessage(new TranslatableComponent("solarcraft.already_traded"), player.getUUID());
                    playSound(SoundEvents.VILLAGER_NO,1,1);
                } else {
                    player.sendMessage(new TranslatableComponent("solarcraft.bring_emeralds"), player.getUUID());
                }

            } else {
                player.sendMessage(new TranslatableComponent("solarcraft.not_enough_skill"), player.getUUID());
                playSound(SoundEvents.VILLAGER_NO,1,1);
            }

        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean save(CompoundTag cmp) {
        this.countPlayers = TRADED_WITH.size();

        for (int i = 0;i<TRADED_WITH.size();i++){
            cmp.putUUID("uuidplayers"+i,TRADED_WITH.get(i));
        }
        cmp.putInt("countplayers",countPlayers);
        return super.save(cmp);
    }

    @Override
    public void load(CompoundTag cmp) {
        countPlayers = cmp.getInt("countplayers");
        for (int i = 0;i<countPlayers;i++){
            TRADED_WITH.add(cmp.getUUID("uuidplayers"+i));
        }
        super.load(cmp);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.VILLAGER_HURT;
    }



    @Override
    public void checkDespawn() {
        this.noActionTime = 0;
    }
}
