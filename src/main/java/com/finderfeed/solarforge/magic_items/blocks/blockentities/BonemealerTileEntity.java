package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarforge.misc_things.DebugTarget;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BonemealerTileEntity extends AbstractRunicEnergyContainer implements DebugTarget {

    private final Map<RunicEnergy.Type,Double> REQUEST =
            new Helpers.RunicEnergyRequestConstructor().add(RunicEnergy.Type.TERA,getRunicEnergyLimit()).build();

    private final Map<RunicEnergy.Type,Double> COST =
            new Helpers.RunicEnergyRequestConstructor().add(RunicEnergy.Type.TERA,200).build();


    public BonemealerTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.BONEMEALER.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world,BlockPos pos,BlockState state,BonemealerTileEntity tile){
        if (!world.isClientSide) {
            tile.requestRunicEnergy(tile.REQUEST, 1);
            if ((world.getGameTime() % 200 == 0)) {
                if (tile.hasEnoughRunicEnergy(tile.COST, 1)) {
                    List<BlockPos> positons = tile.getGrowablePositons();
                    tile.performBonemealing(positons);
                    if (!positons.isEmpty()) {
                        tile.COST.forEach((type, cost) -> {
                            tile.giveEnergy(type, -cost);
                        });
                    }
                }
            }
        }
    }

    private void performBonemealing(List<BlockPos> positions){
        positions.forEach((growablePos) -> {
            BlockState state1 = level.getBlockState(growablePos);
            if (state1.getBlock() instanceof CropBlock block) {
                block.performBonemeal((ServerLevel) level, level.random, growablePos, state1);
                level.sendBlockUpdated(growablePos, state1, state1, 3);
            }
        });
    }

    private List<BlockPos> getGrowablePositons(){
        List<BlockPos> returnThis = new ArrayList<>();

        for (int i = -10; i <= 10;i++){
            for (int g = -10; g <= 10;g++){
                for (int h = -10; h <= 10;h++){
                    BlockPos whereToSeek = worldPosition.offset(i,g,h);
                    if (level.getBlockState(whereToSeek).getBlock() instanceof CropBlock crop){
                        if (crop.isValidBonemealTarget(level,whereToSeek,level.getBlockState(whereToSeek),true)) {
                            returnThis.add(whereToSeek);
                        }
                    }
                }
            }
        }

        return returnThis;
    }


    @Override
    public double getMaxEnergyInput() {
        return 20;
    }

    @Override
    public double getRunicEnergyLimit() {
        return 10000;
    }

    @Override
    public int getSeekingCooldown() {
        return 20;
    }

    @Override
    public boolean shouldFunction() {
        return true;
    }

    @Override
    public double getMaxRange() {
        return 16;
    }

    @Override
    public List<String> getDebugStrings() {
        return List.of("TERA: "+ getRunicEnergy(RunicEnergy.Type.TERA));
    }
}
