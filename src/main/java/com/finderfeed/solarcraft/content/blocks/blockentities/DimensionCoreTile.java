package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.events.other_events.event_handler.SCEventHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DimensionCoreTile extends BlockEntity implements IStructureOwner {

    private boolean structureCorrect = false;


    public DimensionCoreTile(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.DIMENSION_CORE_TILE.get(), p_155229_, p_155230_);
    }



    public static void tick(DimensionCoreTile tile, Level world,BlockPos pos,BlockState state){
        if (world.getGameTime() % 40 == 0){
            tile.forceStructureRecheck();
        }
        if (tile.structureCorrect) {
            if (!world.isClientSide) {
                handleTP(world,pos);
            } else {
                handleParticles(world,pos);
            }
        }
    }

    private static void handleParticles(Level world,BlockPos pos){
        Vec3 portalCenter = Helpers.posToVec(pos).add(0.5,3.5,0.5);
        double rotAngle = 5*(world.getGameTime() % 360);
        int r;
        int gr;
        int b;
        int mod;
        if (Helpers.isDay(world)) {
            r = 255;
            gr = 255;
            b = 0;
            mod = -1;
        } else {
            mod = 1;
            r = (int)(0.5f*255);
            gr = 0;
            b = 255;
        }
        int c = 24;
        double ang = 360/(double)c;
        for (int i = 1; i < c;i++){
            if (i == c/2) continue;
            double angle = i * ang;
            double x = Math.sin(Math.toRadians(angle)) * 2;
            double y = Math.cos(Math.toRadians(angle)) * 2;
            double[] xz = FDMathHelper.rotatePointDegrees(x,0,rotAngle);
            x = xz[0];
            double z = xz[1];
            ClientHelpers.Particles.createParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    portalCenter.x + x,portalCenter.y + y,portalCenter.z + z,0,0,0,r,gr,b,0.25f);
        }
        for (int i = 0; i < 8;i++){
            double angle = i * 360/8f;
            for (float p = 0; p <= 1;p+=0.1){
                double rad = p*7.9;
                if (i % 2 != 0) rad += 0.3f;
                double x = Math.sin(Math.toRadians(angle)) * rad;
                double z = Math.cos(Math.toRadians(angle)) * rad;
                double rx = world.random.nextDouble()*0.5 - 0.25;
                double ry = world.random.nextDouble()*0.5 - 0.25;
                double rz = world.random.nextDouble()*0.5 - 0.25;
                double mx = mod * x / rad * 0.05;
                double mz = mod * z / rad * 0.05;
                ClientHelpers.Particles.createParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        portalCenter.x + x + rx,portalCenter.y + ry,portalCenter.z + z + rz,mx,0,mz,r,gr,b,0.25f);
            }
        }
    }

    private static void handleTP(Level world,BlockPos pos){
        if (world.getGameTime() % 10 == 0) {
            AABB box = Helpers.createAABBWithRadius(Helpers.posToVec(pos).add(0.5,3.5,0.5),
                    1.5,1.5);
            world.getEntitiesOfClass(Entity.class, box, Entity::canChangeDimensions).forEach((entity) -> {
                if (world.getServer() != null) {
                    ServerLevel destination;
                    if (entity.level.dimension() == Level.OVERWORLD) {
                        destination = world.getServer().getLevel(SCEventHandler.RADIANT_LAND_KEY);
                    } else {
                        destination = world.getServer().getLevel(Level.OVERWORLD);
                    }
                    if (destination != null) {

                        entity.changeDimension(destination, RadiantTeleporter.INSTANCE);
                        if (destination.dimension() == SCEventHandler.RADIANT_LAND_KEY) {
                            createWormhole(destination);
                            entity.sendSystemMessage(Component.literal("Use wormhole on 1,Y(~120),1 coordinates to return back"));
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


    public void forceStructureRecheck(){
        this.structureCorrect = Multiblocks.RADIANT_LAND_PORTAL.check(level,worldPosition,true);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        forceStructureRecheck();
    }


    public boolean isStructureCorrect() {
        return structureCorrect;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(worldPosition).add(0,3,0),3,3);
    }

    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(Multiblocks.RADIANT_LAND_PORTAL);
    }
}
