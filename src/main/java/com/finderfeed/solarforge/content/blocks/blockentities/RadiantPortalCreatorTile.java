package com.finderfeed.solarforge.content.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.events.other_events.event_handler.EventHandler;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

public class RadiantPortalCreatorTile extends BlockEntity {

    private AABB TP_AABB;


    public RadiantPortalCreatorTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.PORTAL_CREATOR.get(), p_155229_, p_155230_);
    }


    public boolean isActive(){
        return Helpers.checkStructure(level,worldPosition.offset(-3,-1,-3), Multiblocks.RADIANT_LAND_PORTAL.getM(),false);
    }



    public static void tick(RadiantPortalCreatorTile tile, BlockState state, BlockPos pos, Level world){

        if (!world.isClientSide && tile.isActive()){
            if (tile.TP_AABB == null){
                tile.TP_AABB = new AABB(0.25f,0,0.25f,0.75f,2.5,0.75f).move(tile.worldPosition);
            }
            world.getEntitiesOfClass(Entity.class,tile.TP_AABB, Entity::canChangeDimensions).forEach((entity)->{
                if (world.getServer() != null) {
                    ServerLevel destination;
                    if (entity.level.dimension() == Level.OVERWORLD){
                        destination = world.getServer().getLevel(EventHandler.RADIANT_LAND_KEY);
                    }else{
                        destination = world.getServer().getLevel(Level.OVERWORLD);
                    }
                    if (destination != null){

                        entity.changeDimension(destination, RadiantTeleporter.INSTANCE);
                        if (destination.dimension() == EventHandler.RADIANT_LAND_KEY){
                            createWormhole(destination);
                            entity.sendMessage(new TextComponent("Use wormhole on 1,Y(~120),1 coordinates to return back"),entity.getUUID());
                        }
                    }
                }
            });

        }

    }

    private static void createWormhole(ServerLevel destination){

        int yHeight = destination.getHeight(Heightmap.Types.WORLD_SURFACE,1,1);
        boolean placed = false;
        for (int i = yHeight-10; i <= 255;i++){
            if (destination.getBlockState(BlockPos.ZERO.offset(1,0,1).above(i)).getBlock() == SolarcraftBlocks.WORMHOLE.get()){
                placed = true;
                break;
            }
        }
        if (!placed){
            destination.setBlockAndUpdate(BlockPos.ZERO.offset(1,0,1).above(yHeight + 50), SolarcraftBlocks.WORMHOLE.get().defaultBlockState());
        }
    }


}
class RadiantTeleporter implements ITeleporter {

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