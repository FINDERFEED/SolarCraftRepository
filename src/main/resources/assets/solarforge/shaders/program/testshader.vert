#version 120


void main(){
    vec4 vert = gl_Vertex;
    gl_Position = gl_ModelViewProjectionMatrix * vert;
}