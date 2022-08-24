package com.finderfeed.solarforge.content.loot_modifiers;

import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddRandomAncientFragmentToChests extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */

    private final int[] minMax;

    protected AddRandomAncientFragmentToChests(LootItemCondition[] conditionsIn,int[] minMax) {
        super(conditionsIn);
        this.minMax = minMax;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Random r = context.getRandom();
        int min = minMax[0];
        int amount = min + r.nextInt((minMax[1] - min) + 1);
        ArrayList<ItemStack> stacks = new ArrayList<>(generatedLoot);
        for (int i = 0; i < amount;i++){
            stacks.add(SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance());
        }
        return stacks;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddRandomAncientFragmentToChests>{



        @Override
        public AddRandomAncientFragmentToChests read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {

            JsonArray array = object.getAsJsonArray("count");
            if (array.size() != 2){
                throw new RuntimeException("Error parsing Ancient fragment global loot modifier. Array should contain 2 values min and max");
            }
            int[] arr = new int[]{array.get(0).getAsInt(),array.get(1).getAsInt()};
            return new AddRandomAncientFragmentToChests(ailootcondition,arr);
        }

        @Override
        public JsonObject write(AddRandomAncientFragmentToChests instance) {

            JsonObject object = new JsonObject();
            JsonArray array = new JsonArray();
            array.add(instance.minMax[0]);
            array.add(instance.minMax[1]);
            object.add("count",array);

            return object;
        }
    }
}
