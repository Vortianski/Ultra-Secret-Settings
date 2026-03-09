#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

uniform vec2 Mirrors;
uniform vec2 Position;

uniform float Wrapping;

vec4 wrapTexture(sampler2D tex, vec2 coord) {
    return texture(tex, mix(coord, fract(coord), Wrapping));
}

vec2 mirrorAlongLines(vec2 point, vec3 lineA, vec3 lineB, float iterations) {
    for (int i = 0; i < int(iterations); i++) {
        vec3 a;
        vec3 b;
        if (i % 2 == 0) {
            a = lineA;
            b = lineB;
        } else {
            a = lineB;
            b = lineA;
        }

        float distanceA = a.x * point.x + a.y * point.y + a.z;
        float distanceB = b.x * point.x + b.y * point.y + b.z;
        if (distanceA >= 0.0 && distanceB >= 0.0) {
            return point;
        }

        point -= a.xy * distanceA * 2.0;
    }

    return point;
}

vec3 getLine(float angle, float offset) {
    return vec3(cos(angle), -sin(angle), offset);
}

void main(){
    // center coords around 0,0
    vec2 centered = vec2(texCoord.x - 0.5, texCoord.y - 0.5);

    // iterations: use absolute value and ensure integer count
    float itf = abs(Mirrors.x - 1.0);
    // clamp iterations so we don't run huge loops (safety)
    itf = clamp(itf, 0.0, 64.0);

    vec2 coord = mirrorAlongLines(
    centered,
    getLine(Mirrors.y * 6.28318530718, Position.x),
    getLine((Mirrors.y + (1.0 / Mirrors.x) + 0.5) * 6.28318530718, Position.y),
    floor(itf + 0.5)
    );

    vec4 col = wrapTexture(DiffuseSampler, vec2(coord.x + 0.5, coord.y + 0.5));
    vec4 temp = mix(texture(DiffuseSampler, texCoord), col, 1.0);
    fragColor = vec4(temp.rgb, 1.0); // force alpha = 1
}