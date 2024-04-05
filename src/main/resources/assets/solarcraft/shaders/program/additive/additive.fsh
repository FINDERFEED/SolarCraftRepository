#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D BaseSampler;

uniform vec4 ColorModulate;

in vec2 texCoord;

out vec4 fragColor;

void main(){

    vec4 color = texture(DiffuseSampler, texCoord) * ColorModulate;
    vec4 baseColor = texture(BaseSampler,texCoord);

    vec4 col = vec4(color.x + baseColor.x,color.y + baseColor.y,color.z + baseColor.z,baseColor.w);

    fragColor = col;
}