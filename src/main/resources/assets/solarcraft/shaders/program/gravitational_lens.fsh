#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 ScreenSize;

uniform float effectRadius;
uniform float innerControl;
uniform float outerControl;
uniform float distance;
uniform float intensity;
uniform mat4 modelview;
uniform mat4 projection;

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
    vec2 focus = getWindowCoordinates();
    float distanceBetween = dist(texCoord,focus);
    float factor = max(0.0,min(1.0,1-outerControl*distanceBetween*distance) - min(1.0,innerControl/(distance*distanceBetween)));

    vec2 offset = texCoord - focus;

    vec2 ratio = vec2(factor*intensity,1);

    float rad = length(offset/ratio);

    float deformation = (1/pow(rad*pow(distance,0.5),2)) * effectRadius * 0.1;

    offset = offset * (1-deformation);

    offset = focus + offset;

    vec4 final =  texture(DiffuseSampler,offset);


    fragColor = final;

}