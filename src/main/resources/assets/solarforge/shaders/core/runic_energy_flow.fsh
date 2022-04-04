#version 150


uniform sampler2D Sampler0;
uniform float time;
uniform vec3 definedColor;


in vec2 texCoord0;

out vec4 fragColor;


const float pi = 3.14;

float sing(vec2 uv, float period,float m,float speed){
    return 0.015/distance(uv - vec2(0.,0.5),vec2(uv.x,sin(uv.x*m + time*speed + period)*0.25 *
    smoothstep(0.,1.,1.-abs(uv.x*2.-1.)) ))-0.05;
}

float cosg(vec2 uv, float period,float m,float speed){
    return 0.015/distance(uv - vec2(0.,0.5),vec2(uv.x,cos(uv.x*m + time*speed + period)*0.25 *
    smoothstep(0.,1.,1.-abs(uv.x*2.-1.)) ))-0.05;
}


void main(){

    vec2 uv = texCoord0;

    float col = sing(uv,pi*2.,pi*2.,1.5);
    col += sing(uv,1.57,pi,1.);
    col += sing(uv,0.,pi,1.);
    col += cosg(uv,pi*1.5,pi*3.,3.);

    col = min(col,1.);
    if (col < 0.0){
        discard;
    }


    fragColor = vec4(definedColor,col);
}