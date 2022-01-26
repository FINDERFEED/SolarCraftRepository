package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.SolarWandItem;
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

    public static ResourceLocation Loc = new ResourceLocation("solarforge","textures/misc/mana_hud.png");
    public static ResourceLocation Loc2 = new ResourceLocation("solarforge","textures/gui/tooltips_solarforge.png");
    @SubscribeEvent
    public static void gameOverlay(final RenderGameOverlayEvent event){
        Minecraft mc = Minecraft.getInstance();




        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
           if (!mc.player.isDeadOrDying() && mc.player.getMainHandItem().getItem() instanceof ManaConsumer) {
               int height = event.getWindow().getGuiScaledHeight();
               int width = event.getWindow().getGuiScaledWidth();
               double mana = CapabilitySolarMana.getSolarMana(mc.player).orElseThrow(RuntimeException::new).getMana();
               double percent = mana / 3000;
               ClientHelpers.bindText(Loc);

               //1 x 2 y 3 x move 4 y move 5 x cut 6 y cut 7 8 texture size x y
               GuiComponent.blit(event.getMatrixStack(),width-32,height/2-30,100,22,0,10,(int)(22*percent),40,40);
               GuiComponent.blit(event.getMatrixStack(),width-38,height/2-37,100,0,0,22,40,40,40);

               GuiComponent.drawCenteredString(event.getMatrixStack(), mc.gui.getFont(), Integer.toString((int)mana), width-25,height/2, 0xff002b);
               GuiComponent.drawString(event.getMatrixStack(), mc.gui.getFont(), "/3000", width-43,height/2+10, 0xff002b);
           }
           //TODO:Toggleable abilities overlay
            if (mc.player.getPersistentData().getBoolean("is_alchemist_toggled")){
                int height = event.getWindow().getGuiScaledHeight();
                int width = event.getWindow().getGuiScaledWidth();
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
