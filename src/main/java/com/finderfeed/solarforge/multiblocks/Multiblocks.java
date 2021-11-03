package com.finderfeed.solarforge.multiblocks;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;

import java.util.Map;
import java.util.Properties;

public enum Multiblocks {
    SOLAR_CORE(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_CORE.get().defaultBlockState(),'Z')
            .addBlock(BlocksRegistry.ALGADIUM_BLOCK.get().defaultBlockState(),'H')
            .addBlock(BlocksRegistry.ILLIDIUM_BLOCK.get().defaultBlockState(),'X')
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'J')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'N')
            .addBlock(BlocksRegistry.ALGADIUM_BLOCK.get().defaultBlockState(),'H')
            .addStruct(Structures.CORE_STRUCTURE)
            .addMainBlock(BlocksRegistry.SOLAR_CORE.get().defaultBlockState())
            .addAchievement(Progression.CRAFT_SOLAR_ENERGY_GENERATOR)
            .setStructName("solarforge.solar_core_structure")
        )),
    AURA_HEALER(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.ILLIDIUM_BLOCK.get().defaultBlockState(),'I')
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'L')
            .addBlock(BlocksRegistry.AURA_HEALER_BLOCK.get().defaultBlockState(), 'H')
            .addStruct(Structures.AURA_HEALER_STRUCTURE)
            .addMainBlock(BlocksRegistry.AURA_HEALER_BLOCK.get().defaultBlockState())
            .addAchievement(Progression.USE_SOLAR_INFUSER)
            .setStructName("solarforge.aura_healer_structure")
        )),
    SOLAR_MORTAR(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B')
            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get().defaultBlockState(), 'M')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get().defaultBlockState(), 'H')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'L')
            .addAchievement(Progression.CRAFT_SOLAR_ENERGY_GENERATOR)
            .addStruct(Structures.MORTAR_STRUCTURE)
            .addMainBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get().defaultBlockState())
            .setStructName("solarforge.mortar_structure")
    )),
    SOLAR_ENERGY_GENERATOR(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B')
            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get().defaultBlockState(), 'M')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get().defaultBlockState(), 'H')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'L')
            .addBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get().defaultBlockState(),'G')
            .addMainBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get().defaultBlockState())
            .addAchievement(Progression.CRAFT_SOLAR_LENS)
            .addStruct(Structures.SOLAR_ENERGY_GENERATOR_STRUCTURE)
            .setStructName("solarforge.solar_energy_generator")
    )),
    INFUSER(new Multiblock(new Multiblock.Constructor()
            .addBlock(SolarForge.SOLAR_INFUSER.get().defaultBlockState(),'I')
            .addBlock(BlocksRegistry.SOLAR_POOL.get().defaultBlockState(),'F')
            .addMainBlock(SolarForge.SOLAR_INFUSER.get().defaultBlockState())
            .addStruct(Structures.INFUSER_STRUCTURE)
            .addAchievement(Progression.CRAFT_SOLAR_INFUSER)
            .setStructName("solarforge_struct.solar_infuser")
    )),
    RADIANT_LAND_PORTAL(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.DIMENSION_CORE.get().defaultBlockState(), 'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'H')

            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_SOUTH, 'L')
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_WEST, 'l')
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_NORTH, 'f')
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_EAST, 'g')

            .addBlock(BlocksRegistry.TERA_RUNE_BLOCK.get().defaultBlockState(), 'T')
            .addBlock(BlocksRegistry.FIRA_RUNE_BLOCK.get().defaultBlockState(), 'F')
            .addBlock(BlocksRegistry.KELDA_RUNE_BLOCK.get().defaultBlockState(), 'K')
            .addBlock(BlocksRegistry.ARDO_RUNE_BLOCK.get().defaultBlockState(), 'A')
            .addBlock(BlocksRegistry.URBA_RUNE_BLOCK.get().defaultBlockState(), 'U')
            .addBlock(BlocksRegistry.ZETA_RUNE_BLOCK.get().defaultBlockState(), 'Z')

            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_EAST_STRAIGHT, 'J')
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_EAST_STRAIGHT, 'X')
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_WEST_STRAIGHT, 'M')
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_WEST_STRAIGHT, 'Y')
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_NORTH_STRAIGHT, 'Q')
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_SOUTH_STRAIGHT, '&')

            .addBlock(BlocksRegistry.RADIANT_LAND_PORTAL_CREATOR.get().defaultBlockState(), 'R')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(), 'I')
            .addMainBlock(BlocksRegistry.DIMENSION_CORE.get().defaultBlockState())
            .addStruct(Structures.RADIANT_DIM_PORTAL_STRUCTURE)
            .setStructName("solarcraft.dimension_portal")


    )),
    ZAP_TURRET(new Multiblock(new Multiblock.Constructor()
            .addStruct(Structures.ZAP_TURRET)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(), 'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_STAIRS.get().defaultBlockState(), 'S')
            .addBlock(BlocksRegistry.ZAP_TURRET_BLOCK.get().defaultBlockState(), 'T')
            .addMainBlock(BlocksRegistry.ZAP_TURRET_BLOCK.get().defaultBlockState())
            .addAchievement(Progression.CRAFT_SOLAR_LENS)
            .setStructName("solarcraft.zap_turret")
    ));



    public static Map<String,Multiblock> MULTIBLOCKS = Map.of(
            "infuser", INFUSER.a,
            "generator", SOLAR_ENERGY_GENERATOR.a,
            "healer", AURA_HEALER.a,
            "mortar", SOLAR_MORTAR.a,
            "lesser_core", SOLAR_CORE.a,
            "dim_portal",RADIANT_LAND_PORTAL.a,
            "zap_turret", ZAP_TURRET.a
    );


    Multiblock a;
    Multiblocks(Multiblock a){
        this.a = a;
    }

    public Multiblock getM(){
        return a;
    }

    public static Multiblocks[] ALL_STRUCTURES=
    {
            SOLAR_CORE,
            AURA_HEALER,
            SOLAR_MORTAR,
            SOLAR_ENERGY_GENERATOR,
            INFUSER,
            RADIANT_LAND_PORTAL
    };





}
class Structures{

    public static String[][] ZAP_TURRET = {
            {
                    "BBB",
                    "BBB",
                    "BBB"

            },
            {
                    " S ",
                    "SCS",
                    " S "

            },
            {
                    "   ",
                    " C ",
                    "   "

            },
            {
                    "   ",
                    " T ",
                    "   "

            },

    };


    public static String[][] RADIANT_DIM_PORTAL_STRUCTURE = {
            {
                    "BBBBBBB",
                    "BBBBBBB",
                    "BBBBBBB",
                    "BBBCBBB",
                    "BBBBBBB",
                    "BBBBBBB",
                    "BBBBBBB"
            },
            {
                    "H     H",
                    "       ",
                    "       ",
                    " H R H ",
                    "       ",
                    "       ",
                    "H     H"
            },
            {
                    "K     F",
                    "       ",
                    "       ",
                    " Z   U ",
                    "       ",
                    "       ",
                    "T     A"
            },
            {
                    "HY   XH",
                    "Q     Q",
                    "       ",
                    " H   H ",
                    "       ",
                    "&     &",
                    "HY   XH"
            },
            {
                    "IfffffI",
                    "l     g",
                    "l     g",
                    "lM   Jg",
                    "l     g",
                    "l     g",
                    "ILLLLLI"
            },

    };

    public static String[][] INFUSER_STRUCTURE = {
            {
                    "    F    ",
                    " F     F ",
                    "         ",
                    "         ",
                    "F   I   F",
                    "         ",
                    "         ",
                    " F     F ",
                    "    F    "
            }
    };
    public static String[][] SOLAR_ENERGY_GENERATOR_STRUCTURE =
    {
            {
                    "LHHHL",
                    "HBBBH",
                    "HBBBH",
                    "HBBBH",
                    "LHHHL"
            },
            {
                    "  C  ",
                    "     ",
                    "C L C",
                    "     ",
                    "  C  "
            },
            {
                    "  L  ",
                    "     ",
                    "L G L",
                    "     ",
                    "  L  "
            },
            {
                    "     ",
                    "  B  ",
                    " B B ",
                    "  B  ",
                    "     "
            }
    };
//    SOLAR_ENERGY_GENERATOR(new Multiblock(new Multiblock.Constructor()
//            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get(),'B')
//            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get(), 'M')
//            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get(), 'H')
//            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),'C')
//            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get(),'L')
//            .addBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get(),'G')
//            .addMainBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get())
//            .addAchievement(Achievements.CRAFT_SOLAR_LENS)
//    ));
    public static String[][] MORTAR_STRUCTURE =
            {
                    {
                            "BBBBB",
                            "BBBBB",
                            "BBBBB",
                            "BBBBB",
                            "BBBBB"
                    },
                    {
                            "LHHHL",
                            "H   H",
                            "H   H",
                            "H   H",
                            "LHHHL"
                    },
                    {
                            "CB  C",
                            "    B",
                            "     ",
                            "B    ",
                            "C  BC"
                    },
                    {
                            "C B C",
                            "     ",
                            "B   B",
                            "     ",
                            "C B C"
                    },
                    {
                            "C  BC",
                            "B    ",
                            "     ",
                            "    B",
                            "CB  C"
                    },
                    {
                            "LHHHL",
                            "H   H",
                            "H   H",
                            "H   H",
                            "LHHHL"
                    },
                    {
                            "CB  C",
                            "    B",
                            "     ",
                            "B    ",
                            "C  BC"
                    },
                    {
                            "C B C",
                            "     ",
                            "B   B",
                            "     ",
                            "C B C"
                    },
                    {
                            "C  BC",
                            "B    ",
                            "     ",
                            "    B",
                            "CB  C"
                    },
                    {
                            "LHHHL",
                            "H   H",
                            "H   H",
                            "H   H",
                            "LHHHL"
                    },
                    {
                            " BBB ",
                            "BBBBB",
                            "BB BB",
                            "BBBBB",
                            " BBB "
                    },
                    {
                            "  B  ",
                            " BBB ",
                            "BB BB",
                            " BBB ",
                            "  B  "
                    },
                    {
                            "     ",
                            "  C  ",
                            " CMC ",
                            "  C  ",
                            "     ",
                    },
                    {
                            "     ",
                            "  L  ",
                            " L L ",
                            "  L  ",
                            "     ",
                    }

            };
//    SOLAR_MORTAR(new Multiblock(new Multiblock.Constructor()
//            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get(),'B')
//            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get(), 'M')
//            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get(), 'H')
//            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),'C')
//            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get(),'L')
//            .addAchievement(Achievements.CRAFT_SOLAR_LENS)
//    ));

    public static String[][] AURA_HEALER_STRUCTURE =
            {
                    {
                        "BBB",
                        "BBB",
                        "BBB"
                    },
                    {
                            "L L",
                            " C ",
                            "L L"
                    },
                    {
                            "I I",
                            " H ",
                            "I I"
                    },

            };


    public static String[][] test =
            {
                    {
                            " X ",
                            "XXX",
                            " X "
                    },
                    {
                            " X ",
                            "X X",
                            " X "
                    }
            };

    public static String[][] CORE_STRUCTURE =
            {
                    {
                            "BBBBB",
                            "BBBBB",
                            "BBBBB",
                            "BBBBB",
                            "BBBBB",
                    },
                    {
                            "  J  ",
                            "     ",
                            "J   J",
                            "     ",
                            "  J  ",
                    },
                    {
                        "  N  ",
                        "  X  ",
                        "NXHXN",
                        "  X  ",
                        "  N  ",
                    },
                    {
                            "  X  ",
                            "     ",
                            "X   X",
                            "     ",
                            "  X  ",
                    },
                    {
                            "  H  ",
                            "     ",
                            "H Z H",
                            "     ",
                            "  H  ",
                    },
                    {
                            "  X  ",
                            "     ",
                            "X   X",
                            "     ",
                            "  X  ",
                    },
                    {
                            "     ",
                            "  X  ",
                            " XHX ",
                            "  X  ",
                            "     ",
                    }
            };

    public static class BlockStates{
        public static final BlockState SOLAR_STONE_STAIRS_BOTTOM_EAST_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.EAST)
                .setValue(StairBlock.HALF, Half.BOTTOM)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
        public static final BlockState SOLAR_STONE_STAIRS_BOTTOM_NORTH_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.NORTH)
                .setValue(StairBlock.HALF, Half.BOTTOM)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
        public static final BlockState SOLAR_STONE_STAIRS_BOTTOM_WEST_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.WEST)
                .setValue(StairBlock.HALF, Half.BOTTOM)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
        public static final BlockState SOLAR_STONE_STAIRS_BOTTOM_SOUTH_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.SOUTH)
                .setValue(StairBlock.HALF, Half.BOTTOM)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
        public static final BlockState SOLAR_STONE_STAIRS_TOP_EAST_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.EAST)
                .setValue(StairBlock.HALF, Half.TOP)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
        public static final BlockState SOLAR_STONE_STAIRS_TOP_NORTH_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.NORTH)
                .setValue(StairBlock.HALF, Half.TOP)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
        public static final BlockState SOLAR_STONE_STAIRS_TOP_WEST_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.WEST)
                .setValue(StairBlock.HALF, Half.TOP)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
        public static final BlockState SOLAR_STONE_STAIRS_TOP_SOUTH_STRAIGHT = BlocksRegistry.SOLAR_STONE_STAIRS.get()
                .defaultBlockState()
                .setValue(StairBlock.FACING, Direction.SOUTH)
                .setValue(StairBlock.HALF, Half.TOP)
                .setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);

        public static final BlockState HORIZONTAL_COLLUMN_FACING_SOUTH = BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get().defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING,Direction.SOUTH);
        public static final BlockState HORIZONTAL_COLLUMN_FACING_WEST = BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get().defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING,Direction.WEST);
        public static final BlockState HORIZONTAL_COLLUMN_FACING_NORTH = BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get().defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING,Direction.NORTH);
        public static final BlockState HORIZONTAL_COLLUMN_FACING_EAST = BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get().defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING,Direction.EAST);
    }
}
