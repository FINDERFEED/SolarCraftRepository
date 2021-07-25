package com.finderfeed.solarforge.world_generation.structures.dimensional_shard_structure;

import com.finderfeed.solarforge.events.other_events.FeatureInit;
import com.google.common.collect.ImmutableMap;
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
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

import ResourceLocation;

public class DimStructPieces {
    private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation("solarforge", "dimensional_shard_structure");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));

    /*
     * Begins assembling your structure and where the pieces needs to go.
     */
    public static void start(StructureManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
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
        pieceList.add(new DimStructPieces.Piece(templateManager, DUNGEON_PIECE, blockpos, rotation));
    }

    /*
     * Here's where some voodoo happens. Most of this doesn't need to be touched but you do
     * have to pass in the IStructurePieceType you registered into the super constructors.
     *
     * The method you will most likely want to touch is the handleDataMarker method.
     */
    public static class Piece extends TemplateStructurePiece {
        private ResourceLocation resourceLocation;
        private Rotation rotation;

        public Piece(StructureManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
            super(FeatureInit.DIMENSIONAL_SHARD_STRUCTURE, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = DimStructPieces.OFFSET.get(resourceLocation);
            this.templatePosition = pos.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = rotationIn;
            this.setupPiece(templateManagerIn);
        }

        public Piece(StructureManager templateManagerIn, CompoundTag tagCompound) {
            super(FeatureInit.DIMENSIONAL_SHARD_STRUCTURE, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        private void setupPiece(StructureManager templateManager) {
            StructureTemplate template = templateManager.get(this.resourceLocation);
            StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void addAdditionalSaveData(CompoundTag tag) {
            super.addAdditionalSaveData(tag);
            tag.putString("Template", this.resourceLocation.toString());
            tag.putString("Rot", this.rotation.name());
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
        @Override
        protected void handleDataMarker(String func, BlockPos pos, ServerLevelAccessor world, Random rnd, BoundingBox box) {
            if ("treasure".equals(func)){
                world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
                BlockEntity tile = world.getBlockEntity(pos.below());
                if (tile instanceof ChestBlockEntity){
                    ((ChestBlockEntity) tile).setLootTable(new ResourceLocation("solarforge","chest/dimensional_shard"),rnd.nextLong());
                }
            }
        }
    }
}
