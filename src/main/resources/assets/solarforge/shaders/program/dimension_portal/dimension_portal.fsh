#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 ScreenSize;

uniform float distance;
uniform float radius;
uniform float time;
uniform mat4 modelview;
uniform mat4 projection;
uniform float deltaR;
uniform float sinValue;
uniform float sinSpread;

in vec2 texCoord;

out vec4 fragColor;


vec2 getWindowCoordinates(){
    vec4 clip = projection*(modelview*vec4(0,0,0,1));
    vec3 ndc = clip.xyz/clip.w;
    vec2 value = (ndc.xy+1.0)/2.0;
    return value;
}

float dist(vec2 dotone,vec2 dottwo){
    float factor = ScreenSize.y/ScreenSize.x;
    float a = dottwo.x - dotone.x;
    float b = (dottwo.y - dotone.y)*factor;
    return sqrt(a*a + b*b);
}



void main() {
    vec2 portalPosition = getWindowCoordinates();
    vec2 cord = texCoord;

    vec2 between = cord - portalPosition;
    float len = dist(portalPosition,cord);
    float modFactor = 0;
    float distanceMod = 5.0/distance;

   float angle = atan(between.y,between.x);
   float sinFactor = sin(angle*sinValue + time) * sinSpread;

    float realRadius = (radius + sinFactor)*distanceMod;

    float delta = deltaR*distanceMod;


    vec2 pointOnRadius = portalPosition + between * (realRadius / len);
    vec2 betweenR = cord - pointOnRadius;
    float lenR = dist(vec2(0.0,0.0),betweenR);

    if (delta - lenR >= 0){
        modFactor = smoothstep(0.0,1.0,(delta - lenR)/delta);
    }
    cord = cord + betweenR*modFactor;

    vec4 color = texture(DiffuseSampler, cord);
    fragColor = vec4(color.x,color.y,color.z,color.a);
}