package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.SolarWandItem;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//,value = Dist.CLIENT
@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class HudRenderMana {

    public static ResourceLocation Loc2 = new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png");
    @SubscribeEvent
    public static void gameOverlay(final RenderGameOverlayEvent event){
        Minecraft mc = Minecraft.getInstance();




        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
           //TODO:Toggleable abilities overlay
            if (mc.player.getPersistentData().getBoolean("is_alchemist_toggled")){
                int height = event.getWindow().getGuiScaledHeight();
                ClientHelpers.bindText(Loc2);
                GuiComponent.blit(event.getMatrixStack(),0,height/2-30,100,0,27,27,38,180,185);
            }

            if (mc.player.getMainHandItem().getItem() instanceof SolarWandItem){
                int height = event.getWindow().getGuiScaledHeight();
                int width = event.getWindow().getGuiScaledWidth();
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),2,height/2-43, RunicEnergy.Type.KELDA);
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),14,height/2-43, RunicEnergy.Type.ARDO);
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),26,height/2-43, RunicEnergy.Type.ZETA);
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),2,height/2+15, RunicEnergy.Type.FIRA);
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),14,height/2+15, RunicEnergy.Type.TERA);
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),26,height/2+15, RunicEnergy.Type.URBA);
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),38,height/2-43, RunicEnergy.Type.GIRO);
                RenderingTools.renderRuneEnergyOverlay(event.getMatrixStack(),38,height/2+15, RunicEnergy.Type.ULTIMA);
            }
            
        }


    }


}
