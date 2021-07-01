package com.finderfeed.solarforge.loot_modifiers;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

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
    protected SmeltingLootModifier(ILootCondition[] conditionsIn) {
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

        Optional<FurnaceRecipe> opt = ctx.getLevel().getRecipeManager().getRecipeFor(IRecipeType.SMELTING,new Inventory(stack),ctx.getLevel());
        if (opt.isPresent()){
            return new ItemStack(opt.get().getResultItem().getItem(),stack.getCount());
        }
        return stack;
    }

    public static class Serializer extends GlobalLootModifierSerializer<SmeltingLootModifier>{

        @Override
        public SmeltingLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            return new SmeltingLootModifier(ailootcondition);
        }

        @Override
        public JsonObject write(SmeltingLootModifier instance) {

            return new JsonObject();
        }
    }
}
