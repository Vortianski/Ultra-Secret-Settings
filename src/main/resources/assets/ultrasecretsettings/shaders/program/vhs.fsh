#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float Time;
uniform vec3 EdgeColor;
uniform float GlowIntensity;
uniform float FishEye;

in vec2 texCoord;
out vec4 fragColor;

float random(vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

float noise2(vec2 p) {
    return random(floor(p * 50.0) + Time * 5.0);
}

void main() {
    vec2 uv = texCoord;

    vec2 uvCentered = uv - 0.5;
    float r = length(uvCentered);
    float fisheyeStrength = FishEye;
    float rDistorted = r * (1.0 + fisheyeStrength * r * r);
    vec2 uvDistorted = uvCentered;
    if (r > 0.001) {
        uvDistorted = uvCentered * (rDistorted / r);
    }
    vec2 fisheyeUV = uvDistorted + 0.5;
    fisheyeUV = clamp(fisheyeUV, 0.0, 1.0);

    float distFromCenter = length(fisheyeUV - 0.5) * 2.0;
    float edgeDistortion = distFromCenter * 0.005 * GlowIntensity;
    float rChan = texture(DiffuseSampler, fisheyeUV + vec2(edgeDistortion, 0.0)).r;
    float gChan = texture(DiffuseSampler, fisheyeUV).g;
    float bChan = texture(DiffuseSampler, fisheyeUV - vec2(edgeDistortion, 0.0)).b;
    vec3 color = vec3(rChan, gChan, bChan);

    float grain = random(fisheyeUV * InSize + Time) * 0.04;
    float blockNoise = noise2(vec2(fisheyeUV.x * 20.0, fisheyeUV.y * 15.0)) * 0.03;
    float tapeLines = sin(fisheyeUV.y * InSize.y * 0.8 + Time * 10.0) * 0.02;
    tapeLines *= step(0.7, random(vec2(floor(fisheyeUV.y * 30.0), Time)));
    float totalNoise = grain + blockNoise + tapeLines;
    color += totalNoise;

    float scanlinePos = fract(Time * 2);
    float lineWidth = 0.03;
    float scanline = smoothstep(0.0, lineWidth, abs(fisheyeUV.y - scanlinePos));
    scanline = 1.0 - scanline;
    scanline = mix(1.0, 0.9, scanline);
    color *= scanline;

    float vignette = 1.0 - distFromCenter * 0.6;
    color *= vignette;

    color = mix(color, color * EdgeColor, 0.2);

    fragColor = vec4(color, 1.0);
}