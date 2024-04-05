package com.finderfeed.solarcraft.content.world_generation.structures.blocks;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities.ColdStarInfuserTile;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;



public class ColdStarInfuser extends Block implements EntityBlock {

    public static final BooleanProperty NOT_STARRED = BooleanProperty.create("not_starred");

    public ColdStarInfuser(Properties p_i48440_1_) {
        super(p_i48440_1_);

    }




    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockState starredState = this.defaultBlockState().setValue(NOT_STARRED,false);

        if (player.getItemInHand(hand).getItem() == SCItems.COLD_STAR_PIECE.get()){
            for (int i = 0; i < 96;i++){
                world.addParticle(ParticleTypes.FLAME,pos.getX()+0.5f + Math.cos(Math.toRadians(i*30)),pos.getY() + i * 0.5,pos.getZ()+0.5f + Math.sin(Math.toRadians(i*30)),0,0,0);
            }
        }
        if (hand == InteractionHand.MAIN_HAND) {
            if (!world.isClientSide && Helpers.hasPlayerCompletedProgression(Progression.ACQUIRE_COLD_STAR, player)) {
                if (state != starredState) {
                    if (player.getItemInHand(hand).getItem() == Items.NETHER_STAR){
                        player.getItemInHand(hand).grow(-1);
                        world.setBlock(pos,starredState,3);
                    }
                } else {
                    if (player.getItemInHand(hand).getItem() == SCItems.COLD_STAR_PIECE.get()){
                        player.getItemInHand(hand).grow(-1);
                        ItemStack stack = SCItems.COLD_STAR_PIECE_ACTIVATED.get().getDefaultInstance();
                        if (!player.addItem(stack)){
                            ItemEntity item = new ItemEntity(world,pos.getX(),pos.getY(),pos.getZ(),stack);
                            world.addFreshEntity(item);
                        }
                        world.setBlock(pos, SCBlocks.COLD_STAR_INFUSER.get().defaultBlockState(),3);
                    }
                }
            }
        }


        return super.use(state, world, pos, player, hand, result);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SCTileEntities.COLD_STAR_INFUSER.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            ColdStarInfuserTile.tick(level,blockPos,blockState,(ColdStarInfuserTile)t);
        });
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(NOT_STARRED);

        super.createBlockStateDefinition(p_49915_);
    }


}
