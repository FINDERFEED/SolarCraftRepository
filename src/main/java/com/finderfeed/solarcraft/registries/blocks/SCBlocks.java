package com.finderfeed.solarcraft.registries.blocks;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.BeamGeneratorBlock;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blocks.BeamReflectorBlock;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserBlock;
import com.finderfeed.solarcraft.content.blocks.progression_ores.CorruptedShardOre;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlock;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.blocks.FlammableBlock;
import com.finderfeed.solarcraft.local_library.blocks.FlammableLeavesBlock;
import com.finderfeed.solarcraft.local_library.blocks.FlammableSlabBlock;
import com.finderfeed.solarcraft.local_library.blocks.RotatedPillarFlammableBlock;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalBlock;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualMainBlock;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStand;
import com.finderfeed.solarcraft.content.blocks.*;
import com.finderfeed.solarcraft.content.blocks.primitive.*;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.runic_network.repeater.RunicNetworkRepeater;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.registries.effects.SCEffects;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.ColdStarInfuser;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.InvincibleStone;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.KeyDefender;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.KeyLockBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.effect.MobEffects;


import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, SolarCraft.MOD_ID);
    public  static  final DeferredHolder<Block,InfusingStand> INFUSER_STAND = BLOCKS.register("solar_forge_infusion_pool",()-> new InfusingStand(
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()
    ));

    public  static  final  DeferredHolder<Block,SolarForgeBlock> SOLAR_FORGE = BLOCKS.register("solar_forge",()->
            new SolarForgeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().sound(SoundType.ANCIENT_DEBRIS)));

    public  static  final  DeferredHolder<Block,ProgressionBlock> SOLAR_ORE = BLOCKS.register("solar_ore",() -> new ProgressionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.ANCIENT_DEBRIS),()-> Progression.ENTER_NETHER,Blocks.STONE));

    public  static  final  DeferredHolder<Block,InfuserBlock> INFUSER = BLOCKS.register("solar_infuser",
            ()-> new InfuserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));

    public  static  final DeferredHolder<Block,Block> INFUSING_CRAFTING_TABLE_BLOCK = BLOCKS.register("infusing_crafting_table",()-> new InfusingTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public  static  final DeferredHolder<Block,ProgressionBlock> SOLAR_STONE = BLOCKS.register("solar_stone",()-> new ProgressionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE),()->Progression.ENTER_NETHER,Blocks.STONE));
    public  static  final DeferredHolder<Block,Block> SOLAR_STONE_CHISELED = BLOCKS.register("chiseled_solar_stone",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,ProgressionBlock> ENDER_CRACKS = BLOCKS.register("ender_cracks",()-> new ProgressionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE),()-> Progression.KILL_DRAGON,Blocks.END_STONE));
    public  static  final DeferredHolder<Block,ProgressionBlock> LENSING_CRYSTAL_ORE = BLOCKS.register("lensing_crystal_ore",()-> new ProgressionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),()-> Progression.KILL_WITHER,Blocks.STONE));

    public  static  final DeferredHolder<Block,Block> SOLAR_STONE_COLLUMN = BLOCKS.register("solar_stone_collumn",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> CATALYST_BASE = BLOCKS.register("catalyst_base",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> SOLAR_STONE_BRICKS = BLOCKS.register("solar_stone_bricks",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> SOLAR_STONE_STAIRS = BLOCKS.register("solar_stone_stairs",()-> new StairBlock(()-> SOLAR_STONE_BRICKS.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> SOLAR_STONE_COLLUMN_HORIZONTAL = BLOCKS.register("solar_stone_collumn_horizontal",()-> new GlazedTerracottaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> SOLAR_STONE_SLAB = BLOCKS.register("solar_stone_slab",()-> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> ENERGIZED_STONE = BLOCKS.register("energized_stone",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> KEY_LOCK_BLOCK = BLOCKS.register("key_lock_block",()-> new KeyLockBlock());
    public  static  final DeferredHolder<Block,Block> INVINCIBLE_STONE = BLOCKS.register("invincible_solar_stone",()-> new InvincibleStone());
    public  static  final DeferredHolder<Block,Block> COLD_STAR_INFUSER = BLOCKS.register("cold_star_charger",()-> new ColdStarInfuser(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public  static  final DeferredHolder<Block,Block> KEY_DEFENDER = BLOCKS.register("defence_trap_block",()-> new KeyDefender(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public  static  final DeferredHolder<Block,Block> SPEED_ROAD = BLOCKS.register("speed_road",()-> new SpeedRoadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public  static  final DeferredHolder<Block,Block> SOLAR_LENS = BLOCKS.register("solar_lens",()-> new SolarLensBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.GLASS).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> MAGNET_BLOCK = BLOCKS.register("magnet_block",()-> new MagnetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)));
    public  static  final DeferredHolder<Block,Block> SOLAR_ENERGY_GENERATOR = BLOCKS.register("solar_energy_generator",()-> new SolarEnergyGenerator(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> SOLAR_REPEATER = BLOCKS.register("solar_energy_repeater",()-> new SolarRepeaterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> ILLIDIUM_BLOCK = BLOCKS.register("illidium_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public  static  final DeferredHolder<Block,Block> ALGADIUM_BLOCK = BLOCKS.register("algadium_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public  static  final DeferredHolder<Block,Block> SOLAR_CORE = BLOCKS.register("solar_core_block",()-> new SolarCoreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public  static  final DeferredHolder<Block,Block> AURA_HEALER_BLOCK = BLOCKS.register("aura_healer_block",()-> new AuraHealerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> SOLAR_MORTAR_BLOCK = BLOCKS.register("solar_mortar_block",()-> new SolarMortar(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> SOLAR_FLOWER = BLOCKS.register("solar_flower",()-> new SolarFlower(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)));
    public  static  final DeferredHolder<Block,Block> VOID_LILY = BLOCKS.register("void_lily",()-> new VoidLily(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)));
    public  static  final DeferredHolder<Block,Block> DEAD_SPROUT = BLOCKS.register("dead_sprout",()-> new FlowerBlock(MobEffects.BLINDNESS,2,BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));
    public  static  final DeferredHolder<Block,Block> SOLAR_FURNACE = BLOCKS.register("solar_furnace",()-> new SolarEnergyFurnace(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> TURRET_BLOCK = BLOCKS.register("turret_block",()-> new TurretBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> ZAP_TURRET_BLOCK = BLOCKS.register("zap_turret_block",()-> new ZapTurretBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final DeferredHolder<Block,ProgressionBlock> BLUE_GEM_ORE = BLOCKS.register("blue_gem_ore",()-> new ProgressionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),()->Progression.TRANSMUTE_GEM,Blocks.STONE));

    public  static  final DeferredHolder<Block,Block> CORRUPTED_SHARD_ORE = BLOCKS.register("corrupted_shard_ore",()-> new CorruptedShardOre(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),()->Progression.RUNIC_ENERGY_REPEATER,Blocks.STONE));
    public  static  final DeferredHolder<Block,Block> CORRUPTED_SHARD_ORE_DEEPSLATE = BLOCKS.register("deepslate_corrupted_shard_ore",()-> new CorruptedShardOre(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE),()->Progression.RUNIC_ENERGY_REPEATER,Blocks.DEEPSLATE));

    public  static  final DeferredHolder<Block,RayTrapBlock> RAY_TRAP_BLOCK = BLOCKS.register("ray_trap_block",()-> new RayTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).noOcclusion()));
    public  static  final DeferredHolder<Block,TrapStructureController> TRAP_CONTROLLER = BLOCKS.register("trap_controller",()-> new TrapStructureController(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));
    public  static  final DeferredHolder<Block,BlueGemDoorBlock> BLUE_GEM_DOOR_BLOCK = BLOCKS.register("blue_gem_door_block",()-> new BlueGemDoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));
    public  static  final DeferredHolder<Block,LeavesBlock> ASH_LEAVES = BLOCKS.register("ash_leaves",()-> new FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).noOcclusion(),20));
    public  static  final DeferredHolder<Block,RotatedPillarBlock> BURNT_LOG = BLOCKS.register("burnt_log",()-> new RotatedPillarFlammableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG),20));
    public  static  final DeferredHolder<Block,Block> RUNE_ENERGY_PYLON = BLOCKS.register("rune_energy_pylon",()-> new RuneEnergyPylonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).pushReaction(PushReaction.IGNORE).noOcclusion()));

    public  static  final DeferredHolder<Block,Block> ULDERA_PYLON = BLOCKS.register("uldera_pylon",()-> new UlderaPylonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).pushReaction(PushReaction.IGNORE).noOcclusion()));

    public  static  final DeferredHolder<Block,Block> INSCRIPTION_STONE = BLOCKS.register("inscription_stone",()-> new InscriptionStone(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> REPEATER = BLOCKS.register("repeater",()-> new RunicNetworkRepeater(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> EXPLOSION_BLOCKER = BLOCKS.register("explosion_blocker",()-> new ExplosionBlocker(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> ENCHANTER = BLOCKS.register("elemental_enchanter",()-> new EnchanterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> RUNIC_ENERGY_CHARGER = BLOCKS.register("runic_energy_charger",()-> new RunicEnergyChargerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> RUNIC_TABLE = BLOCKS.register("runic_table",()-> new RunicTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> RUNIC_TREE_SAPLING = BLOCKS.register("runic_tree_sapling",()-> new SaplingBlock(RunicTreeGrower.GROWER,BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING)));
    public  static  final DeferredHolder<Block,LeavesBlock> RUNIC_LEAVES = BLOCKS.register("runic_leaves",()-> new RunicLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).noOcclusion()));
    public  static  final DeferredHolder<Block,RotatedPillarBlock> RUNIC_LOG = BLOCKS.register("runic_log",()-> new RotatedPillarFlammableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG),20));
    public  static  final DeferredHolder<Block,Block> RUNIC_PLANKS = BLOCKS.register("runic_planks",()-> new FlammableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),20));
    public static   final DeferredHolder<Block,Block> RUNIC_STAIRS = BLOCKS.register("runic_stairs",()-> new StairBlock(()-> RUNIC_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static   final DeferredHolder<Block,Block> RUNIC_SLAB = BLOCKS.register("runic_slab",()-> new FlammableSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),20));

    public  static  final DeferredHolder<Block,Block> ARDO_RUNE_BLOCK = BLOCKS.register("ardo_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> ZETA_RUNE_BLOCK = BLOCKS.register("zeta_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> KELDA_RUNE_BLOCK = BLOCKS.register("kelda_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> FIRA_RUNE_BLOCK = BLOCKS.register("fira_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> URBA_RUNE_BLOCK = BLOCKS.register("urba_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> TERA_RUNE_BLOCK = BLOCKS.register("tera_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> GIRO_RUNE_BLOCK = BLOCKS.register("giro_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> ULTIMA_RUNE_BLOCK = BLOCKS.register("ultima_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public  static  final DeferredHolder<Block,Block> MULTIREPEATER_BLOCK = BLOCKS.register("multirune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));


    public static final DeferredHolder<Block,Block> MODULE_APPLIER = BLOCKS.register("module_table",()->new ModuleStation(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));



    public static final DeferredHolder<Block,Block> RADIANT_LEAVES = BLOCKS.register("radiant_leaves",()->new FlammableLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).noOcclusion(),20));
    public static final DeferredHolder<Block,Block> RADIANT_GRASS = BLOCKS.register("radiant_grass",()->new RadiantGrass(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final DeferredHolder<Block,Block> RADIANT_GRASS_NOT_BLOCK = BLOCKS.register("radiant_grass_grass",()->new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)){
//        @Override
//        public boolean isValidBonemealTarget(BlockGetter p_57325_, BlockPos p_57326_, BlockState p_57327_, boolean p_57328_) {
//            return false;
//        }


        @Override
        public boolean isValidBonemealTarget(LevelReader p_255692_, BlockPos p_57326_, BlockState p_57327_) {
            return false;
        }

        @Override
        public boolean isBonemealSuccess(Level p_222583_, RandomSource p_222584_, BlockPos p_222585_, BlockState p_222586_) {
            return false;
        }

        @Override
        public void performBonemeal(ServerLevel p_222578_, RandomSource p_222579_, BlockPos p_222580_, BlockState p_222581_) {

        }


    });


    public static final DeferredHolder<Block,Block> DAMAGE_AMPLIFICATION_BLOCK = BLOCKS.register("damage_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),()->MobEffects.DAMAGE_BOOST));
    public static final DeferredHolder<Block,Block> ARMOR_AMPLIFICATION_BLOCK = BLOCKS.register("armor_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),()->MobEffects.DAMAGE_RESISTANCE));
    public static final DeferredHolder<Block,Block> REGENERATION_AMPLIFICATION_BLOCK = BLOCKS.register("regen_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE),()->MobEffects.REGENERATION));
    public static final DeferredHolder<Block,Block> EVASION_AMPLIFICATION_BLOCK = BLOCKS.register("evasion_amp_block",
            ()->new AmplificationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE), SCEffects.EVASION));

    public static final DeferredHolder<Block,RotatedPillarBlock> RADIANT_LOG = BLOCKS.register("radiant_log",()-> new RotatedPillarFlammableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG),20));
    public static final DeferredHolder<Block,Block> RADIANT_PLANKS = BLOCKS.register("radiant_planks",()-> new FlammableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),20));
    public static final DeferredHolder<Block,Block> RADIANT_WOOD_STAIRS = BLOCKS.register("radiant_stairs",()-> new StairBlock(()-> RADIANT_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredHolder<Block,Block> RADIANT_WOOD_SLAB = BLOCKS.register("radiant_slab",()-> new FlammableSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),20));
    public static final DeferredHolder<Block,Block> RADIANT_CRYSTAL = BLOCKS.register("radiant_crystal",()->new GlassLikeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
    public  static  final DeferredHolder<Block,Block> CRYSTAL_FLOWER = BLOCKS.register("crystal_flower",()-> new CrystalFlower(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH).lightLevel((i)->6)));
    public static final DeferredHolder<Block,Block> RADIANT_BERRY_BUSH = BLOCKS.register("radiant_berry_bush",()->new NABushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)));

    public  static  final DeferredHolder<Block,Block> DIMENSION_CORE = BLOCKS.register("dimension_core",()-> new DimensionCoreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));


    public  static  final DeferredHolder<Block,Block> WORMHOLE = BLOCKS.register("wormhole",()-> new WormholeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).noCollission()));
    public  static  final DeferredHolder<Block,Block> BONEMEALER = BLOCKS.register("bonemealer",()-> new BonemealerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> CRYSTALLIZED_RUNIC_ENERGY = BLOCKS.register("crystallized_runic_energy",()-> new CrystalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.GLASS).noOcclusion()){

        @Override
        public void animateTick(BlockState p_220827_, Level world, BlockPos pos, RandomSource random) {
            if (random.nextInt(5) == 0) {
                Vec3 vec = Helpers.randomVector().normalize().multiply(0.5,0.5,0.5);
                world.addParticle(SCParticleTypes.CRYSTAL_SPARK_PARTICLE.get(),pos.getX() + 0.5 + vec.x,pos.getY() + 0.5 + vec.y,pos.getZ() + 0.5 + vec.z,0,0,0);
            }
        }

    });
    public  static  final DeferredHolder<Block,Block> CRYSTAL = BLOCKS.register("crystal",()-> new CrystalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.GLASS).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> CORRUPTED_STONE = BLOCKS.register("corrupted_stone",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> ULDERA_RUNE_BLOCK = BLOCKS.register("uldera_rune_block",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> CRYSTAL_ENERGY_VINES = BLOCKS.register("crystal_energy_vines",()-> new CrystalEnergyVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));
    public  static  final DeferredHolder<Block,Block> CLEARING_RITUAL_CRYSTAL = BLOCKS.register("clearing_ritual_crystal",()-> new ClearingRitualCrystalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).explosionResistance(3600000.0F).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> CLEARING_RITUAL_MAIN_BLOCK = BLOCKS.register("clearing_ritual_main_block",()-> new ClearingRitualMainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).noOcclusion()));
    public  static  final DeferredHolder<Block,TemporaryLightBlock> TEMPORARY_LIGHT = BLOCKS.register("temporary_light",()-> new TemporaryLightBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT).lightLevel((state)-> state.getValue(TemporaryLightBlock.LIGHT_LEVEL))));
    public  static  final DeferredHolder<Block,Block> THROWN_LIGHT = BLOCKS.register("thrown_light",()-> new ThrownLightBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).sound(SoundType.WOOL)));
    public  static  final DeferredHolder<Block,Block> SAVANNA_DUNGEON_KEEPER = BLOCKS.register("savanna_dungeon_keeper",()-> new SavannaDungeonKeeper(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).noOcclusion()));
    public  static  final DeferredHolder<Block,Block> RUNIC_ENERGY_CORE = BLOCKS.register("runic_energy_core",()-> new RunicEnergyCoreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));

    public  static  final DeferredHolder<Block,Block> RADIANT_STONE = BLOCKS.register("radiant_stone",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public  static  final DeferredHolder<Block,ProgressionBlock> MAGISTONE = BLOCKS.register("magistone",()-> new ProgressionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE),()->Progression.ENTER_NETHER,Blocks.DEEPSLATE));


    public  static  final DeferredHolder<Block,Block> MAGISTONE_BRICKS = BLOCKS.register("magistone_bricks",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> MAGISTONE_STAIRS = BLOCKS.register("magistone_stairs",()-> new StairBlock(()-> MAGISTONE_BRICKS.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> MAGISTONE_RAY = BLOCKS.register("magistone_ray",()-> new GlazedTerracottaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> MAGISTONE_SLAB = BLOCKS.register("magistone_slab",()-> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> MAGISTONE_COLUMN = BLOCKS.register("magistone_column",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,Block> CHISELED_MAGISTONE = BLOCKS.register("chiseled_magistone",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public  static  final DeferredHolder<Block,Block> BEAM_INPUT = BLOCKS.register("beam_input",()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));

    public  static  final DeferredHolder<Block,SunShardLockBlock> SUN_SHARD_LOCK = BLOCKS.register("sun_shard_lock",()-> new SunShardLockBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).pushReaction(PushReaction.IGNORE)));
    public  static  final DeferredHolder<Block,BeamGeneratorBlock> BEAM_GENERATOR = BLOCKS.register("beam_generator", BeamGeneratorBlock::new);
    public  static  final DeferredHolder<Block,BeamReflectorBlock> BEAM_REFLECTOR = BLOCKS.register("beam_reflector",()-> new BeamReflectorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));
    public  static  final DeferredHolder<Block,ElementWeaverBlock> ELEMENT_WEAVER = BLOCKS.register("element_weaver",()-> new ElementWeaverBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public  static  final DeferredHolder<Block,SolarOrbitalMissileLauncherBlock> ORBITAL_MISSILE_LAUNCHER = BLOCKS.register("orbital_missile_launcher",()-> new SolarOrbitalMissileLauncherBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

}
