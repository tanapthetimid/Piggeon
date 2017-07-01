/* 
 * Copyright (c) 2017, Tanapoom Sermchaiwong
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name 'Piggeon' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package piggeon.util;

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
 * piggeon.engine during drawing using the ID stored by
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
