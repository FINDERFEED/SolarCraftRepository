#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D sampler;
uniform mat4 modelview;
uniform mat4 projection;



varying vec4 test;
varying vec2 texCoords;




void main() {



    gl_FragColor = test;
}