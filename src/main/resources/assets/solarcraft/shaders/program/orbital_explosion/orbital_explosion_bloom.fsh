#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D OrbitalExplosionSampler;
uniform sampler2D OrbitalExplosionDepthSampler;
uniform sampler2D DepthTargetSampler;
uniform int horizontal;

uniform float weight[20] = float[] (
0.24197072451914337,
0.22988215219394947,
0.21785219988615365,
0.20593630259830792,
0.1941860994288753,
0.18264913982269412,
0.1713686557854439,
0.16038339961243686,
0.14972754559884888,
0.1394306532091804,
0.12951768830411353,
0.12000909826520897,
0.11092093623054103,
0.10226502916218272,
0.09404918411063215,
0.0862774268196648,
0.07895026672248871,
0.07206498240857045,
0.06561592177990028,
0.05959481135375953
);

in vec2 texCoord;

out vec4 fragColor;


void main(){

    vec4 mainDepth = texture(DepthTargetSampler, texCoord);

    vec4 color = texture(OrbitalExplosionSampler,texCoord);
    vec4 depth = texture(OrbitalExplosionDepthSampler, texCoord);

    vec4 finalColor = texture(DiffuseSampler,texCoord);

    vec2 offs = 1.0/textureSize(OrbitalExplosionSampler,0);

    vec3 result = texture(OrbitalExplosionSampler,texCoord).rgb * weight[0];

    if (horizontal == 1){
        for (int i = 1; i < 20; ++i){
            result += texture(OrbitalExplosionSampler,texCoord + vec2(offs.x*float(i),0.)).rgb * weight[i];
            result += texture(OrbitalExplosionSampler,texCoord - vec2(offs.x*float(i),0.)).rgb * weight[i];
        }
    }else{
        for (int i = 1; i < 20; ++i){
            result += texture(OrbitalExplosionSampler,texCoord + vec2(0.,offs.y*float(i))).rgb * weight[i];
            result += texture(OrbitalExplosionSampler,texCoord - vec2(0.,offs.y*float(i))).rgb * weight[i];
        }
    }


    finalColor.rgb += result;

    fragColor = finalColor;

}