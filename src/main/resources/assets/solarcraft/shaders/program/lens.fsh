#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 ScreenSize;

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
    vec2 pylonPosition = getWindowCoordinates();
    float distanceBetween = dist(texCoord,pylonPosition);
    float lensingFactor = max(0.0,min(1.0,1-outerControl*distanceBetween*distance) - min(1.0,innerControl/(distance*distanceBetween)));

    vec2 differenceVector = pylonPosition - texCoord;


    float finalFactor = lensingFactor*intensity;
    vec2 finalCords = texCoord.xy + vec2(differenceVector.x* finalFactor,differenceVector.y*finalFactor);
    vec4 tex = texture(DiffuseSampler,finalCords);


    fragColor = tex;
}