package engine;

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