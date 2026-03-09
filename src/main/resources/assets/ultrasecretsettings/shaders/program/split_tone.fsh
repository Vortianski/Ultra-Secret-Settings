#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

uniform vec3 ScaleA;
uniform vec3 OffsetA;
uniform vec3 ScaleB;
uniform vec3 OffsetB;
uniform float Normalise;
uniform vec3 ColorModulate;

void main(){
    vec3 col = texture(DiffuseSampler, texCoord).rgb;

    vec3 normal = col*ScaleA - OffsetA;
    normal = (mix(normal, normalize(normal), Normalise)*ScaleB + OffsetB);

    fragColor = vec4(mix(col, normal*ColorModulate, 1), 1.0);
}