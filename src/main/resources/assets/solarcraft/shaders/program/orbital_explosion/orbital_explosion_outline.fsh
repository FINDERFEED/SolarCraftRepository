#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform sampler2D OrbitalExplosionSampler;
uniform sampler2D OrbitalExplosionDepthSampler;

in vec2 texCoord;

out vec4 fragColor;


void main(){

    vec4 mainColor = texture(DiffuseSampler, texCoord);
    vec4 mainDepth = texture(DepthTargetSampler, texCoord);

    vec4 color = texture(OrbitalExplosionSampler,texCoord);
    vec4 depth = texture(OrbitalExplosionDepthSampler, texCoord);

    vec4 col = vec4(depth.r,depth.r,depth.r,1.0);

    if (depth.r < mainDepth.r){
        fragColor = vec4(color.x,color.y,color.z,1.0);
    }else{
        fragColor = vec4(mainColor.x,mainColor.y,mainColor.z,1.0);
    }

    //fragColor = vec4(mainDepth.x,mainDepth.x,mainDepth.x,1.0);
}