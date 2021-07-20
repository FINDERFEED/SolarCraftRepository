#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D sampler;
uniform mat4 modelview;
uniform mat4 projection;

uniform int time;
uniform float intensity;
uniform float timeModifier;

varying vec2 texCoords;


void main() {
    float distortionIntensity = intensity*0.05;
    vec2 cord = texCoords;
    cord.y += 0.1*distortionIntensity*sin(cord.x*90+float(time)/timeModifier);
    cord.x += 0.1*distortionIntensity*sin(cord.y*90+float(time)/timeModifier);
    vec4 color = texture2D(sampler, cord);
    gl_FragColor = vec4(color.x, color.y, color.z, color.a);
}