package com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.content.RadiantPortalBlock;
import com.finderfeed.solarforge.content.entities.projectiles.SummoningProjectile;
import com.finderfeed.solarforge.events.other_events.event_handler.EventHandler;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalTile;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualMainTile;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClearingRitual {

    public static final int MAX_TIME = 2000;

    public static final int RITUAL_OFFLINE = -1;
    public static final int RITUAL_ONLINE = 1;



    public int ticker = 0;
    public int ritualStatus = -1;


    public ClearingRitualMainTile tile;

    public ClearingRitual(ClearingRitualMainTile tile){
        this.tile = tile;
    }




    public void tick(){
        Level world = tile.getLevel();
        BlockPos tilePos = tile.getBlockPos();
        if (this.ritualOnline()){
            ticker++;
            if (!world.isClientSide) {
                if (ticker > 0) {
                    if (ticker >= MAX_TIME) {
                        if (this.checkStructure()) {
                            this.cleanWorld();
                        }
                        this.setRitualStatus(RITUAL_OFFLINE);
                        this.ticker = 0;
                        return;
                    }
                    if (ticker % 200 == 0) {
                        if (!this.checkStructure()) {
                            this.setRitualStatus(RITUAL_OFFLINE);
                            this.ticker = 0;
                            return;
                        }
                        ArrayList<ClearingRitualCrystalTile> crystals = getAllCrystals();
                        crystals.removeIf(ClearingRitualCrystalTile::isCorrupted);
                        if (crystals.size() != 0) {
                            crystals.get(world.random.nextInt(crystals.size())).setCorrupted(true);
                        }
                        SummoningProjectile projectile = new SummoningProjectile(world, SolarcraftEntityTypes.SHADOW_ZOMBIE.get(),
                                43,0,60);
                        double speedMult = world.random.nextDouble()*0.2 + 0.1;
                        Vec3 rnd = new Vec3(1,0,0).yRot(world.random.nextFloat()*360).multiply(speedMult,speedMult,speedMult);
                        projectile.setFallSpeedDecrement(0.025);
                        projectile.setPos(Helpers.getBlockCenter(tile.getBlockPos()).add(0,0.75,0));
                        projectile.setDeltaMovement(rnd.add(0,0.1,0));
                        world.addFreshEntity(projectile);
                    }
                    if (ticker % 40 == 0) {
                        this.randomLightning(world, tilePos);
                    }
                }
            }
        }
    }

    public void stopRitual(){
        this.ticker = 0;
        this.setRitualStatus(RITUAL_OFFLINE);
    }

    private void cleanWorld(){
        Level level = tile.getLevel();
        if (!level.isClientSide && level.dimension() == EventHandler.RADIANT_LAND_KEY){

            RadiantLandCleanedData data = ((ServerLevel)level).getServer().overworld()
                    .getDataStorage()
                    .computeIfAbsent(RadiantLandCleanedData::load,()->new RadiantLandCleanedData(false),"is_radiant_land_cleaned");
            if (!data.isCleaned()){
                List<ServerPlayer> players = ((ServerLevel) level).getPlayers((p)->p.getLevel().dimension() == EventHandler.RADIANT_LAND_KEY);
                List<ServerPlayer> allPlayers = ((ServerLevel) level).getPlayers((p)->true);
                MinecraftServer server = level.getServer();
                if (server != null) {
                    for (ServerPlayer player : allPlayers){
                        Helpers.updateClientRadiantLandStateForPlayer(player,true);
                    }
                    for (ServerPlayer player : players) {
                        player.changeDimension(server.overworld().getLevel(), RadiantPortalBlock.RadiantTeleporter.INSTANCE);
                    }
                }
                data.setCleaned(true);
                data.setDirty();
            }
        }
    }

    public static void setRLState(ServerLevel level,boolean cleaned,boolean notifyPlayers){
        if (notifyPlayers){
        List<ServerPlayer> allPlayers = level.getPlayers((p)->true);
            for (ServerPlayer player : allPlayers){
                Helpers.updateClientRadiantLandStateForPlayer(player,cleaned);
            }
        }
        RadiantLandCleanedData data = level.getServer().overworld()
                .getDataStorage()
                .computeIfAbsent(RadiantLandCleanedData::load,()->new RadiantLandCleanedData(false),"is_radiant_land_cleaned");
        data.setCleaned(cleaned);
        data.setDirty();
    }

    public static boolean getRLState(ServerLevel level){
        RadiantLandCleanedData data = level.getServer().overworld()
                .getDataStorage()
                .computeIfAbsent(RadiantLandCleanedData::load,()->new RadiantLandCleanedData(false),"is_radiant_land_cleaned");
        return data.isCleaned();
    }

    public void randomLightning(Level world,BlockPos pos){
        Vec3 vec = new Vec3(24,0,0).yRot((float)Math.toRadians(world.random.nextInt(360)));
        Vec3 newPos = Helpers.getBlockCenter(pos).add(vec);
        int y = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG,(int)Math.floor(newPos.x),(int)Math.floor(newPos.z));
        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT,world);
        bolt.setPos(new Vec3(newPos.x,y,newPos.z));
        world.addFreshEntity(bolt);
    }

    public void tryStartRitual(){
        if (!tile.getLevel().isClientSide && checkStructure() && ritualStatus == RITUAL_OFFLINE && !getRLState((ServerLevel) tile.getLevel())){
            this.setRitualStatus(RITUAL_ONLINE);
        }
    }

    public boolean ritualOnline(){
        return ritualStatus == RITUAL_ONLINE;
    }

    public void setRitualStatus(int ritualStatus) {
        this.ritualStatus = ritualStatus;
        Helpers.updateTile(tile);
    }

    public int getCurrentTime() {
        return ticker;
    }

    public boolean checkStructure(){
        ArrayList<RunicEnergy.Type> used = new ArrayList<>();
        for (ClearingRitualCrystalTile tile : this.getAllCrystals()){
            used.add(tile.getREType());
            if (true) continue;
            //TODO:delete this shiesh above
            if (used.contains(tile.getREType())){
                return false;
            }else{
                used.add(tile.getREType());
            }
        }
        return used.size() == 8;
    }

    public ArrayList<ClearingRitualCrystalTile> getAllCrystals(){
        Level world = tile.getLevel();
        BlockPos pos = tile.getBlockPos();
        int yShift = -1;
        ArrayList<ClearingRitualCrystalTile> list = new ArrayList<>();
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).north(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).south(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).east(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).west(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).north(12).west(12)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).north(12).east(12)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).south(12).west(12)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).south(12).east(12)));
        list.removeIf(Objects::isNull);
        return list;
    }

    public static List<Vec3> crystalPositions(BlockPos pos){
        List<Vec3> list = new ArrayList<>();
        int yShift = -1;
        list.add(Helpers.getBlockCenter(pos.above(yShift).north(16)));
        list.add(Helpers.getBlockCenter(pos.above(yShift).south(16)));
        list.add(Helpers.getBlockCenter(pos.above(yShift).east(16)));
        list.add(Helpers.getBlockCenter(pos.above(yShift).west(16)));
        list.add(Helpers.getBlockCenter(pos.above(yShift).north(12).west(12)));
        list.add(Helpers.getBlockCenter(pos.above(yShift).north(12).east(12)));
        list.add(Helpers.getBlockCenter(pos.above(yShift).south(12).west(12)));
        list.add(Helpers.getBlockCenter(pos.above(yShift).south(12).east(12)));
        return list;
    }

    public static List<Vec3> crystalOffsets(){
        return List.of(
                    new Vec3(16.5,-0.5,0),
                new Vec3(12.5,-0.5,12.5),
                new Vec3(0,-0.5,16.5),
                new Vec3(-11.5,-0.5,12.5),
                new Vec3(-15.5,-0.5,0),
                new Vec3(-11.5,-0.5,-11.5),
                new Vec3(0,-0.5,-15.5),
                new Vec3(12.5,-0.5,-11.5)
                );
    }

    public void save(CompoundTag tag){
        tag.putInt("ticker",ticker);
        tag.putInt("ritStatus",ritualStatus);
    }

    public void load(CompoundTag tag){
        this.ritualStatus = tag.getInt("ritStatus");
        this.ticker = tag.getInt("ticker");
    }


}
