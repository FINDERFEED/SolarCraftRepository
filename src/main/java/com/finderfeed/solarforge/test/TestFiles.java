package com.finderfeed.solarforge.test;


import com.finderfeed.solarforge.SolarForge;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.CallbackI;

import java.io.*;
import java.nio.charset.Charset;

//@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class TestFiles {
//
//
//    @SubscribeEvent
//    public static void shaderFileRead(final InputEvent.KeyInputEvent event){
//        if (event.getKey() == GLFW.GLFW_KEY_L) {
//            InputStream str = null;
//            try {
//                str = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("solarforge", "shaders/program/test.fsh")).getInputStream();
//                ;
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            System.out.println(str);
//            if (str != null) {
//                InputStreamReader read = new InputStreamReader(str, Charset.defaultCharset());
//                BufferedReader br;
//                br = new BufferedReader(read);
//                String line;
//                try {
//                    while ((line = br.readLine()) != null){
//                        System.out.println(line);
//                    }
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
////            StringBuilder builder = new StringBuilder();
////            BufferedReader br;
////            try {
////                br = new BufferedReader(new FileReader(SolarForge.class.getResourceAsStream()));
////                String line;
////                while ((line = br.readLine()) != null) {
////                    builder.append(line + "/n");
////                }
////            } catch (IOException e) {
////                e.printStackTrace();
////
////            }
////            System.out.println(builder.toString());
//        }
//    }
}
