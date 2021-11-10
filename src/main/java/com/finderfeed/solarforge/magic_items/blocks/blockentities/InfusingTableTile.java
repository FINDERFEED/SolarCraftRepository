package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class InfusingTableTile extends BlockEntity implements OwnedBlock {

    private UUID owner;

    public InfusingTableTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.INFUSING_CRAFTING_TABLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockPos pos,BlockState state,InfusingTableTile tile){
        if (!world.isClientSide){
            List<InfusingCraftingRecipe> list = world.getRecipeManager().getAllRecipesFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE);
            for (InfusingCraftingRecipe r : list){
                System.out.println(r.getTime());
                System.out.println(r.getOutput());
                System.out.println(r.getDefinitions());
                System.out.println(Arrays.toString(r.getPattern()));
            }
        }
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
