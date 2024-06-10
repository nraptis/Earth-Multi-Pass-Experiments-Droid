uniform lowp vec4 ModulateColor;
varying lowp vec2 TextureCoordinatesOut;
uniform sampler2D Texture;
uniform lowp vec2 TextureSize;

void main(void) {

    lowp vec2 center = TextureCoordinatesOut;
    lowp vec3 sum = vec3(0.0, 0.0, 0.0);
    lowp float stepX = (1.0 / TextureSize.x);

    sum += texture2D(Texture, vec2(center.x - stepX * 3.0, center.y)).rgb * 0.07;
    sum += texture2D(Texture, vec2(center.x - stepX * 2.0, center.y)).rgb * 0.105;
    sum += texture2D(Texture, vec2(center.x - stepX, center.y)).rgb * 0.175;
    sum += texture2D(Texture, vec2(center.x, center.y)).rgb * 0.30;
    sum += texture2D(Texture, vec2(center.x + stepX, center.y)).rgb * 0.175;
    sum += texture2D(Texture, vec2(center.x + stepX * 2.0, center.y)).rgb * 0.105;
    sum += texture2D(Texture, vec2(center.x + stepX * 3.0, center.y)).rgb * 0.07;

    lowp float r = clamp(sum.r, 0.0, 1.0);
    lowp float g = clamp(sum.g, 0.0, 1.0);
    lowp float b = clamp(sum.b, 0.0, 1.0);

    gl_FragColor = vec4(r, g, b, 1.0);
}
