package com.finderfeed.solarforge.recipe_types;


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfusingRecipe implements IRecipe<IInventory> {

    public final ResourceLocation id;
    public final Ingredient input1;
    public final Ingredient input2;
    public final Ingredient input3;
    public final Ingredient input4;
    public final Ingredient input5;
    public final Ingredient input6;
    public final Ingredient input7;
    public final Ingredient input8;
    public final Ingredient input9;
    public final ItemStack output;
    public final AncientFragment child;
    public final int infusingTime;
    public final int requriedEnergy;
    public final String tag;
    public final int count;

    public static final InfusingRecipeSerializer serializer = new InfusingRecipeSerializer();
    public InfusingRecipe(ResourceLocation id, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient input4, Ingredient input5, Ingredient input6, Ingredient input7,Ingredient input8,Ingredient input9, ItemStack output, int infusingTime,AncientFragment child
    ,int requriedEnergy,String tag,int count) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.input5 = input5;
        this.input6 = input6;
        this.input7 = input7;
        this.input8 = input8;
        this.input9 = input9;
        this.output = output;
        this.infusingTime = infusingTime;
        this.child = child;

        this.requriedEnergy = requriedEnergy;
        this.tag = tag;
        this.count = count;
    }

    public int getInfusingTime(){
        return infusingTime;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.input1.test(inv.getItem(0))
                && this.input2.test(inv.getItem(1))
                && this.input3.test(inv.getItem(2))
                && this.input4.test(inv.getItem(3))
                && this.input5.test(inv.getItem(4))
                && this.input6.test(inv.getItem(5))
                && this.input7.test(inv.getItem(6))
                && this.input8.test(inv.getItem(7))
                && this.input9.test(inv.getItem(8));
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public IRecipeType<?> getType() {
        return SolarForge.INFUSING_RECIPE_TYPE;
    }
}
