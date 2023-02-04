package com.finderfeed.solarcraft.helpers.multiblock;

import com.finderfeed.solarcraft.registries.Tags;

import java.util.HashMap;
import java.util.Map;

public class Multiblocks {

    public static final Map<String,MultiblockStructure> STRUCTURES = new HashMap<>();

    public static MultiblockStructure INFUSER_TIER_ONE = register(MultiblockStructure.Builder.start()
            .setId("infuser_tier_one")
            .setPattern(new String[][]{{"qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq"}, {"weeweeweeweew", "eeeeeeeeeeeee", "eereeereeeree", "weeeeeeeeeeew", "eeeereeereeee", "eeeeeeeeeeeee", "wereeeteeerew", "eeeeeeeeeeeee", "eeeereeereeee", "weeeeeeeeeeew", "eereeereeeree", "eeeeeeeeeeeee", "weeweeweeweew"}, {"weeMeeMeeMeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "MeeeeeeeeeeeM", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "MeeeeeeeeeeeM", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "MeeeeeeeeeeeM", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeMeeMeeMeew"}, {"weeweeweeweew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeweeweeweew"}, {"weeweeweeweew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeweeweeweew"}, {"weeyuuiuuyeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "yeeeeeeeeeeey", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "ieeeeeeeeeeei", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "yeeeeeeeeeeey", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeyaaiaayeew"}, {"iuuieeeeeiuui", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "ieeeeeeeeeeei", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "ieeeeeeeeeeei", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "iaaieeeeeiaai"}})
            .setMainChar('t')
            .put('q',"solarcraft:magistone_bricks")
            .put('u',"solarcraft:magistone_ray[facing=north]")
            .put('o',"solarcraft:magistone_ray[facing=west]")
            .put('i',"solarcraft:chiseled_magistone")
            .put('r',"solarcraft:solar_forge_infusion_pool")
            .put('y',"minecraft:gold_block")
            .put('w',"solarcraft:magistone_column")
            .put('p',"solarcraft:magistone_ray[facing=east]")
            .put('a',"solarcraft:magistone_ray[facing=south]")
            .put('e',"minecraft:air")
            .put('t',"solarcraft:solar_infuser")
            .put('M',"solarcraft:magistone_column")
            .checkTag('M',Tags.CATALYST)
            .build());

    public static final MultiblockStructure INFUSER_TIER_TWO = register(MultiblockStructure.Builder.start()
            .setId("infuser_tier_two")
            .setPattern(new String[][]{{"qqqqqqwqqqqqwqqqqqq", "qqqqqwwwqqqwwwqqqqq", "qqqqwwwwwqwwwwwqqqq", "qqqwwwwwwwwwwwwwqqq", "qqwwwwwwwwwwwwwwwqq", "qwwwwwwwwwwwwwwwwwq", "wwwwwwwwwwwwwwwwwww", "qwwwwwwwwwwwwwwwwwq", "qqwwwwwwwwwwwwwwwqq", "qqqwwwwwwwwwwwwwqqq", "qqwwwwwwwwwwwwwwwqq", "qwwwwwwwwwwwwwwwwwq", "wwwwwwwwwwwwwwwwwww", "qwwwwwwwwwwwwwwwwwq", "qqwwwwwwwwwwwwwwwqq", "qqqwwwwwwwwwwwwwqqq", "qqqqwwwwwqwwwwwqqqq", "qqqqqwwwqqqwwwqqqqq", "qqqqqqwqqqqqwqqqqqq"}, {"qqqqqwewqqqwewqqqqq", "qqqqqwrwqqqwrwqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqtqqtqqtqqtqqq", "qqqqqqqqqqqqqqqqqqq", "wwqqqyqqqyqqqyqqqww", "euqtqqqqqqqqqqqtqie", "wwqqqqqyqqqyqqqqqww", "qqqqqqqqqqqqqqqqqqq", "qqqtqyqqqoqqqyqtqqq", "qqqqqqqqqqqqqqqqqqq", "wwqqqqqyqqqyqqqqqww", "euqtqqqqqqqqqqqtqie", "wwqqqyqqqyqqqyqqqww", "qqqqqqqqqqqqqqqqqqq", "qqqtqqtqqtqqtqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqwpwqqqwpwqqqqq", "qqqqqwewqqqwewqqqqq"},
                    {"qqqqqqtqqqqqtqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqtqqMqqMqqMqqtqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "tqqMqqqqqqqqqqqMqqt",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqMqqqqqqqqqqqMqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "tqqMqqqqqqqqqqqMqqt",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqtqqMqqMqqMqqtqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqtqqqqqtqqqqqq"}, {"qqqqqqaqqqqqsqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqtqqtqqtqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "dqqtqqqqqqqqqqqtqqf", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqqqqqqqqqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "gqqtqqqqqqqqqqqtqqh", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqtqqtqqtqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqjqqqqqkqqqqqq"}, {"qqqqqqlqqqqqlqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqtqqtqqtqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "lqqtqqqqqqqqqqqtqql", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqqqqqqqqqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "lqqtqqqqqqqqqqqtqql", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqtqqtqqtqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqlqqqqqlqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqzxxexxzqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqzqqqqqqqqqqqzqqq", "qqqcqqqqqqqqqqqvqqq", "qqqcqqqqqqqqqqqvqqq", "qqqeqqqqqqqqqqqeqqq", "qqqcqqqqqqqqqqqvqqq", "qqqcqqqqqqqqqqqvqqq", "qqqzqqqqqqqqqqqzqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqtqqzbbebbzqqtqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqexxeqqqqqexxeqqq", "qqqcqqqqqqqqqqqvqqq", "qqqcqqqqqqqqqqqvqqq", "qqqeqqqqqqqqqqqeqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqeqqqqqqqqqqqeqqq", "qqqcqqqqqqqqqqqvqqq", "qqqcqqqqqqqqqqqvqqq", "qqqebbeqqqqqebbeqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqnuqqqqqqqqqinqqq", "qqqrqqqqqqqqqqqrqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqpqqqqqqqqqqqpqqq", "qqqnuqqqqqqqqqinqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}})
            .setMainChar('o')
            .put('f',"solarcraft:urba_rune_block")
            .put('k',"solarcraft:kelda_rune_block")
            .put('t',"solarcraft:magistone_column")
            .put('l',"solarcraft:repeater")
            .put('v',"solarcraft:magistone_ray[facing=east]")
            .put('o',"solarcraft:solar_infuser")
            .put('p',"solarcraft:magistone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarcraft:magistone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('j',"solarcraft:ardo_rune_block")
            .put('x',"solarcraft:magistone_ray[facing=north]")
            .put('h',"solarcraft:zeta_rune_block")
            .put('c',"solarcraft:magistone_ray[facing=west]")
            .put('n',"minecraft:diamond_block")
            .put('a',"solarcraft:tera_rune_block")
            .put('y',"solarcraft:solar_forge_infusion_pool")
            .put('g',"solarcraft:giro_rune_block")
            .put('q',"minecraft:air")
            .put('w',"solarcraft:magistone_bricks")
            .put('d',"solarcraft:ultima_rune_block")
            .put('s',"solarcraft:fira_rune_block")
            .put('i',"solarcraft:magistone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarcraft:chiseled_magistone")
            .put('z',"minecraft:gold_block")
            .put('b',"solarcraft:magistone_ray[facing=south]")
            .put('u',"solarcraft:magistone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('M',"solarcraft:magistone_column")
            .checkTag('M',Tags.CATALYST)

            .build());

    public static final MultiblockStructure INFUSER_TIER_THREE = register(MultiblockStructure.Builder.start()
            .setId("infuser_tier_three")
            .setPattern(new String[][]{{"qqqqqqwqqqqqwqqqqqq", "qqqqqwwwqqqwwwqqqqq", "qqqqwwwwwwwwwwwqqqq", "qqqwwwwwwwwwwwwwqqq", "qqwwwwwwwwwwwwwwwqq", "qwwwwwwwwwwwwwwwwwq", "wwwwwwwwwwwwwwwwwww", "qwwwwwwwwwwwwwwwwwq", "qqwwwwwwwwwwwwwwwqq", "qqwwwwwwwwwwwwwwwqq", "qqwwwwwwwwwwwwwwwqq", "qwwwwwwwwwwwwwwwwwq", "wwwwwwwwwwwwwwwwwww", "qwwwwwwwwwwwwwwwwwq", "qqwwwwwwwwwwwwwwwqq", "qqqwwwwwwwwwwwwwqqq", "qqqqwwwwwwwwwwwqqqq", "qqqqqwwwqqqwwwqqqqq", "qqqqqqwqqqqqwqqqqqq"}, {"qqqqqwewqqqwewqqqqq", "qqqqqwrwqqqwrwqqqqq", "qqqqqqqqwtwqqqqqqqq", "qqqyqqyqqyqqyqqyqqq", "qqqqqqqqqqqqqqqqqqq", "wwqqquqqquqqquqqqww", "eiqyqqqqqqqqqqqyqoe", "wwqqqqquqqquqqqqqww", "qqwqqqqqqqqqqqqqwqq", "qqtyquqqqpqqquqytqq", "qqwqqqqqqqqqqqqqwqq", "wwqqqqquqqquqqqqqww", "eiqyqqqqqqqqqqqyqoe", "wwqqquqqquqqquqqqww", "qqqqqqqqqqqqqqqqqqq", "qqqyqqyqqyqqyqqyqqq", "qqqqqqqqwtwqqqqqqqq", "qqqqqwawqqqwawqqqqq", "qqqqqwewqqqwewqqqqq"},
                    {"qqqqqqyqqqqqyqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqwtwqqqqqqqq",
                            "qqqyqqMqqMqqMqqyqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "yqqMqqqqqqqqqqqMqqy",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqwqqqqqqqqqqqqqwqq",
                            "qqtMqqqqqqqqqqqMtqq",
                            "qqwqqqqqqqqqqqqqwqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "yqqMqqqqqqqqqqqMqqy",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqyqqMqqMqqMqqyqqq",
                            "qqqqqqqqwtwqqqqqqqq",
                            "qqqqqqqqqqqqqqqqqqq",
                            "qqqqqqyqqqqqyqqqqqq"}, {"qqqqqqsqqqqqdqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqwtwqqqqqqqq", "qqqyqqyqqyqqyqqyqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "fqqyqqqqqqqqqqqyqqg", "qqqqqqqqqqqqqqqqqqq", "qqwqqqqqqqqqqqqqwqq", "qqtyqqqqqqqqqqqytqq", "qqwqqqqqqqqqqqqqwqq", "qqqqqqqqqqqqqqqqqqq", "hqqyqqqqqqqqqqqyqqj", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqyqqyqqyqqyqqyqqq", "qqqqqqqqwtwqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqkqqqqqlqqqqqq"}, {"qqqqqqzqqqqqzqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqwtwqqqqqqqq", "qqqyqqyqqyqqyqqyqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "zqqyqqqqqqqqqqqyqqz", "qqqqqqqqqqqqqqqqqqq", "qqwqqqqqqqqqqqqqwqq", "qqtyqqqqqqqqqqqytqq", "qqwqqqqqqqqqqqqqwqq", "qqqqqqqqqqqqqqqqqqq", "zqqyqqqqqqqqqqqyqqz", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqyqqyqqyqqyqqyqqq", "qqqqqqqqwtwqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqzqqqqqzqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqwtwqqqqqqqq", "qqqyqqxcceccxqqyqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqxqqqqqqqqqqqxqqq", "qqqvqqqqqqqqqqqbqqq", "qqwvqqqqqqqqqqqbwqq", "qqteqqqqqqqqqqqetqq", "qqwvqqqqqqqqqqqbwqq", "qqqvqqqqqqqqqqqbqqq", "qqqxqqqqqqqqqqqxqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqyqqxnnennxqqyqqq", "qqqqqqqqwtwqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqecceqataqecceqqq", "qqqvqqqqqqqqqqqbqqq", "qqqvqqqqqqqqqqqbqqq", "qqqeqqqqqqqqqqqeqqq", "qqqqqqqqqqqqqqqqqqq", "qqqoqqqqqqqqqqqiqqq", "qqqtqqqqqqqqqqqtqqq", "qqqoqqqqqqqqqqqiqqq", "qqqqqqqqqqqqqqqqqqq", "qqqeqqqqqqqqqqqeqqq", "qqqvqqqqqqqqqqqbqqq", "qqqvqqqqqqqqqqqbqqq", "qqqenneqrtrqenneqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqmiqqqqqqqqqomqqq", "qqqrqqqqwtwqqqqrqqq", "qqqqqqqqwtwqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqQqqqqqqqqq", "qqqqwwqqwewqqwwqqqq", "qqqqttqWeqeEqttqqqq", "qqqqwwqqwewqqwwqqqq", "qqqqqqqqqRqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqwtwqqqqqqqq", "qqqaqqqqwtwqqqqaqqq", "qqqmiqqqqqqqqqomqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqwtwqqqqqqqq", "qqqqqqqqTqTqqqqqqqq", "qqqqqqwTwqwTwqqqqqq", "qqqqqqtqqqqqtqqqqqq", "qqqqqqwTwqwTwqqqqqq", "qqqqqqqqTqTqqqqqqqq", "qqqqqqqqwtwqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqaqqqqqqqqq", "qqqqqqqqwewqqqqqqqq", "qqqqqqqoeqeiqqqqqqq", "qqqqqqqqwewqqqqqqqq", "qqqqqqqqqrqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq"}})
            .setMainChar('p')
            .put('g',"solarcraft:urba_rune_block")
            .put('l',"solarcraft:kelda_rune_block")
            .put('y',"solarcraft:magistone_column")
            .put('t',"minecraft:obsidian")
            .put('z',"solarcraft:repeater")
            .put('b',"solarcraft:magistone_ray[facing=east]")
            .put('p',"solarcraft:solar_infuser")
            .put('a',"solarcraft:magistone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarcraft:magistone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('k',"solarcraft:ardo_rune_block")
            .put('c',"solarcraft:magistone_ray[facing=north]")
            .put('E',"solarcraft:magistone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('j',"solarcraft:zeta_rune_block")
            .put('v',"solarcraft:magistone_ray[facing=west]")
            .put('m',"minecraft:diamond_block")
            .put('s',"solarcraft:tera_rune_block")
            .put('u',"solarcraft:solar_forge_infusion_pool")
            .put('h',"solarcraft:giro_rune_block")
            .put('T',"solarcraft:energized_stone")
            .put('q',"minecraft:air")
            .put('w',"solarcraft:magistone_bricks")
            .put('f',"solarcraft:ultima_rune_block")
            .put('Q',"solarcraft:magistone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('d',"solarcraft:fira_rune_block")
            .put('o',"solarcraft:magistone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('W',"solarcraft:magistone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('e',"solarcraft:chiseled_magistone")
            .put('x',"minecraft:gold_block")
            .put('n',"solarcraft:magistone_ray[facing=south]")
            .put('R',"solarcraft:magistone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('i',"solarcraft:magistone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('M',"solarcraft:magistone_column")
            .checkTag('M',Tags.CATALYST)
            .build());

    public static final MultiblockStructure ENERGY_GENERATOR = register(MultiblockStructure.Builder.start()
            .setId("generator")
            .setPattern(new String[][]{{"qwwwq", "errrt", "errrt", "errrt", "qyyyq"}, {"uiopu", "aoooa", "ooqoo", "sooos", "uiopu"}, {"uooou", "ooooo", "oodoo", "ooooo", "uooou"}, {"ufogu", "hojoh", "ojojo", "kojok", "ufogu"}, {"qwwwq", "errrt", "erort", "errrt", "qyyyq"}, {"ooooo", "olllo", "ololo", "olllo", "ooooo"}})
            .setMainChar('d')
            .put('i',"solarcraft:magistone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('h',"solarcraft:magistone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('r',"solarcraft:magistone_bricks")
            .put('t',"solarcraft:magistone_ray[facing=east]")
            .put('w',"solarcraft:magistone_ray[facing=north]")
            .put('y',"solarcraft:magistone_ray[facing=south]")
            .put('a',"solarcraft:magistone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('d',"solarcraft:solar_energy_generator")
            .put('f',"solarcraft:magistone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('o',"minecraft:air")
            .put('l',"solarcraft:magistone_slab[type=bottom,waterlogged=false]")
            .put('s',"solarcraft:magistone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('q',"solarcraft:chiseled_magistone")
            .put('g',"solarcraft:magistone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('e',"solarcraft:magistone_ray[facing=west]")
            .put('k',"solarcraft:magistone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('u',"solarcraft:magistone_column")
            .put('j',"solarcraft:magistone_slab[type=top,waterlogged=false]")
            .put('p',"solarcraft:magistone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .build());

    public static final MultiblockStructure AURA_HEALER = register(MultiblockStructure.Builder.start()
            .setId("healer")
            .setPattern(new String[][]{{"qqq", "qqq", "qqq"}, {"wew", "ere", "wew"}, {"tet", "eye", "tet"}})
            .setMainChar('y')
            .put('q',"solarcraft:solar_stone_bricks")
            .put('r',"solarcraft:chiseled_solar_stone")
            .put('w',"solarcraft:solar_stone_collumn")
            .put('e',"minecraft:air")
            .put('t',"solarcraft:illidium_block")
            .put('y',"solarcraft:aura_healer_block")
            .build());

    public static final MultiblockStructure SOLAR_MORTAR = register(MultiblockStructure.Builder.start()
            .setId("mortar")
            .setPattern(new String[][]{{"qqqqq", "qqqqq", "qqqqq", "qqqqq", "qqqqq"}, {"weeew", "rttty", "rttty", "rttty", "wuuuw"}, {"iqtti", "ttttq", "ttttt", "qtttt", "ittqi"}, {"itqti", "ttttt", "qtttq", "ttttt", "itqti"}, {"ittqi", "qtttt", "ttttt", "ttttq", "iqtti"}, {"weeew", "rttty", "rttty", "rttty", "wuuuw"}, {"iqtti", "ttttq", "ttttt", "qtttt", "ittqi"}, {"itqti", "ttttt", "qtttq", "ttttt", "itqti"}, {"ittqi", "qtttt", "ttttt", "ttttq", "iqtti"}, {"weeew", "rttty", "rttty", "rttty", "wuuuw"}, {"tqqqt", "qqqqq", "qqtqq", "qqqqq", "tqqqt"}, {"ttqtt", "tqqqt", "qqtqq", "tqqqt", "ttqtt"}, {"ttttt", "ttitt", "tioit", "ttitt", "ttttt"}, {"ttttt", "ttwtt", "twtwt", "ttwtt", "ttttt"}})
            .setMainChar('o')
            .put('r',"solarcraft:magistone_ray[facing=west]")
            .put('w',"solarcraft:chiseled_magistone")
            .put('e',"solarcraft:magistone_ray[facing=north]")
            .put('i',"solarcraft:magistone_column")
            .put('q',"solarcraft:magistone_bricks")
            .put('y',"solarcraft:magistone_ray[facing=east]")
            .put('u',"solarcraft:magistone_ray[facing=south]")
            .put('t',"minecraft:air")
            .put('o',"solarcraft:solar_mortar_block")
            .build());

    public static final MultiblockStructure SOLAR_CORE = register(MultiblockStructure.Builder.start()
            .setId("solar_core")
            .setPattern(new String[][]{{"qwwwq", "wwwww", "wwwww", "wwwww", "qwwwq"}, {"errre", "rtytr", "ruior", "rtptr", "errre"}, {"errre", "rrrrr", "rrrrr", "rrrrr", "errre"}, {"arrra", "rrrrr", "rrsrr", "rrrrr", "arrra"}, {"errre", "rrrrr", "rrrrr", "rrrrr", "errre"}, {"errre", "rdfdr", "rgihr", "rdjdr", "errre"}, {"qwwwq", "wwwww", "wwwww", "wwwww", "qwwwq"}})
            .setMainChar('s')
            .put('t',"solarcraft:magistone_slab[type=bottom,waterlogged=false]")
            .put('r',"minecraft:air")
            .put('j',"solarcraft:magistone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('p',"solarcraft:magistone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarcraft:magistone_column")
            .put('i',"solarcraft:illidium_block")
            .put('d',"solarcraft:magistone_slab[type=top,waterlogged=false]")
            .put('s',"solarcraft:solar_core_block")
            .put('u',"solarcraft:magistone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('g',"solarcraft:magistone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('a',"solarcraft:algadium_block")
            .put('f',"solarcraft:magistone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('w',"solarcraft:magistone_bricks")
            .put('o',"solarcraft:magistone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('y',"solarcraft:magistone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('h',"solarcraft:magistone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('q',"solarcraft:chiseled_magistone")
            .build());

    public static final MultiblockStructure RADIANT_LAND_PORTAL = register(MultiblockStructure.Builder.start()
            .setId("dim_portal")
            .setPattern(new String[][]{{"qqqqqweeeeeeeeeeerqqqqq", "qqqqqtyyyyyyyyyyyuqqqqq", "qqieeyyyyyyyyyyyyyeerqq", "qqtyyyyyyyyyyyyyyyyyuqq", "qqtyyyyyyyyyyyyyyyyyuqq", "weyyyyyyyyyoyyyyyyyyyep", "tyyyyyoyyyyoyyyyoyyyyyu", "tyyyyyyoyyyoyyyoyyyyyyu", "tyyyyyyyoyyoyyoyyyyyyyu", "tyyyyyyyyoaoaoyyyyyyyyu", "tyyyyyyyyasdfayyyyyyyyu", "tyyyyoooooghjoooooyyyyu", "tyyyyyyyyaklzayyyyyyyyu", "tyyyyyyyyoaoaoyyyyyyyyu", "tyyyyyyyoyyoyyoyyyyyyyu", "tyyyyyyoyyyoyyyoyyyyyyu", "tyyyyyoyyyyoyyyyoyyyyyu", "xcyyyyyyyyyoyyyyyyyyycv", "qqtyyyyyyyyyyyyyyyyyuqq", "qqtyyyyyyyyyyyyyyyyyuqq", "qqxccyyyyyyyyyyyyyccvqq", "qqqqqtyyyyyyyyyyyuqqqqq", "qqqqqxcccccccccccbqqqqq"}, {"qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqoeoqqqqqqqqqq", "qqqqqqqqqqtyuqqqqqqqqqq", "qqqqoeoqqqocoqqqoeoqqqq", "qqqqtyuqqqqqqqqqtyuqqqq", "qqqqocoqqqqqqqqqocoqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqoeoqqqqqqqqqqqqqoeoqq", "qqtyuqqqqqqqqqqqqqtyuqq", "qqocoqqqqqqqqqqqqqocoqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqoeoqqqqqqqqqoeoqqqq", "qqqqtyuqqqqqqqqqtyuqqqq", "qqqqocoqqqoeoqqqocoqqqq", "qqqqqqqqqqtyuqqqqqqqqqq", "qqqqqqqqqqocoqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqnqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqnqqqqqqqqqqqnqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqnqqqqqqqqqqqqqqqnqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqnqqqqqqqqqqqnqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqnqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqmqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqQqqqqqqqqqqqWqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqEqqqqqqqqqqqqqqqRqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqTqqqqqqqqqqqYqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqUqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqnqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqnqqqqqqqqqqqnqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqnqqqqqqqqqqqqqqqnqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqnqqqqqqqqqqqnqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqnqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqoIoqqqqqqqqqq", "qqqqqqqqqqOyPqqqqqqqqqq", "qqqqoIoqqqoAoqqqoIoqqqq", "qqqqOyPqqqqqqqqqOyPqqqq", "qqqqoAoqqqqqqqqqoAoqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqoIoqqqqqqqqqqqqqoIoqq", "qqOyPqqqqqqqqqqqqqOyPqq", "qqoAoqqqqqqqqqqqqqoAoqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqoIoqqqqqqqqqoIoqqqq", "qqqqOyPqqqqqqqqqOyPqqqq", "qqqqoAoqqqoIoqqqoAoqqqq", "qqqqqqqqqqOyPqqqqqqqqqq", "qqqqqqqqqqoAoqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqSyDySqqqqqqqqq", "qqqqqqSyyyyyyyyySqqqqqq", "qqqqDyyyyyyyyyyyyyDqqqq", "qqqqyyyyyyyoyyyyyyyqqqq", "qqqSyyyyyyyoyyyyyyySqqq", "qqqyyyyoyyyoyyyoyyyyqqq", "qqqyyyyyoyyoyyoyyyyyqqq", "qqSyyyyyyoaoaoyyyyyySqq", "qqyyyyyyyaFGHayyyyyyyqq", "qqDyyoooooJoKoooooyyDqq", "qqyyyyyyyaLZXayyyyyyyqq", "qqSyyyyyyoaoaoyyyyyySqq", "qqqyyyyyoyyoyyoyyyyyqqq", "qqqyyyyoyyyoyyyoyyyyqqq", "qqqSyyyyyyyoyyyyyyySqqq", "qqqqyyyyyyyoyyyyyyyqqqq", "qqqqDyyyyyyyyyyyyyDqqqq", "qqqqqqSyyyyyyyyySqqqqqq", "qqqqqqqqqSyDySqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq"}, {"qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqSSSqqqqqqqqqq", "qqqqqqqqSSyyySSqqqqqqqq", "qqqqqqqSyyyyyyySqqqqqqq", "qqqqqqSyyyyyyyyySqqqqqq", "qqqqqSyyyyyyyyyyySqqqqq", "qqqqSyyyyyyyyyyyyySqqqq", "qqqqSyyyyyyyyyyyyySqqqq", "qqqSyyyyyyyyyyyyyyySqqq", "qqqSyyyyyyyyyyyyyyySqqq", "qqqSyyyyyyyyyyyyyyySqqq", "qqqqSyyyyyyyyyyyyySqqqq", "qqqqSyyyyyyyyyyyyySqqqq", "qqqqqSyyyyyyyyyyySqqqqq", "qqqqqqSyyyyyyyyySqqqqqq", "qqqqqqqSyyyyyyySqqqqqqq", "qqqqqqqqSSyyySSqqqqqqqq", "qqqqqqqqqqSSSqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq"}})
            .setMainChar('h')
            .put('U',"solarcraft:zeta_rune_block")
            .put('q',"minecraft:air")
            .put('h',"solarcraft:dimension_core")
            .put('c',"solarcraft:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarcraft:solar_stone_stairs[facing=west,half=bottom,shape=outer_left,waterlogged=false]")
            .put('n',"solarcraft:solar_stone_collumn")
            .put('w',"solarcraft:solar_stone_stairs[facing=east,half=bottom,shape=outer_right,waterlogged=false]")
            .put('t',"solarcraft:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('l',"minecraft:polished_deepslate_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('v',"solarcraft:solar_stone_stairs[facing=north,half=bottom,shape=outer_left,waterlogged=false]")
            .put('z',"minecraft:polished_deepslate_stairs[facing=east,half=bottom,shape=inner_right,waterlogged=false]")
            .put('o',"minecraft:polished_deepslate")
            .put('X',"minecraft:polished_deepslate_stairs[facing=south,half=top,shape=inner_left,waterlogged=false]")
            .put('u',"solarcraft:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('f',"minecraft:polished_deepslate_stairs[facing=north,half=bottom,shape=inner_right,waterlogged=false]")
            .put('R',"solarcraft:giro_rune_block")
            .put('e',"solarcraft:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('p',"solarcraft:solar_stone_stairs[facing=south,half=bottom,shape=outer_right,waterlogged=false]")
            .put('P',"solarcraft:solar_stone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('Q',"solarcraft:kelda_rune_block")
            .put('j',"minecraft:polished_deepslate_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('S',"solarcraft:solar_stone_slab[type=bottom,waterlogged=false]")
            .put('A',"solarcraft:solar_stone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('T',"solarcraft:fira_rune_block")
            .put('F',"minecraft:polished_deepslate_stairs[facing=west,half=top,shape=inner_right,waterlogged=false]")
            .put('K',"minecraft:polished_deepslate_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('L',"minecraft:polished_deepslate_stairs[facing=south,half=top,shape=inner_right,waterlogged=false]")
            .put('a',"minecraft:glowstone")
            .put('W',"solarcraft:tera_rune_block")
            .put('d',"minecraft:polished_deepslate_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('i',"solarcraft:solar_stone_stairs[facing=south,half=bottom,shape=outer_left,waterlogged=false]")
            .put('G',"minecraft:polished_deepslate_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('s',"minecraft:polished_deepslate_stairs[facing=west,half=bottom,shape=inner_right,waterlogged=false]")
            .put('O',"solarcraft:solar_stone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('b',"solarcraft:solar_stone_stairs[facing=west,half=bottom,shape=outer_right,waterlogged=false]")
            .put('J',"minecraft:polished_deepslate_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('H',"minecraft:polished_deepslate_stairs[facing=north,half=top,shape=inner_right,waterlogged=false]")
            .put('Y',"solarcraft:ultima_rune_block")
            .put('g',"minecraft:polished_deepslate_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('Z',"minecraft:polished_deepslate_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('k',"minecraft:polished_deepslate_stairs[facing=south,half=bottom,shape=inner_right,waterlogged=false]")
            .put('E',"solarcraft:urba_rune_block")
            .put('m',"solarcraft:ardo_rune_block")
            .put('I',"solarcraft:solar_stone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('y',"solarcraft:solar_stone_bricks")
            .put('x',"solarcraft:solar_stone_stairs[facing=north,half=bottom,shape=outer_right,waterlogged=false]")
            .put('D',"solarcraft:chiseled_solar_stone")
            .build());


    public static final MultiblockStructure ZAP_TURRET = register(MultiblockStructure.Builder.start()
            .setId("zap_turret")
            .setPattern(new String[][]{{"qqq", "qqq", "qqq"}, {"wew", "rty", "wuw"}, {"www", "wtw", "www"}, {"www", "wiw", "www"}})
            .setMainChar('i')
            .put('y',"solarcraft:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarcraft:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('u',"solarcraft:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarcraft:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('i',"solarcraft:zap_turret_block")
            .put('t',"solarcraft:solar_stone_collumn")
            .put('q',"solarcraft:solar_stone_bricks")
            .put('w',"minecraft:air")
            .build());

    public static final MultiblockStructure PYLON = register(MultiblockStructure.Builder.start()
            .setId("pylon")
            .setPattern(new String[][]{{"qwwwq", "wwwww", "wweww", "wwwww", "qwwwq"}, {"qqrqq", "qqqqq", "rqqqr", "qqqqq", "qqrqq"}, {"qqtqq", "qqqqq", "tqqqt", "qqqqq", "qqtqq"}, {"qqrqq", "qqqqq", "rqqqr", "qqqqq", "qqrqq"}, {"qyuiq", "oqqqo", "uqqqu", "pqqqp", "qyuiq"}, {"qqaqq", "qqsqq", "dfegh", "qqsqq", "qqjqq"}, {"qqqqq", "qqaqq", "qdehq", "qqjqq", "qqqqq"}, {"qqqqq", "qqqqq", "qqqqq", "qqqqq", "qqqqq"}, {"qqqqq", "qqqqq", "qqkqq", "qqqqq", "qqqqq"}})
            .setMainChar('k')
            .put('h',"solarcraft:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('a',"solarcraft:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('k',"solarcraft:rune_energy_pylon")
            .put('j',"solarcraft:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarcraft:solar_stone_collumn")
            .put('f',"solarcraft:solar_stone_collumn_horizontal[facing=north]")
            .put('w',"solarcraft:solar_stone_bricks")
            .put('o',"solarcraft:solar_stone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('s',"solarcraft:solar_stone_collumn_horizontal[facing=east]")
            .put('u',"solarcraft:ardo_rune_block")
            .put('q',"minecraft:air")
            .put('t',"solarcraft:inscription_stone[type=none]")
            .put('d',"solarcraft:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarcraft:chiseled_solar_stone")
            .put('y',"solarcraft:solar_stone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('i',"solarcraft:solar_stone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('p',"solarcraft:solar_stone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('g',"solarcraft:solar_stone_collumn_horizontal[facing=south]")
            .checkTag('u',Tags.CATALYST)
            .checkTag('t',Tags.INSCRIPTION_STONE)
            .build());

    public static final MultiblockStructure EXPLOSION_BLOCKER = register(MultiblockStructure.Builder.start()
            .setId("explosion_blocker")
            .setPattern(new String[][]{{"qwwwq", "wwwww", "wwwww", "wwwww", "qwwwq"}, {"qeqeq", "rtqty", "qqqqq", "rtqty", "ququq"}, {"qqqqq", "qtqtq", "qqqqq", "qtqtq", "qqqqq"}, {"qqqqq", "qtitq", "qowpq", "qtatq", "qqqqq"}, {"qqqqq", "qsdsq", "qdwdq", "qsdsq", "qqqqq"}, {"qqqqq", "qtqtq", "qqfqq", "qtqtq", "qqqqq"}, {"qqqqq", "qsdsq", "qdwdq", "qsdsq", "qqqqq"}, {"qqqqq", "qqeqq", "qrwyq", "qquqq", "qqqqq"}})
            .setMainChar('f')
            .put('y',"solarcraft:magistone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarcraft:magistone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('u',"solarcraft:magistone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('t',"solarcraft:magistone_column")
            .put('w',"solarcraft:magistone_bricks")
            .put('i',"solarcraft:magistone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('q',"minecraft:air")
            .put('r',"solarcraft:magistone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('f',"solarcraft:explosion_blocker")
            .put('o',"solarcraft:magistone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('p',"solarcraft:magistone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('s',"solarcraft:chiseled_magistone")
            .put('a',"solarcraft:magistone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('d',"solarcraft:energized_stone")
            .build());

    public static final MultiblockStructure RUNIC_ENERGY_CORE = register(new MultiblockStructure.Builder()
            .setId("runic_energy_core")
            .setPattern(new String[][]{{"qwwwwwq", "errrrrt", "errrrrt", "errrrrt", "errrrrt", "errrrrt", "qyyyyyq"}, {"uiooopu", "aoooooa", "ooooooo", "ooooooo", "ooooooo", "sooooos", "uiooopu"}, {"uooooou", "ooooooo", "ooooooo", "ooooooo", "ooooooo", "ooooooo", "uooooou"}, {"qoooooq", "ooooooo", "ooooooo", "ooodooo", "ooooooo", "ooooooo", "qoooooq"}})
            .setMainChar('d')
            .put('e',"solarcraft:magistone_ray[facing=west]")
            .put('a',"solarcraft:magistone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('u',"solarcraft:magistone_column")
            .put('i',"solarcraft:magistone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('w',"solarcraft:magistone_ray[facing=north]")
            .put('r',"solarcraft:magistone_bricks")
            .put('t',"solarcraft:magistone_ray[facing=east]")
            .put('y',"solarcraft:magistone_ray[facing=south]")
            .put('p',"solarcraft:magistone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('q',"solarcraft:chiseled_magistone")
            .put('s',"solarcraft:magistone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('d',"solarcraft:runic_energy_core")
            .put('o',"minecraft:air")
            .build()
    );



    private static MultiblockStructure register(MultiblockStructure structure){
        STRUCTURES.put(structure.getId(),structure);
        return structure;
    }
}
