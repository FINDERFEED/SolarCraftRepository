package com.finderfeed.solarcraft.config;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ItemREConfig extends JsonConfig {


    private static final JsonObject DEFAULT =
            JsonBuilder.begin()

                    .addString("_comment","The runic energy that contains in items (aka their costs in " +
                            "element weaver.)")

                    .addJsonObject("minecraft:iron_ingot")
                    .addFloat("fira",100)
                    .escape()

                    .addJsonObject("minecraft:gold_ingot")
                    .addFloat("ardo",150)
                    .escape()

                    .addJsonObject("minecraft:enchanted_golden_apple")
                    .addFloat("zeta",1500)
                    .addFloat("ardo",200)
                    .addFloat("urba",100)
                    .escape()

                    .addJsonObject("minecraft:gunpowder")
                    .addFloat("kelda",50)
                    .escape()

                    .addJsonObject("minecraft:redstone")
                    .addFloat("zeta",20)
                    .escape()

                    .addJsonObject("solarcraft:solar_shard")
                    .addFloat("urba",200)
                    .addFloat("kelda",100)
                    .addFloat("giro",50)
                    .escape()

                    .addJsonObject("minecraft:flint")
                    .addFloat("tera",30)
                    .escape()

                    .addJsonObject("minecraft:copper_ingot")
                    .addFloat("zeta",75)
                    .escape()

                    .addJsonObject("minecraft:blaze_rod")
                    .addFloat("ardo",150)
                    .escape()

                    .addJsonObject("minecraft:ender_pearl")
                    .addFloat("urba",300)
                    .escape()

                    .addJsonObject("minecraft:netherite_ingot")
                    .addFloat("zeta",1000)
                    .addFloat("kelda",2000)
                    .addFloat("ardo",500)
                    .addFloat("fira",500)
                    .escape()

                    .addJsonObject("solarcraft:energy_dust")
                    .addFloat("kelda",30)
                    .escape()

                    .addJsonObject("solarcraft:solar_dust")
                    .addFloat("ultima",30)
                    .escape()

                    .addJsonObject("solarcraft:void_dust")
                    .addFloat("giro",30)
                    .escape()

                    .addJsonObject("solarcraft:enderite_essence")
                    .addFloat("ardo",30)
                    .escape()

                    .addJsonObject("minecraft:diamond")
                    .addFloat("tera",300)
                    .addFloat("kelda",100)
                    .addFloat("giro",50)
                    .escape()

                    .addJsonObject("minecraft:emerald")
                    .addFloat("tera",100)
                    .addFloat("ultima",100)
                    .addFloat("fira",250)
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
            if (item == null){
                throw new RuntimeException("Item invalid: " + element.getKey());
            }
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
