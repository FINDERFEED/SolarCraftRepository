#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainSampler;
uniform sampler2D OrbitalExplosionSampler;
uniform sampler2D OrbitalExplosionDepthSampler;
uniform sampler2D DepthTargetSampler;
uniform int horizontal;

//uniform float weight[20] = float[] (
//0.24197072451914337,
//0.22988215219394947,
//0.21785219988615365,
//0.20593630259830792,
//0.1941860994288753,
//0.18264913982269412,
//0.1713686557854439,
//0.16038339961243686,
//0.14972754559884888,
//0.1394306532091804,
//0.12951768830411353,
//0.12000909826520897,
//0.11092093623054103,
//0.10226502916218272,
//0.09404918411063215,
//0.0862774268196648,
//0.07895026672248871,
//0.07206498240857045,
//0.06561592177990028,
//0.05959481135375953
//);
uniform float l;
uniform float brightness;

uniform float weight[10] = float[](
0.24197072451914337,
0.21785217131914983,
0.19418604387179786,
0.17136857611339976,
0.14972744564497215,
0.12951757250634205,
0.11092080929169305,
0.09404905069346302,
0.07895013119551161,
0.0656157880233904
);

in vec2 texCoord;

out vec4 fragColor;


void main(){

    vec4 finalColor;
    //texture(DiffuseSampler,texCoord);

    vec2 offs = 1.0/textureSize(OrbitalExplosionSampler,0);

    vec3 result = texture(OrbitalExplosionSampler,texCoord).rgb * weight[0];

    float factor = 0.05;




    if (horizontal == 1){
        finalColor = vec4(0.);
        for (int i = 1; i < 10; ++i){
            result += texture(OrbitalExplosionSampler,texCoord + vec2(offs.x*float(i) * l,0.)).rgb * weight[i]*brightness;
            result += texture(OrbitalExplosionSampler,texCoord - vec2(offs.x*float(i) * l,0.)).rgb * weight[i]*brightness;
        }
    }else{
        finalColor = texture(MainSampler,texCoord);
        for (int i = 1; i < 10; ++i){
            result += texture(DiffuseSampler,texCoord + vec2(0.,offs.y*float(i) * l)).rgb * weight[i]*brightness;
            result += texture(DiffuseSampler,texCoord - vec2(0.,offs.y*float(i) * l)).rgb * weight[i]*brightness;
        }
    }


    if (horizontal == 0){
        result = vec3(1.0) - exp(-result);
    }

    finalColor.rgb += result;


    fragColor = finalColor;

}