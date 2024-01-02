package com.finderfeed.solarcraft.content.world_generation.structures.dungeon_one_key_lock;

import com.finderfeed.solarcraft.content.world_generation.structures.StructurePieces;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Map;


public class DungeonOnePieces {

        private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation("solarcraft", "solarcraft_dungeon_one");
        private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));

        /*
         * Begins assembling your structure and where the pieces needs to go.
         */
        public static void start(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, StructurePiecesBuilder pieceList) {
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
            BlockPos blockpos = rotationOffSet.offset(x-20, pos.getY()-14, z-20);
            pieceList.addPiece(new DungeonOnePieces.Piece(templateManager, DUNGEON_PIECE, rotation,blockpos));
        }

        /*
         * Here's where some voodoo happens. Most of this doesn't need to be touched but you do
         * have to pass in the IStructurePieceType you registered into the super constructors.
         *
         * The method you will most likely want to touch is the handleDataMarker method.
         */
        public static class Piece extends TemplateStructurePiece {
            public Piece( StructureTemplateManager p_163662_, ResourceLocation p_163663_,  Rotation rot, BlockPos p_163666_) {
                super(StructurePieces.DUNGEON_ONE_PIECE.get(), 0, p_163662_, p_163663_, p_163663_.toString(), makeSettings(rot,p_163663_), makePosition(p_163663_,p_163666_,0));
            }

            public Piece(  StructurePieceSerializationContext p_163670_,CompoundTag tagCompound) {
                super(StructurePieces.DUNGEON_ONE_PIECE.get(), tagCompound, p_163670_.structureTemplateManager(), (loc)->{
                    return makeSettings(Rotation.valueOf(tagCompound.getString("Rot")),loc);
                });
            }




            private static StructurePlaceSettings makeSettings(Rotation p_162447_, ResourceLocation p_162448_) {
                return (new StructurePlaceSettings()).setRotation(p_162447_).setMirror(Mirror.NONE).setRotationPivot((BlockPos) new BlockPos(3,5,5)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
            }

            private static BlockPos makePosition(ResourceLocation p_162453_, BlockPos p_162454_, int p_162455_) {
                return p_162454_.offset((Vec3i) DungeonOnePieces.OFFSET.get(p_162453_)).below(p_162455_);
            }
//

            @Override
            protected void addAdditionalSaveData(StructurePieceSerializationContext level, CompoundTag tag) {
                super.addAdditionalSaveData(level,tag);
//                tag.putString("Template", this.resourceLocation.toString());
                tag.putString("Rot", this.placeSettings.getRotation().name());
            }

            @Override
            protected void handleDataMarker(String func, BlockPos pos, ServerLevelAccessor world, RandomSource rnd, BoundingBox p_226910_) {
                if ("chest".equals(func)){
                    world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
                    BlockEntity tile = world.getBlockEntity(pos.below());
                    if (tile instanceof ChestBlockEntity){
                        ((ChestBlockEntity) tile).setLootTable(new ResourceLocation("solarcraft","chest/dungeon_chest_fragments"),rnd.nextLong());
                    }
                }
                if ("chest_star_piece".equals(func)){
                    world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
                    BlockEntity tile = world.getBlockEntity(pos.below());
                    if (tile instanceof ChestBlockEntity){
                        ((ChestBlockEntity) tile).setLootTable(new ResourceLocation("solarcraft","chest/dungeon_one"),rnd.nextLong());
                    }
                }
            }

//            @Override
//            protected void handleDataMarker(String func, BlockPos pos, ServerLevelAccessor world, Random rnd, BoundingBox box) {
//                if ("chest".equals(func)){
//                    world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
//                    BlockEntity tile = world.getBlockEntity(pos.below());
//                    if (tile instanceof ChestBlockEntity){
//                        ((ChestBlockEntity) tile).setLootTable(new ResourceLocation("solarcraft","chest/dungeon_chest_fragments"),rnd.nextLong());
//                    }
//                }
//                if ("chest_star_piece".equals(func)){
//                    world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
//                    BlockEntity tile = world.getBlockEntity(pos.below());
//                    if (tile instanceof ChestBlockEntity){
//                        ((ChestBlockEntity) tile).setLootTable(new ResourceLocation("solarcraft","chest/dungeon_one"),rnd.nextLong());
//                    }
//                }
//            }
        }
    }
//        public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
//                super(FeatureInit.DUNGEON_ONE_PIECE_TEST, 0);
//                this.resourceLocation = resourceLocationIn;
//                BlockPos blockpos = DungeonOnePieces.OFFSET.get(resourceLocation);
//                this.templatePosition = pos.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
//                this.rotation = rotationIn;
//                this.setupPiece(templateManagerIn);
//            }
//
//            public Piece(StructureTemplateManager templateManagerIn, CompoundTag tagCompound) {
//                super(FeatureInit.DUNGEON_ONE_PIECE_TEST, tagCompound);
//                this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
//                this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
//                this.setupPiece(templateManagerIn);
//            }
//
//            private void setupPiece(StructureTemplateManager templateManager) {
//                StructureTemplate template = templateManager.get(this.resourceLocation);
//                StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
//                this.setup(template, this.templatePosition, placementsettings);
//            }
