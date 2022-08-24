package com.finderfeed.solarforge.content.blocks.blockentities.runic_energy;

import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;
//idk why its an interface. dont even ask me, or i will grief your base.
public interface IRunicEnergySaver {

    String ITEM_STACK_TAG = "solarcraft_runic_energy";

    ItemStack droppedStack();

    static ItemStack defaultSave(ItemStack stack,AbstractRunicEnergyContainer container){
        container.getRunicEnergyContainer().saveToTag(stack.getOrCreateTagElement(ITEM_STACK_TAG));
        return stack;
    }

    static void defaultLoad(ItemStack stack, AbstractRunicEnergyContainer tile){
        tile.getRunicEnergyContainer().loadFromTag(stack.getOrCreateTagElement(ITEM_STACK_TAG));
    }

    static void addHoverText(ItemStack stack, List<Component> components){
        RunicEnergyContainer container = new RunicEnergyContainer();
        container.loadFromTag(stack.getOrCreateTagElement(ITEM_STACK_TAG));
        components.add(new TranslatableComponent("solarcraft.contained_energy"));
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            components.add(new TextComponent(type.id.toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.GOLD)
                    .append(new TextComponent(" " + container.get(type) + "/" + container.getMaxEnergy()).withStyle(ChatFormatting.WHITE)));
        }
    }

    static void addHoverText(ItemStack stack, List<Component> components,List<RunicEnergy.Type> types){
        RunicEnergyContainer container = new RunicEnergyContainer();
        container.loadFromTag(stack.getOrCreateTagElement(ITEM_STACK_TAG));
        components.add(new TranslatableComponent("solarcraft.contained_energy"));
        for (RunicEnergy.Type type : types){
            components.add(new TextComponent(type.id.toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.GOLD)
                    .append(new TextComponent(": " + container.get(type) + "/" + container.getMaxEnergy()).withStyle(ChatFormatting.WHITE)));
        }
    }

}
