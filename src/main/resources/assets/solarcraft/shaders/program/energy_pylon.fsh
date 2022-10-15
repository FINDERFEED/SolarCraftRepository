#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 ScreenSize;

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
    float colorFactor = max(0.0,min(1.0,1-0.01*distanceBetween*distance) - min(1.0,10/(distance*distanceBetween)));
    vec4 color = texture(DiffuseSampler,texCoord);


    vec2 finalColor = color.xy + colorFactor*intensity;

    fragColor = vec4(finalColor,color.b,color.a);
}