package com.finderfeed.solarforge.my_automations;


import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class TranslationTextComponentJSONWriter {

//    @SubscribeEvent
//    public static void writeToTxt(final InputEvent.KeyInputEvent event){

//    }

    public void writeAllFragmentsTranslationComponents(InputEvent.KeyInputEvent event,String path){
                if (event.getKey() == GLFW.GLFW_KEY_O   && event.getAction() == GLFW.GLFW_PRESS) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                for (AncientFragment fragment : AncientFragment.getAllFragments()){
                    writer.write("\""+fragment.getTranslation().getKey()+"\": \"\",");
                    writer.newLine();

                    if (fragment.getType() == AncientFragment.Type.ITEM){
                        writer.write("\""+fragment.getItemDescription().getKey()+"\": \"\",");
                        writer.newLine();
                    }else if (fragment.getType() == AncientFragment.Type.INFORMATION){
                        writer.write("\""+fragment.getLore().getKey()+"\": \"\",");
                        writer.newLine();
                    }
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
