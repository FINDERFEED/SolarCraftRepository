#version 150



uniform sampler2D Sampler0;
uniform float time;
uniform vec3 definedColor;


in vec2 texCoord0;

out vec4 fragColor;

float valueNoise(vec2 uv){
    float twoD = fract((sin(uv.x*4530.0 + uv.y*234.0) * cos(uv.x*10.0 + uv.y*234.0))  *4530.0);

    return twoD;
}


float noise5d(vec2 uv){
    float col = valueNoise(uv);
    col += valueNoise(uv*2.0);
    col += valueNoise(uv*4.0);
    col += valueNoise(uv*8.0);
    col += valueNoise(uv*16.0);
    col /= 4.5;
    return col;
}

float smoothNoise(vec2 uv){
    vec2 localUV = smoothstep(0.0,1.0,fract(uv*10.0));
    vec2 id = floor(uv*10.0);

    float bl = noise5d(id);
    float br = noise5d(id + vec2(1.0,0.0));
    float b = mix(bl, br, localUV.x);

    float tl = noise5d(id + vec2(0.0,1.0));
    float tr = noise5d(id + vec2(1.0,1.0));
    float t = mix(tl, tr, localUV.x);

    float c = mix(b,t,localUV.y);
    return c;
}

float voronoi(vec2 uv,float timeMod){
    float dist = 100.0;


    vec2 lv = fract(uv*5.0)-0.5;
    vec2 id = floor(uv*5.0);

    for (float i = -1.0;i <= 1.0;i++){
        for (float g = -1.0;g <= 1.0;g++){
            vec2 offs = vec2(i,g);

            vec2 cuv = vec2(id + offs);
            float nv = smoothNoise(cuv);
            vec2 point = offs + sin(nv * time * timeMod)*0.5;
            float d = length(lv-point);
            if (d < dist){
                dist = d;
            }
        }
    }
    return dist;
}
void main() {

    vec2 uv = texCoord0.xy;
    uv -= 0.5;
    float voronoiValue = voronoi(uv,5.0);

    vec3 finalCol = definedColor;

    float mixedTime = mix(1.5,2.0,sin(time*5.0));

    float col = 1.0-smoothstep(0.01,0.3,length(uv));
    float voronoicircle= mix(voronoiValue,0.0,length(uv)*2.0);
    col += max(mix(col,voronoicircle,abs(length(uv)*2.0)),col);
    col = col*mixedTime;

    float deltaR = 1-definedColor.r;
    float deltaG = 1-definedColor.g;
    float deltaB = 1-definedColor.b;


    finalCol.r += smoothstep(0.0,deltaR,1.0-min(length(uv)*10/mixedTime,1.0));
    finalCol.g += smoothstep(0.0,deltaG,1.0-min(length(uv)*10/mixedTime,1.0));
    finalCol.b += smoothstep(0.0,deltaB,1.0-min(length(uv)*10/mixedTime,1.0));

    fragColor = vec4(finalCol,col);

}
//    color.g -= smoothstep(0.0,0.1,length(uv)) + 0.1;
//    color.b -= smoothstep(0.0,0.1,length(uv)) + 0.1;