package com.finderfeed.solarcraft.content.recipe_types.infusing_new;

import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.recipe_types.SCRecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class InfusingRecipe implements Recipe<Container> {



    private final InfuserTileEntity.Tier tier;
//    public final ResourceLocation id;
    public final Map<Character,Ingredient> INGR_MAP;
    public final String oneRowPattern;
    public final String[] fiveRowPattern;
    public final ItemStack output;
    public final String fragID;
    public final int infusingTime;
    public final int requriedSolarEnergy;
    public final String tag;
    public final int count;
    public final RunicEnergyCost RUNIC_ENERGY_COST;
    private final String catalysts;
    private final Block[] deserializedCatalysts;
//    public static final InfusingRecipeSerializer serializer = new InfusingRecipeSerializer();
    public InfusingRecipe(Map<Character,Ingredient> ingredientMap,String[] fiveRowPattern,String catalysts, ItemStack output, int infusingTime,String fragmentID
            ,int requriedEnergy,String tag,RunicEnergyCost costs) {
        this.INGR_MAP = new HashMap<>(ingredientMap);
        this.INGR_MAP.put(' ',Ingredient.of(Items.AIR));
        this.fiveRowPattern = fiveRowPattern;
        this.oneRowPattern = fiveRowPattern[0] + fiveRowPattern[1] + fiveRowPattern[2] + fiveRowPattern[3] + fiveRowPattern[4];
//        this.id = id;
        this.catalysts = catalysts;
        this.deserializedCatalysts = deserializeCatalysts();
        this.output = output;
        this.infusingTime = infusingTime;
        this.fragID = fragmentID;
        this.RUNIC_ENERGY_COST = costs;
        this.requriedSolarEnergy = requriedEnergy;
        this.count = output.getCount();
        this.tag = tag;
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
    public ItemStack assemble(Container inv,RegistryAccess access) {
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

//    @Override
//    public ResourceLocation getId() {
//        return this.id;
//    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SCRecipeTypes.INFUSING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SCRecipeTypes.INFUSING.get();
    }

    public InfuserTileEntity.Tier getTier() {
        return tier;
    }

    private boolean doRecipeRequiresRunicEnergy(RunicEnergyCost costs){
        for (float cost : costs.getCosts()){
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
            'T', SCBlocks.TERA_RUNE_BLOCK.get(),
            'Z', SCBlocks.ZETA_RUNE_BLOCK.get(),
            'K', SCBlocks.KELDA_RUNE_BLOCK.get(),
            'R', SCBlocks.URBA_RUNE_BLOCK.get(),
            'F', SCBlocks.FIRA_RUNE_BLOCK.get(),
            'A', SCBlocks.ARDO_RUNE_BLOCK.get(),
            'U', SCBlocks.ULTIMA_RUNE_BLOCK.get(),
            'G', SCBlocks.GIRO_RUNE_BLOCK.get()
    );

    public static final Character[] DESERIALIZATOR_RE_TO_CHARACTER = {'Z','A','R','K','F','T','G','U'};

    public Map<Character, Block> getCatalystDeserializer() {
        return DESERIALIZATOR;
    }

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
                        throw new RuntimeException("Incorrect symbol: "+ c + " in catalysts.");
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
