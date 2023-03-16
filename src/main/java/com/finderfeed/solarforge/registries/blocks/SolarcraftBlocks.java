package com.finderfeed.solarforge.registries.blocks;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.blocks.FlammableBlock;
import com.finderfeed.solarforge.local_library.blocks.FlammableLeavesBlock;
import com.finderfeed.solarforge.local_library.blocks.FlammableSlabBlock;
import com.finderfeed.solarforge.local_library.blocks.RotatedPillarFlammableBlock;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalBlock;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualMainBlock;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.infusing_pool.InfusingStand;
import com.finderfeed.solarforge.content.blocks.BlueGemDoorBlock;
import com.finderfeed.solarforge.content.blocks.*;
import com.finderfeed.solarforge.content.blocks.primitive.*;
import com.finderfeed.solarforge.content.blocks.primitive.SolarFlower;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.content.runic_network.repeater.RunicNetworkRepeater;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.registries.effects.SolarcraftEffects;
import com.finderfeed.solarforge.content.world_generation.structures.blocks.ColdStarInfuser;
import com.finderfeed.solarforge.content.world_generation.structures.blocks.InvincibleStone;
import com.finderfeed.solarforge.content.world_generation.structures.blocks.KeyDefender;
import com.finderfeed.solarforge.content.world_generation.structures.blocks.KeyLockBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.effect.MobEffects;


import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

public class SolarcraftBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,"solarforge");
    public  static  final RegistryObject<InfusingStand> INFUSING_POOL = BLOCKS.register("solar_forge_infusion_pool",()-> new InfusingStand(BlockBehaviour.Properties.of(Material.STONE)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .strength(3,3)));

    public  static  final RegistryObject<Block> INFUSING_CRAFTING_TABLE_BLOCK = BLOCKS.register("infusing_crafting_table",()-> new InfusingTableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<SolarStoneBlock> SOLAR_STONE = BLOCKS.register("solar_stone",()-> new SolarStoneBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_CHISELED = BLOCKS.register("chiseled_solar_stone",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<ProgressionBlock> ENDER_CRACKS = BLOCKS.register("ender_cracks",()-> new ProgressionBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE),()-> Progression.KILL_DRAGON,Blocks.END_STONE));
    public  static  final RegistryObject<ProgressionBlock> LENSING_CRYSTAL_ORE = BLOCKS.register("lensing_crystal_ore",()-> new ProgressionBlock(BlockBehaviour.Properties.copy(Blocks.STONE),()-> Progression.KILL_WITHER,Blocks.STONE));

    public  static  final RegistryObject<Block> SOLAR_STONE_COLLUMN = BLOCKS.register("solar_stone_collumn",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> CATALYST_BASE = BLOCKS.register("catalyst_base",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_BRICKS = BLOCKS.register("solar_stone_bricks",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_STAIRS = BLOCKS.register("solar_stone_stairs",()-> new StairBlock(()-> SOLAR_STONE_BRICKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_COLLUMN_HORIZONTAL = BLOCKS.register("solar_stone_collumn_horizontal",()-> new GlazedTerracottaBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_SLAB = BLOCKS.register("solar_stone_slab",()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> ENERGIZED_STONE = BLOCKS.register("energized_stone",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> KEY_LOCK_BLOCK = BLOCKS.register("key_lock_block",()-> new KeyLockBlock());
    public  static  final RegistryObject<Block> INVINCIBLE_STONE = BLOCKS.register("invincible_solar_stone",()-> new InvincibleStone());
    public  static  final RegistryObject<Block> COLD_STAR_INFUSER = BLOCKS.register("cold_star_charger",()-> new ColdStarInfuser(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> KEY_DEFENDER = BLOCKS.register("defence_trap_block",()-> new KeyDefender(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> SPEED_ROAD = BLOCKS.register("speed_road",()-> new SpeedRoadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> SOLAR_LENS = BLOCKS.register("solar_lens",()-> new SolarLensBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.GLASS).noOcclusion()));
    public  static  final RegistryObject<Block> MAGNET_BLOCK = BLOCKS.register("magnet_block",()-> new MagnetBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)));
    public  static  final RegistryObject<Block> SOLAR_ENERGY_GENERATOR = BLOCKS.register("solar_energy_generator",()-> new SolarEnergyGenerator(BlockBehaviour.Properties.copy(Blocks.ANDESITE).noOcclusion()));
    public  static  final RegistryObject<Block> SOLAR_REPEATER = BLOCKS.register("solar_energy_repeater",()-> new SolarRepeaterBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE).noOcclusion()));
    public  static  final RegistryObject<Block> ILLIDIUM_BLOCK = BLOCKS.register("illidium_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> ALGADIUM_BLOCK = BLOCKS.register("algadium_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> SOLAR_CORE = BLOCKS.register("solar_core_block",()-> new SolarCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> AURA_HEALER_BLOCK = BLOCKS.register("aura_healer_block",()-> new AuraHealerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<Block> SOLAR_MORTAR_BLOCK = BLOCKS.register("solar_mortar_block",()-> new SolarMortar(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<Block> SOLAR_FLOWER = BLOCKS.register("solar_flower",()-> new SolarFlower(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)));
    public  static  final RegistryObject<Block> VOID_LILY = BLOCKS.register("void_lily",()-> new VoidLily(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)));
    public  static  final RegistryObject<Block> DEAD_SPROUT = BLOCKS.register("dead_sprout",()-> new FlowerBlock(MobEffects.BLINDNESS,2,BlockBehaviour.Properties.copy(Blocks.POPPY)));
    public  static  final RegistryObject<Block> SOLAR_FURNACE = BLOCKS.register("solar_furnace",()-> new SolarEnergyFurnace(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> TURRET_BLOCK = BLOCKS.register("turret_block",()-> new TurretBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<Block> ZAP_TURRET_BLOCK = BLOCKS.register("zap_turret_block",()-> new ZapTurretBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<UldoradiumOre> ULDORADIUM_ORE = BLOCKS.register("uldoradium_ore",()-> new UldoradiumOre(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<RayTrapBlock> RAY_TRAP_BLOCK = BLOCKS.register("ray_trap_block",()-> new RayTrapBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public  static  final RegistryObject<TrapStructureController> TRAP_CONTROLLER = BLOCKS.register("trap_controller",()-> new TrapStructureController(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public  static  final RegistryObject<BlueGemDoorBlock> BLUE_GEM_DOOR_BLOCK = BLOCKS.register("blue_gem_door_block",()-> new BlueGemDoorBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public  static  final RegistryObject<LeavesBlock> ASH_LEAVES = BLOCKS.register("ash_leaves",()-> new FlammableLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).noOcclusion(),20));
    public  static  final RegistryObject<RotatedPillarBlock> BURNT_LOG = BLOCKS.register("burnt_log",()-> new RotatedPillarFlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG),20));
    public  static  final RegistryObject<Block> RUNE_ENERGY_PYLON = BLOCKS.register("rune_energy_pylon",()-> new RuneEnergyPylonBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

    public  static  final RegistryObject<Block> ULDERA_PYLON = BLOCKS.register("uldera_pylon",()-> new UlderaPylonBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

    public  static  final RegistryObject<Block> INSCRIPTION_STONE = BLOCKS.register("inscription_stone",()-> new InscriptionStone(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> REPEATER = BLOCKS.register("repeater",()-> new RunicNetworkRepeater(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<Block> EXPLOSION_BLOCKER = BLOCKS.register("explosion_blocker",()-> new ExplosionBlocker(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public  static  final RegistryObject<Block> ENCHANTER = BLOCKS.register("elemental_enchanter",()-> new EnchanterBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public  static  final RegistryObject<Block> RUNIC_ENERGY_CHARGER = BLOCKS.register("runic_energy_charger",()-> new RunicEnergyChargerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public  static  final RegistryObject<Block> RUNIC_TABLE = BLOCKS.register("runic_table",()-> new RunicTableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public  static  final RegistryObject<Block> RUNIC_TREE_SAPLING = BLOCKS.register("runic_tree_sapling",()-> new SaplingBlock(new RunicTreeGrower(),BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public  static  final RegistryObject<LeavesBlock> RUNIC_LEAVES = BLOCKS.register("runic_leaves",()-> new RunicLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).noOcclusion()));
    public  static  final RegistryObject<RotatedPillarBlock> RUNIC_LOG = BLOCKS.register("runic_log",()-> new RotatedPillarFlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG),20));
    public  static  final RegistryObject<Block> RUNIC_PLANKS = BLOCKS.register("runic_planks",()-> new FlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),20));
    public static   final RegistryObject<Block> RUNIC_STAIRS = BLOCKS.register("runic_stairs",()-> new StairBlock(()-> RUNIC_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static   final RegistryObject<Block> RUNIC_SLAB = BLOCKS.register("runic_slab",()-> new FlammableSlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),20));

    public  static  final RegistryObject<Block> ARDO_RUNE_BLOCK = BLOCKS.register("ardo_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> ZETA_RUNE_BLOCK = BLOCKS.register("zeta_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> KELDA_RUNE_BLOCK = BLOCKS.register("kelda_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> FIRA_RUNE_BLOCK = BLOCKS.register("fira_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> URBA_RUNE_BLOCK = BLOCKS.register("urba_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> TERA_RUNE_BLOCK = BLOCKS.register("tera_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> GIRO_RUNE_BLOCK = BLOCKS.register("giro_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> ULTIMA_RUNE_BLOCK = BLOCKS.register("ultima_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));


    public static final RegistryObject<Block> MODULE_APPLIER = BLOCKS.register("module_table",()->new ModuleStation(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));



    public static final RegistryObject<Block> RADIANT_LEAVES = BLOCKS.register("radiant_leaves",()->new FlammableLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).noOcclusion(),20));
    public static final RegistryObject<Block> RADIANT_GRASS = BLOCKS.register("radiant_grass",()->new RadiantGrass(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> RADIANT_GRASS_NOT_BLOCK = BLOCKS.register("radiant_grass_grass",()->new TallGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)){
        @Override
        public boolean isValidBonemealTarget(BlockGetter p_57325_, BlockPos p_57326_, BlockState p_57327_, boolean p_57328_) {
            return false;
        }

        @Override
        public boolean isBonemealSuccess(Level level, Random p_57331_, BlockPos p_57332_, BlockState p_57333_) {
            return false;
        }

        @Override
        public void performBonemeal(ServerLevel p_57320_, Random p_57321_, BlockPos p_57322_, BlockState p_57323_) {

        }
    });


    public static final RegistryObject<Block> DAMAGE_AMPLIFICATION_BLOCK = BLOCKS.register("damage_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.copy(Blocks.STONE),()->MobEffects.DAMAGE_BOOST));
    public static final RegistryObject<Block> ARMOR_AMPLIFICATION_BLOCK = BLOCKS.register("armor_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.copy(Blocks.STONE),()->MobEffects.DAMAGE_RESISTANCE));
    public static final RegistryObject<Block> REGENERATION_AMPLIFICATION_BLOCK = BLOCKS.register("regen_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.copy(Blocks.STONE),()->MobEffects.REGENERATION));
    public static final RegistryObject<Block> EVASION_AMPLIFICATION_BLOCK = BLOCKS.register("evasion_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.copy(Blocks.STONE), SolarcraftEffects.EVASION));

    public static final RegistryObject<RotatedPillarBlock> RADIANT_LOG = BLOCKS.register("radiant_log",()-> new RotatedPillarFlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG),20));
    public static final RegistryObject<Block> RADIANT_PLANKS = BLOCKS.register("radiant_planks",()-> new FlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),20));
    public static final RegistryObject<Block> RADIANT_WOOD_STAIRS = BLOCKS.register("radiant_stairs",()-> new StairBlock(()-> RADIANT_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> RADIANT_WOOD_SLAB = BLOCKS.register("radiant_slab",()-> new FlammableSlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),20));
    public static final RegistryObject<Block> RADIANT_CRYSTAL = BLOCKS.register("radiant_crystal",()->new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
    public  static  final RegistryObject<Block> CRYSTAL_FLOWER = BLOCKS.register("crystal_flower",()-> new CrystalFlower(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH).lightLevel((i)->6)));
    public static final RegistryObject<Block> RADIANT_BERRY_BUSH = BLOCKS.register("radiant_berry_bush",()->new BushBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)));

    public  static  final RegistryObject<Block> DIMENSION_CORE = BLOCKS.register("dimension_core",()-> new DimensionCoreBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));


    public  static  final RegistryObject<Block> WORMHOLE = BLOCKS.register("wormhole",()-> new WormholeBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noCollission()));
    public  static  final RegistryObject<Block> BONEMEALER = BLOCKS.register("bonemealer",()-> new BonemealerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK).noOcclusion()));
    public  static  final RegistryObject<Block> CRYSTALLIZED_RUNIC_ENERGY = BLOCKS.register("crystallized_runic_energy",()-> new CrystalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.GLASS).noOcclusion()){
        @Override
        public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
            if (random.nextInt(5) == 0) {
                Vec3 vec = Helpers.randomVector().normalize().multiply(0.5,0.5,0.5);
                world.addParticle(SolarcraftParticleTypes.CRYSTAL_SPARK_PARTICLE.get(),pos.getX() + 0.5 + vec.x,pos.getY() + 0.5 + vec.y,pos.getZ() + 0.5 + vec.z,0,0,0);
            }
        }
    });
    public  static  final RegistryObject<Block> CRYSTAL = BLOCKS.register("crystal",()-> new CrystalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.GLASS).noOcclusion()));
    public  static  final RegistryObject<Block> CORRUPTED_STONE = BLOCKS.register("corrupted_stone",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> ULDERA_RUNE_BLOCK = BLOCKS.register("uldera_rune_block",()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> CRYSTAL_ENERGY_VINES = BLOCKS.register("crystal_energy_vines",()-> new CrystalEnergyVinesBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public  static  final RegistryObject<Block> CLEARING_RITUAL_CRYSTAL = BLOCKS.register("clearing_ritual_crystal",()-> new ClearingRitualCrystalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).explosionResistance(3600000.0F).noOcclusion()));
    public  static  final RegistryObject<Block> CLEARING_RITUAL_MAIN_BLOCK = BLOCKS.register("clearing_ritual_main_block",()-> new ClearingRitualMainBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public  static  final RegistryObject<TemporaryLightBlock> TEMPORARY_LIGHT = BLOCKS.register("temporary_light",()-> new TemporaryLightBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT).lightLevel((state)-> state.getValue(TemporaryLightBlock.LIGHT_LEVEL))));
    public  static  final RegistryObject<Block> THROWN_LIGHT = BLOCKS.register("thrown_light",()-> new ThrownLightBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).sound(SoundType.WOOL)));
    public  static  final RegistryObject<Block> SAVANNA_DUNGEON_KEEPER = BLOCKS.register("savanna_dungeon_keeper",()-> new SavannaDungeonKeeper(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion()));

}
