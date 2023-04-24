package com.finderfeed.solarcraft.config;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ItemREConfig extends JsonConfig{


    private static final JsonObject DEFAULT =
            JsonBuilder.begin()

                    .addString("_comment","The runic energy that contains in items (aka their costs in " +
                            "element weaver.)")

                    .addJsonObject("minecraft:dirt")
                    .addFloat("tera",1)
                    .escape()

                    .addJsonObject("minecraft:iron_ingot")
                    .addFloat("ardo",20)
                    .addFloat("fira",10)
                    .escape()

                    .addJsonObject("minecraft:gold_ingot")
                    .addFloat("ardo",20)
                    .addFloat("fira",10)
                    .addFloat("kelda",10)
                    .escape()
                    .end();

    private Map<Item, RunicEnergyCost> costMap;

    public ItemREConfig() {
        super("item_runic_energies");
    }



    public RunicEnergyCost getItemCost(Item item){
        return this.costMap.get(item);
    }

    @Override
    public JsonObject defaultJson() {
        return DEFAULT;
    }


    @Override
    public void deserialize(JsonObject json) {
        Map<Item,RunicEnergyCost> costMap = new HashMap<>();
        for (var element : json.entrySet()){
            if (element.getKey().contains("_comment")){
                continue;
            }
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getKey()));
            JsonObject re = element.getValue().getAsJsonObject();
            RunicEnergyCost cost = new RunicEnergyCost();
            for (var reElement : re.entrySet()){
                RunicEnergy.Type type = RunicEnergy.Type.byId(reElement.getKey());
                cost.set(type,reElement.getValue().getAsFloat());
            }
            cost.immutable();
            costMap.put(item,cost);
        }
        this.costMap = costMap;
    }

}
