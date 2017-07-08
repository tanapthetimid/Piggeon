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

package piggeon.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.opengl.*;
import org.lwjgl.glfw.*;
import piggeon.util.ImageUtils;

import java.nio.*;

public class WindowHandler
{
    public static long window;

    /**
     * Initiates an OpenGl program and creates a window.
     * 
     * @param title         Title of the window.
     * @param cursorFile    Location of cursor file used as in the game
     * @param width         Width of screen
     * @param height        Height of screen
     * @param winOffsetX    horizontal offset of window on creation
     * @param winOffsetY    vertical offset of window on creation  
     * @param borderWidth   Width of black border around the game window - 0 for none
     */
    public static void init(String title, String cursorFile
                            , int width, int height, int winOffsetX, int winOffsetY, int borderWidth)
    {
        if(!glfwInit())
        {
            System.err.println("GLFW initialization failed");
        }
        
        /*window hints/settings*/
        //glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_FOCUSED, GL_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        //enable glsl 330 core on mac os
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        /*stores created window textureID*/
        window = glfwCreateWindow(width+borderWidth*2, height+borderWidth*2, title, NULL, NULL);

        if(window == NULL)
        {
            System.err.println("Could not create window!");
        }

        /*unused line*/
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        /*===========*/
        
        /*Set input callbacks*/
        glfwSetKeyCallback(window, new InputHandler.KeyboardInput());
        glfwSetCursorPosCallback(window, new InputHandler.MouseInput());
        glfwSetMouseButtonCallback(window, new InputHandler.MouseAction());

        glfwSetWindowPos(window, winOffsetX, winOffsetY);
        
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        GL.createCapabilities();
        
        enableGlFlags();
        
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glfwSwapInterval(1);
        glHint( GL_LINE_SMOOTH_HINT, GL_NICEST ) ;
        
        setCursorImage(cursorFile);
    }
    
    private static void enableGlFlags()
    {
        glEnable(GL_BLEND);
        glEnable(GL_LINE_SMOOTH) ;
    }
    
    /**
     * Sets cursor image to the image at location cursorFile
     */
    private static void setCursorImage(String cursorFile)
    {
        if(cursorFile != null && !cursorFile.isEmpty())
        {
            Object[] values = ImageUtils.loadImageToByteBuffer(cursorFile);
            ByteBuffer buffer = (ByteBuffer) values[0];
            GLFWImage cursorImage = GLFWImage.create();
            cursorImage.width((int)values[1]);
            cursorImage.height((int)values[2]);
            cursorImage.pixels(buffer);
            
            long cursorID = glfwCreateCursor(cursorImage, (int)values[1]/2, (int)values[2]/2);        
            glfwSetCursor(window, cursorID);
            
            //glfwSetCursor(window, glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));
        }
    }
    
    /**
     * Returns the ID of the main window.
     */
    public static long getWindowId()
    {
        return window;
    }
}