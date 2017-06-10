/*
 * Copyright 2017, Tanapoom Sermchaiwong, All rights reserved.
 *
 * This file is part of Piggeon.
 *
 * Piggeon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Piggeon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Piggeon.  If not, see <http://www.gnu.org/licenses/>.
 */

package util;

import engine.ShaderProgram;

import java.io.*;

/**
* Utility class for loading files
*/
public class FileUtils
{
	public static String readStringFromFile(String filename)
	{
		StringBuilder source = new StringBuilder();
		try
		{
			BufferedReader reader 
					= new BufferedReader(
							new InputStreamReader(
								ShaderProgram.class
											 .getClassLoader()
											 .getResourceAsStream(filename)));
			String line;
			while((line = reader.readLine()) != null)
			{
				source.append(line).append("\n");
			}

			reader.close();
		}
		catch(Exception e)
		{
			System.err.println("Err loading file: " + filename);
			e.printStackTrace();
		}

		return source.toString();
	}
}