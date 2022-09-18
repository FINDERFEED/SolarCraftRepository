package com.finderfeed.solarforge.helpers.multiblock;

import com.finderfeed.solarforge.registries.Tags;

import java.util.HashMap;
import java.util.Map;

public class Multiblocks {

    public static final Map<String,MultiblockStructure> STRUCTURES = new HashMap<>();

    public static MultiblockStructure INFUSER_TIER_ONE = register(MultiblockStructure.Builder.start()
            .setId("infuser_tier_one")
            .setPattern(new String[][]{{"qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqq"}, {"weeweeweeweew", "eeeeeeeeeeeee", "eereeereeeree", "weeeeeeeeeeew", "eeeereeereeee", "eeeeeeeeeeeee", "wereeeteeerew", "eeeeeeeeeeeee", "eeeereeereeee", "weeeeeeeeeeew", "eereeereeeree", "eeeeeeeeeeeee", "weeweeweeweew"}, {"weeMeeMeeMeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "MeeeeeeeeeeeM", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "MeeeeeeeeeeeM", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "MeeeeeeeeeeeM", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeMeeMeeMeew"}, {"weeweeweeweew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeweeweeweew"}, {"weeweeweeweew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeeeeeeeeeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeweeweeweew"}, {"weeyuuiuuyeew", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "yeeeeeeeeeeey", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "ieeeeeeeeeeei", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "yeeeeeeeeeeey", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "weeyaaiaayeew"}, {"iuuieeeeeiuui", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "ieeeeeeeeeeei", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "eeeeeeeeeeeee", "ieeeeeeeeeeei", "oeeeeeeeeeeep", "oeeeeeeeeeeep", "iaaieeeeeiaai"}})
            .setMainChar('t')
            .put('q',"solarforge:solar_stone_bricks")
            .put('u',"solarforge:solar_stone_collumn_horizontal[facing=north]")
            .put('o',"solarforge:solar_stone_collumn_horizontal[facing=west]")
            .put('i',"solarforge:chiseled_solar_stone")
            .put('r',"solarforge:solar_forge_infusion_pool")
            .put('y',"minecraft:gold_block")
            .put('w',"solarforge:solar_stone_collumn")
            .put('p',"solarforge:solar_stone_collumn_horizontal[facing=east]")
            .put('a',"solarforge:solar_stone_collumn_horizontal[facing=south]")
            .put('e',"minecraft:air")
            .put('t',"solarforge:solar_infuser")
            .put('M',"solarforge:solar_stone_collumn")
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
            .put('f',"solarforge:urba_rune_block")
            .put('k',"solarforge:kelda_rune_block")
            .put('t',"solarforge:solar_stone_collumn")
            .put('l',"solarforge:repeater")
            .put('v',"solarforge:solar_stone_collumn_horizontal[facing=east]")
            .put('o',"solarforge:solar_infuser")
            .put('p',"solarforge:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarforge:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('j',"solarforge:ardo_rune_block")
            .put('x',"solarforge:solar_stone_collumn_horizontal[facing=north]")
            .put('h',"solarforge:zeta_rune_block")
            .put('c',"solarforge:solar_stone_collumn_horizontal[facing=west]")
            .put('n',"minecraft:diamond_block")
            .put('a',"solarforge:tera_rune_block")
            .put('y',"solarforge:solar_forge_infusion_pool")
            .put('g',"solarforge:giro_rune_block")
            .put('q',"minecraft:air")
            .put('w',"solarforge:solar_stone_bricks")
            .put('d',"solarforge:ultima_rune_block")
            .put('s',"solarforge:fira_rune_block")
            .put('i',"solarforge:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarforge:chiseled_solar_stone")
            .put('z',"minecraft:gold_block")
            .put('b',"solarforge:solar_stone_collumn_horizontal[facing=south]")
            .put('u',"solarforge:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('M',"solarforge:solar_stone_collumn")
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
            .put('g',"solarforge:urba_rune_block")
            .put('l',"solarforge:kelda_rune_block")
            .put('y',"solarforge:solar_stone_collumn")
            .put('t',"minecraft:obsidian")
            .put('z',"solarforge:repeater")
            .put('b',"solarforge:solar_stone_collumn_horizontal[facing=east]")
            .put('p',"solarforge:solar_infuser")
            .put('a',"solarforge:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarforge:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('k',"solarforge:ardo_rune_block")
            .put('c',"solarforge:solar_stone_collumn_horizontal[facing=north]")
            .put('E',"solarforge:solar_stone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('j',"solarforge:zeta_rune_block")
            .put('v',"solarforge:solar_stone_collumn_horizontal[facing=west]")
            .put('m',"minecraft:diamond_block")
            .put('s',"solarforge:tera_rune_block")
            .put('u',"solarforge:solar_forge_infusion_pool")
            .put('h',"solarforge:giro_rune_block")
            .put('T',"solarforge:energized_stone")
            .put('q',"minecraft:air")
            .put('w',"solarforge:solar_stone_bricks")
            .put('f',"solarforge:ultima_rune_block")
            .put('Q',"solarforge:solar_stone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('d',"solarforge:fira_rune_block")
            .put('o',"solarforge:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('W',"solarforge:solar_stone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('e',"solarforge:chiseled_solar_stone")
            .put('x',"minecraft:gold_block")
            .put('n',"solarforge:solar_stone_collumn_horizontal[facing=south]")
            .put('R',"solarforge:solar_stone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('i',"solarforge:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('M',"solarforge:solar_stone_collumn")
            .checkTag('M',Tags.CATALYST)
            .build());

    public static final MultiblockStructure ENERGY_GENERATOR = register(MultiblockStructure.Builder.start()
            .setId("generator")
            .setPattern(new String[][]{{"qwwwq", "errrt", "errrt", "errrt", "qyyyq"}, {"uuiuu", "uuuuu", "iuqui", "uuuuu", "uuiuu"}, {"uuquu", "uuuuu", "quouq", "uuuuu", "uuquu"}, {"uuuuu", "uuruu", "ururu", "uuruu", "uuuuu"}})
            .setMainChar('o')
            .put('r',"solarforge:solar_stone_bricks")
            .put('w',"solarforge:solar_stone_collumn_horizontal[facing=north]")
            .put('e',"solarforge:solar_stone_collumn_horizontal[facing=west]")
            .put('q',"solarforge:chiseled_solar_stone")
            .put('t',"solarforge:solar_stone_collumn_horizontal[facing=east]")
            .put('y',"solarforge:solar_stone_collumn_horizontal[facing=south]")
            .put('i',"solarforge:solar_stone_collumn")
            .put('o',"solarforge:solar_energy_generator")
            .put('u',"minecraft:air")
            .build());

    public static final MultiblockStructure AURA_HEALER = register(MultiblockStructure.Builder.start()
            .setId("healer")
            .setPattern(new String[][]{{"qqq", "qqq", "qqq"}, {"wew", "ere", "wew"}, {"tet", "eye", "tet"}})
            .setMainChar('y')
            .put('q',"solarforge:solar_stone_bricks")
            .put('r',"solarforge:chiseled_solar_stone")
            .put('w',"solarforge:solar_stone_collumn")
            .put('e',"minecraft:air")
            .put('t',"solarforge:illidium_block")
            .put('y',"solarforge:aura_healer_block")
            .build());

    public static final MultiblockStructure SOLAR_MORTAR = register(MultiblockStructure.Builder.start()
            .setId("mortar")
            .setPattern(new String[][]{{"qqqqq", "qqqqq", "qqqqq", "qqqqq", "qqqqq"}, {"weeew", "rttty", "rttty", "rttty", "wuuuw"}, {"iqtti", "ttttq", "ttttt", "qtttt", "ittqi"}, {"itqti", "ttttt", "qtttq", "ttttt", "itqti"}, {"ittqi", "qtttt", "ttttt", "ttttq", "iqtti"}, {"weeew", "rttty", "rttty", "rttty", "wuuuw"}, {"iqtti", "ttttq", "ttttt", "qtttt", "ittqi"}, {"itqti", "ttttt", "qtttq", "ttttt", "itqti"}, {"ittqi", "qtttt", "ttttt", "ttttq", "iqtti"}, {"weeew", "rttty", "rttty", "rttty", "wuuuw"}, {"tqqqt", "qqqqq", "qqtqq", "qqqqq", "tqqqt"}, {"ttqtt", "tqqqt", "qqtqq", "tqqqt", "ttqtt"}, {"ttttt", "ttitt", "tioit", "ttitt", "ttttt"}, {"ttttt", "ttwtt", "twtwt", "ttwtt", "ttttt"}})
            .setMainChar('o')
            .put('r',"solarforge:solar_stone_collumn_horizontal[facing=west]")
            .put('w',"solarforge:chiseled_solar_stone")
            .put('e',"solarforge:solar_stone_collumn_horizontal[facing=north]")
            .put('i',"solarforge:solar_stone_collumn")
            .put('q',"solarforge:solar_stone_bricks")
            .put('y',"solarforge:solar_stone_collumn_horizontal[facing=east]")
            .put('u',"solarforge:solar_stone_collumn_horizontal[facing=south]")
            .put('t',"minecraft:air")
            .put('o',"solarforge:solar_mortar_block")
            .build());

    public static final MultiblockStructure SOLAR_CORE = register(MultiblockStructure.Builder.start()
            .setId("solar_core")
            .setPattern(new String[][]{{"qqqqq", "qqqqq", "qqqqq", "qqqqq", "qqqqq"}, {"wweww", "wwwww", "ewwwe", "wwwww", "wweww"}, {"wwrww", "wwtww", "rtytr", "wwtww", "wwrww"}, {"wwtww", "wwwww", "twwwt", "wwwww", "wwtww"}, {"wwyww", "wwwww", "ywuwy", "wwwww", "wwyww"}, {"wwtww", "wwwww", "twwwt", "wwwww", "wwtww"}, {"wwwww", "wwtww", "wtytw", "wwtww", "wwwww"}})
            .setMainChar('u')
            .put('t',"solarforge:illidium_block")
            .put('r',"solarforge:chiseled_solar_stone")
            .put('e',"solarforge:solar_stone_collumn")
            .put('q',"solarforge:solar_stone_bricks")
            .put('y',"solarforge:algadium_block")
            .put('u',"solarforge:solar_core_block")
            .put('w',"minecraft:air")
            .build());

    public static final MultiblockStructure RADIANT_LAND_PORTAL = register(MultiblockStructure.Builder.start()
            .setId("dim_portal")
            .setPattern(new String[][]{{"qqqqqqq", "qqqqqqq", "qqqqqqq", "qqqwqqq", "qqqqqqq", "qqqqqqq", "qqqqqqq"}, {"errrrre", "rrrrrrr", "rrrrrrr", "rertrer", "rrrrrrr", "rrrrrrr", "errrrre"}, {"yrrrrru", "rrrrrrr", "rrrrrrr", "rirrror", "rrrrrrr", "rrrrrrr", "prrrrra"}, {"esrrrde", "frrrrrf", "rrrrrrr", "rerrrer", "rrrrrrr", "grrrrrg", "esrrrde"}, {"hjjjjjh", "krrrrrl", "krrrrrl", "kzrrrxl", "krrrrrl", "krrrrrl", "hccccch"}})
            .setMainChar('t')
            .put('z',"solarforge:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('p',"solarforge:tera_rune_block")
            .put('e',"solarforge:solar_stone_collumn")
            .put('j',"solarforge:solar_stone_collumn_horizontal[facing=north]")
            .put('q',"solarforge:solar_stone_bricks")
            .put('y',"solarforge:kelda_rune_block")
            .put('g',"solarforge:solar_stone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('l',"solarforge:solar_stone_collumn_horizontal[facing=east]")
            .put('a',"solarforge:ardo_rune_block")
            .put('r',"minecraft:air")
            .put('w',"solarforge:dimension_core")
            .put('x',"solarforge:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('k',"solarforge:solar_stone_collumn_horizontal[facing=west]")
            .put('s',"solarforge:solar_stone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('d',"solarforge:solar_stone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('h',"solarforge:chiseled_solar_stone")
            .put('f',"solarforge:solar_stone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('t',"solarforge:radiant_portal_creator")
            .put('i',"solarforge:zeta_rune_block")
            .put('o',"solarforge:urba_rune_block")
            .put('u',"solarforge:fira_rune_block")
            .put('c',"solarforge:solar_stone_collumn_horizontal[facing=south]")
            .build());

    public static final MultiblockStructure ZAP_TURRET = register(MultiblockStructure.Builder.start()
            .setId("zap_turret")
            .setPattern(new String[][]{{"qqq", "qqq", "qqq"}, {"wew", "rty", "wuw"}, {"www", "wtw", "www"}, {"www", "wiw", "www"}})
            .setMainChar('i')
            .put('y',"solarforge:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarforge:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('u',"solarforge:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarforge:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('i',"solarforge:zap_turret_block")
            .put('t',"solarforge:solar_stone_collumn")
            .put('q',"solarforge:solar_stone_bricks")
            .put('w',"minecraft:air")
            .build());

    public static final MultiblockStructure PYLON = register(MultiblockStructure.Builder.start()
            .setId("pylon")
            .setPattern(new String[][]{{"qwwwq", "wwwww", "wweww", "wwwww", "qwwwq"}, {"qqrqq", "qqqqq", "rqqqr", "qqqqq", "qqrqq"}, {"qqtqq", "qqqqq", "tqqqt", "qqqqq", "qqtqq"}, {"qqrqq", "qqqqq", "rqqqr", "qqqqq", "qqrqq"}, {"qyuiq", "oqqqo", "uqqqu", "pqqqp", "qyuiq"}, {"qqaqq", "qqsqq", "dfegh", "qqsqq", "qqjqq"}, {"qqqqq", "qqaqq", "qdehq", "qqjqq", "qqqqq"}, {"qqqqq", "qqqqq", "qqqqq", "qqqqq", "qqqqq"}, {"qqqqq", "qqqqq", "qqkqq", "qqqqq", "qqqqq"}})
            .setMainChar('k')
            .put('h',"solarforge:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('a',"solarforge:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('k',"solarforge:rune_energy_pylon")
            .put('j',"solarforge:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('r',"solarforge:solar_stone_collumn")
            .put('f',"solarforge:solar_stone_collumn_horizontal[facing=north]")
            .put('w',"solarforge:solar_stone_bricks")
            .put('o',"solarforge:solar_stone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('s',"solarforge:solar_stone_collumn_horizontal[facing=east]")
            .put('u',"solarforge:ardo_rune_block")
            .put('q',"minecraft:air")
            .put('t',"solarforge:inscription_stone[type=none]")
            .put('d',"solarforge:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarforge:chiseled_solar_stone")
            .put('y',"solarforge:solar_stone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('i',"solarforge:solar_stone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('p',"solarforge:solar_stone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('g',"solarforge:solar_stone_collumn_horizontal[facing=south]")
            .checkTag('u',Tags.CATALYST)
            .checkTag('t',Tags.INSCRIPTION_STONE)
            .build());

    public static final MultiblockStructure EXPLOSION_BLOCKER = register(MultiblockStructure.Builder.start()
            .setId("explosion_blocker")
            .setPattern(new String[][]{{"qwwwq", "wwwww", "wwwww", "wwwww", "qwwwq"}, {"qeqeq", "rtqty", "qqqqq", "rtqty", "ququq"}, {"qqqqq", "qtqtq", "qqqqq", "qtqtq", "qqqqq"}, {"qqqqq", "qtitq", "qowpq", "qtatq", "qqqqq"}, {"qqqqq", "qsdsq", "qdwdq", "qsdsq", "qqqqq"}, {"qqqqq", "qtqtq", "qqfqq", "qtqtq", "qqqqq"}, {"qqqqq", "qsdsq", "qdwdq", "qsdsq", "qqqqq"}, {"qqqqq", "qqeqq", "qrwyq", "qquqq", "qqqqq"}})
            .setMainChar('f')
            .put('y',"solarforge:solar_stone_stairs[facing=west,half=bottom,shape=straight,waterlogged=false]")
            .put('e',"solarforge:solar_stone_stairs[facing=south,half=bottom,shape=straight,waterlogged=false]")
            .put('u',"solarforge:solar_stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]")
            .put('t',"solarforge:solar_stone_collumn")
            .put('w',"solarforge:solar_stone_bricks")
            .put('i',"solarforge:solar_stone_stairs[facing=south,half=top,shape=straight,waterlogged=false]")
            .put('q',"minecraft:air")
            .put('r',"solarforge:solar_stone_stairs[facing=east,half=bottom,shape=straight,waterlogged=false]")
            .put('f',"solarforge:explosion_blocker")
            .put('o',"solarforge:solar_stone_stairs[facing=east,half=top,shape=straight,waterlogged=false]")
            .put('p',"solarforge:solar_stone_stairs[facing=west,half=top,shape=straight,waterlogged=false]")
            .put('s',"solarforge:chiseled_solar_stone")
            .put('a',"solarforge:solar_stone_stairs[facing=north,half=top,shape=straight,waterlogged=false]")
            .put('d',"solarforge:energized_stone")
            .build());




    private static MultiblockStructure register(MultiblockStructure structure){
        STRUCTURES.put(structure.getId(),structure);
        return structure;
    }
}
