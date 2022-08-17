package com.finderfeed.solarforge.world_generation.structures.clearing_ritual_structure;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.events.other_events.StructurePieces;
import com.finderfeed.solarforge.world_generation.structures.charging_station.ChargingStationPieces;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Map;
import java.util.Random;

public class ClearingRitualPieces {
    private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation(SolarForge.MOD_ID, "clearing_ritual_structure");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));

    public static void start(StructureManager templateManager, BlockPos pos, Rotation rotation, StructurePiecesBuilder pieceList, Random random) {
        int x = pos.getX();
        int z = pos.getZ();
        BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
        BlockPos blockpos = rotationOffSet.offset(x, pos.getY(), z);
        pieceList.addPiece(new ClearingRitualPieces.Piece(templateManager, DUNGEON_PIECE, rotation,blockpos));
    }

    public static class Piece extends TemplateStructurePiece {


        public Piece( StructureManager p_163662_, ResourceLocation p_163663_,Rotation rot, BlockPos p_163666_) {
            super(StructurePieces.CLEARING_RITUAL_STRUCTURE, 0, p_163662_, p_163663_, p_163663_.toString(), makeSettings(rot,p_163663_), makePosition(p_163663_,p_163666_,0));
        }

        public Piece(StructurePieceSerializationContext p_163670_, CompoundTag tagCompound) {
            super(StructurePieces.CLEARING_RITUAL_STRUCTURE, tagCompound, p_163670_.structureManager(), (loc)->{
                return makeSettings(Rotation.valueOf(tagCompound.getString("Rot")),loc);
            });
        }




        private static StructurePlaceSettings makeSettings(Rotation p_162447_, ResourceLocation p_162448_) {
            return (new StructurePlaceSettings()).setRotation(p_162447_).setMirror(Mirror.NONE)
                    .setRotationPivot((BlockPos) new BlockPos(3,5,5))
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        private static BlockPos makePosition(ResourceLocation p_162453_, BlockPos p_162454_, int p_162455_) {
            return p_162454_.offset((Vec3i)  OFFSET.get(p_162453_)).below(p_162455_);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext level,CompoundTag tag) {
            super.addAdditionalSaveData(level,tag);

            tag.putString("Rot", this.placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String p_186175_1_, BlockPos p_186175_2_, ServerLevelAccessor p_186175_3_, Random p_186175_4_, BoundingBox p_186175_5_) {

        }


    }
}
