#version 150

in vec4 Position;
uniform sampler2D DiffuseSampler;
uniform mat4 ProjMat;
uniform vec2 OutSize;

uniform int screenW;
uniform int screenH;



uniform int time;
uniform float intensity;
uniform float timeModifier;

out vec2 texCoords;

void main(){

    gl_Position = ProjMat *  Position;
    texCoords = Position.xy/OutSize;
}