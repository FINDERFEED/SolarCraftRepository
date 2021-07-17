package com.finderfeed.solarforge.registries.items;

import com.finderfeed.solarforge.SolarForge;

import com.finderfeed.solarforge.infusing_table_things.SolarWandItem;
import com.finderfeed.solarforge.magic_items.armor.SolarArmorMaterial;
import com.finderfeed.solarforge.magic_items.blocks.blockitems.TurretBlockItem;
import com.finderfeed.solarforge.magic_items.blocks.blockitems.UldoradiumOreBlockitem;
import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.magic_items.items.*;
import com.finderfeed.solarforge.magic_items.items.isters.ShieldOfSolarGodISTER;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscGunItem;
import com.finderfeed.solarforge.magic_items.items.vein_miner.VeinMiner;
import com.finderfeed.solarforge.misc_things.ColdStarPieceItem;
import com.finderfeed.solarforge.misc_things.SolarDustItem;
import com.finderfeed.solarforge.misc_things.VoidBlockWand;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.solar_lexicon.unlockables.AncientFragmentISTER;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"solarforge");

    public static final RegistryObject<SolarWandItem> SOLAR_WAND = ITEMS.register("solar_wand",()-> new SolarWandItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static  final RegistryObject<Item> SOLAR_INFUSION_POOL = ITEMS.register("solar_forge_infusion_pool",()-> new BlockItem(BlocksRegistry.SOLAR_POOL.get().getBlock(),new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_BLOCKS).stacksTo(1)));
    public static  final RegistryObject<SolarDustItem> SOLAR_DUST = ITEMS.register("solar_dust",()-> new SolarDustItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_STONE = ITEMS.register("solar_stone",()->new BlockItem(BlocksRegistry.SOLAR_STONE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_CHISELED = ITEMS.register("chiseled_solar_stone",()->new BlockItem(BlocksRegistry.SOLAR_STONE_CHISELED.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN = ITEMS.register("solar_stone_collumn",()->new BlockItem(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_BRICKS = ITEMS.register("solar_stone_bricks",()->new BlockItem(BlocksRegistry.SOLAR_STONE_BRICKS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_STAIRS = ITEMS.register("solar_stone_stairs",()->new BlockItem(BlocksRegistry.SOLAR_STONE_STAIRS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN_HORIZONTAL = ITEMS.register("solar_stone_collumn_horizontal",()->new BlockItem(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    //solar armor
    public static final RegistryObject<ArmorItem> SOLAR_HELMET = ITEMS.register("solar_helmet",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlotType.HEAD,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<ArmorItem> SOLAR_CHESTPLATE = ITEMS.register("solar_chestplate",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlotType.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<ArmorItem> SOLAR_LEGGINS = ITEMS.register("solar_leggins",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlotType.LEGS,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<ArmorItem> SOLAR_BOOTS = ITEMS.register("solar_boots",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlotType.FEET,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));

    //radiant armor
    public static final RegistryObject<RadiantChestplate> RADIANT_CHESTPLATE = ITEMS.register("radiant_chestplate",()-> new RadiantChestplate(SolarArmorMaterial.RADIANT_ARMOR, EquipmentSlotType.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    //lexicon
    public static  final RegistryObject<SolarLexicon> SOLAR_LEXICON = ITEMS.register("solar_lexicon",()-> new SolarLexicon(new Item.Properties().tab(SolarForge.SOLAR_GROUP).stacksTo(1)));

    public static final RegistryObject<VoidBlockWand> VOID_BLOCK_WAND = ITEMS.register("void_block_wand",()-> new VoidBlockWand(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SOLAR_KEY = ITEMS.register("solar_key",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> COLD_STAR_PIECE = ITEMS.register("cold_star_piece",()-> new ColdStarPieceItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> COLD_STAR_PIECE_ACTIVATED = ITEMS.register("cold_star_piece_activated",()-> new ChargedColdStar(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> KEY_LOCK_BLOCK = ITEMS.register("key_lock_block",()->new BlockItem(BlocksRegistry.KEY_LOCK_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> INVINCIBLE_STONE = ITEMS.register("invincible_solar_stone",()->new BlockItem(BlocksRegistry.INVINCIBLE_STONE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_LENS = ITEMS.register("solar_lens",()->new SolarLensBlockItem(BlocksRegistry.SOLAR_LENS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> KEY_DEFENDER = ITEMS.register("defence_trap_block",()->new BlockItem(BlocksRegistry.KEY_DEFENDER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> COLD_STAR_INFUSER = ITEMS.register("cold_star_charger",()->new BlockItem(BlocksRegistry.COLD_STAR_INFUSER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ILLIDIUM_INGOT = ITEMS.register("illidium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> ALGADIUM_INGOT = ITEMS.register("algadium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> QUALADIUM_INGOT = ITEMS.register("qualadium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_INGOT = ITEMS.register("charged_qualadium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SMALL_SOLAR_REACTOR = ITEMS.register("small_solar_reactor",()-> new Item(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> MEDIUM_SOLAR_REACTOR = ITEMS.register("medium_solar_reactor",()-> new Item(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> ILLIDIUM_SWORD = ITEMS.register("illidium_sword",()-> new IllidiumSword());
    public static final RegistryObject<Item> ILLIDIUM_AXE = ITEMS.register("illidium_axe",()->new IllidiumAxe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,4,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> GROWTH_HOE = ITEMS.register("illidium_hoe",()-> new IllidiumHoe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,-4, 0.0F, (new Item.Properties()).rarity(Rarity.RARE).stacksTo(1).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> VEIN_MINER = ITEMS.register("miner_item",()-> new VeinMiner(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER, 1, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1)));
    public static final RegistryObject<Item> ILLIDIUM_SHOVEL = ITEMS.register("illidium_shovel",()-> new ShovelItem(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,1,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> QUALADIUM_SHOVEL = ITEMS.register("qualadium_shovel",()-> new ShovelItem(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> QUALADIUM_SWORD = ITEMS.register("qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> QUALADIUM_HOE = ITEMS.register("qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> QUALADIUM_PICKAXE = ITEMS.register("qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1)));
    public static final RegistryObject<Item> QUALADIUM_AXE = ITEMS.register("qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SHOVEL = ITEMS.register("charged_qualadium_shovel",()-> new ShovelItem(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SWORD = ITEMS.register("charged_qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_HOE = ITEMS.register("charged_qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_PICKAXE = ITEMS.register("charged_qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_AXE = ITEMS.register("charged_qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> SOLAR_DISC_LAUNCHER = ITEMS.register("solar_disc_launcher",()-> new SolarDiscGunItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> MAGNET_BLOCK = ITEMS.register("magnet_block",()->new BlockItem(BlocksRegistry.MAGNET_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_DISC = ITEMS.register("to_render_item",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ENERGY_GENERATOR_BLOCK = ITEMS.register("solar_energy_generator",()->new SolarEnergyGeneratorBlockItem());
    public static final RegistryObject<Item> SOLAR_ENERGY_REPEATER = ITEMS.register("solar_energy_repeater",()->new BlockItem(BlocksRegistry.SOLAR_REPEATER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_NETWORK_BINDER = ITEMS.register("solar_network_binder",()-> new SolarNetworkBinder(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> ILLIDIUM_BLOCK = ITEMS.register("illidium_block",()->new BlockItem(BlocksRegistry.ILLIDIUM_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ALGADIUM_BLOCK = ITEMS.register("algadium_block",()->new BlockItem(BlocksRegistry.ALGADIUM_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_CORE = ITEMS.register("solar_core_block",()->new BlockItem(BlocksRegistry.SOLAR_CORE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> AURA_HEALER = ITEMS.register("aura_healer_block",()->new BlockItem(BlocksRegistry.AURA_HEALER_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static  final RegistryObject<TotemOfImmortality> TOTEM_OF_IMMORTALITY = ITEMS.register("totem_of_immortality",()-> new TotemOfImmortality(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP).stacksTo(1)));
    public static  final RegistryObject<ShieldOfSolarGod> SOLAR_GOD_SHIELD = ITEMS.register("solar_god_shield",()-> new ShieldOfSolarGod(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP).durability(3000).setISTER(()->ShieldOfSolarGodISTER::new)));
    public static final RegistryObject<BlockBoomerang> BLOCK_BOOMERANG = ITEMS.register("block_boomerang",()-> new BlockBoomerang(new Item.Properties().stacksTo(1).tab(SolarForge.SOLAR_GROUP).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ManaAmulet> SOLAR_MANA_AMULET = ITEMS.register("solar_mana_amulet",()-> new ManaAmulet(new Item.Properties().stacksTo(1).tab(SolarForge.SOLAR_GROUP).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<UltraCrossbowItem> ULTRA_CROSSBOW = ITEMS.register("solar_crossbow",()-> new UltraCrossbowItem(new Item.Properties().stacksTo(1).tab(SolarForge.SOLAR_GROUP).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SOLAR_MORTAR = ITEMS.register("solar_mortar_block",()->new BlockItem(BlocksRegistry.SOLAR_MORTAR_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ENERGY_METER = ITEMS.register("energy_meter",()->new EnergyMeter(new Item.Properties().tab(SolarForge.SOLAR_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SOLAR_FLOWER = ITEMS.register("solar_flower",()->new BlockItem(BlocksRegistry.SOLAR_FLOWER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ENERGY_DUST = ITEMS.register("energy_dust",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_FURNACE_BLOCK = ITEMS.register("solar_furnace",()->new BlockItem(BlocksRegistry.SOLAR_FURNACE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> TURRET_BLOCK = ITEMS.register("turret_block",()->new TurretBlockItem(BlocksRegistry.TURRET_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_GOD_SWORD = ITEMS.register("solar_god_sword",()->new SolarGodSword(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,-3,-2.4f,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1)));
    public static final RegistryObject<Item> SOLAR_GOD_PICKAXE = ITEMS.register("solar_god_pickaxe",()-> new SolarGodPickaxe(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER, -6, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> ULDORADIUM_ORE = ITEMS.register("uldoradium_ore",()->new UldoradiumOreBlockitem(BlocksRegistry.ULDORADIUM_ORE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RAY_TRAP_BLOCK = ITEMS.register("ray_trap_block",()->new BlockItem(BlocksRegistry.RAY_TRAP_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> TRAP_CONTROLLER = ITEMS.register("trap_controller",()->new BlockItem(BlocksRegistry.TRAP_CONTROLLER.get(),new Item.Properties()));
    public static final RegistryObject<Item> BLUE_GEM = ITEMS.register("blue_gem",()->new BlueGemItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_ENCHANCED = ITEMS.register("blue_gem_enriched",()->new EnchancedBlueGem(new Item.Properties().tab(SolarForge.SOLAR_GROUP).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_DOOR_BLOCK = ITEMS.register("blue_gem_door_block",()->new BlockItem(BlocksRegistry.BLUE_GEM_DOOR_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> ASH_LEAVES = ITEMS.register("ash_leaves",()->new BlockItem(BlocksRegistry.ASH_LEAVES.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> BURNT_LOG = ITEMS.register("burnt_log",()->new BlockItem(BlocksRegistry.BURNT_LOG.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> DEAD_SPROUT = ITEMS.register("dead_sprout",()->new BlockItem(BlocksRegistry.DEAD_SPROUT.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> FIRST_DIMENSIONAL_SHARD = ITEMS.register("first_dimensional_shard",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ASH_ITEM = ITEMS.register("ash",()->new AshItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> INFO_FRAGMENT = ITEMS.register("ancient_fragment",()->new AncientFragmentItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_FRAGMENTS).stacksTo(1).setISTER(()->AncientFragmentISTER::new)));
    public static final RegistryObject<Item> RUNIC_TABLE = ITEMS.register("runic_table",()->new BlockItem(BlocksRegistry.RUNIC_TABLE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));


    public static final RegistryObject<Item> RUNE_ENERGY_PYLON = ITEMS.register("rune_energy_pylon",()->new BlockItem(BlocksRegistry.RUNE_ENERGY_PYLON.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));


    //runes
    public static final RegistryObject<Item> SOLAR_RUNE_BASE = ITEMS.register("runestone",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));

    public static final RegistryObject<Item> SOLAR_RUNE_ZETA = ITEMS.register("solar_rune_zeta",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_RUNE_ARDO = ITEMS.register("solar_rune_ardo",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_RUNE_URBA = ITEMS.register("solar_rune_urba",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_RUNE_KELDA = ITEMS.register("solar_rune_kelda",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_RUNE_FIRA = ITEMS.register("solar_rune_fira",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_RUNE_TERA = ITEMS.register("solar_rune_tera",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
}
