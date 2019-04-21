#version 330

in vec2 pass_textureCoords;

out vec4 fragColor;

uniform vec3 color;
uniform sampler2D textureSampler;

void main()
{
	vec4 textureColor = texture(textureSampler, pass_textureCoords);

	if(textureColor == vec4(0,0,0,1)){
		fragColor = vec4(color, 1);
	}
	else{
		fragColor = textureColor * vec4(color, 1);
	}

}