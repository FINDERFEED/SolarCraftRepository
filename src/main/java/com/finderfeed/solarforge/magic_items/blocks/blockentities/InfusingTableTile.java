package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InfusingTableTile extends BlockEntity implements OwnedBlock {

    private UUID owner;

    public InfusingTableTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.INFUSING_CRAFTING_TABLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockPos pos,BlockState state,InfusingTableTile tile){
        if (!world.isClientSide){
            if (world.getGameTime() % 20 == 0){
                IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
                Optional<InfusingCraftingRecipe> opt = world.getRecipeManager().getRecipeFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,new PhantomInventory(handler),world);
                System.out.println(opt);
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
