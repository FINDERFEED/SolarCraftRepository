package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.events.other_events.event_handler.EventHandler;
import com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalTile;
import com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualMainTile;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
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

    public static final int MAX_TIME = 1000;

    public static final int RITUAL_OFFLINE = -1;
    public static final int RITUAL_ONLINE = 1;
    public static final int RITUAL_ENDED = 0;


    public int ticker = 0;
    public int ritualStatus = -1;


    public ClearingRitualMainTile tile;

    public ClearingRitual(ClearingRitualMainTile tile){
        this.tile = tile;
    }




    public void tick(){
        Level world = tile.getLevel();
        BlockPos tilePos = tile.getBlockPos();
        if (this.ritualOnline() && !world.isClientSide){
            ticker++;

            if (ticker > 0){
                if (ticker >= MAX_TIME){
                    if (this.checkStructure()){
                        this.cleanWorld();
                    }else{
                        this.setRitualStatus(RITUAL_OFFLINE);
                        this.ticker = 0;
                    }
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
                }
                if (ticker % 150 == 0){
                    this.randomLightning(world,tilePos);
                }
            }
        }
    }

    private void cleanWorld(){
        Level level = tile.getLevel();
        if (!level.isClientSide && level.dimension() == EventHandler.RADIANT_LAND_KEY){

            RadiantLandCleanedData data = ((ServerLevel)level).getServer().overworld()
                    .getDataStorage()
                    .computeIfAbsent(RadiantLandCleanedData::load,()->new RadiantLandCleanedData(false),"is_radiant_land_cleaned");
            if (!data.isCleaned()){
                List<ServerPlayer> players = ((ServerLevel) level).getPlayers((p)->p.getLevel().dimension() == EventHandler.RADIANT_LAND_KEY);
                MinecraftServer server = level.getServer();
                if (server != null) {
                    for (ServerPlayer player : players) {
                        player.changeDimension(server.overworld().getLevel().getLevel().getLevel());
                    }
                }
                data.setCleaned(true);
                data.setDirty();
            }
        }
    }

    public void randomLightning(Level world,BlockPos pos){
        Vec3 vec = new Vec3(8,0,0).yRot((float)Math.toRadians(world.random.nextInt(360)));
        Vec3 newPos = Helpers.getBlockCenter(pos).add(vec);
        int y = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG,(int)Math.floor(newPos.x),(int)Math.floor(newPos.z));
        LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT,world);
        bolt.setPos(new Vec3(newPos.x,y,newPos.z));
        world.addFreshEntity(bolt);
    }

    public void tryStartRitual(){
        if (checkStructure() && ritualStatus != RITUAL_OFFLINE){
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

    public boolean checkStructure(){
        ArrayList<RunicEnergy.Type> used = new ArrayList<>();
        for (ClearingRitualCrystalTile tile : this.getAllCrystals()){
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
        int yShift = 0;
        ArrayList<ClearingRitualCrystalTile> list = new ArrayList<>();
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).north(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).south(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).east(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).west(16)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).north(9).west(9)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).north(9).east(9)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).south(9).west(9)));
        list.add((ClearingRitualCrystalTile) world.getBlockEntity(pos.above(yShift).south(9).east(9)));
        list.removeIf(Objects::isNull);
        return list;
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
