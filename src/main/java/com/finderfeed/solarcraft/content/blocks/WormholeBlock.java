package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.WormholeTileEntity;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.ITeleporter;
import javax.annotation.Nullable;
import java.util.function.Function;

public class WormholeBlock extends Block implements EntityBlock {
    public WormholeBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SCTileEntities.WORMHOLE.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (a,b,c,d)->{
            WormholeTileEntity.tick(a,c,b,(WormholeTileEntity)d);
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && (entity.canChangeDimensions()) && (world.dimension() != Level.OVERWORLD)  &&(world.getServer() != null)){
            ServerLevel destination = world.getServer().getLevel(Level.OVERWORLD);
            if (destination != null){
                entity.changeDimension(destination,WormholeTeleporter.INSTANCE);
            }
        }
        super.entityInside(state, world, pos, entity);
    }
}
class WormholeTeleporter implements ITeleporter {

    public static WormholeTeleporter INSTANCE = new WormholeTeleporter();

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {

        return repositionEntity.apply(true);
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {

        BlockPos pos = entity.getOnPos();

        destWorld.getChunkAt(pos).setUnsaved(true);
        return this.isVanilla() ? defaultPortalInfo.apply(destWorld) :
                new PortalInfo(new Vec3(pos.getX(),destWorld.getHeight(Heightmap.Types.WORLD_SURFACE,pos.getX(),pos.getZ()),pos.getZ()),
                        Vec3.ZERO,
                        entity.getYRot(),
                        entity.getXRot());
    }
}