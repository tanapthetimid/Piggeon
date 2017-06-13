/*
 * Copyright 2017, Tanapoom Sermchaiwong, All rights reserved.
 *
 * This file is part of Piggeon.
 *
 * Piggeon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Piggeon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Piggeon.  If not, see <http://www.gnu.org/licenses/>.
 */

package piggeon.engine;

import piggeon.util.FileUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram
{
	private int programID;
	private int fragmentShaderID;
	private int vertexShaderID;

	/**
	 * Create a piggeon.shader program
	 */
	public ShaderProgram()
	{
		programID = glCreateProgram();
	}

	/**
	 * Attach a Vertex Shader to this program
	 *
	 * @param filename File name of vertex piggeon.shader source
	 */
	public void attachVertexShader(String filename)
	{
		//load source
		String vertexShaderSource = FileUtils.readStringFromFile(filename);

		//create piggeon.shader and set source
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderID, vertexShaderSource);

		//compile piggeon.shader
		glCompileShader(vertexShaderID);

		//check for error
		if(glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Error creating vertex piggeon.shader\n"
				+ glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));

		//attach piggeon.shader
		glAttachShader(programID, vertexShaderID);
	}

	/**
	 * Attach a Fragment Shader to this program
	 *
	 * @param filename File name of fragment piggeon.shader source
	 */
	public void attachFragmentShader(String filename)
	{
		//load source
		String fragmentShaderSource = FileUtils.readStringFromFile(filename);

		//create piggeon.shader and set source
		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderID, fragmentShaderSource);

		//compile piggeon.shader
		glCompileShader(fragmentShaderID);

		//check for error
		if(glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Error creating fragment piggeon.shader\n"
				+ glGetShaderInfoLog(fragmentShaderID, glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH)));

		//attach piggeon.shader
		glAttachShader(programID, fragmentShaderID);
	}

	/**
	 * link this program to use it
	 */
	public void link()
	{
		//link this program
		glLinkProgram(programID);

		//check for linking error
		if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
			throw new RuntimeException("Unable to link piggeon.shader program!");
	}

	public void bind()
	{
		glUseProgram(programID);
	}

	public void unbind()
	{
		glUseProgram(0);
	}

	public void dispose()
	{
		unbind();

		//detach shaders
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);

		//delete piggeon.shader
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		//delete program
		glDeleteProgram(programID);
	}

	public int getID()
	{
		return programID;
	}
}