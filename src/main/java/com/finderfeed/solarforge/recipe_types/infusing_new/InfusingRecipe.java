package com.finderfeed.solarforge.recipe_types.infusing_new;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class InfusingRecipe implements Recipe<Container> {



    private final InfuserTileEntity.Tier tier;
    public final ResourceLocation id;
    public final Map<Character,Ingredient> INGR_MAP;
    public final String oneRowPattern;
    public final String[] fiveRowPattern;
    public final ItemStack output;
    public final String child;
    public final int infusingTime;
    public final int requriedEnergy;
    public final String tag;
    public final int count;
    public final Map<RunicEnergy.Type,Double> RUNIC_ENERGY_COST;
    private final String catalysts;
    private final Block[] deserializedCatalysts;
    public static final InfusingRecipeSerializer serializer = new InfusingRecipeSerializer();
    public InfusingRecipe(ResourceLocation id,Map<Character,Ingredient> ingredientMap,String[] fiveRowPattern,String catalysts, ItemStack output, int infusingTime,String fragmentID
            ,int requriedEnergy,String tag,int count,Map<RunicEnergy.Type,Double> costs) {
        this.INGR_MAP = ingredientMap;
        this.fiveRowPattern = fiveRowPattern;
        this.oneRowPattern = fiveRowPattern[0] + fiveRowPattern[1] + fiveRowPattern[2] + fiveRowPattern[3] + fiveRowPattern[4];
        this.id = id;
        this.catalysts = catalysts;
        this.deserializedCatalysts = deserializeCatalysts();
        this.output = output;
        this.infusingTime = infusingTime;
        this.child = fragmentID;
        this.RUNIC_ENERGY_COST = costs;
        this.requriedEnergy = requriedEnergy;
        this.tag = tag;
        this.count = count;
        if (requriedEnergy > 0){
            this.tier = InfuserTileEntity.Tier.SOLAR_ENERGY;
        }else if (doRecipeRequiresRunicEnergy(costs)){
            this.tier = InfuserTileEntity.Tier.RUNIC_ENERGY;
        }else{
            this.tier = InfuserTileEntity.Tier.FIRST;
        }
    }

    public Block[] getDeserializedCatalysts() {
        return deserializedCatalysts;
    }

    public int getInfusingTime(){
        return infusingTime;
    }

    @Override
    public boolean matches(Container inv, Level world) {
        for (int i = 0; i < 13;i++){
            Ingredient ingr = INGR_MAP.get(oneRowPattern.charAt(i));
            if (!ingr.test(inv.getItem(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(Container inv) {
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
    public RecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return SolarForge.INFUSING_RECIPE_TYPE;
    }

    public InfuserTileEntity.Tier getTier() {
        return tier;
    }

    private boolean doRecipeRequiresRunicEnergy(Map<RunicEnergy.Type,Double> costs){
        for (double cost : costs.values()){
            if (Math.round(cost) != 0){
                return true;
            }
        }
        return false;
    }

    public String getCatalysts() {
        return catalysts;
    }

    private Map<Character,Block> DESERIALIZATOR = Map.of(
            'T', BlocksRegistry.TERA_RUNE_BLOCK.get(),
            'Z', BlocksRegistry.ZETA_RUNE_BLOCK.get(),
            'K', BlocksRegistry.KELDA_RUNE_BLOCK.get(),
            'R', BlocksRegistry.URBA_RUNE_BLOCK.get(),
            'F', BlocksRegistry.FIRA_RUNE_BLOCK.get(),
            'A', BlocksRegistry.ARDO_RUNE_BLOCK.get(),
            'U', BlocksRegistry.ULTIMA_RUNE_BLOCK.get(),
            'G', BlocksRegistry.GIRO_RUNE_BLOCK.get()
    );


    public Block[] deserializeCatalysts(){
        if (!catalysts.equals("            ")) {
            Block[] bl = new Block[12];
            for (int i = 0; i < 12; i++) {
                char c = catalysts.charAt(i);
                Block block = DESERIALIZATOR.get(c);
                if (block != null) {
                    bl[i] = block;
                } else {
                    if (c != ' '){
                        throw new RuntimeException("Incorrect symbol: "+ c + " in catalysts. Recipe: " + this.id);
                    }
                    bl[i] = null;
                }
            }
            return bl;
        }else{
            return null;
        }
    }
}
