package com.finderfeed.solarforge.registries.items;

import com.finderfeed.solarforge.SolarForge;

import com.finderfeed.solarforge.magic_items.blocks.FuelBlockItem;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.SolarWandItem;
import com.finderfeed.solarforge.magic_items.armor.SolarArmorMaterial;
import com.finderfeed.solarforge.magic_items.blocks.blockitems.TurretBlockItem;
import com.finderfeed.solarforge.magic_items.blocks.blockitems.UldoradiumOreBlockitem;
import com.finderfeed.solarforge.magic_items.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.magic_items.items.*;
import com.finderfeed.solarforge.magic_items.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarforge.magic_items.items.small_items.*;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscGunItem;
import com.finderfeed.solarforge.magic_items.items.vein_miner.VeinMiner;
import com.finderfeed.solarforge.misc_things.ColdStarPieceItem;
import com.finderfeed.solarforge.misc_things.SolarDustItem;
import com.finderfeed.solarforge.misc_things.SolarcraftDebugStick;
import com.finderfeed.solarforge.misc_things.VoidBlockWand;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ItemsRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"solarforge");

    public static final RegistryObject<SolarWandItem> SOLAR_WAND = ITEMS.register("solar_wand",()-> new SolarWandItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static  final RegistryObject<Item> SOLAR_INFUSION_POOL = ITEMS.register("solar_forge_infusion_pool",()-> new BlockItem(BlocksRegistry.SOLAR_POOL.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static  final RegistryObject<SolarDustItem> SOLAR_DUST = ITEMS.register("solar_dust",()-> new SolarDustItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> SOLAR_STONE = ITEMS.register("solar_stone",()->new ProgressionBlockItem(BlocksRegistry.SOLAR_STONE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_CHISELED = ITEMS.register("chiseled_solar_stone",()->new BlockItem(BlocksRegistry.SOLAR_STONE_CHISELED.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN = ITEMS.register("solar_stone_collumn",()->new BlockItem(BlocksRegistry.SOLAR_STONE_COLLUMN.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_BRICKS = ITEMS.register("solar_stone_bricks",()->new BlockItem(BlocksRegistry.SOLAR_STONE_BRICKS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_STAIRS = ITEMS.register("solar_stone_stairs",()->new BlockItem(BlocksRegistry.SOLAR_STONE_STAIRS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN_HORIZONTAL = ITEMS.register("solar_stone_collumn_horizontal",()->new BlockItem(BlocksRegistry.SOLAR_STONE_COLLUMN_HORIZONTAL.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    //solar armor
    public static final RegistryObject<ArmorItem> SOLAR_HELMET = ITEMS.register("solar_helmet",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.HEAD,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<ArmorItem> SOLAR_CHESTPLATE = ITEMS.register("solar_chestplate",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<ArmorItem> SOLAR_LEGGINS = ITEMS.register("solar_leggins",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.LEGS,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<ArmorItem> SOLAR_BOOTS = ITEMS.register("solar_boots",()-> new ArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.FEET,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));

    //radiant armor
    public static final RegistryObject<RadiantChestplate> RADIANT_CHESTPLATE = ITEMS.register("radiant_chestplate",()-> new RadiantChestplate(SolarArmorMaterial.RADIANT_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    //lexicon
    public static  final RegistryObject<SolarLexicon> SOLAR_LEXICON = ITEMS.register("solar_lexicon",()-> new SolarLexicon(new Item.Properties().tab(SolarForge.SOLAR_GROUP).stacksTo(1)));

    public static final RegistryObject<VoidBlockWand> VOID_BLOCK_WAND = ITEMS.register("void_block_wand",()-> new VoidBlockWand(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SOLAR_KEY = ITEMS.register("solar_key",()-> new RareSolarcraftItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> COLD_STAR_PIECE = ITEMS.register("cold_star_piece",()-> new ColdStarPieceItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_MATERIALS).stacksTo(1)));
    public static final RegistryObject<Item> COLD_STAR_PIECE_ACTIVATED = ITEMS.register("cold_star_piece_activated",()-> new ChargedColdStar(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> KEY_LOCK_BLOCK = ITEMS.register("key_lock_block",()->new BlockItem(BlocksRegistry.KEY_LOCK_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> INVINCIBLE_STONE = ITEMS.register("invincible_solar_stone",()->new BlockItem(BlocksRegistry.INVINCIBLE_STONE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_LENS = ITEMS.register("solar_lens",()->new SolarLensBlockItem(BlocksRegistry.SOLAR_LENS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> KEY_DEFENDER = ITEMS.register("defence_trap_block",()->new BlockItem(BlocksRegistry.KEY_DEFENDER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> COLD_STAR_INFUSER = ITEMS.register("cold_star_charger",()->new BlockItem(BlocksRegistry.COLD_STAR_INFUSER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ILLIDIUM_INGOT = ITEMS.register("illidium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> ALGADIUM_INGOT = ITEMS.register("algadium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> QUALADIUM_INGOT = ITEMS.register("qualadium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_INGOT = ITEMS.register("charged_qualadium_ingot",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> AMETHYST_CORE = ITEMS.register("amethyst_core",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> RUNIC_CORE = ITEMS.register("runic_core",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> GEMINIUM_INGOT = ITEMS.register("geminium_ingot",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> TURRET_RADAR = ITEMS.register("turret_radar",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SMALL_SOLAR_REACTOR = ITEMS.register("small_solar_reactor",()-> new Item(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> MEDIUM_SOLAR_REACTOR = ITEMS.register("medium_solar_reactor",()-> new Item(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> ILLIDIUM_SWORD = ITEMS.register("illidium_sword",()-> new IllidiumSword());
    public static final RegistryObject<Item> ILLIDIUM_AXE = ITEMS.register("illidium_axe",()->new IllidiumAxe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,4,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> GROWTH_HOE = ITEMS.register("illidium_hoe",()-> new IllidiumHoe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,-4, 0.0F, (new Item.Properties()).rarity(Rarity.RARE).stacksTo(1).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> VEIN_MINER = ITEMS.register("miner_item",()-> new VeinMiner(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER, 1, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1)));
    public static final RegistryObject<Item> ILLIDIUM_SHOVEL = ITEMS.register("illidium_shovel",()-> new ShovelItem(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,1,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> QUALADIUM_SHOVEL = ITEMS.register("qualadium_shovel",()-> new ShovelItem(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> QUALADIUM_SWORD = ITEMS.register("qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_WEAPONS)));
    public static final RegistryObject<Item> QUALADIUM_HOE = ITEMS.register("qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> QUALADIUM_PICKAXE = ITEMS.register("qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1)));
    public static final RegistryObject<Item> QUALADIUM_AXE = ITEMS.register("qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SHOVEL = ITEMS.register("charged_qualadium_shovel",()-> new ShovelItem(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SWORD = ITEMS.register("charged_qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_WEAPONS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_HOE = ITEMS.register("charged_qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_PICKAXE = ITEMS.register("charged_qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1)));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_AXE = ITEMS.register("charged_qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> SOLAR_DISC_LAUNCHER = ITEMS.register("solar_disc_launcher",()-> new SolarDiscGunItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarForge.SOLAR_GROUP_WEAPONS)));
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
    public static  final RegistryObject<ShieldOfSolarGod> SOLAR_GOD_SHIELD = ITEMS.register("solar_god_shield",()-> new ShieldOfSolarGod(new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_WEAPONS).durability(3000)));
    public static final RegistryObject<BlockBoomerang> BLOCK_BOOMERANG = ITEMS.register("block_boomerang",()-> new BlockBoomerang(new Item.Properties().stacksTo(1).tab(SolarForge.SOLAR_GROUP).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ManaAmulet> SOLAR_MANA_AMULET = ITEMS.register("solar_mana_amulet",()-> new ManaAmulet(new Item.Properties().stacksTo(1).tab(SolarForge.SOLAR_GROUP).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<UltraCrossbowItem> ULTRA_CROSSBOW = ITEMS.register("solar_crossbow",()-> new UltraCrossbowItem(new Item.Properties().stacksTo(1).tab(SolarForge.SOLAR_GROUP_WEAPONS).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SOLAR_MORTAR = ITEMS.register("solar_mortar_block",()->new BlockItem(BlocksRegistry.SOLAR_MORTAR_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ENERGY_METER = ITEMS.register("energy_meter",()->new EnergyMeter(new Item.Properties().tab(SolarForge.SOLAR_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SOLAR_FLOWER = ITEMS.register("solar_flower",()->new BlockItem(BlocksRegistry.SOLAR_FLOWER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ENERGY_DUST = ITEMS.register("energy_dust",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SOLAR_FURNACE_BLOCK = ITEMS.register("solar_furnace",()->new BlockItem(BlocksRegistry.SOLAR_FURNACE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> TURRET_BLOCK = ITEMS.register("turret_block",()->new TurretBlockItem(BlocksRegistry.TURRET_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ZAP_TURRET_BLOCK = ITEMS.register("zap_turret_block",()->new BlockItem(BlocksRegistry.ZAP_TURRET_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_GOD_SWORD = ITEMS.register("solar_god_sword",()->new SolarGodSword(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,-3,-2.4f,new Item.Properties().rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_WEAPONS).stacksTo(1)));
    public static final RegistryObject<Item> SOLAR_GOD_PICKAXE = ITEMS.register("solar_god_pickaxe",()-> new SolarGodPickaxe(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER, -6, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC).tab(SolarForge.SOLAR_GROUP_TOOLS)));
    public static final RegistryObject<Item> ULDORADIUM_ORE = ITEMS.register("uldoradium_ore",()->new UldoradiumOreBlockitem(BlocksRegistry.ULDORADIUM_ORE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RAY_TRAP_BLOCK = ITEMS.register("ray_trap_block",()->new BlockItem(BlocksRegistry.RAY_TRAP_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> TRAP_CONTROLLER = ITEMS.register("trap_controller",()->new BlockItem(BlocksRegistry.TRAP_CONTROLLER.get(),new Item.Properties()));
    public static final RegistryObject<Item> BLUE_GEM = ITEMS.register("blue_gem",()->new BlueGemItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_ENCHANCED = ITEMS.register("blue_gem_enriched",()->new EnchancedBlueGem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_DOOR_BLOCK = ITEMS.register("blue_gem_door_block",()->new BlockItem(BlocksRegistry.BLUE_GEM_DOOR_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> ASH_LEAVES = ITEMS.register("ash_leaves",()->new BlockItem(BlocksRegistry.ASH_LEAVES.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> BURNT_LOG = ITEMS.register("burnt_log",()->new BlockItem(BlocksRegistry.BURNT_LOG.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> DEAD_SPROUT = ITEMS.register("dead_sprout",()->new BlockItem(BlocksRegistry.DEAD_SPROUT.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> FIRST_DIMENSIONAL_SHARD = ITEMS.register("first_dimensional_shard",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ASH_ITEM = ITEMS.register("ash",()->new AshItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP)));
    public static final RegistryObject<Item> INFO_FRAGMENT = ITEMS.register("ancient_fragment",()->new AncientFragmentItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_FRAGMENTS).stacksTo(1)));
    public static final RegistryObject<Item> RUNIC_TABLE = ITEMS.register("runic_table",()->new BlockItem(BlocksRegistry.RUNIC_TABLE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));


    public static final RegistryObject<Item> RUNIC_LEAVES = ITEMS.register("runic_leaves",()->new BlockItem(BlocksRegistry.RUNIC_LEAVES.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RUNIC_LOG = ITEMS.register("runic_log",()->new FuelBlockItem(BlocksRegistry.RUNIC_LOG.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),600));
    public static final RegistryObject<Item> RUNIC_PLANKS = ITEMS.register("runic_planks",()->new FuelBlockItem(BlocksRegistry.RUNIC_PLANKS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RUNIC_STAIRS = ITEMS.register("runic_stairs",()->new FuelBlockItem(BlocksRegistry.RUNIC_STAIRS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RUNIC_SLAB = ITEMS.register("runic_slab",()->new FuelBlockItem(BlocksRegistry.RUNIC_SLAB.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),300));


    public static final RegistryObject<Item> RUNE_ENERGY_PYLON = ITEMS.register("rune_energy_pylon",()->new EnergyPylonBlockItem(BlocksRegistry.RUNE_ENERGY_PYLON.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("solar_debug_stick",()->new SolarcraftDebugStick(new Item.Properties()));
    //runes
    public static final RegistryObject<Item> SOLAR_RUNE_BASE = ITEMS.register("runestone",()->new RuneBase(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));

    public static final RegistryObject<Item> CRYSTALLITE_CORE = ITEMS.register("crystallite_core",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));


    public static final RegistryObject<Item> SOLAR_RUNE_ZETA = ITEMS.register("solar_rune_zeta",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SOLAR_RUNE_ARDO = ITEMS.register("solar_rune_ardo",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SOLAR_RUNE_URBA = ITEMS.register("solar_rune_urba",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SOLAR_RUNE_KELDA = ITEMS.register("solar_rune_kelda",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SOLAR_RUNE_FIRA = ITEMS.register("solar_rune_fira",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SOLAR_RUNE_TERA = ITEMS.register("solar_rune_tera",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_MATERIALS)));


    public static final RegistryObject<Item> SOLAR_GOD_BOW = ITEMS.register("solar_god_bow",()->new SolarGodBow(new Item.Properties().tab(SolarForge.SOLAR_GROUP_WEAPONS).stacksTo(1).durability(8000)));


    public static final RegistryObject<Item> REGEN_AMULET = ITEMS.register("regen_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.REGENERATION));
    public static final RegistryObject<Item> STRENGTH_AMULET = ITEMS.register("strength_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.DAMAGE_BOOST));
    public static final RegistryObject<Item> NIGHT_VISION_AMULET = ITEMS.register("night_vision_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.NIGHT_VISION));
    public static final RegistryObject<Item> SPEED_AMULET = ITEMS.register("speed_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.MOVEMENT_SPEED));
    public static final RegistryObject<Item> HASTE_AMULET = ITEMS.register("haste_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.DIG_SPEED));
    public static final RegistryObject<Item> JUMP_AMULET = ITEMS.register("jump_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.JUMP));


    public static final RegistryObject<Item> REPEATER = ITEMS.register("repeater",()->new RunicNetworkRepeaterBlockItem(BlocksRegistry.REPEATER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> ARDO_RUNE_BLOCK = ITEMS.register("ardo_rune_block",()->new BlockItem(BlocksRegistry.ARDO_RUNE_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ZETA_RUNE_BLOCK = ITEMS.register("zeta_rune_block",()->new BlockItem(BlocksRegistry.ZETA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> KELDA_RUNE_BLOCK = ITEMS.register("kelda_rune_block",()->new BlockItem(BlocksRegistry.KELDA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> URBA_RUNE_BLOCK = ITEMS.register("urba_rune_block",()->new BlockItem(BlocksRegistry.URBA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> TERA_RUNE_BLOCK = ITEMS.register("tera_rune_block",()->new BlockItem(BlocksRegistry.TERA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> FIRA_RUNE_BLOCK = ITEMS.register("fira_rune_block",()->new BlockItem(BlocksRegistry.FIRA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> RADIANT_CRYSTAL = ITEMS.register("radiant_crystal",()->new BlockItem(BlocksRegistry.RADIANT_CRYSTAL.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> WORMHOLE = ITEMS.register("wormhole",()->new BlockItem(BlocksRegistry.WORMHOLE.get(),new Item.Properties()));
    public static final RegistryObject<Item> REACH_GLOVES = ITEMS.register("gloves_of_reach",()->new Item(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> text, TooltipFlag p_41424_) {
            text.add(new TranslatableComponent("gloves_of_reach_active").withStyle(ChatFormatting.GOLD));
            super.appendHoverText(p_41421_, p_41422_, text, p_41424_);
        }
    });
    public static final RegistryObject<Item> DIMENSION_CORE = ITEMS.register("dimension_core",()->new RadiantCoreBlockItem(BlocksRegistry.DIMENSION_CORE.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RADIANT_LEAVES = ITEMS.register("radiant_leaves",()->new BlockItem(BlocksRegistry.RADIANT_LEAVES.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RADIANT_GRASS = ITEMS.register("radiant_grass",()->new BlockItem(BlocksRegistry.RADIANT_GRASS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RADIANT_GRASS_NOT_BLOCK = ITEMS.register("radiant_grass_grass",()->new BlockItem(BlocksRegistry.RADIANT_GRASS_NOT_BLOCK.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RADIANT_LOG = ITEMS.register("radiant_log",()->new FuelBlockItem(BlocksRegistry.RADIANT_LOG.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),600));
    public static final RegistryObject<Item> RADIANT_PLANKS = ITEMS.register("radiant_planks",()->new FuelBlockItem(BlocksRegistry.RADIANT_PLANKS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RADIANT_STAIRS = ITEMS.register("radiant_stairs",()->new FuelBlockItem(BlocksRegistry.RADIANT_WOOD_STAIRS.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RADIANT_SLAB = ITEMS.register("radiant_slab",()->new FuelBlockItem(BlocksRegistry.RADIANT_WOOD_SLAB.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RADIANT_BERRY = ITEMS.register("radiant_berry",()->new RadiantBerry(new Item.Properties().tab(SolarForge.SOLAR_GROUP).food(new FoodProperties.Builder().alwaysEat().nutrition(5).saturationMod(0.9F).build())));
    public static final RegistryObject<Item> RADIANT_BERRY_BUSH = ITEMS.register("radiant_berry_bush",()->new BlockItem(BlocksRegistry.RADIANT_BERRY_BUSH.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> RADIANT_PORTAL_CREATOR = ITEMS.register("radiant_portal_creator",()->new PortalCreatorBlockItem(BlocksRegistry.RADIANT_LAND_PORTAL_CREATOR.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));


    public static final RegistryObject<Item> EXPERIENCE_CRYSTAL = ITEMS.register("xp_crystal",()->new ExperienceCrystal(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1)));


    public static final RegistryObject<Item> MODULE_APPLIER = ITEMS.register("module_table",()->new BlockItem(BlocksRegistry.MODULE_APPLIER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<ModuleItem> PHYSICAL_DEFENCE_MODULE_10 = ITEMS.register("defence_module_physical_10",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.DEFENCE_MODULE_PHYSICAL_10));
    public static final RegistryObject<ModuleItem> SWORD_AUTOHEAL_MODULE = ITEMS.register("sword_heal_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.SWORD_AUTOHEAL_MODULE));
    public static final RegistryObject<ModuleItem> SWORD_AOE_ATTACK = ITEMS.register("sword_aoe_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.SWORD_AOE_ATTACK_ABILITY));
    public static final RegistryObject<ModuleItem> PICKAXE_AUTO_SMELT = ITEMS.register("pickaxe_auto_smelt_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.PICKAXES, ModuleItem.Tags.SMELTING));
    public static final RegistryObject<ModuleItem> MAGIC_DAMAGE_MODULE_5 = ITEMS.register("magic_damage_5_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5,ModuleItem.Tags.POISONING_BLADE, ModuleItem.Tags.FURY_SWIPES));
    public static final RegistryObject<ModuleItem> PICKAXE_MINER_ABILITY_MODULE = ITEMS.register("pickaxe_miner_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.PICKAXES, ModuleItem.Tags.MINER));
    public static final RegistryObject<ModuleItem> DISARMING_THORNS_MODULE = ITEMS.register("disarming_thorns_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.DISARMING_THORNS));
    public static final RegistryObject<ModuleItem> BLESSED_MODULE = ITEMS.register("blessed_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.BLESSED));
    public static final RegistryObject<ModuleItem> POISONING_BLADE_MODULE = ITEMS.register("poisoning_blade_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.POISONING_BLADE,ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5, ModuleItem.Tags.FURY_SWIPES));
    public static final RegistryObject<ModuleItem> FURY_SWIPES_MODULE = ITEMS.register("fury_swipes_module",()->new ModuleItem(new Item.Properties().tab(SolarForge.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.FURY_SWIPES,ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5, ModuleItem.Tags.POISONING_BLADE));
    public static final RegistryObject<Item> BONEMEALER = ITEMS.register("bonemealer",()->new BlockItem(BlocksRegistry.BONEMEALER.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));

}
