package com.finderfeed.solarforge;

import com.finderfeed.solarforge.SolarAbilities.screens.AbilityBuyScreen;
import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RayTrapTileEntity;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens.RunicTableContainerScreen;
import com.finderfeed.solarforge.misc_things.*;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;


public class ClientHelpers {
    public static LocalPlayer getClientPlayer(){
        return Minecraft.getInstance().player;
    }


    public static void updateClientRunicEnergyForPlayer(float amount, RunicEnergy.Type type){

        RunicEnergy.setEnergy(getClientPlayer(),amount,type);
    }



    public static void handleSolarWandParticles(Vec3 pos,Vec3 vel){
        SmallSolarStrikeParticle particle = (SmallSolarStrikeParticle) Minecraft.getInstance().particleEngine.createParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),pos.x,pos.y,pos.z,vel.normalize().x,vel.normalize().y,vel.normalize().z);
        particle.setLifetime((int)Math.round(vel.length()/vel.normalize().length())*5/2 );
    }

    public static void updateEnergyTypeOnClient(BlockPos pos,String id){
        BlockEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
        if (tile instanceof RuneEnergyPylonTile){
            RuneEnergyPylonTile pylon = (RuneEnergyPylonTile) tile;
            pylon.setType(RunicEnergy.Type.byId(id));
        }
    }


    public static void updatePatternOnScreen(int[] pattern){
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof RunicTableContainerScreen){
            RunicTableContainerScreen screen1 = (RunicTableContainerScreen) screen;
            screen1.pattern.clear();
            for (int a : pattern){
                screen1.pattern.add(ProgressionHelper.RUNES[a].getDefaultInstance());
            }
        }
    }


    public static void playTotemAnimation( ){
        LocalPlayer ent = Minecraft.getInstance().player;
        Minecraft.getInstance().particleEngine.createTrackingEmitter(ent, ParticleTypes.TOTEM_OF_UNDYING, 30);
        Minecraft.getInstance().gameRenderer.displayItemActivation(ItemsRegister.TOTEM_OF_IMMORTALITY.get().getDefaultInstance());
        Minecraft.getInstance().level.playLocalSound(ent.getX(), ent.getY(), ent.getZ(), SoundEvents.TOTEM_USE, ent.getSoundSource(), 1F, 0.6F, false);
    }

    public static void updateRepeatersOnClient(BlockPos pos,BlockPos pos2){
        BlockEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
        if (tile instanceof AbstractSolarNetworkRepeater){
            ((AbstractSolarNetworkRepeater) tile).connectedTo = pos2;
        }
    }

    public static void updateGeneratorOnClient(BlockPos pos, BlockPos pos2,int index,boolean remove){
        BlockEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
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
        BlockEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
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
        Level world = Minecraft.getInstance().player.level;


        world.playSound(Minecraft.getInstance().player,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,getSoundByID(soundID),
                SoundSource.BLOCKS,volume,pitch);

    }

    public static SoundEvent getSoundByID(int id){
        if (id == 1){
            return Sounds.SOLAR_MORTAR_SHOOT.get();
        }else if(id == 2){
            return Sounds.SOLAR_MORTAR_PROJECTILE.get();
        }
        return null;
    }


    public static void reloadProgression(ServerPlayer playerServer){

        Player player = Minecraft.getInstance().player;
        for (Achievement a : Achievement.getAllAchievements()){
            Helpers.setAchievementStatus(a,player,Helpers.hasPlayerUnlocked(a,playerServer));

        }
    }

    /**
     * Updates RayTrapTileEntity client trigger
     */
    public static void updateIntegerLASERTRAP(BlockPos pos, int i){
        BlockEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
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

    public static Component getNameBasedOnProgression(ItemStack stack){
        IProgressionBlock block = (IProgressionBlock) ((BlockItem)stack.getItem()).getBlock();
        Player playerEntity = getClientPlayer();
        if (playerEntity != null) {
            if (Helpers.hasPlayerUnlocked(block.getRequiredProgression(), playerEntity)) {
                return block.getUnlockedBlock().getName();
            } else {
                return block.getLockedBlock().asItem().getDefaultInstance().getHoverName();
            }
        }
        return block.getLockedBlock().asItem().getDefaultInstance().getHoverName();
    }

    public static TranslatableComponent getTranslationFor(Block block){
        if (block.equals(BlocksRegistry.ULDORADIUM_ORE.get())){
            return new TranslatableComponent("item.solarforge.uldoradium_ore");
        }
        return null;
    }


    public static void updateLexiconInventory(ItemStack[] stacks){
        ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
        if ((stack.getItem() instanceof SolarLexicon)){
            IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
            if (handler != null){
                for (int i = 0; i < stacks.length;i++){
                    handler.insertItem(i,stacks[i],false);
                }
            }

        }
    }
    public static void triggerProgressionUnlockShader(){
        RenderEventsHandler.triggerProgressionShader();
    }

    public static void playSound(SoundEvent event,float a, float b){
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(event,a,b));
    }

    public static void bindText(ResourceLocation loc){
        RenderSystem.setShaderTexture(0,loc);
    }

    public static List<AbstractWidget> getScreenButtons(Screen screen){
        List<Widget> widgets = screen.renderables;
        List<AbstractWidget> returnable = new ArrayList<>();
        widgets.forEach((widget)->{
            if (widget instanceof AbstractWidget){
                returnable.add((AbstractWidget) widget);
            }
        });
        return returnable;
    }

    public static void openAbilityScreen(){
        Minecraft.getInstance().setScreen(new AbilityBuyScreen());
    }

    public static void createEffectParticle(double x, double y, double z,double xs, double ys, double zs, MobEffect effect){
        SmallSolarStrikeParticle particle = (SmallSolarStrikeParticle) Minecraft.getInstance().particleEngine.
                createParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),x,y,z,xs,ys,zs);
        int[] rgba = FinderfeedMathHelper.intToRgba(effect.getColor());
        particle.setColor(rgba[0],rgba[1],rgba[2]);
    }
}
