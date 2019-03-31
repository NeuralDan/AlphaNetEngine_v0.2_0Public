#version 330

in vec2 pass_textureCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
	fragColor = texture(textureSampler, pass_textureCoords);
    //fragColor = vec4(0.0, 0.5, 0.5, 1.0);
}