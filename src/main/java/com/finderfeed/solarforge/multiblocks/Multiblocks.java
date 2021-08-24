package com.finderfeed.solarforge.multiblocks;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;

import java.util.Map;

public enum Multiblocks {
    SOLAR_CORE(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_CORE.get(),'Z')
            .addBlock(BlocksRegistry.ALGADIUM_BLOCK.get(),'H')
            .addBlock(BlocksRegistry.ILLIDIUM_BLOCK.get(),'X')
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get(),'B')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),'J')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get(),'N')
            .addBlock(BlocksRegistry.ALGADIUM_BLOCK.get(),'H')
            .addStruct(Structures.CORE_STRUCTURE)
            .addMainBlock(BlocksRegistry.SOLAR_CORE.get())
            .addAchievement(Achievement.CRAFT_SOLAR_ENERGY_GENERATOR)
            .setStructName("solarforge.solar_core_structure")
        )),
    AURA_HEALER(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.ILLIDIUM_BLOCK.get(),'I')
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get(),'B')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get(),'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),'L')
            .addBlock(BlocksRegistry.AURA_HEALER_BLOCK.get(), 'H')
            .addStruct(Structures.AURA_HEALER_STRUCTURE)
            .addMainBlock(BlocksRegistry.AURA_HEALER_BLOCK.get())
            .addAchievement(Achievement.USE_SOLAR_INFUSER)
            .setStructName("solarforge.aura_healer_structure")
        )),
    SOLAR_MORTAR(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get(),'B')
            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get(), 'M')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get(), 'H')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get(),'L')
            .addAchievement(Achievement.CRAFT_SOLAR_ENERGY_GENERATOR)
            .addStruct(Structures.MORTAR_STRUCTURE)
            .addMainBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get())
            .setStructName("solarforge.mortar_structure")
    )),
    SOLAR_ENERGY_GENERATOR(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get(),'B')
            .addBlock(BlocksRegistry.SOLAR_MORTAR_BLOCK.get(), 'M')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get(), 'H')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get(),'L')
            .addBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get(),'G')
            .addMainBlock(BlocksRegistry.SOLAR_ENERGY_GENERATOR.get())
            .addAchievement(Achievement.CRAFT_SOLAR_LENS)
            .addStruct(Structures.SOLAR_ENERGY_GENERATOR_STRUCTURE)
            .setStructName("solarforge.solar_energy_generator")
    )),
    INFUSER(new Multiblock(new Multiblock.Constructor()
            .addBlock(SolarForge.SOLAR_INFUSER.get(),'I')
            .addBlock(BlocksRegistry.SOLAR_POOL.get(),'F')
            .addMainBlock(SolarForge.SOLAR_INFUSER.get())
            .addStruct(Structures.INFUSER_STRUCTURE)
            .addAchievement(Achievement.CRAFT_SOLAR_INFUSER)
            .setStructName("solarforge_struct.solar_infuser")
    )),
    RADIANT_LAND_PORTAL(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.DIMENSION_CORE.get(), 'C')
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get(),'B')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),'H')
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get(), 'L')
            .addBlock(BlocksRegistry.TERA_RUNE_BLOCK.get(), 'T')
            .addBlock(BlocksRegistry.FIRA_RUNE_BLOCK.get(), 'F')
            .addBlock(BlocksRegistry.KELDA_RUNE_BLOCK.get(), 'K')
            .addBlock(BlocksRegistry.ARDO_RUNE_BLOCK.get(), 'A')
            .addBlock(BlocksRegistry.URBA_RUNE_BLOCK.get(), 'U')
            .addBlock(BlocksRegistry.ZETA_RUNE_BLOCK.get(), 'Z')
            .addBlock(BlocksRegistry.SOLAR_STONE_STAIRS.get(), 'J')
            .addMainBlock(BlocksRegistry.DIMENSION_CORE.get())
            .addStruct(Structures.RADIANT_DIM_PORTAL_STRUCTURE)
            .setStructName("solarcraft.dimension_portal")


    ));



    public static Map<String,Multiblock> multiblocks = Map.of(
            "infuser", INFUSER.a,
            "generator", SOLAR_ENERGY_GENERATOR.a,
            "healer", AURA_HEALER.a,
            "mortar", SOLAR_MORTAR.a,
            "lesser_core", SOLAR_CORE.a,
            "dim_portal",RADIANT_LAND_PORTAL.a
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
                    "HJ   JH",
                    "J     J",
                    "       ",
                    " H   H ",
                    "       ",
                    "J     J",
                    "HJ   JH"
            },
            {
                    "ILLLLLI",
                    "L     L",
                    "L     L",
                    "LJ   JL",
                    "L     L",
                    "L     L",
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
}
