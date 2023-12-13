package com.finderfeed.solarcraft.content.loot_modifiers;

import com.finderfeed.solarcraft.registries.items.SCItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class AddRandomAncientFragmentToChests extends LootModifier {

    public static final Codec<AddRandomAncientFragmentToChests> CODEC = RecordCodecBuilder.create(inst->codecStart(inst)
            .and(inst.group(
                    Codec.INT.fieldOf("min").forGetter((obj)->{
                        return obj.min;
                    }),
                    Codec.INT.fieldOf("max").forGetter((obj)->{
                        return obj.max;
                    })
            )).apply(inst,AddRandomAncientFragmentToChests::new));

    private int min;
    private int max;

    protected AddRandomAncientFragmentToChests(LootItemCondition[] conditionsIn,int min,int max) {
        super(conditionsIn);
        this.min = min;
        this.max = max;
    }


    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource r = context.getRandom();

        int amount = min + r.nextInt((max - min) + 1);
        ObjectArrayList<ItemStack> stacks = new ObjectArrayList<>(generatedLoot);
        for (int i = 0; i < amount;i++){
            stacks.add(SCItems.INFO_FRAGMENT.get().getDefaultInstance());
        }
        return stacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

//    public static class Serializer extends GlobalLootModifierSerializer<AddRandomAncientFragmentToChests>{
//
//
//
//        @Override
//        public AddRandomAncientFragmentToChests read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
//
//            JsonArray array = object.getAsJsonArray("count");
//            if (array.size() != 2){
//                throw new RuntimeException("Error parsing Ancient fragment global loot modifier. Array should contain 2 values min and max");
//            }
//            int[] arr = new int[]{array.get(0).getAsInt(),array.get(1).getAsInt()};
//            return new AddRandomAncientFragmentToChests(ailootcondition,arr);
//        }
//
//        @Override
//        public JsonObject write(AddRandomAncientFragmentToChests instance) {
//
//            JsonObject object = new JsonObject();
//            JsonArray array = new JsonArray();
//            array.add(instance.minMax[0]);
//            array.add(instance.minMax[1]);
//            object.add("count",array);
//
//            return object;
//        }
//    }
}
