package com.finderfeed.solarcraft.registries.items;

import com.finderfeed.solarcraft.SolarCraft;

import com.finderfeed.solarcraft.content.blocks.FuelBlockItem;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalItem;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserBlockItem;
import com.finderfeed.solarcraft.content.items.solar_wand.SolarWandItem;
import com.finderfeed.solarcraft.content.armor.SolarArmorMaterial;
import com.finderfeed.solarcraft.content.blocks.blockitems.TurretBlockItem;
import com.finderfeed.solarcraft.content.blocks.blockitems.UldoradiumOreBlockitem;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockItem;
import com.finderfeed.solarcraft.content.items.divine_armor.*;
import com.finderfeed.solarcraft.content.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarcraft.content.items.*;
import com.finderfeed.solarcraft.content.items.primitive.*;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftArmorItem;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftBlockItem;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_disc_gun.SolarDiscGunItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.vein_miner.IllidiumPickaxe;
import com.finderfeed.solarcraft.misc_things.*;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.finderfeed.solarcraft.SolarCraft.*;
public class SolarcraftItems {

    public static Map<CreativeModeTab,List<RegistryObject<? extends Item>>> itemTabs = new HashMap<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"solarcraft");

    public static final RegistryObject<SolarWandItem> SOLAR_WAND = registerItem(ITEMS.register("solar_wand",()-> new SolarWandItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1))),SolarCraft.SOLAR_GROUP);
    public static  final RegistryObject<Item> SOLAR_INFUSION_POOL = registerItem(ITEMS.register("solar_forge_infusion_pool",()-> new SolarcraftBlockItem(SolarcraftBlocks.INFUSING_POOL.get(),new Item.Properties().rarity(Rarity.EPIC),()->AncientFragment.SOLAR_INFUSER)),SolarCraft.SOLAR_GROUP_BLOCKS);
    public static  final RegistryObject<SolarcraftItem> SOLAR_DUST = registerItem(ITEMS.register("solar_dust",()-> new SolarcraftItem(new Item.Properties(),()->AncientFragment.DUSTS)),SolarCraft.SOLAR_GROUP_MATERIALS);
    public static  final RegistryObject<Item> VOID_DUST = registerItem(ITEMS.register("void_dust",()-> new SolarcraftItem(new Item.Properties(),()->AncientFragment.DUSTS)),SolarCraft.SOLAR_GROUP_MATERIALS);
    public static final RegistryObject<Item> ENERGY_DUST = registerItem(ITEMS.register("energy_dust",()->new SolarcraftItem(new Item.Properties(),()->AncientFragment.DUSTS)),SolarCraft.SOLAR_GROUP_MATERIALS);
    public static  final RegistryObject<Item> ENDERITE_ESSENCE = registerItem(ITEMS.register("enderite_essence",()-> new Item(new Item.Properties().rarity(Rarity.RARE))),SolarCraft.SOLAR_GROUP_MATERIALS);
    public static final RegistryObject<Item> SOLAR_STONE = registerItem(ITEMS.register("solar_stone",()->new ProgressionBlockItem(SolarcraftBlocks.SOLAR_STONE.get(),new Item.Properties())),SolarCraft.SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<ProgressionBlockItem> LENSING_CRYSTAL_ORE = registerItem(ITEMS.register("lensing_crystal_ore",()->new ProgressionBlockItem(SolarcraftBlocks.LENSING_CRYSTAL_ORE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> ENDER_CRACKS = registerItem(ITEMS.register("ender_cracks",()->new ProgressionBlockItem(SolarcraftBlocks.ENDER_CRACKS.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_STONE_CHISELED = registerItem(ITEMS.register("chiseled_solar_stone",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_CHISELED.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN = registerItem(ITEMS.register("solar_stone_collumn",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_COLLUMN.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_STONE_BRICKS = registerItem(ITEMS.register("solar_stone_bricks",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_BRICKS.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> CATALYST_BASE = registerItem(ITEMS.register("catalyst_base",()->new SolarcraftBlockItem(SolarcraftBlocks.CATALYST_BASE.get(),new Item.Properties(),()->AncientFragment.CATALYSTS)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ENERGIZED_STONE = registerItem(ITEMS.register("energized_stone",()->new BlockItem(SolarcraftBlocks.ENERGIZED_STONE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_STONE_STAIRS = registerItem(ITEMS.register("solar_stone_stairs",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_STAIRS.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_STONE_SLAB = registerItem(ITEMS.register("solar_stone_slab",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_SLAB.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> INFUSING_TABLE = registerItem(ITEMS.register("infusing_crafting_table",()->new BlockItem(SolarcraftBlocks.INFUSING_CRAFTING_TABLE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SPEED_ROAD = registerItem(ITEMS.register("speed_road",()->new SolarcraftBlockItem(SolarcraftBlocks.SPEED_ROAD.get(),new Item.Properties(),()->AncientFragment.SPEED_ROAD)),SOLAR_GROUP_BLOCKS);


    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN_HORIZONTAL = registerItem(ITEMS.register("solar_stone_collumn_horizontal",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_COLLUMN_HORIZONTAL.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    //solar armor
    public static final RegistryObject<ArmorItem> SOLAR_HELMET = registerItem(ITEMS.register("solar_helmet",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.HEAD,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));
    public static final RegistryObject<ArmorItem> SOLAR_CHESTPLATE = registerItem(ITEMS.register("solar_chestplate",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));
    public static final RegistryObject<ArmorItem> SOLAR_LEGGINGS = registerItem(ITEMS.register("solar_leggins",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.LEGS,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));
    public static final RegistryObject<ArmorItem> SOLAR_BOOTS = registerItem(ITEMS.register("solar_boots",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.FEET,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));

    public static final RegistryObject<ArmorItem> DIVINE_HELMET = registerItem(ITEMS.register("divine_helmet",()-> new DivineHelmet(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.HEAD,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));
    public static final RegistryObject<ArmorItem> DIVINE_CHESTPLATE = registerItem(ITEMS.register("divine_chestplate",()-> new DivineChestplate(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));
    public static final RegistryObject<ArmorItem> DIVINE_LEGGINGS = registerItem(ITEMS.register("divine_leggings",()-> new DivineLeggings(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.LEGS,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));
    public static final RegistryObject<ArmorItem> DIVINE_BOOTS = registerItem(ITEMS.register("divine_boots",()-> new DivineBoots(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.FEET,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));

    //radiant armor
    public static final RegistryObject<RadiantChestplate> RADIANT_CHESTPLATE = registerItem(ITEMS.register("radiant_chestplate",()-> new RadiantChestplate(SolarArmorMaterial.RADIANT_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.RADIANT_CHESTPLATE));
    //lexicon
    public static  final RegistryObject<SolarLexicon> SOLAR_LEXICON = registerItem(ITEMS.register("solar_lexicon",()-> new SolarLexicon(new Item.Properties().tab(SolarCraft.SOLAR_GROUP).stacksTo(1)));

    public static final RegistryObject<VoidBlockWand> VOID_BLOCK_WAND = registerItem(ITEMS.register("void_block_wand",()-> new VoidBlockWand(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SOLAR_KEY = registerItem(ITEMS.register("solar_key",()-> new RareSolarcraftItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->null));
    public static final RegistryObject<Item> COLD_STAR_PIECE = registerItem(ITEMS.register("cold_star_piece",()-> new ColdStarPieceItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_MATERIALS).stacksTo(1)));
    public static final RegistryObject<Item> COLD_STAR_PIECE_ACTIVATED = registerItem(ITEMS.register("cold_star_piece_activated",()-> new ChargedColdStar(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> KEY_LOCK_BLOCK = registerItem(ITEMS.register("key_lock_block",()->new BlockItem(SolarcraftBlocks.KEY_LOCK_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> INVINCIBLE_STONE = registerItem(ITEMS.register("invincible_solar_stone",()->new BlockItem(SolarcraftBlocks.INVINCIBLE_STONE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_LENS = registerItem(ITEMS.register("solar_lens",()->new SolarLensBlockItem(SolarcraftBlocks.SOLAR_LENS.get(),new Item.Properties(),()->AncientFragment.SOLAR_LENS)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> KEY_DEFENDER = registerItem(ITEMS.register("defence_trap_block",()->new BlockItem(SolarcraftBlocks.KEY_DEFENDER.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> COLD_STAR_INFUSER = registerItem(ITEMS.register("cold_star_charger",()->new BlockItem(SolarcraftBlocks.COLD_STAR_INFUSER.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> ILLIDIUM_INGOT = registerItem(ITEMS.register("illidium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()-> AncientFragment.ILLIDIUM_INGOT));
    public static final RegistryObject<Item> ALGADIUM_INGOT = registerItem(ITEMS.register("algadium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.ALGADIUM_INGOT));
    public static final RegistryObject<Item> QUALADIUM_INGOT = registerItem(ITEMS.register("qualadium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.QUALADIUM_INGOT));
    public static final RegistryObject<Item> ENERGETIC_INGOT = registerItem(ITEMS.register("energetic_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.ENERGETIC_INGOT));
    public static final RegistryObject<Item> ENDERIUM_INGOT = registerItem(ITEMS.register("enderium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.ENDERIUM_INGOT));

    public static final RegistryObject<Item> LENSING_CRYSTAL = registerItem(ITEMS.register("lensing_crystal",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS)));

    public static final RegistryObject<Item> ENDER_RADAR = registerItem(ITEMS.register("ender_radar",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1),()->AncientFragment.ENDER_RADAR){
        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    });
    public static final RegistryObject<Item> CHARGED_QUALADIUM_INGOT = registerItem(ITEMS.register("charged_qualadium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> AMETHYST_CORE = registerItem(ITEMS.register("amethyst_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.AMETHYST_CORE));
    public static final RegistryObject<Item> RUNIC_CORE = registerItem(ITEMS.register("runic_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.RUNIC_CORE));

    public static final RegistryObject<Item> CRYSTAL_CORE = registerItem(ITEMS.register("crystal_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> CRYSTAL_STAR = registerItem(ITEMS.register("crystal_star",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> ENERGY_CORE = registerItem(ITEMS.register("energy_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> VOID_CORE = registerItem(ITEMS.register("void_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> MATERIALIZATION_CORE = registerItem(ITEMS.register("materialization_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));

    public static final RegistryObject<Item> GEMINIUM_INGOT = registerItem(ITEMS.register("geminium_ingot",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.GEMINIUM_INGOT));
    public static final RegistryObject<Item> TURRET_RADAR = registerItem(ITEMS.register("turret_radar",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.TURRET_RADAR));
    public static final RegistryObject<Item> SMALL_SOLAR_REACTOR = registerItem(ITEMS.register("small_solar_reactor",()-> new RareSolarcraftItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.SMALL_SOLAR_REACTOR));
    public static final RegistryObject<Item> MEDIUM_SOLAR_REACTOR = registerItem(ITEMS.register("medium_solar_reactor",()-> new RareSolarcraftItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.MEDIUM_SOLAR_REACTOR));
    public static final RegistryObject<Item> ILLIDIUM_SWORD = registerItem(ITEMS.register("illidium_sword",()-> new IllidiumSword());
    public static final RegistryObject<Item> ILLIDIUM_AXE = registerItem(ITEMS.register("illidium_axe",()->new IllidiumAxe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,4,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.ILLIDIUM_AXE));
    public static final RegistryObject<Item> GROWTH_HOE = registerItem(ITEMS.register("illidium_hoe",()-> new IllidiumHoe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,-4, 0.0F, (new Item.Properties()).rarity(Rarity.RARE).stacksTo(1).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.QUALADIUM_HOE));
    public static final RegistryObject<Item> VEIN_MINER = registerItem(ITEMS.register("miner_item",()-> new IllidiumPickaxe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER, 1, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1),()->AncientFragment.ILLIDIUM_PICKAXE));
    public static final RegistryObject<Item> ILLIDIUM_SHOVEL = registerItem(ITEMS.register("illidium_shovel",()-> new RareSolarcraftShovelItem(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,1,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.ILLIDIUM_SHOVEL));
    public static final RegistryObject<Item> QUALADIUM_SHOVEL = registerItem(ITEMS.register("qualadium_shovel",()-> new RareSolarcraftShovelItem(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.QUALADIUM_SHOVEL));
    public static final RegistryObject<Item> QUALADIUM_SWORD = registerItem(ITEMS.register("qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_WEAPONS),()->AncientFragment.QUALADIUM_SWORD));
    public static final RegistryObject<Item> QUALADIUM_HOE = registerItem(ITEMS.register("qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.QUALADIUM_HOE));
    public static final RegistryObject<Item> QUALADIUM_PICKAXE = registerItem(ITEMS.register("qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1),()->AncientFragment.QUALADIUM_PICKAXE));
    public static final RegistryObject<Item> QUALADIUM_AXE = registerItem(ITEMS.register("qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()-> AncientFragment.QUALADIUM_AXE));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SHOVEL = registerItem(ITEMS.register("charged_qualadium_shovel",()-> new RareSolarcraftShovelItem(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SWORD = registerItem(ITEMS.register("charged_qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_WEAPONS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_HOE = registerItem(ITEMS.register("charged_qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_PICKAXE = registerItem(ITEMS.register("charged_qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_AXE = registerItem(ITEMS.register("charged_qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS), ()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> SOLAR_DISC_LAUNCHER = registerItem(ITEMS.register("solar_disc_launcher",()-> new SolarDiscGunItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_WEAPONS).stacksTo(1),()->AncientFragment.DISC_LAUNCHER));
    public static final RegistryObject<Item> MAGNET_BLOCK = registerItem(ITEMS.register("magnet_block",()->new BlockItem(SolarcraftBlocks.MAGNET_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_DISC = registerItem(ITEMS.register("to_render_item",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ENERGY_GENERATOR_BLOCK = registerItem(ITEMS.register("solar_energy_generator",()->new SolarEnergyGeneratorBlockItem());
    public static final RegistryObject<Item> SOLAR_ENERGY_REPEATER = registerItem(ITEMS.register("solar_energy_repeater",()->new SolarcraftBlockItem(SolarcraftBlocks.SOLAR_REPEATER.get(),new Item.Properties(),()->AncientFragment.SOLAR_ENERGY_REPEATER)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> EXPLOSION_BLOCKER = registerItem(ITEMS.register("explosion_blocker",()->new SolarcraftBlockItem(SolarcraftBlocks.EXPLOSION_BLOCKER.get(),new Item.Properties(),()->AncientFragment.EXPLOSION_BLOCKER)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ENCHANTER = registerItem(ITEMS.register("elemental_enchanter",()->new SolarcraftBlockItem(SolarcraftBlocks.ENCHANTER.get(),new Item.Properties(),()->AncientFragment.ENCHANTER){
        @Override
        public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level world, List<Component> cmps, TooltipFlag flag) {

            super.appendHoverText(stack, world, cmps, flag);
        }
    }),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> RUNIC_ENERGY_CHARGER = registerItem(ITEMS.register("runic_energy_charger",()->new SolarcraftBlockItem(SolarcraftBlocks.RUNIC_ENERGY_CHARGER.get(),new Item.Properties(),()->null){
        @Override
        public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level world, List<Component> cmps, TooltipFlag flag) {
            
            super.appendHoverText(stack, world, cmps, flag);
        }
    }),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> SOLAR_NETWORK_BINDER = registerItem(ITEMS.register("solar_network_binder",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> ILLIDIUM_BLOCK = registerItem(ITEMS.register("illidium_block",()->new SolarcraftBlockItem(SolarcraftBlocks.ILLIDIUM_BLOCK.get(),new Item.Properties(),()->AncientFragment.ILLIDIUM_INGOT)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ALGADIUM_BLOCK = registerItem(ITEMS.register("algadium_block",()->new SolarcraftBlockItem(SolarcraftBlocks.ALGADIUM_BLOCK.get(),new Item.Properties(),()->AncientFragment.ALGADIUM_INGOT)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_CORE = registerItem(ITEMS.register("solar_core_block",()->new SolarcraftBlockItem(SolarcraftBlocks.SOLAR_CORE.get(),new Item.Properties(),()->AncientFragment.SOLAR_CORE)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> AURA_HEALER = registerItem(ITEMS.register("aura_healer_block",()->new RareSolarcraftBlockItem(SolarcraftBlocks.AURA_HEALER_BLOCK.get(),new Item.Properties(),()->AncientFragment.AURA_HEALER)),SOLAR_GROUP_BLOCKS);
    public static  final RegistryObject<TotemOfImmortality> TOTEM_OF_IMMORTALITY = registerItem(ITEMS.register("totem_of_immortality",()-> new TotemOfImmortality(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP).stacksTo(1),()->AncientFragment.TOTEM_OF_IMMORTALITY));
    public static  final RegistryObject<ShieldOfSolarGod> SOLAR_GOD_SHIELD = registerItem(ITEMS.register("solar_god_shield",()-> new ShieldOfSolarGod(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_WEAPONS).durability(3000),()->AncientFragment.SOLAR_GOD_SHIELD));
    public static final RegistryObject<BlockBoomerang> BLOCK_BOOMERANG = registerItem(ITEMS.register("block_boomerang",()-> new BlockBoomerang(new Item.Properties().stacksTo(1).tab(SolarCraft.SOLAR_GROUP).rarity(Rarity.UNCOMMON),()->AncientFragment.BLOCK_BOOMERANG));
//    public static final RegistryObject<ManaAmulet> SOLAR_MANA_AMULET = registerItem(ITEMS.register("solar_mana_amulet",()-> new ManaAmulet(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON),()->null));
    public static final RegistryObject<UltraCrossbowItem> ULTRA_CROSSBOW = registerItem(ITEMS.register("solar_crossbow",()-> new UltraCrossbowItem(new Item.Properties().stacksTo(1).tab(SolarCraft.SOLAR_GROUP_WEAPONS).rarity(Rarity.UNCOMMON),()->AncientFragment.SOLAR_CROSSBOW));
    public static final RegistryObject<LightningGun> LIGHTNING_GUN = registerItem(ITEMS.register("lightning_gun",()-> new LightningGun(new Item.Properties().stacksTo(1).tab(SolarCraft.SOLAR_GROUP_WEAPONS).rarity(Rarity.UNCOMMON),()->AncientFragment.LIGHTNING_GUN));
    public static final RegistryObject<Item> SOLAR_MORTAR = registerItem(ITEMS.register("solar_mortar_block",()->new RareSolarcraftBlockItem(SolarcraftBlocks.SOLAR_MORTAR_BLOCK.get(),new Item.Properties(),()->AncientFragment.SOLAR_MORTAR)),SOLAR_GROUP_BLOCKS);
//    public static final RegistryObject<Item> ENERGY_METER = registerItem(ITEMS.register("energy_meter",()->new EnergyMeter(new Item.Properties().tab(SolarCraft.SOLAR_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SOLAR_FLOWER = registerItem(ITEMS.register("solar_flower",()->new BlockItem(SolarcraftBlocks.SOLAR_FLOWER.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> VOID_LILY = registerItem(ITEMS.register("void_lily",()->new BlockItem(SolarcraftBlocks.VOID_LILY.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> CRYSTAL_FLOWER = registerItem(ITEMS.register("crystal_flower",()->new BlockItem(SolarcraftBlocks.CRYSTAL_FLOWER.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> SOLAR_FURNACE_BLOCK = registerItem(ITEMS.register("solar_furnace",()->new RareSolarcraftBlockItem(SolarcraftBlocks.SOLAR_FURNACE.get(),new Item.Properties(),()->AncientFragment.SOLAR_FURNACE)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> TURRET_BLOCK = registerItem(ITEMS.register("turret_block",()->new TurretBlockItem(SolarcraftBlocks.TURRET_BLOCK.get(),new Item.Properties(),()->AncientFragment.SOLAR_TURRET)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ZAP_TURRET_BLOCK = registerItem(ITEMS.register("zap_turret_block",()->new RareSolarcraftBlockItem(SolarcraftBlocks.ZAP_TURRET_BLOCK.get(),new Item.Properties(),()->AncientFragment.ZAP_TURRET)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> SOLAR_GOD_SWORD = registerItem(ITEMS.register("solar_god_sword",()->new SolarGodSword(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,-3,-2.4f,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_WEAPONS).stacksTo(1),()->AncientFragment.SOLAR_GOD_SWORD));
    public static final RegistryObject<Item> SOLAR_GOD_PICKAXE = registerItem(ITEMS.register("solar_god_pickaxe",()-> new SolarGodPickaxe(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER, -6, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.SOLAR_GOD_PICKAXE));
    public static final RegistryObject<Item> ULDORADIUM_ORE = registerItem(ITEMS.register("uldoradium_ore",()->new UldoradiumOreBlockitem(SolarcraftBlocks.ULDORADIUM_ORE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RAY_TRAP_BLOCK = registerItem(ITEMS.register("ray_trap_block",()->new BlockItem(SolarcraftBlocks.RAY_TRAP_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> TRAP_CONTROLLER = registerItem(ITEMS.register("trap_controller",()->new BlockItem(SolarcraftBlocks.TRAP_CONTROLLER.get(),new Item.Properties()));
    public static final RegistryObject<Item> BLUE_GEM = registerItem(ITEMS.register("blue_gem",()->new BlueGemItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_ENCHANCED = registerItem(ITEMS.register("blue_gem_enriched",()->new EnchancedBlueGem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_DOOR_BLOCK = registerItem(ITEMS.register("blue_gem_door_block",()->new BlockItem(SolarcraftBlocks.BLUE_GEM_DOOR_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> ASH_LEAVES = registerItem(ITEMS.register("ash_leaves",()->new BlockItem(SolarcraftBlocks.ASH_LEAVES.get(),new Item.Properties()));
    public static final RegistryObject<Item> BURNT_LOG = registerItem(ITEMS.register("burnt_log",()->new BlockItem(SolarcraftBlocks.BURNT_LOG.get(),new Item.Properties()));
    public static final RegistryObject<Item> DEAD_SPROUT = registerItem(ITEMS.register("dead_sprout",()->new BlockItem(SolarcraftBlocks.DEAD_SPROUT.get(),new Item.Properties()));
    public static final RegistryObject<Item> FIRST_DIMENSIONAL_SHARD = registerItem(ITEMS.register("first_dimensional_shard",()->new Item(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ASH_ITEM = registerItem(ITEMS.register("ash",()->new AshItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));

    public static final RegistryObject<Item> INFO_FRAGMENT = registerItem(ITEMS.register("ancient_fragment",()->new AncientFragmentItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_FRAGMENTS)));

    public static final RegistryObject<Item> RUNIC_TABLE = registerItem(ITEMS.register("runic_table",()->new SolarcraftBlockItem(SolarcraftBlocks.RUNIC_TABLE.get(),new Item.Properties(),()->AncientFragment.RUNIC_TABLE));

    public static final RegistryObject<Item> DAMAGE_AMPLIFICATION_BLOCK = registerItem(ITEMS.register("damage_amp_block",()->new BlockItem(SolarcraftBlocks.DAMAGE_AMPLIFICATION_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ARMOR_AMPLIFICATION_BLOCK = registerItem(ITEMS.register("armor_amp_block",()->new BlockItem(SolarcraftBlocks.ARMOR_AMPLIFICATION_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> REGEN_AMPLIFICATION_BLOCK = registerItem(ITEMS.register("regen_amp_block",()->new BlockItem(SolarcraftBlocks.REGENERATION_AMPLIFICATION_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> EVASION_AMPLIFICATION_BLOCK = registerItem(ITEMS.register("evasion_amp_block",()->new BlockItem(SolarcraftBlocks.EVASION_AMPLIFICATION_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> RUNIC_TREE_SAPLING = registerItem(ITEMS.register("runic_tree_sapling",()->new BlockItem(SolarcraftBlocks.RUNIC_TREE_SAPLING.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RUNIC_LEAVES = registerItem(ITEMS.register("runic_leaves",()->new BlockItem(SolarcraftBlocks.RUNIC_LEAVES.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RUNIC_LOG = registerItem(ITEMS.register("runic_log",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_LOG.get(),new Item.Properties(),600)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RUNIC_PLANKS = registerItem(ITEMS.register("runic_planks",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_PLANKS.get(),new Item.Properties(),300)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RUNIC_STAIRS = registerItem(ITEMS.register("runic_stairs",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_STAIRS.get(),new Item.Properties(),300)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RUNIC_SLAB = registerItem(ITEMS.register("runic_slab",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_SLAB.get(),new Item.Properties(),300)),SOLAR_GROUP_BLOCKS);


    public static final RegistryObject<Item> RUNE_ENERGY_PYLON = registerItem(ITEMS.register("rune_energy_pylon",()->new EnergyPylonBlockItem(SolarcraftBlocks.RUNE_ENERGY_PYLON.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> INSCRIPTION_STONE = registerItem(ITEMS.register("inscription_stone",()->new BlockItem(SolarcraftBlocks.INSCRIPTION_STONE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> DEBUG_STICK = registerItem(ITEMS.register("solar_debug_stick",()->new SolarcraftDebugStick(new Item.Properties()));
    //runes
    public static final RegistryObject<Item> SOLAR_RUNE_BASE = registerItem(ITEMS.register("runestone",()->new RuneBase(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS)));

    public static final RegistryObject<Item> CRYSTALLITE_CORE = registerItem(ITEMS.register("crystallite_core",()->new RareSolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.DIMENSION_CORE));


    public static final RegistryObject<Item> SOLAR_RUNE_ZETA = registerItem(ITEMS.register("solar_rune_zeta",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.ZETA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_ARDO = registerItem(ITEMS.register("solar_rune_ardo",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.ARDO,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_URBA = registerItem(ITEMS.register("solar_rune_urba",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.URBA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_KELDA = registerItem(ITEMS.register("solar_rune_kelda",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.KELDA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_FIRA = registerItem(ITEMS.register("solar_rune_fira",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.FIRA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_TERA = registerItem(ITEMS.register("solar_rune_tera",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.TERA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_GIRO = registerItem(ITEMS.register("solar_rune_giro",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.GIRO,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_ULTIMA = registerItem(ITEMS.register("solar_rune_ultima",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.ULTIMA,()->null));

    public static final RegistryObject<Item> EMPTY_CRYSTAL = registerItem(ITEMS.register("empty_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> ZETA_CRYSTAL = registerItem(ITEMS.register("zeta_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> ARDO_CRYSTAL = registerItem(ITEMS.register("ardo_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> URBA_CRYSTAL = registerItem(ITEMS.register("urba_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> KELDA_CRYSTAL = registerItem(ITEMS.register("kelda_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> FIRA_CRYSTAL = registerItem(ITEMS.register("fira_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> TERA_CRYSTAL = registerItem(ITEMS.register("tera_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> GIRO_CRYSTAL = registerItem(ITEMS.register("giro_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> ULTIMA_CRYSTAL = registerItem(ITEMS.register("ultima_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));


    public static final RegistryObject<Item> SOLAR_GOD_BOW = registerItem(ITEMS.register("solar_god_bow",()->new SolarGodBow(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_WEAPONS).stacksTo(1).durability(8000),()->AncientFragment.SOLAR_GOD_BOW));


    public static final RegistryObject<Item> REGEN_AMULET = registerItem(ITEMS.register("regen_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.REGENERATION,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> STRENGTH_AMULET = registerItem(ITEMS.register("strength_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.DAMAGE_BOOST,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> NIGHT_VISION_AMULET = registerItem(ITEMS.register("night_vision_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.NIGHT_VISION,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> SPEED_AMULET = registerItem(ITEMS.register("speed_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.MOVEMENT_SPEED,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> HASTE_AMULET = registerItem(ITEMS.register("haste_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.DIG_SPEED,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> JUMP_AMULET = registerItem(ITEMS.register("jump_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.JUMP,()->AncientFragment.AMULETS));


    public static final RegistryObject<Item> REPEATER = registerItem(ITEMS.register("repeater",()->new RunicNetworkRepeaterBlockItem(SolarcraftBlocks.REPEATER.get(),new Item.Properties(),()->AncientFragment.RUNIC_ENERGY_REPEATER));

    public static final RegistryObject<Item> ARDO_RUNE_BLOCK = registerItem(ITEMS.register("ardo_rune_block",()->new     BlockItem(SolarcraftBlocks.ARDO_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ZETA_RUNE_BLOCK = registerItem(ITEMS.register("zeta_rune_block",()->new     BlockItem(SolarcraftBlocks.ZETA_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> KELDA_RUNE_BLOCK = registerItem(ITEMS.register("kelda_rune_block",()->new   BlockItem(SolarcraftBlocks.KELDA_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> URBA_RUNE_BLOCK = registerItem(ITEMS.register("urba_rune_block",()->new     BlockItem(SolarcraftBlocks.URBA_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> TERA_RUNE_BLOCK = registerItem(ITEMS.register("tera_rune_block",()->new     BlockItem(SolarcraftBlocks.TERA_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> FIRA_RUNE_BLOCK = registerItem(ITEMS.register("fira_rune_block",()->new     BlockItem(SolarcraftBlocks.FIRA_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> GIRO_RUNE_BLOCK = registerItem(ITEMS.register("giro_rune_block",()->new     BlockItem(SolarcraftBlocks.GIRO_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ULTIMA_RUNE_BLOCK = registerItem(ITEMS.register("ultima_rune_block",()->new BlockItem(SolarcraftBlocks.ULTIMA_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> MULTIREPEATER_BLOCK = registerItem(ITEMS.register("multirune_block",()->new BlockItem(SolarcraftBlocks.MULTIREPEATER_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);


    public static final RegistryObject<Item> RADIANT_CRYSTAL = registerItem(ITEMS.register("radiant_crystal",()->new BlockItem(SolarcraftBlocks.RADIANT_CRYSTAL.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> WORMHOLE = registerItem(ITEMS.register("wormhole",()->new BlockItem(SolarcraftBlocks.WORMHOLE.get(),new Item.Properties()));
    public static final RegistryObject<Item> REACH_GLOVES = registerItem(ITEMS.register("gloves_of_reach",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1),()->AncientFragment.GLOVES_OF_REACH){
        @Override
        public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> text, TooltipFlag p_41424_) {
            text.add(Component.translatable("gloves_of_reach_active").withStyle(ChatFormatting.GOLD));
            super.appendHoverText(p_41421_, p_41422_, text, p_41424_);
        }
    });
    public static final RegistryObject<Item> DIMENSION_CORE = registerItem(ITEMS.register("dimension_core",()->new RadiantCoreBlockItem(SolarcraftBlocks.DIMENSION_CORE.get(),new Item.Properties(),()->AncientFragment.DIMENSION_CORE)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_LEAVES = registerItem(ITEMS.register("radiant_leaves",()->new BlockItem(SolarcraftBlocks.RADIANT_LEAVES.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_GRASS = registerItem(ITEMS.register("radiant_grass",()->new BlockItem(SolarcraftBlocks.RADIANT_GRASS.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_GRASS_NOT_BLOCK = registerItem(ITEMS.register("radiant_grass_grass",()->new BlockItem(SolarcraftBlocks.RADIANT_GRASS_NOT_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_LOG = registerItem(ITEMS.register("radiant_log",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_LOG.get(),new Item.Properties(),600)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_PLANKS = registerItem(ITEMS.register("radiant_planks",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_PLANKS.get(),new Item.Properties(),300)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_STAIRS = registerItem(ITEMS.register("radiant_stairs",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_WOOD_STAIRS.get(),new Item.Properties(),300)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_SLAB = registerItem(ITEMS.register("radiant_slab",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_WOOD_SLAB.get(),new Item.Properties(),300)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> RADIANT_BERRY = registerItem(ITEMS.register("radiant_berry",()->new RadiantBerry(new Item.Properties().tab(SolarCraft.SOLAR_GROUP).food(new FoodProperties.Builder().alwaysEat().nutrition(5).saturationMod(0.9F).build())));
    public static final RegistryObject<Item> RADIANT_BERRY_BUSH = registerItem(ITEMS.register("radiant_berry_bush",()->new BlockItem(SolarcraftBlocks.RADIANT_BERRY_BUSH.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);


//    public static final RegistryObject<Item> RADIANT_PORTAL_CREATOR = registerItem(ITEMS.register("radiant_portal_creator",()->new PortalCreatorBlockItem(SolarcraftBlocks.RADIANT_LAND_PORTAL_CREATOR.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));


    public static final RegistryObject<Item> EXPERIENCE_CRYSTAL = registerItem(ITEMS.register("xp_crystal",()->new ExperienceCrystal(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1),()->AncientFragment.EXPERIENCE_CRYSTAL));


    public static final RegistryObject<Item> MODULE_APPLIER = registerItem(ITEMS.register("module_table",()->new SolarcraftBlockItem(SolarcraftBlocks.MODULE_APPLIER.get(),new Item.Properties(),()->AncientFragment.MODULES)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<ModuleItem> PHYSICAL_DEFENCE_MODULE_10 = registerItem(ITEMS.register("defence_module_physical_10",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.DEFENCE_MODULE_PHYSICAL_10,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> SWORD_AUTOHEAL_MODULE = registerItem(ITEMS.register("sword_heal_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.SWORD_AUTOHEAL_MODULE,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> SWORD_AOE_ATTACK = registerItem(ITEMS.register("sword_aoe_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.SWORD_AOE_ATTACK_ABILITY,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> PICKAXE_AUTO_SMELT = registerItem(ITEMS.register("pickaxe_auto_smelt_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.PICKAXES, ModuleItem.Tags.SMELTING,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> MAGIC_DAMAGE_MODULE_5 = registerItem(ITEMS.register("magic_damage_5_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5,()->AncientFragment.MODULES,ModuleItem.Tags.POISONING_BLADE, ModuleItem.Tags.FURY_SWIPES));
    public static final RegistryObject<ModuleItem> PICKAXE_MINER_ABILITY_MODULE = registerItem(ITEMS.register("pickaxe_miner_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.PICKAXES, ModuleItem.Tags.MINER,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> DISARMING_THORNS_MODULE = registerItem(ITEMS.register("disarming_thorns_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.DISARMING_THORNS,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> BLESSED_MODULE = registerItem(ITEMS.register("blessed_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.BLESSED,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> POISONING_BLADE_MODULE = registerItem(ITEMS.register("poisoning_blade_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.POISONING_BLADE,()->AncientFragment.MODULES,ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5, ModuleItem.Tags.FURY_SWIPES));
    public static final RegistryObject<ModuleItem> FURY_SWIPES_MODULE = registerItem(ITEMS.register("fury_swipes_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.FURY_SWIPES,()->AncientFragment.MODULES,ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5, ModuleItem.Tags.POISONING_BLADE));
    public static final RegistryObject<Item> BONEMEALER = registerItem(ITEMS.register("bonemealer",()->new SolarcraftBlockItem(SolarcraftBlocks.BONEMEALER.get(),new Item.Properties(),()->AncientFragment.BONEMEALER)),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> CRYSTALLIZED_RUNIC_ENERGY = registerItem(ITEMS.register("crystallized_runic_energy",()->new BlockItem(SolarcraftBlocks.CRYSTALLIZED_RUNIC_ENERGY.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> CRYSTAL = registerItem(ITEMS.register("crystal",()->new BlockItem(SolarcraftBlocks.CRYSTAL.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> CORRUPTED_STONE = registerItem(ITEMS.register("corrupted_stone",()->new BlockItem(SolarcraftBlocks.CORRUPTED_STONE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ULDERA_RUNE_BLOCK = registerItem(ITEMS.register("uldera_rune_block",()->new BlockItem(SolarcraftBlocks.ULDERA_RUNE_BLOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> CRYSTAL_HEART = registerItem(ITEMS.register("crystal_heart",()->new RareSolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC),()->null));
    public static final RegistryObject<Item> CRYSTAL_HEART_PIECE = registerItem(ITEMS.register("crystal_heart_piece",()->new RareSolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC),()->null));
    public static final RegistryObject<Item> CRYSTAL_ENERGY_VINES = registerItem(ITEMS.register("crystal_energy_vines",()->new BlockItem(SolarcraftBlocks.CRYSTAL_ENERGY_VINES.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));
    public static final RegistryObject<Item> CLEARING_CRYSTAL_RITUAL = registerItem(ITEMS.register("clearing_ritual_crystal",()->new ClearingRitualCrystalItem(SolarcraftBlocks.CLEARING_RITUAL_CRYSTAL.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));

    public static final RegistryObject<Item> CLEARING_RITUAL_MAIN_BLOCK = registerItem(ITEMS.register("clearing_ritual_main_block",()->new BlockItem(SolarcraftBlocks.CLEARING_RITUAL_MAIN_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> THROWN_LIGHT = registerItem(ITEMS.register("thrown_light",()->new ThrownLight(SolarcraftBlocks.THROWN_LIGHT.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));

    public static  final RegistryObject<Item> SOLAR_SHARD = registerItem(ITEMS.register("solar_shard",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP)));
    public static  final RegistryObject<Item> SOLAR_FORGE_ITEM = registerItem(ITEMS.register("solar_forge",()-> new SolarForgeBlockItem(SolarCraft.SOLAR_FORGE.get(),new Item.Properties().rarity(Rarity.EPIC).stacksTo(1))),SOLAR_GROUP_BLOCKS);
    public static  final RegistryObject<Item> SOLAR_ORE_ITEM = registerItem(ITEMS.register("solar_ores",()-> new ProgressionBlockItem(SolarCraft.SOLAR_ORE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static  final RegistryObject<Item> INFUSER_ITEM = registerItem(ITEMS.register("solar_infuser",()-> new InfuserBlockItem(SolarCraft.SOLAR_INFUSER.get(),new Item.Properties().rarity(Rarity.EPIC).stacksTo(1),()-> AncientFragment.SOLAR_INFUSER)),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<ProgressionBlockItem> MAGISTONE = registerItem(ITEMS.register("magistone",()->new ProgressionBlockItem(SolarcraftBlocks.MAGISTONE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> MAGISTONE_STAIRS = registerItem(ITEMS.register("magistone_stairs",()->new BlockItem(SolarcraftBlocks.MAGISTONE_STAIRS.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> MAGISTONE_SLAB = registerItem(ITEMS.register("magistone_slab",()->new BlockItem(SolarcraftBlocks.MAGISTONE_SLAB.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> MAGISTONE_RAY = registerItem(ITEMS.register("magistone_ray",()->new BlockItem(SolarcraftBlocks.MAGISTONE_RAY.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> MAGISTONE_COLUMN = registerItem(ITEMS.register("magistone_column",()->new BlockItem(SolarcraftBlocks.MAGISTONE_COLUMN.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> CHISELED_MAGISTONE = registerItem(ITEMS.register("chiseled_magistone",()->new BlockItem(SolarcraftBlocks.CHISELED_MAGISTONE.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> MAGISTONE_BRICKS = registerItem(ITEMS.register("magistone_bricks",()->new BlockItem(SolarcraftBlocks.MAGISTONE_BRICKS.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> BEAM_INPUT = registerItem(ITEMS.register("beam_input",()->new BlockItem(SolarcraftBlocks.BEAM_INPUT.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> SUN_SHARD_LOCK = registerItem(ITEMS.register("sun_shard_lock",()->new BlockItem(SolarcraftBlocks.SUN_SHARD_LOCK.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> BEAM_REFLECTOR = registerItem(ITEMS.register("beam_reflector",()->new BlockItem(SolarcraftBlocks.BEAM_REFLECTOR.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> BEAM_GENERATOR = registerItem(ITEMS.register("beam_generator",()->new BlockItem(SolarcraftBlocks.BEAM_GENERATOR.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ELEMENT_WEAVER = registerItem(ITEMS.register("element_weaver",()->new BlockItem(SolarcraftBlocks.ELEMENT_WEAVER.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);
    public static final RegistryObject<Item> ORBITAL_MISSILE_LAUNCHER = registerItem(ITEMS.register("orbital_missile_launcher",()->new BlockItem(SolarcraftBlocks.ORBITAL_MISSILE_LAUNCHER.get(),new Item.Properties())),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> RUNIC_ENERGY_CORE = registerItem(ITEMS.register("runic_energy_core",()->new SolarcraftBlockItem(SolarcraftBlocks.RUNIC_ENERGY_CORE.get(),new Item.Properties(),()->AncientFragment.RUNIC_ENERGY_CORE)),SOLAR_GROUP_BLOCKS);

    public static final RegistryObject<Item> SUN_SHARD = registerItem(ITEMS.register("sun_shard",()->new SunShardItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC)));



    public static <T extends Item> RegistryObject<T> registerItem(RegistryObject<T> reg,CreativeModeTab tab){
        var a = itemTabs.get(tab);
        if (a != null){
            a.add(reg);
        }else{
            itemTabs.put(tab, Lists.newArrayList(reg));
        }
        return reg;
    }

    public static void registerIntoCreativeTabs(BuildCreativeModeTabContentsEvent event){
        itemTabs.get(event.getTab()).forEach(event::accept);
    }

}
