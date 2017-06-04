#version 330 core

in vec2 pass_tex_coords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
	fragColor = texture(textureSampler, pass_tex_coords);
}