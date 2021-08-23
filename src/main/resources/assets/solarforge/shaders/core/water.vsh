#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec2 UV2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat4 modelview;
uniform mat4 projection;
uniform float time;



out vec4 vertexColor;
out vec2 texCoord0;
out vec2 texCoord2;


float distancesq(vec3 dotone,vec3 dottwo){
    float x = dottwo.x - dotone.x;
    float y = dottwo.y - dotone.y;
    return sqrt(x*x+y*y);
}


void main() {


    float razn = distancesq(Position,vec3(0.0,0.0,0.0))*15 +time;

    gl_Position = ProjMat * ModelViewMat * vec4(vec3(Position.x,Position.y,Position.z +sin(razn)*0.25),1.0);

    vertexColor = Color;
    texCoord0 = UV0;
    texCoord2 = UV2;
}
