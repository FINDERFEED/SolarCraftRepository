package com.finderfeed.solarcraft.content.world_generation.structures.clearing_ritual_structure;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.structures.StructurePieces;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Map;

public class ClearingRitualPieces {
    private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation(SolarCraft.MOD_ID, "clearing_ritual_structure");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));

    public static void start(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, StructurePiecesBuilder pieceList) {
        int x = pos.getX();
        int z = pos.getZ();
        BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
        BlockPos blockpos = rotationOffSet.offset(x, pos.getY(), z);
        pieceList.addPiece(new ClearingRitualPieces.Piece(templateManager, DUNGEON_PIECE, rotation,blockpos));
    }

    public static class Piece extends TemplateStructurePiece {


        public Piece( StructureTemplateManager p_163662_, ResourceLocation p_163663_,Rotation rot, BlockPos p_163666_) {
            super(StructurePieces.CLEARING_RITUAL_STRUCTURE.get(), 0, p_163662_, p_163663_, p_163663_.toString(), makeSettings(rot,p_163663_), makePosition(p_163663_,p_163666_,0));
        }

        public Piece(StructurePieceSerializationContext p_163670_, CompoundTag tagCompound) {
            super(StructurePieces.CLEARING_RITUAL_STRUCTURE.get(), tagCompound, p_163670_.structureTemplateManager(), (loc)->{
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
        protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {

        }



    }
}
