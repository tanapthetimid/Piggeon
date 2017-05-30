package src;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.*;
import java.util.concurrent.atomic.*;

public class InputHandler{
    public static boolean[] keys = new boolean[65536];

    private static AtomicReference<Double> mouseX = new AtomicReference<>();
    private static AtomicReference<Double> mouseY = new AtomicReference<>();
    private static AtomicBoolean mouseDown = new AtomicBoolean();

    public static boolean isKeyDown(int keycode){
        return keys[keycode];
    }

    public static int getMouseX(){
        return (int)(mouseX.get()+0.5);
    }

    public static int getMouseY(){
        return (int)(mouseY.get()+0.5);
    }

    public static boolean isMouseDown(){
        return mouseDown.get();
    }
    
    /**
     * Handles GLFWMouseButtonCallback
     */
    public static class MouseAction extends GLFWMouseButtonCallback{
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if(action == GLFW_PRESS){
                mouseDown.set(true);
            }else if(action == GLFW_RELEASE){
                mouseDown.set(false);
            }
        }
    }

    /**
     * Handles cursor position callback
     */
    public static class MouseInput extends GLFWCursorPosCallback{
        @Override
        public void invoke(long window, double xpos, double ypos){
            mouseX.set(xpos);
            mouseY.set(ypos);
        }
    }
    
    /**
     * Handles GLFWKeyCallback
     */
    public static class KeyboardInput extends GLFWKeyCallback{
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods){
            keys[key] = action != GLFW_RELEASE;
        }
    } 
}