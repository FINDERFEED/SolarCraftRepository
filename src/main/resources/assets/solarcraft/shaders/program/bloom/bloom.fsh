#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D BloomSampler;

uniform float deviation;
uniform float size;
uniform float scale;
uniform float colMod;


in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;


float gaussian(float sqx){

    float dd = deviation * deviation;
    float a = 1 / sqrt(6.28 * dd);
    float e = exp(-((sqx)/(2 * dd)));

    float result = a * e;
    return result;
}

void main(){


    vec3 col = vec3(0);
    for (float x = -size; x <= size;x++){
        for (float y = -size; y <= size;y++){
            float cx = x * oneTexel.x * colMod;
            float cy = y * oneTexel.y * colMod;
            vec2 coord = texCoord + vec2(cx, cy);
            float rx = x * scale * deviation;
            float ry = y * scale * deviation;
            float sql = rx * rx + ry * ry;
            vec4 f = texture(DiffuseSampler, coord) * gaussian(sql);
            col += f.rgb;
        }
    }

    fragColor = vec4(col,1.) * colMod;
}