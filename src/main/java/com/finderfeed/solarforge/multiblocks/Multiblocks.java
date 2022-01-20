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
            .addBlock(BlocksRegistry.INFUSING_POOL.get().defaultBlockState(),'F',false)
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
            .addStruct(Structures.INFUSER_TIER_1)
            .addAchievement(Progression.SOLAR_INFUSER)
            .setStructName("solarforge_struct.solar_infuser")
    )),
    INFUSER_TIER_RUNIC_ENERGY(new Multiblock(new Multiblock.Constructor()
            .extend(INFUSER_TIER_FIRST.getM())
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_EAST_STRAIGHT,'a',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_WEST_STRAIGHT,'b',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_NORTH_STRAIGHT,'c',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_SOUTH_STRAIGHT,'d',false)

            .addBlock(BlocksRegistry.ARDO_RUNE_BLOCK.get().defaultBlockState(),'f',false)
            .addBlock(BlocksRegistry.KELDA_RUNE_BLOCK.get().defaultBlockState(),'g',false)
            .addBlock(BlocksRegistry.ZETA_RUNE_BLOCK.get().defaultBlockState(),'h',false)
            .addBlock(BlocksRegistry.URBA_RUNE_BLOCK.get().defaultBlockState(),'j',false)
            .addBlock(BlocksRegistry.FIRA_RUNE_BLOCK.get().defaultBlockState(),'k',false)
            .addBlock(BlocksRegistry.TERA_RUNE_BLOCK.get().defaultBlockState(),'l',false)
            .addBlock(BlocksRegistry.ULTIMA_RUNE_BLOCK.get().defaultBlockState(),'z',false)
            .addBlock(BlocksRegistry.GIRO_RUNE_BLOCK.get().defaultBlockState(),'x',false)
            .addBlock(BlocksRegistry.REPEATER.get().defaultBlockState(),'o',false)
            .addBlock(Blocks.DIAMOND_BLOCK.defaultBlockState(),'v',false)
            .addStruct(Structures.INFUSER_TIER_2)
            .setStructName("solarforge_struct.solar_infuser")
    )),
    INFUSER_TIER_SOLAR_ENERGY(new Multiblock(new Multiblock.Constructor()
            .extend(INFUSER_TIER_RUNIC_ENERGY.getM())
            .addBlock(Blocks.OBSIDIAN.defaultBlockState(),'q',false)
            .addBlock(BlocksRegistry.ENERGIZED_STONE.get().defaultBlockState(),'e',false)

            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_EAST_STRAIGHT,'m',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_WEST_STRAIGHT,'n',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_NORTH_STRAIGHT,'u',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_SOUTH_STRAIGHT,'V',false)

            .addStruct(Structures.INFUSER_TIER_3)
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

            .addBlockAndTag(BlocksRegistry.INSCRIPTION_STONE.get().defaultBlockState(),Tags.INSCRIPTION_STONE,'c',false)
            .addBlockAndTag(BlocksRegistry.ARDO_RUNE_BLOCK.get().defaultBlockState(),Tags.CATALYST,'l',false)

            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'A',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'B',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'N',false)
            .addBlock(BlocksRegistry.RUNE_ENERGY_PYLON.get().defaultBlockState(),'p',false)

            .addMainBlock(BlocksRegistry.RUNE_ENERGY_PYLON.get().defaultBlockState())
            .addStruct(Structures.PYLON)
            .setStructName("structure.energy_pylon")
            .addAchievement(Progression.RUNE_ENERGY_PYLON)

    )),
    EXPLOSION_BLOCKER(new Multiblock(new Multiblock.Constructor()
            .addBlock(BlocksRegistry.SOLAR_STONE_CHISELED.get().defaultBlockState(),'q',false)
            .addBlock(BlocksRegistry.ENERGIZED_STONE.get().defaultBlockState(),'e',false)
            .addBlock(BlocksRegistry.SOLAR_STONE_COLLUMN.get().defaultBlockState(),'g',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_EAST_STRAIGHT,'m',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_WEST_STRAIGHT,'n',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_NORTH_STRAIGHT,'u',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_BOTTOM_SOUTH_STRAIGHT,'v',false)

            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_EAST_STRAIGHT,'M',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_WEST_STRAIGHT,'N',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_NORTH_STRAIGHT,'U',false)
            .addBlock(Structures.BlockStates.SOLAR_STONE_STAIRS_TOP_SOUTH_STRAIGHT,'V',false)

            .addBlock(BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState(),'f',false)
            .addBlock(BlocksRegistry.EXPLOSION_BLOCKER.get().defaultBlockState(),'b',false)

            .addStruct(Structures.EXPLOSION_BLOCKER)
            .addMainBlock(BlocksRegistry.EXPLOSION_BLOCKER.get().defaultBlockState())
            .setStructName("solarforge_struct.explosion_blocker")
    )),
    ;



    public static Map<String,Multiblock> MULTIBLOCKS = Map.ofEntries(
            Map.entry("infuser_tier_one", INFUSER_TIER_FIRST.a),
            Map.entry("infuser_tier_two", INFUSER_TIER_RUNIC_ENERGY.a),
            Map.entry("infuser_tier_three", INFUSER_TIER_SOLAR_ENERGY.a),
            Map.entry("generator", SOLAR_ENERGY_GENERATOR.a),
            Map.entry("healer", AURA_HEALER.a),
            Map.entry("mortar", SOLAR_MORTAR.a),
            Map.entry("lesser_core", SOLAR_CORE.a),
            Map.entry("dim_portal",RADIANT_LAND_PORTAL.a),
            Map.entry("zap_turret", ZAP_TURRET.a),
            Map.entry("pylon", RUNIC_ENERGY_PYLON.a),
            Map.entry("explosion_blocker", EXPLOSION_BLOCKER.a)
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
            RADIANT_LAND_PORTAL,
    };





}
class Structures{

    public static String[][] EXPLOSION_BLOCKER = {
            {
                    " fff ",
                    "fffff",
                    "fffff",
                    "fffff",
                    " fff "
            },
            {
                    " v v ",
                    "mg gn",
                    "     ",
                    "mg gn",
                    " u u "
            },
            {
                    "     ",
                    " g g ",
                    "     ",
                    " g g ",
                    "     "
            },
            {
                    "     ",
                    " gVg ",
                    " MfN ",
                    " gUg ",
                    "     "
            },
            {
                    "     ",
                    " qeq ",
                    " efe ",
                    " qeq ",
                    "     "
            },
            {
                    "     ",
                    " g g ",
                    "  b  ",
                    " g g ",
                    "     "
            },
            {
                    "     ",
                    " qeq ",
                    " efe ",
                    " qeq ",
                    "     "
            },
            {
                    "     ",
                    "  v  ",
                    " mfn ",
                    "  u  ",
                    "     "
            },
    };

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
                    "  c  ",
                    "     ",
                    "c   c",
                    "     ",
                    "  c  "
            },
            {
                    "  N  ",
                    "     ",
                    "N   N",
                    "     ",
                    "  N  "
            },
            {
                    " TlD ",
                    "H   H",
                    "l   l",
                    "G   G",
                    " TlD "
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
            },
            {
                    "     ",
                    "     ",
                    "     ",
                    "     ",
                    "     "
            },
            {
                    "     ",
                    "     ",
                    "  p  ",
                    "     ",
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

    public static String[][] INFUSER_TIER_1 = {
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
                    "  D   B   D  ",
                    "D  F     F  D",
                    "             ",
                    "             ",
                    "D B   I   B D",
                    "             ",
                    "             ",
                    "D  F     F  D",
                    "  D   B   D  ",
                    "             ",
                    "D  D  D  D  D",
            },
            {
                    "D  M  D  M  D",
                    "             ",
                    "  M   F   M  ",
                    "M           M",
                    "             ",
                    "             ",
                    "D F       F D",
                    "             ",
                    "             ",
                    "M           M",
                    "  M   F   M  ",
                    "             ",
                    "D  M  D  M  D",
            },
            {
                    "D  D  D  D  D",
                    "             ",
                    "  D       D  ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "  D       D  ",
                    "             ",
                    "D  D  D  D  D",
            },
            {
                    "D  D  D  D  D",
                    "             ",
                    "  C       C  ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "             ",
                    "             ",
                    "D           D",
                    "  C       C  ",
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

    public static String[][] INFUSER_TIER_2 = {
            {
                    "      B     B      ",
                    "     BBB   BBB     ",
                    "    BBBBB BBBBB    ",
                    "   BBBBBBBBBBBBB   ",
                    "  BBBBBBBBBBBBBBB  ",
                    " BBBBBBBBBBBBBBBBB ",
                    "BBBBBBBBBBBBBBBBBBB",
                    " BBBBBBBBBBBBBBBBB ",
                    "  BBBBBBBBBBBBBBB  ",
                    "   BBBBBBBBBBBBB   ",
                    "  BBBBBBBBBBBBBBB  ",
                    " BBBBBBBBBBBBBBBBB ",
                    "BBBBBBBBBBBBBBBBBBB",
                    " BBBBBBBBBBBBBBBBB ",
                    "  BBBBBBBBBBBBBBB  ",
                    "   BBBBBBBBBBBBB   ",
                    "    BBBBB BBBBB    ",
                    "     BBB   BBB     ",
                    "      B     B      ",
            },
            {
                    "     BCB   BCB     ",
                    "     BcB   BcB     ",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "BB   D   B   D   BB",
                    "Cb D  F     F  D aC",
                    "BB               BB",
                    "                   ",
                    "   D B   I   B D   ",
                    "                   ",
                    "BB               BB",
                    "Cb D  F     F  D aC",
                    "BB   D   B   D   BB",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "     BdB   BdB     ",
                    "     BCB   BCB     ",
            },
            {
                    "      D     D      ",
                    "                   ",
                    "                   ",
                    "   D  M  D  M  D   ",
                    "                   ",
                    "     M   F   M     ",
                    "D  M           M  D",
                    "                   ",
                    "                   ",
                    "   D F       F D   ",
                    "                   ",
                    "                   ",
                    "D  M           M  D",
                    "     M   F   M     ",
                    "                   ",
                    "   D  M  D  M  D   ",
                    "                   ",
                    "                   ",
                    "      D     D      ",
            },
            {
                    "      l     k      ",
                    "                   ",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "     D       D     ",
                    "z  D           D  j",
                    "                   ",
                    "                   ",
                    "   D           D   ",
                    "                   ",
                    "                   ",
                    "x  D           D  h",
                    "     D       D     ",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "                   ",
                    "      f     g      ",
            },
            {
                    "      o     o      ",
                    "                   ",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "     C       C     ",
                    "o  D           D  o",
                    "                   ",
                    "                   ",
                    "   D           D   ",
                    "                   ",
                    "                   ",
                    "o  D           D  o",
                    "     C       C     ",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "                   ",
                    "      o     o      ",
            },
            {
                    "                   ",
                    "                   ",
                    "                   ",
                    "   D  GLLCLLG  D   ",
                    "                   ",
                    "                   ",
                    "   G           G   ",
                    "   K           H   ",
                    "   K           H   ",
                    "   C           C   ",
                    "   K           H   ",
                    "   K           H   ",
                    "   G           G   ",
                    "                   ",
                    "                   ",
                    "   D  GJJCJJG  D   ",
                    "                   ",
                    "                   ",
                    "                   ",
            },
            {
                    "                   ",
                    "                   ",
                    "                   ",
                    "   CLLC     CLLC   ",
                    "   K           H   ",
                    "   K           H   ",
                    "   C           C   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "   C           C   ",
                    "   K           H   ",
                    "   K           H   ",
                    "   CJJC     CJJC   ",
                    "                   ",
                    "                   ",
                    "                   ",
            },
            {
                    "                   ",
                    "                   ",
                    "                   ",
                    "   vb         av   ",
                    "   c           c   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "   d           d   ",
                    "   vb         av   ",
                    "                   ",
                    "                   ",
                    "                   ",
            },

    };

    public static String[][] INFUSER_TIER_3 = {
            {
                    "      B     B      ",
                    "     BBB   BBB     ",
                    "    BBBBBBBBBBB    ",
                    "   BBBBBBBBBBBBB   ",
                    "  BBBBBBBBBBBBBBB  ",
                    " BBBBBBBBBBBBBBBBB ",
                    "BBBBBBBBBBBBBBBBBBB",
                    " BBBBBBBBBBBBBBBBB ",
                    "  BBBBBBBBBBBBBBB  ",
                    "  BBBBBBBBBBBBBBB  ",
                    "  BBBBBBBBBBBBBBB  ",
                    " BBBBBBBBBBBBBBBBB ",
                    "BBBBBBBBBBBBBBBBBBB",
                    " BBBBBBBBBBBBBBBBB ",
                    "  BBBBBBBBBBBBBBB  ",
                    "   BBBBBBBBBBBBB   ",
                    "    BBBBBBBBBBB    ",
                    "     BBB   BBB     ",
                    "      B     B      ",
            },
            {
                    "     BCB   BCB     ",
                    "     BcB   BcB     ",
                    "        BqB        ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "BB   D   B   D   BB",
                    "Cb D  F     F  D aC",
                    "BB               BB",
                    "  B             B  ",
                    "  qD B   I   B Dq  ",
                    "  B             B  ",
                    "BB               BB",
                    "Cb D  F     F  D aC",
                    "BB   D   B   D   BB",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "        BqB        ",
                    "     BdB   BdB     ",
                    "     BCB   BCB     ",
            },
            {
                    "      D     D      ",
                    "                   ",
                    "        BqB        ",
                    "   D  M  D  M  D   ",
                    "                   ",
                    "     M   F   M     ",
                    "D  M           M  D",
                    "                   ",
                    "  B             B  ",
                    "  qD F       F Dq  ",
                    "  B             B  ",
                    "                   ",
                    "D  M           M  D",
                    "     M   F   M     ",
                    "                   ",
                    "   D  M  D  M  D   ",
                    "        BqB        ",
                    "                   ",
                    "      D     D      ",
            },
            {
                    "      l     k      ",
                    "                   ",
                    "        BqB        ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "     D       D     ",
                    "z  D           D  j",
                    "                   ",
                    "  B             B  ",
                    "  qD           Dq  ",
                    "  B             B  ",
                    "                   ",
                    "x  D           D  h",
                    "     D       D     ",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "        BqB        ",
                    "                   ",
                    "      f     g      ",
            },
            {
                    "      o     o      ",
                    "                   ",
                    "        BqB        ",
                    "   D  D  D  D  D   ",
                    "                   ",
                    "     C       C     ",
                    "o  D           D  o",
                    "                   ",
                    "  B             B  ",
                    "  qD           Dq  ",
                    "  B             B  ",
                    "                   ",
                    "o  D           D  o",
                    "     C       C     ",
                    "                   ",
                    "   D  D  D  D  D   ",
                    "        BqB        ",
                    "                   ",
                    "      o     o      ",
            },
            {
                    "                   ",
                    "                   ",
                    "        BqB        ",
                    "   D  GLLCLLG  D   ",
                    "                   ",
                    "                   ",
                    "   G           G   ",
                    "   K           H   ",
                    "  BK           HB  ",
                    "  qC           Cq  ",
                    "  BK           HB  ",
                    "   K           H   ",
                    "   G           G   ",
                    "                   ",
                    "                   ",
                    "   D  GJJCJJG  D   ",
                    "        BqB        ",
                    "                   ",
                    "                   ",
            },
            {
                    "                   ",
                    "                   ",
                    "                   ",
                    "   CLLC dqd CLLC   ",
                    "   K           H   ",
                    "   K           H   ",
                    "   C           C   ",
                    "                   ",
                    "   a           b   ",
                    "   q           q   ",
                    "   a           b   ",
                    "                   ",
                    "   C           C   ",
                    "   K           H   ",
                    "   K           H   ",
                    "   CJJC cqc CJJC   ",
                    "                   ",
                    "                   ",
                    "                   ",
            },
            {
                    "                   ",
                    "                   ",
                    "                   ",
                    "   vb         av   ",
                    "   c    BqB    c   ",
                    "        BqB        ",
                    "                   ",
                    "         V         ",
                    "    BB  BCB  BB    ",
                    "    qq mC Cn qq    ",
                    "    BB  BCB  BB    ",
                    "         u         ",
                    "                   ",
                    "        BqB        ",
                    "   d    BqB    d   ",
                    "   vb         av   ",
                    "                   ",
                    "                   ",
                    "                   ",
            },
            {
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "        BqB        ",
                    "        e e        ",
                    "      BeB BeB      ",
                    "      q     q      ",
                    "      BeB BeB      ",
                    "        e e        ",
                    "        BqB        ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
            },
            {
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "         d         ",
                    "        BCB        ",
                    "       aC Cb       ",
                    "        BCB        ",
                    "         c         ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
                    "                   ",
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
