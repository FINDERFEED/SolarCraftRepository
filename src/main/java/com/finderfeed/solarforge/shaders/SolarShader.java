package com.finderfeed.solarforge.shaders;

import com.finderfeed.solarforge.SolarForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SolarShader {

    private int SHADER;
    private int VERTEX;
    private int FRAGMENT;

    public SolarShader(String name){
        SHADER = GL20.glCreateProgram();
        VERTEX = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        VERTEX = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        GL20.glShaderSource(VERTEX,readFile(name+".vert"));
        GL20.glCompileShader(VERTEX);
        if (GL20.glGetShaderi(VERTEX,GL20.GL_COMPILE_STATUS) != 1){
            SolarForge.LOGGER.log(Level.ERROR,GL20.glGetShaderInfoLog(VERTEX));
        }


        GL20.glShaderSource(FRAGMENT,readFile(name+".frag"));
        GL20.glCompileShader(FRAGMENT);
        if (GL20.glGetShaderi(FRAGMENT,GL20.GL_COMPILE_STATUS) != 1){
            SolarForge.LOGGER.log(Level.ERROR,GL20.glGetShaderInfoLog(FRAGMENT));
        }

        GL20.glAttachShader(SHADER,VERTEX);
        GL20.glAttachShader(SHADER,FRAGMENT);

        GL20.glLinkProgram(SHADER);
        if (GL20.glGetProgrami(SHADER,GL20.GL_LINK_STATUS) != 1){
            SolarForge.LOGGER.log(Level.ERROR,GL20.glGetProgramInfoLog(SHADER));
        }
        GL20.glValidateProgram(SHADER);
        if (GL20.glGetProgrami(SHADER,GL20.GL_VALIDATE_STATUS) != 1){
            SolarForge.LOGGER.log(Level.ERROR,GL20.glGetProgramInfoLog(SHADER));
        }
    }

    public void process(){
        GL20.glUseProgram(SHADER);
    }

    public void disable() {

    }

    private String readFile(String filename){
        StringBuilder builder = new StringBuilder();
        BufferedReader buffer;
        try {
            buffer = new BufferedReader(
                    new InputStreamReader(Minecraft
                            .getInstance()
                            .getResourceManager()
                            .getResource(new ResourceLocation("solarforge","shaders/program/"+filename))
                            .getInputStream()
                    ));
            String line;
            while ((line = buffer.readLine()) != null){
                builder.append(line);
                builder.append("\n" );
            }
        }catch (IOException e){
            e.printStackTrace();

        }
        return builder.toString();
    }

}
