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

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.*;
import java.util.concurrent.atomic.*;

/**
 * This class handles Inputs and various callbacks from GLFW
 * Other objects/classes can statically call methods in this class
 * to query user input. 
 * 
 * Time sensitive input must be handled individually.
 */

public class InputHandler
{
    public static boolean[] keys = new boolean[65536];

    private static AtomicReference<Double> mouseX = new AtomicReference<>();
    private static AtomicReference<Double> mouseY = new AtomicReference<>();
    private static AtomicBoolean mouseDown = new AtomicBoolean();
	
	/**
	 * checks if the specified key is pressed
	 *
	 * @param keycode		- the Integer keycode representation of a key
	 * 
	 * @return 				- boolean; true = pressed; false = not pressed
	 */
    public static boolean isKeyDown(int keycode)
    {
        return keys[keycode];
    }

	//returns the mouse X location
    public static int getMouseX()
    {
        return (int)(mouseX.get()+0.5);
    }

	//returns the mouse Y location
    public static int getMouseY()
    {
        return (int)(mouseY.get()+0.5);
    }
	
	//mouse press check interfaces
    public static boolean isMouseDown()
    {
        return mouseDown.get();
    }
    
    /**
     * Handles GLFWMouseButtonCallback
     */
    public static class MouseAction extends GLFWMouseButtonCallback
    {
        @Override
        public void invoke(long window, int button, int action, int mods) 
        {
            if(action == GLFW_PRESS)
            {
                mouseDown.set(true);
            }else if(action == GLFW_RELEASE)
            {
                mouseDown.set(false);
            }
        }
    }

    /**
     * Handles cursor position callback
     */
    public static class MouseInput extends GLFWCursorPosCallback
    {
        @Override
        public void invoke(long window, double xpos, double ypos)
        {
            mouseX.set(xpos);
            mouseY.set(ypos);
        }
    }
    
    /**
     * Handles GLFWKeyCallback
     */
    public static class KeyboardInput extends GLFWKeyCallback
    {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods)
        {
            keys[key] = action != GLFW_RELEASE;
        }
    } 
}