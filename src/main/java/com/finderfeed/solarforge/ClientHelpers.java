package com.finderfeed.solarforge;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RayTrapTileEntity;
import com.finderfeed.solarforge.misc_things.AbstractEnergyGeneratorTileEntity;
import com.finderfeed.solarforge.misc_things.AbstractSolarCore;
import com.finderfeed.solarforge.misc_things.AbstractSolarNetworkRepeater;
import com.finderfeed.solarforge.misc_things.IProgressionBlock;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ClientHelpers {
    public static ClientPlayerEntity getClientPlayer(){
        return Minecraft.getInstance().player;
    }

    public static void playTotemAnimation( ){
        ClientPlayerEntity ent = Minecraft.getInstance().player;
        Minecraft.getInstance().particleEngine.createTrackingEmitter(ent, ParticleTypes.TOTEM_OF_UNDYING, 30);
        Minecraft.getInstance().gameRenderer.displayItemActivation(ItemsRegister.TOTEM_OF_IMMORTALITY.get().getDefaultInstance());
        Minecraft.getInstance().level.playLocalSound(ent.getX(), ent.getY(), ent.getZ(), SoundEvents.TOTEM_USE, ent.getSoundSource(), 1F, 0.6F, false);
    }

    public static void updateRepeatersOnClient(BlockPos pos,BlockPos pos2){
        TileEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
        if (tile instanceof AbstractSolarNetworkRepeater){
            ((AbstractSolarNetworkRepeater) tile).connectedTo = pos2;
        }
    }

    public static void updateGeneratorOnClient(BlockPos pos, BlockPos pos2,int index,boolean remove){
        TileEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
        if (tile instanceof AbstractEnergyGeneratorTileEntity){
            if (!remove) {

                if (((AbstractEnergyGeneratorTileEntity) tile).poslist.size()-1 >= index) {
                    ((AbstractEnergyGeneratorTileEntity) tile).poslist.set(index, pos2);
                }else{
                    ((AbstractEnergyGeneratorTileEntity) tile).poslist.add(pos2);
                }
            }else{

                ((AbstractEnergyGeneratorTileEntity) tile).poslist.remove(pos2);
            }
        }
    }
    public static void updateCoreOnClient(BlockPos pos, BlockPos pos2,int index,boolean remove){
        TileEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
        if (tile instanceof AbstractSolarCore){
            if (!remove) {
                if (((AbstractSolarCore) tile).poslist.size()-1 >= index) {
                    ((AbstractSolarCore) tile).poslist.set(index, pos2);
                }else{
                    ((AbstractSolarCore) tile).poslist.add(pos2);
                }
            }else{
                ((AbstractSolarCore) tile).poslist.remove(pos2);
            }
        }
    }
    public static void playSoundAtPos(BlockPos pos,int soundID,float pitch, float volume){
        World world = Minecraft.getInstance().player.level;


        world.playSound(Minecraft.getInstance().player,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,getSoundByID(soundID),
                SoundCategory.BLOCKS,volume,pitch);

    }

    public static SoundEvent getSoundByID(int id){
        if (id == 1){
            return Sounds.SOLAR_MORTAR_SHOOT.get();
        }else if(id == 2){
            return Sounds.SOLAR_MORTAR_PROJECTILE.get();
        }
        return null;
    }


    public static void reloadProgression(ServerPlayerEntity playerServer){

        PlayerEntity player = Minecraft.getInstance().player;
        for (Achievement a : Achievement.ALL_ACHIEVEMENTS){
            Helpers.setAchievementStatus(a,player,Helpers.hasPlayerUnlocked(a,playerServer));

        }
    }

    /**
     * Updates RayTrapTileEntity client trigger
     */
    public static void updateIntegerLASERTRAP(BlockPos pos, int i){
        TileEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
        if (tile instanceof RayTrapTileEntity){
            ((RayTrapTileEntity) tile).CLIENT_TRIGGER_INTEGER = 1;
        }
    }

    /**
     * F3+A from code
     */
    public static void reloadChunks(){
        Minecraft.getInstance().levelRenderer.allChanged();
    }

    public static ITextComponent getNameBasedOnProgression(ItemStack stack){
        IProgressionBlock block = (IProgressionBlock) ((BlockItem)stack.getItem()).getBlock();
        PlayerEntity playerEntity = getClientPlayer();
        if (playerEntity != null) {
            if (Helpers.hasPlayerUnlocked(block.getRequiredProgression(), playerEntity)) {
                return block.getUnlockedBlock().getName();
            } else {
                return block.getLockedBlock().asItem().getDefaultInstance().getHoverName();
            }
        }
        return block.getLockedBlock().asItem().getDefaultInstance().getHoverName();
    }

    public static TranslationTextComponent getTranslationFor(Block block){
        if (block.equals(BlocksRegistry.ULDORADIUM_ORE.get())){
            return new TranslationTextComponent("item.solarforge.uldoradium_ore");
        }
        return null;
    }
}
