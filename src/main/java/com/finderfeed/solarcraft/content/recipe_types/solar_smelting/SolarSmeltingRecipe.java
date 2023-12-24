package com.finderfeed.solarcraft.content.recipe_types.solar_smelting;

import com.finderfeed.solarcraft.misc_things.PhantomInventory;

import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

public class SolarSmeltingRecipe implements Recipe<PhantomInventory> {

    @Deprecated
    public NonNullList<Ingredient> list;

    public final List<ItemStack> stacks;

    public final ItemStack output;

    public final int smeltingTime;
//    public static final SolarSmeltingRecipeSerializer serializer = new SolarSmeltingRecipeSerializer();
    public SolarSmeltingRecipe(List<ItemStack> stacks, ItemStack output, int infusingTime) {
        this.stacks = stacks;
        this.output = output;
        this.smeltingTime = infusingTime;

    }

    public int getInfusingTime(){
        return smeltingTime;
    }

    @Override
    public boolean matches(PhantomInventory inv, Level world) {

        List<ItemStack> stacks = getStacks();
        boolean returnable = true;
        for (ItemStack stack : stacks){
            if (!listContainsItemStack(inv.INVENTORY,stack)){
                returnable = false;
                break;
            }
        }

        return returnable;
    }

    private boolean listContainsItemStack(List<ItemStack> inventory,ItemStack stack){
        for (ItemStack item : inventory){
            if (item.getItem() == stack.getItem()){
                if (item.getCount() >= stack.getCount()){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsOrNot(List<Item> lists,int index){
        if (!list.get(index).isEmpty()){
            return lists.contains(list.get(index).getItems()[0].getItem());
        }else{
            return lists.contains(Items.AIR);
        }
    }

    public List<ItemStack> getStacks() {
        return stacks;
    }

    @Override
    public ItemStack assemble(PhantomInventory inv, RegistryAccess access) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.output;
    }



    @Override
    public RecipeSerializer<?> getSerializer() {
        return SolarcraftRecipeTypes.SMELTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SolarcraftRecipeTypes.SMELTING.get();
    }
}