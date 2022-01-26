package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;


import java.util.List;

public abstract class AbstractStructureBlockentity extends BlockEntity  {

    public final Progression a;
    public final AABB box;

    public AbstractStructureBlockentity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_, Progression a, AABB box) {
        super(p_155228_, p_155229_, p_155230_);
        this.a = a;
        this.box = box;
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, AbstractStructureBlockentity tile) {
        if (!tile.level.isClientSide){
            List<Player> list = tile.level.getEntitiesOfClass(Player.class,tile.box.move(tile.worldPosition));
            for (Player a :list){
                Helpers.fireProgressionEvent(a,tile.a);
            }
        }
    }
}
