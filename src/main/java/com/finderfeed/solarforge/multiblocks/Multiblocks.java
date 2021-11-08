package com.finderfeed.solarforge.multiblocks;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.registries.Tags;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import net.minecraft.core.Direction;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;


import java.util.Map;

public enum Multiblocks {
    SOLAR_CORE(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_CORE.get().defaultBlockState(),'Z',false)
            .addBlock(BlocksRegistry.ALGADIUM_BLOCK.get().defaultBlockState(),'H',false)
            .addBlock(BlocksRegistry.ILLIDIUM_BLOCK.get().defaultBlockState(),'X',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'J',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'N',false)
            .addBlock(BlocksRegistry.ALGADIUM_BLOCK.get().defaultBlockState(),'H',false)
            .addStruct(Structures.CORE_STRUCTURE)
            .addMainBlock(BlocksRegistry.SOLAR_CORE.get().defaultBlockState())
            .addAchievement(Progression.CRAFT_SOLAR_ENERGY_GENERATOR)
            .setStructName("solarforge.solar_core_structure")
        )),
    AURA_HEALER(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.ILLIDIUM_BLOCK.get().defaultBlockState(),'I',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'C',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'L',false)
            .addBlock(BlocksRegistry.AURA_HEALER_BLOCK.get().defaultBlockState(), 'H',false)
            .addStruct(Structures.AURA_HEALER_STRUCTURE)
            .addMainBlock(BlocksRegistry.AURA_HEALER_BLOCK.get().defaultBlockState())
            .addAchievement(Progression.SOLAR_INFUSER)
            .setStructName("solarforge.aura_healer_structure")
        )),
    SOLAR_MORTAR(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get().defaultBlockState(), 'M',false)

            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_EAST, 'H',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_WEST, 'K',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_SOUTH, 'h',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_NORTH, 'k',true)

            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'C',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'L',false)
            .addAchievement(Progression.CRAFT_SOLAR_ENERGY_GENERATOR)
            .addStruct(Structures.MORTAR_STRUCTURE)
            .addMainBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get().defaultBlockState())
            .setStructName("solarforge.mortar_structure")
    )),
    SOLAR_ENERGY_GENERATOR(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get().defaultBlockState(), 'M',false)

            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_EAST, 'H',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_WEST, 'F',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_NORTH, 'h',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_SOUTH, 'f',true)

            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'C',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'L',false)
            .addBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get().defaultBlockState(),'G',false)
            .addMainBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get().defaultBlockState())
            .addAchievement(Progression.CRAFT_SOLAR_LENS)
            .addStruct(Structures.SOLAR_ENERGY_GENERATOR_STRUCTURE)
            .setStructName("solarforge.solar_energy_generator")
    )),
    INFUSER_TIER_FIRST(new Multiblock(new Multiblock.Constructor()
            .addBlock(SolarForge.SOLAR_INFUSER.get().defaultBlockState(),'I',false)
            .addBlock(BlocksRegistry.SOLAR_POOL.get().defaultBlockState(),'F',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'C',false)
            .addBlock(Blocks.GOLD_BLOCK.defaultBlockState(),'G',false)

            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_EAST,'H',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_WEST,'K',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_SOUTH,'J',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_NORTH,'L',true)

            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'D',false)

            .addBlockAndTag(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(), Tags.CATALYST,'M',false)



            .addMainBlock(SolarForge.SOLAR_INFUSER.get().defaultBlockState())
            .addStruct(Structures.INFUSER_STRUCTURE)
            .addAchievement(Progression.SOLAR_INFUSER)
            .setStructName("solarforge_struct.solar_infuser")
    )),
    RADIANT_LAND_PORTAL(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.DIMENSION_CORE.get().defaultBlockState(), 'C',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'H',false)

            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_SOUTH, 'L',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_WEST, 'l',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_NORTH, 'f',true)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_EAST, 'g',true)

            .addBlock(BlocksRegistry.TERA_RUNE_BLOCK.get().defaultBlockState(), 'T',false)
            .addBlock(BlocksRegistry.FIRA_RUNE_BLOCK.get().defaultBlockState(), 'F',false)
            .addBlock(BlocksRegistry.KELDA_RUNE_BLOCK.get().defaultBlockState(), 'K',false)
            .addBlock(BlocksRegistry.ARDO_RUNE_BLOCK.get().defaultBlockState(), 'A',false)
            .addBlock(BlocksRegistry.URBA_RUNE_BLOCK.get().defaultBlockState(), 'U',false)
            .addBlock(BlocksRegistry.ZETA_RUNE_BLOCK.get().defaultBlockState(), 'Z',false)

            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_EAST_STRAIGHT, 'J',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_EAST_STRAIGHT, 'X',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_WEST_STRAIGHT, 'M',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_WEST_STRAIGHT, 'Y',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_NORTH_STRAIGHT, 'Q',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_SOUTH_STRAIGHT, '&',false)

            .addBlock(BlocksRegistry.RADIANT_LAND_PORTAL_CREATOR.get().defaultBlockState(), 'R',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(), 'I',false)
            .addMainBlock(BlocksRegistry.DIMENSION_CORE.get().defaultBlockState())
            .addStruct(Structures.RADIANT_DIM_PORTAL_STRUCTURE)
            .setStructName("solarcraft.dimension_portal")


    )),
    ZAP_TURRET(new Multiblock(new Multiblock.Constructor()
            .addStruct(Structures.ZAP_TURRET)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(), 'C',false)

            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_WEST_STRAIGHT, 'S',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_EAST_STRAIGHT, 'D',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_NORTH_STRAIGHT, 's',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_SOUTH_STRAIGHT, 'd',false)

            .addBlock(BlocksRegistry.ZAP_TURRET_BLOCK.get().defaultBlockState(), 'T',false)
            .addMainBlock(BlocksRegistry.ZAP_TURRET_BLOCK.get().defaultBlockState())
            .addAchievement(Progression.CRAFT_SOLAR_LENS)
            .setStructName("solarcraft.zap_turret")
    )),
    RUNIC_ENERGY_PYLON(new Multiblock(new Multiblock.Constructor()
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_EAST_STRAIGHT,'T',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_WEST_STRAIGHT,'D',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_NORTH_STRAIGHT,'G',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_SOUTH_STRAIGHT,'H',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_EAST_STRAIGHT,'t',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_WEST_STRAIGHT,'d',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_NORTH_STRAIGHT,'g',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_SOUTH_STRAIGHT,'h',false)

            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_EAST,'Y',false)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_WEST,'U',false)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_SOUTH,'I',false)
            .addBlock(Structures.BlockStates.HORIZONTAL_COLLUMN_FACING_NORTH,'O',false)

            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'A',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'N',false)
            .addMainBlock(BlocksRegistry.RUNE_ENERGY_PYLON.get().defaultBlockState())
            .addStruct(Structures.PYLON)
            .setStructName("structure.energy_pylon")
            .addAchievement(Progression.RUNE_ENERGY_PYLON)

    ))
    ;



    public static Map<String,Multiblock> MULTIBLOCKS = Map.of(
            "infuser", INFUSER_TIER_FIRST.a,
            "generator", SOLAR_ENERGY_GENERATOR.a,
            "healer", AURA_HEALER.a,
            "mortar", SOLAR_MORTAR.a,
            "lesser_core", SOLAR_CORE.a,
            "dim_portal",RADIANT_LAND_PORTAL.a,
            "zap_turret", ZAP_TURRET.a,
            "pylon", RUNIC_ENERGY_PYLON.a
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
            INFUSER_TIER_FIRST,
            RADIANT_LAND_PORTAL
    };





}
class Structures{

    public static String[][] PYLON = {
            {
                    " BBB ",
                    "BBBBB",
                    "BBABB",
                    "BBBBB",
                    " BBB "
            },
            {
                    "  N  ",
                    "     ",
                    "N   N",
                    "     ",
                    "  N  "
            },
            {
                    "     ",
                    "     ",
                    "     ",
                    "     ",
                    "     "
            },
            {
                    "  N  ",
                    "     ",
                    "N   N",
                    "     ",
                    "  N  "
            },
            {
                    " TAD ",
                    "H   H",
                    "A   A",
                    "G   G",
                    " TAD "
            },
            {
                    "  h  ",
                    "  Y  ",
                    "tOAId",
                    "  Y  ",
                    "  g  "
            },
            {
                    "     ",
                    "  h  ",
                    " tAd ",
                    "  g  ",
                    "     "
            }

    };

    public static String[][] ZAP_TURRET = {
            {
                    "BBB",
                    "BBB",
                    "BBB"

            },
            {
                    " s ",
                    "SCD",
                    " d "

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
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
                    "BBBBBBBBBBBBB",
            },
            {
                    "D  D  D  D  D",
                    "             ",
                    "      B      ",
                    "D  F     F  D",
                    "             ",
                    "             ",
                    "D B   I   B D",
                    "             ",
                    "             ",
                    "D  F     F  D",
                    "      B      ",
                    "             ",
                    "D  D  D  D  D",
            },
            {
                    "D  M  D  M  D",
                    "             ",
                    "      F      ",
                    "M           M",
                    "             ",
                    "             ",
                    "D F       F D",
                    "             ",
                    "             ",
                    "M           M",
                    "      F      ",
                    "             ",
                    "D  M  D  M  D",
            },
            {
                    "D  D  D  D  D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D  D  D  D  D",
            },
            {
                    "D  D  D  D  D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D  D  D  D  D",
            },
            {
                    "D  GLLCLLG  D",
                    "             ",
                    "             ",
                    "G           G",
                    "K           H",
                    "K           H",
                    "C           C",
                    "K           H",
                    "K           H",
                    "G           G",
                    "             ",
                    "             ",
                    "D  GJJCJJG  D",
            },
            {
                    "CLLC     CLLC",
                    "K           H",
                    "K           H",
                    "C           C",
                    "             ",
                    "             ",
                    "             ",
                    "             ",
                    "             ",
                    "C           C",
                    "K           H",
                    "K           H",
                    "CJJC     CJJC",
            },

    };
    public static String[][] SOLAR_ENERGY_GENERATOR_STRUCTURE =
    {
            {
                    "LhhhL",
                    "FBBBH",
                    "FBBBH",
                    "FBBBH",
                    "LfffL"
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
                            "LkkkL",
                            "K   H",
                            "K   H",
                            "K   H",
                            "LhhhL"
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
                            "LkkkL",
                            "K   H",
                            "K   H",
                            "K   H",
                            "LhhhL"
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
                            "LkkkL",
                            "K   H",
                            "K   H",
                            "K   H",
                            "LhhhL"
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
