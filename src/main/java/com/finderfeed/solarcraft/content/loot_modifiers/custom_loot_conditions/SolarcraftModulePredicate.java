package com.finderfeed.solarcraft.content.loot_modifiers.custom_loot_conditions;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.advancements.critereon.ICustomItemPredicate;

public class SolarcraftModulePredicate implements ICustomItemPredicate {

    public final String nbt;

    public SolarcraftModulePredicate(String prd){
        this.nbt = prd;
    }
    public static SolarcraftModulePredicate fromJson(JsonObject json) {

        String predicate = GsonHelper.getAsString(json,"module_tag");
        return new SolarcraftModulePredicate(predicate);
    }

    public static final Codec<SolarcraftModulePredicate> CODEC = ExtraCodecs.FLAT_JSON.flatXmap(json->{
        SolarcraftModulePredicate predicate = fromJson(json.getAsJsonObject());
        return DataResult.success(predicate);
    },ref->{
       throw new RuntimeException("Serialization for module item predicate is not implemented");
    });
    @Override
    public Codec<? extends ICustomItemPredicate> codec() {
        return CODEC;
    }

    @Override
    public boolean test(ItemStack stack) {
        return stack.getTagElement(nbt) != null;
    }
}
