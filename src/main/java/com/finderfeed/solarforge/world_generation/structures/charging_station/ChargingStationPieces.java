package com.finderfeed.solarforge.world_generation.structures.charging_station;

import com.finderfeed.solarforge.events.other_events.StructurePieces;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import java.util.Map;
import java.util.Random;



public class ChargingStationPieces {
    private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation("solarforge", "cold_star_charging_station");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));

    /*
     * Begins assembling your structure and where the pieces needs to go.
     */
    public static void start(StructureManager templateManager, BlockPos pos, Rotation rotation, StructurePiecesBuilder pieceList, Random random) {
        int x = pos.getX();
        int z = pos.getZ();

        // This is how we factor in rotation for multi-piece structures.
        //
        // I would recommend using the OFFSET map above to have each piece at correct height relative of each other
        // and keep the X and Z equal to 0. And then in rotations, have the centermost piece have a rotation
        // of 0, 0, 0 and then have all other pieces' rotation be based off of the bottommost left corner of
        // that piece (the corner that is smallest in X and Z).
        //
        // Lots of trial and error may be needed to get this right for your structure.
        BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
        BlockPos blockpos = rotationOffSet.offset(x, pos.getY(), z);
        pieceList.addPiece(new ChargingStationPieces.Piece(templateManager, DUNGEON_PIECE, rotation,blockpos));
    }

    /*
     * Here's where some voodoo happens. Most of this doesn't need to be touched but you do
     * have to pass in the IStructurePieceType you registered into the super constructors.
     *
     * The method you will most likely want to touch is the handleDataMarker method.
     */
    public static class Piece extends TemplateStructurePiece {


        public Piece( StructureManager p_163662_, ResourceLocation p_163663_,Rotation rot, BlockPos p_163666_) {
            super(StructurePieces.CHARGING_PIECE, 0, p_163662_, p_163663_, p_163663_.toString(), makeSettings(rot,p_163663_), makePosition(p_163663_,p_163666_,0));
        }

        public Piece( StructurePieceSerializationContext p_163670_,CompoundTag tagCompound) {
            super(StructurePieces.CHARGING_PIECE, tagCompound, p_163670_.structureManager(), (loc)->{
                return makeSettings(Rotation.valueOf(tagCompound.getString("Rot")),loc);
            });
        }




        private static StructurePlaceSettings makeSettings(Rotation p_162447_, ResourceLocation p_162448_) {
            return (new StructurePlaceSettings()).setRotation(p_162447_).setMirror(Mirror.NONE).setRotationPivot((BlockPos) new BlockPos(3,5,5)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        private static BlockPos makePosition(ResourceLocation p_162453_, BlockPos p_162454_, int p_162455_) {
            return p_162454_.offset((Vec3i) ChargingStationPieces.OFFSET.get(p_162453_)).below(p_162455_);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext level,CompoundTag tag) {
            super.addAdditionalSaveData(level,tag);

            tag.putString("Rot", this.placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String p_186175_1_, BlockPos p_186175_2_, ServerLevelAccessor p_186175_3_, Random p_186175_4_, BoundingBox p_186175_5_) {

        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */


        /*
         * If you added any data marker structure blocks to your structure, you can access and modify them here.
         * In this case, our structure has a data maker with the string "chest" put into it. So we check to see
         * if the incoming function is "chest" and if it is, we now have that exact position.
         *
         * So what is done here is we replace the structure block with
         * a chest and we can then set the loottable for it.
         *
         * You can set other data markers to do other behaviors such as spawn a random mob in a certain spot,
         * randomize what rare block spawns under the floor, or what item an Item Frame will have.
         */

    }
}
//        public Piece(StructureManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
//            super(FeatureInit.CHARGING_PIECE, 0);
//            this.resourceLocation = resourceLocationIn;
//            BlockPos blockpos = ChargingStationPieces.OFFSET.get(resourceLocation);
//            this.templatePosition = pos.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
//            this.rotation = rotationIn;
//            this.setupPiece(templateManagerIn);
//        }
//
//        public Piece(StructureManager templateManagerIn, CompoundTag tagCompound) {
//            super(FeatureInit.CHARGING_PIECE, tagCompound);
//            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
//            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
//            this.setupPiece(templateManagerIn);
//        }
//
//        private void setupPiece(StructureManager templateManager) {
//            StructureTemplate template = templateManager.get(this.resourceLocation);
//            StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
//            this.setup(template, this.templatePosition, placementsettings);
//        }