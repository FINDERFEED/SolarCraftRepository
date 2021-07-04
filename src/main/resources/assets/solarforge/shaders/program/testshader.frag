#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D sampler;


varying vec2 texCoords;

void main(){

    vec4 color = texture2D(sampler, texCoords);
    gl_FragColor = color;
}