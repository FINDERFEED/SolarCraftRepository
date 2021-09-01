#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec2 UV2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat4 modelview;
uniform float heightLimit;
uniform float modifier;

out vec4 vertexColor;
out vec2 texCoord0;
out vec2 texCoord2;


void main() {

    float x;
    if (Position.x > 0){
        x = 1;
    }else{
        x = -1;
    }

    float z;
    if (Position.z > 0){
        z = 1;
    }else{
        z = -1;
    }
    float xzmod;
    if (Position.y <= heightLimit){
          xzmod = modifier/Position.y;
    }else{
          xzmod = 0;
    }
    gl_Position = ProjMat *modelview* vec4(vec3(Position.x+xzmod*x,Position.y,Position.z+xzmod*z),1.0);

    vertexColor = Color;
    texCoord0 = UV0;
    texCoord2 = UV2;
}
