package com.finderfeed.solarforge.content.loot_modifiers;

import com.google.gson.JsonObject;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SmeltingLootModifier extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected SmeltingLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

        List<ItemStack> list = new ArrayList<>();
        for (ItemStack stack : generatedLoot){
            list.add(getSmeltedStack(stack,context));
        }

        return list;
    }

    public ItemStack getSmeltedStack(ItemStack stack,LootContext ctx){

        Optional<SmeltingRecipe> opt = ctx.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING,new SimpleContainer(stack),ctx.getLevel());
        if (opt.isPresent()){
            return new ItemStack(opt.get().getResultItem().getItem(),stack.getCount());
        }
        return stack;
    }

    public static class Serializer extends GlobalLootModifierSerializer<SmeltingLootModifier>{

        @Override
        public SmeltingLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            return new SmeltingLootModifier(ailootcondition);
        }

        @Override
        public JsonObject write(SmeltingLootModifier instance) {

            return new JsonObject();
        }
    }
}
