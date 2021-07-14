#version 120

uniform int screenW;
uniform int screenH;
uniform sampler2D sampler;
uniform mat4 modelview;
uniform mat4 projection;

uniform int time;
uniform float distance;
uniform float intensity;
uniform float size;
uniform float radiusCircle;

varying vec2 texCoords;

float roundToNearest(float val, float base) {
    return ceil(val/base)*base;
}

bool isInCircle(float radius,vec2 dot,vec2 center){
        float a = (dot.x - center.x)*(dot.x - center.x);
        float b = (dot.y - center.y)*(dot.y - center.y);
        if ((a + b) <= radius*radius ){
            return true;
        }
    return false;
}

bool isOutOfCircle(float radius,vec2 dot,vec2 center){
    float a = (dot.x - center.x)*(dot.x - center.x);
    float b = (dot.y - center.y)*(dot.y - center.y);
    if ((a + b) >= radius*radius ){
        return true;
    }
    return false;
}


void main() {
    vec2 texUV = texCoords;
    if (isInCircle(radiusCircle+0.05,texCoords,vec2(0.5,0.5))){
        if (isOutOfCircle(radiusCircle-0.05,texCoords,vec2(0.5,0.5))){
            float density = 1.0+intensity*12.0;
            texUV.x = roundToNearest(texUV.x, density/(float(screenW)));
            texUV.y = roundToNearest(texUV.y, density/(float(screenH)));
            texUV.y += intensity*0.004*sin(float(time)+texUV.x*8.0);
        }
    }
    vec4 color = texture2D(sampler, texUV);
    gl_FragColor = vec4(color.x, color.y, color.z, color.a);
}