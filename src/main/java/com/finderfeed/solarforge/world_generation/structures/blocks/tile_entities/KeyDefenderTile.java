package com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities;

import com.finderfeed.solarforge.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class KeyDefenderTile extends AbstractStructureBlockentity {
    public boolean activated= false;
    public KeyDefenderTile() {
        super(TileEntitiesRegistry.KEY_DEFENDER_TILE.get(), Achievement.FIND_KEY_SOURCE,new AxisAlignedBB(-5,-1,-5,5,1,5));
    }


    public void triggerTrap(){
        if (!this.level.isClientSide && !activated){
            this.activated = true;
            for (int i = 0;i<20;i++){

                ZombieEntity entity = new ZombieEntity(EntityType.ZOMBIE,this.level);
                Vector3d pos;

                    pos = new Vector3d(this.worldPosition.getX() + level.random.nextFloat()*8-4, this.worldPosition.getY() + 1, this.worldPosition.getZ() + level.random.nextFloat()*8-4);


                entity.setPos(pos.x,pos.y,pos.z);
                this.level.addFreshEntity(entity);

            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide && this.level.hasSignal(worldPosition, Direction.UP)) {
            triggerTrap();
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.putBoolean("activated",activated);
        return super.save(p_189515_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        this.activated = p_230337_2_.getBoolean("activated");
        super.load(p_230337_1_, p_230337_2_);
    }
}
