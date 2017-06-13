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

package util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.system.MemoryStack;

import java.nio.*;

import java.util.HashMap;
/**
 * This class contains static methods used
 * for loading image files from (String path)
 * into a texture stored statically by OpenGL.
 * 
 * This texture can be retrieved later by the
 * engine during drawing using the ID stored by
 * each GameObject
 */
public class ImageUtils
{
	//HashMap for caching texture ID -- load a texture only once
	private static HashMap<String, ImageInfo> loadedTextures = new HashMap<>();

    /**
     * Loads image from specified path. Returns an ImageInfo
     * object that stores critical information such as id, width,
     * and height.
     * 
     * @param   path (required) String path of jpg or png image
     * @return                  ImageInfo object.
     */
    public static ImageInfo loadImage(String path)
    {
    	ImageInfo imageInfo = loadedTextures.get(path);
    	if(imageInfo == null){
	        /*generates texture and binds texture*/
	        int texture = glGenTextures();
	        glBindTexture(GL_TEXTURE_2D, texture);
	        
	        /*sets texture sampling filter for scaling images*/
	        
	        /*scale down filter*/
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	        /*scale up filter*/
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	        
	        int width = 0;
	        int height = 0;
	        
	        /*loads image into byte buffer then makes it available to GPU through OpenGl*/
	        try (MemoryStack stack = MemoryStack.stackPush()) 
	        {
	            IntBuffer w = stack.mallocInt(1);
	            IntBuffer h = stack.mallocInt(1);
	            IntBuffer comp = stack.mallocInt(1);

	            ByteBuffer image = stbi_load(path,w,h,comp,4);
	            if(image == null)
	            {
	                System.err.println("failed to load image");
	            }

	            width = w.get();
	            height = h.get();

	            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
	            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
                glGenerateMipmap(GL_TEXTURE_2D);
            }
	        imageInfo = new ImageInfo(texture, width, height);
	        loadedTextures.put(path, imageInfo);
	    }
	    return imageInfo;
    }
    
    /**
     * loads image into a ByteBuffer object and returns
     * an Object[] consisting of the ByteBuffer, width,
     * and height.
     * 
     * @param path (required)   String path of image file
     * @return                  Returns ByteBuffer, width, height - in Object[] form
     */
    public static Object[] loadImageToByteBuffer(String path)
    {
        Object[] value = new Object[3];
        
        try (MemoryStack stack = MemoryStack.stackPush()) 
        {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            ByteBuffer image = stbi_load(path,w,h,comp,4);
            if(image == null)
            {
                System.err.println("failed to load image");
            }
            value[0] = image;
            value[1] = w.get();
            value[2] = h.get();
        }
        return value;
    }
    
}
