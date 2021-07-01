package com.finderfeed.solarforge.registries.blocks;

import com.finderfeed.solarforge.infusing_table_things.infusing_pool.InfusingPool;
import com.finderfeed.solarforge.magic_items.blocks.BlueGemDoorBlock;
import com.finderfeed.solarforge.magic_items.blocks.*;
import com.finderfeed.solarforge.magic_items.decoration_blocks.SolarFlower;
import com.finderfeed.solarforge.structures.blocks.ColdStarInfuser;
import com.finderfeed.solarforge.structures.blocks.InvincibleStone;
import com.finderfeed.solarforge.structures.blocks.KeyDefender;
import com.finderfeed.solarforge.structures.blocks.KeyLockBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,"solarforge");
    public  static  final RegistryObject<InfusingPool> SOLAR_POOL = BLOCKS.register("solar_forge_infusion_pool",()-> new InfusingPool(AbstractBlock.Properties.of(Material.STONE)
            .sound(SoundType.METAL)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(1)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .strength(3,3)));
    public  static  final RegistryObject<Block> SOLAR_STONE = BLOCKS.register("solar_stone",()-> new Block(AbstractBlock.Properties.copy(Blocks.ANDESITE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_CHISELED = BLOCKS.register("chiseled_solar_stone",()-> new Block(AbstractBlock.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_COLLUMN = BLOCKS.register("solar_stone_collumn",()-> new Block(AbstractBlock.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_BRICKS = BLOCKS.register("solar_stone_bricks",()-> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_STAIRS = BLOCKS.register("solar_stone_stairs",()-> new StairsBlock(()-> SOLAR_STONE_BRICKS.get().defaultBlockState(),AbstractBlock.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> SOLAR_STONE_COLLUMN_HORIZONTAL = BLOCKS.register("solar_stone_collumn_horizontal",()-> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> KEY_LOCK_BLOCK = BLOCKS.register("key_lock_block",()-> new KeyLockBlock());
    public  static  final RegistryObject<Block> INVINCIBLE_STONE = BLOCKS.register("invincible_solar_stone",()-> new InvincibleStone());
    public  static  final RegistryObject<Block> COLD_STAR_INFUSER = BLOCKS.register("cold_star_charger",()-> new ColdStarInfuser(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> KEY_DEFENDER = BLOCKS.register("defence_trap_block",()-> new KeyDefender(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> SOLAR_LENS = BLOCKS.register("solar_lens",()-> new SolarLensBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.GLASS).noOcclusion()));
    public  static  final RegistryObject<Block> MAGNET_BLOCK = BLOCKS.register("magnet_block",()-> new MagnetBlock(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));
    public  static  final RegistryObject<Block> SOLAR_ENERGY_GENERATOR = BLOCKS.register("solar_energy_generator",()-> new SolarEnergyGenerator(AbstractBlock.Properties.copy(Blocks.ANDESITE).noOcclusion()));
    public  static  final RegistryObject<Block> SOLAR_REPEATER = BLOCKS.register("solar_energy_repeater",()-> new SolarRepeaterBlock(AbstractBlock.Properties.copy(Blocks.ANDESITE).noOcclusion()));
    public  static  final RegistryObject<Block> ILLIDIUM_BLOCK = BLOCKS.register("illidium_block",()-> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> ALGADIUM_BLOCK = BLOCKS.register("algadium_block",()-> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> SOLAR_CORE = BLOCKS.register("solar_core_block",()-> new SolarCoreBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public  static  final RegistryObject<Block> AURA_HEALER_BLOCK = BLOCKS.register("aura_healer_block",()-> new AuraHealerBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<Block> SOLAR_MORTAR_BLOCK = BLOCKS.register("solar_mortar_block",()-> new SolarMortar(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<Block> SOLAR_FLOWER = BLOCKS.register("solar_flower",()-> new SolarFlower(AbstractBlock.Properties.copy(Blocks.DEAD_BUSH)));
    public  static  final RegistryObject<Block> DEAD_SPROUT = BLOCKS.register("dead_sprout",()-> new FlowerBlock(Effects.BLINDNESS,2,AbstractBlock.Properties.copy(Blocks.POPPY)));
    public  static  final RegistryObject<Block> SOLAR_FURNACE = BLOCKS.register("solar_furnace",()-> new SolarEnergyFurnace(AbstractBlock.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<Block> TURRET_BLOCK = BLOCKS.register("turret_block",()-> new TurretBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public  static  final RegistryObject<UldoradiumOre> ULDORADIUM_ORE = BLOCKS.register("uldoradium_ore",()-> new UldoradiumOre(AbstractBlock.Properties.copy(Blocks.STONE)));
    public  static  final RegistryObject<RayTrapBlock> RAY_TRAP_BLOCK = BLOCKS.register("ray_trap_block",()-> new RayTrapBlock(AbstractBlock.Properties.copy(Blocks.BEDROCK).noOcclusion()));
    public  static  final RegistryObject<TrapStructureController> TRAP_CONTROLLER = BLOCKS.register("trap_controller",()-> new TrapStructureController(AbstractBlock.Properties.copy(Blocks.BEDROCK)));
    public  static  final RegistryObject<BlueGemDoorBlock> BLUE_GEM_DOOR_BLOCK = BLOCKS.register("blue_gem_door_block",()-> new BlueGemDoorBlock(AbstractBlock.Properties.copy(Blocks.BEDROCK)));
    public  static  final RegistryObject<LeavesBlock> ASH_LEAVES = BLOCKS.register("ash_leaves",()-> new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES).noOcclusion()));
    public  static  final RegistryObject<RotatedPillarBlock> BURNT_LOG = BLOCKS.register("burnt_log",()-> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)));
   // public  static  final RegistryObject<Block> SOLAR_CHEST = BLOCKS.register("solar_chest",()-> new ChestBlock(AbstractBlock.Properties.copy(Blocks.STONE), ()->TileEntityType.CHEST));
}
