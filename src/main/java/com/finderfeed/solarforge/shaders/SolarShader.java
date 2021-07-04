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
        FRAGMENT = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

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

        GL20.glDetachShader(SHADER,VERTEX);
        GL20.glDetachShader(SHADER,FRAGMENT);
        GL20.glDeleteShader(VERTEX);
        GL20.glDeleteShader(FRAGMENT);
    }

    public void process(){
        GL20.glUseProgram(SHADER);
    }

    public void disable() {
       GL20.glUseProgram(0);
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
            throw new RuntimeException();
        }
        return builder.toString();
    }

    public void addUniform(String name,int value){
        int loc = GL20.glGetUniformLocation(SHADER,name);
        if (loc != -1){
            GL20.glUniform1i(loc,value);
        }
    }

    public void addUniform(String name,float value){
        int loc = GL20.glGetUniformLocation(SHADER,name);
        if (loc != -1){
            GL20.glUniform1f(loc,value);
        }
    }




    public void setDefaultUniforms(){
        if (GL20.glGetUniformLocation(SHADER,"screenW") != -1){
            GL20.glUniform1i(GL20.glGetUniformLocation(SHADER,"screenW"),Minecraft.getInstance().getWindow().getWidth());
        }
        if (GL20.glGetUniformLocation(SHADER,"screenH") != -1){
            GL20.glUniform1i(GL20.glGetUniformLocation(SHADER,"screenH"),Minecraft.getInstance().getWindow().getHeight());
        }
    }

}
