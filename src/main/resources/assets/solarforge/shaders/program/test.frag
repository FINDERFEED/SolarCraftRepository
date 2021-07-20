#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D sampler;
uniform mat4 modelview;
uniform mat4 projection;
uniform float intensity;


uniform float distance;
uniform float scaleFactor;

varying vec2 texCoords;

//THIS SHADER IS UNUSED BECAUSE IT BELONGS TO REIKA KALSEKI (CHROMATICRAFT MOD)!

vec2 getScreenCoordinates(float a,float b,float c){
    vec4 clip = projection*(modelview*vec4(a,b,c,1));
    vec3 ndc = clip.xyz/clip.w;
    vec2 value = (ndc.xy+1.0)/2.0;
    return value;
}

float distsq(vec2 a, vec2 b) {
    float f = float(screenH)/float(screenW);
    float dx = (a.x-b.x);
    float dy = (a.y-b.y)*f;
    return dx*dx+dy*dy;
}

void main() {
    vec2 coreXY = getScreenCoordinates(0, 0, 0);

    float distv = distsq(coreXY, texCoords);
    float distfac_vertex = max(0.0, min(1.0, 3.5-40.0*distv*distance));
    float f0 = max(intensity*0.5, intensity-scaleFactor*0.15);
    float vf = f0*distfac_vertex;
    vec2 texUV = mix(texCoords, coreXY, vf/7.5);
    vec4 orig = texture2D(sampler, texUV);

    gl_FragColor = vec4(orig.r, orig.g, orig.b, orig.a);
}