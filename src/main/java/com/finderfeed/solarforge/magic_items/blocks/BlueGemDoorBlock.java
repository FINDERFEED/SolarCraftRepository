package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

public class BlueGemDoorBlock extends GlazedTerracottaBlock {

    public static final  Property<Boolean> UNLOCKED = BooleanProperty.create("unlocked");

    public BlueGemDoorBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(getStateDefinition().any().setValue(UNLOCKED,false));
    }



    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isClientSide && hand.equals(Hand.MAIN_HAND)){

            if (player.getMainHandItem().getItem() == ItemsRegister.BLUE_GEM_ENCHANCED.get()){
                if (!world.getBlockState(pos).getValue(UNLOCKED)) {
                    player.getMainHandItem().grow(-1);
                }
                world.setBlock(pos,state.setValue(UNLOCKED,true), Constants.BlockFlags.DEFAULT);
                world.playSound(null,player,Sounds.GEM_INSERT.get(), SoundCategory.AMBIENT,1,1);


                if ( world.getBlockState(pos.offset(0,0,-2)).hasProperty(UNLOCKED)&& world.getBlockState(pos.offset(0,0,-2)).getValue(UNLOCKED)){

                    for (int x = 0;x < 3;x++){
                        for (int y = 0;y < 3;y++){
                            world.destroyBlock(pos.offset(0,-1,-2).offset(0,y,x),false);
                        }
                    }
                }else if ( world.getBlockState(pos.offset(0,0,2)).hasProperty(UNLOCKED) && world.getBlockState(pos.offset(0,0,2)).getValue(UNLOCKED)){

                    for (int x = 0;x < 3;x++){
                        for (int y = 0;y < 3;y++){
                            world.destroyBlock(pos.offset(0,-1,0).offset(0,y,x),false);
                        }
                    }
                }
            }
        }

        return super.use(state, world, pos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(UNLOCKED);
        super.createBlockStateDefinition(p_206840_1_);
    }
}
