#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainSampler;
uniform sampler2D GlowSampler;
uniform int horizontal;

uniform float l;
uniform float brightness;

uniform float weight[10] = float[](
0.24197072451914337,
0.21785217131914983,
0.19418604387179786,
0.17136857611339976,
0.14972744564497215,
0.12951757250634205,
0.11092080929169305,
0.09404905069346302,
0.07895013119551161,
0.0656157880233904
);
//uniform float weight[5] = float[] (0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);

in vec2 texCoord;

out vec4 fragColor;


void main(){

    vec4 finalColor;

    vec2 offs = 1.0/textureSize(GlowSampler,0);

    vec3 result = texture(GlowSampler,texCoord).rgb * weight[0];

    float factor = 0.05;

    if (horizontal == 1){
        finalColor = vec4(0.);
        for (int i = 1; i < 10; ++i){
            result += texture(GlowSampler,texCoord + vec2(offs.x*float(i) * l,0.)).rgb * weight[i]*brightness;
            result += texture(GlowSampler,texCoord - vec2(offs.x*float(i) * l,0.)).rgb * weight[i]*brightness;
        }
    }else{
        finalColor = texture(MainSampler,texCoord);
        for (int i = 1; i < 10; ++i){
            result += texture(DiffuseSampler,texCoord + vec2(0.,offs.y*float(i) * l)).rgb * weight[i]*brightness;
            result += texture(DiffuseSampler,texCoord - vec2(0.,offs.y*float(i) * l)).rgb * weight[i]*brightness;
        }
    }

    if (horizontal == 0){
        result = vec3(1.0) - exp(-result);
    }

    finalColor.rgb += result;
    fragColor = finalColor;
}