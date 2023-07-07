package com.finderfeed.solarcraft.content.loot_modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SmeltingLootModifier extends LootModifier {

    public static final Codec<SmeltingLootModifier> CODEC = RecordCodecBuilder.create(inst->codecStart(inst).apply(inst,SmeltingLootModifier::new));

    protected SmeltingLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> list = new ObjectArrayList<>();
        for (ItemStack stack : generatedLoot){
            list.add(getSmeltedStack(stack,context));
        }

        return list;
    }



    public ItemStack getSmeltedStack(ItemStack stack,LootContext ctx){

        Optional<SmeltingRecipe> opt = ctx.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING,new SimpleContainer(stack),ctx.getLevel());
        if (opt.isPresent()){
            return new ItemStack(opt.get().getResultItem(ctx.getLevel().registryAccess()).getItem(),stack.getCount());
        }
        return stack;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

//    public static class Serializer extends GlobalLootModifierSerializer<SmeltingLootModifier>{
//
//        @Override
//        public SmeltingLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
//            return new SmeltingLootModifier(ailootcondition);
//        }
//
//        @Override
//        public JsonObject write(SmeltingLootModifier instance) {
//
//            return new JsonObject();
//        }
//    }
}
