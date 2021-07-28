#version 150

uniform sampler2D DiffuseSampler;

uniform float Intensity;
uniform float Time;
uniform float TimeModifier;

in vec2 texCoord;

out vec4 fragColor;

void main(){
	float distortionIntensity = Intensity*0.05;
    vec2 cord = texCoord;
    cord.y += 0.1*distortionIntensity*sin(cord.x*90+Time/TimeModifier);
    cord.x += 0.1*distortionIntensity*sin(cord.y*90+Time/TimeModifier);
    vec4 color = texture(DiffuseSampler, cord);
    fragColor = vec4(color.x, color.y, color.z, color.a);
}
