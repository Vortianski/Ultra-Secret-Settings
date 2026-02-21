#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D OutlineSampler;
uniform vec2 InSize;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main(){
    vec2 unused = InSize;
    vec4 diffuseTexel = texture(DiffuseSampler, texCoord);
    vec4 outlineTexel = texture(OutlineSampler, texCoord);
    fragColor = vec4(diffuseTexel.rgb + diffuseTexel.rgb * outlineTexel.rgb * vec3(0.75), 1.0);
}
