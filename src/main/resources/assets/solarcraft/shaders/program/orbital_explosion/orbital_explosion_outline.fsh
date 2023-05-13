#version 150

uniform vec2 ScreenSize;
uniform Sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;


void main(){

    fragColor = vec4(1.,1.,1.,1.);
}