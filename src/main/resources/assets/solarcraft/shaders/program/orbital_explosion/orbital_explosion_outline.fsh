#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform sampler2D OrbitalExplosionSampler;
uniform sampler2D OrbitalExplosionDepthSampler;

in vec2 texCoord;

out vec4 fragColor;


void main(){

    vec4 mainColor = texture(DiffuseSampler, texCoord);
    vec4 mainDepth = texture(DiffuseDepthSampler, texCoord);

    vec4 color = texture(OrbitalExplosionSampler,texCoord);
    vec4 depth = texture(OrbitalExplosionDepthSampler, texCoord);

    vec3 black = vec3(0.,0.,0.);

    fragColor = mainColor;
}