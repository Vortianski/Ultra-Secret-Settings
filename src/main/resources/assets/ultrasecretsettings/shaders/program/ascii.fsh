// This file has been modified from the original.
// Original: Souper Secret Settings
// Changes: Ported to NeoForge and made it work without luminance

#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

out vec4 fragColor;

uniform vec3 Scale;
uniform ivec2 Grid;
uniform vec2 Levels;
uniform vec4 Thresholds;
uniform ivec3 BitShift;
uniform ivec4 Char1A;
uniform ivec4 Char1B;
uniform ivec4 Char2A;
uniform ivec4 Char2B;
uniform ivec4 Char3A;
uniform ivec4 Char3B;
uniform ivec4 Char4A;
uniform ivec4 Char4B;
uniform ivec4 Char5A;
uniform ivec4 Char5B;

bool getChar(ivec4 charA, ivec4 charB, ivec2 index) {
    return (((((index.y/4)%2 > 0 ? charB : charA)[index.y%4] * BitShift.y) ^ BitShift.z) & (BitShift.x << index.x)) > 0;
}

void main() {
    ivec2 pixelCoord = ivec2(texCoord/oneTexel/Scale.xy);

    vec3 col = texture(DiffuseSampler, mix(pixelCoord, (pixelCoord/Grid)*Grid, Scale.z)*oneTexel*Scale.xy).rgb;

    float m = max(max(col.r, col.g), col.b);
    col /= m;
    m *= Levels.y;
    float l = fract(m*0.9999);

    ivec4 charA;
    ivec4 charB;
    if (l < Thresholds.x) {
        charA = Char1A;
        charB = Char1B;
    } else if (l < Thresholds.y) {
        charA = Char2A;
        charB = Char2B;
    } else if (l <= Thresholds.z) {
        charA = Char3A;
        charB = Char3B;
    } else if (l <= Thresholds.w) {
        charA = Char4A;
        charB = Char4B;
    } else {
        charA = Char5A;
        charB = Char5B;
    }

    ivec2 coord = (Grid-ivec2(1)) - (pixelCoord)%Grid;
    ivec2 offset = (ivec2(8) - Grid) / 2;
    if (Grid.x < 8) {
        coord.x += offset.x;
    }
    if (Grid.y < 8) {
        coord.y += offset.y;
    }

    col = round(col*Levels.x)/Levels.x * (getChar(charA, charB, coord) ? ceil(m) : min(floor(m),Levels.y-1))/Levels.y;
    fragColor = vec4(mix(texture(DiffuseSampler, texCoord).rgb, col, 1), 1.0);
}