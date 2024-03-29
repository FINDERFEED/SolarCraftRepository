package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarcraft.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ModuleItem extends SolarcraftItem implements ICustomTooltip {
    public static final CustomTooltip MODULE = new CustomTooltip("module",
            Math.round(40*1.5f),Math.round(9*1.5f),
            Math.round(40*1.5f),Math.round(9*1.5f),
            6,
            0xFF561100, 0xFF330300,0xf0100010).setyOffsetTop(1);

    private Type type;
    private String subTag;
    private ModuleItem.Tags[] incompatibleWith;

    public ModuleItem(Properties p_41383_,Type type,Tags subTag, Supplier<AncientFragment> fragmentSupplier) {
        super(p_41383_,fragmentSupplier);
        this.type = type;
        this.subTag = subTag.tag;
    }
    public ModuleItem(Properties p_41383_,Type type,Tags subTag, Supplier<AncientFragment> fragmentSupplier,ModuleItem.Tags... incompatibleWith) {
        this(p_41383_,type,subTag,fragmentSupplier);
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

    @Override
    public CustomTooltip getTooltip() {
        return MODULE;
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
            sacredTexts.add(Component.translatable("solarcraft.module_armor").withStyle(ChatFormatting.GOLD));
        }else if(type == Type.SWORDS){
            sacredTexts.add(Component.translatable("solarcraft.module_swords").withStyle(ChatFormatting.GOLD));
        }else if (type == Type.PICKAXES){
            sacredTexts.add(Component.translatable("solarcraft.module_pickaxes").withStyle(ChatFormatting.GOLD));
        }

        sacredTexts.add(Component.translatable(subTag).withStyle(ChatFormatting.GOLD));
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
        FURY_SWIPES("solarcraft_fury_swipes"),
        BLESSED("solarcraft_module_blessed");

        public String tag;

        Tags(String tag){
            this.tag = tag;
        }
    }
    public static void applyHoverText(ItemStack stack,List<Component> comp){
        for (Tags tag : Tags.values()){
            if (stack.getTagElement(tag.tag) != null){

                comp.add(Component.translatable(tag.tag).withStyle(ChatFormatting.GOLD));
            }
        }
    }
}
