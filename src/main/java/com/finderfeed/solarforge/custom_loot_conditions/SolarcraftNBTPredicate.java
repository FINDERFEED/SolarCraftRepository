package com.finderfeed.solarforge.custom_loot_conditions;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.advancements.critereon.ItemPredicate;

import net.minecraft.world.item.ItemStack;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;
import java.util.Set;

public class SolarcraftNBTPredicate extends ItemPredicate{
    //public final NBTPredicate nbt;
    public final String nbt;
    public final String subNBT;

    public final int higherthan;
    public SolarcraftNBTPredicate(String prd,int higherthan,String subNBT){
        this.subNBT = subNBT;
        this.nbt = prd;

        this.higherthan = higherthan;
    }
    public boolean matches(ItemStack stack){
        if (    (stack.getTagElement(nbt) != null) &&
                (stack.getTagElement(nbt).getInt(subNBT) >= higherthan)
        ){
            return true;
        }
        return false;
    }
    public static SolarcraftNBTPredicate fromJson(JsonObject json) {

        String predicate = GsonHelper.getAsString(json,"nbt");
        String subNBT = GsonHelper.getAsString(json,"subnbt");
        int higherthan = GsonHelper.getAsInt(json,"higherthan");
        return new SolarcraftNBTPredicate(predicate,higherthan,subNBT);
    }
}
