package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.magic_items.items.isters.ShieldOfSolarGodISTER;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import net.minecraft.item.ShieldItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;

import javax.annotation.Nullable;

import java.util.List;

public class ShieldOfSolarGod extends ShieldItem {



    public ShieldOfSolarGod(Properties p_i48470_1_) {
        super(p_i48470_1_);
    }




    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity player, int idk) {

        if (!world.isClientSide) {
            float damage = 0;
            int usingTime = (72000 - idk)/20;
            if (usingTime >= 5 && usingTime < 10){
                damage = 3.5f;
            }else if (usingTime >= 10 && usingTime < 15) {
                damage = 5.5f;
            }else if (usingTime >= 15 && usingTime < 20){
                damage = 7.5f;
            }else if (usingTime >= 20 && usingTime < 30){
                damage = 9.5f;
            }else if (usingTime >= 30){
                damage = 12;
            }

            List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(-5, -3, -5, 5, 3, 5)
                    .move(player.position().x , player.position().y , player.position().z ));
            list.remove(player);
            for (LivingEntity ent : list) {
                if (damage >= 7.5) {
                    Vector3d vec = player.position();
                    Vector3d vecPosTarget = ent.position();
                    Vector3d velocity = new Vector3d(vecPosTarget.x- vec.x,vecPosTarget.y- vec.y,vecPosTarget.z- vec.z).normalize();
                    ent.push(velocity.x*4,velocity.y*4,velocity.z*4);
                }

                ent.hurt(DamageSource.MAGIC, damage);
                ent.setSecondsOnFire(5);
                ((ServerWorld)world).sendParticles(ParticleTypes.FLAME,ent.getX(),ent.getY()+0.5f,ent.getZ(),20,0,0.02,0,0.1);


            }
            if (damage == 12){
                ((ServerWorld)world).playSound(null,player, SoundEvents.WITHER_BREAK_BLOCK, SoundCategory.AMBIENT,0.3f,0.5f);
            }
           // stack.getTagElement("current_damage").putInt("damage_", 0);

        }
        if (player.level.isClientSide) {
            ((ShieldOfSolarGodISTER) getItemStackTileEntityRenderer()).setBeingUsed(false);
        }
        super.releaseUsing(stack, world, player, idk);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarforge.solar_shield_of_god").withStyle(TextFormatting.GOLD));
        p_77624_3_.add(new StringTextComponent("5-10 : 3.5").withStyle(TextFormatting.GOLD));
        p_77624_3_.add(new StringTextComponent("10-15 : 5.5").withStyle(TextFormatting.GOLD));
        p_77624_3_.add(new StringTextComponent("15-20 : 7.5").withStyle(TextFormatting.GOLD));
        p_77624_3_.add(new StringTextComponent("20-30 : 9.5").withStyle(TextFormatting.GOLD));
        p_77624_3_.add(new StringTextComponent("30+ : 12").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (player.level.isClientSide) {
            ((ShieldOfSolarGodISTER) getItemStackTileEntityRenderer()).setBeingUsed(true);
        }
        float damage = 0;
        int usingTime = (72000 - count)/20;
        if (usingTime >= 5 && usingTime < 10){
            damage = 3.5f;
        }else if (usingTime >= 10 && usingTime < 15) {
            damage = 5.5f;
        }else if (usingTime >= 15 && usingTime < 20){
            damage = 7.5f;
        }else if (usingTime >= 20 && usingTime < 30){
            damage = 9.5f;
        }else if (usingTime >= 30){
            damage = 12;
        }
        ((PlayerEntity)player).displayClientMessage(new StringTextComponent("-"+ (int) Math.floor((float) (72000 - count) / 20) +"->"+damage+"-").withStyle(TextFormatting.GOLD),true);
        super.onUsingTick(stack, player, count);
    }







}
