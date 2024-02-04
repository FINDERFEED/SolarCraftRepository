package com.finderfeed.solarcraft.content.loot_modifiers.custom_loot_conditions;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;

import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.common.advancements.critereon.ICustomItemPredicate;

public class SolarcraftNBTPredicate implements ICustomItemPredicate {
    //public final NBTPredicate nbt;
    public final String nbt;
    public final String subNBT;

    public final int higherthan;
    public SolarcraftNBTPredicate(String prd,int higherthan,String subNBT){
        this.subNBT = subNBT;
        this.nbt = prd;

        this.higherthan = higherthan;
    }

    @Override
    public boolean test(ItemStack stack){
        if ((stack.getTagElement(nbt) != null) &&
                (stack.getTagElement(nbt).getInt(subNBT) >= higherthan)){
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

    public static final Codec<SolarcraftNBTPredicate> CODEC = RecordCodecBuilder.create(p->p.group(
            Codec.STRING.fieldOf("nbt").forGetter(pr->pr.nbt),
            Codec.INT.fieldOf("higherthan").forGetter(pr->pr.higherthan),
            Codec.STRING.fieldOf("subnbt").forGetter(pr->pr.subNBT)
    ).apply(p,SolarcraftNBTPredicate::new));
    @Override
    public Codec<? extends ICustomItemPredicate> codec() {
        return CODEC;
    }
}
