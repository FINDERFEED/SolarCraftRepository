#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D sampler;
uniform mat4 modelview;
uniform mat4 projection;
uniform float intensity;


uniform float posX;
uniform float posY;
uniform float posZ;


varying vec2 texCoords;


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
    vec2 locusXY = getScreenCoordinates(posX, posY, posZ);
    float distv = distsq(locusXY, texCoords);
    float distfac_vertex = max(0.0, min(1.0, 3.0-400.0*distv));
    float vf = intensity*distfac_vertex;
    vec2 texUV = mix(texCoords, locusXY, vf/4.0);
    vec4 color = texture2D(sampler, texUV);

    gl_FragColor = vec4(color.x, color.y, color.z, color.a);
}