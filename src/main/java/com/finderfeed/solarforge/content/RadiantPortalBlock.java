package com.finderfeed.solarforge.content;

import com.finderfeed.solarforge.events.other_events.event_handler.EventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

public class RadiantPortalBlock extends Block implements EntityBlock {
    public RadiantPortalBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && entity.canChangeDimensions()){
            if (world.getServer() != null) {

                ServerLevel destination;
                if (entity.level.dimension() == Level.OVERWORLD){
                    destination = world.getServer().getLevel(EventHandler.RADIANT_LAND_KEY);
                }else{
                    destination = world.getServer().getLevel(Level.OVERWORLD);
                }
                if (destination != null){

                    entity.changeDimension(destination,RadiantTeleporter.INSTANCE);
                }else {
                    return;
                }
            }else{
                return;
            }

        }

        super.entityInside(state, world, pos, entity);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}
class RadiantTeleporter implements ITeleporter{

    public static RadiantTeleporter INSTANCE = new RadiantTeleporter();

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {

        return repositionEntity.apply(true);
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {

        BlockPos pos;
        if (destWorld.dimension() == Level.OVERWORLD){
            pos = entity.getOnPos();
        }else{
            pos = BlockPos.ZERO;
        }
        destWorld.getChunkAt(pos).setUnsaved(true);
        return this.isVanilla() ? defaultPortalInfo.apply(destWorld) :
                new PortalInfo(new Vec3(pos.getX(),destWorld.getHeight(Heightmap.Types.WORLD_SURFACE,pos.getX(),pos.getZ()),pos.getZ()),
                        Vec3.ZERO,
                        entity.getYRot(),
                        entity.getXRot());
    }
}
