package com.finderfeed.solarcraft.content.world_generation.structures.maze_key_keeper;

import com.finderfeed.solarcraft.events.other_events.StructurePieces;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Map;


public class TrapStructurePieces {
    private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation("solarcraft", "savanna_dungeon");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));


    public static void start(StructureTemplateManager templateManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieceList) {
        int x = pos.getX();
        int z = pos.getZ();


        BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
        BlockPos blockpos = rotationOffSet.offset(x-5, pos.getY(), z-5);
        pieceList.addPiece(new TrapStructurePieces.Piece(templateManager, DUNGEON_PIECE, rotation,blockpos));
    }

    public static class Piece extends TemplateStructurePiece {

        public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, Rotation rot, BlockPos pos) {
            super(StructurePieces.DUNGEON_MAZE_PIECE, 0, templateManagerIn, resourceLocationIn, resourceLocationIn.toString(), makeSettings(rot,DUNGEON_PIECE), makePosition(DUNGEON_PIECE,pos,0));
        }

        public Piece(  StructurePieceSerializationContext p_163670_,CompoundTag tagCompound) {
            super(StructurePieces.DUNGEON_MAZE_PIECE, tagCompound, p_163670_.structureTemplateManager(), (loc)->{
                return makeSettings(Rotation.valueOf(tagCompound.getString("Rot")),loc);
            });
        }



        private static StructurePlaceSettings makeSettings(Rotation p_162447_, ResourceLocation p_162448_) {
            return (new StructurePlaceSettings()).setRotation(p_162447_).setMirror(Mirror.NONE).setRotationPivot((BlockPos) new BlockPos(3,5,5)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        private static BlockPos makePosition(ResourceLocation p_162453_, BlockPos p_162454_, int p_162455_) {
            return p_162454_.offset((Vec3i) TrapStructurePieces.OFFSET.get(p_162453_)).below(p_162455_);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext level,CompoundTag tag) {
            super.addAdditionalSaveData(level,tag);
//            tag.putString("Template", this.resourceLocation.toString());
            tag.putString("Rot", this.placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String func, BlockPos pos, ServerLevelAccessor world, RandomSource rnd, BoundingBox box) {
//            if ("chest".equals(func)){
//                world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
//                BlockEntity tile = world.getBlockEntity(pos.below());
//                if (tile instanceof ChestBlockEntity){
//                    ((ChestBlockEntity) tile).setLootTable(new ResourceLocation(SolarCraft.MOD_ID,"chest/dungeon_maze"),rnd.nextLong());
//                }
//            }
        }

    }
}
