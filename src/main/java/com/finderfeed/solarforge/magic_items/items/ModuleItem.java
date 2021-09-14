package com.finderfeed.solarforge.magic_items.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ModuleItem extends Item {

    private Type type;
    private String subTag;
    private ModuleItem.Tags[] incompatibleWith;

    public ModuleItem(Properties p_41383_,Type type,Tags subTag) {
        super(p_41383_);
        this.type = type;
        this.subTag = subTag.tag;
    }
    public ModuleItem(Properties p_41383_,Type type,Tags subTag,ModuleItem.Tags... incompatibleWith) {
        this(p_41383_,type,subTag);
        this.incompatibleWith = incompatibleWith;
    }

    public Tags[] getIncompatibleWith() {
        return incompatibleWith;
    }

    public Type getType(){
        return type;
    }

    public String getSubTag() {
        return subTag;
    }

    public enum Type{
        ARMOR,
        SWORDS,
        TOOLS,
        PICKAXES
    }


    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> sacredTexts, TooltipFlag p_41424_) {
        if (type == Type.ARMOR){
            sacredTexts.add(new TranslatableComponent("solarcraft.module_armor").withStyle(ChatFormatting.GOLD));
        }else if(type == Type.SWORDS){
            sacredTexts.add(new TranslatableComponent("solarcraft.module_swords").withStyle(ChatFormatting.GOLD));
        }else if (type == Type.PICKAXES){
            sacredTexts.add(new TranslatableComponent("solarcraft.module_pickaxes").withStyle(ChatFormatting.GOLD));
        }

        sacredTexts.add(new TranslatableComponent(subTag).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_41421_, p_41422_, sacredTexts, p_41424_);
    }

    public enum Tags{
        DEFENCE_MODULE_PHYSICAL_10("solarcraft_10_percent_defence_physical"),
        SWORD_AUTOHEAL_MODULE("solarcraft_sword_autoheal"),
        SWORD_AOE_ATTACK_ABILITY("solarcraft_sword_aoe_attack"),
        SMELTING("solarcraft_smelting_module"),
        MAGIC_DAMAGE_BONUS_5("solarcraft_magic_damage_bonus_5"),
        MINER("solarcraft_miner_ability_tag"),
        DISARMING_THORNS("solarcraft_disarming_thorns"),
        POISONING_BLADE("solarcraft_poisoning_blade"),
        BLESSED("solarcraft_module_blessed");

        public String tag;

        Tags(String tag){
            this.tag = tag;
        }
    }
    public static void applyHoverText(ItemStack stack,List<Component> comp){
        for (Tags tag : Tags.values()){
            if (stack.getTagElement(tag.tag) != null){

                comp.add(new TranslatableComponent(tag.tag).withStyle(ChatFormatting.GOLD));
            }
        }
    }
}
