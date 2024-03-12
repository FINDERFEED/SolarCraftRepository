#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D BloomSampler;

uniform float deviation;
uniform float size;


in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

float gaussian(float x){

    float dd = deviation * deviation;
    float a = 1 / sqrt(6.28 * dd);
    float e = exp(-((x*x)/(2 * dd)));

    float result = a * e;
    return result;
}

void main(){

    vec4 col = vec4(0);
    for (float i = -size; i <= size;i++){
        vec2 coord = texCoord + vec2(0,i);
        vec4 f = texture(DiffuseSampler,coord) * gaussian(i * oneTexel.y);
        col += f;
    }

    fragColor = col;
}