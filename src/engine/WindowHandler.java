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

package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.opengl.*;
import org.lwjgl.glfw.*;
import util.ImageUtils;

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
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_FOCUSED, GL_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        /*stores created window id*/
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