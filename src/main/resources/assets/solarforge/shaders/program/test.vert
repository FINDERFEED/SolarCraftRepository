#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D sampler;
uniform mat4 modelview;
uniform mat4 projection;

varying vec2 texCoords;

void main(){
    vec4 vert = gl_Vertex;
    gl_Position = gl_ModelViewProjectionMatrix * vert;
    texCoords = vec2(gl_MultiTexCoord0);

}