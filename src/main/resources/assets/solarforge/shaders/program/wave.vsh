#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D DiffuseSampler;

uniform int time;
uniform float intensity;
uniform float timeModifier;

varying vec2 texCoords;

void main(){
    vec4 vert = gl_Vertex;
    gl_Position = gl_ModelViewProjectionMatrix * vert;
    texCoords = vec2(gl_MultiTexCoord0);

}