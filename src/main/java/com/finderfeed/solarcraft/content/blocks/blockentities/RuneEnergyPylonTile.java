package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.IREWandDrainable;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.RunicEnergyGiver;
import com.finderfeed.solarcraft.content.blocks.primitive.InscriptionStone;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.misc_things.*;

import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.UpdateTypeOnClientPacket;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;


import java.util.List;


public class RuneEnergyPylonTile extends BlockEntity implements  DebugTarget, RunicEnergyGiver,IREWandDrainable{

    private RunicEnergy.Type type = RunicEnergy.Type.ARDO;
    private float currentEnergy = 0;
    private float energyPerTick = 0f;
    private float maxEnergy = 100000;
    private int updateTick = 40;

    public RuneEnergyPylonTile(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.RUNE_ENERGY_PYLON.get(), p_155229_, p_155230_);
    }


    public void setType(RunicEnergy.Type type) {
        this.type = type;
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, RuneEnergyPylonTile tile) {
        if (!tile.level.isClientSide){
            assignEnergyAndGainIt(tile);
//            doUpdate(tile);
            doProgression(tile);
        }
        imbueItemsNear(tile);


    }

    public void addEnergy(RunicEnergy.Type type,double amount){
        this.currentEnergy += amount;
    }


    public static void imbueItemsNear(RuneEnergyPylonTile tile){
        if (tile.getEnergyType() != null) {
            AABB bb = new AABB(tile.worldPosition.offset(-8, -10, -8), tile.worldPosition.offset(8, 0, 8));
            tile.level.getEntitiesOfClass(ItemEntity.class, bb, (entity) -> entity.getItem().getItem() instanceof IImbuableItem).forEach(entity -> {
                if (!entity.level.isClientSide) {
                    IImbuableItem item = (IImbuableItem) entity.getItem().getItem();
                    if (item.imbue(entity,tile)){
                        double neededEnergy = item.getCost();
                        int maxItems = (int) Math.floor(tile.getCurrentEnergy() / neededEnergy);
                        ItemStack stack = entity.getItem();
                        if (maxItems > stack.getCount()) {
                            tile.currentEnergy -= stack.getCount() * neededEnergy;
                        } else {
                            tile.currentEnergy -= maxItems * neededEnergy;
                        }
                    }
                } else {
                    if (entity.level.getGameTime() % 5 == 1) {

                        double rndX = entity.level.random.nextDouble() * 0.6 - 0.3;
                        double rndY = entity.level.random.nextDouble() * 0.6 - 0.3;
                        double rndZ = entity.level.random.nextDouble() * 0.6 - 0.3;

                        entity.level.addParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                entity.position().x + rndX, entity.position().y + rndY, entity.position().z + rndZ, 0, 0.1, 0
                        );
                    }
                }
            });
        }
    }

    private static int updateEntityTime(ItemEntity entity){
        int time = entity.getPersistentData().getInt(SolarCraftTags.IMBUE_TIME_TAG);
        entity.getPersistentData().putInt(SolarCraftTags.IMBUE_TIME_TAG,time+1);
        return time;
    }

    public static void assignEnergyAndGainIt(RuneEnergyPylonTile tile){
        if (tile.type == null) {
            RunicEnergy.Type[] types = {RunicEnergy.Type.ARDO, RunicEnergy.Type.TERA, RunicEnergy.Type.FIRA, RunicEnergy.Type.URBA, RunicEnergy.Type.KELDA, RunicEnergy.Type.ZETA, RunicEnergy.Type.GIRO, RunicEnergy.Type.ULTIMA
            };
            tile.type = types[tile.level.random.nextInt(types.length)];
            Helpers.updateTile(tile);
        }
        if (isStructCorrect(tile)) {
            float bonus = getPerTickEnergyBonus(tile);
            if (bonus+tile.currentEnergy  + SolarcraftConfig.RUNIC_ENERGY_PER_TICK_PYLON.get().floatValue() <= tile.maxEnergy) {
                tile.currentEnergy += tile.energyPerTick + SolarcraftConfig.RUNIC_ENERGY_PER_TICK_PYLON.get().floatValue() +bonus;
            } else {
                tile.currentEnergy = tile.maxEnergy;
            }
        }
    }

    public static float getPerTickEnergyBonus(RuneEnergyPylonTile tile){
        BlockState state = tile.level.getBlockState(tile.worldPosition.below(6).north(2));
        BlockState state1 = tile.level.getBlockState(tile.worldPosition.below(6).north(-2));
        BlockState state2 = tile.level.getBlockState(tile.worldPosition.below(6).west(-2));
        BlockState state3 = tile.level.getBlockState(tile.worldPosition.below(6).west(2));
        float r = 0;
        float d = SolarcraftConfig.RUNIC_ENERGY_PER_TICK_UPGRADE.get().floatValue();
        if (validState(state,tile)){
            r+=d;
        }
        if (validState(state1,tile)){
            r+=d;
        }
        if (validState(state2,tile)){
            r+=d;
        }
        if (validState(state3,tile)){
            r+=d;
        }
        return r;
    }


    private static boolean validState(BlockState state,RuneEnergyPylonTile tile){
        if (state.hasProperty(InscriptionStone.PROP)){
            return state.getValue(InscriptionStone.PROP) == tile.getEnergyType();
        }else {
            return false;
        }
    }



    public static boolean isStructCorrect(RuneEnergyPylonTile tile){
        BlockPos[] positions = {
          tile.worldPosition.below(4).west(2),
                tile.worldPosition.below(4).east(2),
                tile.worldPosition.below(4).north(2),
                tile.worldPosition.below(4).south(2)
        };
        for (BlockPos p : positions){
            if (RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(tile.level.getBlockState(p).getBlock()) != tile.getEnergyType()){
                return false;
            }
        }
        return Multiblocks.PYLON.check(tile.level,tile.worldPosition,true);
    }

    public static void doUpdate(RuneEnergyPylonTile tile){
        tile.updateTick++;
        if (tile.updateTick >= 40){
            SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(), tile.worldPosition.getY(), tile.worldPosition.getZ(), 40, tile.level.dimension())),
                    new UpdateTypeOnClientPacket(tile.worldPosition, tile.type.id));
            tile.updateTick = 0;
        }
    }

    public static void doProgression(RuneEnergyPylonTile tile){
        if (tile.level.getGameTime() % 20 == 0) {
            AABB box = new AABB(tile.worldPosition.offset(-4, -10, -4), tile.worldPosition.offset(4, 2, 4));
            tile.level.getEntitiesOfClass(Player.class, box).forEach((player) -> {
                Helpers.fireProgressionEvent(player, Progression.RUNE_ENERGY_PYLON);
            });
        }
    }


    @Override
    public void saveAdditional(CompoundTag nbt) {
        if (this.type != null) {
            nbt.putString("energy_type", type.id);
        }
        nbt.putFloat("energy",currentEnergy);
        nbt.putFloat("energy_tick",energyPerTick);
        nbt.putFloat("maxenergy",maxEnergy);
        super.saveAdditional(nbt);
    }

    @Override
    public void load( CompoundTag nbt) {

        this.type = RunicEnergy.Type.byId(nbt.getString("energy_type"));
        currentEnergy = nbt.getFloat("energy");
        energyPerTick = nbt.getFloat("energy_tick");
        maxEnergy = nbt.getFloat("maxenergy");
        super.load( nbt);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return Helpers.createTilePacket(this,tag);
    }


    public void givePlayerEnergy(Player entity, float amount){
        if (amount <= getCurrentEnergy()){
            this.currentEnergy-=amount;
            float flag = RunicEnergy.givePlayerEnergy(entity,amount,type);
            this.currentEnergy+=flag;

        }
    }


    public void setCurrentEnergy(float currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public void setEnergyPerTick(float energyPerTick) {
        this.energyPerTick = energyPerTick;
    }

    public void setMaxEnergy(float maxEnergy) {
        this.maxEnergy = maxEnergy;
    }



    public RunicEnergy.Type getEnergyType() {
        return type;
    }

    public float getEnergyPerTick() {
        return energyPerTick;
    }

    public float getCurrentEnergy() {
        return currentEnergy;
    }

    public float getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public double extractEnergy(RunicEnergy.Type type, double maxAmount) {
        if (getCurrentEnergy() >= maxAmount){
            this.currentEnergy-=maxAmount;
            return maxAmount;
        }else{
            double b = getCurrentEnergy();
            this.currentEnergy = 0;
            return b;
        }
    }

    @Override
    public List<RunicEnergy.Type> getTypes() {
        return this.getEnergyType() != null ? List.of(this.getEnergyType()) : null;
    }




    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public double getRunicEnergy(RunicEnergy.Type type) {
        return this.currentEnergy;
    }

    @Override
    public double getRange() {
        return 20;
    }

    @Override
    public List<String> getDebugStrings() {
        return List.of(getEnergyType().id.toUpperCase()+ " " + getCurrentEnergy());
    }


    @Override
    public float drainEnergy(RunicEnergy.Type type,Player player, float amount) {
        if (!player.level.isClientSide && type == this.type) {
            float delta = Math.min(amount, currentEnergy);
            this.currentEnergy -= delta;
            Helpers.fireProgressionEvent(player, Progression.RUNE_ENERGY_CLAIM);
            if (!RunicEnergy.hasFoundType(player, type)) {
                Helpers.sendEnergyTypeToast((ServerPlayer) player, type);
                RunicEnergy.setFound(player, type);
            }
            if (!Helpers.hasPlayerCompletedProgression(Progression.ALL_ENERGY_TYPES, player)) {
                boolean f = true;
                for (RunicEnergy.Type t : RunicEnergy.Type.getAll()) {
                    f = RunicEnergy.hasFoundType(player, t);
                    if (!f) {
                        break;
                    }
                }
                if (f) {
                    Helpers.fireProgressionEvent(player, Progression.ALL_ENERGY_TYPES);
                }
            }
            return delta;
        }
        return 0;
    }

    @Override
    public float returnEnergy(RunicEnergy.Type type,Player player, float amount) {
        if (!level.isClientSide) {
            float r = this.maxEnergy + amount - maxEnergy;
            this.currentEnergy = Math.min(this.currentEnergy + amount, maxEnergy);
            return r > 0 ? r : 0;
        }else{
            return 0;
        }
    }

    @Override
    public float getMaxEnergyDrain() {
        return 5;
    }

    @Override
    public List<RunicEnergy.Type> allowedDrainableTypes() {
        return List.of(getEnergyType());
    }

    @Override
    public boolean shouldAutomaticallySwitchWandType() {
        return true;
    }

//    @Override
//    public void onLoad() {
//        super.onLoad();
//        if (!level.isClientSide){
//            ChunkPos pos = new ChunkPos(worldPosition);
//            ForgeChunkManager.forceChunk((ServerLevel) level, SolarCraft.MOD_ID,worldPosition,
//                    pos.x,pos.z,true,true);
//        }
//    }
}
