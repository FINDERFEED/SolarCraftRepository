package com.finderfeed.solarcraft.misc_things;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IUpgradable {

    String UPGRADE_TAG = "solarcraft_upgrade_tag";

    default void upgrade(ItemStack prev, ItemStack stack){
        if (!(prev.getItem() instanceof IUpgradable)) return;
        int currentlvl = getItemLevel(prev);
        if (currentlvl >= getMaxUpgrades()) return;
        setItemLevel(stack,currentlvl+1);
    }

    String getUpgradeTagString();

    int getMaxUpgrades();

    /**
     * 1 - second level description
     * e.t.c...
     */
    List<Component> getUpgradeDescriptions();

    default int getItemLevel(ItemStack stack){
        if (!(stack.getItem() instanceof IUpgradable)) return 0;
        return stack.getOrCreateTagElement(UPGRADE_TAG).getInt(getUpgradeTagString());
    }

    default void setItemLevel(ItemStack stack,int level){
        if (!(stack.getItem() instanceof IUpgradable)) return;
        stack.getOrCreateTagElement(UPGRADE_TAG).putInt(getUpgradeTagString(),level);
    }

    default void addComponents(ItemStack item,List<Component> components){
        int level = getItemLevel(item);
        components.add(Component.translatable("solarcraft.item_upgrades.current_lvl").append(": " +
                (level + 1)).withStyle(ChatFormatting.GOLD));
        for (int i = 1; i <= getMaxUpgrades(); i++){
            Component component = getUpgradeDescriptions().get(i-1).copy().withStyle(ChatFormatting.GOLD);
            if (level < i){
                component = component.copy().withStyle(ChatFormatting.STRIKETHROUGH).withStyle(ChatFormatting.ITALIC);
            }
            components.add(component);
        }
    }


}
