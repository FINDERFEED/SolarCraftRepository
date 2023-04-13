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
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class SolarcraftItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"solarcraft");

    public static final RegistryObject<SolarWandItem> SOLAR_WAND = ITEMS.register("solar_wand",()-> new SolarWandItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP).stacksTo(1)));
    public static  final RegistryObject<Item> SOLAR_INFUSION_POOL = ITEMS.register("solar_forge_infusion_pool",()-> new SolarcraftBlockItem(SolarcraftBlocks.INFUSING_POOL.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SOLAR_INFUSER));
    public static  final RegistryObject<SolarcraftItem> SOLAR_DUST = ITEMS.register("solar_dust",()-> new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.DUSTS));
    public static  final RegistryObject<Item> VOID_DUST = ITEMS.register("void_dust",()-> new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.DUSTS));
    public static final RegistryObject<Item> ENERGY_DUST = ITEMS.register("energy_dust",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.DUSTS));
    public static  final RegistryObject<Item> ENDERITE_ESSENCE = ITEMS.register("enderite_essence",()-> new Item(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> SOLAR_STONE = ITEMS.register("solar_stone",()->new ProgressionBlockItem(SolarcraftBlocks.SOLAR_STONE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<ProgressionBlockItem> LENSING_CRYSTAL_ORE = ITEMS.register("lensing_crystal_ore",()->new ProgressionBlockItem(SolarcraftBlocks.LENSING_CRYSTAL_ORE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> ENDER_CRACKS = ITEMS.register("ender_cracks",()->new ProgressionBlockItem(SolarcraftBlocks.ENDER_CRACKS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_CHISELED = ITEMS.register("chiseled_solar_stone",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_CHISELED.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN = ITEMS.register("solar_stone_collumn",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_COLLUMN.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_BRICKS = ITEMS.register("solar_stone_bricks",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_BRICKS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> CATALYST_BASE = ITEMS.register("catalyst_base",()->new SolarcraftBlockItem(SolarcraftBlocks.CATALYST_BASE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.CATALYSTS));
    public static final RegistryObject<Item> ENERGIZED_STONE = ITEMS.register("energized_stone",()->new BlockItem(SolarcraftBlocks.ENERGIZED_STONE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_STAIRS = ITEMS.register("solar_stone_stairs",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_STAIRS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_STONE_SLAB = ITEMS.register("solar_stone_slab",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_SLAB.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> INFUSING_TABLE = ITEMS.register("infusing_crafting_table",()->new BlockItem(SolarcraftBlocks.INFUSING_CRAFTING_TABLE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SPEED_ROAD = ITEMS.register("speed_road",()->new SolarcraftBlockItem(SolarcraftBlocks.SPEED_ROAD.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SPEED_ROAD));


    public static final RegistryObject<Item> SOLAR_STONE_COLLUMN_HORIZONTAL = ITEMS.register("solar_stone_collumn_horizontal",()->new BlockItem(SolarcraftBlocks.SOLAR_STONE_COLLUMN_HORIZONTAL.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    //solar armor
    public static final RegistryObject<ArmorItem> SOLAR_HELMET = ITEMS.register("solar_helmet",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.HEAD,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));
    public static final RegistryObject<ArmorItem> SOLAR_CHESTPLATE = ITEMS.register("solar_chestplate",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));
    public static final RegistryObject<ArmorItem> SOLAR_LEGGINGS = ITEMS.register("solar_leggins",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.LEGS,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));
    public static final RegistryObject<ArmorItem> SOLAR_BOOTS = ITEMS.register("solar_boots",()-> new SolarcraftArmorItem(SolarArmorMaterial.SOLAR_ARMOR, EquipmentSlot.FEET,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.SOLAR_ARMOR));

    public static final RegistryObject<ArmorItem> DIVINE_HELMET = ITEMS.register("divine_helmet",()-> new DivineHelmet(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.HEAD,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));
    public static final RegistryObject<ArmorItem> DIVINE_CHESTPLATE = ITEMS.register("divine_chestplate",()-> new DivineChestplate(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));
    public static final RegistryObject<ArmorItem> DIVINE_LEGGINGS = ITEMS.register("divine_leggings",()-> new DivineLeggings(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.LEGS,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));
    public static final RegistryObject<ArmorItem> DIVINE_BOOTS = ITEMS.register("divine_boots",()-> new DivineBoots(SolarArmorMaterial.DIVINE_ARMOR, EquipmentSlot.FEET,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.DIVINE_ARMOR));

    //radiant armor
    public static final RegistryObject<RadiantChestplate> RADIANT_CHESTPLATE = ITEMS.register("radiant_chestplate",()-> new RadiantChestplate(SolarArmorMaterial.RADIANT_ARMOR, EquipmentSlot.CHEST,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->AncientFragment.RADIANT_CHESTPLATE));
    //lexicon
    public static  final RegistryObject<SolarLexicon> SOLAR_LEXICON = ITEMS.register("solar_lexicon",()-> new SolarLexicon(new Item.Properties().tab(SolarCraft.SOLAR_GROUP).stacksTo(1)));

    public static final RegistryObject<VoidBlockWand> VOID_BLOCK_WAND = ITEMS.register("void_block_wand",()-> new VoidBlockWand(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SOLAR_KEY = ITEMS.register("solar_key",()-> new RareSolarcraftItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP),()->null));
    public static final RegistryObject<Item> COLD_STAR_PIECE = ITEMS.register("cold_star_piece",()-> new ColdStarPieceItem(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_MATERIALS).stacksTo(1)));
    public static final RegistryObject<Item> COLD_STAR_PIECE_ACTIVATED = ITEMS.register("cold_star_piece_activated",()-> new ChargedColdStar(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_MATERIALS)));
    public static final RegistryObject<Item> KEY_LOCK_BLOCK = ITEMS.register("key_lock_block",()->new BlockItem(SolarcraftBlocks.KEY_LOCK_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> INVINCIBLE_STONE = ITEMS.register("invincible_solar_stone",()->new BlockItem(SolarcraftBlocks.INVINCIBLE_STONE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_LENS = ITEMS.register("solar_lens",()->new SolarLensBlockItem(SolarcraftBlocks.SOLAR_LENS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SOLAR_LENS));
    public static final RegistryObject<Item> KEY_DEFENDER = ITEMS.register("defence_trap_block",()->new BlockItem(SolarcraftBlocks.KEY_DEFENDER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> COLD_STAR_INFUSER = ITEMS.register("cold_star_charger",()->new BlockItem(SolarcraftBlocks.COLD_STAR_INFUSER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> ILLIDIUM_INGOT = ITEMS.register("illidium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()-> AncientFragment.ILLIDIUM_INGOT));
    public static final RegistryObject<Item> ALGADIUM_INGOT = ITEMS.register("algadium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.ALGADIUM_INGOT));
    public static final RegistryObject<Item> QUALADIUM_INGOT = ITEMS.register("qualadium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.QUALADIUM_INGOT));
    public static final RegistryObject<Item> ENERGETIC_INGOT = ITEMS.register("energetic_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.ENERGETIC_INGOT));
    public static final RegistryObject<Item> ENDERIUM_INGOT = ITEMS.register("enderium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.ENDERIUM_INGOT));

    public static final RegistryObject<Item> LENSING_CRYSTAL = ITEMS.register("lensing_crystal",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS)));

    public static final RegistryObject<Item> ENDER_RADAR = ITEMS.register("ender_radar",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1),()->AncientFragment.ENDER_RADAR){
        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    });
    public static final RegistryObject<Item> CHARGED_QUALADIUM_INGOT = ITEMS.register("charged_qualadium_ingot",()-> new SolarcraftItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> AMETHYST_CORE = ITEMS.register("amethyst_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.AMETHYST_CORE));
    public static final RegistryObject<Item> RUNIC_CORE = ITEMS.register("runic_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.RUNIC_CORE));

    public static final RegistryObject<Item> CRYSTAL_CORE = ITEMS.register("crystal_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> CRYSTAL_STAR = ITEMS.register("crystal_star",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> ENERGY_CORE = ITEMS.register("energy_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> VOID_CORE = ITEMS.register("void_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));
    public static final RegistryObject<Item> MATERIALIZATION_CORE = ITEMS.register("materialization_core",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.CRYSTAL_CORES));

    public static final RegistryObject<Item> GEMINIUM_INGOT = ITEMS.register("geminium_ingot",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.GEMINIUM_INGOT));
    public static final RegistryObject<Item> TURRET_RADAR = ITEMS.register("turret_radar",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.TURRET_RADAR));
    public static final RegistryObject<Item> SMALL_SOLAR_REACTOR = ITEMS.register("small_solar_reactor",()-> new RareSolarcraftItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.SMALL_SOLAR_REACTOR));
    public static final RegistryObject<Item> MEDIUM_SOLAR_REACTOR = ITEMS.register("medium_solar_reactor",()-> new RareSolarcraftItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.MEDIUM_SOLAR_REACTOR));
    public static final RegistryObject<Item> ILLIDIUM_SWORD = ITEMS.register("illidium_sword",()-> new IllidiumSword());
    public static final RegistryObject<Item> ILLIDIUM_AXE = ITEMS.register("illidium_axe",()->new IllidiumAxe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,4,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.ILLIDIUM_AXE));
    public static final RegistryObject<Item> GROWTH_HOE = ITEMS.register("illidium_hoe",()-> new IllidiumHoe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,-4, 0.0F, (new Item.Properties()).rarity(Rarity.RARE).stacksTo(1).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.QUALADIUM_HOE));
    public static final RegistryObject<Item> VEIN_MINER = ITEMS.register("miner_item",()-> new IllidiumPickaxe(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER, 1, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1),()->AncientFragment.ILLIDIUM_PICKAXE));
    public static final RegistryObject<Item> ILLIDIUM_SHOVEL = ITEMS.register("illidium_shovel",()-> new RareSolarcraftShovelItem(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,1,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.ILLIDIUM_SHOVEL));
    public static final RegistryObject<Item> QUALADIUM_SHOVEL = ITEMS.register("qualadium_shovel",()-> new RareSolarcraftShovelItem(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.QUALADIUM_SHOVEL));
    public static final RegistryObject<Item> QUALADIUM_SWORD = ITEMS.register("qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_WEAPONS),()->AncientFragment.QUALADIUM_SWORD));
    public static final RegistryObject<Item> QUALADIUM_HOE = ITEMS.register("qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.QUALADIUM_HOE));
    public static final RegistryObject<Item> QUALADIUM_PICKAXE = ITEMS.register("qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1),()->AncientFragment.QUALADIUM_PICKAXE));
    public static final RegistryObject<Item> QUALADIUM_AXE = ITEMS.register("qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()-> AncientFragment.QUALADIUM_AXE));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SHOVEL = ITEMS.register("charged_qualadium_shovel",()-> new RareSolarcraftShovelItem(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-3.5f,-2.8f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_SWORD = ITEMS.register("charged_qualadium_sword",()-> new QualadiumSword(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_WEAPONS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_HOE = ITEMS.register("charged_qualadium_hoe",()-> new QualadiumHoe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,-9, 0.0F, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_PICKAXE = ITEMS.register("charged_qualadium_pickaxe",()-> new QualadiumPickaxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER, -3, -2.8F, (new Item.Properties()).rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1).durability(-1),()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> CHARGED_QUALADIUM_AXE = ITEMS.register("charged_qualadium_axe",()->new QualadiumAxe(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,0,-3.2f,new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_TOOLS), ()->AncientFragment.CHARGED_QUALADIUM_INGOT));
    public static final RegistryObject<Item> SOLAR_DISC_LAUNCHER = ITEMS.register("solar_disc_launcher",()-> new SolarDiscGunItem(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP_WEAPONS).stacksTo(1),()->AncientFragment.DISC_LAUNCHER));
    public static final RegistryObject<Item> MAGNET_BLOCK = ITEMS.register("magnet_block",()->new BlockItem(SolarcraftBlocks.MAGNET_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> SOLAR_DISC = ITEMS.register("to_render_item",()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ENERGY_GENERATOR_BLOCK = ITEMS.register("solar_energy_generator",()->new SolarEnergyGeneratorBlockItem());
    public static final RegistryObject<Item> SOLAR_ENERGY_REPEATER = ITEMS.register("solar_energy_repeater",()->new SolarcraftBlockItem(SolarcraftBlocks.SOLAR_REPEATER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SOLAR_ENERGY_REPEATER));
    public static final RegistryObject<Item> EXPLOSION_BLOCKER = ITEMS.register("explosion_blocker",()->new SolarcraftBlockItem(SolarcraftBlocks.EXPLOSION_BLOCKER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.EXPLOSION_BLOCKER));
    public static final RegistryObject<Item> ENCHANTER = ITEMS.register("elemental_enchanter",()->new SolarcraftBlockItem(SolarcraftBlocks.ENCHANTER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.ENCHANTER){
        @Override
        public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level world, List<Component> cmps, TooltipFlag flag) {

            super.appendHoverText(stack, world, cmps, flag);
        }
    });

    public static final RegistryObject<Item> RUNIC_ENERGY_CHARGER = ITEMS.register("runic_energy_charger",()->new SolarcraftBlockItem(SolarcraftBlocks.RUNIC_ENERGY_CHARGER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->null){
        @Override
        public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level world, List<Component> cmps, TooltipFlag flag) {
            
            super.appendHoverText(stack, world, cmps, flag);
        }
    });

    public static final RegistryObject<Item> SOLAR_NETWORK_BINDER = ITEMS.register("solar_network_binder",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> ILLIDIUM_BLOCK = ITEMS.register("illidium_block",()->new SolarcraftBlockItem(SolarcraftBlocks.ILLIDIUM_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.ILLIDIUM_INGOT));
    public static final RegistryObject<Item> ALGADIUM_BLOCK = ITEMS.register("algadium_block",()->new SolarcraftBlockItem(SolarcraftBlocks.ALGADIUM_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.ALGADIUM_INGOT));
    public static final RegistryObject<Item> SOLAR_CORE = ITEMS.register("solar_core_block",()->new SolarcraftBlockItem(SolarcraftBlocks.SOLAR_CORE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SOLAR_CORE));
    public static final RegistryObject<Item> AURA_HEALER = ITEMS.register("aura_healer_block",()->new RareSolarcraftBlockItem(SolarcraftBlocks.AURA_HEALER_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.AURA_HEALER));
    public static  final RegistryObject<TotemOfImmortality> TOTEM_OF_IMMORTALITY = ITEMS.register("totem_of_immortality",()-> new TotemOfImmortality(new Item.Properties().rarity(Rarity.RARE).tab(SolarCraft.SOLAR_GROUP).stacksTo(1),()->AncientFragment.TOTEM_OF_IMMORTALITY));
    public static  final RegistryObject<ShieldOfSolarGod> SOLAR_GOD_SHIELD = ITEMS.register("solar_god_shield",()-> new ShieldOfSolarGod(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_WEAPONS).durability(3000),()->AncientFragment.SOLAR_GOD_SHIELD));
    public static final RegistryObject<BlockBoomerang> BLOCK_BOOMERANG = ITEMS.register("block_boomerang",()-> new BlockBoomerang(new Item.Properties().stacksTo(1).tab(SolarCraft.SOLAR_GROUP).rarity(Rarity.UNCOMMON),()->AncientFragment.BLOCK_BOOMERANG));
//    public static final RegistryObject<ManaAmulet> SOLAR_MANA_AMULET = ITEMS.register("solar_mana_amulet",()-> new ManaAmulet(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON),()->null));
    public static final RegistryObject<UltraCrossbowItem> ULTRA_CROSSBOW = ITEMS.register("solar_crossbow",()-> new UltraCrossbowItem(new Item.Properties().stacksTo(1).tab(SolarCraft.SOLAR_GROUP_WEAPONS).rarity(Rarity.UNCOMMON),()->AncientFragment.SOLAR_CROSSBOW));
    public static final RegistryObject<LightningGun> LIGHTNING_GUN = ITEMS.register("lightning_gun",()-> new LightningGun(new Item.Properties().stacksTo(1).tab(SolarCraft.SOLAR_GROUP_WEAPONS).rarity(Rarity.UNCOMMON),()->AncientFragment.LIGHTNING_GUN));
    public static final RegistryObject<Item> SOLAR_MORTAR = ITEMS.register("solar_mortar_block",()->new RareSolarcraftBlockItem(SolarcraftBlocks.SOLAR_MORTAR_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SOLAR_MORTAR));
//    public static final RegistryObject<Item> ENERGY_METER = ITEMS.register("energy_meter",()->new EnergyMeter(new Item.Properties().tab(SolarCraft.SOLAR_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SOLAR_FLOWER = ITEMS.register("solar_flower",()->new BlockItem(SolarcraftBlocks.SOLAR_FLOWER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> VOID_LILY = ITEMS.register("void_lily",()->new BlockItem(SolarcraftBlocks.VOID_LILY.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> CRYSTAL_FLOWER = ITEMS.register("crystal_flower",()->new BlockItem(SolarcraftBlocks.CRYSTAL_FLOWER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> SOLAR_FURNACE_BLOCK = ITEMS.register("solar_furnace",()->new RareSolarcraftBlockItem(SolarcraftBlocks.SOLAR_FURNACE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SOLAR_FURNACE));
    public static final RegistryObject<Item> TURRET_BLOCK = ITEMS.register("turret_block",()->new TurretBlockItem(SolarcraftBlocks.TURRET_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.SOLAR_TURRET));
    public static final RegistryObject<Item> ZAP_TURRET_BLOCK = ITEMS.register("zap_turret_block",()->new RareSolarcraftBlockItem(SolarcraftBlocks.ZAP_TURRET_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.ZAP_TURRET));
    public static final RegistryObject<Item> SOLAR_GOD_SWORD = ITEMS.register("solar_god_sword",()->new SolarGodSword(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,-3,-2.4f,new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_WEAPONS).stacksTo(1),()->AncientFragment.SOLAR_GOD_SWORD));
    public static final RegistryObject<Item> SOLAR_GOD_PICKAXE = ITEMS.register("solar_god_pickaxe",()-> new SolarGodPickaxe(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER, -6, -2.8F, (new Item.Properties()).rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_TOOLS),()->AncientFragment.SOLAR_GOD_PICKAXE));
    public static final RegistryObject<Item> ULDORADIUM_ORE = ITEMS.register("uldoradium_ore",()->new UldoradiumOreBlockitem(SolarcraftBlocks.ULDORADIUM_ORE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RAY_TRAP_BLOCK = ITEMS.register("ray_trap_block",()->new BlockItem(SolarcraftBlocks.RAY_TRAP_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> TRAP_CONTROLLER = ITEMS.register("trap_controller",()->new BlockItem(SolarcraftBlocks.TRAP_CONTROLLER.get(),new Item.Properties()));
    public static final RegistryObject<Item> BLUE_GEM = ITEMS.register("blue_gem",()->new BlueGemItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_ENCHANCED = ITEMS.register("blue_gem_enriched",()->new EnchancedBlueGem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).fireResistant()));
    public static final RegistryObject<Item> BLUE_GEM_DOOR_BLOCK = ITEMS.register("blue_gem_door_block",()->new BlockItem(SolarcraftBlocks.BLUE_GEM_DOOR_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> ASH_LEAVES = ITEMS.register("ash_leaves",()->new BlockItem(SolarcraftBlocks.ASH_LEAVES.get(),new Item.Properties()));
    public static final RegistryObject<Item> BURNT_LOG = ITEMS.register("burnt_log",()->new BlockItem(SolarcraftBlocks.BURNT_LOG.get(),new Item.Properties()));
    public static final RegistryObject<Item> DEAD_SPROUT = ITEMS.register("dead_sprout",()->new BlockItem(SolarcraftBlocks.DEAD_SPROUT.get(),new Item.Properties()));
    public static final RegistryObject<Item> FIRST_DIMENSIONAL_SHARD = ITEMS.register("first_dimensional_shard",()->new Item(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ASH_ITEM = ITEMS.register("ash",()->new AshItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));

    public static final RegistryObject<Item> INFO_FRAGMENT = ITEMS.register("ancient_fragment",()->new AncientFragmentItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_FRAGMENTS)));

    public static final RegistryObject<Item> RUNIC_TABLE = ITEMS.register("runic_table",()->new SolarcraftBlockItem(SolarcraftBlocks.RUNIC_TABLE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.RUNIC_TABLE));

    public static final RegistryObject<Item> DAMAGE_AMPLIFICATION_BLOCK = ITEMS.register("damage_amp_block",()->new BlockItem(SolarcraftBlocks.DAMAGE_AMPLIFICATION_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ARMOR_AMPLIFICATION_BLOCK = ITEMS.register("armor_amp_block",()->new BlockItem(SolarcraftBlocks.ARMOR_AMPLIFICATION_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> REGEN_AMPLIFICATION_BLOCK = ITEMS.register("regen_amp_block",()->new BlockItem(SolarcraftBlocks.REGENERATION_AMPLIFICATION_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> EVASION_AMPLIFICATION_BLOCK = ITEMS.register("evasion_amp_block",()->new BlockItem(SolarcraftBlocks.EVASION_AMPLIFICATION_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> RUNIC_TREE_SAPLING = ITEMS.register("runic_tree_sapling",()->new BlockItem(SolarcraftBlocks.RUNIC_TREE_SAPLING.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RUNIC_LEAVES = ITEMS.register("runic_leaves",()->new BlockItem(SolarcraftBlocks.RUNIC_LEAVES.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RUNIC_LOG = ITEMS.register("runic_log",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_LOG.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),600));
    public static final RegistryObject<Item> RUNIC_PLANKS = ITEMS.register("runic_planks",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_PLANKS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RUNIC_STAIRS = ITEMS.register("runic_stairs",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_STAIRS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RUNIC_SLAB = ITEMS.register("runic_slab",()->new FuelBlockItem(SolarcraftBlocks.RUNIC_SLAB.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),300));


    public static final RegistryObject<Item> RUNE_ENERGY_PYLON = ITEMS.register("rune_energy_pylon",()->new EnergyPylonBlockItem(SolarcraftBlocks.RUNE_ENERGY_PYLON.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> INSCRIPTION_STONE = ITEMS.register("inscription_stone",()->new BlockItem(SolarcraftBlocks.INSCRIPTION_STONE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("solar_debug_stick",()->new SolarcraftDebugStick(new Item.Properties()));
    //runes
    public static final RegistryObject<Item> SOLAR_RUNE_BASE = ITEMS.register("runestone",()->new RuneBase(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS)));

    public static final RegistryObject<Item> CRYSTALLITE_CORE = ITEMS.register("crystallite_core",()->new RareSolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.DIMENSION_CORE));


    public static final RegistryObject<Item> SOLAR_RUNE_ZETA = ITEMS.register("solar_rune_zeta",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.ZETA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_ARDO = ITEMS.register("solar_rune_ardo",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.ARDO,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_URBA = ITEMS.register("solar_rune_urba",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.URBA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_KELDA = ITEMS.register("solar_rune_kelda",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.KELDA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_FIRA = ITEMS.register("solar_rune_fira",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.FIRA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_TERA = ITEMS.register("solar_rune_tera",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.TERA,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_GIRO = ITEMS.register("solar_rune_giro",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.GIRO,()->null));
    public static final RegistryObject<Item> SOLAR_RUNE_ULTIMA = ITEMS.register("solar_rune_ultima",()->new RuneItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS), RunicEnergy.Type.ULTIMA,()->null));

    public static final RegistryObject<Item> EMPTY_CRYSTAL = ITEMS.register("empty_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> ZETA_CRYSTAL = ITEMS.register("zeta_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> ARDO_CRYSTAL = ITEMS.register("ardo_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> URBA_CRYSTAL = ITEMS.register("urba_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> KELDA_CRYSTAL = ITEMS.register("kelda_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> FIRA_CRYSTAL = ITEMS.register("fira_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> TERA_CRYSTAL = ITEMS.register("tera_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> GIRO_CRYSTAL = ITEMS.register("giro_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));
    public static final RegistryObject<Item> ULTIMA_CRYSTAL = ITEMS.register("ultima_crystal",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS),()->AncientFragment.INFUSED_CRYSTALS));


    public static final RegistryObject<Item> SOLAR_GOD_BOW = ITEMS.register("solar_god_bow",()->new SolarGodBow(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_WEAPONS).stacksTo(1).durability(8000),()->AncientFragment.SOLAR_GOD_BOW));


    public static final RegistryObject<Item> REGEN_AMULET = ITEMS.register("regen_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.REGENERATION,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> STRENGTH_AMULET = ITEMS.register("strength_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.DAMAGE_BOOST,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> NIGHT_VISION_AMULET = ITEMS.register("night_vision_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.NIGHT_VISION,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> SPEED_AMULET = ITEMS.register("speed_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.MOVEMENT_SPEED,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> HASTE_AMULET = ITEMS.register("haste_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.DIG_SPEED,()->AncientFragment.AMULETS));
    public static final RegistryObject<Item> JUMP_AMULET = ITEMS.register("jump_amulet",()->new EffectAmulet(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), MobEffects.JUMP,()->AncientFragment.AMULETS));


    public static final RegistryObject<Item> REPEATER = ITEMS.register("repeater",()->new RunicNetworkRepeaterBlockItem(SolarcraftBlocks.REPEATER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.RUNIC_ENERGY_REPEATER));

    public static final RegistryObject<Item> ARDO_RUNE_BLOCK = ITEMS.register("ardo_rune_block",()->new     BlockItem(SolarcraftBlocks.ARDO_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ZETA_RUNE_BLOCK = ITEMS.register("zeta_rune_block",()->new     BlockItem(SolarcraftBlocks.ZETA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> KELDA_RUNE_BLOCK = ITEMS.register("kelda_rune_block",()->new   BlockItem(SolarcraftBlocks.KELDA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> URBA_RUNE_BLOCK = ITEMS.register("urba_rune_block",()->new     BlockItem(SolarcraftBlocks.URBA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> TERA_RUNE_BLOCK = ITEMS.register("tera_rune_block",()->new     BlockItem(SolarcraftBlocks.TERA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> FIRA_RUNE_BLOCK = ITEMS.register("fira_rune_block",()->new     BlockItem(SolarcraftBlocks.FIRA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> GIRO_RUNE_BLOCK = ITEMS.register("giro_rune_block",()->new     BlockItem(SolarcraftBlocks.GIRO_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ULTIMA_RUNE_BLOCK = ITEMS.register("ultima_rune_block",()->new BlockItem(SolarcraftBlocks.ULTIMA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> MULTIREPEATER_BLOCK = ITEMS.register("multirune_block",()->new BlockItem(SolarcraftBlocks.MULTIREPEATER_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));


    public static final RegistryObject<Item> RADIANT_CRYSTAL = ITEMS.register("radiant_crystal",()->new BlockItem(SolarcraftBlocks.RADIANT_CRYSTAL.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> WORMHOLE = ITEMS.register("wormhole",()->new BlockItem(SolarcraftBlocks.WORMHOLE.get(),new Item.Properties()));
    public static final RegistryObject<Item> REACH_GLOVES = ITEMS.register("gloves_of_reach",()->new SolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1),()->AncientFragment.GLOVES_OF_REACH){
        @Override
        public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> text, TooltipFlag p_41424_) {
            text.add(Component.translatable("gloves_of_reach_active").withStyle(ChatFormatting.GOLD));
            super.appendHoverText(p_41421_, p_41422_, text, p_41424_);
        }
    });
    public static final RegistryObject<Item> DIMENSION_CORE = ITEMS.register("dimension_core",()->new RadiantCoreBlockItem(SolarcraftBlocks.DIMENSION_CORE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.DIMENSION_CORE));
    public static final RegistryObject<Item> RADIANT_LEAVES = ITEMS.register("radiant_leaves",()->new BlockItem(SolarcraftBlocks.RADIANT_LEAVES.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RADIANT_GRASS = ITEMS.register("radiant_grass",()->new BlockItem(SolarcraftBlocks.RADIANT_GRASS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RADIANT_GRASS_NOT_BLOCK = ITEMS.register("radiant_grass_grass",()->new BlockItem(SolarcraftBlocks.RADIANT_GRASS_NOT_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> RADIANT_LOG = ITEMS.register("radiant_log",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_LOG.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),600));
    public static final RegistryObject<Item> RADIANT_PLANKS = ITEMS.register("radiant_planks",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_PLANKS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RADIANT_STAIRS = ITEMS.register("radiant_stairs",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_WOOD_STAIRS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RADIANT_SLAB = ITEMS.register("radiant_slab",()->new FuelBlockItem(SolarcraftBlocks.RADIANT_WOOD_SLAB.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),300));
    public static final RegistryObject<Item> RADIANT_BERRY = ITEMS.register("radiant_berry",()->new RadiantBerry(new Item.Properties().tab(SolarCraft.SOLAR_GROUP).food(new FoodProperties.Builder().alwaysEat().nutrition(5).saturationMod(0.9F).build())));
    public static final RegistryObject<Item> RADIANT_BERRY_BUSH = ITEMS.register("radiant_berry_bush",()->new BlockItem(SolarcraftBlocks.RADIANT_BERRY_BUSH.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));


//    public static final RegistryObject<Item> RADIANT_PORTAL_CREATOR = ITEMS.register("radiant_portal_creator",()->new PortalCreatorBlockItem(SolarcraftBlocks.RADIANT_LAND_PORTAL_CREATOR.get(),new Item.Properties().tab(SolarForge.SOLAR_GROUP_BLOCKS)));


    public static final RegistryObject<Item> EXPERIENCE_CRYSTAL = ITEMS.register("xp_crystal",()->new ExperienceCrystal(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1),()->AncientFragment.EXPERIENCE_CRYSTAL));


    public static final RegistryObject<Item> MODULE_APPLIER = ITEMS.register("module_table",()->new SolarcraftBlockItem(SolarcraftBlocks.MODULE_APPLIER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> PHYSICAL_DEFENCE_MODULE_10 = ITEMS.register("defence_module_physical_10",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.DEFENCE_MODULE_PHYSICAL_10,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> SWORD_AUTOHEAL_MODULE = ITEMS.register("sword_heal_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.SWORD_AUTOHEAL_MODULE,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> SWORD_AOE_ATTACK = ITEMS.register("sword_aoe_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.SWORD_AOE_ATTACK_ABILITY,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> PICKAXE_AUTO_SMELT = ITEMS.register("pickaxe_auto_smelt_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.PICKAXES, ModuleItem.Tags.SMELTING,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> MAGIC_DAMAGE_MODULE_5 = ITEMS.register("magic_damage_5_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5,()->AncientFragment.MODULES,ModuleItem.Tags.POISONING_BLADE, ModuleItem.Tags.FURY_SWIPES));
    public static final RegistryObject<ModuleItem> PICKAXE_MINER_ABILITY_MODULE = ITEMS.register("pickaxe_miner_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.PICKAXES, ModuleItem.Tags.MINER,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> DISARMING_THORNS_MODULE = ITEMS.register("disarming_thorns_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.DISARMING_THORNS,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> BLESSED_MODULE = ITEMS.register("blessed_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.ARMOR, ModuleItem.Tags.BLESSED,()->AncientFragment.MODULES));
    public static final RegistryObject<ModuleItem> POISONING_BLADE_MODULE = ITEMS.register("poisoning_blade_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.POISONING_BLADE,()->AncientFragment.MODULES,ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5, ModuleItem.Tags.FURY_SWIPES));
    public static final RegistryObject<ModuleItem> FURY_SWIPES_MODULE = ITEMS.register("fury_swipes_module",()->new ModuleItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_TOOLS).stacksTo(1), ModuleItem.Type.SWORDS, ModuleItem.Tags.FURY_SWIPES,()->AncientFragment.MODULES,ModuleItem.Tags.MAGIC_DAMAGE_BONUS_5, ModuleItem.Tags.POISONING_BLADE));
    public static final RegistryObject<Item> BONEMEALER = ITEMS.register("bonemealer",()->new SolarcraftBlockItem(SolarcraftBlocks.BONEMEALER.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.BONEMEALER));
    public static final RegistryObject<Item> CRYSTALLIZED_RUNIC_ENERGY = ITEMS.register("crystallized_runic_energy",()->new BlockItem(SolarcraftBlocks.CRYSTALLIZED_RUNIC_ENERGY.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> CRYSTAL = ITEMS.register("crystal",()->new BlockItem(SolarcraftBlocks.CRYSTAL.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> CORRUPTED_STONE = ITEMS.register("corrupted_stone",()->new BlockItem(SolarcraftBlocks.CORRUPTED_STONE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> ULDERA_RUNE_BLOCK = ITEMS.register("uldera_rune_block",()->new BlockItem(SolarcraftBlocks.ULDERA_RUNE_BLOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> CRYSTAL_HEART = ITEMS.register("crystal_heart",()->new RareSolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC),()->null));
    public static final RegistryObject<Item> CRYSTAL_HEART_PIECE = ITEMS.register("crystal_heart_piece",()->new RareSolarcraftItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC),()->null));
    public static final RegistryObject<Item> CRYSTAL_ENERGY_VINES = ITEMS.register("crystal_energy_vines",()->new BlockItem(SolarcraftBlocks.CRYSTAL_ENERGY_VINES.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));
    public static final RegistryObject<Item> CLEARING_CRYSTAL_RITUAL = ITEMS.register("clearing_ritual_crystal",()->new ClearingRitualCrystalItem(SolarcraftBlocks.CLEARING_RITUAL_CRYSTAL.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));

    public static final RegistryObject<Item> CLEARING_RITUAL_MAIN_BLOCK = ITEMS.register("clearing_ritual_main_block",()->new BlockItem(SolarcraftBlocks.CLEARING_RITUAL_MAIN_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> THROWN_LIGHT = ITEMS.register("thrown_light",()->new ThrownLight(SolarcraftBlocks.THROWN_LIGHT.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP)));

    public static  final RegistryObject<Item> SOLAR_SHARD = ITEMS.register("solar_shard",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP)));
    public static  final RegistryObject<Item> SOLAR_FORGE_ITEM = ITEMS.register("solar_forge",()-> new SolarForgeBlockItem(SolarCraft.SOLAR_FORGE.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_BLOCKS).stacksTo(1)));
    public static  final RegistryObject<Item> SOLAR_ORE_ITEM = ITEMS.register("solar_ores",()-> new ProgressionBlockItem(SolarCraft.SOLAR_ORE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static  final RegistryObject<Item> INFUSER_ITEM = ITEMS.register("solar_infuser",()-> new InfuserBlockItem(SolarCraft.SOLAR_INFUSER.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SolarCraft.SOLAR_GROUP_BLOCKS).stacksTo(1),()-> AncientFragment.SOLAR_INFUSER));

    public static final RegistryObject<ProgressionBlockItem> MAGISTONE = ITEMS.register("magistone",()->new ProgressionBlockItem(SolarcraftBlocks.MAGISTONE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> MAGISTONE_STAIRS = ITEMS.register("magistone_stairs",()->new BlockItem(SolarcraftBlocks.MAGISTONE_STAIRS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> MAGISTONE_SLAB = ITEMS.register("magistone_slab",()->new BlockItem(SolarcraftBlocks.MAGISTONE_SLAB.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> MAGISTONE_RAY = ITEMS.register("magistone_ray",()->new BlockItem(SolarcraftBlocks.MAGISTONE_RAY.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> MAGISTONE_COLUMN = ITEMS.register("magistone_column",()->new BlockItem(SolarcraftBlocks.MAGISTONE_COLUMN.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> CHISELED_MAGISTONE = ITEMS.register("chiseled_magistone",()->new BlockItem(SolarcraftBlocks.CHISELED_MAGISTONE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> MAGISTONE_BRICKS = ITEMS.register("magistone_bricks",()->new BlockItem(SolarcraftBlocks.MAGISTONE_BRICKS.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> BEAM_INPUT = ITEMS.register("beam_input",()->new BlockItem(SolarcraftBlocks.BEAM_INPUT.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> SUN_SHARD_LOCK = ITEMS.register("sun_shard_lock",()->new BlockItem(SolarcraftBlocks.SUN_SHARD_LOCK.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> BEAM_REFLECTOR = ITEMS.register("beam_reflector",()->new BlockItem(SolarcraftBlocks.BEAM_REFLECTOR.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));
    public static final RegistryObject<Item> BEAM_GENERATOR = ITEMS.register("beam_generator",()->new BlockItem(SolarcraftBlocks.BEAM_GENERATOR.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS)));

    public static final RegistryObject<Item> RUNIC_ENERGY_CORE = ITEMS.register("runic_energy_core",()->new SolarcraftBlockItem(SolarcraftBlocks.RUNIC_ENERGY_CORE.get(),new Item.Properties().tab(SolarCraft.SOLAR_GROUP_BLOCKS),()->AncientFragment.RUNIC_ENERGY_CORE));

    public static final RegistryObject<Item> SUN_SHARD = ITEMS.register("sun_shard",()->new SunShardItem(new Item.Properties().tab(SolarCraft.SOLAR_GROUP_MATERIALS).rarity(Rarity.EPIC)));

}
