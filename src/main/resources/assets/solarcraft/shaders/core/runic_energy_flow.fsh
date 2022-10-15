#version 150


uniform sampler2D Sampler0;
uniform float time;
uniform vec3 definedColor;
uniform vec3 innerColor;
uniform float modifier;
uniform vec4 ColorModulator;

in vec2 texCoord0;

out vec4 fragColor;



const float pi = 3.14;

float sing(vec2 uv, float period,float m,float speed,float md){
    return 0.015/distance(uv - vec2(0.,0.5),vec2(uv.x,sin(uv.x*m + time*speed + period)*0.25 *
    smoothstep(0.,1.,1.-abs(uv.x*2.-1.)) ))*md-0.05;
}

float cosg(vec2 uv, float period,float m,float speed,float md){
    return 0.015/distance(uv - vec2(0.,0.5),vec2(uv.x,cos(uv.x*m + time*speed + period)*0.25 *
    smoothstep(0.,1.,1.-abs(uv.x*2.-1.)) ))*md-0.05;
}


void main(){

    vec2 uv = texCoord0;

    float col = sing(uv,pi*2.,pi*2.,1.5,1.);
    col += sing(uv,1.57,pi,1.,1.);
    col += sing(uv,0.,pi,1.,1.);
    col += cosg(uv,pi*1.5,pi*3.,3.,1.);

    float colGradient = sing(uv,pi*2.,pi*2.,1.5,modifier);
    colGradient += sing(uv,1.57,pi,1.,modifier);
    colGradient += sing(uv,0.,pi,1.,modifier);
    colGradient += cosg(uv,pi*1.5,pi*3.,3.,modifier);

    col = min(col,1.);
    colGradient = min(colGradient,1.);
    if (col < 0.0){
        discard;
    }

    vec3 color = vec3(0.,0.,0.);
    color = mix(definedColor,innerColor,colGradient);



    fragColor = vec4(color,col);
}