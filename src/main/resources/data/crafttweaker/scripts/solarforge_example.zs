import mods.solarforge.RunicEnergyCost;

//<recipetype:solarforge:infusing_crafting>.addRecipe(name as string, output as IItemStack, inputs as IItemStack[][], processingTime as int, fragment as string).

//Adds a new recipe to the Infusing Crafting Table.

//The inputs array must be a 3x3 2D array, meaning that it MUST have 9 objects in it and be padded with air.

//You can get the usable fragment parameters using the command /ct dump solarforge_fragments.

<recipetype:solarforge:infusing_crafting>.addRecipe("infusing_crafting_test_recipe", <item:minecraft:egg>, [
[<item:minecraft:air>, <item:minecraft:diamond>, <item:minecraft:air>], [<item:minecraft:white_wool>, <item:minecraft:stick>, <item:minecraft:white_wool>], [<item:minecraft:air>, <item:minecraft:diamond>, <item:minecraft:air>]
], 300, "crystals");


//<recipetype:solarforge:infusing_new>.addRecipe(name as string, output as IItemStack, inputs as IItemStack[][], catalysts as Block[][], processing time as int, fragment as string, cost as RunicEnergyCost);

//Adds a new recipe to the Infuser Multiblock setup.

//Inputs needs to be a Two Dimensional array that looks like this:
// [ [input1, input2, input3], [input4, input5], [input6, input7, input8], [input9, input10], [input11, input12, input13] ]

//Catalysts needs to be a Two Dimensional array that looks like this:

// [ [catalyst1, catalyst2, catalyst3], [catalyst4, catalyst5, catalyst6], [catalyst7, catalyst8, catalyst9], [catalyst10, catalyst11, catalyst12]]

//Each array in the Catalysts 2D array represents one axis in space. The recipe setup and the catalysts need to match what's expected of the recipe.
//The catalysts axis are [[North], [East], [South], [West]].
// Usable catalysts can be found by using the /ct dump solarforge_catalysts command.

//If a Block that isn't on the catalysts list is used, it will be ignored and Solar Stone Bricks will be required as a catalyst instead.
//When holding a valid catalyst block, the blocks where it is placeable will be highlighted in a white outline.


//The cost


var empty = <block:minecraft:air>;


<recipetype:solarforge:infusing_new>.addRecipe("infusing_test_recipe", <item:minecraft:egg>,
[[<item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:diamond>], [<item:minecraft:diamond>, <item:minecraft:diamond>], [<item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:diamond>], [<item:minecraft:diamond>, <item:minecraft:diamond>], [<item:minecraft:diamond>, <item:minecraft:diamond>, <item:minecraft:diamond>]],
[[empty, <block:solarforge:zeta_rune_block>, empty],[<block:solarforge:urba_rune_block>, empty, <block:solarforge:fira_rune_block>],[empty, <block:solarforge:ardo_rune_block>, empty],[<block:solarforge:giro_rune_block>, empty, <block:solarforge:ultima_rune_block>]],
300, "crystals", RunicEnergyCost.EMPTY());

<recipetype:solarforge:solar_smelting>.addRecipe("solar_smelting_test_recipe", <item:minecraft:diamond_pickaxe>, [<item:minecraft:dirt>, <item:minecraft:emerald>], 300);

/*
 List of all known SolarForge Fragments 
 - algadium_ingot
 - amethyst_core
 - amulets
 - aura_healer
 - aura_healer_structure
 - block_boomerang
 - bonemealer
 - catalysts
 - charged_qualadium_ingot
 - crystal_cores
 - crystals
 - defence_crystal
 - dimension_core
 - dimension_structure
 - disc_launcher
 - divine_armor
 - eight_elements
 - elemental_enchanter
 - ender_radar
 - enderium_ingot
 - energetic_ingot
 - energy_dust
 - experience_crystal
 - explosion_blocker
 - explosion_blocker_structure
 - fragment
 - geminium_ingot
 - gloves_of_reach
 - illidium_axe
 - illidium_hoe
 - illidium_ingot
 - illidium_pickaxe
 - illidium_shovel
 - illidium_sword
 - infusing_crafting_table
 - item_magnet
 - lexicon
 - lightning_gun
 - medium_solar_reactor
 - modules
 - qualadium_axe
 - qualadium_hoe
 - qualadium_ingot
 - qualadium_pickaxe
 - qualadium_shovel
 - qualadium_sword
 - radiant_cuirass
 - radiant_land
 - re_charger
 - runic_core
 - runic_energy
 - runic_energy_repeater
 - runic_table
 - small_solar_reactor
 - solar_boots
 - solar_chestplate
 - solar_core
 - solar_core_structure
 - solar_crossbow
 - solar_dust
 - solar_energy_generator
 - solar_energy_generator_structure
 - solar_energy_repeater
 - solar_forge
 - solar_furnace
 - solar_god_bow
 - solar_god_bow_upgrade
 - solar_god_pickaxe
 - solar_god_pickaxe_upgrade
 - solar_god_shield
 - solar_god_sword
 - solar_god_sword_upgrade
 - solar_helmet
 - solar_infuser
 - solar_infuser_structure
 - solar_infuser_structure_2
 - solar_infuser_structure_3
 - solar_leggings
 - solar_lens
 - solar_mortar
 - solar_mortar_structure
 - solar_stones
 - solar_turret
 - speed_road
 - totem_of_immortality
 - turret_radar
 - void_dust
 - wand
 - zap_turret
 - zap_turret_structure
*/

/*
 List of all known Catalyst Blocks 
- <block:solarforge:fira_rune_block>
- <block:solarforge:tera_rune_block>
- <block:solarforge:kelda_rune_block>
- <block:solarforge:zeta_rune_block>
- <block:solarforge:urba_rune_block>
- <block:solarforge:ultima_rune_block>
- <block:solarforge:giro_rune_block>
- <block:solarforge:ardo_rune_block>
*/