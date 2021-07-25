package com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities;

import com.finderfeed.solarforge.misc_things.AbstractStructureBlockentity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class KeyDefenderTile extends AbstractStructureBlockentity {
    public boolean activated= false;
    public KeyDefenderTile() {
        super(TileEntitiesRegistry.KEY_DEFENDER_TILE.get(), Achievement.FIND_KEY_SOURCE,new AABB(-5,-1,-5,5,1,5));
    }


    public void triggerTrap(){
        if (!this.level.isClientSide && !activated){
            this.activated = true;
            for (int i = 0;i<20;i++){

                Zombie entity = new Zombie(EntityType.ZOMBIE,this.level);
                Vec3 pos;

                    pos = new Vec3(this.worldPosition.getX() + level.random.nextFloat()*8-4, this.worldPosition.getY() + 1, this.worldPosition.getZ() + level.random.nextFloat()*8-4);


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
    public CompoundTag save(CompoundTag p_189515_1_) {
        p_189515_1_.putBoolean("activated",activated);
        return super.save(p_189515_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundTag p_230337_2_) {
        this.activated = p_230337_2_.getBoolean("activated");
        super.load(p_230337_1_, p_230337_2_);
    }
}
