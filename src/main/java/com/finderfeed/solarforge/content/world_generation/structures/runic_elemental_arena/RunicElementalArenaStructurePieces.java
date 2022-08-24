package com.finderfeed.solarforge.content.world_generation.structures.runic_elemental_arena;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.events.other_events.StructurePieces;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Map;
import java.util.Random;

public class RunicElementalArenaStructurePieces {
    private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation(SolarForge.MOD_ID, "runic_elemental_arena");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));


    public static void start(StructureManager templateManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieceList, Random random) {
        int x = pos.getX();
        int z = pos.getZ();


        BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
        BlockPos blockpos = rotationOffSet.offset(x- 8, pos.getY()-1, z- 8);
        pieceList.addPiece(new RunicElementalArenaStructurePieces.Piece(templateManager, DUNGEON_PIECE, rotation,blockpos));
    }

    public static class Piece extends TemplateStructurePiece {

        public Piece( StructureManager templateManagerIn, ResourceLocation resourceLocationIn,Rotation rot, BlockPos pos) {
            super(StructurePieces.RUNIC_ELEMENTAL_ARENA_PIECE, 0, templateManagerIn, resourceLocationIn, resourceLocationIn.toString(),
                    makeSettings(rot,DUNGEON_PIECE),
                    makePosition(DUNGEON_PIECE,pos,1));
        }

        public Piece(StructurePieceSerializationContext p_163670_, CompoundTag tagCompound) {
            super(StructurePieces.RUNIC_ELEMENTAL_ARENA_PIECE, tagCompound, p_163670_.structureManager(), (loc)->{
                return makeSettings(Rotation.valueOf(tagCompound.getString("Rot")),loc);
            });
        }



        private static StructurePlaceSettings makeSettings(Rotation p_162447_, ResourceLocation p_162448_) {
            return (new StructurePlaceSettings()).setRotation(p_162447_).setMirror(Mirror.NONE)
                    .setRotationPivot((BlockPos) new BlockPos(8,0,8)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        private static BlockPos makePosition(ResourceLocation p_162453_, BlockPos p_162454_, int p_162455_) {
            return p_162454_.offset((Vec3i)RunicElementalArenaStructurePieces.OFFSET.get(p_162453_)).below(p_162455_);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext level,CompoundTag tag) {
            super.addAdditionalSaveData(level,tag);

            tag.putString("Rot", this.placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String p_73683_, BlockPos p_73684_, ServerLevelAccessor p_73685_, Random p_73686_, BoundingBox p_73687_) {

        }

    }
}

