

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