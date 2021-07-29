package com.finderfeed.solarforge.rendering.shaders;

import com.mojang.math.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;


@Deprecated
public class Uniform {

    private int SHADER;
    private String name;

    private int integer;

    private float fl;

    private Matrix4f m4f;

    private FloatBuffer bufm4f;

    private Type type;

    /**
     *
     * @param name name of the uniform
     *
     * @param shader shader program
     */
    private Uniform(String name,Type type,int shader){
        this.type = type;
        this.name = name;
        this.SHADER = shader;
    }

    public Uniform(String name,int a,int shader){
        this(name,Type.INT,shader);
        this.integer = a;
    }

    public Uniform(String name,float a,int shader){
        this(name,Type.FLOAT,shader);
        this.fl = a;
    }

    public Uniform(String name,Matrix4f a,int shader){
        this(name,Type.MATRIX4F,shader);
        this.m4f = a;
    }

    public Uniform(String name,FloatBuffer a,int shader){
        this(name,Type.FLOAT_BUFFER_MAT4F,shader);
        this.bufm4f = a;
    }

    public void applyUniform(){
        switch (type){
            case INT:{
                int loc = GL20.glGetUniformLocation(SHADER,name);
                if (loc != -1){
                    GL20.glUniform1i(loc,integer);
                }

                break;
            }
            case FLOAT:{
                int loc = GL20.glGetUniformLocation(SHADER,name);
                if (loc != -1){
                    GL20.glUniform1f(loc,fl);
                }
                break;
            }
            case MATRIX4F:{
                int loc = GL20.glGetUniformLocation(SHADER,name);
                if (loc != -1){
                    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
                    m4f.store(buffer);
                    buffer.rewind();
                    GL20.glUniformMatrix4fv(loc,false,buffer);
                }
                break;
            }
            case FLOAT_BUFFER_MAT4F:{
                int loc = GL20.glGetUniformLocation(SHADER,name);
                if (loc != -1){
                    GL20.glUniformMatrix4fv(loc,false,bufm4f);
                }
                break;
            }

        }
    }

    enum Type{
        INT,
        FLOAT,
        MATRIX4F,
        FLOAT_BUFFER_MAT4F
    }
}
