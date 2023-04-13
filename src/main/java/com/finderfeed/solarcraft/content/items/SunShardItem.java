package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SunShardItem extends RareSolarcraftItem {

    public static final int MAX_HEATED_TIME = 200;

    public SunShardItem(Properties p_41383_) {
        super(p_41383_, ()->null);
    }


    @Override
    public void inventoryTick(ItemStack item, Level world, Entity holder, int slot, boolean inHands) {
        if (!world.isClientSide && holder instanceof LivingEntity living){
            proceedTime(item,living);
        }
        super.inventoryTick(item, world, holder, slot, inHands);
    }

    public void proceedTime(ItemStack item, LivingEntity entity){
        CompoundTag tag = item.getOrCreateTagElement("solarcraft_tag");
        boolean heated = tag.getBoolean("heated_up");
        if (heated){
            entity.setSecondsOnFire(2);
            int heatedTime = getHeatedTime(item) - 1;
            setHeatedTime(item,heatedTime);
            if (heatedTime <= 0){
                setHeated(item,false);
            }
        }else{
            setHeatedTime(item,0);
        }
    }

    public int getHeatedTime(ItemStack stack){
        CompoundTag tag = getTag(stack);
        return tag.getInt("heated_up_time");
    }

    public void setHeatedTime(ItemStack stack,int time){
        CompoundTag tag = getTag(stack);
        tag.putInt("heated_up_time", FDMathHelper.clamp(0,time,MAX_HEATED_TIME));
    }

    public void setHeated(ItemStack stack,boolean heated){
        CompoundTag tag = getTag(stack);
        tag.putBoolean("heated_up",heated);
    }

    public boolean isHeated(ItemStack stack){
        CompoundTag tag = getTag(stack);
        boolean heated = tag.getBoolean("heated_up");
        return heated;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }



    public CompoundTag getTag(ItemStack stack){
        return  stack.getOrCreateTagElement("solarcraft_tag");
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(item, world, components, flag);
        if (isHeated(item)){
            int time = getHeatedTime(item);
            components.add(Component.translatable("solarcraft.descriptions.item.sun_shard")
                            .withStyle(
                                    time % 40 >= 20 ? ChatFormatting.RED : ChatFormatting.GOLD
                            )
                    .append(Component.literal(": " + time).withStyle(
                            time % 40 >= 20 ? ChatFormatting.RED : ChatFormatting.GOLD
                    )));
        }
    }
}
