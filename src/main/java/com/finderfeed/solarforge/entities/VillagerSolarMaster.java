package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VillagerSolarMaster extends CreatureEntity {

    private List<UUID> TRADED_WITH = new ArrayList<>();
    private int countPlayers = 0;

    public VillagerSolarMaster(EntityType<? extends CreatureEntity> p_i48575_1_, World p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3,new LookRandomlyGoal(this));
        this.goalSelector.addGoal(10,new RandomWalkingGoal(this,0.5));

        super.registerGoals();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes(){

        return CreatureEntity.createMobAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.MOVEMENT_SPEED,0.5);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {

        if (!level.isClientSide && (hand.equals(Hand.MAIN_HAND))) {
            ItemStack stack = Items.EMERALD.getDefaultInstance();
            stack.setCount(64);


            if (Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_LENS, player) &&
                    ((player.getMainHandItem().getItem() == Items.EMERALD) && (player.getMainHandItem().getCount() == player.getMainHandItem().getMaxStackSize()))) {
                if (TRADED_WITH.contains(player.getUUID())) {
                    player.sendMessage(new TranslationTextComponent("solarcraft.already_traded"), player.getUUID());
                    playSound(SoundEvents.VILLAGER_NO,1,1);
                } else {
                    player.sendMessage(new TranslationTextComponent("solarcraft.use_villager_success"), player.getUUID());
                    if (!Helpers.hasPlayerUnlocked(Achievement.TRADE_FOR_BLUE_GEM,player) && Helpers.canPlayerUnlock(Achievement.TRADE_FOR_BLUE_GEM,player)){
                        Helpers.setAchievementStatus(Achievement.TRADE_FOR_BLUE_GEM,player,true);
                        Helpers.triggerToast(Achievement.TRADE_FOR_BLUE_GEM,player);
                    }

                    playSound(SoundEvents.VILLAGER_YES,1,1);
                    TRADED_WITH.add(player.getUUID());
                    player.getMainHandItem().setCount(0);
                    player.addItem(new ItemStack(ItemsRegister.BLUE_GEM.get(),30));
                }

            } else if (Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_LENS, player)) {
                if (TRADED_WITH.contains(player.getUUID())) {
                    player.sendMessage(new TranslationTextComponent("solarcraft.already_traded"), player.getUUID());
                    playSound(SoundEvents.VILLAGER_NO,1,1);
                } else {
                    player.sendMessage(new TranslationTextComponent("solarcraft.bring_emeralds"), player.getUUID());
                }

            } else {
                player.sendMessage(new TranslationTextComponent("solarcraft.not_enough_skill"), player.getUUID());
                playSound(SoundEvents.VILLAGER_NO,1,1);
            }

        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean save(CompoundNBT cmp) {
        this.countPlayers = TRADED_WITH.size();

        for (int i = 0;i<TRADED_WITH.size();i++){
            cmp.putUUID("uuidplayers"+i,TRADED_WITH.get(i));
        }
        cmp.putInt("countplayers",countPlayers);
        return super.save(cmp);
    }

    @Override
    public void load(CompoundNBT cmp) {
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
