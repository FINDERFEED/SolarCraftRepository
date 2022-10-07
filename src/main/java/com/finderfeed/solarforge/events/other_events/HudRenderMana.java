package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.SolarWandItem;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;


import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

//,value = Dist.CLIENT
//@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class HudRenderMana {

//    public static ResourceLocation Loc2 = new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png");
//    @SubscribeEvent
//    public static void gameOverlay(final RenderGameOverlayEvent event){
//        Minecraft mc = Minecraft.getInstance();
//
//
//
//
//        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
//            List<ResourceLocation> locationsToRender = new ArrayList<>();
//            for (ToggleableAbility ability : AbilitiesRegistry.getToggleableAbilities()){
//                if (ability.isToggled(mc.player)){
//                    locationsToRender.add(new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/" + ability.id + ".png"));
//                }
//            }
//
//            int initYPos = mc.getWindow().getGuiScaledHeight()/2 - 10 - (locationsToRender.size()-1)*20;
//            int initXPos = mc.getWindow().getGuiScaledWidth() - 20;
//            for (int i = 0; i < locationsToRender.size();i++){
//                ResourceLocation location = locationsToRender.get(i);
//                ClientHelpers.bindText(location);
//                Gui.blit(event.getMatrixStack(),initXPos,initYPos + i*20,0,0,20,20,20,20);
//            }
//
//
//
//
//
//            if (mc.player.getMainHandItem().getItem() instanceof SolarWandItem){
//                int height = event.getWindow().getGuiScaledHeight();
//                int width = event.getWindow().getGuiScaledWidth();
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),2,height/2-43, RunicEnergy.Type.KELDA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),14,height/2-43, RunicEnergy.Type.ARDO);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),26,height/2-43, RunicEnergy.Type.ZETA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),2,height/2+15, RunicEnergy.Type.FIRA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),14,height/2+15, RunicEnergy.Type.TERA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),26,height/2+15, RunicEnergy.Type.URBA);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),38,height/2-43, RunicEnergy.Type.GIRO);
//                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),38,height/2+15, RunicEnergy.Type.ULTIMA);
//            }
//
//        }


//    }


}
