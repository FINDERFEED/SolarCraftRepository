#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D OrbitalExplosionSampler;
uniform sampler2D OrbitalExplosionDepthSampler;
uniform sampler2D DepthTargetSampler;

in vec2 texCoord;

out vec4 fragColor;


void main(){

    vec4 mainDepth = texture(DepthTargetSampler, texCoord);

    vec4 color = texture(OrbitalExplosionSampler,texCoord);
    vec4 depth = texture(OrbitalExplosionDepthSampler, texCoord);

    vec4 finalColor = texture(DiffuseSampler,texCoord);

    for (float x = -0.1; x < 0.1;x+=0.02){
        vec2 cord = vec2(texCoord.x + x,texCoord.y);
        vec4 c = texture(OrbitalExplosionSampler,cord);
        if (c.xyz != vec3(0.)){
            finalColor.x += smoothstep(c.x,0.,abs(x)/0.1);
            finalColor.y += smoothstep(c.y,0.,abs(x)/0.1);
            finalColor.z += smoothstep(c.z,0.,abs(x)/0.1);
            finalColor.w += smoothstep(c.w,0.,abs(x)/0.1);
            break;
        }
    }

    fragColor = finalColor;

}