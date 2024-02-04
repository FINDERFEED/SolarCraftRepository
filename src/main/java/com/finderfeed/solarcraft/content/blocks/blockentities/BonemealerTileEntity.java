package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.DebugTarget;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class BonemealerTileEntity extends AbstractRunicEnergyContainer implements DebugTarget {

//    private final Map<RunicEnergy.Type,Double> REQUEST =
//            new Helpers.RunicEnergyRequestConstructor().add(RunicEnergy.Type.TERA,getRunicEnergyLimit()).build();

    private final RunicEnergyCost REQUEST = new RunicEnergyCost().set(RunicEnergy.Type.TERA, (float) getRunicEnergyLimit());

//    private final Map<RunicEnergy.Type,Double> COST =
//            new Helpers.RunicEnergyRequestConstructor().add(RunicEnergy.Type.TERA,200).build();

    private final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.TERA,200);

    public BonemealerTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.BONEMEALER.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world,BlockPos pos,BlockState state,BonemealerTileEntity tile){
        if (!world.isClientSide) {
            tile.requestRunicEnergy(tile.REQUEST, 1);
            if ((world.getGameTime() % 200 == 0)) {
                if (tile.hasEnoughRunicEnergy(tile.COST, 1)) {
                    List<BlockPos> positons = tile.getGrowablePositons();
                    tile.performBonemealing(positons);
                    if (!positons.isEmpty()) {
//                        tile.COST.forEach((type, baseCost) -> {
//                            tile.giveEnergy(type, -baseCost);
//                        });
                        tile.COST.getSetTypes().forEach((type)->{
                            tile.giveEnergy(type,tile.COST.get(type));
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
                        if (crop.isValidBonemealTarget(level,whereToSeek,level.getBlockState(whereToSeek))) {
                            returnThis.add(whereToSeek);
                        }
                    }
                }
            }
        }

        return returnThis;
    }


    @Override
    public float getREPerTickInput() {
        return 20;
    }

    @Override
    public float getRunicEnergyLimit() {
        return 2000;
    }

    @Override
    public int getSeekCooldown() {
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
