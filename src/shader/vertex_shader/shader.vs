#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 tex_coords;

uniform mat4 translate;
uniform mat4 scale;
uniform mat4 rotate;

out vec2 pass_tex_coords;

void main()
{
	pass_tex_coords = tex_coords;
	gl_Position = vec4(position, 0.0, 1.0) * scale * rotate * translate;
}