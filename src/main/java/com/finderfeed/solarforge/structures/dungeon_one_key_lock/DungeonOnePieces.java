package com.finderfeed.solarforge.structures.dungeon_one_key_lock;

import com.finderfeed.solarforge.other_events.FeatureInit;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

    public class DungeonOnePieces {

        private static final ResourceLocation DUNGEON_PIECE = new ResourceLocation("solarforge", "solarforge_dungeon_one");
        private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(DUNGEON_PIECE, new BlockPos(0, 1, 0));

        /*
         * Begins assembling your structure and where the pieces needs to go.
         */
        public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
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
            BlockPos blockpos = rotationOffSet.offset(x, pos.getY()-14, z);
            pieceList.add(new DungeonOnePieces.Piece(templateManager, DUNGEON_PIECE, blockpos, rotation));
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

            public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
                super(FeatureInit.DUNGEON_ONE_PIECE_TEST, 0);
                this.resourceLocation = resourceLocationIn;
                BlockPos blockpos = DungeonOnePieces.OFFSET.get(resourceLocation);
                this.templatePosition = pos.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                this.rotation = rotationIn;
                this.setupPiece(templateManagerIn);
            }

            public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
                super(FeatureInit.DUNGEON_ONE_PIECE_TEST, tagCompound);
                this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
                this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
                this.setupPiece(templateManagerIn);
            }

            private void setupPiece(TemplateManager templateManager) {
                Template template = templateManager.get(this.resourceLocation);
                PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
                this.setup(template, this.templatePosition, placementsettings);
            }

            @Override
            protected void addAdditionalSaveData(CompoundNBT tag) {
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
            protected void handleDataMarker(String func, BlockPos pos, IServerWorld world, Random rnd, MutableBoundingBox box) {
                if ("chest".equals(func)){
                    world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
                    TileEntity tile = world.getBlockEntity(pos.below());
                    if (tile instanceof ChestTileEntity){
                        ((ChestTileEntity) tile).setLootTable(LootTables.ABANDONED_MINESHAFT,rnd.nextLong());
                    }
                }
                if ("chest_star_piece".equals(func)){
                    world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
                    TileEntity tile = world.getBlockEntity(pos.below());
                    if (tile instanceof ChestTileEntity){
                        ((ChestTileEntity) tile).setLootTable(new ResourceLocation("solarforge","chest/dungeon_one"),rnd.nextLong());
                    }
                }
            }
        }
    }

