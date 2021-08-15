package com.finderfeed.solarforge.custom_loot_conditions;

import com.finderfeed.solarforge.events.other_events.event_handler.ModuleEventsHandler;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

public class SolarcraftModulePredicate extends ItemPredicate {

    public final String nbt;

    public SolarcraftModulePredicate(String prd){

        this.nbt = prd;

    }
    public boolean matches(ItemStack stack){
        return stack.getTagElement(nbt) != null;
    }
    public static SolarcraftModulePredicate fromJson(JsonObject json) {

        String predicate = GsonHelper.getAsString(json,"module_tag");
        return new SolarcraftModulePredicate(predicate);
    }
}
