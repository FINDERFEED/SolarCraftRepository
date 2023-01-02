#version 150

#define PI 3.14159265359

uniform sampler2D Sampler0;

uniform float distFromCenter; //a value between 0.0 and 0.5
uniform float innerRadius; //a value between distFromCenter and outRadius
uniform float outRadius; //a value between innerRadius and (0.5 - distFromCenter)
uniform int sectionCount;
uniform vec4 color;
uniform vec4 sColor;
uniform int selectedSection;


in vec2 texCoord0;

out vec4 fragColor;


float computeAngle(vec2 d,vec2 center){
    float cx = d.x - center.x;
    float cy = d.y - center.y;
    return atan(cy,cx);
}

vec2 rotateVector(vec2 v,float angle){
    float sa = sin(angle);
    float ca = cos(angle);
    return vec2(
        v.x * ca - v.y * sa,
        v.x * sa + v.y * ca
    );
}

float distSqr(vec2 one,vec2 two){

    float x = two.x - one.x;
    float y = two.y - one.y;

    return x*x + y*y;
}

void main() {

    vec2 uv1 = texCoord0;

    vec2 tCenter = vec2(0.5,0.5);
    vec2 offsVec = rotateVector(uv1 - tCenter,PI/2.);
    vec2 uv = tCenter + offsVec;

    float angle = computeAngle(uv,tCenter) + PI;

    float sectionAngle = (PI*2.0) / float(sectionCount);
    float sectionId = floor(angle / sectionAngle);
    float fAngle = sectionAngle * sectionId + (sectionAngle/2.0);
    vec2 cOffs = rotateVector(vec2(-distFromCenter,0.0),fAngle);

    vec2 center = tCenter + cOffs;
    float angle2 = computeAngle(uv,center) + PI;
    float dist = distSqr(uv,tCenter);

    if (abs(fAngle - angle2) <= sectionAngle / 2. && dist >= innerRadius*innerRadius && dist <= outRadius*outRadius){
        if (int(sectionId) == selectedSection){
            fragColor = sColor;
        }else{
            fragColor = color;
        }
    }else{
        fragColor = vec4(0.,0.,0.,0.);
    }

}
